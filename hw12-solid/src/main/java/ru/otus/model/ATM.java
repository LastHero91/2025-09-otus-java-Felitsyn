package ru.otus.model;

import ru.otus.model.currency.Currency;
import ru.otus.service.InitService;
import ru.otus.service.ProcessingService;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ATM {
    private static final Logger logger = LoggerFactory.getLogger(ATM.class);

    public ATM() {
        Map<Currency, DepositBox> depositBoxes = new HashMap();
        depositBoxes.put(Currency.RUB, new RUBDepositBox());
        depositBoxes.put(Currency.USD, new USDDepositBox());
        depositBoxes.put(Currency.EUR, new EURDepositBox());
        logger.info("Создан банкомант с ячейками для валюты: {}", depositBoxes.keySet());

        InitService.printInitMessage();
        InitService.initDenomination(depositBoxes);

        ProcessingService.start(depositBoxes);

        logger.info("Банкомант остановлен");
    }
}
