package pharmacy.model;

import pharmacy.policy.DispensingPolicy;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class Medication {

    private static final AtomicInteger COUNTER = new AtomicInteger(1);

    private final int id;
    private final String name;
    private final BigDecimal price;
    private int stock;
    private final DispensingPolicy dispensingPolicy;

    public Medication(
            String name,
            BigDecimal price,
            int stock,
            DispensingPolicy dispensingPolicy
    ) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Medication name cannot be empty");
        }

        Objects.requireNonNull(price, "Price cannot be null");
        Objects.requireNonNull(dispensingPolicy, "Dispensing policy cannot be null");

        if (price.signum() < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }

        if (stock < 0) {
            throw new IllegalArgumentException("Stock cannot be negative");
        }

        this.id = COUNTER.getAndIncrement();
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.dispensingPolicy = dispensingPolicy;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public MedicationCategory getCategory() {
        return dispensingPolicy.getCategory();
    }

    public DispensingPolicy getDispensingPolicy() {
        return dispensingPolicy;
    }

    public void deductStock(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }

        if (quantity > stock) {
            throw new IllegalArgumentException("Insufficient stock");
        }

        stock -= quantity;
    }

    public void restock(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Restock quantity must be greater than zero");
        }

        stock = Math.addExact(stock, quantity);
    }
}