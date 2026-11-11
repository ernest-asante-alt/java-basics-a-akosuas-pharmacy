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
    }
}