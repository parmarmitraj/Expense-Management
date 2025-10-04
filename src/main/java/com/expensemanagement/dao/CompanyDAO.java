package com.expensemanagement.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.expensemanagement.model.Company;
import com.expensemanagement.utility.DBConnectionUtil;

public class CompanyDAO {

    public int addCompany(Company company) throws SQLException {
        String sql = "INSERT INTO companies (company_name, country, default_currency) VALUES (?, ?, ?)";
        int generatedCompanyId = -1;
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnectionUtil.getConnection();
            // Statement.RETURN_GENERATED_KEYS is used to get the auto-incremented ID back
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            pstmt.setString(1, company.getCompanyName());
            pstmt.setString(2, company.getCountry());
            pstmt.setString(3, company.getDefaultCurrency());
            
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                // Get the generated ID
                rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    generatedCompanyId = rs.getInt(1);
                }
            }
        } finally {
            // Close resources in reverse order of creation
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        }
        
        return generatedCompanyId;
    }
}