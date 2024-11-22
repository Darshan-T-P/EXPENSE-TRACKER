package expensetracker;


// Custom exception for invalid budget
public class InvalidBudgetException extends Exception {
    public InvalidBudgetException(String message) {
        super(message);  // Pass the error message to the Exception class
    }
}