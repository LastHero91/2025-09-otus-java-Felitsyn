package ru.otus.crm.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.HwCache;
import ru.otus.HwListener;
import ru.otus.MyCache;
import ru.otus.crm.model.Client;
import ru.otus.crm.service.DBServiceClient;
import ru.otus.crm.service.DbServiceClientImpl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.*;

public class DBClientProxy {
    private static final Logger logger = LoggerFactory.getLogger(DBClientProxy.class);

    public static DBServiceClient createDBServiceClient(DbServiceClientImpl dbServiceClient,
                                                        HwCache<Long, Client> cache) {
        return (DBServiceClient) Proxy.newProxyInstance(DBClientProxy.class.getClassLoader(),
                new Class<?>[] {DBServiceClient.class},
                new CacheInvocationHandler(dbServiceClient, cache));
    }

    private static class CacheInvocationHandler implements InvocationHandler {
        private final DBServiceClient dbServiceClient;
        private final HwCache<Long, Client> cache;

        CacheInvocationHandler(DbServiceClientImpl dbServiceClient,
                               HwCache<Long, Client> cache) {
            this.dbServiceClient = dbServiceClient;
            this.cache = cache;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            return switch (method.getName()) {
                case "saveClient" -> saveWithCache(method, args);
                case "getClient" -> getWithCache(method, args);
                case "findAll" -> findAllWithCache(method, args);
                case "removeClient" -> removeWithCache(method, args);
                default -> method.invoke(dbServiceClient, args);
            };
        }

        @Override
        public String toString() {
            return "CacheInvocationHandler{" + "DBServiceClient=" + dbServiceClient + '}';
        }

        private Client saveWithCache(Method method, Object[] args) throws Throwable {
            if (args == null || args.length != 1 || !(args[0] instanceof Client))
                throw new IllegalArgumentException(String.format("%s", args));

            Client client = (Client) method.invoke(dbServiceClient, args);
            cache.put(client.getId(), client);

            return client;
        }

        private Optional<Client> getWithCache(Method method, Object[] args) throws Throwable {
            if (args == null || args.length != 1 || !(args[0] instanceof Long))
                throw new IllegalArgumentException(String.format("%s", args));

            Client cached = cache.get((Long) args[0]);
            if (cached != null)
                return Optional.of(cached);

            return (Optional<Client>) method.invoke(dbServiceClient, args);
        }

        private List<Client> findAllWithCache(Method method, Object[] args) throws Throwable {
            if (args != null)
                throw new IllegalArgumentException(String.format("%s", args));

            List<Client> allClientsInCache = cache.getAll();
            List<Client> allClientsInDB = (List<Client>) method.invoke(dbServiceClient, args);

            Set<Client> allUniqueClients = new HashSet<>(allClientsInCache);
            allUniqueClients.addAll(allClientsInDB);

            return new ArrayList<>(allUniqueClients);
        }

        private Client removeWithCache(Method method, Object[] args) throws Throwable {
            if (args == null || args.length != 1 || !(args[0] instanceof Client))
                throw new IllegalArgumentException(String.format("%s", args));

            Client client = (Client) args[0];
            cache.remove(client.getId());
            method.invoke(dbServiceClient, client);
            return client;
        }
    }
}