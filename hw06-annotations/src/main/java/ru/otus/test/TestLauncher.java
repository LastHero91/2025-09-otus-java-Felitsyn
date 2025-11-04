package ru.otus.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.test.annotations.After;
import ru.otus.test.annotations.Before;
import ru.otus.test.annotations.DisplayName;
import ru.otus.test.annotations.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestLauncher {
    private static final Logger logger = LoggerFactory.getLogger(TestLauncher.class);

    public static void start(String className) {
        Map<String, Object> mapState = new HashMap<>();
        mapState.put("className", className);

        TestLauncher.init(mapState);
        TestLauncher.run(mapState);
    }

    private static void init(Map<String, Object> mapState) {
        Class<?> clazz;
        Constructor<?> constructor;
        List<Method> BEFORE_METHODS = new ArrayList<>();
        List<Method> TEST_METHODS = new ArrayList<>();
        List<Method> AFTER_METHODS = new ArrayList<>();

        try {
            clazz = Class.forName(String.valueOf(mapState.get("className")));
            constructor = clazz.getConstructor();

            for (var method : clazz.getDeclaredMethods()) {
                for (var annotation : method.getAnnotations()) {
                    if (annotation.annotationType().equals(Before.class))
                        BEFORE_METHODS.add(method);
                    if (annotation.annotationType().equals(Test.class))
                        TEST_METHODS.add(method);
                    if (annotation.annotationType().equals(After.class))
                        AFTER_METHODS.add(method);
                }
            }

            mapState.put("classObject", clazz);
            mapState.put("constructor", constructor);
            mapState.put("BEFORE_METHODS", BEFORE_METHODS);
            mapState.put("TEST_METHODS", TEST_METHODS);
            mapState.put("AFTER_METHODS", AFTER_METHODS);
        } catch (Exception e) {
            logger.error("Ошибка при инициализации локальных переменных:");
            e.printStackTrace();
        }
    }

    private static void run(Map<String, Object> mapState) {
        int test = 0, successfulTests = 0, failedTests = 0;
        Exception exception = null;

        Class<?> clazz = (Class<?>) mapState.get("classObject");
        Constructor<?> constructor = (Constructor<?>) mapState.get("constructor");
        List<Method> BEFORE_METHODS = (List<Method>) mapState.get("BEFORE_METHODS");
        List<Method> TEST_METHODS = (List<Method>) mapState.get("TEST_METHODS");
        List<Method> AFTER_METHODS = (List<Method>) mapState.get("AFTER_METHODS");

        mapState.put("successfulTests", successfulTests);
        mapState.put("failedTests", failedTests);
        mapState.put("exception", null);
        try {
            logger.info("{}:", clazz.getAnnotation(DisplayName.class).value());
            for (var methodTest : TEST_METHODS) {
                test++;
                mapState.put("object", constructor.newInstance());

                callMethods(BEFORE_METHODS, mapState);
                if (mapState.get("exception") == null) {
                    callTestMethod(methodTest, mapState);
                }
                callMethods(AFTER_METHODS, mapState);

                exception = (Exception) mapState.get("exception");
                if (exception != null) {
                    exception.printStackTrace();
                    Thread.sleep(500);
                }
            }
        } catch (Exception e) {
            logger.error("Ошибка при обработке методов:");
            e.printStackTrace();
        }
        logger.info("Всего тестов произведено: {}, успешеных: {}, не успешных: {}", test, successfulTests, failedTests);
    }

    private static void callMethods(List<Method> methodsList, Map<String, Object> mapState) {
        for (var methodBefore : methodsList) {
            try {
                methodBefore.setAccessible(true);
                methodBefore.invoke(mapState.get("object"));
            } catch (Exception e) {
                mapState.put("exception", e);
            }
        }
    }

    private static void callTestMethod(Method methodTest, Map<String, Object> mapState) {
        int successfulTests = (int) mapState.get("successfulTests");
        int failedTests = (int) mapState.get("failedTests");

        try {
            methodTest.setAccessible(true);
            methodTest.invoke(mapState.get("object"));
            successfulTests++;
            logger.info("Тест пройден: \"{}\"", methodTest.getAnnotation(DisplayName.class).value());
        } catch (Exception e) {
            failedTests++;
            logger.error("Тест не пройден: \"{}\"", methodTest.getAnnotation(DisplayName.class).value());
        }

        mapState.put("successfulTests", successfulTests);
        mapState.put("failedTests", failedTests);
    }
}
