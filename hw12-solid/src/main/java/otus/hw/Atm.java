package otus.hw;

import otus.hw.cash.Banknote;

import java.util.*;
import java.util.stream.Collectors;

public class Atm<T extends Banknote> implements AdminAccess<T> {

    private final AtmFactory<T> factory;
    private final Map<T.Nominal, Cassette<T>> cassettes;

    public Atm(AtmFactory<T> factory) {
        this.factory = factory;
        cassettes = new TreeMap<>(Comparator.comparing(Banknote.Nominal::getAmount));
    }

    public void createCassette(T.Nominal nominal, int currentNumberBanknote) {
        cassettes.put(nominal, factory.createCassette(nominal, currentNumberBanknote));
    }

    public void putCash(List<T> cash) {
        cash.stream().collect(Collectors.groupingBy(Banknote::getNominal, Collectors.summingInt(value -> 1)))
                .forEach((nominal, amount) -> {
                    Cassette<T> cassette = cassettes.get(nominal);
                    if (cassette != null) {
                        cassette.put(amount);
                    } else {
                        throw new UnsupportedOperationException("Not found cassette for these banknote nominal - " + nominal);
                    }
                });
    }

    public List<T> getCash(int amountCash) {
        checkCardinality(amountCash);

        List<T> cash = new ArrayList<>();

        for (Map.Entry<T.Nominal, Cassette<T>> entry : cassettes.entrySet()) {
            int cassetteNominalAmount = entry.getKey().getAmount();

            if (cassetteNominalAmount > amountCash || amountCash == 0)
                break;

            Cassette<T> cassette = entry.getValue();

            int remainderBanknote = cassette.getRemainderBankNote();
            int remainderSum = remainderBanknote * cassetteNominalAmount;

            if (remainderSum < amountCash) {
                cash.addAll(cassette.get(remainderBanknote));
                amountCash -= remainderSum;
            } else {
                cash.addAll(cassette.get(amountCash/cassetteNominalAmount));
                amountCash = 0;
            }
        }

        if (cash.size() == 0 || amountCash > 0)
            throw new RuntimeException("Error get cash");

        return cash;
    }

    public List<T> getAllCash() {
        return cassettes.values().stream()
                .map(cassette -> cassette.get(cassette.getRemainderBankNote()))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    private void checkCardinality(int numberCash) {
        if (numberCash % cassettes.entrySet().iterator().next().getKey().getAmount() != 0)
            throw new RuntimeException("Error get cash");
    }

    @Override
    public Map<T.Nominal, Integer> getBalanceAtm() {
        return cassettes.entrySet().stream().collect(Collectors.toMap(
                Map.Entry::getKey,
                entry -> entry.getValue().getRemainderBankNote(),
                Integer::sum));
    }

    public AdminAccess<T> getAdminAccess() {
        return (AdminAccess<T>) this;
    }
}
