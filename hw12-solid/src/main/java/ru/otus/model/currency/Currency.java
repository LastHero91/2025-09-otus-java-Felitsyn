package ru.otus.model.currency;

public enum Currency {
    RUB("RUB", 810),
    USD("USD", 840),
    EUR("EUR", 978);

    private final String letterCode;
    private final int digitalCode;

    Currency(String letterCode, int digitalCode) {
        this.letterCode = letterCode;
        this.digitalCode = digitalCode;
    }

    public String getLetterCode() {
        return letterCode;
    }

    public int getDigitalCode() {
        return digitalCode;
    }
}
