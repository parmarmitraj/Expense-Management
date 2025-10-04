package com.expensemanagement.model;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

public class Expense {
    private int expenseId;
    private int userId;
    private String category;
    private BigDecimal amount;
    private String currency;
    private String description;
    private Date expenseDate;
    private String status;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    
    // --- NEW FIELDS ---
    private Integer flowId; // Can be null
    private String employeeName; // For manager view

    // Getters and Setters for all fields...
    
    public int getExpenseId() { return expenseId; }
    public void setExpenseId(int expenseId) { this.expenseId = expenseId; }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Date getExpenseDate() { return expenseDate; }
    public void setExpenseDate(Date expenseDate) { this.expenseDate = expenseDate; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
    public Timestamp getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Timestamp updatedAt) { this.updatedAt = updatedAt; }

    // --- GETTERS AND SETTERS FOR NEW FIELDS ---
    public Integer getFlowId() { return flowId; }
    public void setFlowId(Integer flowId) { this.flowId = flowId; }
    public String getEmployeeName() { return employeeName; }
    public void setEmployeeName(String employeeName) { this.employeeName = employeeName; }
}