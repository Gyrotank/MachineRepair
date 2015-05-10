<%@ include file="tagsused.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<link href="<c:url value="/resources/css/general.css"/>" rel="stylesheet" type="text/css" />

<title><spring:message code="label.homepage.title" /></title>

</head>
<body>
	
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
	
	<h3 class = "left">		
		<c:if test="${login == ''}">
   			<a href="${pageContext.servletContext.contextPath}/login">
   			<spring:message code="label.homepage.login" /></a>&emsp;
   			<a href="${pageContext.servletContext.contextPath}/signuppage">
   			<spring:message code="label.homepage.signup" /></a>&emsp;
		</c:if>
		<c:if test="${login != ''}">
			<spring:message code="label.homepage.welcome" /> ${login}!
		</c:if>		
		
	</h3>
	<br>
	<center>
		<h1><spring:message code="label.homepage.header" /></h1>
	</center>
	
	<c:if test="${fn:contains(user_token_authorities, 'ROLE_MANAGER')
					|| fn:contains(user_token_authorities, 'ROLE_ADMIN')}">
		<a href="${pageContext.servletContext.contextPath}/managerpage">
			<spring:message code="label.homepage.managerTools" /></a>
		<br>				
	</c:if>
	<c:if test="${fn:contains(user_token_authorities, 'ROLE_ADMIN')}">
		<br>
		<a href="${pageContext.servletContext.contextPath}/adminpage">
			<spring:message code="label.homepage.adminTools" /></a>
		<br>
	</c:if>
	<c:if test="${fn:contains(user_token_authorities, 'ROLE_CLIENT')
				&& not fn:contains(user_token_authorities, 'ROLE_MANAGER')
				&& not fn:contains(user_token_authorities, 'ROLE_ADMIN')}">
		<a href="${pageContext.servletContext.contextPath}/clientpage">
			<spring:message code="label.homepage.personalCabinet" /></a>
		<br>		
	</c:if>
	<c:if test="${login != ''}">
		<br>
		<a href="<c:url value="/logout"/>"><spring:message code="label.homepage.logout" /></a>
	</c:if>
	
</body>
</html>