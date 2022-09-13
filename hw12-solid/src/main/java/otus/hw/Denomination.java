package otus.hw;

/**
 * Banknote denomination
 */
public enum Denomination {
    FIFTY(50),
    ONE_HUNDRED(100),
    FIVE_HUNDRED(500),
    ONE_THOUSAND(1000),
    ONE(1); // for testing

    private int num;

    Denomination(int i) {
        this.num = i;
    }

    public int getNum() {
        return num;
    }
}
