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
	<form:form model="#" method="POST" action="login"
		modelAttribute="loginStudent">
    NAME     <form:input path="name" />
		<br />
PASSWORD     <form:password path="password" />
		<br />
		<input type="submit" value="login" />

	</form:form>
</body>
</html>
<a href="stuInfo">Register here</a>