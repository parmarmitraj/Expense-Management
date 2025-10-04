package com.expensemanagement.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.expensemanagement.dao.UserDAO;
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
        
        // Double-check session and user to be safe
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        
        User user = (User) session.getAttribute("user");

        if ("Admin".equals(user.getRole())) {
            UserDAO userDAO = new UserDAO();
            try {
                List<User> userList = userDAO.getUsersByCompanyId(user.getCompanyId());
                request.setAttribute("userList", userList);
                request.getRequestDispatcher("admin_dashboard.jsp").forward(request, response);
            } catch (SQLException e) {
                e.printStackTrace(); // This prints the error to your Eclipse console
                
                // --- THIS IS THE FIX ---
                // Tell the browser what to do in case of an error
                request.setAttribute("errorMessage", "Error fetching user data from the database.");
                request.getRequestDispatcher("error.jsp").forward(request, response);
                // ---------------------
            }
        } else if ("Manager".equals(user.getRole())) {
            request.getRequestDispatcher("manager_dashboard.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("employee_dashboard.jsp").forward(request, response);
        }
    }
}