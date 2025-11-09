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

        HWInvocationHandler(TestLogging testLoggingClass) {
            this.testLoggingClass = testLoggingClass;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            var classMethods = testLoggingClass.getClass().getMethods();
            var parameterTypesInterface = method.getParameterTypes();

            String[] argsStr = args != null ? Arrays.stream(args)
                    .map(obj -> Objects.toString(obj, ""))
                    .toArray(String[]::new) : new String[]{""};

            for (var classMethod : classMethods){
                var parameterTypesClass = classMethod.getParameterTypes();

                if (classMethod.getAnnotation(Log.class) != null &&
                        Arrays.equals(parameterTypesClass, parameterTypesInterface)) {
                    logger.info("executed method: {}, param: {}", classMethod.getName(), String.join(", ",argsStr));
                }
            }

            return method.invoke(testLoggingClass, args);
        }

        @Override
        public String toString() {
            return "HWInvocationHandler{" + "testLoggingClass=" + testLoggingClass + '}';
        }
    }
}