package ru.otus.jdbc.mapper;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import ru.otus.core.repository.DataTemplate;
import ru.otus.core.repository.DataTemplateException;
import ru.otus.core.repository.executor.DbExecutor;
import ru.otus.crm.model.Client;

/** Сохраняет объект в базу, читает объект из базы */
@SuppressWarnings("java:S1068")
public class DataTemplateJdbc<T> implements DataTemplate<T> {

    private final DbExecutor dbExecutor;
    private final EntitySQLMetaData entitySQLMetaData;
    private final EntityClassMetaData<T> entityClassMetaData;

    public DataTemplateJdbc(DbExecutor dbExecutor,
                            EntitySQLMetaData entitySQLMetaData,
                            EntityClassMetaData<T> entityClassMetaData) {
        this.dbExecutor = dbExecutor;
        this.entitySQLMetaData = entitySQLMetaData;
        this.entityClassMetaData = entityClassMetaData;
    }

    @Override
    public Optional<T> findById(Connection connection, long id) {
        return dbExecutor.executeSelect(connection,
                entitySQLMetaData.getSelectByIdSql(),
                List.of(id),
                rs -> {
                    try {
                        if (rs.next()) {
                            ResultSetMetaData rsmd = rs.getMetaData();
                            Object[] constructorArguments = new Object[rsmd.getColumnCount()];

                            for (int i = 0; i < rsmd.getColumnCount(); i++) {
                                constructorArguments[i] = rs.getObject(i+1);
                            }

                            return entityClassMetaData
                                    .getConstructor()
                                    .newInstance(constructorArguments);
                        }

                        return null;
                    } catch (Exception e) {
                        throw new DataTemplateException(e);
                    }
                });
    }

    @Override
    public List<T> findAll(Connection connection) {
        return dbExecutor.executeSelect(connection,
                entitySQLMetaData.getSelectAllSql(),
                Collections.emptyList(),
                rs -> {
                    try {
                        List<T> listObj = new ArrayList<>();
                        while (rs.next()) {
                            ResultSetMetaData rsmd = rs.getMetaData();
                            Object[] constructorArguments = new Object[rsmd.getColumnCount()];

                            for (int i = 0; i < rsmd.getColumnCount(); i++) {
                                constructorArguments[i] = rs.getObject(i+1);
                            }

                            listObj.add(entityClassMetaData
                                    .getConstructor()
                                    .newInstance(constructorArguments));
                        }

                        return listObj;
                    } catch (Exception e) {
                        throw new DataTemplateException(e);
                    }
                }).orElseThrow();
    }

    @Override
    public long insert(Connection connection, T client) {
        try {
            Class<T> clazz = (Class<T>) client.getClass();

            List<String> fieldNames = entityClassMetaData.getFieldsWithoutId()
                    .stream()
                    .map(Field::getName).toList();

            List<Object> fieldValues = fieldNames
                    .stream()
                    .map(fieldName -> {
                        try {
                            Field field = clazz.getDeclaredField(fieldName);
                            field.setAccessible(true);
                            return field.get(client);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .collect(Collectors.toList());

            return dbExecutor.executeStatement(connection, entitySQLMetaData.getInsertSql(), fieldValues);
        } catch (Exception e) {
            throw new DataTemplateException(e);
        }
    }

    @Override
    public void update(Connection connection, T client) {
        try {
            Class<T> clazz = (Class<T>) client.getClass();

            List<String> fieldNames = entityClassMetaData.getFieldsWithoutId()
                    .stream()
                    .map(Field::getName)
                    .collect(Collectors.toList());
            fieldNames.add(clazz.getDeclaredField(entityClassMetaData.getIdField().getName()).getName());

            List<Object> fieldValues = fieldNames
                    .stream()
                    .map(fieldName -> {
                        try {
                            Field field = clazz.getDeclaredField(fieldName);
                            field.setAccessible(true);
                            return field.get(client);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .collect(Collectors.toList());

            dbExecutor.executeStatement(connection, entitySQLMetaData.getUpdateSql(), fieldValues);
        } catch (Exception e) {
            throw new DataTemplateException(e);
        }
    }
}
