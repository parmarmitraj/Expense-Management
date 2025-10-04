package com.expensemanagement.service;

import java.sql.SQLException;
import com.expensemanagement.dao.ExpenseDAO;

public class ApprovalService {
    
    private ExpenseDAO expenseDAO = new ExpenseDAO();

    public void processApproval(int expenseId, int approverId, String decision) throws SQLException {
        // Step 1: Log the current approver's decision
        expenseDAO.logApprovalDecision(expenseId, approverId, decision);

        // Step 2: Check if this decision completes the approval process based on the rule
        checkApprovalRule(expenseId);
    }

    private void checkApprovalRule(int expenseId) throws SQLException {
        int totalApprovers = expenseDAO.getTotalApproversForExpense(expenseId);
        if (totalApprovers == 0) return; // No rule to check

        int approvals = expenseDAO.getApprovalCount(expenseId);
        int rejections = expenseDAO.getRejectionCount(expenseId);

        // --- THE RULE LOGIC ---
        // This logic is for our sample "100% PERCENTAGE" rule.
        // For a more complex system, you would first fetch the rule type and threshold from the database.
        
        // Rule: If any one person rejects, the whole thing is rejected immediately.
        if (rejections > 0) {
            expenseDAO.updateExpenseStatus(expenseId, "Rejected");
            return;
        }

        // Rule: If the number of approvals equals the total required approvers, it's approved.
        if (approvals == totalApprovers) {
            expenseDAO.updateExpenseStatus(expenseId, "Approved");
            return;
        }
        
        // Otherwise, the expense status remains "Pending" for the next approver.
    }
}