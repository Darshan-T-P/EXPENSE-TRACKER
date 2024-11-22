package expensetracker;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;

public class ExpenseTracker {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ExpenseStorage storage = new ExpenseStorage();

        while (true) {
            System.out.println("\nExpense Tracker Menu:");
            System.out.println("1. Add Personal Expense");
            System.out.println("2. Add Business Expense");
            System.out.println("3. View All Expenses");
            System.out.println("4. Total Expenses");
            System.out.println("5. Set Budget");
            System.out.println("6. Check Budget");
            System.out.println("7. View Expenses by Payment Method");
            System.out.println("8. View Expenses by Category");
            System.out.println("9. Delete Expense by Index");
            System.out.println("0. Exit");

            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> {
                    System.out.println("Enter details for Personal Expense:");
                    String date = getValidatedDate(scanner);
                    System.out.print("Description: ");
                    String description = scanner.nextLine();
                    double amount = getValidatedAmount(scanner);
                    System.out.print("Payment Method: ");
                    String paymentMethod = scanner.nextLine();
                    System.out.print("Category: ");
                    String category = scanner.nextLine();

                    PersonalExpense personalExpense = new PersonalExpense(date, description, amount, paymentMethod, category);
                    storage.addExpense(personalExpense);
                }
                case 2 -> {
                    System.out.println("Enter details for Business Expense:");
                    String date = getValidatedDate(scanner);
                    System.out.print("Description: ");
                    String description = scanner.nextLine();
                    double amount = getValidatedAmount(scanner);
                    System.out.print("Payment Method: ");
                    String paymentMethod = scanner.nextLine();
                    System.out.print("Business Purpose: ");
                    String businessPurpose = scanner.nextLine();

                    BusinessExpense businessExpense = new BusinessExpense(date, description, amount, paymentMethod, businessPurpose);
                    storage.addExpense(businessExpense);
                }
                case 3 -> storage.viewExpenses();
                case 4 -> storage.calculateTotals();
                case 5 -> {
                    // Handle budget setting with validation in ExpenseTracker
                    double budget = getValidBudget(scanner);
                    storage.setBudget(budget);
                }
                case 6 -> storage.checkBudget();
                case 7 -> storage.viewByPaymentMethod();
                case 8 -> storage.viewByCategory();
                case 9 -> {
                    System.out.println("Enter the index of the expense to delete:");
                    int index = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    try {
                        storage.deleteExpenseByIndex(index);
                    } catch (InvalidIndexException e) {
                        System.out.println(e.getMessage());
                    }
                }
                case 0 -> {
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }

    // Method to validate the date input
    private static String getValidatedDate(Scanner scanner) {
        while (true) {
            try {
                System.out.print("Date (YYYY-MM-DD): ");
                String dateInput = scanner.nextLine();
                LocalDate.parse(dateInput); // Validates the date format
                return dateInput; // Return valid date
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please enter the date in YYYY-MM-DD format.");
            }
        }
    }

    // Method to validate the amount input
    private static double getValidatedAmount(Scanner scanner) {
        while (true) {
            try {
                System.out.print("Amount: ");
                double amount = scanner.nextDouble();
                scanner.nextLine(); // Consume newline
                if (amount <= 0) {
                    throw new IllegalArgumentException("Amount must be greater than zero. Please try again.");
                }
                return amount; // Return valid amount
            } catch (InputMismatchException e) {
                System.out.println("Invalid amount. Please enter a valid numeric value.");
                scanner.nextLine(); // Clear invalid input
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage()); // Display custom error message
            }
        }
    }
    // Method to validate and set the budget
    public static double getValidBudget(Scanner scanner) {
        while (true) {
            try {
                System.out.print("Enter your budget amount: ");
                double budget = scanner.nextDouble();
                scanner.nextLine(); // Consume newline

                if (budget<=0) {
                    System.out.println("Budget cannot be negative. Please try again.");
                } else {
                    return budget; // Return valid budget
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid numeric value for the budget.");
                scanner.nextLine(); // Clear invalid input
            }
        }
    }

}
