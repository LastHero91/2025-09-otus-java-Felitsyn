package ru.otus.model;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import java.util.List;

import ru.otus.model.dto.ATMDTO;
import ru.otus.model.dto.DepositBoxDTO;
import ru.otus.service.ProcessingService;

public class ATM implements ATMDTO {
    private static final Logger logger = LoggerFactory.getLogger(ATM.class);

    private final List<DepositBoxDTO> depositBoxList;
    private final ProcessingService processingService;

    public ATM(List<DepositBoxDTO> depositBoxList) {
        this.depositBoxList = depositBoxList;
        this.processingService = new ProcessingService();

        logger.info("Создан банкомат с ячейками: {}", this.depositBoxList);
    }

    @Override
    public void initDepositBoxList() {
        processingService.takeBanknotes(depositBoxList);
        logger.info("Ячейки банкомата проинициализированы: {}", depositBoxList);
    }

    @Override
    public void startProcessing() {
        while (true) {
            String clarifyProcess = processingService.clarifyProcess();

            switch (clarifyProcess) {
                case "остановить" -> {return;}
                case "остаток" -> processingService.printBalance(depositBoxList);
                case "пополнить" -> processingService.takeBanknotes(depositBoxList);
                case "снять" -> processingService.giveBanknotes(depositBoxList);
            }
        }
    }

    @Override
    public void endProcessing() {
        logger.info("Работа банкомата остановлена");
    }
}
