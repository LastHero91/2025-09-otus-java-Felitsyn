package ru.otus.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.model.currency.Denomination;

public class IncorrectDenominationException extends Exception {
    private static final Logger logger = LoggerFactory.getLogger(IncorrectDenominationException.class);

    public IncorrectDenominationException(Denomination denomination) {
        logger.error("Купюра не принята банкоматом. Обнаружена не корректная купюра: {}", denomination);
    }
}
