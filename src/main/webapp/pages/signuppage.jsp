<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Log In</title>

<style>
.error {
    color: #ff0000;
    font-style: italic;
    font-weight: bold;
}
</style>
</head>
<body>
	<h1>Please enter registration information...</h1>
	
	<div class="error">
	<c:out value="${message}"/>
	</div>
	  	
  	<form method="post" action="signuppage/signup">
  	<table>
  		<tr>
  			<td>Name:*</td>
  			<td><input type="text" name="name" value="${entered_name}"/></td>
  		</tr>
  		<tr>
  			<td>Login:*</td>
  			<td><input type="text" name="login" value="${entered_login}"/></td>
  		</tr>
  		<tr>
  			<td>Password:*</td>
  			<td><input type="password" name="password1"/></td>
  		</tr>
  		<tr>
  			<td>Repeat password:*</td>
  			<td><input type="password" name="password2"/></td>
  		</tr>
  		<tr>
  			<td><button>Register</button></td>
  		</tr>
  	</table>
  	</form>
  	
</body>
</html>