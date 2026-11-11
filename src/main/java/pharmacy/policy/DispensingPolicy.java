package pharmacy.policy;

import pharmacy.model.MedicationCategory;

public interface DispensingPolicy {

    MedicationCategory getCategory();

    void validatePrescriber(String prescriber);
}