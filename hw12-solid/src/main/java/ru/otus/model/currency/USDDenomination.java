package ru.otus.model.currency;

public enum USDDenomination implements Denomination {
    USD1(1),
    USD2(2),
    USD5(5),
    USD10(10),
    USD20(20),
    USD50(50),
    USD100(100);

    public static Denomination getMaxDenomination() {
        return USD100;
    }

    private final int digitDenomination;
    private final Currency currency = Currency.USD;

    USDDenomination(int digitDenomination){
        this.digitDenomination = digitDenomination;
    }

    @Override
    public int getDigitDenomination() {
        return digitDenomination;
    }

    @Override
    public Currency getCurrency() {
        return currency;
    }
}
