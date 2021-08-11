package otus.hw;

import com.google.common.collect.ListMultimap;
import com.google.common.collect.MultimapBuilder;

public class HelloOtus {
    public static void main(String[] args) {
        ListMultimap<String, String> multimap = MultimapBuilder
                .hashKeys()
                .arrayListValues()
                .build();

        multimap.put("junior", "zero work experience");
        multimap.put("middle", "work experience > 1 year and <= 3 year");
        multimap.put("senior", "work experience > 3 year");

        multimap.put("junior", ", or <= 1 year");

        multimap.forEach((key, value) -> System.out.println("key: " + key + ", value: " + value));
    }
}
