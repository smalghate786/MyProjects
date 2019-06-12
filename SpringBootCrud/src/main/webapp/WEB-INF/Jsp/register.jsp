<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>SpringBoot Login</title>
</head>
<body>
	<form:form model="#" method="POST" action="add"
		modelAttribute="student">
NAME   <form:input path="name" />
		<br />
AGE     <form:input path="age" />
		<br />
Address <form:input path="address" />
		<br />
Password <form:password path="password" />
		<br />
		<input type="submit" value="Add" />
	</form:form>
	<a href="login">Login here</a>
</body>
</html>
