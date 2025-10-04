<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.expensemanagement.model.User" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%
    User loggedInUser = (User) session.getAttribute("user");
    if (loggedInUser == null || !"Admin".equals(loggedInUser.getRole())) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>Admin Dashboard</title>
    <style>
        body { font-family: sans-serif; margin: 2rem; }
        .container { display: flex; gap: 2rem; }
        .user-list, .add-user-form { flex: 1; padding: 1rem; border: 1px solid #ccc; border-radius: 8px; }
        table { width: 100%; border-collapse: collapse; }
        th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }
        th { background-color: #f2f2f2; }
        form { display: flex; flex-direction: column; gap: 0.5rem; }
        input, select, button { padding: 0.5rem; }
        .header { display: flex; justify-content: space-between; align-items: center; }
    </style>
</head>
<body>
    <div class="header">
        <h1>Admin Dashboard</h1>
        <div>
            Welcome, <strong><c:out value="${sessionScope.user.fullName}" /></strong>!
            <a href="logout" style="margin-left: 1rem;">Logout</a>
        </div>
    </div>
    <hr>
    <div class="container">
        <div class="user-list">
            <h2>Company Users</h2>
            <table>
                <thead>
                    <tr>
                        <th>Name</th>
                        <th>Email</th>
                        <th>Role</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="user" items="${userList}">
                        <tr>
                            <td><c:out value="${user.fullName}" /></td>
                            <td><c:out value="${user.email}" /></td>
                            <td><c:out value="${user.role}" /></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
        <div class="add-user-form">
            <h2>Add New User</h2>
            <form action="addUser" method="post">
                <label for="fullName">Full Name:</label>
                <input type="text" name="fullName" required>
                <label for="email">Email:</label>
                <input type="email" name="email" required>
                <label for="password">Password:</label>
                <input type="password" name="password" required>
                <label for="role">Role:</label>
                <select name="role" id="roleSelect">
                    <option value="Employee">Employee</option>
                    <option value="Manager">Manager</option>
                </select>
                <div id="managerSelection" style="display: flex; flex-direction: column; gap: 0.5rem;">
                    <label for="managerId">Assign to Manager:</label>
                    <select name="managerId">
                        <option value="0">-- No Manager --</option>
                        <c:forEach var="manager" items="${managerList}">
                            <option value="${manager.userId}"><c:out value="${manager.fullName}"/></option>
                        </c:forEach>
                    </select>
                </div>
                <button type="submit">Add User</button>
            </form>
        </div>
    </div>

    <script>
        // Optional: Hide manager dropdown if the selected role is "Manager"
        document.getElementById('roleSelect').addEventListener('change', function() {
            var managerDiv = document.getElementById('managerSelection');
            if (this.value === 'Manager') {
                managerDiv.style.display = 'none';
            } else {
                managerDiv.style.display = 'flex';
            }
        });
    </script>
</body>
</html>