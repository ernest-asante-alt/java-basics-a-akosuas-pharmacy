package pharmacy.policy;

import pharmacy.model.MedicationCategory;

public class OTCPolicy implements DispensingPolicy {

    @Override
    public MedicationCategory getCategory() {
        return MedicationCategory.OTC;
    }

    @Override
    public void validatePrescriber(String prescriber) {
        // OTC medication does not require a prescriber.
    }
}