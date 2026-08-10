package com.expensetracker.repository;

import com.expensetracker.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

// This interface talks to the database. Spring Data JPA gives us
// all the basic methods for free, so we write ZERO SQL here:
//
//   save(expense)      -> insert a new row OR update an existing one
//   findAll()          -> get every expense as a List<Expense>
//   findById(id)       -> find one expense by its id
//   existsById(id)     -> check whether an expense with that id exists
//   deleteById(id)     -> delete the expense with that id
//
// The first generic <Expense> is the class we store,
// the second <Long> is the type of its id.
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
}
