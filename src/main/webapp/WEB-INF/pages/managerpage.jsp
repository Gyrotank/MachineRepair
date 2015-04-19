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
		<p><a href="<c:url value="/index"/>"><spring:message code="label.managerpage.sidebar.index" /></a></p>
		<hr class="style-seven">
		<c:if test="${fn:contains(user_token_authorities, 'ROLE_ADMIN')}">			
			<p><a href="<c:url value="/adminpage"/>">
				<spring:message code="label.managerpage.sidebar.adminpage" /></a></p>
			<hr class="style-seven">						
		</c:if>		
			<dl class="tabs vertical">
  			<dd class="active"><a href="#pending_orders">
  				<spring:message code="label.managerpage.sidebar.pending" /></a></dd>
  			<dd><a href="#manage_active_orders">
  				<spring:message code="label.managerpage.sidebar.active" /></a></dd>
		<hr class="style-seven">
		<p><a href="<c:url value="/logout"/>">
			<spring:message code="label.managerpage.sidebar.logout" /></a></p>
	</div>	
	
	<div id="content">
	<div class="tabs-content">
	<div class="content active" id="pending_orders">
	<h2><spring:message code="label.managerpage.pending" /></h2>
	<c:choose>
	<c:when test="${empty pending_orders}">
		<br>
		<div style="text-align: center;">
		<span style="font-size: 18px; line-height: 18px;">
		<c:out value="No matching records found"/>
		<br><br><br>
		</span>
		</div>
	</c:when>
	<c:otherwise>
		<form method="post" action="managerpage/pendingorderspaging" accept-charset="UTF-8">
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
		<th align="center" data-visible="false">
			<spring:message code="label.managerpage.pending.actions" /></th></tr>
		</thead>
		<tbody>	
  		<c:forEach var="po" items="${pending_orders}" varStatus="loopStatus">
  		<tr class="${loopStatus.index % 2 == 0 ? 'even' : 'odd'}">
    		<td><c:out value="${loopStatus.index + 1}"/></td>
    		<td>${po.client.clientName}</td> 
    		<td>    			
    			<c:choose>
    			<c:when test="${locale == 'ru'}">
    				${po.repairType.repairTypeNameRu}
    			</c:when>
    			<c:otherwise>
    				${po.repairType.repairTypeName}
    			</c:otherwise>
    			</c:choose>
    		</td>
    		<td>${po.machine.machineSerialNumber}</td>
    		<td>${po.machine.machineServiceable.machineServiceableName}</td>
    		<td>${po.start}</td>    		
    		<td><a href="<c:url value="confirm/?order_id=${po.orderId}" />">
    			<spring:message code="label.managerpage.pending.actions.confirm" /></a><br>
    		<a href="<c:url value="cancel/?order_id=${po.orderId}" />">
    			<spring:message code="label.managerpage.pending.actions.cancel" /></a></td>
    	</tr>
  		</c:forEach>
  	</tbody>
  	</table>
  	</c:otherwise>
  	</c:choose>
  	</div>
  	
  	<hr class="style-seven">
  	
  	<div class="content" id="manage_active_orders">
  	<h2><spring:message code="label.managerpage.active" /></h2>
  	<form method="post" action="managerpage/clientpaging" accept-charset="UTF-8">
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
		<form method="post" action="managerpage/startedorderspaging" accept-charset="UTF-8">
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
	<th align="center" data-visible="false">
		<spring:message code="label.managerpage.started.actions" /></th></tr>
	</thead>
	<tbody>	
  	<c:forEach var="so" items="${started_orders_for_selected_client}" varStatus="loopStatus">
  	<tr class="${loopStatus.index % 2 == 0 ? 'even' : 'odd'}">
    	<td><c:out value="${loopStatus.index + 1}"/></td>    	 
    	<td>
    		<c:choose>
    			<c:when test="${locale == 'ru'}">
    				${so.repairType.repairTypeNameRu}
    			</c:when>
    			<c:otherwise>
    				${so.repairType.repairTypeName}
    			</c:otherwise>
    		</c:choose>
    	</td>
    	<td>${so.machine.machineSerialNumber}</td>
    	<td>${so.machine.machineServiceable.machineServiceableName}</td>
    	<td>${so.start}</td>
    	<td>
    		<c:choose>
  				<c:when test="${so.status == 'pending' && locale == 'ru'}">
  					<c:out value="в обработке"/>
  				</c:when>
  				<c:when test="${so.status == 'started' && locale == 'ru'}">
  					<c:out value="выполняется"/>
	  			</c:when>
  				<c:when test="${so.status == 'ready' && locale == 'ru'}">
  					<c:out value="готов"/>
  				</c:when>
  				<c:when test="${so.status == 'finished' && locale == 'ru'}">
  					<c:out value="завершен"/>
  				</c:when>
  				</c:choose>
    			<c:choose>
  				<c:when test="${so.status == 'pending' && locale == 'en'}">
	  				<c:out value="pending"/>
  				</c:when>
  				<c:when test="${so.status == 'started' && locale == 'en'}">
  					<c:out value="started"/>
  				</c:when>
	  			<c:when test="${so.status == 'ready' && locale == 'en'}">
  					<c:out value="ready"/>
  				</c:when>
  				<c:when test="${so.status == 'finished' && locale == 'en'}">
  					<c:out value="finished"/>
  				</c:when>
  			</c:choose>
    	</td>
    	<td>${so.manager}</td>
    	<c:if test="${so.status == 'started'}">
   			<td align="center">
   			<a href="<c:url value="setready/?order_id=${so.orderId}" />">
   				<spring:message code="label.managerpage.started.actions.setReady" /></a>
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
		<form method="post" action="managerpage/readyorderspaging" accept-charset="UTF-8">
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
    			<c:when test="${locale == 'ru'}">
    				${ro.repairType.repairTypeNameRu}
    			</c:when>
    			<c:otherwise>
    				${ro.repairType.repairTypeName}
    			</c:otherwise>
    		</c:choose>
    	</td>
    	<td>${ro.machine.machineSerialNumber}</td>
    	<td>${ro.machine.machineServiceable.machineServiceableName}</td>
    	<td>${ro.start}</td>
    	<td>
    		<c:choose>
  				<c:when test="${ro.status == 'pending' && locale == 'ru'}">
  					<c:out value="в обработке"/>
  				</c:when>
  				<c:when test="${ro.status == 'started' && locale == 'ru'}">
  					<c:out value="выполняется"/>
	  			</c:when>
  				<c:when test="${ro.status == 'ready' && locale == 'ru'}">
  					<c:out value="готов"/>
  				</c:when>
  				<c:when test="${ro.status == 'finished' && locale == 'ru'}">
  					<c:out value="завершен"/>
  				</c:when>
  				</c:choose>
    			<c:choose>
  				<c:when test="${ro.status == 'pending' && locale == 'en'}">
	  				<c:out value="pending"/>
  				</c:when>
  				<c:when test="${ro.status == 'started' && locale == 'en'}">
  					<c:out value="started"/>
  				</c:when>
	  			<c:when test="${ro.status == 'ready' && locale == 'en'}">
  					<c:out value="ready"/>
  				</c:when>
  				<c:when test="${ro.status == 'finished' && locale == 'en'}">
  					<c:out value="finished"/>
  				</c:when>
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