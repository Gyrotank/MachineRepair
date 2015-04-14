<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="springForm"%>
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

<title>Working Cabinet</title>

</head>

<body>
	<jsp:useBean id="now" class="java.util.Date" />
	<fmt:formatDate var="current_year" value="${now}" pattern="yyyy" />
		
	<div id="sidebar">
		<p><a href="<c:url value="/index"/>">Home</a>
		<hr>
			<dl class="tabs vertical">
  			<dd class="active"><a href="#create_orders">Create orders</a></dd>
  			<dd><a href="#current_orders">Current orders</a></dd>
  			<dd><a href="#past_orders">Past Orders</a></dd>  			
		</dl>
		<hr>
		<p><a href="<c:url value="/logout"/>">Log out</a></p>
	</div>
	
	<div id="content">
	<br>
	<h1>Welcome to the system, ${clientname}!</h1>
	<br>
	<div class="tabs-content">
	<div class="content active" id="create_orders">
	<h2>Create Order For First Time Repair:</h2>
  	<form method="post" action="createorderforfirstrepair" accept-charset="UTF-8">
  	<table>
  		<tr>
  		<td>Machine Name:&nbsp</td>
  		<td><select name="machineServiceableId">
		<option value="0"><c:out value="-Select machine-" /></option>
  		<c:forEach var="ms" items="${machines_serviceable}">
  			<c:choose>
  			<c:when test="${first_repair_selected_serviceable_id == ms.machineServiceableId}">
  				<option selected value="${ms.machineServiceableId}">
  				<c:out value="${ms.machineServiceableTrademark} - ${ms.machineServiceableName}" />
  				</option>
  			</c:when>
  			<c:otherwise>
  				<option value="${ms.machineServiceableId}">
  				<c:out value="${ms.machineServiceableTrademark} - ${ms.machineServiceableName}" />
  				</option>
  			</c:otherwise>
  			</c:choose>
  		</c:forEach>
  		</select></td>
  		<td><small><font color="red">
     		<c:out value="${message_first_repair_serviceable_id}"/>
   		</font></small></td>  		  		
  		</tr>
  		<tr>
  		<td>S/N:</td>
  		<td><input type="text" name="machineSerialNumber" maxlength="32"
  				 value="${first_repair_entered_serial_number}"/></td>
  		<td><small><font color="red">
  			<c:out value="${message_first_repair_serial_number}"/>
  		</font></small></td>
  		</tr>
  		<tr> 
  		<td>Year:</td>  		
  		<td><input type="text" name="machineYear" size="4" maxlength="4"
  				 value="${first_repair_selected_year}"/></td>
		<td><small><font color="red">
			<c:out value="${message_first_repair_year}"/>
		</font></small></td>
		</tr>
		<tr>
  		<td>Repair Type:</td>
  		<td><select name="repairTypeId">
		<option value="0"><c:out value="-Select repair type-" /></option>
  		<c:forEach var="rt" items="${repair_types}">
  			<c:choose>
  			<c:when test="${first_repair_selected_repairtype_id == rt.repairTypeId}">
  				<option selected value="${rt.repairTypeId}">
  				<c:out value="${rt.repairTypeName} (${rt.repairTypePrice})" />
  				</option>
  			</c:when>
  			<c:otherwise>
  				<option value="${rt.repairTypeId}">
  				<c:out value="${rt.repairTypeName} (${rt.repairTypePrice})" />
  				</option>
  			</c:otherwise>
  			</c:choose>  			
  		</c:forEach>
  		</select></td>
  		<td><small><font color="red">
  			<c:out value="${message_first_repair_repairtype_id}"/>
  		</font></small></td>
  		</tr>
  		<tr>
  			<td><button>Create</button></td>
  		</tr>
  	</table>
  	</form>
  	<br>
  	<c:choose>
	<c:when test="${(empty my_past_orders) && (empty my_current_orders)}">				
	</c:when>
	<c:otherwise>
  	<h2>Create Order For Repeated Repair:</h2>
  	<c:out value="${message_repeated_order}"/>
  	<form method="post" action="createorderforrepeatedrepair" accept-charset="UTF-8">
  	<table>
  		<tr>
  		<td>S/N:</td>
  		<td><select name="machineSerialNumber">
  		<option value=""><c:out value="-Select serial number-" /></option>
  		<c:forEach var="sn" items="${my_machines_serial_numbers}">
  			<c:choose>
  			<c:when test="${repeated_repair_entered_serial_number == sn}">
  				<option selected value="${sn}"><c:out value="${sn}" /></option>
  			</c:when>
  			<c:otherwise>
  				<option value="${sn}"><c:out value="${sn}" /></option>
  			</c:otherwise>
  			</c:choose>  			
  		</c:forEach>
  		</select>
  		</td>
  		<td><small><font color="red">
  			<c:out value="${message_repeated_repair_serial_number}"/>
  		</font></small></td>
  		</tr> 
  		<tr> 		
		<td>Repair Type:&nbsp</td>
		<td><select name="repairTypeId">
		<option value="0"><c:out value="-Select repair type-" /></option>
  		<c:forEach var="rt" items="${repair_types}">
  			<c:choose>
  			<c:when test="${repeated_repair_selected_repairtype_id == rt.repairTypeId}">
  				<option selected value="${rt.repairTypeId}">
  				<c:out value="${rt.repairTypeName} (${rt.repairTypePrice})" />
  				</option>
  			</c:when>
  			<c:otherwise>
  				<option value="${rt.repairTypeId}">
  				<c:out value="${rt.repairTypeName} (${rt.repairTypePrice})" />
  				</option>
  			</c:otherwise>
  			</c:choose>
  		</c:forEach>
  		</select></td>
  		<td><small><font color="red">
  			<c:out value="${message_repeated_repair_repairtype_id}"/>
  		</font></small></td>
  		</tr>
  		<tr>
  			<td><button>Create</button></td>
  		</tr>
  	</table>
  	</form>
  	</c:otherwise>
  	</c:choose>
  	</div>  	  	
  	<br>
	
	<div class="content" id="current_orders">	  		
	<h2>Your Current Orders:</h2>
	<c:choose>
	<c:when test="${empty my_current_orders}">
		<br>
		<span style="font-size: 18px; line-height: 18px;">
		<c:out value="${message_current_orders}"/>
		<br><br>
		</span>		
	</c:when>
	<c:otherwise>
	<br>
		<form method="post" action="clientpage/currentorderspaging" accept-charset="UTF-8">
  		<table>
  		<tr>
  			<td style="width:5%" align="center">records</td>  			
  			<td style="width:10%" align="center">
  				<input name="currentOrdersPageStart" maxlength="5" size="8"
  				value="${current_orders_paging_first + 1}"/></td>  			
  			<td style="width:5%" align="center">to</td>  			
  			<td style="width:10%" align="center">
  				<input name="currentOrdersPageEnd" maxlength="5" size="8"
  				value="${current_orders_paging_last + 1}"/></td>
  			<td style="width:20%" align="center">of ${current_orders_count} in total</td>
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
	<th align="center" data-sortable="true" data-switchable="false">RepairType:</th>
	<th align="center" data-sortable="true" data-switchable="false">Machine S/N:</th>
	<th align="center" data-sortable="true" data-visible="false">Machine Name:</th>
	<th align="center" data-sortable="true" data-switchable="false">Start:</th>
	<th align="center" data-sortable="true" data-switchable="false">Status:</th>
	<th align="center" data-visible="false">Actions:</th></tr>
	</thead>
	<tbody>
  	<c:forEach var="co" items="${my_current_orders}" varStatus="loopStatus">
  	<tr class="${loopStatus.index % 2 == 0 ? 'even' : 'odd'}">
    	<td><c:out value="${loopStatus.index + 1}"/></td>    	 
    	<td>${co.repairType.repairTypeName}</td>
    	<td>${co.machine.machineSerialNumber}</td>
    	<td>${co.machine.machineServiceable.machineServiceableName}</td>
    	<td>${co.start}</td>
    	<td>${co.status}</td>
    	<c:if test="${co.status == 'ready'}">
   			<td align="center">
   			<a href="<c:url value="pay/?order_id=${co.orderId}" />">Pay</a>
   			</td>
		</c:if>    	
    </tr>
  	</c:forEach>
  	</tbody>
  	</table>
  	</c:otherwise>
  	</c:choose>
  	</div>
  	
  	<br><hr>
  	
  	<div class="content" id="past_orders">
  	<h2>Your Past Orders:</h2>
	<c:choose>
	<c:when test="${empty my_past_orders}">
		<br>		
		<span style="font-size: 18px; line-height: 18px;">
		<c:out value="${message_past_orders}"/>
		<br><br><br>
		</span>		
	</c:when>
	<c:otherwise>
		<br>
		<form method="post" action="clientpage/pastorderspaging" accept-charset="UTF-8">
  		<table>
  		<tr>
  			<td style="width:5%" align="center">records</td>  			
  			<td style="width:10%" align="center">
  				<input name="pastOrdersPageStart" maxlength="5" size="8"
  				value="${past_orders_paging_first + 1}"/></td>  			
  			<td style="width:5%" align="center">to</td>  			
  			<td style="width:10%" align="center">
  				<input name="pastOrdersPageEnd" maxlength="5" size="8"
  				value="${past_orders_paging_last + 1}"/></td>
  			<td style="width:20%" align="center">of ${past_orders_count} in total</td>
  			<td style="width:50%" align="left"><button>Go</button></td>  			
  		</tr>  		
  		</table>
  		</form>	
	<table data-toggle="table" 
		data-classes="table table-hover table-condensed" 
    	data-striped="true"
    	data-pagination="true"
		data-search="true"
		border="1" style="width:900px" align="center">
	<thead>
	<tr><th align="center" data-sortable="true"></th>
	<th align="center" data-sortable="true">RepairType:</th>
	<th align="center" data-sortable="true">Machine S/N:</th>
	<th align="center" data-sortable="true">Machine Name:</th>
	<th align="center" data-sortable="true">Start:</th>
	<th align="center" data-sortable="true">Status:</th></tr>
	</thead>
	<tbody>
  	<c:forEach var="po" items="${my_past_orders}" varStatus="loopStatus">
  	<tr class="${loopStatus.index % 2 == 0 ? 'even' : 'odd'}">
    	<td><c:out value="${loopStatus.index + 1}"/></td>    	 
    	<td>${po.repairType.repairTypeName}</td>
    	<td>${po.machine.machineSerialNumber}</td>
    	<td>${po.machine.machineServiceable.machineServiceableName}</td>
    	<td>${po.start}</td>
    	<td>${po.status}</td>    	
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