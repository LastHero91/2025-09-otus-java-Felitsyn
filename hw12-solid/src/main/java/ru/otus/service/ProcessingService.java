package ru.otus.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.*;

import ru.otus.model.dto.DepositBoxDTO;

public class ProcessingService {
    private static final Logger logger = LoggerFactory.getLogger(ProcessingService.class);

    private final TakeBanknoteService takeBanknoteService = new TakeBanknoteService();
    private final GiveBanknoteService giveBanknoteService = new GiveBanknoteService();

    public String clarifyProcess() {
        System.out.println("""
                Уточните Ваши действия:
                * Снять денежные средства, тогда введите слово: снять
                * Пополнить денежные средства, тогда введите слово: пополнить
                * Узнать остаток средств в банкомате, тогда введите слово: остаток
                * Выключить банкомат (суперспособность), тогда введите слово: остановить""");
        Scanner scanner = new Scanner(System.in);
        String actionStr = scanner.nextLine();

        logger.info("Клиент выбрал действие: {}. Процесс определения действия клиента окончен.", actionStr);
        return actionStr;
    }

    public void printBalance(List<DepositBoxDTO> depositBoxList) {
        long totalSum = 0L;

        for (var depositBox : depositBoxList) {
            System.out.printf("Остаток в ячейке с номиналом в %d %s: %d\n",
                    depositBox.getBanknote().getAmount(), depositBox.getBanknote().getCurrency(), depositBox.getSum());
            totalSum += depositBox.getSum();
        }

        System.out.printf("Общая сумма остатка средств в банкомате:%d\n", totalSum);
        logger.info("Окончен процесс определения остатка купюр в банкомате. Всего средств: {}", totalSum);
    }

    public void takeBanknotesProcess(List<DepositBoxDTO> depositBoxList) {
        takeBanknoteService.takeBanknotesProcess(depositBoxList);
        logger.info("Окончен процесс пополнения купюр в банкомате");
    }

    public void giveBanknotesProcess(List<DepositBoxDTO> depositBoxList) {
        giveBanknoteService.giveBanknotesProcess(depositBoxList);
        logger.info("Окончен процесс выдачи купюр в банкомате");
    }
}
