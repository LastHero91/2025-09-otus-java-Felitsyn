package ru.otus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.handler.ComplexProcessor;
import ru.otus.listener.ListenerPrinterConsole;
import ru.otus.listener.homework.HistoryListener;
import ru.otus.model.Message;
import ru.otus.model.ObjectForMessage;
import ru.otus.processor.*;

import java.util.ArrayList;
import java.util.List;

public class HomeWork {

    /*
    Реализовать to do:
      1. Добавить поля field11 - field13 (для field13 используйте класс ObjectForMessage)
      2. Сделать процессор, который поменяет местами значения field11 и field12
      3. Сделать процессор, который будет выбрасывать исключение в четную секунду (сделайте тест с гарантированным результатом)
            Секунда должна определяьться во время выполнения.
            Тест - важная часть задания
            Обязательно посмотрите пример к паттерну Мементо!
      4. Сделать Listener для ведения истории (подумайте, как сделать, чтобы сообщения не портились)
         Уже есть заготовка - класс HistoryListener, надо сделать его реализацию
         Для него уже есть тест, убедитесь, что тест проходит
    */

    private static final Logger logger = LoggerFactory.getLogger(HomeWork.class);

    public static void main(String[] args) {
        /*
          по аналогии с Demo.class
          из элеменов "to do" создать new ComplexProcessor и обработать сообщение
        */

        var processors = List.of(
                new LoggerProcessor(new ProcessorConcatFields()),
                new ProcessorUpperField10(),
                new ProcessorSwapFields(),
                new LoggerProcessor(new ProcessorEvenSeconds()));

        var complexProcessor = new ComplexProcessor(processors, Throwable::printStackTrace);
        var listenerPrinter = new ListenerPrinterConsole();
        var historyListener = new HistoryListener();

        var field13 = new ObjectForMessage();
        List<String> listData = new ArrayList<>();
        listData.add("Раз");
        listData.add("два");
        listData.add("три");
        listData.add("елочка – гори!");
        field13.setData(listData);

        complexProcessor.addListener(listenerPrinter);
        complexProcessor.addListener(historyListener);

        long id = 2L;
        var message = new Message.Builder(id)
                .field1("field1")
                .field2("field2")
                .field3("field3")
                .field6("field6")
                .field10("field10")
                .field11("field11")
                .field12("field12")
                .field13(field13)
                .build();

        var result = complexProcessor.handle(message);
        field13.setData(List.of("1", "2", "3", "елочка – гори!"));

        logger.info("getField13:{}", message.getField13().getData());
        logger.info("OldGetField13:{}", historyListener.findMessageById(id).get().getField13().getData());
        logger.info("result:{}", result);

        complexProcessor.removeListener(listenerPrinter);
        complexProcessor.removeListener(historyListener);
    }
}
