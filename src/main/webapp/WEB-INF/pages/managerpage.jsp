<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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

<title>Orders Management</title>
</head>

<body>	
	<h1 align = "center">ORDERS MANAGEMENT</h1>
	
	<div id="sidebar">
		<p><a href="<c:url value="/index"/>">Home</a></p>
		<hr>
		<c:if test="${fn:contains(user_token_authorities, 'ROLE_ADMIN')}">			
			<p><a href="<c:url value="/adminpage"/>">Switch to administrative tools</a></p>
			<hr>						
		</c:if>		
			<dl class="tabs vertical">
  			<dd class="active"><a href="#pending_orders">Pending orders</a></dd>
  			<dd><a href="#manage_active_orders">Manage active orders</a></dd>
		<hr>
		<p><a href="<c:url value="/logout"/>">Log out</a></p>
	</div>	
	
	<div id="content">
	<div class="tabs-content">
	<div class="content active" id="pending_orders">
	<h2>Pending Orders:</h2>
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
  			<td style="width:5%" align="center">orders&nbsp</td>  			
  			<td style="width:10%" align="center">
  				<input name="pendingOrdersPageStart" maxlength="5" size="8"
  				value="${pending_orders_paging_first + 1}"/></td>  			
  			<td style="width:5%" align="center">to</td>  			
  			<td style="width:10%" align="center">
  				<input name="pendingOrdersPageEnd" maxlength="5" size="8"
  				value="${pending_orders_paging_last + 1}"/></td>
  			<td style="width:20%" align="center">of ${pending_orders_count} in total</td>
  			<td style="width:50%" align="left"><button>Go</button></td>  			
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
		<th align="center" data-sortable="true" data-switchable="false">Client:</th>
		<th align="center" data-sortable="true" data-visible="false">RepairType:</th>
		<th align="center" data-sortable="true" data-switchable="false">Machine S/N:</th>
		<th align="center" data-sortable="true" data-visible="false">Machine Name:</th>
		<th align="center" data-sortable="true" data-switchable="false">Start:</th>
		<th align="center" data-sortable="true" data-switchable="false">Status:</th>
		<th align="center" data-visible="false">Actions:</th></tr>
		</thead>
		<tbody>	
  		<c:forEach var="po" items="${pending_orders}" varStatus="loopStatus">
  		<tr class="${loopStatus.index % 2 == 0 ? 'even' : 'odd'}">
    		<td><c:out value="${loopStatus.index + 1}"/></td>
    		<td>${po.client.clientName}</td> 
    		<td>${po.repairType.repairTypeName}</td>
    		<td>${po.machine.machineSerialNumber}</td>
    		<td>${po.machine.machineServiceable.machineServiceableName}</td>
    		<td>${po.start}</td>
    		<td>${po.status}</td>
    		<td><a href="<c:url value="confirm/?order_id=${po.orderId}" />">Confirm</a><br>
    		<a href="<c:url value="cancel/?order_id=${po.orderId}" />">Cancel</a></td>
    	</tr>
  		</c:forEach>
  	</tbody>
  	</table>
  	</c:otherwise>
  	</c:choose>
  	</div>
  	
  	<div class="content" id="manage_active_orders">
  	<h2>Manage Active Orders:</h2>
  	<form method="post" action="managerpage/clientpaging" accept-charset="UTF-8">
  		<table>
  		<tr>
  			<td style="width:5%" align="center">clients&nbsp</td>  			
  			<td style="width:10%" align="center">
  				<input name="clientPageStart" maxlength="5" size="8"
  				value="${clients_paging_first + 1}"/></td>  			
  			<td style="width:5%" align="center">to</td>  			
  			<td style="width:10%" align="center">
  				<input name="clientPageEnd" maxlength="5" size="8"
  				value="${clients_paging_last + 1}"/></td>
  			<td style="width:20%" align="center">of ${clients_count} in total</td>
  			<td style="width:50%" align="left"><button>Go</button></td>  			
  		</tr>  		
  		</table>
  	</form>
  	<br>
  	<form method="post" action="listactiveordersforselectedclient" accept-charset="UTF-8">
  		<select name="clientId" onchange="this.form.submit();">
  			<option value="0"><c:out value="-Select client-" /></option>  			
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
		<c:out value="No started orders found"/>
		</span>
		</div>
	</c:when>
	<c:otherwise>
		<br>
		<form method="post" action="managerpage/startedorderspaging" accept-charset="UTF-8">
  		<table>
  		<tr>
  			<td style="width:5%" align="center">orders&nbsp</td>  			
  			<td style="width:10%" align="center">
  				<input name="startedOrdersPageStart" maxlength="5" size="8"
  				value="${started_orders_paging_first + 1}"/></td>  			
  			<td style="width:5%" align="center">to</td>  			
  			<td style="width:10%" align="center">
  				<input name="startedOrdersPageEnd" maxlength="5" size="8"
  				value="${started_orders_paging_last + 1}"/></td>
  			<td style="width:20%" align="center">of ${started_orders_count} in total</td>
  			<td style="width:50%" align="left"><button>Go</button></td>  			
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
	<th align="center" data-sortable="true" data-visible="false">RepairType:</th>
	<th align="center" data-sortable="true" data-switchable="false">Machine S/N:</th>
	<th align="center" data-sortable="true" data-visible="false">Machine Name:</th>
	<th align="center" data-sortable="true" data-switchable="false">Start:</th>
	<th align="center" data-sortable="true" data-switchable="false">Status:</th>
	<th align="center" data-visible="false">Actions:</th></tr>
	</thead>
	<tbody>	
  	<c:forEach var="so" items="${started_orders_for_selected_client}" varStatus="loopStatus">
  	<tr class="${loopStatus.index % 2 == 0 ? 'even' : 'odd'}">
    	<td><c:out value="${loopStatus.index + 1}"/></td>    	 
    	<td>${so.repairType.repairTypeName}</td>
    	<td>${so.machine.machineSerialNumber}</td>
    	<td>${so.machine.machineServiceable.machineServiceableName}</td>
    	<td>${so.start}</td>
    	<td>${so.status}</td>
    	<c:if test="${so.status == 'started'}">
   			<td align="center">
   			<a href="<c:url value="setready/?order_id=${so.orderId}" />">Set Ready</a>
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
		<c:out value="No ready orders found"/>
		</span>
		</div>
	</c:when>
	<c:otherwise>
		<br>
		<form method="post" action="managerpage/readyorderspaging" accept-charset="UTF-8">
  		<table>
  		<tr>
  			<td style="width:5%" align="center">orders&nbsp</td>  			
  			<td style="width:10%" align="center">
  				<input name="readyOrdersPageStart" maxlength="5" size="8"
  				value="${ready_orders_paging_first + 1}"/></td>  			
  			<td style="width:5%" align="center">to</td>  			
  			<td style="width:10%" align="center">
  				<input name="readyOrdersPageEnd" maxlength="5" size="8"
  				value="${ready_orders_paging_last + 1}"/></td>
  			<td style="width:20%" align="center">of ${ready_orders_count} in total</td>
  			<td style="width:50%" align="left"><button>Go</button></td>  			
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
	<th align="center" data-sortable="true" data-visible="false">RepairType:</th>
	<th align="center" data-sortable="true" data-switchable="false">Machine S/N:</th>
	<th align="center" data-sortable="true" data-visible="false">Machine Name:</th>
	<th align="center" data-sortable="true" data-switchable="false">Start:</th>
	<th align="center" data-sortable="true" data-switchable="false">Status:</th>	
	</thead>
	<tbody>	
  	<c:forEach var="ro" items="${ready_orders_for_selected_client}" varStatus="loopStatus">
  	<tr class="${loopStatus.index % 2 == 0 ? 'even' : 'odd'}">
    	<td><c:out value="${loopStatus.index + 1}"/></td>    	 
    	<td>${ro.repairType.repairTypeName}</td>
    	<td>${ro.machine.machineSerialNumber}</td>
    	<td>${ro.machine.machineServiceable.machineServiceableName}</td>
    	<td>${ro.start}</td>
    	<td>${ro.status}</td>    	
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