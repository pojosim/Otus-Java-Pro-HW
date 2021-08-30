package otus.hw;

import otus.hw.cash.Banknote;

public class Bank {

    public <T extends Banknote> Atm<T> createAtm(Class<T> cashType) {
        AtmFactory factory = AtmFactory.getFactory(cashType);
        return new Atm<T>(factory);
    }

}
