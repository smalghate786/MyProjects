<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Task Login</title>
</head>
<body>
<h2>LOGIN</h2>
${message}
<form:form action="check" modelAttribute="task" method="POST">
UserEmail:<form:input path="user_Email" required="true"/>
<br/>
UserPassword :<form:password path="user_Password" required="true"/>
<br/>
<br/>
<input type="submit" value="Task Login"/>
<br/>
</form:form>
<br/>
<a href="register"><b>Register</b></a>

</body>
</html>