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
  				<a href="?locale=en&amp;machine-id=${machine.machineId}">
  				<img src="${pageContext.servletContext.contextPath}/resources/images/usa.png" 
  					width="40"></a>
  			</c:when>
  			<c:otherwise>
  				<a href="?locale=en&amp;machine-id=${machine.machineId}">
  				<img src="${pageContext.servletContext.contextPath}/resources/images/usa.png" 
  					width="32"></a>
  			</c:otherwise>
		</c:choose>
		<c:choose>
  			<c:when test="${locale == 'ru'}">
  				<a href="?locale=ru&amp;machine-id=${machine.machineId}">
  				<img src="${pageContext.servletContext.contextPath}/resources/images/rus.png" 
  					width="40"></a>
  			</c:when>
  			<c:otherwise>
  				<a href="?locale=ru&amp;machine-id=${machine.machineId}">
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
	<h2><spring:message code="label.adminpage.updateMachine" /></h2>
	<form:form method="post" commandName="machine" action="updateMachine" accept-charset="UTF-8">
  	<table>
  		<tr>
  			<td><label for="machineSerialNumberInput">
  				<spring:message code="label.adminpage.machines.name" />
  				</label>
  			</td>
  			<td>
  				<input value="${machineCurrent.machineServiceable.machineServiceableName}" 
  					maxlength="32" size="32" readonly="readonly" disabled="disabled"/>
  			</td>
  		</tr>  		
  		<tr>
  			<td><label for="machineSerialNumberInput">
  				<spring:message code="label.adminpage.addNewMachine.sn" />
  				</label></td>
  			<td>
  				<input value="${machineCurrent.machineSerialNumber}" maxlength="32" 
  					size="32" readonly="readonly" disabled="disabled"/>
  			</td>
  			<td><form:input path="machineSerialNumber" id="machineSerialNumberInput"
  					size="32" maxlength="32" /></td>
  			<td><form:errors path="machineSerialNumber" cssClass="error" /></td>
  		</tr>
  		<tr>  		
  			<td><label for="machineYearInput">
  			<spring:message code="label.adminpage.addNewMachine.year" />
  			</label></td>
  			<td>
  				<input value="${machineCurrent.machineYear}"	maxlength="4" size="4"
  					readonly="readonly" disabled="disabled"/>
  			</td>
  			<td><form:input size="4" minlength="4" maxlength="4"
  				 path="machineYear" id="machineYearInput"/></td>
  			<td><form:errors path="machineYear" cssClass="error" /></td>  			
		</tr>
		<tr>
  			<td><label for="machineTimesRepairedInput">
  			<spring:message code="label.adminpage.addNewMachine.timesRepaired" />
  			</label></td>
  			<td>
  				<input value="${machineCurrent.machineTimesRepaired}"	maxlength="3" size="3"
  					readonly="readonly" disabled="disabled"/>
  			</td>
  			<td><form:input size="3" maxlength="3"
  				 path="machineTimesRepaired" id="machineTimesRepairedInput"/></td>
  			<td><form:errors path="machineTimesRepaired" cssClass="error" /></td>  		
  		</tr>
  		<tr>
  			<td><button><spring:message code="label.adminpage.buttonUpdate" />
  				</button></td>
  		</tr>
  	</table>
  	</form:form>
  	<div class="success">
  		<c:out value="${message_machine_updated}"/>
  	</div>
	<div class="error">
  		<c:out value="${message_machine_not_updated}"/>
  	</div>
  	<div class="info">
  		<c:out value="${message_machine_no_changes}"/>
  	</div>
	</div>
	</div>
	</div>
</body>
</html>