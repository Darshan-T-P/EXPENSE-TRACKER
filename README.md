# **Expense Tracker** 🧾💰

## **Overview** 🌟

The **Expense Tracker** is a robust Java-based application designed to help users manage and categorize expenses effectively.  
Leveraging **MySQL** for persistent data storage and a user-friendly command-line interface, this project offers a streamlined solution for tracking personal and business expenditures.  
It ensures accuracy and scalability with advanced features like budget validation and dynamic expense categorization.

---

## **Features** 🚀

- **Database-Driven Expense Management**:
  - CRUD operations (Create, Read, Update, Delete) for managing expense records.
  - Separate tracking of personal and business expenses.

- **Budget Control** 💸:
  - Prevents overspending through validation logic.
  - Alerts when expenses exceed the set budget limit.

- **Dynamic Operations** 🔄:
  - Add, modify, or delete expense categories and records.
  - Seamlessly integrate updates with the underlying MySQL database.

- **Error Handling** ⚠️:
  - Uses custom exceptions for invalid inputs and budget violations.

- **Comprehensive Reporting** 📊:
  - Provides detailed logs of all financial transactions.
  - Easily extensible for adding summary reports or analytics.

---

## **Technologies Used** 🛠️

- **Programming Language**: Java
- **Database**: MySQL
- **Database Driver**: MySQL Connector/J
- **IDE Used**: VS Code

---

## **Features of the Application** ✨

- **Add Expense** 🏷️:
  - Allows users to enter new expenses with details such as amount, description, date, and type (Personal or Business).

- **View Expenses** 👀:
  - Users can view all recorded expenses with detailed logs, including the date, amount, description, and type.

- **Update Expenses** ✏️:
  - Users can update any expense record by modifying the details such as amount, description, or category.

- **Delete Expenses** 🗑️:
  - Users can remove specific expense records from the system.

- **Total Expense Calculation** 💵:
  - The application calculates the total amount of expenses for both Personal and Business categories.

- **Budget Control** 💰:
  - The system checks if any category (Personal/Business) exceeds the set budget limit and alerts users accordingly.

- **Expense Categorization** 📂:
  - Expenses are categorized into Personal and Business for better tracking and reporting.

- **Generate Expense Summary** 📋:
  - The app can generate an expense summary showing the total expenses for each category.

- **Error Handling** 🛑:
  - Includes custom exceptions to handle invalid inputs, missing data, and budget violations.

---

## **Getting Started** 🏁

### **Prerequisites** 📋

Before running the application, ensure you have the following installed:

1. **Java Development Kit (JDK)** - Version 8 or higher.
2. **MySQL Server** - Installed and running on your machine.
3. **MySQL Connector/J** - Download `mysql-connector-j-9.1.0.jar` and include it in your project directory.
4. **IDE** - Installed and running on VS Code.

---

## **Setup Instructions** 🔧

### **Step 1: Clone the Repository** 🖥️

```bash
git clone https://github.com/Darshan-T-P/EXPENSE-TRACKER.git
cd EXPENSE-TRACKER
```
---
### *Step 2: Set Up the MySQL Database*

1. Open your MySQL CLI or GUI.
2. Create a database for the Expense Tracker:

   ```sql
   CREATE DATABASE expense_tracker;
   ```

2. **Update the database credentials** 🔑:

   ```java
   String url = "jdbc:mysql://localhost:3306/expense_tracker";
   String user = "your_mysql_username";
   String password = "your_mysql_password";
   ```

   ### *Step 3: Install the MySQL Connector🔌*
  - *Download the MySQL Connector/J (mysql-connector-j-9.1.0.jar) and ensure it is included in your project's build path.*
  - Download it  from the official MySQL website:

   - *MySQL Connector/J download page*: [Download MySQL Connector/J](https://dev.mysql.com/downloads/connector/j/)
---

## *Usage Instruction🚀:*
 --To compile the Java source files, run:
 ```java
    javac -cp .;mysql-connector-j-9.1.0.jar expensetracker/*.java
  ```
--To Execute the main class:
  ```java
  java -cp .;mysql-connector-j-9.1.0.jar expensetracker.ExpenseTracker
  ```

---
## **Conclusion🎉**
With this Expense Tracker 💼, you can easily manage and track your personal or business expenses, monitor your budgets 💰, and generate detailed reports 📊. Whether you're an individual looking to manage your spending or a small business owner 🏢, this tool provides a simple and efficient solution.

Enjoy tracking your finances! 💡📈
-----
