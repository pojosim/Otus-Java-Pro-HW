package otus.hw;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Bank {
    Logger logger = Logger.getLogger(Bank.class.getName());

    private BigDecimal rateUsd;
    private BigDecimal countMoneyUsd = new BigDecimal(100);

//    public Bank() {
//        countMoneyUsd = new BigDecimal(100);
//    }
//
//    public Bank(double moneyInBank) {
//        countMoneyUsd = new BigDecimal(moneyInBank);
//    }

    public void setRateUsd2Rub(double rateUsd) {
        this.rateUsd = new BigDecimal(rateUsd);
    }

    public Usd exchangeRubOnUsd(Ruble rubles) {
        BigDecimal sum = rubles.getAmount().divide(rateUsd, 2, RoundingMode.HALF_UP);
        if (sum.compareTo(countMoneyUsd) > 0) {
//            logger.log(Level.WARNING, String.format("Change money error, bank does not have so much money, money in bank = %s, exchange sum = %s",
//                    countMoneyUsd.toString(), sum.toString()));
            throw new BankException("our bank does not have so much money to exchange");
        }

        countMoneyUsd = countMoneyUsd.subtract(sum);
        return new Usd(sum);
    }

}
