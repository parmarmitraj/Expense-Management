<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.expensemanagement.model.User" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%
    User loggedInUser = (User) session.getAttribute("user");
    if (loggedInUser == null || !"Manager".equals(loggedInUser.getRole())) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>Manager Dashboard</title>
    <style>
        body { font-family: sans-serif; margin: 2rem; }
        table { width: 100%; border-collapse: collapse; margin-top: 1rem; }
        th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }
        th { background-color: #f2f2f2; }
        .header { display: flex; justify-content: space-between; align-items: center; }
        button { padding: 5px 10px; cursor: pointer; }
    </style>
</head>
<body>
    <div class="header">
        <h1>Manager Dashboard</h1>
        <div>
            Welcome, <strong><c:out value="${sessionScope.user.fullName}" /></strong>!
            <a href="logout" style="margin-left: 1rem;">Logout</a>
        </div>
    </div>
    <hr>
    <h2>Pending Approvals</h2>
    <table>
        <thead>
            <tr>
                <th>Employee Name</th>
                <th>Date</th>
                <th>Category</th>
                <th>Amount</th>
                <th>Description</th>
                <th>Action</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="expense" items="${pendingApprovals}">
                <tr>
                    <td><c:out value="${expense.employeeName}" /></td>
                    <td><c:out value="${expense.expenseDate}" /></td>
                    <td><c:out value="${expense.category}" /></td>
                    <td><c:out value="${expense.amount}" /> <c:out value="${expense.currency}" /></td>
                    <td><c:out value="${expense.description}" /></td>
                    <td>
                        <form action="approval" method="post" style="display:inline;">
                            <input type="hidden" name="expenseId" value="${expense.expenseId}">
                            <button type="submit" name="action" value="Approved" style="color: green;">Approve</button>
                            <button type="submit" name="action" value="Rejected" style="color: red;">Reject</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
             <c:if test="${empty pendingApprovals}">
                <tr>
                    <td colspan="6" style="text-align:center;">No pending approvals.</td>
                </tr>
            </c:if>
        </tbody>
    </table>
</body>
</html>