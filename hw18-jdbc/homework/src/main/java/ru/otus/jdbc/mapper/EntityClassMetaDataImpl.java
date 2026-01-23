package ru.otus.jdbc.mapper;

import ru.otus.crm.model.annotation.Id;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

public class EntityClassMetaDataImpl<T> implements EntityClassMetaData<T> {
    private final Class<T> clazz;

    public EntityClassMetaDataImpl(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public String getName() {
        return clazz.getSimpleName();
    }

    @Override
    public Constructor<T> getConstructor() {
        try {
            Class[] fieldsClazz = getAllFields().stream()
                    .map(Field::getType)
                    .toArray(Class[]::new);
            return clazz.getConstructor(fieldsClazz);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Field getIdField() {
        return getAllFields()
                .stream()
                .filter(f -> f.isAnnotationPresent(Id.class))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Поле с аннотацией ID не найдено"));
    }

    @Override
    public List<Field> getAllFields() {
        return List.of(clazz.getDeclaredFields());
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        return getAllFields()
                .stream()
                .filter(f -> !f.equals(getIdField()))
                .collect(Collectors.toList());
    }
}
