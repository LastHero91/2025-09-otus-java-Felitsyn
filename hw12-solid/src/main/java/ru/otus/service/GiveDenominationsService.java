package ru.otus.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.exception.DenominationDeficiencyException;
import ru.otus.exception.IncorrectSumException;
import ru.otus.model.DepositBox;
import ru.otus.model.EURDepositBox;
import ru.otus.model.RUBDepositBox;
import ru.otus.model.USDDepositBox;
import ru.otus.model.currency.*;

import java.util.ArrayList;
import java.util.List;

public class GiveDenominationsService {
    private static final Logger logger = LoggerFactory.getLogger(GiveDenominationsService.class);

    public static void printGivenDenominations(Currency currency, List<Denomination> denominations) {
        logger.info("Запущен процесс вывода сообщения о количестве выданных купюр");

        int sum = 0;
        for (var denomination : denominations)
            sum += denomination.getDigitDenomination();

        System.out.printf("Сумма %d %s выдана\n", sum, currency.getLetterCode());
        logger.info("""
                Окончен процесс вывода сообщения о количестве выданных купюр. Купюр выдано: {} {}""",
                sum, currency.getLetterCode());
    }
    public static List<Denomination> give(DepositBox depositBox, int sum) {
        logger.info("Запущен процесс выдачи купюр");

        List<Denomination> denominations = new ArrayList<>();
        try {
            denominations = switch (depositBox.getCurrency()) {
                case RUB -> giveRUB((RUBDepositBox) depositBox, sum);
                case USD -> giveUSD((USDDepositBox) depositBox, sum);
                case EUR -> giveEUR((EURDepositBox) depositBox, sum);
            };
        } catch (DenominationDeficiencyException | IncorrectSumException e) {}

        logger.info("Окончен процесс выдачи купюр. Итого купюр выдано: {}", denominations);
        return denominations;
    }

    private static List<Denomination> giveRUB(RUBDepositBox rubDepositBox, int sum) throws DenominationDeficiencyException, IncorrectSumException {
        logger.info("Запущен процесс вывода из ячейки рублевых купюр");
        List<Denomination> denominations = new ArrayList<>();

        if (sum > rubDepositBox.getSum())
            throw new DenominationDeficiencyException(sum);

        sum = RUBRemoveDenominationTypeFromSum(rubDepositBox, RUBDenomination.RUB5000, sum, denominations);
        sum = RUBRemoveDenominationTypeFromSum(rubDepositBox, RUBDenomination.RUB2000, sum, denominations);
        sum = RUBRemoveDenominationTypeFromSum(rubDepositBox, RUBDenomination.RUB1000, sum, denominations);
        sum = RUBRemoveDenominationTypeFromSum(rubDepositBox, RUBDenomination.RUB500, sum, denominations);
        sum = RUBRemoveDenominationTypeFromSum(rubDepositBox, RUBDenomination.RUB200, sum, denominations);
        sum = RUBRemoveDenominationTypeFromSum(rubDepositBox, RUBDenomination.RUB100, sum, denominations);

        if (sum > 0)
            throw new IncorrectSumException(rubDepositBox.getCurrency(), sum);

        logger.info("Окончен процесс вывода из ячейки рублевых купюр");
        return denominations;
    }

    private static int RUBRemoveDenominationTypeFromSum(RUBDepositBox rubDepositBox, RUBDenomination rubDenomination, int sum, List<Denomination> denominations) {
        logger.info("Запущен процесс вывода из ячейки рублевых купюр {} {}", rubDenomination, rubDenomination.getCurrency());
        while (sum >= rubDenomination.getDigitDenomination()
                && sum % rubDenomination.getDigitDenomination() == 0
                && switch (rubDenomination) {
                    case RUB5000 -> rubDepositBox.getQuantity5000();
                    case RUB2000 -> rubDepositBox.getQuantity2000();
                    case RUB1000 -> rubDepositBox.getQuantity1000();
                    case RUB500 -> rubDepositBox.getQuantity500();
                    case RUB200 -> rubDepositBox.getQuantity200();
                    case RUB100 -> rubDepositBox.getQuantity100();
                } > 0) {
            denominations.add(rubDepositBox.downQuantity(rubDenomination));
            logger.info("Кпюра {} {} извлечена из ячейки", rubDenomination, rubDenomination.getCurrency());
            sum -= rubDenomination.getDigitDenomination();
        }
        logger.info("Окончен процесс вывода из ячейки рублевых купюр {} {}", rubDenomination, rubDenomination.getCurrency());
        return sum;
    }

    private static List<Denomination> giveUSD(USDDepositBox usdDepositBox, int sum) throws DenominationDeficiencyException, IncorrectSumException {
        logger.info("Запущен процесс вывода из ячейки долларовых купюр");
        List<Denomination> denominations = new ArrayList<>();

        if (sum > usdDepositBox.getSum())
            throw new DenominationDeficiencyException(sum);

        sum = USDRemoveDenominationTypeFromSum(usdDepositBox, USDDenomination.USD100, sum, denominations);
        sum = USDRemoveDenominationTypeFromSum(usdDepositBox, USDDenomination.USD50, sum, denominations);
        sum = USDRemoveDenominationTypeFromSum(usdDepositBox, USDDenomination.USD20, sum, denominations);
        sum = USDRemoveDenominationTypeFromSum(usdDepositBox, USDDenomination.USD10, sum, denominations);
        sum = USDRemoveDenominationTypeFromSum(usdDepositBox, USDDenomination.USD5, sum, denominations);
        sum = USDRemoveDenominationTypeFromSum(usdDepositBox, USDDenomination.USD2, sum, denominations);
        sum = USDRemoveDenominationTypeFromSum(usdDepositBox, USDDenomination.USD1, sum, denominations);

        if (sum > 0)
            throw new IncorrectSumException(usdDepositBox.getCurrency(), sum);

        logger.info("Окончен процесс вывода из ячейки долларовых купюр");
        return denominations;
    }

    private static int USDRemoveDenominationTypeFromSum(USDDepositBox usdDepositBox, USDDenomination usdDenomination, int sum, List<Denomination> denominations) {
        logger.info("Запущен процесс вывода из ячейки долларовых купюр {} {}", usdDenomination, usdDenomination.getCurrency());
        while (sum >= usdDenomination.getDigitDenomination()
                && sum % usdDenomination.getDigitDenomination() == 0
                && switch (usdDenomination) {
                    case USD100 -> usdDepositBox.getQuantity100();
                    case USD50 -> usdDepositBox.getQuantity50();
                    case USD20 -> usdDepositBox.getQuantity20();
                    case USD10 -> usdDepositBox.getQuantity10();
                    case USD5 -> usdDepositBox.getQuantity5();
                    case USD2 -> usdDepositBox.getQuantity2();
                    case USD1 -> usdDepositBox.getQuantity1();
                } > 0) {
            denominations.add(usdDepositBox.downQuantity(usdDenomination));
            logger.info("Кпюра {} {} извлечена из ячейки", usdDenomination, usdDenomination.getCurrency());
            sum -= usdDenomination.getDigitDenomination();
        }

        logger.info("Окончен процесс вывода из ячейки долларовых купюр {} {}", usdDenomination, usdDenomination.getCurrency());
        return sum;
    }

    private static List<Denomination> giveEUR(EURDepositBox eurDepositBox, int sum) throws DenominationDeficiencyException, IncorrectSumException {
        logger.info("Запущен процесс вывода из ячейки евро купюр");
        List<Denomination> denominations = new ArrayList<>();

        if (sum > eurDepositBox.getSum())
            throw new DenominationDeficiencyException(sum);

        sum = EURRemoveDenominationTypeFromSum(eurDepositBox, EURDenomination.EUR500, sum, denominations);
        sum = EURRemoveDenominationTypeFromSum(eurDepositBox, EURDenomination.EUR200, sum, denominations);
        sum = EURRemoveDenominationTypeFromSum(eurDepositBox, EURDenomination.EUR100, sum, denominations);
        sum = EURRemoveDenominationTypeFromSum(eurDepositBox, EURDenomination.EUR50, sum, denominations);
        sum = EURRemoveDenominationTypeFromSum(eurDepositBox, EURDenomination.EUR20, sum, denominations);
        sum = EURRemoveDenominationTypeFromSum(eurDepositBox, EURDenomination.EUR10, sum, denominations);
        sum = EURRemoveDenominationTypeFromSum(eurDepositBox, EURDenomination.EUR5, sum, denominations);

        if (sum > 0)
            throw new IncorrectSumException(eurDepositBox.getCurrency(), sum);

        logger.info("Окончен процесс вывода из ячейки евро купюр");
        return denominations;
    }

    private static int EURRemoveDenominationTypeFromSum(EURDepositBox eurDepositBox, EURDenomination eurDenomination, int sum, List<Denomination> denominations) {
        logger.info("Запущен процесс вывода из ячейки долларовых купюр {} {}", eurDenomination, eurDenomination.getCurrency());
        while (sum >= eurDenomination.getDigitDenomination()
                && sum % eurDenomination.getDigitDenomination() == 0
                && switch (eurDenomination) {
                    case EUR500 -> eurDepositBox.getQuantity500();
                    case EUR200 -> eurDepositBox.getQuantity200();
                    case EUR100 -> eurDepositBox.getQuantity100();
                    case EUR50 -> eurDepositBox.getQuantity50();
                    case EUR20 -> eurDepositBox.getQuantity20();
                    case EUR10 -> eurDepositBox.getQuantity10();
                    case EUR5 -> eurDepositBox.getQuantity5();
                } > 0) {
            denominations.add(eurDepositBox.downQuantity(eurDenomination));
            logger.info("Кпюра {} {} извлечена из ячейки", eurDenomination, eurDenomination.getCurrency());
            sum -= eurDenomination.getDigitDenomination();
        }
        logger.info("Окончен процесс вывода из ячейки долларовых купюр {} {}", eurDenomination, eurDenomination.getCurrency());
        return sum;
    }
}
