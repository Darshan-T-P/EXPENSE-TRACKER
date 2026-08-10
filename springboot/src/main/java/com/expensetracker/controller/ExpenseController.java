package com.expensetracker.controller;

import com.expensetracker.model.Expense;
import com.expensetracker.service.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

// @RestController = this class exposes REST endpoints (URLs the frontend calls).
// @RequestMapping("/api") = every endpoint below starts with /api.
@RestController
@RequestMapping("/api")
public class ExpenseController {

    // The controller only receives the HTTP request and hands the work to the service.
    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    // POST /api/expenses -> create a new expense from the JSON body.
    // @Valid runs the @NotBlank/@DecimalMin checks on the Expense object.
    @PostMapping("/expenses")
    @ResponseStatus(HttpStatus.CREATED) // reply with status 201 Created
    public Expense addExpense(@Valid @RequestBody Expense expense) {
        return expenseService.addExpense(expense);
    }

    // GET /api/expenses -> return every expense as a JSON list.
    @GetMapping("/expenses")
    public List<Expense> getAllExpenses() {
        return expenseService.getAllExpenses();
    }

    // GET /api/expenses/totals -> PERSONAL + BUSINESS + OVERALL totals.
    @GetMapping("/expenses/totals")
    public Map<String, Double> getTotals() {
        return expenseService.getTotals();
    }

    // GET /api/expenses/payment-method -> totals grouped by payment method.
    @GetMapping("/expenses/payment-method")
    public Map<String, Double> getByPaymentMethod() {
        return expenseService.getByPaymentMethod();
    }

    // GET /api/expenses/category -> totals grouped by category.
    @GetMapping("/expenses/category")
    public Map<String, Double> getByCategory() {
        return expenseService.getByCategory();
    }

    // PUT /api/expenses/{id} -> update the expense with this id.
    // @PathVariable reads the {id} part of the URL.
    @PutMapping("/expenses/{id}")
    public Expense updateExpense(@PathVariable Long id, @Valid @RequestBody Expense expense) {
        return expenseService.updateExpense(id, expense);
    }

    // DELETE /api/expenses/{id} -> delete the expense with this id.
    @DeleteMapping("/expenses/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // reply with status 204, no body
    public void deleteExpense(@PathVariable Long id) {
        expenseService.deleteExpense(id);
    }

    // PUT /api/budget -> save the budget. Body: {"budget": 1000}
    // We read the body as a simple Map and take the "budget" value out of it.
    @PutMapping("/budget")
    public Map<String, Double> setBudget(@RequestBody Map<String, Double> body) {
        double budget = body.get("budget");
        expenseService.setBudget(budget);
        return Map.of("budget", budget);
    }

    // GET /api/budget/check -> are we over or under the budget?
    @GetMapping("/budget/check")
    public Map<String, Object> checkBudget() {
        return expenseService.checkBudget();
    }
}
