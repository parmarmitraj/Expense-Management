package com.expensemanagement.servlet;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;

import com.expensemanagement.dao.ExpenseDAO;
import com.expensemanagement.model.Expense;
import com.expensemanagement.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/addExpense")
public class AddExpenseServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");

        try {
            Date expenseDate = Date.valueOf(request.getParameter("expenseDate"));
            String category = request.getParameter("category");
            BigDecimal amount = new BigDecimal(request.getParameter("amount"));
            String currency = request.getParameter("currency");
            String description = request.getParameter("description");

            Expense expense = new Expense();
            expense.setUserId(user.getUserId());
            expense.setExpenseDate(expenseDate);
            expense.setCategory(category);
            expense.setAmount(amount);
            expense.setCurrency(currency.toUpperCase());
            expense.setDescription(description);

            ExpenseDAO expenseDAO = new ExpenseDAO();
            expenseDAO.addExpense(expense);
            
            response.sendRedirect("dashboard");

        } catch (SQLException | IllegalArgumentException e) {
            e.printStackTrace();
            // Optionally, redirect to an error page
            response.sendRedirect("employee_dashboard.jsp?error=true");
        }
    }
}