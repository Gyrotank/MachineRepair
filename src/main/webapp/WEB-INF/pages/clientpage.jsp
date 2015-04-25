<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="springForm"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
	"http://www.w3.org/TR/html4/loose.dtd">
	
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<link href="<c:url value="/resources/css/general.css"/>" rel="stylesheet" type="text/css" />
<link href="<c:url value="/resources/css/ufd-base.css"/>" rel="stylesheet" type="text/css" />
<link href="<c:url value="/resources/css/plain.css"/>" rel="stylesheet" type="text/css" />
<link href="<c:url value="/resources/css/bootstrap.min.css"/>" rel="stylesheet" />
<link href="<c:url value="/resources/css/bootstrap-table.css"/>" rel="stylesheet" />

<script src="<c:url value="/resources/js/jquery-1.11.2.min.js" />"></script>
<script src="<c:url value="/resources/js/jquery-ui.js" />"></script>
<script src="<c:url value="/resources/js/jquery.ui.ufd.js" />"></script>
<script src="<c:url value="/resources/js/bootstrap.min.js" />"></script>
<script src="<c:url value="/resources/js/bootstrap-table.js" />"></script>
<script src="<c:url value="/resources/js/bootstrap-table-en-US.js" />"></script>
<c:choose>
  	<c:when test="${locale == 'ru'}">
  		<script src="<c:url value="/resources/js/bootstrap-table-ru-RU.js" />"></script>
  	</c:when>
  	<c:otherwise>
  		<script src="<c:url value="/resources/js/bootstrap-table-en-US.js" />"></script>
  	</c:otherwise>
</c:choose>

<title><spring:message code="label.clientpage.title" /></title>

</head>

<body>
	<jsp:useBean id="now" class="java.util.Date" />
	<fmt:formatDate var="current_year" value="${now}" pattern="yyyy" />
	
	<div id="sidebar">
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
		<hr class="style-seven">
		<p><a href="<c:url value="/index"/>"><spring:message code="label.clientpage.sidebar.index" /></a>
		<hr class="style-seven">
			<dl class="tabs vertical">
			<dd class="active"><a href="<c:url value="/clientpage"/>">
  				<spring:message code="label.clientpage.sidebar.dashboard" /></a></dd>
  			<dd><a href="<c:url value="/clientpagecreateorders"/>">
  				<spring:message code="label.clientpage.sidebar.createOrders" /></a></dd>
  			<dd><a href="<c:url value="/clientpagecurrentorders"/>">
  				<spring:message code="label.clientpage.sidebar.currentOrders" /></a></dd>
  			<dd><a href="<c:url value="/clientpagepastorders"/>">
  				<spring:message code="label.clientpage.sidebar.pastOrders" /></a></dd>  			
		</dl>
		<hr class="style-seven">
		<p><a href="<c:url value="/logout"/>"><spring:message code="label.clientpage.sidebar.logout" /></a></p>
	</div>
	
	<div id="content">
	<br>
	<h1><spring:message code="label.clientpage.header.welcome" />  ${clientname}!</h1>
	<br>
	<div class="tabs-content">

  	</div>
  	</div>  	
</body>
</html>