<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	You are sucessfully login!....
	<br> Welcome <span style="color: red"><b></b></span> to Candid Java SpringMVC
	<br>
	<a href="login">Logout</a>
	
	<a href="register">Register</a>
	
	
	<table border="3" width="50%" cellpadding="3">
	<tr><th>Username</th><th>Name</th><th>Email</th><th>Age</th><th>Edit</th><th>Delete</th></tr>
    <c:forEach var="emp1" items="${list}"> 
    <tr>
    <td><c:out value="${emp1.username}"/></td>
    <td>${emp1.name}</td>
    <td>${emp1.email}</td>
    <td>${emp1.age}</td>
   
   
 
   
    <td><a href="editemp/${emp1.id}">Edit</a></td>
    <td><a href="deleteemp/${emp1.id}">Delete</a></td>
   
    </tr>
    </c:forEach>
    </table>
	
</body>
</html>