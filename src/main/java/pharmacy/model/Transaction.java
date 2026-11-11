package pharmacy.model;

import java.math.BigDecimal;

public record Transaction(
        int id,
        String customerName,
        String medicationName,
        int quantity,
        BigDecimal total,
        TransactionType type,
        String prescriber
) {
    public Transaction {
        if (id <= 0) {
            throw new IllegalArgumentException("Transaction ID must be positive");
        }

        if (customerName == null || customerName.isBlank()) {
            throw new IllegalArgumentException("Customer name cannot be empty");
        }

        if (medicationName == null || medicationName.isBlank()) {
            throw new IllegalArgumentException("Medication name cannot be empty");
        }

        if (quantity <= 0) {
            throw new IllegalArgumentException("Transaction quantity must be positive");
        }

        if (total == null || total.signum() < 0) {
            throw new IllegalArgumentException("Transaction total cannot be negative");
        }

        if (type == null) {
            throw new IllegalArgumentException("Transaction type cannot be null");
        }
    }
}