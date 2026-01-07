package ru.otus.processor;

import ru.otus.model.Message;

public class ProcessorSwapFields implements Processor {
    @Override
    public Message process(Message message) {
        var field12 = message.getField11();
        var field11 = message.getField12();

        return message
                .toBuilder()
                .field12(field12)
                .field11(field11)
                .build();
    }
}
