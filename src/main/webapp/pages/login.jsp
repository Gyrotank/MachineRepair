<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Log In</title>
</head>
<body>
	<h1>Please enter login information...</h1>
	<c:if test="${not empty param.error}">
		<font color="red"> ERROR: ${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message} </font>
	</c:if>  	
  	<form method="POST" action="<c:url value="/j_spring_security_check"/>">
	<table>
	<tr>
		<td align="right">Login</td>
		<td><input type="text" name="login" /></td>
	</tr>
	<tr>
		<td align="right">Password</td>
		<td><input type="password" name="password" /></td>
	</tr>
	<tr>
		<td align="right">Remember Me</td>
		<td><input type="checkbox" name="_spring_security_remember_me" /></td>
	</tr>
	<tr>
		<td colspan="2" align="right"><input type="submit" value="Login" />
		<input type="reset" value="Reset" /></td>
	</tr>
	</table>
</form>
</body>
</html>