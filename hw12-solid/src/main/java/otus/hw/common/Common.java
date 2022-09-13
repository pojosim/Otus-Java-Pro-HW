package otus.hw.common;

import otus.hw.Banknote;
import otus.hw.Denomination;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Common {

    public static List<Banknote> generateBanknotes(Denomination denomination, int count) {
        return IntStream.rangeClosed(1, count).mapToObj(value -> new Banknote(denomination)).collect(Collectors.toList());
    }

}
