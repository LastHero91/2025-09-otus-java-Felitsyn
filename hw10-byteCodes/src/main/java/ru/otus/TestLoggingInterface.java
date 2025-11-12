package ru.otus;

public interface TestLoggingInterface {
    void calculation();
    @Log
    void calculation(int param);
    void calculation(int param, int param2);
    void calculation(int param1, int param2, String param3);
    void calculation(int param1, int param2, String param3, String param4);
}
