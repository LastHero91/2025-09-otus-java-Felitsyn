package homework;

import java.util.*;

public class CustomerService {
    NavigableMap<Customer, String> sortedCustomers = new TreeMap<>(Comparator.comparingLong(Customer::getScores));

    public Map.Entry<Customer, String> getSmallest() {
        return sortedCustomers.firstEntry();
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        return sortedCustomers.higherEntry(customer);
    }

    public void add(Customer customer, String data) {
        sortedCustomers.put(customer, data);
    }
}
