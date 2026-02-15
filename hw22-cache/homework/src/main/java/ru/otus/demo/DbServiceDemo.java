package ru.otus.demo;

import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.HwCache;
import ru.otus.HwListener;
import ru.otus.MyCache;
import ru.otus.crm.model.core.repository.DataTemplateHibernate;
import ru.otus.crm.model.core.repository.HibernateUtils;
import ru.otus.crm.model.core.sessionmanager.TransactionManagerHibernate;
import ru.otus.crm.dbmigrations.MigrationsExecutorFlyway;
import ru.otus.crm.model.Address;
import ru.otus.crm.model.Client;
import ru.otus.crm.model.Phone;
import ru.otus.crm.proxy.DBClientProxy;
import ru.otus.crm.service.DbServiceClientImpl;

public class DbServiceDemo {

    private static final Logger log = LoggerFactory.getLogger(DbServiceDemo.class);

    public static final String HIBERNATE_CFG_FILE = "hibernate.cfg.xml";

    public static void main(String[] args) throws InterruptedException {
        var configuration = new Configuration().configure(HIBERNATE_CFG_FILE);

        var dbUrl = configuration.getProperty("hibernate.connection.url");
        var dbUserName = configuration.getProperty("hibernate.connection.username");
        var dbPassword = configuration.getProperty("hibernate.connection.password");

        new MigrationsExecutorFlyway(dbUrl, dbUserName, dbPassword).executeMigrations();

        var sessionFactory = HibernateUtils.buildSessionFactory(configuration, Client.class, Address.class, Phone.class);

        var transactionManager = new TransactionManagerHibernate(sessionFactory);
        ///
        var clientTemplate = new DataTemplateHibernate<>(Client.class);

        // Создание кеша, listener и клиентских сервисов
        HwCache<Long, Client> cache = new MyCache<>();
        cache.addListener(new HwListener<Long, Client>() {
            @Override
            public void notify(Long key, Client value, String action) {
                log.info("key:{}, value:{}, action: {}", key, value, action);
            }
        });
        var dbServiceClient = new DbServiceClientImpl(transactionManager, clientTemplate);
        var dbServiceClientWithCache = DBClientProxy.createDBServiceClient(
                new DbServiceClientImpl(transactionManager, clientTemplate),
                cache);

        ///
        dbServiceClient.saveClient(new Client("FirstTestClient"));
        dbServiceClientWithCache.saveClient(new Client("SecondTestClient"));

        // Добавление клиентов (+/- одинаково)
        long timer = System.currentTimeMillis();
        var client = dbServiceClient.saveClient(new Client("dbService"));
        log.info("Insert client without cache: {}ms\n{}", System.currentTimeMillis() - timer, client);

        timer = System.currentTimeMillis();
        var clientWithCache = dbServiceClientWithCache.saveClient(new Client("dbServiceWithCache"));
        log.info("Insert client with cache: {}ms\n{}", System.currentTimeMillis() - timer, clientWithCache);
        Thread.sleep(10000L);

        // Поиск клиентов (с кешем сильно быстрее)
        timer = System.currentTimeMillis();
        var clientSecondSelected = dbServiceClient
                .getClient(client.getId())
                .orElseThrow(() -> new RuntimeException("Client not found, id:" + client.getId()));
        log.info("Select client without cache: {}ms\n{}", System.currentTimeMillis() - timer, clientSecondSelected);

        timer = System.currentTimeMillis();
        var clientSecondSelectedWithCache = dbServiceClientWithCache
                .getClient(clientWithCache.getId())
                .orElseThrow(() -> new RuntimeException("Client not found, id:" + clientWithCache.getId()));
        log.info("Select client with cache: {}ms\n{}", System.currentTimeMillis() - timer, clientSecondSelectedWithCache);
        Thread.sleep(10000L);

        // Обновление клиентов (с кешем побыстрее О_о)
        timer = System.currentTimeMillis();
        dbServiceClient.saveClient(new Client(clientSecondSelected.getId(), "dbServiceUpdated"));
        log.info("Update client without cache: {}ms\n{}", System.currentTimeMillis() - timer, clientSecondSelected);
        // Смотрим, что точно обновился
        var clientUpdated = dbServiceClient
                .getClient(clientSecondSelected.getId())
                .orElseThrow(() -> new RuntimeException("Client not found, id:" + clientSecondSelected.getId()));
        log.info("clientUpdated:{}", clientUpdated);

        timer = System.currentTimeMillis();
        dbServiceClientWithCache.saveClient(new Client(clientSecondSelectedWithCache.getId(), "dbServiceWithCacheUpdated"));
        log.info("Update client with cache: {}ms\n{}", System.currentTimeMillis() - timer, clientSecondSelectedWithCache);
        // Смотрим, что точно обновился
        var clientUpdatedWithCache = dbServiceClientWithCache
                .getClient(clientSecondSelectedWithCache.getId())
                .orElseThrow(() -> new RuntimeException("Client not found, id:" + clientSecondSelectedWithCache.getId()));
        log.info("clientUpdatedWithCache:{}", clientUpdatedWithCache);
        Thread.sleep(10000L);

        // Ищем клиента, который точно не в кеше (без кеша совсем на чуть-чуть побыстрее)
        timer = System.currentTimeMillis();
        var clientInDB = dbServiceClient
                .getClient(35L)
                .orElseThrow(() -> new RuntimeException("Client not found, id: 35"));
        log.info("dbServiceClient clientInDB (id = 30): {}ms", System.currentTimeMillis() - timer);

        timer = System.currentTimeMillis();
        clientInDB = dbServiceClientWithCache
                .getClient(36L)
                .orElseThrow(() -> new RuntimeException("Client not found, id: 36"));
        log.info("dbServiceClientWithCache clientInDB (id = 31): {}ms", System.currentTimeMillis() - timer);
        Thread.sleep(10000L);

        // Удаление клиента (с кешем чуть-чуть побыстрее О_о)
        timer = System.currentTimeMillis();
        dbServiceClient.removeClient(clientUpdated);
        log.info("Delete client without cache: {}ms\n{}", System.currentTimeMillis() - timer, clientUpdated);

        timer = System.currentTimeMillis();
        dbServiceClientWithCache.removeClient(clientUpdatedWithCache);
        log.info("Delete client with cache: {}ms\n{}", System.currentTimeMillis() - timer, clientUpdatedWithCache);
        Thread.sleep(10000L);

        // Поиск и вывод в лог всех клиентов
        // (с кешем сильно быстрее не смотря на то, что клиенты далеко не все в кеше)
        log.info("All clients without cache:");
        timer = System.currentTimeMillis();
        dbServiceClient.findAll().forEach(cl -> log.info("client:{}", cl));
        log.info("Time spent on clients without caching: {}ms", System.currentTimeMillis() - timer);

        log.info("All clients with cache:");
        timer = System.currentTimeMillis();
        dbServiceClientWithCache.findAll().forEach(cl -> log.info("client:{}", cl));
        log.info("Time spent on clients with caching: {}ms", System.currentTimeMillis() - timer);
        Thread.sleep(20000L);

        // Проверка очистки кэша после просьбы к GC
        int sizeIteration = 300;
        for (int i = 1; i <= sizeIteration; i++) {
            dbServiceClientWithCache.saveClient(new Client("dbServiceWithCache" + i));

            if (i % 10 == 0)
                log.info("iteration = {}; cacheSize = {};", i, cache.getSizeCache());
            if (i % 25 == 0 || i == sizeIteration) {
                System.gc();
                log.info("Call System.gc(); iteration = {}; cacheSize = {};", i, cache.getSizeCache());
                Thread.sleep(1000L);
            }
        }
    }
}
