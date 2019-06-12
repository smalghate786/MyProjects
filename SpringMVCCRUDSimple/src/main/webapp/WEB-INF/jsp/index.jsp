<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.4.0/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.19.0/jquery.validate.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.19.0/jquery.validate.min.js"></script>
<style type="text/css">
 .error{
   color: #FF0000;
 }
</style>
<script  type="text/javascript">
$(document).ready(function () {
 $('#formid').validate({
	  rules: {
		  name: 'required',
	    password: {
	      required: true,
	      minlength: 4
	    }
	  },
	  messages: {
	    name: 'Enter the username',
	    password: 'Enter password',
	    password: {
	      minlength: 'Password must be at least 8 characters long'
	    }
	  }
	});
});
</script>
</head>
<body>
<h1>Login !</h1>
<form:form  id ="formid" method="POST" action="login">
	<table>
		<tr>
			<td>Name :</td>
			<td><form:input  path="name" /></td>
		</tr>
		<tr>
			<td>Password :</td>
			<td><form:password  path="password"/></td>
		</tr>
		<tr>
			<td><input type="submit" value="login" /></td>
		</tr>
	</table>
</form:form>

<a href="empform">Sign up</a>
</body>
</html>
