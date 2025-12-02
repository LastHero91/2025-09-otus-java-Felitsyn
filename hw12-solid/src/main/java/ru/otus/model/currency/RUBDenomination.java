package ru.otus.model.currency;

public enum RUBDenomination implements Denomination {
    RUB100(100),
    RUB200(200),
    RUB500(500),
    RUB1000(1000),
    RUB2000(2000),
    RUB5000(5000);

    public static Denomination getMaxDenomination() {
        return RUB5000;
    }

    private final int digitDenomination;
    private final Currency currency = Currency.RUB;

    RUBDenomination (int digitDenomination){
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
