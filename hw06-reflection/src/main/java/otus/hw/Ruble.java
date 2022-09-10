package otus.hw;

import java.math.BigDecimal;

public class Ruble extends Money {

    public Ruble(BigDecimal amount) {
        super(amount, "Ruble");
    }

    public Ruble(double amount) {
        super(new BigDecimal(amount), "Ruble");
    }

    public static Ruble create(double amount) {
        return new Ruble(new BigDecimal(amount));
    }
}
