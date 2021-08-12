package otus.hw;


import java.util.ArrayDeque;

public class CustomerReverseOrder {

    //todo: 2. надо реализовать методы этого класса
    //надо подобрать подходящую структуру данных, тогда решение будет в "две строчки"
    ArrayDeque<Customer> customers = new ArrayDeque<>();

    public void add(Customer customer) {
        customers.offer(customer);
    }

    public Customer take() {
        return customers.pollLast();
    }
}
