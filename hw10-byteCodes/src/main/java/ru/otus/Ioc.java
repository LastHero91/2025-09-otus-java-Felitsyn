package ru.otus;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class Ioc {
    private static final Logger logger = LoggerFactory.getLogger(Ioc.class);

    private Ioc() {}

    public static TestLoggingInterface createTestLogging() {
        InvocationHandler handler = new HWInvocationHandler(new TestLogging());
        return (TestLoggingInterface)
                Proxy.newProxyInstance(Ioc.class.getClassLoader(), new Class<?>[] {TestLoggingInterface.class}, handler);
    }

    private static class HWInvocationHandler implements InvocationHandler {
        private final TestLoggingInterface testLoggingClass;
        private final Method[] classMethods;

        HWInvocationHandler(TestLogging testLoggingClass) {
            this.testLoggingClass = testLoggingClass;
            this.classMethods = testLoggingClass.getClass().getMethods();
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            // Типы аргументов метода у интерфейса
            var parameterTypesInterface = method.getParameterTypes();
            // Массив значений аргументов метода у интерфейса
            var argsStr = args != null
                    ? Arrays.stream(args)
                        .map(obj -> Objects.toString(obj, "null"))
                        .toArray(String[]::new)
                    : new String[]{""};

            // Обходим все методы экземпляра класса
            for (var objMethod : classMethods){
                // Типы аргументов метода экземпляра класса
                var parameterTypesClass = objMethod.getParameterTypes();
                // Проверяем:
                if (objMethod.getAnnotation(Log.class) != null &&                 // у метода объекта есть аннотация Log
                        objMethod.getName().equals(method.getName()) &&                         // имена методов объекта и интерфейса совпадают
                        Arrays.equals(parameterTypesClass, parameterTypesInterface)) {   // массивы аргументов методов объекта и интерфейса совпадает
                    logger.info("executed method: {}, param: {}",
                            method.getName(), String.join(", ",argsStr));    // логируем имя метода и перечисление аргументов метода через запятую

                    // Лог зачем мне методы объекта класса, а не аргумент method метода invoke
                    logger.info("Interface method annotation:{};",
                            method.getAnnotation(Log.class));
                    logger.info("Object method annotation:{};",
                            objMethod.getAnnotation(Log.class));
                    // Лог для удобства просмотра
                    logger.info("---------------------------------------------");
                }
            }

            // Вызываем "оригинальный" метод
            return method.invoke(testLoggingClass, args);
        }

        @Override
        public String toString() {
            return "HWInvocationHandler{" + "testLoggingClass=" + testLoggingClass + '}';
        }
    }
}