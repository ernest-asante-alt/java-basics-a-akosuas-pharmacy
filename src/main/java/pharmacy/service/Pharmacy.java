package pharmacy.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import pharmacy.model.Customer;
import pharmacy.model.Medication;
import pharmacy.model.Transaction;
import pharmacy.model.TransactionType;

public class Pharmacy {

    private static final AtomicInteger TRANSACTION_COUNTER =
            new AtomicInteger(1);

    private final String name;

    private final List<Medication> medications = new ArrayList<>();
    private final List<Customer> customers = new ArrayList<>();
    private final List<Transaction> transactions = new ArrayList<>();

    private final PricingService pricingService;

    public Pharmacy(String name) {
        this(name, new PricingService());
    }

    public Pharmacy(String name, PricingService pricingService) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Pharmacy name cannot be empty");
        }

        if (pricingService == null) {
            throw new IllegalArgumentException("Pricing service cannot be null");
        }

        this.name = name;
        this.pricingService = pricingService;
    }

    public String getName() {
        return name;
    }

    public void addMedication(Medication medication) {
        if (medication == null) {
            throw new IllegalArgumentException("Medication cannot be null");
        }

        if (medications.contains(medication)) {
            throw new IllegalArgumentException(
                    "Medication has already been added"
            );
        }

        medications.add(medication);
    }

    public void addCustomer(Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException("Customer cannot be null");
        }

        if (customers.contains(customer)) {
            throw new IllegalArgumentException(
                    "Customer has already been added"
            );
        }

        customers.add(customer);
    }

    public List<Medication> getMedications() {
        return List.copyOf(medications);
    }

    public List<Customer> getCustomers() {
        return List.copyOf(customers);
    }

    public List<Transaction> getTransactions() {
        return List.copyOf(transactions);
    }

    public boolean dispense(
            Customer customer,
            Medication medication,
            int quantity,
            String prescriber
    ) {
        if (customer == null || medication == null) {
            System.out.println("Cannot dispense: customer or medication is missing");
            return false;
        }

        if (!customers.contains(customer)) {
            System.out.println("Cannot dispense: customer is not registered with this pharmacy");
            return false;
        }

        if (!medications.contains(medication)) {
            System.out.println("Cannot dispense: medication is not held by this pharmacy");
            return false;
        }

        if (quantity <= 0) {
            System.out.println("Cannot dispense: quantity must be greater than zero");
            return false;
        }

        if (quantity > medication.getStock()) {
            System.out.println(
                    "Cannot dispense " + medication.getName()
                            + ": insufficient stock ("
                            + medication.getStock() + " available)"
            );
            return false;
        }

        try {
            medication.getDispensingPolicy()
                    .validatePrescriber(prescriber);
        } catch (IllegalArgumentException exception) {
            System.out.println(
                    "Cannot dispense " + medication.getName()
                            + ": " + exception.getMessage()
            );
            return false;
        }

        // Calculate everything before changing any business records.
        BigDecimal total = pricingService.calculateTotal(
                medication,
                customer,
                quantity
        );

        // Commit the successful dispensing operation.
        medication.deductStock(quantity);

        customer.addHistoryEntry(
                "Got " + quantity + " x " + medication.getName()
        );

        transactions.add(
                new Transaction(
                        TRANSACTION_COUNTER.getAndIncrement(),
                        customer.getName(),
                        medication.getName(),
                        quantity,
                        total,
                        TransactionType.DISPENSE,
                        prescriber
                )
        );

        printDispenseConfirmation(
                customer,
                medication,
                quantity,
                total,
                prescriber
        );

        return true;
    }

    public void restock(Medication medication, int quantity) {
        if (medication == null) {
            throw new IllegalArgumentException("Medication cannot be null");
        }

        if (!medications.contains(medication)) {
            throw new IllegalArgumentException(
                    "Medication is not held by this pharmacy"
            );
        }

        if (quantity <= 0) {
            throw new IllegalArgumentException(
                    "Restock quantity must be greater than zero"
            );
        }

        medication.restock(quantity);

        transactions.add(
                new Transaction(
                        TRANSACTION_COUNTER.getAndIncrement(),
                        "(supplier)",
                        medication.getName(),
                        quantity,
                        BigDecimal.ZERO,
                        TransactionType.RESTOCK,
                        null
                )
        );

        System.out.println(
                "Restocked " + quantity + " x " + medication.getName()
        );
    }

    private void printDispenseConfirmation(
            Customer customer,
            Medication medication,
            int quantity,
            BigDecimal total,
            String prescriber
    ) {
        String message = "Dispensed " + quantity
                + " x " + medication.getName()
                + " to " + customer.getName();

        if (medication.getCategory()
                != pharmacy.model.MedicationCategory.OTC) {
            message += " (prescribed by " + prescriber + ")";
        }

        System.out.println(
                message + " for GHS " + formatMoney(total)
        );
    }

    private String formatMoney(BigDecimal amount) {
        BigDecimal formatted = amount.stripTrailingZeros();

        if (formatted.scale() <= 0) {
            formatted = formatted.setScale(1);
        }

        return formatted.toPlainString();
    }
}