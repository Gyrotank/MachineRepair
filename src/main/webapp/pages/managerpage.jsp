<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Orders Management</title>

<style>
h3.left {
    position: absolute;
    left: 10px;
    top: 0px;
}

h3.right {
    position: absolute;
    right: 10px;
    top: 0px;
}

tr.odd {background-color: #EEDDEE}

tr.even {background-color: #EEEEDD}
</style>

</head>
<body>
	
	<h3 class="left"><a href="<c:url value="/index"/>">Home</a>
	<c:if test="${fn:contains(user_token_authorities, 'ROLE_ADMIN')}">
		<br>
		<a href="<c:url value="/adminpage"/>">Switch to administrative tools</a>						
	</c:if></h3>
	<h3 class="right"><a href="<c:url value="/logout"/>">Log out</a></h3>
	<br>
	<br>
	<br>
	<h2>Pending Orders:</h2>
	<table border="1" style="width:1000px">
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
  	
  	<h2>Manage Active Orders:</h2>
  	<form method="post" action="listactiveordersforselectedclient">
  		<select name="clientId" onchange="this.form.submit();">
  			<option value="0"><c:out value="---" /></option>
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
  	
  	<table border="1" style="width:1000px">
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
  	
</body>
</html>