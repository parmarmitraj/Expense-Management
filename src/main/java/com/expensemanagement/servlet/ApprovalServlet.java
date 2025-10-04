package com.expensemanagement.servlet;

import java.io.IOException;
import java.sql.SQLException;
import com.expensemanagement.model.User;
import com.expensemanagement.service.ApprovalService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/approval")
public class ApprovalServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int expenseId = Integer.parseInt(request.getParameter("expenseId"));
            String action = request.getParameter("action"); // "Approved" or "Rejected"
            User approver = (User) request.getSession().getAttribute("user");

            if (approver == null) {
                response.sendRedirect("login.jsp");
                return;
            }

            ApprovalService approvalService = new ApprovalService();
            approvalService.processApproval(expenseId, approver.getUserId(), action);
            
            response.sendRedirect("dashboard");

        } catch (NumberFormatException | SQLException e) {
            e.printStackTrace();
            // Redirect to an error page
            response.sendRedirect("dashboard?error=true");
        }
    }
}