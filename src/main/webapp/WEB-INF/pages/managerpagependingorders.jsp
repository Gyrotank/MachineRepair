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
	<div class="content active" id="pending_orders">
	<h2><spring:message code="label.managerpage.pending" /></h2>
		<div class="success">
  			<c:out value="${message_confirm_succeeded}"/><c:out value="${message_cancel_succeeded}"/>
  		</div>
		<div class="error">
  			<c:out value="${message_confirm_failed}"/><c:out value="${message_cancel_failed}"/>
  		</div>
	<c:choose>
	<c:when test="${empty pending_orders}">
		<br>
		<div style="text-align: center;">
		<span style="font-size: 18px; line-height: 18px;">
			<spring:message code="label.managerpage.noPendingOrders" />
		<br><br><br>
		</span>
		</div>
	</c:when>
	<c:otherwise>		
		<form method="post" action="managerpagependingorders/pendingorderspaging"
			accept-charset="UTF-8">
  		<table>
  		<tr>
  			<td style="width:5%" align="center"><spring:message code="label.managerpage.records" /></td>  			
  			<td style="width:10%" align="center">
  				<input name="pendingOrdersPageStart" maxlength="5" size="8"
  				value="${pending_orders_paging_first + 1}"/></td>  			
  			<td style="width:5%" align="center">
  				<spring:message code="label.managerpage.to" /></td>  			
  			<td style="width:10%" align="center">
  				<input name="pendingOrdersPageEnd" maxlength="5" size="8"
  				value="${pending_orders_paging_last + 1}"/></td>
  			<td style="width:20%" align="center">
  			<spring:message code="label.managerpage.of" />
  			${pending_orders_count}
  			<spring:message code="label.managerpage.total" /></td>
  			<td style="width:50%" align="left"><button>
  				<spring:message code="label.managerpage.buttonGo" /></button></td>  			
  		</tr>  		
  		</table>
  		</form>
		<table data-toggle="table" 
			data-classes="table table-hover table-condensed" 
    		data-striped="true"
    		data-pagination="true"
			data-search="true"
			data-show-columns="true"
			border="1" style="width:900px" align="center">
		<thead>
		<tr><th align="center" data-sortable="true" data-switchable="false"></th>
		<th align="center" data-sortable="true" data-switchable="false">
			<spring:message code="label.managerpage.pending.client" /></th>
		<th align="center" data-sortable="true" data-visible="false">
			<spring:message code="label.managerpage.pending.repairType" /></th>
		<th align="center" data-sortable="true" data-switchable="false">
			<spring:message code="label.managerpage.pending.sn" /></th>
		<th align="center" data-sortable="true" data-visible="false">
			<spring:message code="label.managerpage.pending.machineName" /></th>
		<th align="center" data-sortable="true" data-switchable="false">
			<spring:message code="label.managerpage.pending.date" /></th>
		<th align="center" data-switchable="false">
			<spring:message code="label.managerpage.pending.actions" /></th></tr>
		</thead>
		<tbody>	
  		<c:forEach var="po" items="${pending_orders}" varStatus="loopStatus">
  		<tr class="${loopStatus.index % 2 == 0 ? 'even' : 'odd'}">
    		<td><c:out value="${loopStatus.index + 1}"/></td>
    		<td>${po.client.clientName}</td> 
    		<td>    			
    			<c:choose>
  				<c:when test="${po.repairType.available == 0}">
  				<div class="disabled">
  					<c:choose>   			
    				<c:when test="${locale == 'ru'}">
    					${po.repairType.repairTypeNameRu}
    				</c:when>
    				<c:otherwise>
    					${po.repairType.repairTypeName}
    				</c:otherwise>
    				</c:choose>
    			</div>
    			</c:when>
    			<c:otherwise>
    			<c:choose>   			
    			<c:when test="${locale == 'ru'}">
    				${po.repairType.repairTypeNameRu}
    			</c:when>
    			<c:otherwise>
    				${po.repairType.repairTypeName}
    			</c:otherwise>
    			</c:choose>
    			</c:otherwise>
    			</c:choose>
    		</td>
    		<td>${po.machine.machineSerialNumber}</td>
    		<td>
    			<c:choose>
  				<c:when test="${po.machine.machineServiceable.available == 0}">
  				<div class="disabled">
    				${po.machine.machineServiceable.machineServiceableName}
    			</div>
    			</c:when>
    			<c:otherwise>
    				${po.machine.machineServiceable.machineServiceableName}
    			</c:otherwise>
    			</c:choose>
    		</td>
    		<td>${po.start}</td>    		
    		<td><a href="<c:url value="confirm/?order_id=${po.orderId}"/>" 
    			onclick="return confirm('${dialog_confirm_order}')">
    			<img src="resources/images/confirm.png" width="24"></a>
    		<a href="<c:url value="cancel/?order_id=${po.orderId}" />"
    			onclick="return confirm('${dialog_cancel_order}')">
    			<img src="resources/images/cancel.png" width="24"></a></td>
    	</tr>
  		</c:forEach>
  	</tbody>
  	</table>
  	</c:otherwise>
  	</c:choose>
  	</div>
  	</div>
  	</div>
</body>
</html>