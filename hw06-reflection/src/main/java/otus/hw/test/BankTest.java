package otus.hw.test;

import otus.hw.Bank;
import otus.hw.BankException;
import otus.hw.Ruble;
import otus.hw.Usd;
import otus.hw.test.annotation.After;
import otus.hw.test.annotation.Before;
import otus.hw.test.annotation.Test;

import java.math.BigDecimal;

public class BankTest {
    Bank bank;

    @Before
    public void setUp() {
        bank = new Bank();
        bank.setRateUsd2Rub(72.60);
    }

    @Test
    public void testExchangeUsd() {
        Usd usd = bank.exchangeRubOnUsd(Ruble.create(72.60));
        assert usd.getAmount().compareTo(BigDecimal.ONE) == 0;
    }

    @Test
    public void testExchangeUsdBigAmount() {
        Usd usd = bank.exchangeRubOnUsd(Ruble.create(7187.4));
        assert usd.getAmount().compareTo(new BigDecimal("99")) == 0;
    }

    @Test
    public void testExchangeUsdException() {
        try {
            bank.exchangeRubOnUsd(Ruble.create(10000));
        } catch (Exception e) {
            assert e instanceof BankException;
        }
    }

    @After
    public void tearDown() {
        bank = null;
    }
}
