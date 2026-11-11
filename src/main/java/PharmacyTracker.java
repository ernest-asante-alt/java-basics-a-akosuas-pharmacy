
import java.math.BigDecimal;

import pharmacy.model.Customer;
import pharmacy.model.CustomerType;
import pharmacy.model.Medication;
import pharmacy.policy.OTCPolicy;
import pharmacy.policy.PrescriptionPolicy;
import pharmacy.report.PharmacyReport;
import pharmacy.service.Pharmacy;

public class PharmacyTracker {

    public static void main(String[] args) {

        Pharmacy pharmacy = new Pharmacy("Akosua's Pharmacy");

        // Create medications with their reorder levels.
        Medication paracetamol = new Medication(
                "Paracetamol 500mg",
                new BigDecimal("12.50"),
                100,
                30,
                new OTCPolicy()
        );

        Medication amoxicillin = new Medication(
                "Amoxicillin 250mg",
                new BigDecimal("30.00"),
                40,
                40,
                new PrescriptionPolicy()
        );

        pharmacy.addMedication(paracetamol);
        pharmacy.addMedication(amoxicillin);

        // Create customers.
        Customer kofi = new Customer(
                "Kofi Mensah",
                CustomerType.WALK_IN
        );

        Customer ama = new Customer(
                "Ama Owusu",
                CustomerType.REGISTERED
        );

        ama.setPhone("0244 000 000");

        pharmacy.addCustomer(kofi);
        pharmacy.addCustomer(ama);

        // Perform pharmacy operations.
        pharmacy.dispense(kofi, paracetamol, 2, null);

        pharmacy.dispense(
                ama,
                amoxicillin,
                1,
                "Dr. Boateng"
        );

        pharmacy.restock(paracetamol, 50);

        // This request should be rejected because only 39 remain.
        pharmacy.dispense(
                kofi,
                amoxicillin,
                100,
                "Dr. Boateng"
        );

        // Display the end-of-day report.
        PharmacyReport report = new PharmacyReport();
        report.printReport(pharmacy);
    }
}
