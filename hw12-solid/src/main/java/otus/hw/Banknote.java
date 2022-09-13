package otus.hw;

import java.util.concurrent.ThreadLocalRandom;

public class Banknote {
    private static final ThreadLocalRandom THREAD_LOCAL_RANDOM = ThreadLocalRandom.current();

    private final Denomination denomination;
    private final long serialNumber; // пример чтобы каждая банкнота была уникальна в рамках банкомата

    public Banknote(Denomination denomination) {
        this.denomination = denomination;
        serialNumber = THREAD_LOCAL_RANDOM.nextLong(Long.MAX_VALUE);
    }

    public Denomination getDenomination() {
        return denomination;
    }

    public long getSerialNumber() {
        return serialNumber;
    }

    @Override
    public String toString() {
        return "Banknote{" +
                "denomination=" + denomination +
                ", serialNumber=" + serialNumber +
                '}';
    }
}
