package otus.hw;

import otus.hw.cash.Banknote;
import otus.hw.cash.Ruble;
import otus.hw.cash.Usd;

public interface AtmFactory<T extends Banknote> {

    static AtmFactory<? extends Banknote> getFactory(Class<? extends Banknote> cashType) {
        if (cashType.equals(Ruble.class))
            return new RubleAtmFactory();
        else if (cashType.equals(Usd.class))
            return new UsdAtmFactory();
        else
            throw new UnsupportedOperationException("Error, this cash type: \"" + cashType.getSimpleName() + "\" is not unsupported");
    }

    Cassette<T> createCassette(T.Nominal nominal, int currentNumberBanknote);

}
