package ru.otus.model.dto;

public interface BanknoteDTO {
    int getAmount();
    String getCurrency();
    int hashCode();
    boolean equals(Object o);
    String toString();
}
