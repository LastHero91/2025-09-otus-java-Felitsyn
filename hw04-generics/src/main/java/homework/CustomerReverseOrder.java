package homework;

import java.util.Deque;
import java.util.ArrayDeque;

public class CustomerReverseOrder {
    private final Deque<Customer> stackCustomers = new ArrayDeque<>();

    public void add(Customer customer) {
        stackCustomers.push(customer);
    }

    public Customer take() {
        return stackCustomers.pop();
    }
}
