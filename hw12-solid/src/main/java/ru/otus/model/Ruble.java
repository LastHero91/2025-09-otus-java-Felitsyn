package ru.otus.model;

import java.util.Objects;
import ru.otus.model.dto.BanknoteDTO;

public class Ruble implements BanknoteDTO {
    private static final String CURRENCY = "RUB";
    private final int amount;

    public Ruble(int amount) {
        this.amount = amount;
    }

    @Override
    public int getAmount() {
        return amount;
    }

    @Override
    public String getCurrency() {
        return CURRENCY;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ruble ruble = (Ruble) o;
        return amount == ruble.amount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount);
    }

    @Override
    public String toString() {
        return String.format("Ruble{amount = %s}", amount);
    }
}