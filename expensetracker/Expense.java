package expensetracker;

public interface Expense {
    String getDate();                // Returns the date of the expense
    String getDescription();         // Returns the description of the expense
    double getAmount();              // Returns the amount of the expense
    String getPaymentMethod();       // Returns the payment method of the expense
    String getType();                // Returns the type (e.g., "Personal" or "Business")
}
