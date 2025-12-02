package ru.otus.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.model.currency.Denomination;

public class DenominationOverflowException extends Exception {
    private static final Logger logger = LoggerFactory.getLogger(DenominationOverflowException.class);

    public DenominationOverflowException(Denomination denomination) {
        logger.error("Купюра не принята банкоматом: Ячейка переполнена для вида купюр - {}{}.",
                denomination.getCurrency().getLetterCode(),
                denomination.getDigitDenomination());
    }
}