package com.expensemanagement.utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionUtil {

    // --- Replace with your own database credentials ---
    private static final String URL = "jdbc:mysql://localhost:3306/expense_management_db";
    private static final String USERNAME = "root"; // e.g., "root"
    private static final String PASSWORD = "mitraj"; // e.g., "password123"
    // ------------------------------------------------

    // Method to get a database connection
    public static Connection getConnection() {
        Connection connection = null;
        try {
            // 1. Register the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // 2. Open a connection
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            
        } catch (ClassNotFoundException e) {
            System.err.println("JDBC Driver not found!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Database connection failed!");
            e.printStackTrace();
        }
        return connection;
    }
}