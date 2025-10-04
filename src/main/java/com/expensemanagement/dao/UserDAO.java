package com.expensemanagement.dao;

import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.expensemanagement.model.User;
import com.expensemanagement.utility.DBConnectionUtil;

public class UserDAO {

    /**
     * Adds a new user to the database.
     * @param user The user object to be added.
     * @throws SQLException
     */
    public void addUser(User user) throws SQLException {
        String sql = "INSERT INTO users (company_id, email, password_hash, full_name, role) VALUES (?, ?, ?, ?, ?)";
        
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DBConnectionUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            
            pstmt.setInt(1, user.getCompanyId());
            pstmt.setString(2, user.getEmail());
            // IMPORTANT: In a real application, you MUST hash the password before storing it.
            // For simplicity here, we are storing it directly, but this is NOT secure.
            pstmt.setString(3, user.getPasswordHash());
            pstmt.setString(4, user.getFullName());
            pstmt.setString(5, user.getRole());
            
            pstmt.executeUpdate();

        } finally {
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        }
    }

    /**
     * Retrieves a user from the database by their email.
     * @param email The email of the user to find.
     * @return A User object if found, otherwise null.
     * @throws SQLException
     */
    public User getUserByEmail(String email) throws SQLException {
        String sql = "SELECT * FROM users WHERE email = ?";
        User user = null;
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnectionUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, email);
            
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setCompanyId(rs.getInt("company_id"));
                user.setEmail(rs.getString("email"));
                user.setPasswordHash(rs.getString("password_hash"));
                user.setFullName(rs.getString("full_name"));
                user.setRole(rs.getString("role"));
                user.setManagerId(rs.getObject("manager_id", Integer.class)); // Handles potential NULL
            }
        } finally {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        }
        
        return user;
    }
    
 // Add this method inside the UserDAO class
    public List<User> getUsersByCompanyId(int companyId) throws SQLException {
        List<User> userList = new ArrayList<>();
        String sql = "SELECT * FROM users WHERE company_id = ?";
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnectionUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, companyId);
            
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setCompanyId(rs.getInt("company_id"));
                user.setEmail(rs.getString("email"));
                user.setFullName(rs.getString("full_name"));
                user.setRole(rs.getString("role"));
                user.setManagerId(rs.getObject("manager_id", Integer.class));
                userList.add(user);
            }
        } finally {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        }
        
        return userList;
    }
}