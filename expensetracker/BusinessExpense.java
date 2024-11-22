package expensetracker;

public class BusinessExpense implements Expense {
    private final String date;
    private final String description;
    private final double amount;
    private final String paymentMethod;
    private final String businessPurpose;

    public BusinessExpense(String date, String description, double amount, String paymentMethod, String businessPurpose) {
        this.date = date;
        this.description = description;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.businessPurpose = businessPurpose;
    }

    @Override
    public String getDate() {
        return date;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public double getAmount() {
        return amount;
    }

    @Override
    public String getPaymentMethod() {
        return paymentMethod;
    }

    @Override
    public String getType() {
        return "Business";
    }

    // Getter method for businessPurpose
    public String getBusinessPurpose() {
        return businessPurpose;
    }
}
