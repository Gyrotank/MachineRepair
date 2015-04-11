<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Log In</title>

<style>
.error {
    color: #ff0000;
    font-style: italic;
    font-weight: bold;
}

h3.left {
    position: absolute;
    left: 10px;
    top: 0px;
}

h3.right {
    position: absolute;
    right: 10px;
    top: 0px;
}
</style>
</head>
<body>

	<h3 class = "right">
		<a href="<c:url value="/index"/>">Back to home page</a>
	</h3>
	
	<h1>Please enter registration information...</h1>
	
	<div class="error">
	<c:out value="${message}"/>
	</div>
	  	
  	<form method="post" action="signuppage/signup" accept-charset="UTF-8">
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