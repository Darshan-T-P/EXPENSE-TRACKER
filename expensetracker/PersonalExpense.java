package expensetracker;

public class PersonalExpense implements Expense {
    private final String date;
    private final String description;
    private final double amount;
    private final String paymentMethod;
    private final String category;

    public PersonalExpense(String date, String description, double amount, String paymentMethod, String category) {
        this.date = date;
        this.description = description;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.category = category;
    }

    @Override
    public String getDate() {return date; }

    @Override
    public String getDescription() { return description; }

    @Override
    public double getAmount() {return amount; }

    @Override
    public String getPaymentMethod() { return paymentMethod;}

    @Override
    public String getType() { return "Personal";  }
    // Getter method for category
    public String getCategory() {
        return category;
    }
}
