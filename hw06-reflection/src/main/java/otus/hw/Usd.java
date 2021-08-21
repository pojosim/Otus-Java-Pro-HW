package otus.hw;

import java.math.BigDecimal;

public class Usd extends Money {

    public Usd(BigDecimal amount) {
        super(amount, "USD");
    }

    public Usd(double amount) {
        super(new BigDecimal(amount), "USD");
    }
}
