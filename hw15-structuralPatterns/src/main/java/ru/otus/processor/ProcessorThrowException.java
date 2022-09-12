package ru.otus.processor;

import ru.otus.model.Message;


public class ProcessorThrowException implements Processor {

    private long startTime;

    public ProcessorThrowException() {
        startTime = System.currentTimeMillis();
    }

    @Override
    public Message process(Message message) {
        do {
            try {
                long currentTimeMillis = System.currentTimeMillis();
                long l = currentTimeMillis - startTime;
                if (l > 0 && (l / 1000) % 2 == 0) {
                    throw new RuntimeException("Настала чётная секунда");
                }
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        } while (true);
    }
}
