package otus.hw;

import otus.hw.cash.Banknote;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Cassette<T extends Banknote> {
    private int currentNumberBanknotes;
    private final T.Nominal nominal;

    private Cassette(Banknote.Nominal nominal, int numberBanknotes) {
        this.currentNumberBanknotes = numberBanknotes;
        this.nominal = nominal;
    }

    public void put(int amount) {
        currentNumberBanknotes += amount;
    }

    public List<T> get(int amount) {
        if (amount > currentNumberBanknotes)
            throw new RuntimeException("No so much money");

        currentNumberBanknotes -= amount;
        return IntStream.range(0, amount).mapToObj(i -> (T) nominal.createBanknote()).collect(Collectors.toList());
    }

    public T.Nominal getNominal() {
        return nominal;
    }

    public int getRemainderBankNote() {
        return currentNumberBanknotes;
    }

    public static class CassetteBuilder<T extends Banknote> {
        private int numberBanknotes;
        private T.Nominal nominal;

        public CassetteBuilder<T> setNominal(T.Nominal nominal) {
            this.nominal = nominal;
            return this;
        }

        public CassetteBuilder<T> fillCassette(int amount) {
            this.numberBanknotes = amount;
            return this;
        }

        public Cassette<T> build() {
            if (nominal == null || numberBanknotes < 1)
                throw new RuntimeException("Error build cassette");

            return new Cassette<>(nominal, numberBanknotes);
        }
    }
}
