package pharmacy.service;

import java.math.BigDecimal;
import java.math.RoundingMode;

import pharmacy.model.Customer;
import pharmacy.model.CustomerType;
import pharmacy.model.Medication;

public class PricingService {

    private static final BigDecimal REGISTERED_DISCOUNT =
            new BigDecimal("0.05");

    public BigDecimal calculateTotal(
            Medication medication,
            Customer customer,
            int quantity
    ) {
        if (quantity <= 0) {
            throw new IllegalArgumentException(
                    "Quantity must be greater than zero"
            );
        }

        BigDecimal total = medication.getPrice()
                .multiply(BigDecimal.valueOf(quantity));

        if (customer.getType() == CustomerType.REGISTERED) {
            BigDecimal discount = total.multiply(REGISTERED_DISCOUNT);
            total = total.subtract(discount);
        }

        return total.setScale(2, RoundingMode.HALF_UP);
    }
}