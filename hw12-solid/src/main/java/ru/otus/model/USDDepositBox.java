package ru.otus.model;

import ru.otus.model.currency.Currency;
import ru.otus.model.currency.USDDenomination;

public class USDDepositBox implements DepositBox{
    private static final Currency CURRENCY = Currency.USD;
    private int quantity1, quantity2, quantity5, quantity10, quantity20, quantity50, quantity100;

    @Override
    public Currency getCurrency() {
        return CURRENCY;
    }

    @Override
    public long getSum() {
        return quantity1 + quantity2*2 + quantity5*5 + quantity10*10 + quantity20*20 + quantity50*50 + quantity100*100;
    }

    public int upQuantity(USDDenomination denomination) {
        int quantity = switch (denomination) {
            case USD1 -> ++quantity1;
            case USD2 -> ++quantity2;
            case USD5 -> ++quantity5;
            case USD10 -> ++quantity10;
            case USD20 -> ++quantity20;
            case USD50 -> ++quantity50;
            case USD100 -> ++quantity100;
        };
        return quantity;
    }

    public USDDenomination downQuantity(USDDenomination denomination) {
        switch (denomination) {
            case USD1 -> --quantity1;
            case USD2 -> --quantity2;
            case USD5 -> --quantity5;
            case USD10 -> --quantity10;
            case USD20 -> --quantity20;
            case USD50 -> --quantity50;
            case USD100 -> --quantity100;
        }

        USDDenomination usdDenomination = switch (denomination) {
            case USD1 -> USDDenomination.USD1;
            case USD2 -> USDDenomination.USD2;
            case USD5 -> USDDenomination.USD5;
            case USD10 -> USDDenomination.USD10;
            case USD20 -> USDDenomination.USD20;
            case USD50 -> USDDenomination.USD50;
            case USD100 -> USDDenomination.USD100;
        };
        return usdDenomination;
    }

    public int getQuantity1() {
        return quantity1;
    }

    public int getQuantity2() {
        return quantity2;
    }

    public int getQuantity5() {
        return quantity5;
    }

    public int getQuantity10() {
        return quantity10;
    }

    public int getQuantity20() {
        return quantity20;
    }

    public int getQuantity50() {
        return quantity50;
    }

    public int getQuantity100() {
        return quantity100;
    }

    public void setQuantity1(int quantity1) {
        this.quantity1 = quantity1;
    }

    public void setQuantity2(int quantity2) {
        this.quantity2 = quantity2;
    }

    public void setQuantity5(int quantity5) {
        this.quantity5 = quantity5;
    }

    public void setQuantity10(int quantity10) {
        this.quantity10 = quantity10;
    }

    public void setQuantity20(int quantity20) {
        this.quantity20 = quantity20;
    }

    public void setQuantity50(int quantity50) {
        this.quantity50 = quantity50;
    }

    public void setQuantity100(int quantity100) {
        this.quantity100 = quantity100;
    }
}