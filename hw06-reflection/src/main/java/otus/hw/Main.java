package otus.hw;

public class Main {

    public static void main(String[] args) {
        Bank rusBank = new Bank();
        rusBank.setRateUsd2Rub(73.46);
        Usd usd = rusBank.exchangeRubOnUsd(new Ruble(100.9));
        System.out.println(usd);
    }
}
