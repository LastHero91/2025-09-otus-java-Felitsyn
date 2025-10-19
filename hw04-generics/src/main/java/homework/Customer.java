package homework;

import java.util.ArrayList;
import java.util.List;

public class Customer {
    private static final List<Customer> CUSTOMERS = new ArrayList<>();
    private final long id;
    private String name;
    private long scores;

    public Customer(long id, String name, long scores) {
        CUSTOMERS.add(this);

        this.id = id;
        this.name = name;
        this.scores = scores;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        CUSTOMERS.stream().filter(customer -> customer.getId() == this.id).forEach(customer -> customer.name = name);
    }

    public long getScores() {
        return scores;
    }

    public void setScores(long scores) {
        CUSTOMERS.forEach(customer -> customer.scores += scores);
    }

    @Override
    public String toString() {
        return "Customer{" + "id=" + id + ", name='" + name + '\'' + ", scores=" + scores + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Customer customer = (Customer) o;

        return this.id == customer.id;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(id);
    }
}
