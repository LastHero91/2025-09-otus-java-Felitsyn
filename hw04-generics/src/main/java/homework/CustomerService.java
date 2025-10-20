package homework;

import java.util.*;

public class CustomerService {
    private static final Set<Customer> CUSTOMERS = new HashSet<>();
    private final Comparator comparator = Comparator.comparingLong(Customer::getScores);
    private final NavigableMap<Customer, String> sortedCustomers = new TreeMap<>(comparator);
    private final NavigableMap<Customer, String> copySortedCustomers = new TreeMap<>(comparator);

    public static void putNewCustomer(Customer customer) {
        CUSTOMERS.add(customer);
    }

    public Map.Entry<Customer, String> getSmallest() {
        Map.Entry<Customer, String> entry = sortedCustomers.firstEntry();

        copySortedCustomers.clear();
        CUSTOMERS.stream()
                .filter(customer -> customer.equals(entry.getKey()))
                .forEach(k -> copySortedCustomers.put(k, entry.getValue()));

        return copySortedCustomers.firstEntry();
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        copySortedCustomers.clear();
        sortedCustomers
                .forEach((k, v) -> copySortedCustomers.put(new Customer(k), v));

        return copySortedCustomers.higherEntry(customer);
    }

    public void add(Customer customer, String data) {
        sortedCustomers.put(customer, data);
    }
}
