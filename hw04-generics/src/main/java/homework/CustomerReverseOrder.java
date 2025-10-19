package homework;

import java.util.Deque;
import java.util.ArrayDeque;

public class CustomerReverseOrder {
    Deque<Customer> stackCustomers = new ArrayDeque<>();

    public void add(Customer customer) {
        stackCustomers.add(customer);
    }

    public Customer take() {
        return stackCustomers.pollLast();
    }
}
