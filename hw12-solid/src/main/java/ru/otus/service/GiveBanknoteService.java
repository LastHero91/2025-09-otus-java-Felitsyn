package ru.otus.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.Scanner;

import ru.otus.model.Ruble;
import ru.otus.model.dto.BanknoteDTO;
import ru.otus.model.dto.DepositBoxDTO;

class GiveBanknoteService {
    private static final Logger logger = LoggerFactory.getLogger(GiveBanknoteService.class);

    public int getSumFromClient() {
        System.out.println("Введите сумму вывода:");
        Scanner scanner = new Scanner(System.in);
        int sum = scanner.nextInt();

        logger.info("Клиентом задана сумма для выдачи купюр: {}", sum);
        return sum;
    }

    public void printGivenMessage(List<BanknoteDTO> acceptedBanknotes, Integer unacceptedSum) {
        int sum = 0;
        for (var acceptedBanknote : acceptedBanknotes)
            sum += acceptedBanknote.getAmount();

        System.out.printf("""
                Выдана сумма: %d
                Не смогли выдатать сумму: %d
                """, sum, unacceptedSum);

        logger.info("""
                Окончен процесс вывода сообщения о количестве выданных купюр и не выданной сумме.
                Выдана сумма: {}
                Не смогли выдатать сумму: {}""",
                sum, unacceptedSum);
    }

    public int putBanknoteListsFromClient(List<DepositBoxDTO> depositBoxList,
                                                 int sum, List<BanknoteDTO> acceptedBanknotes) {
        for (var depositBox: depositBoxList) {
            int amount = depositBox.getBanknote().getAmount();
            int countBanknotes = depositBox.takeBanknotes(sum / amount);

            for (int i = 0; i < countBanknotes; i++) {
                acceptedBanknotes.add(new Ruble(amount));
            }

            sum -= (countBanknotes * amount);
        }

        logger.info("""
                Окончен процесс изъятия купюр из ячеек банкомата.
                Купюр смогли взять из ячеек банкомата: {}
                Сумма, которую не смогли выдать: {}""",
                acceptedBanknotes, sum);

        return sum;
    }
}