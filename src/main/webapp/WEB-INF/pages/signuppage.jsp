<%@ include file="tagsused.jsp" %>
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
	  	
  	<form:form method="post" commandName="userRegistrationDTO" 
  		action="signuppage/signup" accept-charset="UTF-8">
  	<table>
  		<tr>
  			<td><spring:message code="label.signuppage.name" /></td>
  			<td><form:input type="text" path="name" 
  				maxlength="50" value="${userRegistrationDTO.name}"/></td>
  			<td><form:errors path="name" cssClass="error" /></td>
  		</tr>
  		<tr>
  			<td><spring:message code="label.signuppage.login" /></td>
  			<td><form:input type="text" path="login" 
  				maxlength="50" value="${userRegistrationDTO.login}"/></td>
  			<td><form:errors path="login" cssClass="error" /></td>
  		</tr>
  		<tr>
  			<td><spring:message code="label.signuppage.password" /></td>
  			<td><form:input type="password" maxlength="50" path="password1"/></td>
  			<td><form:errors path="password1" cssClass="error" /></td>
  		</tr>
  		<tr>
  			<td><spring:message code="label.signuppage.repeatPassword" /></td>
  			<td><form:input type="password" maxlength="50" path="password2"/></td>
  			<td><form:errors path="password2" cssClass="error" /></td>
  		</tr>
  		<tr>
  			<td><button><spring:message code="label.signuppage.buttonRegister" /></button></td>
  		</tr>
  	</table>
  	</form:form>
</body>
</html>