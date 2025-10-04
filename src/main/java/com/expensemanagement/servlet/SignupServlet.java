package com.expensemanagement.servlet;

import java.io.IOException;
import java.sql.SQLException;

import com.expensemanagement.dao.CompanyDAO;
import com.expensemanagement.dao.UserDAO;
import com.expensemanagement.model.Company;
import com.expensemanagement.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/signup")
public class SignupServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve form parameters
        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String companyName = request.getParameter("companyName");
        String country = request.getParameter("country");
        String currency = request.getParameter("currency");

        CompanyDAO companyDAO = new CompanyDAO();
        UserDAO userDAO = new UserDAO();

        try {
            // 1. Create and save the new company
            Company newCompany = new Company();
            newCompany.setCompanyName(companyName);
            newCompany.setCountry(country);
            newCompany.setDefaultCurrency(currency);

            // The addCompany method returns the generated company_id
            int companyId = companyDAO.addCompany(newCompany);

            if (companyId != -1) {
                // 2. Create and save the new admin user
                User adminUser = new User();
                adminUser.setCompanyId(companyId);
                adminUser.setFullName(fullName);
                adminUser.setEmail(email);
                
                // DANGER: Storing plain text passwords is a major security risk!
                // In a real application, you MUST hash and salt passwords using a library like BCrypt.
                adminUser.setPasswordHash(password); 
                adminUser.setRole("Admin");

                userDAO.addUser(adminUser);

                // 3. Redirect to the login page on success
                response.sendRedirect("login.jsp");
            } else {
                // Handle error - company not created
                // For simplicity, we'll just redirect to an error page or back to signup
                response.sendRedirect("signup.jsp?error=true");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database errors
            response.sendRedirect("signup.jsp?error=true");
        }
    }
}