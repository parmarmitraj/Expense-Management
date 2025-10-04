package com.expensemanagement.servlet;

import java.io.IOException;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.expensemanagement.dao.ExpenseDAO;

@WebServlet("/approval")
public class ApprovalServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int expenseId = Integer.parseInt(request.getParameter("expenseId"));
            String action = request.getParameter("action"); // "Approved" or "Rejected"

            ExpenseDAO expenseDAO = new ExpenseDAO();
            expenseDAO.updateExpenseStatus(expenseId, action);
            
            response.sendRedirect("dashboard");

        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
            // Redirect to an error page or back to the dashboard with an error message
            response.sendRedirect("dashboard?error=true");
        }
    }
}