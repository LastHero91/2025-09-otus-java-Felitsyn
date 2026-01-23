package ru.otus.jdbc.mapper;

import java.lang.reflect.Field;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class EntitySQLMetaDataImpl<T> implements EntitySQLMetaData {

    private final EntityClassMetaData<T> entityClassMetaData;

    public EntitySQLMetaDataImpl(EntityClassMetaData<T> entityClassMetaData) {
        this.entityClassMetaData = entityClassMetaData;
    }

    @Override
    public String getSelectAllSql() {
        return String.format("select * from %s",
                        entityClassMetaData.getName())
                .toLowerCase();
    }

    @Override
    public String getSelectByIdSql() {
        return String.format("select * from %s where %s = ?",
                entityClassMetaData.getName(), entityClassMetaData.getIdField().getName())
                .toLowerCase();
    }

    @Override
    public String getInsertSql() {
        var fieldNames = entityClassMetaData.getFieldsWithoutId()
                .stream()
                .map(Field::getName)
                .collect(Collectors.toList());
        var columnStr = String.join(", ", fieldNames);
        var valueStr = String.join(", ",
                IntStream.range(0, fieldNames.size())
                        .mapToObj(i -> "?")
                        .collect(Collectors.toList()));

        return String.format("insert into %s (%s) values (%s)",
                        entityClassMetaData.getName(), columnStr, valueStr)
                .toLowerCase();
    }

    @Override
    public String getUpdateSql() {
        var setStr = String.join(", ",
                entityClassMetaData.getFieldsWithoutId()
                        .stream()
                        .map(field -> String.format("%s = ?", field.getName()))
                        .collect(Collectors.toList()));

        return String.format("update %s set %s where %s = ?",
                entityClassMetaData.getName(), setStr, entityClassMetaData.getIdField().getName())
                .toLowerCase();
    }
}
