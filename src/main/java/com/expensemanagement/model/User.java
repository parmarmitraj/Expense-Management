package com.expensemanagement.model;

import java.sql.Timestamp;

public class User {
    private int userId;
    private int companyId;
    private Integer managerId; // Use Integer to allow for null values
    private String email;
    private String passwordHash;
    private String fullName;
    private String role; // "Admin", "Manager", "Employee"
    private Timestamp createdAt;

    // Getters and Setters for all fields...
    // (You can auto-generate these in Eclipse: Source -> Generate Getters and Setters...)
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public int getCompanyId() { return companyId; }
    public void setCompanyId(int companyId) { this.companyId = companyId; }
    public Integer getManagerId() { return managerId; }
    public void setManagerId(Integer managerId) { this.managerId = managerId; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
}