package com.expensemanagement.filter;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

// This filter will intercept any requests made to "dashboard.jsp"
@WebFilter("/dashboard")
public class AuthenticationFilter implements Filter {

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        
        // Get the session, don't create a new one.
        HttpSession session = httpRequest.getSession(false);
        
        // Check if user is logged in (session exists and has a "user" attribute)
        if (session != null && session.getAttribute("user") != null) {
            // User is logged in, so let the request continue to the dashboard.
            chain.doFilter(request, response);
        } else {
            // User is not logged in, redirect them to the login page.
            httpResponse.sendRedirect("login.jsp");
        }
    }
    
    // You can leave the init() and destroy() methods as they are.
}