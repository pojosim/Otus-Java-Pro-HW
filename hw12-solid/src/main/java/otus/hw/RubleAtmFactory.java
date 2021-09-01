package otus.hw;

import otus.hw.cash.Ruble;

public class RubleAtmFactory implements AtmFactory<Ruble> {

    @Override
    public Cassette<Ruble> createCassette(Ruble.Nominal nominal, int currentNumberBanknote) {
        return new Cassette.CassetteBuilder<Ruble>()
                .setNominal(nominal)
                .fillCassette(currentNumberBanknote)
                .build();
    }
}
