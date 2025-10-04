package com.expensemanagement.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.expensemanagement.model.User;
import com.expensemanagement.utility.DBConnectionUtil;

public class UserDAO {

    public void addUser(User user) throws SQLException {
        // MODIFIED: Added manager_id to the INSERT statement
        String sql = "INSERT INTO users (company_id, email, password_hash, full_name, role, manager_id) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, user.getCompanyId());
            pstmt.setString(2, user.getEmail());
            pstmt.setString(3, user.getPasswordHash());
            pstmt.setString(4, user.getFullName());
            pstmt.setString(5, user.getRole());
            
            // MODIFIED: Set the manager_id. setObject handles nulls correctly.
            if (user.getManagerId() != null) {
                pstmt.setInt(6, user.getManagerId());
            } else {
                pstmt.setNull(6, java.sql.Types.INTEGER);
            }
            
            pstmt.executeUpdate();
        }
    }

    public User getUserByEmail(String email) throws SQLException {
        String sql = "SELECT * FROM users WHERE email = ?";
        User user = null;
        
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setCompanyId(rs.getInt("company_id"));
                user.setEmail(rs.getString("email"));
                user.setPasswordHash(rs.getString("password_hash"));
                user.setFullName(rs.getString("full_name"));
                user.setRole(rs.getString("role"));
                user.setManagerId(rs.getObject("manager_id", Integer.class));
            }
        }
        return user;
    }
    
    public List<User> getUsersByCompanyId(int companyId) throws SQLException {
        List<User> userList = new ArrayList<>();
        String sql = "SELECT * FROM users WHERE company_id = ?";
        
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, companyId);
            ResultSet rs = pstmt.executeQuery();
            
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
        }
        return userList;
    }

    // NEW METHOD
    public List<User> getManagersByCompanyId(int companyId) throws SQLException {
        List<User> managerList = new ArrayList<>();
        String sql = "SELECT * FROM users WHERE company_id = ? AND role = 'Manager'";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, companyId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setFullName(rs.getString("full_name"));
                managerList.add(user);
            }
        }
        return managerList;
    }
}