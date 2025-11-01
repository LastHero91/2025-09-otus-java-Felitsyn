package homework;

import java.util.*;

public class CustomerService {
    private final NavigableMap<Customer, String> sortedCustomerMap =
            new TreeMap<>(Comparator.comparingLong(Customer::getScores));

    public Map.Entry<Customer, String> getSmallest() {
        return getCustomerEntryCopy(sortedCustomerMap.firstEntry());
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        return getCustomerEntryCopy(sortedCustomerMap.higherEntry(customer));
    }

    public void add(Customer customer, String data) {
        sortedCustomerMap.put(customer, data);
    }

    /** Возвращает Map.Entry<Customer, String> с глубоким копированием Customer */
    private Map.Entry<Customer, String> getCustomerEntryCopy(Map.Entry<Customer, String> entry) {
        if (entry == null) return null;

        NavigableMap<Customer, String> singleCustomerMap = new TreeMap<>(sortedCustomerMap.comparator());
        singleCustomerMap.put(new Customer(entry.getKey()), entry.getValue());
        return singleCustomerMap.firstEntry();
    }
}
