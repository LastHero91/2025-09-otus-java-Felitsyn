package ru.otus.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DenominationDeficiencyException extends Exception {
    private static final Logger logger = LoggerFactory.getLogger(DenominationDeficiencyException.class);

    public DenominationDeficiencyException(int sum) {
        logger.error("Суммы {} нет в банкомате", sum);
    }
}
