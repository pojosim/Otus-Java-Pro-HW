package otus.hw.cash;

public class Usd extends Banknote {
    private final UsdNominal nominal;

    public Usd(UsdNominal nominal) {
        this.nominal = nominal;
    }

    @Override
    public String getCurrencyName() {
        return "Доллар США";
    }

    @Override
    public UsdNominal getNominal() {
        return nominal;
    }

    public enum UsdNominal implements Nominal {
        ONE(1),
        TWO(2),
        FIVE(5),
        TEN(10),
        TWENTY(20),
        FIFTY(50),
        HUNDRED(100);

        private final int nominalAmount;

        UsdNominal(int nominalAmount) {
            this.nominalAmount = nominalAmount;
        }

        public int getAmount() {
            return nominalAmount;
        }

        @Override
        @SuppressWarnings("unchecked")
        public <T extends Banknote> T createBanknote() {
            return (T) new Usd(this);
        }
    }
}
