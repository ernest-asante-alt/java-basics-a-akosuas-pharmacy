
package pharmacy.report;

import pharmacy.model.Medication;
import pharmacy.service.Pharmacy;

public class PharmacyReport {

    public void printReport(Pharmacy pharmacy) {

        System.out.println();
        System.out.println("=== " + pharmacy.getName() + " - end of day ===");

        System.out.println("Stock levels:");

        for (Medication medication : pharmacy.getMedications()) {
            System.out.println(
                    "  " + medication.getName()
                            + " (" + medication.getCategory() + "): "
                            + medication.getStock() + " in stock"
            );
        }

        System.out.println(
                "Transactions today: "
                        + pharmacy.getTransactions().size()
        );

        // Display low-stock warnings.
        System.out.println();
        System.out.println("=== LOW STOCK ALERTS ===");

        boolean hasLowStock = false;

        for (Medication medication : pharmacy.getMedications()) {

            if (medication.isLowStock()) {
                hasLowStock = true;

                System.out.println(
                        "WARNING: " + medication.getName()
                );

                System.out.println(
                        "  Current stock: " + medication.getStock()
                );

                System.out.println(
                        "  Reorder level: " + medication.getReorderLevel()
                );

                System.out.println(
                        "  Action: Restock recommended"
                );
            }
        }

        if (!hasLowStock) {
            System.out.println("All medicines have sufficient stock.");
        }
    }
}
