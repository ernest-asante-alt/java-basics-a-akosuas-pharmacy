package pharmacy.model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Customer {

    private static final AtomicInteger COUNTER = new AtomicInteger(1);

    private final int id;
    private final String name;
    private final CustomerType type;
    private String phone;

    private final List<String> history = new ArrayList<>();

    public Customer(String name, CustomerType type) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Customer name cannot be empty");
        }

        if (type == null) {
            throw new IllegalArgumentException("Customer type cannot be null");
        }

        this.id = COUNTER.getAndIncrement();
        this.name = name;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public CustomerType getType() {
        return type;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<String> getHistory() {
        return List.copyOf(history);
    }

    public void addHistoryEntry(String entry) {
        if (entry == null || entry.isBlank()) {
            throw new IllegalArgumentException("History entry cannot be empty");
        }

        history.add(entry);
    }
}