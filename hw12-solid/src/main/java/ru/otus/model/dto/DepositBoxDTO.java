package ru.otus.model.dto;

public interface DepositBoxDTO {
    BanknoteDTO getBanknote();

    int getTotalBanknotes();

    long getSum();

    int addBanknotes(int count);

    int takeBanknotes(int count);
}