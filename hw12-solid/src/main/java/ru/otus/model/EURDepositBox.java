package ru.otus.model;

import ru.otus.model.currency.Currency;
import ru.otus.model.currency.EURDenomination;

public class EURDepositBox implements DepositBox {
    private static final Currency CURRENCY = Currency.EUR;
    private int quantity5, quantity10, quantity20, quantity50, quantity100, quantity200, quantity500;

    @Override
    public Currency getCurrency() {
        return CURRENCY;
    }

    @Override
    public long getSum() {
        return quantity5*5 + quantity10*10 + quantity20*20 + quantity50*50 + quantity100*100 + quantity200*200 + quantity500*500;
    }

    public int upQuantity(EURDenomination denomination) {
        int quantity = switch (denomination) {
            case EUR5 -> ++quantity5;
            case EUR10 -> ++quantity10;
            case EUR20 -> ++quantity20;
            case EUR50 -> ++quantity50;
            case EUR100 -> ++quantity100;
            case EUR200 -> ++quantity200;
            case EUR500 -> ++quantity500;
        };
        return quantity;
    }

    public EURDenomination downQuantity(EURDenomination denomination) {
        switch (denomination) {
            case EUR5 -> --quantity5;
            case EUR10 -> --quantity10;
            case EUR20 -> --quantity20;
            case EUR50 -> --quantity50;
            case EUR100 -> --quantity100;
            case EUR200 -> --quantity200;
            case EUR500 -> --quantity500;
        }

        EURDenomination eurDenomination = switch (denomination) {
            case EUR5 -> EURDenomination.EUR5;
            case EUR10 -> EURDenomination.EUR10;
            case EUR20 -> EURDenomination.EUR20;
            case EUR50 -> EURDenomination.EUR50;
            case EUR100 -> EURDenomination.EUR100;
            case EUR200 -> EURDenomination.EUR200;
            case EUR500 -> EURDenomination.EUR500;
        };
        return eurDenomination;
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

    public int getQuantity200() {
        return quantity200;
    }

    public int getQuantity500() {
        return quantity500;
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

    public void setQuantity200(int quantity200) {
        this.quantity200 = quantity200;
    }

    public void setQuantity500(int quantity500) {
        this.quantity500 = quantity500;
    }
}