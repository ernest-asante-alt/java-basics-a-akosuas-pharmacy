package pharmacy.policy;

import pharmacy.model.MedicationCategory;

public class ControlledSubstancePolicy implements DispensingPolicy {

    @Override
    public MedicationCategory getCategory() {
        return MedicationCategory.CONTROLLED;
    }

    @Override
    public void validatePrescriber(String prescriber) {
        if (prescriber == null || prescriber.isBlank()) {
            throw new IllegalArgumentException(
                    "A prescriber is required for controlled substances"
            );
        }
    }
}