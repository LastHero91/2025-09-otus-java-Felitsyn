package ru.otus.service;

import static ru.otus.model.currency.EURDenomination.*;
import static ru.otus.model.currency.RUBDenomination.*;
import static ru.otus.model.currency.USDDenomination.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.model.*;
import ru.otus.model.currency.Currency;

import java.util.Map;
import java.util.Scanner;

public class InitService {
    private static final Logger logger = LoggerFactory.getLogger(ATM.class);
    public static void initDenomination(Map<Currency, DepositBox> depositBoxes) {
        logger.info("Запущен процесс загрузки банкнот в банкомат");
        for (Map.Entry<Currency, DepositBox> depositBox : depositBoxes.entrySet()) {
            InitService.printCurrencyExampleInit(depositBox.getKey());

            Scanner scanner = new Scanner(System.in);
            String currencyLine = scanner.nextLine();
            logger.info("Была введена строка с количеством для загрузки банкнот в банкомат: {} {}", currencyLine, depositBox.getKey());

            switch (depositBox.getKey()) {
                case RUB -> RUBInitDenomination(currencyLine, (RUBDepositBox) depositBox.getValue());
                case USD -> USDInitDenomination(currencyLine, (USDDepositBox) depositBox.getValue());
                case EUR -> EURInitDenomination(currencyLine, (EURDepositBox) depositBox.getValue());
            }
        }
        logger.info("Окончен процесс загрузки банкнот в банкомат");
    }

    public static void printInitMessage() {
        System.out.println("""
                \nПроизведем загрузку банкомата купюрами при запуске банкомата.
                Для каждой валюты необходимо задать первончальное количество купюр.""");
        logger.info("Выведено первоначальное сообщение о загрузке банкнот в банкомат");
    }

    public static void printCurrencyExampleInit(Currency currency) {
        logger.info("Запущен процесс детального описания примера ввода купюр по валюте: {}", currency.getLetterCode());
        System.out.printf("Введите через ; количество и суммы для заполнения ячеек валюты %s в формате: n-штук - s-номинал.\n", currency.getLetterCode());
        switch (currency) {
            case RUB -> System.out.printf("Пример ввода для валюты %s: 50 - 100; 20 - 200; 1000 - 500; 2000 - 1000; 30 - 2000; 1000 - 5000%n", currency.getLetterCode());
            case USD -> System.out.printf("Пример ввода для валюты %s: 50 - 1; 20 - 2; 100 - 5; 200 - 10; 30 - 20; 200 - 50; 500 - 100%n", currency.getLetterCode());
            case EUR -> System.out.printf("Пример ввода для валюты %s: 50 - 5; 20 - 10; 100 - 20; 200 - 50; 30 - 100; 200 - 200; 500 - 500%n", currency.getLetterCode());
        }
        logger.info("Окончен процесс детального описания примера ввода купюр по валюте: {}", currency.getLetterCode());
    }

    public static void RUBInitDenomination(String currencyLine, RUBDepositBox rubDepositBox) {
        logger.info("Запущен процесс загрузки вложения купюр в {} ячейку", rubDepositBox.getCurrency().getLetterCode());
        int i = 0;
        int count = 0;
        for (var str : currencyLine.split("\\W+")) {
            int digitPart = Integer.parseInt(str.trim());

            if (i % 2 == 0)
                count = digitPart;
            else {
                if (digitPart == RUB100.getDigitDenomination())
                    rubDepositBox.setQuantity100(count);
                else if (digitPart == RUB200.getDigitDenomination())
                    rubDepositBox.setQuantity200(count);
                else if (digitPart == RUB500.getDigitDenomination())
                    rubDepositBox.setQuantity500(count);
                else if (digitPart == RUB1000.getDigitDenomination())
                    rubDepositBox.setQuantity1000(count);
                else if (digitPart == RUB2000.getDigitDenomination())
                    rubDepositBox.setQuantity2000(count);
                else if (digitPart == RUB5000.getDigitDenomination())
                    rubDepositBox.setQuantity5000(count);

                logger.info("Внесено {} купюр наминалом {}", count, digitPart);
            }
            i++;
        }
        logger.info("Окончен процесс загрузки вложения купюр в {} ячейку", rubDepositBox.getCurrency().getLetterCode());
    }

    public static void USDInitDenomination(String currencyLine, USDDepositBox usdDepositBox) {
        logger.info("Запущен процесс загрузки вложения купюр в {} ячейку", usdDepositBox.getCurrency().getLetterCode());
        int i = 0;
        int count = 0;
        for (var str : currencyLine.split("\\W+")) {
            int digitPart = Integer.parseInt(str.trim());

            if (i % 2 == 0)
                count = digitPart;
            else {
                if (digitPart == USD1.getDigitDenomination())
                    usdDepositBox.setQuantity1(count);
                else if (digitPart == USD2.getDigitDenomination())
                    usdDepositBox.setQuantity2(count);
                else if (digitPart == USD5.getDigitDenomination())
                    usdDepositBox.setQuantity5(count);
                else if (digitPart == USD10.getDigitDenomination())
                    usdDepositBox.setQuantity10(count);
                else if (digitPart == USD20.getDigitDenomination())
                    usdDepositBox.setQuantity20(count);
                else if (digitPart == USD50.getDigitDenomination())
                    usdDepositBox.setQuantity50(count);
                else if (digitPart == USD100.getDigitDenomination())
                    usdDepositBox.setQuantity100(count);

                logger.info("Внесено {} купюр наминалом {}", count, digitPart);
            }
            i++;
        }
        logger.info("Окончен процесс загрузки вложения купюр в {} ячейку", usdDepositBox.getCurrency().getLetterCode());
    }

    public static void EURInitDenomination(String currencyLine, EURDepositBox eurDepositBox) {
        logger.info("Запущен процесс загрузки вложения купюр в {} ячейку", eurDepositBox.getCurrency().getLetterCode());
        int i = 0;
        int count = 0;
        for (var str : currencyLine.split("\\W+")) {
            int digitPart = Integer.parseInt(str.trim());

            if (i % 2 == 0)
                count = digitPart;
            else {
                if (digitPart == EUR5.getDigitDenomination())
                    eurDepositBox.setQuantity5(count);
                else if (digitPart == EUR10.getDigitDenomination())
                    eurDepositBox.setQuantity10(count);
                else if (digitPart == EUR20.getDigitDenomination())
                    eurDepositBox.setQuantity20(count);
                else if (digitPart == EUR50.getDigitDenomination())
                    eurDepositBox.setQuantity50(count);
                else if (digitPart == EUR100.getDigitDenomination())
                    eurDepositBox.setQuantity100(count);
                else if (digitPart == EUR200.getDigitDenomination())
                    eurDepositBox.setQuantity200(count);
                else if (digitPart == EUR500.getDigitDenomination())
                    eurDepositBox.setQuantity500(count);

                logger.info("Внесено {} купюр наминалом {}", count, digitPart);
            }
            i++;
        }
        logger.info("Окончен процесс загрузки вложения купюр в {} ячейку", eurDepositBox.getCurrency().getLetterCode());
    }
}
