package com.expensetracker.service;

import com.expensetracker.model.Expense;
import com.expensetracker.model.ExpenseType;
import com.expensetracker.repository.ExpenseRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

// @Service = Spring creates one copy of this class and shares it with the controller.
// This class contains the business logic: the "rules" of the application.
@Service
public class ExpenseService {

    // The repository is our only way to read and write expenses in the database.
    private final ExpenseRepository expenseRepository;

    // The budget is just a number we remember while the app is running.
    private double budget = 0;

    // Constructor injection: Spring automatically passes the repository here.
    public ExpenseService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    // ---- CRUD (Create, Read, Update, Delete) ------------------------------

    public Expense addExpense(Expense expense) {
        validate(expense);
        return expenseRepository.save(expense);
    }

    public Expense updateExpense(Long id, Expense newDetails) {
        // 1. Find the existing row, or throw an error if it does not exist.
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid ID. No expense found with id: " + id));

        // 2. Check the new values are valid.
        validate(newDetails);

        // 3. Copy the new values onto the existing row.
        expense.setType(newDetails.getType());
        expense.setDate(newDetails.getDate());
        expense.setDescription(newDetails.getDescription());
        expense.setAmount(newDetails.getAmount());
        expense.setPaymentMethod(newDetails.getPaymentMethod());
        expense.setCategory(newDetails.getCategory());
        expense.setBusinessPurpose(newDetails.getBusinessPurpose());

        // 4. save() updates the existing row (because the id is already set).
        return expenseRepository.save(expense);
    }

    public void deleteExpense(Long id) {
        if (!expenseRepository.existsById(id)) {
            throw new IllegalArgumentException("Invalid ID. No expense found with id: " + id);
        }
        expenseRepository.deleteById(id);
    }

    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

    // ---- Reports ----------------------------------------------------------
    // For simplicity we load all expenses and add them up in plain Java.
    // (For a huge database we would let SQL do the sums, but this is easier to read.)

    // Totals for PERSONAL, BUSINESS and an OVERALL total.
    public Map<String, Double> getTotals() {
        Map<String, Double> totals = new LinkedHashMap<>();
        double overall = 0;

        for (Expense expense : expenseRepository.findAll()) {
            // "PERSONAL" or "BUSINESS" is used as the key, the money is added to it.
            totals.put(expense.getType().name(), add(totals, expense.getType().name(), expense.getAmount()));
            overall += expense.getAmount();
        }
        totals.put("OVERALL", overall);
        return totals;
    }

    // How much money was spent with each payment method (Cash, Card, ...).
    public Map<String, Double> getByPaymentMethod() {
        Map<String, Double> result = new LinkedHashMap<>();
        for (Expense expense : expenseRepository.findAll()) {
            result.put(expense.getPaymentMethod(), add(result, expense.getPaymentMethod(), expense.getAmount()));
        }
        return result;
    }

    // How much money was spent in each personal category (Food, Travel, ...).
    public Map<String, Double> getByCategory() {
        Map<String, Double> result = new LinkedHashMap<>();
        for (Expense expense : expenseRepository.findAll()) {
            String category = expense.getCategory();
            if (category != null) {
                result.put(category, add(result, category, expense.getAmount()));
            }
        }
        return result;
    }

    // Small helper: old value + new amount, or just the amount if nothing exists yet.
    private double add(Map<String, Double> map, String key, double amount) {
        return map.getOrDefault(key, 0.0) + amount;
    }

    // ---- Budget -----------------------------------------------------------

    public void setBudget(double budget) {
        if (budget <= 0) {
            throw new IllegalArgumentException("Budget must be greater than zero.");
        }
        this.budget = budget;
    }

    // Tells the frontend if we are over or under the budget.
    public Map<String, Object> checkBudget() {
        double total = 0;
        for (Expense expense : expenseRepository.findAll()) {
            total += expense.getAmount();
        }

        double remaining = budget - total;
        boolean overBudget = remaining < 0;

        Map<String, Object> status = new LinkedHashMap<>();
        status.put("budget", budget);
        status.put("totalExpenses", total);
        status.put("remaining", Math.max(remaining, 0));
        status.put("overBudget", overBudget);
        status.put("exceededBy", overBudget ? Math.abs(remaining) : 0);
        return status;
    }

    // ---- Validation -------------------------------------------------------

    // Checks the business rules before saving.
    private void validate(Expense expense) {
        if (expense.getAmount() <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero.");
        }
        try {
            LocalDate.parse(expense.getDate()); // throws if the format is wrong
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format. Please use YYYY-MM-DD.");
        }
        if (expense.getType() == ExpenseType.PERSONAL
                && (expense.getCategory() == null || expense.getCategory().isBlank())) {
            throw new IllegalArgumentException("Category is required for personal expenses.");
        }
        if (expense.getType() == ExpenseType.BUSINESS
                && (expense.getBusinessPurpose() == null || expense.getBusinessPurpose().isBlank())) {
            throw new IllegalArgumentException("Business purpose is required for business expenses.");
        }
    }
}
