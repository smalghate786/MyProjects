<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<h1>Student List</h1>
<table border="3" width="50%" cellpadding="3">
	<tr>
		<th>Id</th>
		<th>Name</th>
		<th>Age</th>
		<th>Address</th>
		<th>Edit</th>
		<th>Delete</th>
	</tr>
	<c:forEach var="Stu" items="${list}">
		<tr>
			<td>${Stu.id}</td>
			<td>${Stu.name}</td>
			<td>${Stu.age}</td>
			<td>${Stu.address}</td>
			<td><a href="editemp?id=${Stu.id}">Edit</a></td>
			<td><a href="deleteemp?id=${Stu.id}">
					<button
						onclick="return confirm('Are you sure you want to delete?')">
						Delete</button>
			</a></td>
		</tr>
	</c:forEach>
</table>
<br />
<a href="stuInfo">Add New Student</a>
<a href="logout">Logout</a>