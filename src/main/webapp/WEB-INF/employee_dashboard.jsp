<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.expensemanagement.model.User" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%
    User loggedInUser = (User) session.getAttribute("user");
    if (loggedInUser == null || !"Employee".equals(loggedInUser.getRole())) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>Employee Dashboard</title>
    <style>
        body { font-family: sans-serif; margin: 2rem; }
        .container { display: flex; gap: 2rem; }
        .expense-history, .add-expense-form { flex: 1; padding: 1rem; border: 1px solid #ccc; border-radius: 8px; }
        table { width: 100%; border-collapse: collapse; }
        th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }
        th { background-color: #f2f2f2; }
        form { display: flex; flex-direction: column; gap: 0.5rem; }
        input, select, button, textarea { padding: 0.5rem; font-family: sans-serif;}
        .header { display: flex; justify-content: space-between; align-items: center; }
        .status-Pending { color: orange; }
        .status-Approved { color: green; }
        .status-Rejected { color: red; }
    </style>
</head>
<body>
    <div class="header">
        <h1>Employee Dashboard</h1>
        <div>
            Welcome, <strong><c:out value="${sessionScope.user.fullName}" /></strong>!
            <a href="logout" style="margin-left: 1rem;">Logout</a>
        </div>
    </div>
    <hr>
    <div class="container">
        <div class="add-expense-form">
            <h2>Submit New Expense</h2>
            <form action="addExpense" method="post">
                <label for="expenseDate">Date of Expense:</label>
                <input type="date" name="expenseDate" required>

                <label for="category">Category:</label>
                <select name="category">
                    <option value="Travel">Travel</option>
                    <option value="Food">Food</option>
                    <option value="Office Supplies">Office Supplies</option>
                    <option value="Other">Other</option>
                </select>

                <label for="amount">Amount:</label>
                <input type="number" step="0.01" name="amount" required>

                <label for="currency">Currency:</label>
                <input type="text" name="currency" maxlength="3" value="USD" required>

                <label for="description">Description:</label>
                <textarea name="description" rows="3" required></textarea>
                
                <button type="submit">Submit Expense</button>
            </form>
        </div>
        <div class="expense-history">
            <h2>Your Expense History</h2>
            <table>
                <thead>
                    <tr>
                        <th>Date</th>
                        <th>Category</th>
                        <th>Amount</th>
                        <th>Description</th>
                        <th>Status</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="expense" items="${expenseList}">
                        <tr>
                            <td><c:out value="${expense.expenseDate}" /></td>
                            <td><c:out value="${expense.category}" /></td>
                            <td><c:out value="${expense.amount}" /> <c:out value="${expense.currency}" /></td>
                            <td><c:out value="${expense.description}" /></td>
                            <td class="status-${expense.status}"><strong><c:out value="${expense.status}" /></strong></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</body>
</html>