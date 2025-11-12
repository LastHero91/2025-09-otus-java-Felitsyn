package ru.otus;

public class Demo {
    public static void main(String[] args) {
        TestLoggingInterface testLogging = Ioc.createTestLogging();
        testLogging.calculation();
        testLogging.calculation(6);
        testLogging.calculation(7, 7);
        testLogging.calculation(9, 9, "tstStr");
        testLogging.calculation(9, 9, "tstStr", null);
    }
}