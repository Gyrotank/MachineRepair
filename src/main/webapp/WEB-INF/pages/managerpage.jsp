<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
	"http://www.w3.org/TR/html4/loose.dtd">
	
<html>
<head>
<%@ include file="header.jsp" %>

<title><spring:message code="label.managerpage.title" /></title>
</head>

<body>	
	<h1 align = "center"><spring:message code="label.managerpage.header" /></h1>
	
	<%@ include file="managersidebar.jsp" %>	
	
	<div id="content">
	<div class="tabs-content">
  	
  	</div>
  	</div>  	
  	
</body>
</html>