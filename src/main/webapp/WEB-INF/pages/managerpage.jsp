<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<link href="<c:url value="/resources/css/general.css"/>" rel="stylesheet" type="text/css" />
<link href="<c:url value="/resources/css/ufd-base.css"/>" rel="stylesheet" type="text/css" />
<link href="<c:url value="/resources/css/plain.css"/>" rel="stylesheet" type="text/css" />
<link href="<c:url value="/resources/css/pager.css"/>" rel="stylesheet" type="text/css" />

<script src="<c:url value="/resources/js/jquery-1.11.1.min.js" />"></script>
<script src="<c:url value="/resources/js/jquery-ui.js" />"></script>
<script src="<c:url value="/resources/js/jquery.ui.ufd.js" />"></script>
<script src="<c:url value="/resources/js/pager.js" />"></script>

<title>Orders Management</title>
</head>

<body>
	
	<h1 align = "center">ORDERS MANAGEMENT</h1>
	
	<div id="sidebar">
		<p><a href="<c:url value="/index"/>">Home</a></p>
		<c:if test="${fn:contains(user_token_authorities, 'ROLE_ADMIN')}">			
			<p><a href="<c:url value="/adminpage"/>">Switch to administrative tools</a></p>						
		</c:if>
		<hr>
		<p><a href="<c:url value="/logout"/>">Log out</a></p>
	</div>	
	
	<div id="content">
	<h2>Pending Orders:</h2>
	<table border="1" style="width:900px" id="tablepaging1" class="yui" align="center">
	<tr><th align="center"></th><th align="center">Client:</th>
	<th align="center">RepairType:</th><th align="center">Machine S/N:</th>
	<th align="center">Machine Name:</th><th align="center">Start:</th>
	<th align="center">Status:</th><th align="center">Actions:</th></tr>	
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
  	</table>
  	<div id="pageNavPosition1" style="padding-top: 20px" align="center">
	</div>
	<script type="text/javascript"><!--
		var pager1 = new Pager('tablepaging1', 5);
		pager1.init();
		pager1.showPageNav('pager1', 'pageNavPosition1');
		pager1.showPage(1);
	</script>
  	
  	<h2>Manage Active Orders:</h2>
  	<form method="post" action="listactiveordersforselectedclient" accept-charset="UTF-8">
  		<select name="clientId" onchange="this.form.submit();">
  			<option value="0"><c:out value="-Select client-" /></option>
  			<c:forEach var="c" items="${clients}">
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
  	
  	<table border="1" style="width:900px" id="tablepaging2" class="yui" align="center">
	<tr><th align="center"></th><th align="center">Client:</th>
	<th align="center">RepairType:</th><th align="center">Machine S/N:</th>
	<th align="center">Machine Name:</th><th align="center">Start:</th>
	<th align="center">Status:</th><th align="center">Actions:</th></tr>	
  	<c:forEach var="ao" items="${active_orders_for_selected_client}" varStatus="loopStatus">
  	<tr class="${loopStatus.index % 2 == 0 ? 'even' : 'odd'}">
    	<td><c:out value="${loopStatus.index + 1}"/></td>
    	<td>${ao.client.clientName}</td> 
    	<td>${ao.repairType.repairTypeName}</td>
    	<td>${ao.machine.machineSerialNumber}</td>
    	<td>${ao.machine.machineServiceable.machineServiceableName}</td>
    	<td>${ao.start}</td>
    	<td>${ao.status}</td>
    	<c:if test="${ao.status == 'started'}">
   			<td align="center">
   			<a href="<c:url value="setready/?order_id=${ao.orderId}" />">Set Ready</a>
   			</td>
   		</c:if>
    </tr>
  	</c:forEach>
  	</table>
  	<div id="pageNavPosition2" style="padding-top: 20px" align="center">
	</div>
	<script type="text/javascript"><!--
		var pager2 = new Pager('tablepaging2', 5);
		pager2.init();
		pager2.showPageNav('pager2', 'pageNavPosition2');
		pager2.showPage(1);
	</script>
	</div>
  	
</body>
</html>