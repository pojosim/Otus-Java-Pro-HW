package otus.hw;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import otus.hw.common.Common;
import otus.hw.exception.AtmException;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

class BankTest {

    Atm atm;

    @BeforeEach
    void setUp() {
        atm = new MyAtm(List.of(
                new Cassette(Denomination.FIFTY, 100, Common.generateBanknotes(Denomination.FIFTY, 50)),
                new Cassette(Denomination.ONE_HUNDRED, 100, Common.generateBanknotes(Denomination.ONE_HUNDRED, 50)),
                new Cassette(Denomination.FIVE_HUNDRED, 100, Common.generateBanknotes(Denomination.FIVE_HUNDRED, 50)),
                new Cassette(Denomination.ONE_THOUSAND, 100, Common.generateBanknotes(Denomination.ONE_THOUSAND, 50))
        ));
    }

    @Test
    @DisplayName("Accept one denomination banknote")
    void acceptOneDenominationBanknote() {
        // given
        List<Banknote> banknotes = Common.generateBanknotes(Denomination.FIFTY, 49);

        // when
        List<Banknote> returnBanknotes = atm.acceptCash(banknotes);

        // then
        Assertions.assertThat(returnBanknotes).isEmpty();

        Assertions.assertThat(atm.getAvailableBanknote(Denomination.FIFTY)).isEqualTo(99);
        Assertions.assertThat(atm.getAvailableBanknote(Denomination.ONE_HUNDRED)).isEqualTo(50);
        Assertions.assertThat(atm.getAvailableBanknote(Denomination.FIVE_HUNDRED)).isEqualTo(50);
        Assertions.assertThat(atm.getAvailableBanknote(Denomination.ONE_THOUSAND)).isEqualTo(50);
        Assertions.assertThat(atm.getAvailableBanknote(Denomination.ONE)).isZero();
    }

    @Test
    @DisplayName("Accept many denomination banknote")
    void acceptManyDenominationBanknote() {
        // given
        List<Banknote> banknotes = Common.generateBanknotes(Denomination.FIFTY, 49);
        banknotes.addAll(Common.generateBanknotes(Denomination.ONE_THOUSAND, 20));
        Collections.shuffle(banknotes);

        // when
        List<Banknote> returnBanknotes = atm.acceptCash(banknotes);

        // then
        Assertions.assertThat(returnBanknotes).isEmpty();

        Assertions.assertThat(atm.getAvailableBanknote(Denomination.FIFTY)).isEqualTo(99);
        Assertions.assertThat(atm.getAvailableBanknote(Denomination.ONE_HUNDRED)).isEqualTo(50);
        Assertions.assertThat(atm.getAvailableBanknote(Denomination.FIVE_HUNDRED)).isEqualTo(50);
        Assertions.assertThat(atm.getAvailableBanknote(Denomination.ONE_THOUSAND)).isEqualTo(70);
        Assertions.assertThat(atm.getAvailableBanknote(Denomination.ONE)).isEqualTo(0);
    }

    @Test
    @DisplayName("Dispense banknote")
    void dispenseBanknote() throws AtmException {
        // given
        int cash = 4850;

        // when
        List<Banknote> banknotes = atm.dispenseCash(cash);

        // then
        Assertions.assertThat(banknotes).hasSize(9);

        Assertions.assertThat(atm.getAvailableBanknote(Denomination.FIFTY)).isEqualTo(49);
        Assertions.assertThat(atm.getAvailableBanknote(Denomination.ONE_HUNDRED)).isEqualTo(47);
        Assertions.assertThat(atm.getAvailableBanknote(Denomination.FIVE_HUNDRED)).isEqualTo(49);
        Assertions.assertThat(atm.getAvailableBanknote(Denomination.ONE_THOUSAND)).isEqualTo(46);
    }

    @Test
    @DisplayName("Dispense and after put banknotes")
    void dispenseAndAfterPutBanknotes() throws AtmException {
        // given
        int cash = 4850;

        // when
        List<Banknote> banknotes = atm.dispenseCash(cash);
        List<Banknote> returnBanknotes = atm.acceptCash(banknotes);

        // then
        Assertions.assertThat(returnBanknotes).isEmpty();

        Assertions.assertThat(atm.getAvailableBanknote(Denomination.FIFTY)).isEqualTo(50);
        Assertions.assertThat(atm.getAvailableBanknote(Denomination.ONE_HUNDRED)).isEqualTo(50);
        Assertions.assertThat(atm.getAvailableBanknote(Denomination.FIVE_HUNDRED)).isEqualTo(50);
        Assertions.assertThat(atm.getAvailableBanknote(Denomination.ONE_THOUSAND)).isEqualTo(50);
    }

    @Test
    @DisplayName("Should Throw Exception when ask dispense wrong amount")
    void shouldThrowExceptionWhenWrongAmount() throws AtmException {
        // given
        int cash = 11;

        // when
        // then
        Assertions
                .assertThatThrownBy(() -> atm.dispenseCash(cash))
                .isInstanceOf(AtmException.class)
                .hasMessage("This amount cannot be dispense");

        Assertions.assertThat(atm.getAvailableBanknote(Denomination.FIFTY)).isEqualTo(50);
        Assertions.assertThat(atm.getAvailableBanknote(Denomination.ONE_HUNDRED)).isEqualTo(50);
        Assertions.assertThat(atm.getAvailableBanknote(Denomination.FIVE_HUNDRED)).isEqualTo(50);
        Assertions.assertThat(atm.getAvailableBanknote(Denomination.ONE_THOUSAND)).isEqualTo(50);
    }

    @Test
    @DisplayName("Should return extra banknotes that did not fit into the cassettes")
    void shouldReturnExtraBanknotes() throws AtmException {
        // given
        List<Banknote> banknotes = Common.generateBanknotes(Denomination.ONE_THOUSAND, 53);
        banknotes.addAll(Common.generateBanknotes(Denomination.ONE_HUNDRED, 55));
        Collections.shuffle(banknotes);

        // when
        List<Banknote> returnBanknotes = atm.acceptCash(banknotes);

        // then
        Function<Denomination, Condition<Banknote>> conditional = denomination ->
                new Condition<>(banknote -> banknote.getDenomination() == denomination, "");

        Assertions.assertThat(returnBanknotes).hasSize(8)
                .haveExactly(3, conditional.apply(Denomination.ONE_THOUSAND))
                .haveExactly(5, conditional.apply(Denomination.ONE_HUNDRED));

        Assertions.assertThat(atm.getAvailableBanknote(Denomination.FIFTY)).isEqualTo(50);
        Assertions.assertThat(atm.getAvailableBanknote(Denomination.ONE_HUNDRED)).isEqualTo(100);
        Assertions.assertThat(atm.getAvailableBanknote(Denomination.FIVE_HUNDRED)).isEqualTo(50);
        Assertions.assertThat(atm.getAvailableBanknote(Denomination.ONE_THOUSAND)).isEqualTo(100);
    }

}