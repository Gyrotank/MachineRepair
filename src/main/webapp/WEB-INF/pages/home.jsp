<%@ include file="tagsused.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<link href="<c:url value="/resources/css/general.css"/>"
	rel="stylesheet" type="text/css" />

<title><spring:message code="label.homepage.title" /></title>

</head>
<body>

	<h3 class="right">
		<c:choose>
			<c:when test="${locale == 'en'}">
				<a href="?locale=en"><img src="resources/images/usa.png"
					width="40"></a>
			</c:when>
			<c:otherwise>
				<a href="?locale=en"><img src="resources/images/usa.png"
					width="32"></a>
			</c:otherwise>
		</c:choose>
		<c:choose>
			<c:when test="${locale == 'ru'}">
				<a href="?locale=ru"><img src="resources/images/rus.png"
					width="40"></a>
			</c:when>
			<c:otherwise>
				<a href="?locale=ru"><img src="resources/images/rus.png"
					width="32"></a>
			</c:otherwise>
		</c:choose>
		<br>
	</h3>

	<h3 class="left">
		<a href="${pageContext.servletContext.contextPath}/login"> <spring:message
				code="label.homepage.login" /></a>&emsp; <a
			href="${pageContext.servletContext.contextPath}/signuppage"> <spring:message
				code="label.homepage.signup" /></a>&emsp;

	</h3>
	<br>
	<div align="center">
		<h1>
			<spring:message code="label.homepage.header" />
		</h1>
	</div>

</body>
</html>