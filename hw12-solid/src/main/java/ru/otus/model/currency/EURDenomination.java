package ru.otus.model.currency;

public enum EURDenomination implements Denomination {
    EUR5(5),
    EUR10(10),
    EUR20(20),
    EUR50(50),
    EUR100(100),
    EUR200(200),
    EUR500(500);

    public static Denomination getMaxDenomination() {
        return EUR500;
    }

    private final int digitDenomination;
    private final Currency currency = Currency.EUR;

    EURDenomination(int digitDenomination){
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
