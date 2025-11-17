package ru.otus;

class TestLogging implements TestLoggingInterface {
    public void calculation() {}
    @Log
    public void calculation(int param) {}
    public void calculation(int param, int param2) {}
    @Log
    public void calculation(int param1, int param2, String param3) {}
    public void calculation(int param1, int param2, String param3, String param4) {}
}
