<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Login Page</title>
</head>
<body>
    <h1 style="color:red">Login Page</h1>
	<form:form action="login" modelAttribute="userCmd" method="POST" >
   username :: <form:input path="username" />
		<br>
   password :: <form:input path="password" />
		<br>
		<input type="submit" value="Login" />
	</form:form>
<p> ${msg} </p>
</body>
</html>