package otus.hw;

import otus.hw.exception.AtmException;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MyAtm implements Atm {

    private final Map<Denomination, Cassette> cassettes;

    public MyAtm(List<Cassette> cassettes) {
        // form the amount first from large denominations
        this.cassettes = cassettes.stream()
                .collect(Collectors.toMap(Cassette::getDenomination, Function.identity(), (cassette, cassette2) -> cassette2, () -> new TreeMap<>(Comparator.reverseOrder())));
    }

    @Override
    public List<Banknote> dispenseCash(int amountCash) throws AtmException {
        checkAvailableDispense(amountCash);

        List<Banknote> retBanknotes = new ArrayList<>();

        var entries = cassettes.entrySet().stream().filter(entry -> entry.getKey().getNum() <= amountCash).collect(Collectors.toList());

        int remainder = amountCash;
        for (Map.Entry<Denomination, Cassette> entry : entries) {
            Cassette cassette = entry.getValue();
            Denomination denomination = entry.getKey();
            int denVal = denomination.getNum();
            int count = remainder / denVal;

            if (cassette.getAvailableBanknote() < count) {
                count = count - cassette.getAvailableBanknote();
                remainder = (remainder % denVal) + (count * denVal);
            } else {
                remainder = remainder % denVal;
            }

            for (int i = 0; i < count; i++) {
                retBanknotes.add(cassette.get());
            }
        }

        return retBanknotes;
    }

    private void checkAvailableDispense(int amountCash) throws AtmException {
        var entries = cassettes.entrySet().stream().filter(entry -> entry.getKey().getNum() <= amountCash).collect(Collectors.toList());

        int remainder = amountCash;
        for (Map.Entry<Denomination, Cassette> entry : entries) {
            if (remainder == 0) break;

            int denomination = entry.getKey().getNum();
            int count = remainder / denomination;
            if (entry.getValue().getAvailableBanknote() < count) {
                remainder = (remainder % denomination) + ((count - entry.getValue().getAvailableBanknote()) * denomination);
            } else {
                remainder = remainder % denomination;
            }
        }

        if (remainder != 0) {
            throw new AtmException("This amount cannot be dispense");
        }
    }

    @Override
    public List<Banknote> acceptCash(List<Banknote> acceptBanknotes) {
        if (acceptBanknotes == null || acceptBanknotes.isEmpty()) {
            throw new IllegalArgumentException();
        }

        Map<Denomination, List<Banknote>> banknoteMap = acceptBanknotes.stream().collect(Collectors.groupingBy(Banknote::getDenomination));

        List<Banknote> retList = new ArrayList<>();

        for (Map.Entry<Denomination, List<Banknote>> entry : banknoteMap.entrySet()) {
            // todo тут зависит от бизнес требований - можно или возвращать такие купюры (как в классической работе банкомата), или вообще ничего не принимать
            if (cassettes.containsKey(entry.getKey())) {
                Cassette cassette = cassettes.get(entry.getKey());
                List<Banknote> banknotes = entry.getValue();

                for (Banknote banknote : banknotes) {
                    if (cassette.isAvailableSpace()) {
                        cassette.put(banknote);
                    } else {
                        retList.add(banknote);
                    }
                }
            } else {
                retList.addAll(entry.getValue());
            }
        }
        return retList;
    }

    @Override
    public String getCashBalance() {
        return cassettes
                .entrySet()
                .stream()
                .map(entry -> String.format("denomination %d : available banknotes %d", entry.getKey().getNum(), entry.getValue().getAvailableBanknote()))
                .collect(Collectors.joining(",\n", "Balance ATM : \n", "."));
    }

    @Override
    public int getAvailableBanknote(Denomination denomination) {
        return Optional.ofNullable(cassettes.get(denomination)).map(Cassette::getAvailableBanknote).orElse(0);
    }
}
