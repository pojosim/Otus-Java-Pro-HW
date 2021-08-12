package otus.hw;


import java.util.*;

public class CustomerService {

    //todo: 3. надо реализовать методы этого класса
    //важно подобрать подходящую Map-у, посмотрите на редко используемые методы, они тут полезны
    TreeMap<Customer, String> map = new TreeMap<>(Comparator.comparing(Customer::getScores));

    public Map.Entry<Customer, String> getSmallest() {
        //Возможно, чтобы реализовать этот метод, потребуется посмотреть как Map.Entry сделан в jdk
        return cloneEntry(map.firstEntry());
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
//        a key-value mapping associated with the least key strictly greater than the given key, or null if there is no such key.
        return Optional.ofNullable(map.higherEntry(customer)).map(this::cloneEntry).orElse(null);
    }

    public void add(Customer customer, String data) {
        map.putIfAbsent(customer, data);
    }

    private Map.Entry<Customer, String> cloneEntry(Map.Entry<Customer, String> entry) {
        return new AbstractMap.SimpleEntry<>(entry.getKey().clone(), entry.getValue());
    }
}
