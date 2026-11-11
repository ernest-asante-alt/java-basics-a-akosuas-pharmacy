# Akosua's Pharmacy — Stock and Dispensing Tracker

## About the Project

This project is a rebuilt version of the original stock and dispensing tracker for **Akosua's Pharmacy**. It was developed as part of the **Java Basics Applied Lab**.

The original application could track medicines, customers, dispensing transactions and stock levels. However, I found problems with how the code was organised and how it handled important business rules.

My goal was to improve the structure of the application, protect important data and make it easier to maintain and extend without unnecessarily changing its existing behaviour.

## What the Application Does

The application allows the pharmacy to:

- Add and manage medications.
- Track available medicine stock.
- Register walk-in and registered customers.
- Dispense OTC and prescription medicines.
- Record the prescriber for prescription medicines.
- Apply a 5% discount to registered customers.
- Restock medications.
- Keep a record of dispensing and restocking transactions.
- Generate a pharmacy stock and transaction report.
- Identify medicines that have reached or fallen below their reorder levels.

The design also allows controlled substances to be introduced with their own dispensing requirements.

## Improvements Made

During my inspection of the original code, I identified several problems that could affect the pharmacy's daily operations.

I made the following improvements:

1. **Protected business data:** I made important fields private and added methods to control how data is accessed and changed.

2. **Stock protection:** The application now checks available stock before dispensing. It rejects a request when the requested quantity is greater than the available stock, preventing negative stock.

3. **Input validation:** I added checks to prevent invalid values such as negative prices, stock quantities and dispensing quantities.

4. **Protected collections:** I prevented external code from directly modifying the pharmacy's internal medication, customer, transaction and customer-history lists.

5. **Improved medication categories:** I replaced category strings with enums and introduced dispensing policies to make it easier to add new medication categories.

6. **Separated responsibilities:** I separated medication, customer, pricing, dispensing and reporting responsibilities into different classes.

7. **Low-stock warnings:** I introduced reorder levels for medications so the pharmacy can identify medicines that need restocking before they run out.

## Project Structure

The application is organised into the following main areas:

```text
src/
└── main/
    └── java/
        ├── PharmacyTracker.java
        └── pharmacy/
            ├── model/
            │   ├── Medication.java
            │   ├── MedicationCategory.java
            │   ├── Customer.java
            │   ├── CustomerType.java
            │   ├── Transaction.java
            │   └── TransactionType.java
            ├── policy/
            │   ├── DispensingPolicy.java
            │   ├── OTCPolicy.java
            │   ├── PrescriptionPolicy.java
            │   └── ControlledSubstancePolicy.java
            ├── service/
            │   ├── Pharmacy.java
            │   └── PricingService.java
            └── report/
                └── PharmacyReport.java
```

### Main Classes and Their Responsibilities

- **PharmacyTracker:** Starts the application and creates the sample medications and customers.
- **Medication:** Stores medication details, price, stock, reorder level and dispensing policy.
- **Customer:** Stores customer details and dispensing history.
- **Transaction:** Represents a dispensing or restocking transaction.
- **Pharmacy:** Manages medications, customers, dispensing, restocking and transactions.
- **PricingService:** Calculates medication costs and registered-customer discounts.
- **PharmacyReport:** Generates stock and transaction reports, including low-stock warnings.
- **DispensingPolicy:** Defines the rules that different medication categories must follow.

## Design Decisions

I used encapsulation to protect important business information. For example, other classes cannot directly change medication stock. They must use the methods provided by the `Medication` class.

I used enums instead of strings for medication categories and customer types. This helps prevent errors caused by inconsistent category names.

I also used a `DispensingPolicy` interface so that each medication category can have its own dispensing rules. This makes it easier to introduce controlled substances in the future without putting every rule inside one large method.

I separated pricing and reporting from the main pharmacy management class so that each class has a clearer responsibility.

I used `BigDecimal` for medication prices because it is more suitable for financial calculations than `double`.

## Behaviour Preservation

I kept the original valid dispensing and restocking behaviour, including:

- OTC medication dispensing.
- Prescription medication dispensing with a prescriber.
- The 5% discount for registered customers.
- Stock updates after successful dispensing and restocking.
- Customer history and transaction recording.

One important change is that the application now rejects dispensing requests when there is insufficient stock. This prevents the pharmacy from recording sales for quantities it does not have.

## Additional Improvement: Low-Stock Warnings

The original application displayed stock quantities but did not have reorder levels or low-stock warnings.

I added reorder levels to medications. When a medication's available stock reaches or falls below its reorder level, the application displays a warning.

I chose this improvement because it helps the pharmacy identify medicines that need restocking before they run out. This can help reduce stock shortages and improve medicine availability for customers.

## Running the Application

The application requires **JDK 21 or later**.

I use **Visual Studio Code with the Java extension from Oracle** to run the application.

To run the project:

1. Open the project folder in Visual Studio Code.
2. Make sure the Java extension is installed and the JDK is configured.
3. Open `PharmacyTracker.java`.
4. Click **Run** above the `main` method.
5. View the application output in the VS Code terminal.

## Conclusion

Through this rebuild, I focused on making the pharmacy application safer, better organised and easier to extend.

The main aim was not simply to make the code look cleaner, but to ensure that the application protects important pharmacy records and follows the business rules when handling medicines, customers and transactions.