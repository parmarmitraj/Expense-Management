<%@ page language="java" contentType="text/html; charset=UTF-8" isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
<title>Error</title>
</head>
<body>
    <h2>Something went wrong!</h2>
    <p>We're sorry, but an unexpected error occurred.</p>
    <p><b>Error Details:</b> <%= request.getAttribute("errorMessage") %></p>
    <a href="dashboard">Go back to Dashboard</a>
</body>
</html>