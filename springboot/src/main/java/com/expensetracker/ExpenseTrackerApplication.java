package com.expensetracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// This is the entry point of the whole backend.
// @SpringBootApplication tells Spring to find all the other classes
// (controllers, services, repositories) and wire them together.
@SpringBootApplication
public class ExpenseTrackerApplication {

    public static void main(String[] args) {
        // This one line starts the embedded web server and the whole app.
        SpringApplication.run(ExpenseTrackerApplication.class, args);
    }
}
