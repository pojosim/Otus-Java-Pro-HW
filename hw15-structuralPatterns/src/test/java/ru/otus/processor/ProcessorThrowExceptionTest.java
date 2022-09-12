package ru.otus.processor;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class ProcessorThrowExceptionTest {

    @Test
    void throwExceptionEveryTwoSeconds() {
        for (int i = 0; i < 5; i++) {
            ProcessorThrowException processorThrowException = new ProcessorThrowException();
            var start = System.currentTimeMillis();
            try {
                processorThrowException.process(null);
            } catch (RuntimeException e) {
                Assertions.assertThat((System.currentTimeMillis() - start) / 1000 % 2).isZero();
            }
        }
    }

    @Test
    void throwException() {
        Assertions.assertThatExceptionOfType(RuntimeException.class).isThrownBy(() -> {
            new ProcessorThrowException().process(null);
        });
    }
}