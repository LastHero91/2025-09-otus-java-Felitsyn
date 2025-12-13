package ru.otus.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.*;

import ru.otus.model.Ruble;
import ru.otus.model.dto.BanknoteDTO;
import ru.otus.model.dto.DepositBoxDTO;

class TakeBanknoteService {
    private static final Logger logger = LoggerFactory.getLogger(TakeBanknoteService.class);

    public void printInitMessage(List<DepositBoxDTO> depositBoxList) {
        StringBuilder strBanknoteAmounts = new StringBuilder();
        for (var depositBox : depositBoxList)
            strBanknoteAmounts.append(String.format("%s; ",depositBox.getBanknote().getAmount() + depositBox.getBanknote().getCurrency()));

        System.out.printf("""
                Необходимо произвести загрузку банкомата купюрами. В банкомате доступны ячейки для купюр: %s
                Для загрузки купюр введите через ; количество и сумму в формате: штук - номинал
                Пример ввода: 50 - 100; 20 - 200; 1000 - 500; 2000 - 1000; 30 - 2000; 1000 - 5000
                """,
                strBanknoteAmounts);

        logger.info("Выведено сообщение о необходимости произвести загрузку банкомата купюрами, с примером ввода.");
    }

    public void printAcceptedSum(Map<BanknoteDTO, Integer> acceptedBanknotes) {
        long sum = 0;
        for (var acceptedBanknote : acceptedBanknotes.entrySet())
            sum += (long) acceptedBanknote.getKey().getAmount() * acceptedBanknote.getValue();

        System.out.printf("Сумма принятых купюр - %d\n", sum);

        logger.info("""
                    Окончен процесс вывода сообщения о сумме принятых купюр в банкомат.
                    Список принятых купюр: {}
                    Сумма: {}""", acceptedBanknotes, sum);
    }

    public void printUnacceptedBanknotes(Map<Integer, Integer> unacceptedBanknotes) {
        for (var unacceptedBanknote : unacceptedBanknotes.entrySet()) {
            if (unacceptedBanknote.getValue() > 0) {
                System.out.printf("Не принята купюра - %s, в количестве - %s;\n",
                        unacceptedBanknote.getKey(), unacceptedBanknote.getValue());
            }
        }

        logger.info("""
                    Окончен процесс вывода сообщения о не принятых купюрах в банкомат.
                    Список не принятых купюр: {}""", unacceptedBanknotes);
    }

    public void fillMapBanknotes(List<DepositBoxDTO> depositBoxList,
                                        Map<BanknoteDTO, Integer> acceptedBanknotes, Map<Integer, Integer> unacceptedBanknotes) {
        Scanner scanner = new Scanner(System.in);
        String banknotesStr = scanner.nextLine();

        int i = 0;
        int count = 0;
        for (var str : banknotesStr.split("\\W+")) {
            int digitPart = Integer.parseInt(str.trim());

            if (i % 2 == 0)
                count = digitPart;
            else {
                for (int j = 0; j < depositBoxList.size(); j++) {
                    int depositBoxAmount = depositBoxList.get(j).getBanknote().getAmount();

                    if (depositBoxAmount == digitPart) {
                        acceptedBanknotes.put(new Ruble(digitPart), count);
                        break;
                    }

                    if (j == depositBoxList.size() - 1)
                        unacceptedBanknotes.put(digitPart, count);
                }
            }
            i++;
        }

        logger.info("""
                Окончен процесс ввода купюр в приемник.
                Купюры попавшие в принятые: {}
                Купюры не попавшие в принятые: {}""",
                acceptedBanknotes, unacceptedBanknotes);
    }

    public void putInDepositBoxList(List<DepositBoxDTO> depositBoxList,
                                           Map<BanknoteDTO, Integer> acceptedBanknotes, Map<Integer, Integer> unacceptedBanknotes) {
        // Циклами смотрим есть ли пересечения по купюрам
        for (var depositBox : depositBoxList) {
            for (var acceptedBanknote : acceptedBanknotes.entrySet()) {
                // Если есть, то добавляем
                if (depositBox.getBanknote().equals(acceptedBanknote.getKey())) {
                    int unacceptedCount = depositBox.addBanknotes(acceptedBanknote.getValue());
                    // Пополняем не принятые купюры
                    unacceptedBanknotes.put(acceptedBanknote.getKey().getAmount(), unacceptedCount);
                    break;
                }
            }
        }

        logger.info("""
                Окончен процесс заполнения ячейки принятыми купюрами в банкомат.
                Полный список принятых купюр: {}
                Полный список не принятых купюр: {}""",
                depositBoxList, unacceptedBanknotes);
    }
}