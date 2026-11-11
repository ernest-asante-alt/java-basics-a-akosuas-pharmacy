package pharmacy.policy;

import pharmacy.model.MedicationCategory;

public class PrescriptionPolicy implements DispensingPolicy {

    @Override
    public MedicationCategory getCategory() {
        return MedicationCategory.PRESCRIPTION;
    }

    @Override
    public void validatePrescriber(String prescriber) {
        if (prescriber == null || prescriber.isBlank()) {
            throw new IllegalArgumentException(
                    "A prescriber is required for prescription medication"
            );
        }
    }
}