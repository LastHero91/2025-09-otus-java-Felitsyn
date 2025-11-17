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
                    methods.add(getMethodSignature(method));
                }
            }
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (methods.contains(getMethodSignature(method))) {
                var argsStr = args != null
                         ? Arrays.stream(args)
                        .map(obj -> Objects.toString(obj, "null"))
                        .toArray(String[]::new)
                        : new String[]{""};

                logger.info("executed method: {}, param: {}",
                        method.getName(), String.join(", ",argsStr));
                }

            return method.invoke(testLoggingClass, args);
        }

        @Override
        public String toString() {
            return "HWInvocationHandler{" + "testLoggingClass=" + testLoggingClass + '}';
        }

        private String getMethodSignature(Method method) {
            return method.getName() + Arrays.asList(method.getParameterTypes());
        }
    }
}