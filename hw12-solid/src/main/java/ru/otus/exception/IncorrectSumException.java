package ru.otus.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.model.currency.Currency;

public class IncorrectSumException extends Exception {
    private static final Logger logger = LoggerFactory.getLogger(IncorrectSumException.class);

    public IncorrectSumException(Currency currency, int sum) {
        logger.error("Введена не корректная сумма. Возвращено: {} {}", sum, currency.getLetterCode());
    }
}
