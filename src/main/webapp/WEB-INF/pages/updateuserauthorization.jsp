<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
			 "http://www.w3.org/TR/html4/loose.dtd">
			 
<html>
<head>
<%@ include file="header.jsp" %>

<title><spring:message code="label.adminpage.title" /></title>
</head>

<body>
<h1 align = "center"><spring:message code="label.adminpage.header" /></h1>
	
	<div id="sidebar">
		<c:choose>
  			<c:when test="${locale == 'en'}">
  				<a href="?locale=en&amp;user-authorization-id=${userAuthorizationDTO.userAuthorizationId}">
  				<img src="${pageContext.servletContext.contextPath}/resources/images/usa.png" 
  					width="40"></a>
  			</c:when>
  			<c:otherwise>
  				<a href="?locale=en&amp;user-authorization-id=${userAuthorizationDTO.userAuthorizationId}">
  				<img src="${pageContext.servletContext.contextPath}/resources/images/usa.png" 
  					width="32"></a>
  			</c:otherwise>
		</c:choose>
		<c:choose>
  			<c:when test="${locale == 'ru'}">
  				<a href="?locale=ru&amp;user-authorization-id=${userAuthorizationDTO.userAuthorizationId}">
  				<img src="${pageContext.servletContext.contextPath}/resources/images/rus.png" 
  					width="40"></a>
  			</c:when>
  			<c:otherwise>
  				<a href="?locale=ru&amp;user-authorization-id=${userAuthorizationDTO.userAuthorizationId}">
  				<img src="${pageContext.servletContext.contextPath}/resources/images/rus.png" 
  					width="32"></a>
  			</c:otherwise>
		</c:choose>
		<hr class="style-seven">
		<p><a href="<c:url value="/index"/>">
			<spring:message code="label.adminpage.sidebar.index" /></a></p>
		<hr class="style-seven">		
		<p><a href="<c:url value="/managerpage"/>">
			<spring:message code="label.adminpage.sidebar.managerpage" /></a></p>
		<hr class="style-seven">
	<dl class="tabs vertical">
  		<dd class="active"><a href="<c:url value="/adminpage"/>">
			<spring:message code="label.adminpage.sidebar.dashboard" /></a></dd>
  		<dd><a href="<c:url value="/adminpagemachines"/>">
			<spring:message code="label.adminpage.sidebar.machines" /></a></dd>
  		<dd><a href="<c:url value="/adminpagemachinesserviceable"/>">
			<spring:message code="label.adminpage.sidebar.serviceableMachines" /></a></dd>
  		<dd><a href="<c:url value="/adminpagerepairtypes"/>">
			<spring:message code="label.adminpage.sidebar.repairTypes" /></a></dd>
  		<dd><a href="<c:url value="/adminpageusers"/>">
			<spring:message code="label.adminpage.sidebar.users" /></a></dd>
  		<dd><a href="<c:url value="/adminpageuserauthorizations"/>">
			<spring:message code="label.adminpage.sidebar.userAuths" /></a></dd>
  		<dd><a href="<c:url value="/adminpageclients"/>">
			<spring:message code="label.adminpage.sidebar.clients" /></a></dd>
  		<dd><a href="<c:url value="/adminpageorders"/>">
			<spring:message code="label.adminpage.sidebar.orders" /></a></dd>
	</dl>
		<hr class="style-seven">
		<p><a href="<c:url value="/logout"/>">
			<spring:message code="label.adminpage.sidebar.logout" /></a></p>
	</div>
	
	<div id="content">
	<div class="tabs-content">
	<div class="content">
	<h2><spring:message code="label.adminpage.updateUserAuthorization"/> ${userAuthorizationDTOCurrent.user.login}</h2>
	<form:form method="post" commandName="userAuthorizationDTO" 
  		action="updateUserAuthorization" accept-charset="UTF-8">
  	<table data-toggle="table" 
		data-classes="table table-hover table-condensed"
		data-striped="true"    	    	
		border="1" style="width:600px" align="center">
  	<thead>
  		<tr>  		
		<c:forEach var="ur" items="${userRoles}" varStatus="loopStatus">
		<th data-align="center" data-width="200">
			<c:choose>
  				<c:when test="${locale == 'ru'}">
  					<c:out value="${ur.descRu}"/>
  				</c:when>
  				<c:otherwise>
  					<c:out value="${ur.descEn}"/>
  				</c:otherwise>  				
  			</c:choose>
  		</th>
		</c:forEach>
		</tr>			
  	</thead>
  	<tbody>
  		<tr>  			
  			<td>
  				<c:choose>
  				<c:when test="${userAuthorizationDTOCurrent.isAdmin}">
  					<input type="checkbox" checked readonly="readonly" disabled="disabled">
  				</c:when>
  				<c:otherwise>
  					<input type="checkbox" readonly="readonly" disabled="disabled">
  				</c:otherwise>  				
  				</c:choose>  				
  			</td>
  			<td>
  				<c:choose>
  				<c:when test="${userAuthorizationDTOCurrent.isClient}">
  					<input type="checkbox" checked readonly="readonly" disabled="disabled">
  				</c:when>
  				<c:otherwise>
  					<input type="checkbox" readonly="readonly" disabled="disabled">
  				</c:otherwise>  				
  				</c:choose>  				
  			</td>
  			<td>
  				<c:choose>
  				<c:when test="${userAuthorizationDTOCurrent.isManager}">
  					<input type="checkbox" checked readonly="readonly" disabled="disabled">
  				</c:when>
  				<c:otherwise>
  					<input type="checkbox" readonly="readonly" disabled="disabled">
  				</c:otherwise>  				
  				</c:choose>
  			</td>
  		</tr>  		
  		<tr>
  			<td>
  				<c:choose>
  				<c:when test="${userAuthorizationDTOCurrent.isOnlyAdmin}">
  					<form:checkbox path="isAdmin" id="isAdminInput" 
  						disabled="true" checked="true"/>
  				</c:when>
  				<c:when test="${userAuthorizationDTOCurrent.isAdmin 
  					&& !userAuthorizationDTOCurrent.isOnlyAdmin}">
  					<form:checkbox path="isAdmin" id="isAdminInput" checked="true"/>
  				</c:when>
  				<c:otherwise>
  					<form:checkbox path="isAdmin" id="isAdminInput"/>
  				</c:otherwise>
  				</c:choose>
  			</td>
  			<td>
  				<c:choose>
  				<c:when test="${userAuthorizationDTOCurrent.isClient}">
  					<form:checkbox path="isClient" id="isClientInput" checked="true"/>
  				</c:when>
  				<c:otherwise>
  					<form:checkbox path="isClient" id="isClientInput"/>
  				</c:otherwise>
  				</c:choose>
  			</td>
  			<td>
  				<c:choose>
  				<c:when test="${userAuthorizationDTOCurrent.isManager}">
  					<form:checkbox path="isManager" id="isManagerInput" checked="true"/>
  				</c:when>
  				<c:otherwise>
  					<form:checkbox path="isManager" id="isManagerInput"/>
  				</c:otherwise>
  				</c:choose>
  			</td>
  		</tr>  		 		
  	</tbody>
  	</table>
  	<p align="center">
  		<button><spring:message code="label.adminpage.buttonUpdate" /></button>
  	</p>
  	</form:form>
  	<div class="success">
  		<c:out value="${message_user_authorization_updated}"/>
  	</div>
	<div class="error">
  		<c:out value="${message_user_authorization_not_updated}"/>
  	</div>
  	<div class="info">
  		<c:out value="${message_user_authorization_no_changes}"/>
  	</div>
	</div>
	</div>
	</div>
</body>
</html>