package otus.hw;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Objects;

public class Cassette implements Comparable<Cassette> {

    private final Denomination denomination;
    private final ArrayDeque<Banknote> banknotes;
    private int sizeCassette;

    public Cassette(Denomination denomination, int sizeCassette, List<Banknote> banknotes) {
        if (banknotes.size() > sizeCassette)
            throw new IllegalArgumentException("Error, count banknotes more cassette size");

        this.denomination = denomination;
        this.banknotes = new ArrayDeque<>(sizeCassette);
        this.banknotes.addAll(banknotes);
        this.sizeCassette = sizeCassette;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cassette cassette = (Cassette) o;
        return denomination == cassette.denomination;
    }

    @Override
    public int hashCode() {
        return Objects.hash(denomination);
    }

    @Override
    public int compareTo(Cassette o) {
        return Integer.compare(this.denomination.getNum(), o.denomination.getNum());
    }

    public Denomination getDenomination() {
        return denomination;
    }

    public int getAvailableBanknote() {
        return banknotes.size();
    }

    public boolean isAvailableSpace() {
        return sizeCassette - banknotes.size() > 0;
    }

    public void put(Banknote banknote) {
        if (banknotes.size() == sizeCassette)
            throw new RuntimeException("Not available space in cassette");

        banknotes.offerLast(banknote);
    }

    public Banknote get() {
        if (banknotes.size() == 0)
            throw new RuntimeException("Not available banknote");

        return banknotes.pollLast();
    }
}
