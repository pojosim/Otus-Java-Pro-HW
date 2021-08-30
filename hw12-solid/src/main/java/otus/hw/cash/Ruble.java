package otus.hw.cash;

public class Ruble extends Banknote {
    private final RubleNominal nominal;

    public Ruble(RubleNominal nominal) {
        this.nominal = nominal;
    }

    @Override
    public String getCurrencyName() {
        return "Российский Рубль";
    }

    @Override
    public Nominal getNominal() {
        return nominal;
    }

    public enum RubleNominal implements Nominal {
        FIFTY(50),
        ONE_HUNDRED(100),
        TWO_HUNDRED(200),
        FIVE_HUNDRED(500),
        ONE_THOUSAND(1000),
        TWO_THOUSAND(2000),
        FIVE_THOUSAND(5000);

        private final int nominalAmount;

        RubleNominal(int nominalAmount) {
            this.nominalAmount = nominalAmount;
        }

        public int getAmount() {
            return nominalAmount;
        }

        @Override
        @SuppressWarnings("unchecked")
        public <T extends Banknote> T createBanknote() {
            return (T) new Ruble(this);
        }
    }
}
