<%@ include file="tagsused.jsp" %>

<html>
<head>

<%@ include file="header.jsp" %>

<title><spring:message code="label.adminpage.title" /></title>

</head>

<body>
	
	<jsp:useBean id="now" class="java.util.Date" />
	<fmt:formatDate var="current_year" value="${now}" pattern="yyyy" />
	
	<h1 align = "center"><spring:message code="label.adminpage.header" /></h1>
	
	<%@ include file="adminsidebar.jsp" %>
	
	<div id="content">
	<div class="tabs-content">

  	</div>
  	</div>
</body>
</html>