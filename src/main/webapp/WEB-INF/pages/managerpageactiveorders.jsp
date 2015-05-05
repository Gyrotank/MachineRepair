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
	
  	<div class="content" id="manage_active_orders">
  	<h2><spring:message code="label.managerpage.active" /></h2>
  		<div class="success">
  			<c:out value="${message_set_ready_succeeded}"/>
  		</div>
		<div class="error">
  			<c:out value="${message_set_ready_failed}"/>
  		</div>
  	<form method="post" action="managerpageactiveorders/clientpaging" accept-charset="UTF-8">
  		<table>
  		<tr>
  			<td style="width:5%" align="center">
  				<spring:message code="label.managerpage.records2" /></td>  			
  			<td style="width:10%" align="center">
  				<input name="clientPageStart" maxlength="5" size="8"
  				value="${clients_paging_first + 1}"/></td>  			
  			<td style="width:5%" align="center">
  				<spring:message code="label.managerpage.to" /></td>  			
  			<td style="width:10%" align="center">
  				<input name="clientPageEnd" maxlength="5" size="8"
  				value="${clients_paging_last + 1}"/></td>
  			<td style="width:20%" align="center">
  			<spring:message code="label.managerpage.of" />
  			${clients_count}
  			<spring:message code="label.managerpage.total" /></td>
  			<td style="width:50%" align="left"><button>
  				<spring:message code="label.managerpage.buttonGo" /></button></td>  			
  		</tr>  		
  		</table>
  	</form>
  	<br>
  	<form method="post" action="listactiveordersforselectedclient" accept-charset="UTF-8">
  		<select name="clientId" onchange="this.form.submit();">
  			<option value="0">
  				<spring:message code="label.managerpage.started.selectClient" />
  			</option>  			
  			<c:forEach var="c" items="${clients_short}">
  				<c:choose>
  					<c:when test="${selected_client_id == c.clientId}">
  						<option selected value="${c.clientId}">
  							<c:out value="${c.clientName}"/>
  						</option>
  					</c:when>
  					<c:otherwise>
  						<option value="${c.clientId}">
  							<c:out value="${c.clientName}"/>
  						</option>
  					</c:otherwise>
  				</c:choose>
  			</c:forEach>
  		</select>
  	</form>  	
  	<c:choose>
	<c:when test="${empty started_orders_for_selected_client}">
		<br>
		<div style="text-align: center;">
		<span style="font-size: 18px; line-height: 18px;">
			<spring:message code="label.managerpage.noStartedOrders" />
		</span>
		</div>
	</c:when>
	<c:otherwise>		
		<br>
		<form method="post" action="managerpageactiveorders/startedorderspaging" 
			accept-charset="UTF-8">
  		<table>
  		<tr>
  			<td style="width:5%" align="center">
  				<spring:message code="label.managerpage.records" /></td>  			
  			<td style="width:10%" align="center">
  				<input name="startedOrdersPageStart" maxlength="5" size="8"
  				value="${started_orders_paging_first + 1}"/></td>  			
  			<td style="width:5%" align="center">
  				<spring:message code="label.managerpage.to" /></td>  			
  			<td style="width:10%" align="center">
  				<input name="startedOrdersPageEnd" maxlength="5" size="8"
  				value="${started_orders_paging_last + 1}"/></td>
  			<td style="width:20%" align="center">
  			<spring:message code="label.managerpage.of" />
  			${started_orders_count}
  			<spring:message code="label.managerpage.total" /></td>
  			<td style="width:50%" align="left"><button>
  				<spring:message code="label.managerpage.buttonGo" /></button></td>  			
  		</tr>  		
  		</table>
  		</form>
  	<table border="1" data-toggle="table" 
		data-classes="table table-hover table-condensed" 
    	data-striped="true"
    	data-pagination="true"
		data-search="true"
		data-show-columns="true"
		border="1" style="width:900px" align="center">
	<thead>
	<tr><th align="center" data-sortable="true" data-switchable="false"></th>
	<th align="center" data-sortable="true" data-visible="false">
		<spring:message code="label.managerpage.started.repairType" /></th>
	<th align="center" data-sortable="true" data-switchable="false">
		<spring:message code="label.managerpage.started.sn" /></th>
	<th align="center" data-sortable="true" data-visible="false">
		<spring:message code="label.managerpage.started.machineName" /></th>
	<th align="center" data-sortable="true" data-switchable="false">
		<spring:message code="label.managerpage.started.date" /></th>
	<th align="center" data-sortable="true" data-switchable="false">
		<spring:message code="label.managerpage.started.status" /></th>
	<th align="center" data-visible="false">
		<spring:message code="label.managerpage.started.manager" /></th>
	<th align="center" data-switchable="false">
		<spring:message code="label.managerpage.started.actions" /></th></tr>
	</thead>
	<tbody>	
  	<c:forEach var="so" items="${started_orders_for_selected_client}" varStatus="loopStatus">
  	<tr class="${loopStatus.index % 2 == 0 ? 'even' : 'odd'}">
    	<td><c:out value="${loopStatus.index + 1}"/></td>    	 
    	<td>
    		<c:choose>
  				<c:when test="${so.repairType.available == 0}">
  				<div class="disabled">
  					<c:choose>   			
    				<c:when test="${locale == 'ru'}">
    					${so.repairType.repairTypeNameRu}
    				</c:when>
    				<c:otherwise>
    					${so.repairType.repairTypeName}
    				</c:otherwise>
    				</c:choose>
    			</div>
    			</c:when>
    			<c:otherwise>
    			<c:choose>   			
    			<c:when test="${locale == 'ru'}">
    				${so.repairType.repairTypeNameRu}
    			</c:when>
    			<c:otherwise>
    				${so.repairType.repairTypeName}
    			</c:otherwise>
    			</c:choose>
    			</c:otherwise>
    		</c:choose>
    	</td>
    	<td>${so.machine.machineSerialNumber}</td>
    	<td>
    		<c:choose>
  				<c:when test="${so.machine.machineServiceable.available == 0}">
  				<div class="disabled">
    				${so.machine.machineServiceable.machineServiceableName}
    			</div>
    			</c:when>
    			<c:otherwise>
    				${so.machine.machineServiceable.machineServiceableName}
    			</c:otherwise>
    		</c:choose>
    	</td>
    	<td>${so.start}</td>
    	<td>
    		<c:choose>
				<c:when test="${locale == 'ru'}">
					<c:out value="${so.status.orderStatusNameRu}"/>
				</c:when>
				<c:otherwise>
					<c:out value="${so.status.orderStatusName}"/>
				</c:otherwise>
  			</c:choose>
    	</td>
    	<td>${so.manager}</td>
    	<c:if test="${so.status.orderStatusName == 'started'}">
   			<td align="center">
   			<a href="<c:url value="setready/?order_id=${so.orderId}" />"
   				onclick="return confirm('${dialog_setready_order}')">
   				<img src="resources/images/set_ready.png" width="24"></a>
   			</td>
   		</c:if>
    </tr>
  	</c:forEach>
  	</tbody>
  	</table>
  	</c:otherwise>
  	</c:choose>
  	<br>
  	<c:choose>
	<c:when test="${empty ready_orders_for_selected_client}">
		<br>
		<div style="text-align: center;">
		<span style="font-size: 18px; line-height: 18px;">
		<spring:message code="label.managerpage.noReadyOrders" />
		</span>
		</div>
	</c:when>
	<c:otherwise>
		<br>
		<form method="post" action="managerpageactiveorders/readyorderspaging" 
			accept-charset="UTF-8">
  		<table>
  		<tr>
  			<td style="width:5%" align="center">
  				<spring:message code="label.managerpage.records" /></td>  			
  			<td style="width:10%" align="center">
  				<input name="readyOrdersPageStart" maxlength="5" size="8"
  				value="${ready_orders_paging_first + 1}"/></td>  			
  			<td style="width:5%" align="center">
  				<spring:message code="label.managerpage.to" /></td>  			
  			<td style="width:10%" align="center">
  				<input name="readyOrdersPageEnd" maxlength="5" size="8"
  				value="${ready_orders_paging_last + 1}"/></td>
  			<td style="width:20%" align="center">
  			<spring:message code="label.managerpage.of" />
  			${ready_orders_count}
  			<spring:message code="label.managerpage.total" /></td>
  			<td style="width:50%" align="left"><button>
  				<spring:message code="label.managerpage.buttonGo" /></button></td>  			
  		</tr>  		
  		</table>
  		</form>
  	<table border="1" data-toggle="table" 
		data-classes="table table-hover table-condensed" 
    	data-striped="true"
    	data-pagination="true"
		data-search="true"
		data-show-columns="true"
		border="1" style="width:900px" align="center">
	<thead>
	<tr><th align="center" data-sortable="true" data-switchable="false"></th>
	<th align="center" data-sortable="true" data-visible="false">
		<spring:message code="label.managerpage.ready.repairType" /></th>
	<th align="center" data-sortable="true" data-switchable="false">
		<spring:message code="label.managerpage.ready.sn" /></th>
	<th align="center" data-sortable="true" data-visible="false">
		<spring:message code="label.managerpage.ready.machineName" /></th>
	<th align="center" data-sortable="true" data-switchable="false">
		<spring:message code="label.managerpage.ready.date" /></th>
	<th align="center" data-sortable="true" data-switchable="false">
		<spring:message code="label.managerpage.ready.status" /></th>
	<th align="center" data-visible="false">
		<spring:message code="label.managerpage.ready.manager" /></th></tr>	
	</thead>
	<tbody>	
  	<c:forEach var="ro" items="${ready_orders_for_selected_client}" varStatus="loopStatus">
  	<tr class="${loopStatus.index % 2 == 0 ? 'even' : 'odd'}">
    	<td><c:out value="${loopStatus.index + 1}"/></td>    	 
    	<td>
    		<c:choose>    			
  				<c:when test="${ro.repairType.available == 0}">
  					<div class="disabled">
  						<c:choose>
						<c:when test="${locale == 'ru'}">
    						${ro.repairType.repairTypeNameRu}
    					</c:when>
    					<c:otherwise>
    						${ro.repairType.repairTypeName}
    					</c:otherwise>
    					</c:choose>
					</div>
  				</c:when>
  				<c:otherwise>
  					<c:choose>
    				<c:when test="${locale == 'ru'}">
    					${ro.repairType.repairTypeNameRu}
    				</c:when>
    				<c:otherwise>
    					${ro.repairType.repairTypeName}
    				</c:otherwise>
    				</c:choose>
    			</c:otherwise>    			
    		</c:choose>
    	</td>
    	<td>${ro.machine.machineSerialNumber}</td>
    	<td>${ro.machine.machineServiceable.machineServiceableName}</td>
    	<td>${ro.start}</td>
    	<td>
    		<c:choose>
				<c:when test="${locale == 'ru'}">
					<c:out value="${ro.status.orderStatusNameRu}"/>
				</c:when>
				<c:otherwise>
					<c:out value="${ro.status.orderStatusName}"/>
				</c:otherwise>
  			</c:choose>
    	</td>
    	<td>${ro.manager}</td>    	
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