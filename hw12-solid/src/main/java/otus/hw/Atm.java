package otus.hw;

import otus.hw.exception.AtmException;

import java.util.List;

public interface Atm {

    /**
     * Operation for dispense cash from ATM
     *
     * @param amountCash requested amount
     * @return List banknotes
     * @throws AtmException if there are no banknotes available for dispensing
     */
    List<Banknote> dispenseCash(int amountCash) throws AtmException;

    /**
     * Operation for accept banknotes into ATM
     *
     * @param banknotes list banknotes for replenishment
     * @return banknotes for which there are no cassettes or there is no space left in the cassettes
     */
    List<Banknote> acceptCash(List<Banknote> banknotes);

    /**
     * Return string format balance in ATM
     *
     * @return string balance
     */
    String getCashBalance();

    /**
     * Return count available banknote
     * @param denomination type denomination
     * @return if atm contains type denomination then count else zero
     */
    int getAvailableBanknote(Denomination denomination);

}


