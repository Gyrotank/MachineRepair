<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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

<title><spring:message code="label.managerpage.title" /></title>
</head>

<body>	
	<h1 align = "center"><spring:message code="label.managerpage.header" /></h1>
	
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
		<p><a href="<c:url value="/index"/>">
			<spring:message code="label.managerpage.sidebar.index" /></a></p>
		<hr class="style-seven">
		<c:if test="${fn:contains(user_token_authorities, 'ROLE_ADMIN')}">			
			<p><a href="<c:url value="/adminpage"/>">
				<spring:message code="label.managerpage.sidebar.adminpage" /></a></p>
			<hr class="style-seven">						
		</c:if>		
			<dl class="tabs vertical">
			<dd class="active"><a href="<c:url value="/managerpage"/>">
  				<spring:message code="label.managerpage.sidebar.dashboard" /></a></dd>
  			<dd><a href="<c:url value="/managerpagependingorders"/>">
  				<spring:message code="label.managerpage.sidebar.pending" /></a></dd>
  			<dd><a href="<c:url value="/managerpageactiveorders"/>">
  				<spring:message code="label.managerpage.sidebar.active" /></a></dd>
		<hr class="style-seven">
		<p><a href="<c:url value="/logout"/>">
			<spring:message code="label.managerpage.sidebar.logout" /></a></p>
	</div>	
	
	<div id="content">
	<div class="tabs-content">
  	
  	</div>
  	</div>  	
  	
</body>
</html>