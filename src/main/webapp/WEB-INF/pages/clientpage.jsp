<%@ include file="tagsused.jsp"%>

<html>
<head>
<%@ include file="header.jsp"%>

<title><spring:message code="label.clientpage.title" /></title>

</head>

<body>
	<jsp:useBean id="now" class="java.util.Date" />
	<fmt:formatDate var="current_year" value="${now}" pattern="yyyy" />

	<%@ include file="clientsidebar.jsp"%>

	<div id="content">
		<br>
		<h1>
			<spring:message code="label.clientpage.header.welcome" />
			${clientname}!
		</h1>
		<br>
		<div class="tabs-content"></div>
	</div>
</body>
</html>