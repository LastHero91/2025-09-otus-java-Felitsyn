package ru.otus;

import ru.otus.model.dto.ATMDTO;
import ru.otus.model.ATM;
import ru.otus.service.InitATMService;

public class Demo {
    public static void main(String[] args) {
        // Созданы ячейки и банкомат
        ATMDTO atm = new ATM(InitATMService.createDepositBoxList());
        // Проинициализированы ячейки банкота
        atm.initDepositBoxList();
        // Запущен основной процессор (процесс взаимодейсвтия с клиентом)
        atm.startProcessing();
        // Остановлен основной процессор (выключение банкомата)
        atm.endProcessing();
    }
}
