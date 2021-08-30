package otus.hw;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import otus.hw.cash.Ruble;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class BankTest {
    Bank bank;
    Atm<Ruble> atm;

    private Map<Ruble.Nominal, Integer> getPrivilegeBalanceAtm() {
        AdminAccess<Ruble> adminAccess = atm.getAdminAccess();
        Map<Ruble.Nominal, Integer> balanceAtm = adminAccess.getBalanceAtm();
        return balanceAtm;
    }

    @BeforeEach
    void setUp() {
        bank = new Bank();
        atm = bank.createAtm(Ruble.class);
        atm.createCassette(Ruble.RubleNominal.ONE_HUNDRED, 100);
        atm.createCassette(Ruble.RubleNominal.FIVE_HUNDRED, 100);
        atm.createCassette(Ruble.RubleNominal.ONE_THOUSAND, 100);
        atm.createCassette(Ruble.RubleNominal.FIVE_THOUSAND, 100);
    }

    @Test
    void shouldPutCashIntoAtm() {
        atm.putCash(List.of(new Ruble(Ruble.RubleNominal.ONE_THOUSAND),
                new Ruble(Ruble.RubleNominal.FIVE_HUNDRED),
                new Ruble(Ruble.RubleNominal.FIVE_HUNDRED)));

        Map<Ruble.Nominal, Integer> balanceAtm = getPrivilegeBalanceAtm();

        Assertions.assertThat(balanceAtm.get(Ruble.RubleNominal.ONE_THOUSAND)).isEqualTo(101);
        Assertions.assertThat(balanceAtm.get(Ruble.RubleNominal.FIVE_HUNDRED)).isEqualTo(102);
    }

    @Test
    void shouldGetCashFromAtm() {
        List<Ruble> cash = atm.getCash(15_500);
        Map<Ruble.Nominal, Integer> cashGrouping = cash.stream().collect(Collectors.groupingBy(Ruble::getNominal,
                Collectors.collectingAndThen(Collectors.toList(), List::size)));

        // проверка что банкомат выдал минимальными купюрами
        Assertions.assertThat(cashGrouping.get(Ruble.RubleNominal.ONE_HUNDRED)).isEqualTo(100);
        Assertions.assertThat(cashGrouping.get(Ruble.RubleNominal.FIVE_HUNDRED)).isEqualTo(11);

        // проверка что выдал не больше не меньше
        Integer countBanknotes = cashGrouping.values().stream().reduce(Integer::sum).orElse(0);
        Assertions.assertThat(countBanknotes).isEqualTo(111);

        // проверка что в банкомате уменьшилась сумма
        Map<Ruble.Nominal, Integer> balanceAtm = getPrivilegeBalanceAtm();
        Assertions.assertThat(balanceAtm.get(Ruble.RubleNominal.ONE_HUNDRED)).isEqualTo(0);
        Assertions.assertThat(balanceAtm.get(Ruble.RubleNominal.FIVE_HUNDRED)).isEqualTo(89);
    }

    @Test
    void shouldGetAllCashFromAtm() {
        List<Ruble> allCash = atm.getAllCash();

        // проверка что количество купюр которое было в банкомате
        Assertions.assertThat(allCash.size()).isEqualTo(400);

        // проверка что все номиналы соответствуют
        allCash.stream().collect(Collectors.groupingBy(Ruble::getNominal, Collectors.toList()))
                .values().forEach(list -> Assertions.assertThat(list.size()).isEqualTo(100));

        // проверка что в банкомате все пустые кассеты
        getPrivilegeBalanceAtm().values()
                .forEach(count -> Assertions.assertThat(count).isEqualTo(0));
    }

    @Test
    void shouldThrowExceptionWhenNoMatchingCassette() {
        Assertions.assertThatExceptionOfType(RuntimeException.class).isThrownBy(() -> atm.getCash(15_345));
    }

}