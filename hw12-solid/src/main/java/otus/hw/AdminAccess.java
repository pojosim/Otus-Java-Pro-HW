package otus.hw;

import otus.hw.cash.Banknote;

import java.util.Map;

public interface AdminAccess<T extends Banknote> {

    Map<T.Nominal, Integer> getBalanceAtm();

}
