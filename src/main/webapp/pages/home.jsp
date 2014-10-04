<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>MachineRepair Home Page</title>

<style>
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
	
	<h3 class = "left">
		<c:if test="${login != ''}">
			Welcome, ${login}!
		</c:if>
	</h3>
	
	<h3 class = "right">
		<c:if test="${login == ''}">
   			<a href="${pageContext.servletContext.contextPath}/login">Log In</a>&emsp;
   			<a href="${pageContext.servletContext.contextPath}/signuppage">Sign Up</a>&emsp;
		</c:if>
		
		<c:if test="${login != ''}">
			<a href="<c:url value="/logout"/>">Log out</a>
		</c:if>
	</h3>
	<br>
	<center>
		<h1>MachineRepair Inc.</h1>
	</center>
	
	<c:if test="${fn:contains(user_token_authorities, 'ROLE_MANAGER')
					|| fn:contains(user_token_authorities, 'ROLE_ADMIN')}">
		<a href="${pageContext.servletContext.contextPath}/managerpage">Manager Tools</a>
		<br>				
	</c:if>
	<c:if test="${fn:contains(user_token_authorities, 'ROLE_ADMIN')}">
		<br>
		<a href="${pageContext.servletContext.contextPath}/adminpage">Admin Tools</a>
	</c:if>
	<c:if test="${fn:contains(user_token_authorities, 'ROLE_CLIENT')
				&& not fn:contains(user_token_authorities, 'ROLE_MANAGER')
				&& not fn:contains(user_token_authorities, 'ROLE_ADMIN')}">
		<a href="${pageContext.servletContext.contextPath}/clientpage">Personal Cabinet</a>		
	</c:if>		
	
</body>
</html>