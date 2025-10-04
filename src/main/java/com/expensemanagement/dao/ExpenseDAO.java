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
        // Updated to include flow_id
        String sql = "INSERT INTO expenses (user_id, category, amount, currency, description, expense_date, status, flow_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, expense.getUserId());
            pstmt.setString(2, expense.getCategory());
            pstmt.setBigDecimal(3, expense.getAmount());
            pstmt.setString(4, expense.getCurrency());
            pstmt.setString(5, expense.getDescription());
            pstmt.setDate(6, expense.getExpenseDate());
            pstmt.setString(7, "Pending");
            pstmt.setObject(8, expense.getFlowId()); // Use setObject for nullable Integer

            pstmt.executeUpdate();
        }
    }

    public List<Expense> getExpensesByUserId(int userId) throws SQLException {
        // ... (This method remains unchanged from the previous step)
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

    public void updateExpenseStatus(int expenseId, String status) throws SQLException {
        String sql = "UPDATE expenses SET status = ? WHERE expense_id = ?";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, status);
            pstmt.setInt(2, expenseId);
            pstmt.executeUpdate();
        }
    }

    // --- NEW METHODS FOR ADVANCED APPROVAL ---

    public List<Expense> getExpensesAwaitingApprovalBy(int approverId) throws SQLException {
        List<Expense> expenseList = new ArrayList<>();
        String sql = "SELECT e.*, u.full_name FROM expenses e " +
                     "JOIN users u ON e.user_id = u.user_id " +
                     "WHERE e.status = 'Pending' AND e.flow_id IS NOT NULL " +
                     "AND EXISTS (SELECT 1 FROM approval_steps s WHERE s.flow_id = e.flow_id AND s.approver_id = ?) " +
                     "AND NOT EXISTS (SELECT 1 FROM expense_approvals a WHERE a.expense_id = e.expense_id AND a.approver_id = ?)";

        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, approverId);
            pstmt.setInt(2, approverId);
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
                expense.setEmployeeName(rs.getString("full_name"));
                expenseList.add(expense);
            }
        }
        return expenseList;
    }

    public void logApprovalDecision(int expenseId, int approverId, String decision) throws SQLException {
        String sql = "INSERT INTO expense_approvals (expense_id, approver_id, decision) VALUES (?, ?, ?)";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, expenseId);
            pstmt.setInt(2, approverId);
            pstmt.setString(3, decision);
            pstmt.executeUpdate();
        }
    }

    public int getTotalApproversForExpense(int expenseId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM approval_steps WHERE flow_id = (SELECT flow_id FROM expenses WHERE expense_id = ?)";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, expenseId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }
    
    public int getApprovalCount(int expenseId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM expense_approvals WHERE expense_id = ? AND decision = 'Approved'";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, expenseId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }

    public int getRejectionCount(int expenseId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM expense_approvals WHERE expense_id = ? AND decision = 'Rejected'";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, expenseId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }
}