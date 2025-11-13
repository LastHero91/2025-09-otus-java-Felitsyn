package ru.otus;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.*;

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
        private final List<String> methods = new ArrayList<>();

        HWInvocationHandler(TestLogging testLoggingClass) {
            this.testLoggingClass = testLoggingClass;

            for (var method : testLoggingClass.getClass().getMethods()) {
                if (method.isAnnotationPresent(Log.class)) {
                    methods.add(method.getName() + Arrays.asList(method.getParameterTypes()));
                }
            }
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            var argsStr = args != null
                    ? Arrays.stream(args)
                        .map(obj -> Objects.toString(obj, "null"))
                        .toArray(String[]::new)
                    : new String[]{""};

            if (methods.contains(method.getName() + Arrays.asList(method.getParameterTypes()))
                    || method.isAnnotationPresent(Log.class)) {
                logger.info("executed method: {}, param: {}",
                        method.getName(), String.join(", ",argsStr));
                }

            return method.invoke(testLoggingClass, args);
        }

        @Override
        public String toString() {
            return "HWInvocationHandler{" + "testLoggingClass=" + testLoggingClass + '}';
        }
    }
}