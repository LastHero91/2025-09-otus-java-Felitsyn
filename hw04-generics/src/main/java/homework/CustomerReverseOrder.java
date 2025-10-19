package homework;

import java.util.Deque;
import java.util.LinkedList;

public class CustomerReverseOrder {
    Deque<Customer> stackCustomers = new LinkedList<>();

    public void add(Customer customer) {
        stackCustomers.add(customer);
    }

    public Customer take() {
        return stackCustomers.pollLast();
    }
}
