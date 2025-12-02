package ru.otus.model;

import ru.otus.model.currency.Currency;

public interface DepositBox {
    Currency getCurrency();
    long getSum();
}