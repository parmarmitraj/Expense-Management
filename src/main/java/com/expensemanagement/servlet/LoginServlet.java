package com.expensemanagement.servlet;

import java.io.IOException;
import java.sql.SQLException;

import com.expensemanagement.dao.UserDAO;
import com.expensemanagement.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        UserDAO userDAO = new UserDAO();
        User user = null;

        try {
            user = userDAO.getUserByEmail(email);
        } catch (SQLException e) {
            e.printStackTrace();
            // Redirect to login page with a generic error
            response.sendRedirect("login.jsp?error=true");
            return;
        }
        
        // --- Authentication Logic ---
        // IMPORTANT: This is a plain text password comparison.
        // This is INSECURE and for demonstration purposes only.
        // In a real application, you would use a library like BCrypt to compare
        // the submitted password with the hashed password from the database.
        // e.g., if (user != null && BCrypt.checkpw(password, user.getPasswordHash()))
        
        if (user != null && user.getPasswordHash().equals(password)) {
            // Authentication successful
            
            // 1. Create a session
            HttpSession session = request.getSession();
            
            // 2. Store user information in the session
            session.setAttribute("user", user);
            
            // 3. Redirect to the dashboard
            response.sendRedirect("dashboard");
            
        } else {
            // Authentication failed
            response.sendRedirect("login.jsp?error=true");
        }
    }
}