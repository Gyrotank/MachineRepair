<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<link href="<c:url value="/resources/css/general.css"/>" rel="stylesheet" type="text/css" />

<title><spring:message code="label.signuppage.title" /></title>

</head>
<body>

	<h3 class = "left">
		<a href="<c:url value="/index"/>"><spring:message code="label.signuppage.back" /></a>
	</h3>
	
	<h3 class = "right">
	<c:choose>
  			<c:when test="${locale == 'en'}">
  				<a href="?locale=en"><img src="resources/images/usa.png" width="40"></a>
  			</c:when>
  			<c:otherwise>
  				<a href="?locale=en"><img src="resources/images/usa.png" width="32"></a>
  			</c:otherwise>
		</c:choose>
		<c:choose>
  			<c:when test="${locale == 'ru'}">
  				<a href="?locale=ru"><img src="resources/images/rus.png" width="40"></a>
  			</c:when>
  			<c:otherwise>
  				<a href="?locale=ru"><img src="resources/images/rus.png" width="32"></a>
  			</c:otherwise>
		</c:choose>		
		<br>
	</h3>
	
	<h1><br><spring:message code="label.signuppage.header" /></h1>
	
	<c:choose>
    	<c:when test="${not empty message}">
        	<div class="error">
				<c:out value="${message}"/>
			</div>
    	</c:when>
    	<c:otherwise>
        	<font color="white"> _ </font>
    	</c:otherwise>
	</c:choose>
	  	
  	<form method="post" action="signuppage/signup" accept-charset="UTF-8">
  	<table>
  		<tr>
  			<td><spring:message code="label.signuppage.name" /></td>
  			<td><input type="text" name="name" maxlength="50" value="${entered_name}"/></td>
  		</tr>
  		<tr>
  			<td><spring:message code="label.signuppage.login" /></td>
  			<td><input type="text" name="login" maxlength="50" value="${entered_login}"/></td>
  		</tr>
  		<tr>
  			<td><spring:message code="label.signuppage.password" /></td>
  			<td><input type="password" maxlength="50" name="password1"/></td>
  		</tr>
  		<tr>
  			<td><spring:message code="label.signuppage.repeatPassword" /></td>
  			<td><input type="password" maxlength="50" name="password2"/></td>
  		</tr>
  		<tr>
  			<td><button><spring:message code="label.signuppage.buttonRegister" /></button></td>
  		</tr>
  	</table>
  	</form>
  	
<!--   	<form method="post" action="signuppage/generate" accept-charset="UTF-8"> -->
<!--   		<button>Generate 50000 new clients</button> -->
<!--   	</form> -->
</body>
</html>