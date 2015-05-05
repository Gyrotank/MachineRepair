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
<%@ include file="header.jsp" %>

<title><spring:message code="label.clientpage.title" /></title>

</head>

<body>
	<jsp:useBean id="now" class="java.util.Date" />
	<fmt:formatDate var="current_year" value="${now}" pattern="yyyy" />
	
	<%@ include file="clientsidebar.jsp" %>
	
	<div id="content">
	<br>
	<h1><spring:message code="label.clientpage.header.welcome" />  ${clientname}!</h1>
	<br>
	<div class="tabs-content">

  	</div>
  	</div>  	
</body>
</html>