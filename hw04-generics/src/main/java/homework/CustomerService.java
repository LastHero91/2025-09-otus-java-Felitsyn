package homework;

import java.util.*;

public class CustomerService {
    private final NavigableMap<Customer, String> sortedCustomers =
            new TreeMap<>(Comparator.comparingLong(Customer::getScores));

    public Map.Entry<Customer, String> getSmallest() {
        return getSortedCustomersCopy().firstEntry();
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        return getSortedCustomersCopy().higherEntry(customer);
    }

    public void add(Customer customer, String data) {
        sortedCustomers.put(customer, data);
    }

    /** Возвращает глубокую копию sortedCustomers. */
    private NavigableMap<Customer, String> getSortedCustomersCopy() {
        NavigableMap<Customer, String> sortedCustomersCopy = new TreeMap<>(sortedCustomers.comparator());
        sortedCustomers.forEach((k, v) -> sortedCustomersCopy.put(new Customer(k), new String(v)));

        return sortedCustomersCopy;
    }
}
