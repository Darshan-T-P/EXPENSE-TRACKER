package expensetracker;

// Custom exception for invalid index
public class InvalidIndexException extends Exception {
    public InvalidIndexException(String message) {
        super(message);  // Pass the error message to the Exception class
    }
}