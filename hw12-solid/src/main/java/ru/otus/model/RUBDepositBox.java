package ru.otus.model;

import ru.otus.model.currency.Currency;
import ru.otus.model.currency.RUBDenomination;

public class RUBDepositBox implements DepositBox {
    private static final Currency CURRENCY = Currency.RUB;
    private int quantity100, quantity200, quantity500, quantity1000, quantity2000, quantity5000;

    @Override
    public Currency getCurrency() {
        return CURRENCY;
    }

    @Override
    public long getSum() {
        return quantity100*100 + quantity200*200 + quantity500*500 + quantity1000*1000 + quantity2000*2000 + quantity5000*5000;
    }

    public int upQuantity(RUBDenomination denomination) {
        int quantity = switch (denomination) {
            case RUB100 -> ++quantity100;
            case RUB200 -> ++quantity200;
            case RUB500 -> ++quantity500;
            case RUB1000 -> ++quantity1000;
            case RUB2000 -> ++quantity2000;
            case RUB5000 -> ++quantity5000;
        };
        return quantity;
    }

    public RUBDenomination downQuantity(RUBDenomination denomination) {
        switch (denomination) {
            case RUB100 -> --quantity100;
            case RUB200 -> --quantity200;
            case RUB500 -> --quantity500;
            case RUB1000 -> --quantity1000;
            case RUB2000 -> --quantity2000;
            case RUB5000 -> --quantity5000;
        }

        RUBDenomination rubDenomination = switch (denomination) {
            case RUB100 -> RUBDenomination.RUB100;
            case RUB200 -> RUBDenomination.RUB200;
            case RUB500 -> RUBDenomination.RUB500;
            case RUB1000 -> RUBDenomination.RUB1000;
            case RUB2000 -> RUBDenomination.RUB2000;
            case RUB5000 -> RUBDenomination.RUB5000;
        };

        return rubDenomination;
    }

    public int getQuantity100() {
        return quantity100;
    }

    public int getQuantity200() {
        return quantity200;
    }

    public int getQuantity500() {
        return quantity500;
    }

    public int getQuantity1000() {
        return quantity1000;
    }

    public int getQuantity2000() {
        return quantity2000;
    }

    public int getQuantity5000() {
        return quantity5000;
    }

    public void setQuantity100(int quantity100) {
        this.quantity100 = quantity100;
    }

    public void setQuantity200(int quantity200) {
        this.quantity200 = quantity200;
    }

    public void setQuantity500(int quantity500) {
        this.quantity500 = quantity500;
    }

    public void setQuantity1000(int quantity1000) {
        this.quantity1000 = quantity1000;
    }

    public void setQuantity2000(int quantity2000) {
        this.quantity2000 = quantity2000;
    }

    public void setQuantity5000(int quantity5000) {
        this.quantity5000 = quantity5000;
    }
}