package ru.otus.test;

import ru.otus.test.annotations.After;
import ru.otus.test.annotations.DisplayName;
import ru.otus.test.annotations.Test;
import ru.otus.test.annotations.Before;


@DisplayName("Тестирование сравнения строки")
public class TestString {
    private String valuePrivate;

    @Before
    private void initValuePrivate(){
        valuePrivate = "test";
    }

    @Test
    @DisplayName("Сравнение строк-литералов оператором ==")
    private void testHeapSpaceString() {
        String expectedString = "test";
        if (valuePrivate!=expectedString)
            throw new RuntimeException();
    }

    @Test
    @DisplayName("Сравнение строк методом equals")
    private void testEqualsString() {
        String expectedString = new String("test");
        if (!valuePrivate.equals(expectedString))
            throw new RuntimeException();
    }

    @Test
    @DisplayName("Сравнение строк, созданных через конструктор, оператором ==")
    private void testNotHeapSpaceString(){
        String expectedString = new String("test");
        if (valuePrivate!=expectedString)
            throw new RuntimeException();
    }

    @After
    private void setNullValuePrivate(){
        valuePrivate = null;
    }
}