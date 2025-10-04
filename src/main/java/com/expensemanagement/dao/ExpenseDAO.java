package com.expensemanagement.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.expensemanagement.model.Expense;
import com.expensemanagement.utility.DBConnectionUtil;

public class ExpenseDAO {

    public void addExpense(Expense expense) throws SQLException {
        String sql = "INSERT INTO expenses (user_id, category, amount, currency, description, expense_date, status) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, expense.getUserId());
            pstmt.setString(2, expense.getCategory());
            pstmt.setBigDecimal(3, expense.getAmount());
            pstmt.setString(4, expense.getCurrency());
            pstmt.setString(5, expense.getDescription());
            pstmt.setDate(6, expense.getExpenseDate());
            pstmt.setString(7, "Pending");

            pstmt.executeUpdate();
        }
    }

    public List<Expense> getExpensesByUserId(int userId) throws SQLException {
        List<Expense> expenseList = new ArrayList<>();
        String sql = "SELECT * FROM expenses WHERE user_id = ? ORDER BY expense_date DESC";
        
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Expense expense = new Expense();
                expense.setExpenseId(rs.getInt("expense_id"));
                expense.setUserId(rs.getInt("user_id"));
                expense.setCategory(rs.getString("category"));
                expense.setAmount(rs.getBigDecimal("amount"));
                expense.setCurrency(rs.getString("currency"));
                expense.setDescription(rs.getString("description"));
                expense.setExpenseDate(rs.getDate("expense_date"));
                expense.setStatus(rs.getString("status"));
                expense.setCreatedAt(rs.getTimestamp("created_at"));
                expenseList.add(expense);
            }
        }
        return expenseList;
    }

    // NEW METHOD
    public List<Expense> getPendingExpensesForManager(int managerId) throws SQLException {
        List<Expense> expenseList = new ArrayList<>();
        String sql = "SELECT e.*, u.full_name FROM expenses e " +
                     "JOIN users u ON e.user_id = u.user_id " +
                     "WHERE u.manager_id = ? AND e.status = 'Pending' ORDER BY e.expense_date ASC";

        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, managerId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Expense expense = new Expense();
                expense.setExpenseId(rs.getInt("expense_id"));
                expense.setUserId(rs.getInt("user_id"));
                expense.setCategory(rs.getString("category"));
                expense.setAmount(rs.getBigDecimal("amount"));
                expense.setCurrency(rs.getString("currency"));
                expense.setDescription(rs.getString("description"));
                expense.setExpenseDate(rs.getDate("expense_date"));
                expense.setStatus(rs.getString("status"));
                expense.setCreatedAt(rs.getTimestamp("created_at"));
                expense.setEmployeeName(rs.getString("full_name"));
                expenseList.add(expense);
            }
        }
        return expenseList;
    }

    // NEW METHOD
    public void updateExpenseStatus(int expenseId, String status) throws SQLException {
        String sql = "UPDATE expenses SET status = ? WHERE expense_id = ?";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, status);
            pstmt.setInt(2, expenseId);
            pstmt.executeUpdate();
        }
    }
}