package ru.otus.model;

import ru.otus.model.dto.BanknoteDTO;
import ru.otus.model.dto.DepositBoxDTO;

public class RubleDepositBox implements DepositBoxDTO {
    private final Ruble ruble;
    private int totalBanknotes = 0;

    public RubleDepositBox(Ruble ruble) {
        this.ruble = ruble;
    }

    @Override
    public BanknoteDTO getBanknote() {
        return ruble;
    }

    @Override
    public int getTotalBanknotes() {
        return totalBanknotes;
    }

    @Override
    public long getSum() {
        return (long) ruble.getAmount() * totalBanknotes;
    }

    @Override
    public int addBanknotes(int count) {
        int unacceptedCount = 0;

        if (count <= 0)
            return count;

        while (true) {
            if (count <= Integer.MAX_VALUE - this.totalBanknotes) {
                this.totalBanknotes += count;
                return unacceptedCount;
            }
            count--;
            unacceptedCount++;
        }
    }

    @Override
    public int takeBanknotes(int count) {
        if (count <= 0)
            return count;

        while (true) {
            if (this.totalBanknotes >= count) {
                this.totalBanknotes -= count;
                return count;
            }
            count--;
        }
    }

    @Override
    public String toString() {
        return String.format("RubleDepositBox{ruble = %s; totalBanknotes = %s}", ruble, totalBanknotes);
    }
}