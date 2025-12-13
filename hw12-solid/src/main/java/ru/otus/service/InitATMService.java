package ru.otus.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.ArrayList;

import ru.otus.model.Ruble;
import ru.otus.model.RubleDepositBox;
import ru.otus.model.dto.DepositBoxDTO;

public class InitATMService {
    private static final Logger logger = LoggerFactory.getLogger(InitATMService.class);

    public static List<DepositBoxDTO> createDepositBoxList() {
        List<DepositBoxDTO> depositBoxList = new ArrayList<>();

        depositBoxList.add(new RubleDepositBox(new Ruble(100)));
        depositBoxList.add(new RubleDepositBox(new Ruble(200)));
        depositBoxList.add(new RubleDepositBox(new Ruble(500)));
        depositBoxList.add(new RubleDepositBox(new Ruble(1000)));
        depositBoxList.add(new RubleDepositBox(new Ruble(2000)));
        depositBoxList.add(new RubleDepositBox(new Ruble(5000)));
        depositBoxList.sort((rdb1, rdb2) -> Integer.compare(rdb2.getBanknote().getAmount(), rdb1.getBanknote().getAmount()));

        logger.info("Созданы ячейки банкномата: {}", depositBoxList);
        return depositBoxList;
    }
}
