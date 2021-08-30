package otus.hw.cash;

public abstract class Banknote {
    public abstract Nominal getNominal();

    public abstract String getCurrencyName();

    public interface Nominal {

        int getAmount();

        <T extends Banknote> T createBanknote();
    }
}
