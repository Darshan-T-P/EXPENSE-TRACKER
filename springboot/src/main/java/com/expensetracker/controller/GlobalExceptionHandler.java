package com.expensetracker.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.Map;

// @RestControllerAdvice = when ANY endpoint throws an exception,
// the matching method below turns it into a clean JSON error message
// instead of an ugly server error.
@RestControllerAdvice
public class GlobalExceptionHandler {

    // Called when the service throws IllegalArgumentException,
    // e.g. "Amount must be greater than zero." or "Invalid ID ...".
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST) // reply with status 400
    public Map<String, String> handleBadRequest(IllegalArgumentException e) {
        return Map.of("error", e.getMessage());
    }

    // Called when the @Valid checks on Expense fail (missing or empty field).
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST) // reply with status 400
    public Map<String, String> handleValidation(MethodArgumentNotValidException e) {
        // Collect every field that failed and its message, e.g. {"amount": "amount must be greater than zero"}
        Map<String, String> errors = new LinkedHashMap<>();
        e.getBindingResult().getFieldErrors().forEach(fe -> errors.put(fe.getField(), fe.getDefaultMessage()));
        return errors;
    }
}
