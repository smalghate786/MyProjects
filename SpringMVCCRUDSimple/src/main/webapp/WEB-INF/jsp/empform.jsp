<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
		<h1>Register Here</h1>
       <form:form method="POST" action="save">  
      	<table >  
         <tr>  
          <td>Name : </td> 
          <td><form:input path="name"  /></td>
         </tr>  
         <tr>  
          <td>Salary :</td>  
          <td><form:input path="salary" /></td>
         </tr> 
         <tr>  
          <td>Designation :</td>  
          <td><form:input path="designation" /></td>
         </tr> 
         <tr>  
          <td>Password : </td>  
          <td><form:password path="password"/></td>
         </tr>
         
         <tr>  
          <td>Select Roles : </td>  
           <td> <form:radiobutton path="role" value="admin"/>Admin
                <form:radiobutton path="role" value="normal"/> Normal</td>
         </tr>
         
         <tr>  
          <td><input type="submit" value="Save" /></td>  
         </tr>  
        </table>  
       </form:form>  
<a href="login">Login</a>
       