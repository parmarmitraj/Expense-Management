package com.expensemanagement.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import com.expensemanagement.dao.ExpenseDAO;
import com.expensemanagement.dao.UserDAO;
import com.expensemanagement.model.Expense;
import com.expensemanagement.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        if ("Admin".equals(user.getRole())) {
            // ... (Admin logic remains the same)
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
            // --- UPDATED LOGIC FOR MANAGERS ---
            ExpenseDAO expenseDAO = new ExpenseDAO();
            try {
                // Get expenses where this manager is an approver and hasn't voted yet
                List<Expense> pendingApprovals = expenseDAO.getExpensesAwaitingApprovalBy(user.getUserId());
                request.setAttribute("pendingApprovals", pendingApprovals);
                request.getRequestDispatcher("manager_dashboard.jsp").forward(request, response);
            } catch (SQLException e) {
                e.printStackTrace();
                request.setAttribute("errorMessage", "Error fetching manager data.");
                request.getRequestDispatcher("error.jsp").forward(request, response);
            }
        } else { // Employee
            // ... (Employee logic remains the same)
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