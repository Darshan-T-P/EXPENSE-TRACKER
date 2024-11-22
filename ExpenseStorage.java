package expensetracker;

import java.sql.*;


public class ExpenseStorage {
    private final Connection connection;
    private double budget = 0;

    // Constructor to establish the database connection
    public ExpenseStorage() {
        try {
            this.connection = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to connect to the database: " + e.getMessage());
        }
    }

    // Add an expense to the database
    public synchronized void addExpense(Expense expense) {
        String query = "INSERT INTO expenses (type, date, description, amount, payment_method, category, business_purpose) " +
                       "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, expense instanceof PersonalExpense ? "Personal" : "Business");
            stmt.setString(2, expense.getDate());
            stmt.setString(3, expense.getDescription());
            stmt.setDouble(4, expense.getAmount());
            stmt.setString(5, expense.getPaymentMethod());

            if (expense instanceof PersonalExpense) {
                stmt.setString(6, ((PersonalExpense) expense).getCategory());
                stmt.setNull(7, Types.VARCHAR);
            } else {
                stmt.setNull(6, Types.VARCHAR);
                stmt.setString(7, ((BusinessExpense) expense).getBusinessPurpose());
            }

            stmt.executeUpdate();
            System.out.println("Expense added to the database successfully!");
        } catch (SQLException e) {
            System.out.println("Error adding expense: " + e.getMessage());
        }
    }

    // Set the budget

public synchronized void setBudget(double budget) {
    this.budget = budget;
    System.out.println("Budget set to: $" + budget);
}


    // View all expenses from the database
    public synchronized void viewExpenses() {
        String query = "SELECT * FROM expenses";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            if (!rs.isBeforeFirst()) {
                System.out.println("No expenses recorded.");
                return;
            }

            System.out.println("\n------------------- Expenses --------------------");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id"));
                System.out.println("Type: " + rs.getString("type"));
                System.out.println("Date: " + rs.getDate("date"));
                System.out.println("Description: " + rs.getString("description"));
                System.out.println("Amount: $" + rs.getDouble("amount"));
                System.out.println("Payment Method: " + rs.getString("payment_method"));
                System.out.println("Category: " + rs.getString("category"));
                System.out.println("Business Purpose: " + rs.getString("business_purpose"));
                System.out.println("-------------------------------------------------");
            }
        } catch (SQLException e) {
            System.out.println("Error viewing expenses: " + e.getMessage());
        }
    }

    // Calculate total expenses
    public synchronized void calculateTotals() {
        String query = "SELECT type, SUM(amount) AS total FROM expenses GROUP BY type";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            double personalTotal = 0;
            double businessTotal = 0;

            System.out.println("\n---------------- Expense Totals ----------------");
            while (rs.next()) {
                String type = rs.getString("type");
                double total = rs.getDouble("total");

                if ("Personal".equalsIgnoreCase(type)) {
                    personalTotal = total;
                } else if ("Business".equalsIgnoreCase(type)) {
                    businessTotal = total;
                }

                System.out.println(type + " Expenses Total: $" + total);
            }

            System.out.println("Overall Total: $" + (personalTotal + businessTotal));
            System.out.println("------------------------------------------------\n");

        } catch (SQLException e) {
            System.out.println("Error calculating totals: " + e.getMessage());
        }
    }

    // View expenses grouped by payment method
    public synchronized void viewByPaymentMethod() {
        String query = "SELECT payment_method, SUM(amount) AS total FROM expenses GROUP BY payment_method";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            System.out.println("\n---------- Expenses by Payment Method ----------");
            while (rs.next()) {
                String method = rs.getString("payment_method");
                double total = rs.getDouble("total");
                System.out.println(method + ": $" + total);
            }
            System.out.println("------------------------------------------------\n");
        } catch (SQLException e) {
            System.out.println("Error viewing by payment method: " + e.getMessage());
        }
    }

    // Check budget against total expenses
    public synchronized void checkBudget() {
        String query = "SELECT SUM(amount) AS total FROM expenses";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            if (rs.next()) {
                double total = rs.getDouble("total");

                System.out.println("\n---------------- Budget Overview ----------------");
                System.out.println("Budget: $" + budget);
                System.out.println("Total Expenses: $" + total);

                if (total > budget) {
                    System.out.println("Warning: You have exceeded your budget by $" + (total - budget));
                } else {
                    System.out.println("You are within your budget. Remaining: $" + (budget - total));
                }
                System.out.println("------------------------------------------------\n");
            }

        } catch (SQLException e) {
            System.out.println("Error checking budget: " + e.getMessage());
        }
    }

    // View expenses grouped by category (Personal Expenses only)
    public synchronized void viewByCategory() {
        String query = "SELECT category, SUM(amount) AS total FROM expenses WHERE category IS NOT NULL GROUP BY category";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            System.out.println("\n------------ Expenses by Category ------------");
            while (rs.next()) {
                String category = rs.getString("category");
                double total = rs.getDouble("total");
                System.out.println(category + ": $" + total);
            }
            System.out.println("----------------------------------------------\n");
        } catch (SQLException e) {
            System.out.println("Error viewing by category: " + e.getMessage());
        }
    }

    // Delete expense by ID
// Delete expense by ID and reindex
    public synchronized void deleteExpenseByIndex(int id) throws InvalidIndexException {
        String deleteQuery = "DELETE FROM expenses WHERE id = ?";
        String reindexQuery = "UPDATE expenses SET id = (SELECT ROW_NUMBER() OVER (ORDER BY date, id) FROM expenses temp WHERE temp.id = expenses.id)";
        try (PreparedStatement deleteStmt = connection.prepareStatement(deleteQuery)) {
            deleteStmt.setInt(1, id);
            int rowsAffected = deleteStmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new InvalidIndexException("Invalid ID. No expense found.");
            }

        // Reindex the IDs
            try (Statement reindexStmt = connection.createStatement()) {
                reindexStmt.executeUpdate(reindexQuery);
            }
            System.out.println("Expense deleted and reindexed successfully.");
            } catch (SQLException e) {
                System.out.println("Error deleting or reindexing expense: " + e.getMessage());
        }
    }

}
