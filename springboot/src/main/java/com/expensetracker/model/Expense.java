package com.expensetracker.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

// @Entity means: this class is stored in the database.
// Each field below becomes a column in the "expenses" table,
// and each object we save becomes one row.
@Entity
@Table(name = "expenses")
public class Expense {

    // Primary key. IDENTITY = the database picks the next number automatically.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // PERSONAL or BUSINESS
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ExpenseType type;

    // The "YYYY-MM-DD" date the expense was made.
    // @NotBlank = the frontend must send this, it cannot be empty.
    @NotBlank(message = "date is required (YYYY-MM-DD)")
    @Column(nullable = false)
    private String date;

    @NotBlank(message = "description is required")
    @Column(nullable = false)
    private String description;

    // @DecimalMin = the amount must be at least 0.01.
    @NotNull(message = "amount is required")
    @DecimalMin(value = "0.01", message = "amount must be greater than zero")
    @Column(nullable = false)
    private Double amount;

    @NotBlank(message = "paymentMethod is required")
    @Column(name = "payment_method", nullable = false)
    private String paymentMethod;

    // Only filled in for PERSONAL expenses.
    @Column
    private String category;

    // Only filled in for BUSINESS expenses.
    @Column(name = "business_purpose")
    private String businessPurpose;

    // ---- Getters and setters ---------------------------------------------
    // These let other classes read and change the fields.
    // Spring needs them to convert JSON <-> Java and to save data.

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ExpenseType getType() {
        return type;
    }

    public void setType(ExpenseType type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBusinessPurpose() {
        return businessPurpose;
    }

    public void setBusinessPurpose(String businessPurpose) {
        this.businessPurpose = businessPurpose;
    }
}
