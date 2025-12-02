package ru.otus.service;

import static ru.otus.model.currency.Currency.*;
import static ru.otus.model.currency.EURDenomination.*;
import static ru.otus.model.currency.EURDenomination.EUR500;
import static ru.otus.model.currency.RUBDenomination.*;
import static ru.otus.model.currency.RUBDenomination.RUB5000;
import static ru.otus.model.currency.USDDenomination.*;
import static ru.otus.model.currency.USDDenomination.USD100;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.exception.DenominationOverflowException;
import ru.otus.exception.IncorrectDenominationException;
import ru.otus.model.DepositBox;
import ru.otus.model.EURDepositBox;
import ru.otus.model.RUBDepositBox;
import ru.otus.model.USDDepositBox;
import ru.otus.model.currency.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class TakeDenominationsService {
    private static final Logger logger = LoggerFactory.getLogger(TakeDenominationsService.class);

    public static void printAcceptedDenominations(int acceptedDenomination) {
        logger.info("Запущен процесс вывода сообщения о принятых банкнот в банкомат");
        System.out.printf("Итого принято средств: %d\n", acceptedDenomination);
        logger.info("Окончен процесс вывода сообщения о принятых банкнот в банкомат");
    }

    public static List<Denomination> clarifyTakeDenomination() {
        logger.info("Запущен процесс выбора валюты для пополнения купюр");

        while (true) {
            System.out.printf("Введите код валюты вносимых средств: %s %s %s\n",
                    RUB.getLetterCode(), USD.getLetterCode(), EUR.getLetterCode());

            Scanner scanner = new Scanner(System.in);
            String currencyStr = scanner.nextLine();
            if (RUB.getLetterCode().equals(currencyStr)) {
                InitService.printCurrencyExampleInit(RUB);
                logger.info("Окончен процесс выбора валюты для пополнения купюр. Выбрана валюта: {}", currencyStr);
                return RUBTakeDenomination();
            }
            else if (USD.getLetterCode().equals(currencyStr)) {
                InitService.printCurrencyExampleInit(USD);
                logger.info("Окончен процесс выбора валюты для пополнения купюр. Выбрана валюта: {}", currencyStr);
                return USDTakeDenomination();
            }
            else if (EUR.getLetterCode().equals(currencyStr)) {
                InitService.printCurrencyExampleInit(EUR);
                logger.info("Окончен процесс выбора валюты для пополнения купюр. Выбрана валюта: {}", currencyStr);
                return EURTakeDenomination();
            }
            else
                continue;
        }
    }

    private static List<Denomination> RUBTakeDenomination() {
        logger.info("Запущен процесс формирования списка внесенных купюр в рублях");

        List<Denomination> denominations = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        String denominationString = scanner.nextLine();

        logger.info("Клиент внес список купюр в рублях: {}", denominationString);

        int i = 0;
        int count = 0;
        for (var str : denominationString.split("\\W+")) {
            int digitPart = Integer.parseInt(str.trim());

            if (i % 2 == 0)
                count = digitPart;
            else {
                if (digitPart == RUB100.getDigitDenomination())
                    while (count != 0) {
                        denominations.add(RUB100);
                        count--;

                        logger.info("В список купюр добавлен номинал: {} {}", RUB100.getDigitDenomination(), RUB100.getCurrency());
                    }
                else if (digitPart == RUB200.getDigitDenomination())
                    while (count != 0) {
                        denominations.add(RUB200);
                        count--;

                        logger.info("В список купюр добавлен номинал: {} {}", RUB200.getDigitDenomination(), RUB200.getCurrency());
                    }
                else if (digitPart == RUB500.getDigitDenomination())
                    while (count != 0) {
                        denominations.add(RUB500);
                        count--;

                        logger.info("В список купюр добавлен номинал: {} {}", RUB500.getDigitDenomination(), RUB500.getCurrency());
                    }
                else if (digitPart == RUB1000.getDigitDenomination())
                    while (count != 0) {
                        denominations.add(RUB1000);
                        count--;

                        logger.info("В список купюр добавлен номинал: {} {}", RUB1000.getDigitDenomination(), RUB1000.getCurrency());
                    }
                else if (digitPart == RUB2000.getDigitDenomination())
                    while (count != 0) {
                        denominations.add(RUB2000);
                        count--;

                        logger.info("В список купюр добавлен номинал: {} {}", RUB2000.getDigitDenomination(), RUB2000.getCurrency());
                    }
                else if (digitPart == RUB5000.getDigitDenomination())
                    while (count != 0) {
                        denominations.add(RUB5000);
                        count--;

                        logger.info("В список купюр добавлен номинал: {} {}", RUB5000.getDigitDenomination(), RUB5000.getCurrency());
                    }
            }
            i++;
        }

        logger.info("Окончен процесс формирования списка внесенных купюр в рублях");
        return denominations;
    }

    private static List<Denomination> USDTakeDenomination() {
        logger.info("Запущен процесс формирования списка внесенных купюр в долларах");

        List<Denomination> denominations = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        String denominationString = scanner.nextLine();

        logger.info("Клиент внес список купюр в долларах: {}", denominationString);

        int i = 0;
        int count = 0;
        for (var str : denominationString.split("\\W+")) {
            int digitPart = Integer.parseInt(str.trim());

            if (i % 2 == 0)
                count = digitPart;
            else {
                if (digitPart == USD1.getDigitDenomination())
                    while (count != 0) {
                        denominations.add(USD1);
                        count--;

                        logger.info("В список купюр добавлен номинал: {} {}", USD1.getDigitDenomination(), USD1.getCurrency());
                    }
                else if (digitPart == USD2.getDigitDenomination())
                    while (count != 0) {
                        denominations.add(USD2);
                        count--;

                        logger.info("В список купюр добавлен номинал: {} {}", USD2.getDigitDenomination(), USD2.getCurrency());
                    }
                else if (digitPart == USD5.getDigitDenomination())
                    while (count != 0) {
                        denominations.add(USD5);
                        count--;

                        logger.info("В список купюр добавлен номинал: {} {}", USD5.getDigitDenomination(), USD5.getCurrency());
                    }
                else if (digitPart == USD10.getDigitDenomination())
                    while (count != 0) {
                        denominations.add(USD10);
                        count--;

                        logger.info("В список купюр добавлен номинал: {} {}", USD10.getDigitDenomination(), USD10.getCurrency());
                    }
                else if (digitPart == USD20.getDigitDenomination())
                    while (count != 0) {
                        denominations.add(USD20);
                        count--;

                        logger.info("В список купюр добавлен номинал: {} {}", USD20.getDigitDenomination(), USD20.getCurrency());
                    }
                else if (digitPart == USD50.getDigitDenomination())
                    while (count != 0) {
                        denominations.add(USD50);
                        count--;

                        logger.info("В список купюр добавлен номинал: {} {}", USD50.getDigitDenomination(), USD50.getCurrency());
                    }
                else if (digitPart == USD100.getDigitDenomination())
                    while (count != 0) {
                        denominations.add(USD100);
                        count--;

                        logger.info("В список купюр добавлен номинал: {} {}", USD100.getDigitDenomination(), USD100.getCurrency());
                    }
            }
            i++;
        }

        logger.info("Окончен процесс формирования списка внесенных купюр в долларах");
        return denominations;
    }

    private static List<Denomination> EURTakeDenomination() {
        logger.info("Запущен процесс формирования списка внесенных купюр в евро");
        List<Denomination> denominations = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        String denominationString = scanner.nextLine();

        logger.info("Клиент внес список купюр в евро: {}", denominationString);

        int i = 0;
        int count = 0;
        for (var str : denominationString.split("\\W+")) {
            int digitPart = Integer.parseInt(str.trim());

            if (i % 2 == 0)
                count = digitPart;
            else {
                if (digitPart == EUR5.getDigitDenomination())
                    while (count != 0) {
                        denominations.add(EUR5);
                        count--;

                        logger.info("В список купюр добавлен номинал: {} {}", EUR5.getDigitDenomination(), EUR5.getCurrency());
                    }
                else if (digitPart == EUR10.getDigitDenomination())
                    while (count != 0) {
                        denominations.add(EUR10);
                        count--;

                        logger.info("В список купюр добавлен номинал: {} {}", EUR10.getDigitDenomination(), EUR10.getCurrency());
                    }
                else if (digitPart == EUR20.getDigitDenomination())
                    while (count != 0) {
                        denominations.add(EUR20);
                        count--;

                        logger.info("В список купюр добавлен номинал: {} {}", EUR20.getDigitDenomination(), EUR20.getCurrency());
                    }
                else if (digitPart == EUR50.getDigitDenomination())
                    while (count != 0) {
                        denominations.add(EUR50);
                        count--;

                        logger.info("В список купюр добавлен номинал: {} {}", EUR50.getDigitDenomination(), EUR50.getCurrency());
                    }
                else if (digitPart == EUR100.getDigitDenomination())
                    while (count != 0) {
                        denominations.add(EUR100);
                        count--;

                        logger.info("В список купюр добавлен номинал: {} {}", EUR100.getDigitDenomination(), EUR100.getCurrency());
                    }
                else if (digitPart == EUR200.getDigitDenomination())
                    while (count != 0) {
                        denominations.add(EUR200);
                        count--;

                        logger.info("В список купюр добавлен номинал: {} {}", EUR200.getDigitDenomination(), EUR200.getCurrency());
                    }
                else if (digitPart == EUR500.getDigitDenomination())
                    while (count != 0) {
                        denominations.add(EUR500);
                        count--;

                        logger.info("В список купюр добавлен номинал: {} {}", EUR500.getDigitDenomination(), EUR500.getCurrency());
                    }
            }
            i++;
        }

        logger.info("Окончен процесс формирования списка внесенных купюр в евро");
        return denominations;
    }

    public static int take(Map<Currency, DepositBox> depositBoxes, List<Denomination> denominations) {
        logger.info("Запущен процесс помещения списка купюр в ячейки");

        int acceptedDenomination = 0;
        for (var denomination : denominations) {
            try {
                acceptedDenomination += switch (denomination.getCurrency()) {
                    case RUB -> takeRUB((RUBDepositBox) depositBoxes.get(RUB), (RUBDenomination) denomination);
                    case USD -> takeUSD((USDDepositBox) depositBoxes.get(USD), (USDDenomination) denomination);
                    case EUR -> takeEUR((EURDepositBox) depositBoxes.get(EUR), (EURDenomination) denomination);
                };
            } catch (IncorrectDenominationException | DenominationOverflowException ignored) {}
        }

        logger.info("Окочен процесс помещения списка купюр в ячейки. Итого пополнено {}", acceptedDenomination);
        return acceptedDenomination;
    }

    private static int takeRUB(RUBDepositBox rubDepositBox, RUBDenomination rubDenomination) throws DenominationOverflowException, IncorrectDenominationException {
        logger.info("Запущен процесс обработки купюры {} {} ", rubDenomination.getDigitDenomination(), rubDenomination.getCurrency());

        if (rubDepositBox.getSum() >= Integer.MAX_VALUE - RUBDenomination.getMaxDenomination().getDigitDenomination())
            throw new DenominationOverflowException(rubDenomination);

        switch (rubDenomination) {
            case RUB100 -> rubDepositBox.upQuantity(RUBDenomination.RUB100);
            case RUB200 -> rubDepositBox.upQuantity(RUBDenomination.RUB200);
            case RUB500 -> rubDepositBox.upQuantity(RUBDenomination.RUB500);
            case RUB1000 -> rubDepositBox.upQuantity(RUBDenomination.RUB1000);
            case RUB2000 -> rubDepositBox.upQuantity(RUBDenomination.RUB2000);
            case RUB5000 -> rubDepositBox.upQuantity(RUBDenomination.RUB5000);
            default -> throw new IncorrectDenominationException(rubDenomination);
        }

        logger.info("Окончен процесс обработки купюры {} {} ", rubDenomination.getDigitDenomination(), rubDenomination.getCurrency());
        return rubDenomination.getDigitDenomination();
    }

    private static int takeUSD(USDDepositBox usdDepositBox, USDDenomination usdDenomination) throws DenominationOverflowException, IncorrectDenominationException {
        logger.info("Запущен процесс обработки купюры {} {} ", usdDenomination.getDigitDenomination(), usdDenomination.getCurrency());

        if (usdDepositBox.getSum() >= Integer.MAX_VALUE - USDDenomination.getMaxDenomination().getDigitDenomination())
            throw new DenominationOverflowException(usdDenomination);

        switch (usdDenomination) {
            case USD1 -> usdDepositBox.upQuantity(USDDenomination.USD1);
            case USD2 -> usdDepositBox.upQuantity(USDDenomination.USD2);
            case USD5 -> usdDepositBox.upQuantity(USDDenomination.USD5);
            case USD10 -> usdDepositBox.upQuantity(USDDenomination.USD10);
            case USD20 -> usdDepositBox.upQuantity(USDDenomination.USD20);
            case USD50 -> usdDepositBox.upQuantity(USDDenomination.USD50);
            case USD100 -> usdDepositBox.upQuantity(USDDenomination.USD100);
            default -> throw new IncorrectDenominationException(usdDenomination);
        }

        logger.info("Окончен процесс обработки купюры {} {} ", usdDenomination.getDigitDenomination(), usdDenomination.getCurrency());
        return usdDenomination.getDigitDenomination();
    }

    private static int takeEUR(EURDepositBox eurDepositBox, EURDenomination eurDenomination) throws DenominationOverflowException, IncorrectDenominationException {
        logger.info("Запущен процесс обработки купюры {} {} ", eurDenomination.getDigitDenomination(), eurDenomination.getCurrency());

        if (eurDepositBox.getSum() >= Integer.MAX_VALUE - USDDenomination.getMaxDenomination().getDigitDenomination())
            throw new DenominationOverflowException(eurDenomination);

        switch (eurDenomination) {
            case EUR5 -> eurDepositBox.upQuantity(EURDenomination.EUR5);
            case EUR10 -> eurDepositBox.upQuantity(EURDenomination.EUR10);
            case EUR20 -> eurDepositBox.upQuantity(EURDenomination.EUR20);
            case EUR50 -> eurDepositBox.upQuantity(EURDenomination.EUR50);
            case EUR100 -> eurDepositBox.upQuantity(EURDenomination.EUR100);
            case EUR200 -> eurDepositBox.upQuantity(EURDenomination.EUR200);
            case EUR500 -> eurDepositBox.upQuantity(EURDenomination.EUR500);
            default -> throw new IncorrectDenominationException(eurDenomination);
        }

        logger.info("Окончен процесс обработки купюры {} {} ", eurDenomination.getDigitDenomination(), eurDenomination.getCurrency());
        return eurDenomination.getDigitDenomination();
    }
}

