package com.expensemanagement.model;

import java.sql.Timestamp;

public class Company {
    private int companyId;
    private String companyName;
    private String country;
    private String defaultCurrency;
    private Timestamp createdAt;

    // Getters and Setters...
    public int getCompanyId() { return companyId; }
    public void setCompanyId(int companyId) { this.companyId = companyId; }
    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }
    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }
    public String getDefaultCurrency() { return defaultCurrency; }
    public void setDefaultCurrency(String defaultCurrency) { this.defaultCurrency = defaultCurrency; }
    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
}