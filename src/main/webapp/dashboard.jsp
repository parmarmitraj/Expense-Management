<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.expensemanagement.model.User" %>

<%
    // Check if the user is logged in by looking for the "user" object in the session.
    User loggedInUser = (User) session.getAttribute("user");

    // If no user is found in the session, they are not logged in.
    // Redirect them back to the login page.
    if (loggedInUser == null) {
        response.sendRedirect("login.jsp");
        return; // Important to stop further processing of the page
    }
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Dashboard</title>
<style>
    body { font-family: sans-serif; margin: 2rem; }
    h1 { color: #333; }
</style>
</head>
<body>
    <h1>Welcome to the Dashboard, <%= loggedInUser.getFullName() %>!</h1>
    <p>Your Role: <strong><%= loggedInUser.getRole() %></strong></p>
    
    <a href="logout">Logout</a>

</body>
</html>