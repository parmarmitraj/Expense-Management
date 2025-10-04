package com.expensemanagement.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.expensemanagement.dao.ExpenseDAO;
import com.expensemanagement.dao.UserDAO;
import com.expensemanagement.model.Expense;
import com.expensemanagement.model.User;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        
        User user = (User) session.getAttribute("user");

        if ("Admin".equals(user.getRole())) {
            UserDAO userDAO = new UserDAO();
            try {
                List<User> userList = userDAO.getUsersByCompanyId(user.getCompanyId());
                List<User> managerList = userDAO.getManagersByCompanyId(user.getCompanyId());
                request.setAttribute("userList", userList);
                request.setAttribute("managerList", managerList);
                request.getRequestDispatcher("admin_dashboard.jsp").forward(request, response);
            } catch (SQLException e) {
                e.printStackTrace();
                request.setAttribute("errorMessage", "Error fetching admin data.");
                request.getRequestDispatcher("error.jsp").forward(request, response);
            }
        } else if ("Manager".equals(user.getRole())) {
            ExpenseDAO expenseDAO = new ExpenseDAO();
            try {
                List<Expense> pendingApprovals = expenseDAO.getPendingExpensesForManager(user.getUserId());
                request.setAttribute("pendingApprovals", pendingApprovals);
                request.getRequestDispatcher("manager_dashboard.jsp").forward(request, response);
            } catch (SQLException e) {
                e.printStackTrace();
                request.setAttribute("errorMessage", "Error fetching manager data.");
                request.getRequestDispatcher("error.jsp").forward(request, response);
            }
        } else { // Employee
            ExpenseDAO expenseDAO = new ExpenseDAO();
            try {
                List<Expense> expenseList = expenseDAO.getExpensesByUserId(user.getUserId());
                request.setAttribute("expenseList", expenseList);
                request.getRequestDispatcher("employee_dashboard.jsp").forward(request, response);
            } catch (SQLException e) {
                e.printStackTrace();
                request.setAttribute("errorMessage", "Error fetching expense data.");
                request.getRequestDispatcher("error.jsp").forward(request, response);
            }
        }
    }
}