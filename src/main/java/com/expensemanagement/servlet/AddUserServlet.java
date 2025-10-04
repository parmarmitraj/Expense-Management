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

@WebServlet("/addUser")
public class AddUserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User adminUser = (User) request.getSession().getAttribute("user");

        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String role = request.getParameter("role");

        User newUser = new User();
        newUser.setCompanyId(adminUser.getCompanyId());
        newUser.setFullName(fullName);
        newUser.setEmail(email);
        newUser.setPasswordHash(password); // REMINDER: HASH THIS IN A REAL APP
        newUser.setRole(role);

        UserDAO userDAO = new UserDAO();
        try {
            userDAO.addUser(newUser);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        response.sendRedirect("dashboard");
    }
}