<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
		<h1>Edit Employee</h1>
       <form:form method="POST" action="/springhibernatexml/editsave" modelAttribute="emp">  
      	<table >  
      	<tr>
      	<td></td>  
         <td><form:hidden  path="id" /></td>
         </tr> 
         <tr>  
          <td>Username : </td> 
          <td><form:input path="username"  /></td>
         </tr>  
         <tr>  
          <td>Name :</td>  
          <td><form:input path="name" /></td>
         </tr> 
         <tr>  
          <td>Email :</td>  
          <td><form:input path="email" /></td>
         </tr> 
          <tr>  
          <td>Age :</td>  
          <td><form:input path="age" /></td>
         </tr> 
           <tr>
          <td><input type="submit" value="Edit Save" /></td>  
         </tr>  
        </table>  
       </form:form>  
