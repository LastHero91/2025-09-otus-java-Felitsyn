package ru.otus.service;

import static ru.otus.model.currency.Currency.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.model.DepositBox;
import ru.otus.model.currency.Currency;
import ru.otus.model.currency.Denomination;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ProcessingService {
    private static final Logger logger = LoggerFactory.getLogger(ProcessingService.class);

    public static void start(Map<Currency, DepositBox> depositBoxes) {
        logger.info("Запущен основной процессор банкомата");
        if (depositBoxes == null) return;
        while (true) {
            String actionStr = clarifyProcess();

            switch (actionStr) {
                case "остановить" -> {
                    logger.info("Остановлен основной процессор банкомата");
                    return;}
                case "остаток" -> printBalance(depositBoxes);
                case "пополнить" -> takeDenomination(depositBoxes);
                case "снять" -> giveDenominations(depositBoxes);
            }
        }
    }

    private static String clarifyProcess() {
        logger.info("Запущен процесс определения действия клиента");
        System.out.println("""
                Уточните Ваши действия: 
                * Снять денежные средства, тогда введите слово: снять
                * Пополнить денежные средства, тогда введите слово: пополнить
                * Узнать остаток средствв банкомате (суперспособность), тогда введите слово: остаток
                * Выключить банкомат (суперспособность), тогда введите слово: остановить""");
        Scanner scanner = new Scanner(System.in);
        String actionStr = scanner.nextLine();

        logger.info("Клиент выбрал действие: {}. Процесс определения действия клиента окончен", actionStr);
        return actionStr;
    }

    private static void printBalance(Map<Currency, DepositBox> depositBoxes) {
        logger.info("Запущен процесс определения остатка купюр в банкомате");

        for (var depositBox : depositBoxes.entrySet())
            System.out.printf("Остаток в %s ячейке: %d\n", depositBox.getKey().getLetterCode(), depositBox.getValue().getSum());

        logger.info("Окончен процесс определения остатка купюр в банкомате");
    }

    private static void takeDenomination(Map<Currency, DepositBox> depositBoxes) {
        logger.info("Запущен процесс пополнения купюр в банкомате");

        List<Denomination> denominations = TakeDenominationsService.clarifyTakeDenomination();
        int acceptedDenomination = TakeDenominationsService.take(depositBoxes, denominations);
        TakeDenominationsService.printAcceptedDenominations(acceptedDenomination);

        logger.info("Окончен процесс пополнения купюр в банкомате");
    }

    private static void giveDenominations(Map<Currency, DepositBox> depositBoxes) {
        logger.info("Запущен процесс выдачи купюр");

        while (true) {
            List<Denomination> denominations;
            System.out.printf("Введите код валюты выводимых средств: %s %s %s\n",
                    RUB.getLetterCode(), USD.getLetterCode(), EUR.getLetterCode());

            Scanner scanner = new Scanner(System.in);
            String currencyStr = scanner.nextLine();
            logger.info("Клиентом выбрана валюта для выдачи купюр: {}", currencyStr);

            System.out.printf("Введите сумму вывода: \n");
            scanner.reset();
            int sum = scanner.nextInt();
            logger.info("Клиентом выбрана сумма для выдачи купюр: {}", sum);

            for (var depositBox : depositBoxes.entrySet()) {
                if (depositBox.getKey().getLetterCode().equals(currencyStr)) {
                    try {
                        denominations = GiveDenominationsService.give(depositBox.getValue(), sum);
                        GiveDenominationsService.printGivenDenominations(depositBox.getKey(), denominations);
                    } catch (Exception e) {
                        continue;
                    }
                } else
                    continue;
            }

            logger.info("Окончен процесс выдачи купюр в банкомате");
            break;
        }
    }
}
