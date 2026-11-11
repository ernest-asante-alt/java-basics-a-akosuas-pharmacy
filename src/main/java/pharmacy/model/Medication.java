
package pharmacy.model;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

import pharmacy.policy.DispensingPolicy;

public class Medication {

    private static final AtomicInteger COUNTER = new AtomicInteger(1);

    private final int id;
    private final String name;
    private final BigDecimal price;
    private int stock;
    private final int reorderLevel;
    private final DispensingPolicy dispensingPolicy;

    // Original constructor retained for existing code.
    // Uses 10 as the default reorder level.
    public Medication(
            String name,
            BigDecimal price,
            int stock,
            DispensingPolicy dispensingPolicy
    ) {
        this(name, price, stock, 10, dispensingPolicy);
    }

    // New constructor that accepts a custom reorder level.
    public Medication(
            String name,
            BigDecimal price,
            int stock,
            int reorderLevel,
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

        if (reorderLevel < 0) {
            throw new IllegalArgumentException("Reorder level cannot be negative");
        }

        this.id = COUNTER.getAndIncrement();
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.reorderLevel = reorderLevel;
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

    public int getReorderLevel() {
        return reorderLevel;
    }

    public boolean isLowStock() {
        return stock <= reorderLevel;
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
