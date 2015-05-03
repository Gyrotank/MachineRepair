<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
<script src="<c:url value="/resources/js/adminpage-edit-scripts.js" />"></script>

<c:choose>
  	<c:when test="${locale == 'ru'}">
  		<script src="<c:url value="/resources/js/bootstrap-table-ru-RU.js" />"></script>
  	</c:when>
  	<c:otherwise>
  		<script src="<c:url value="/resources/js/bootstrap-table-en-US.js" />"></script>
  	</c:otherwise>
</c:choose>

<title><spring:message code="label.adminpage.title" /></title>
</head>

<body>
	<h1 align = "center"><spring:message code="label.adminpage.header" /></h1>
	
	<div id="sidebar">
		<c:choose>
  			<c:when test="${locale == 'en'}">
  				<a href="?locale=en&amp;order-id=${order.orderId}">
  				<img src="${pageContext.servletContext.contextPath}/resources/images/usa.png" 
  					width="40"></a>
  			</c:when>
  			<c:otherwise>
  				<a href="?locale=en&amp;order-id=${order.orderId}">
  				<img src="${pageContext.servletContext.contextPath}/resources/images/usa.png" 
  					width="32"></a>
  			</c:otherwise>
		</c:choose>
		<c:choose>
  			<c:when test="${locale == 'ru'}">
  				<a href="?locale=ru&amp;order-id=${order.orderId}">
  				<img src="${pageContext.servletContext.contextPath}/resources/images/rus.png" 
  					width="40"></a>
  			</c:when>
  			<c:otherwise>
  				<a href="?locale=ru&amp;order-id=${order.orderId}">
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
	<h2><spring:message code="label.adminpage.updateOrder" /></h2>
	<form:form method="post" commandName="order" 
		action="updateOrder" accept-charset="UTF-8">
  	<table>
  		<tr>
  			<td><label>
  				<spring:message code="label.adminpage.orders.repairType" />
  			</label></td>
  			<td>
  				<c:choose>
    				<c:when test="${locale == 'ru'}">
    					<input value="${orderCurrent.repairType.repairTypeNameRu}" maxlength="50" 
  							size="50" readonly="readonly" disabled="disabled"/>
    				</c:when>
    				<c:otherwise>
    					<input value="${orderCurrent.repairType.repairTypeName}" maxlength="50" 
  							size="50" readonly="readonly" disabled="disabled"/>
    				</c:otherwise>
    			</c:choose>  				
  			</td>
  			<td>
  			<select name="repairTypeId">
  			<option value="0">
  				<spring:message code="label.adminpage.addNewOrder.selectRepairType" />
  			</option>
<!-- !!!ONLY 100 REPAIR TYPES ARE FETCHED AS OF NOW!!! -->
  			<c:forEach var="rt" items="${repair_types}">
  				<c:choose>
  					<c:when test="${orderCurrent.repairType.repairTypeId == rt.repairTypeId}">
  						<option selected value="${rt.repairTypeId}">
  							<c:choose>
    							<c:when test="${locale == 'ru'}">
    								<c:out value="${rt.repairTypeNameRu}"/>
    							</c:when>
    							<c:otherwise>
    								<c:out value="${rt.repairTypeName}"/>
    							</c:otherwise>
    						</c:choose>  							
  						</option>
  					</c:when>
  					<c:otherwise>
  						<option value="${rt.repairTypeId}">
  							<c:choose>
    							<c:when test="${locale == 'ru'}">
    								<c:out value="${rt.repairTypeNameRu}"/>
    							</c:when>
    							<c:otherwise>
    								<c:out value="${rt.repairTypeName}"/>
    							</c:otherwise>
    						</c:choose> 
  						</option>
  					</c:otherwise>
  				</c:choose>  				
  			</c:forEach>
  			</select>
  			</td>
  			<td>
  			<div class="error">
  				<c:out value="${message_order_repair_type_id}"/>
  			</div>
  			</td>
  		</tr>  		
  		<tr>  		
  			<td><label>
  				<spring:message code="label.adminpage.addNewOrder.date" />
  			</label></td>
  			<td>
  				<input value="${entered_order_start}" maxlength="50" 
  					size="50" readonly="readonly" disabled="disabled"/>
  			</td>
  			<td><input name="startDate" value="${entered_order_start}"/></td>                
  			<td>
  			<div class="error">
  				<c:out value="${message_order_start}"/>
  			</div>
  			</td>  			  		
  		</tr>
  		<tr>
  			<td><label for="statusInput">
  				<spring:message code="label.adminpage.addNewOrder.status" />
  			</label></td>
  			<td>
  				<c:choose>
  				<c:when test="${orderCurrent.status == 'pending' && locale == 'ru'}">  					
  					<input value="в обработке" maxlength="50" 
  					size="50" readonly="readonly" disabled="disabled"/>
  				</c:when>
  				<c:when test="${orderCurrent.status == 'started' && locale == 'ru'}">
  					<input value="выполняется" maxlength="50" 
  					size="50" readonly="readonly" disabled="disabled"/>
	  			</c:when>
  				<c:when test="${orderCurrent.status == 'ready' && locale == 'ru'}">
  					<input value="готов" maxlength="50" 
  					size="50" readonly="readonly" disabled="disabled"/>
  				</c:when>
  				<c:when test="${orderCurrent.status == 'finished' && locale == 'ru'}">
  					<input value="завершен" maxlength="50" 
  					size="50" readonly="readonly" disabled="disabled"/>
  				</c:when>
  				</c:choose>
    			<c:choose>
  				<c:when test="${orderCurrent.status == 'pending' && locale == 'en'}">
	  				<input value="pending" maxlength="50" 
  					size="50" readonly="readonly" disabled="disabled"/>
  				</c:when>
  				<c:when test="${orderCurrent.status == 'started' && locale == 'en'}">
  					<input value="started" maxlength="50" 
  					size="50" readonly="readonly" disabled="disabled"/>
  				</c:when>
	  			<c:when test="${orderCurrent.status == 'ready' && locale == 'en'}">
  					<input value="ready" maxlength="50" 
  					size="50" readonly="readonly" disabled="disabled"/>
  				</c:when>
  				<c:when test="${orderCurrent.status == 'finished' && locale == 'en'}">
  					<input value="finished" maxlength="50" 
  					size="50" readonly="readonly" disabled="disabled"/>
  				</c:when>
  				</c:choose>
  			</td>
  			<td><form:select path="status" id="statusInput">
  				<form:option selected="true" value="${orderCurrent.status}">
  				<c:choose>
  				<c:when test="${orderCurrent.status == 'pending' && locale == 'ru'}">
  					<c:out value="в обработке"/>
  				</c:when>
  				<c:when test="${orderCurrent.status == 'started' && locale == 'ru'}">
  					<c:out value="выполняется"/>
	  			</c:when>
  				<c:when test="${orderCurrent.status == 'ready' && locale == 'ru'}">
  					<c:out value="готов"/>
  				</c:when>
  				<c:when test="${orderCurrent.status == 'finished' && locale == 'ru'}">
  					<c:out value="завершен"/>
  				</c:when>
  				</c:choose>
    			<c:choose>
  				<c:when test="${orderCurrent.status == 'pending' && locale == 'en'}">
	  				<c:out value="pending"/>
  				</c:when>
  				<c:when test="${orderCurrent.status == 'started' && locale == 'en'}">
  					<c:out value="started"/>
  				</c:when>
	  			<c:when test="${orderCurrent.status == 'ready' && locale == 'en'}">
  					<c:out value="ready"/>
  				</c:when>
  				<c:when test="${orderCurrent.status == 'finished' && locale == 'en'}">
  					<c:out value="finished"/>
  				</c:when>
  				</c:choose>
  				</form:option>
  				<form:option value="pending">  					
  					<c:choose>
    					<c:when test="${locale == 'ru'}">
    						<c:out value="в обработке"/>
    					</c:when>
    					<c:otherwise>
    						<c:out value="pending"/>
    					</c:otherwise>
    				</c:choose>
  				</form:option>
  				<form:option value="started">  					
  					<c:choose>
    					<c:when test="${locale == 'ru'}">
    						<c:out value="выполняется"/>
    					</c:when>
    					<c:otherwise>
    						<c:out value="started" />
    					</c:otherwise>
    				</c:choose>
  				</form:option>
  				<form:option value="ready">
  					<c:choose>
    					<c:when test="${locale == 'ru'}">
    						<c:out value="готов"/>
    					</c:when>
    					<c:otherwise>
    						<c:out value="ready" />
    					</c:otherwise>
    				</c:choose>
  				</form:option>
  				<form:option value="finished">
  					<c:choose>
    					<c:when test="${locale == 'ru'}">
    						<c:out value="завершен"/>
    					</c:when>
    					<c:otherwise>
    						<c:out value="finished" />
    					</c:otherwise>
    				</c:choose>
  				</form:option>
  			</form:select></td>
  			<td><form:errors path="status" cssClass="error" /></td>  			
  		</tr>
  		<tr>
  			<td>
  				<label><spring:message code="label.adminpage.addNewOrder.manager" /></label>
  			</td>
  			<td>
  				<input value="${orderCurrent.manager}" maxlength="50" 
  					size="50" readonly="readonly" disabled="disabled"/>
  			</td>
  			<td><select name="manager">
  			<option value="-">
  				<spring:message code="label.adminpage.addNewOrder.selectManager" />
  			</option>
  			<c:forEach var="man" items="${managers}">
  				<c:choose>
  					<c:when test="${orderCurrent.manager == man}">
  						<option selected value="${man}">
  							<c:out value="${man}"/>
  						</option>
  					</c:when>
  					<c:otherwise>
  						<option value="${man}">
  							<c:out value="${man}"/>
  						</option>
  					</c:otherwise>
  				</c:choose>  				
  			</c:forEach>
  			</select></td>
  			<td>
  			<div class="error">
  				<c:out value="${message_order_manager}"/>
  			</div>
  			</td>
  		</tr>
  		<tr>  
  			<td><button>
  				<spring:message code="label.adminpage.buttonUpdate" />
  			</button></td>
  		</tr>
  	</table>
  	</form:form>
  	<div class="success">
  		<c:out value="${message_order_updated}"/>
  	</div>
	<div class="error">
  		<c:out value="${message_order_not_updated}"/>
  	</div>
  	<div class="info">
  		<c:out value="${message_order_no_changes}"/>
  	</div>
	</div>
	</div>
	</div>
</body>
</html>