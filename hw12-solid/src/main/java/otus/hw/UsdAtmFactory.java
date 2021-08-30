package otus.hw;

import otus.hw.cash.Usd;

public class UsdAtmFactory implements AtmFactory<Usd> {

    @Override
    public Cassette<Usd> createCassette(Usd.Nominal nominal, int currentNumberBanknote) {
        return new Cassette.CassetteBuilder<Usd>()
                .setNominal(nominal)
                .fillCassette(currentNumberBanknote)
                .build();
    }
}
