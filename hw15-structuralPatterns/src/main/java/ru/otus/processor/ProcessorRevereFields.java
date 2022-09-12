package ru.otus.processor;

import ru.otus.model.Message;

public class ProcessorRevereFields implements Processor{
    @Override
    public Message process(Message message) {
        String tmp = message.getField11();
        return message.toBuilder().field11(message.getField12()).field12(tmp).build();
    }
}
