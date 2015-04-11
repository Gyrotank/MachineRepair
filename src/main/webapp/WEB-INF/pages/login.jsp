<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<link href="<c:url value="/resources/css/general.css"/>" rel="stylesheet" type="text/css" />

<title>Log In</title>

</head>
<body>

	<h3 class = "right">
		<a href="<c:url value="/index"/>">Back to home page</a>
	</h3>
	
	<h1>Please enter login information...</h1>
	<c:choose>
    	<c:when test="${not empty param.error}">
        	<font color="red"> ERROR: ${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message} </font>
    	</c:when>
    	<c:otherwise>
        	<font color="white"> _ </font>
    	</c:otherwise>
	</c:choose>
  	<form method="POST" action="<c:url value="/j_spring_security_check"/>" accept-charset="UTF-8">
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