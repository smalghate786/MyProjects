    <%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>  
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
	<h1>Employees List</h1>
	<table border="3" width="50%" cellpadding="3">
	<tr><th>Id</th><th>Name</th><th>Salary</th><th>Designation</th><th>Edit</th><th>Delete</th></tr>
    <c:forEach var="emp" items="${list}"> 
    <tr>
    <td>${emp.id}</td>
    <td>${emp.name}</td>
    <td>${emp.salary}</td>
    <td>${emp.designation}</td>
    <td><a href="editemp/${emp.id}">Edit</a></td>
   <td><a href="deleteemp/${emp.id}"> <button onclick="return confirm('Are you sure you want to delete?')"> Delete</a></td>
    </tr>
    </c:forEach>
    </table>
    <br/>
    <a href="empform">Add New Employee</a>
    <a href="logout">Logout</a>