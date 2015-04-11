<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
<link href="<c:url value="/resources/css/pager.css"/>" rel="stylesheet" type="text/css" />

<script src="<c:url value="/resources/js/jquery-1.11.1.min.js" />"></script>
<script src="<c:url value="/resources/js/jquery-ui.js" />"></script>
<script src="<c:url value="/resources/js/jquery.ui.ufd.js" />"></script>
<script src="<c:url value="/resources/js/pager.js" />"></script>

<title>Administrative Tools</title>

</head>

<body>
	
	<jsp:useBean id="now" class="java.util.Date" />
	<fmt:formatDate var="current_year" value="${now}" pattern="yyyy" />
	
	<h1 align = "center">ADMINISTRATIVE TOOLS</h1>
	
	<div id="sidebar">
		<p><a href="<c:url value="/index"/>">Home</a></p>		
		<p><a href="<c:url value="/managerpage"/>">Switch to orders management</a></p>
		<hr>
	<dl class="tabs vertical">
  		<dd class="active"><a href="#machines">Machines</a></dd>
  		<dd><a href="#serviceable_machines">Serviceable Machines</a></dd>
  		<dd><a href="#repair_types">Repair Types</a></dd>
  		<dd><a href="#users">Users</a></dd>
  		<dd><a href="#user_auths">User Authorizations</a></dd>
  		<dd><a href="#clients">Clients</a></dd>
  		<dd><a href="#orders">Orders</a></dd>
	</dl>
		<hr>
		<p><a href="<c:url value="/logout"/>">Log out</a></p>
	</div>
	
	<div id="content">
	<div class="tabs-content">
	<div class="content active" id="machines">
	<h1>Machines</h1>
	<table border="1" style="width:900px" id="tablepaging1" class="yui" align="center">
	<tr><th align="center"></th><th align="center">M_S_Name:</th>
	<th align="center">S/N:</th>
	<th align="center">Year:</th><th align="center">Times Repaired:</th></tr>
  	<c:forEach var="m" items="${machines}" varStatus="loopStatus">    	
    <tr class="${loopStatus.index % 2 == 0 ? 'even' : 'odd'}">
    	<td><c:out value="${loopStatus.index + 1}"/></td>
    	<td>${m.machineServiceable.machineServiceableName}</td> 
    	<td>${m.machineSerialNumber}</td>
    	<td>${m.machineYear}</td>
    	<td>${m.machineTimesRepaired}</td>    	
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
  	
  	<div>
	<h2>Add New Machine:</h2>
  	<form:form method="post" commandName="machine" action="addMachine" accept-charset="UTF-8">
  	<table>
  		<tr>
  			<td>Machine Name: </td>
  			<td>
  				<script type="text/javascript">
  					$("#machineServiceableId").ufd();
				</script>
  				<select id="machineServiceableId" name="machineServiceableId">
  				<option value="0"><c:out value="-Select machine name-" /></option>
  				<c:forEach var="ms" items="${machines_serviceable}">
  				<c:choose>
  					<c:when test="${selected_machineserviceable_id == ms.machineServiceableId}">
  						<option selected value="${ms.machineServiceableId}">
  							<c:out value="${ms.machineServiceableName}"/>
  						</option>
  					</c:when>
  					<c:otherwise>
  						<option value="${ms.machineServiceableId}">
  							<c:out value="${ms.machineServiceableName}"/>
  						</option>
  					</c:otherwise>
  				</c:choose>  					
  				</c:forEach>
  				</select>  				
  				</td>
  				<td>
  					<div class="error">
  						<c:out value="${message_machineserviceable_id}"/>
  					</div>
  				</td>
  		</tr>
  		<tr>
  			<td><label for="machineSerialNumberInput">S/N: </label></td>
  			<td><form:input path="machineSerialNumber" id="machineSerialNumberInput"
  					maxlength="32" /></td>
  			<td><form:errors path="machineSerialNumber" cssClass="error" /></td>
  		</tr>
  		<tr>  		
  			<td><label for="machineYearInput">Year: </label></td>
  			<td><form:input size="4" minlength="4" maxlength="4"
  				 path="machineYear" id="machineYearInput"/></td>
  			<td><form:errors path="machineYear" cssClass="error" /></td>  			
		</tr>
		<tr>
  			<td><label for="machineTimesRepairedInput">Times Repaired: </label></td>
  			<td><form:input size="5" maxlength="3"
  				 path="machineTimesRepaired" id="machineTimesRepairedInput"/></td>
  			<td><form:errors path="machineTimesRepaired" cssClass="error" /></td>  		
  		</tr>
  		<tr>
  			<td><button>Add</button></td>
  		</tr>
  	</table>
  	</form:form>  	
  	</div>
  	</div>
  	
  	<br><hr>
  	
  	<div class="content" id="serviceable_machines">
  	<h1>Serviceable Machines</h1>
  	<table border="1" style="width:900px" id="tablepaging2" class="yui" align="center">
	<tr><th align="center"></th><th align="center">Name:</th>
	<th align="center">Trademark:</th><th align="center">Country:</th></tr>
  	<c:forEach var="ms" items="${machines_serviceable}" varStatus="loopStatus">    	
    <tr class="${loopStatus.index % 2 == 0 ? 'even' : 'odd'}">
    	<td><c:out value="${loopStatus.index + 1}"/></td>
    	<td>${ms.machineServiceableName}</td> 
    	<td>${ms.machineServiceableTrademark}</td>
    	<td>${ms.machineServiceableCountry}</td>    	   	
    </tr>
  	</c:forEach>
  	</table>
  	<div id="pageNavPosition2" style="padding-top: 20px" align="center">
	</div>
	<script type="text/javascript">
		var pager2 = new Pager('tablepaging2', 5);
		pager2.init();
		pager2.showPageNav('pager2', 'pageNavPosition2');
		pager2.showPage(1);
	</script>
  	
  	<h2>Add New Serviceable Machine:</h2>
  	<form:form method="post" commandName="machineServiceable" action="addMachineServiceable" accept-charset="UTF-8">
  	<table>
  		<tr>
  			<td><label for="machineServiceableNameInput">Name: </label></td>
  			<td><form:input path="machineServiceableName" id="machineServiceableNameInput"
  					maxlength="50"/></td>
  			<td><form:errors path="machineServiceableName" cssClass="error" /></td>  			
  		</tr>
  		<tr>
  			<td><label for="machineServiceableNameInput">Trademark: </label></td>
  			<td>
  			<form:input path="machineServiceableTrademark" id="machineServiceableTrademarkInput"
  					maxlength="50"/>
  			</td>
  			<td><form:errors path="machineServiceableTrademark" cssClass="error" /></td>
  		</tr>
  		<tr>
  			<td><label for="machineServiceableCountryInput">Country: </label></td>
  			<td>
  			<form:input path="machineServiceableCountry" id="machineServiceableCountryInput"
  					maxlength="50"/>
  			</td>
  			<td><form:errors path="machineServiceableCountry" cssClass="error" /></td>  			
  		</tr>
  		<tr> 		
  			<td><button>Add</button></td>
  		</tr>
  	</table>
  	</form:form>
  	</div>
  	
  	<br><hr>
    
    <div class="content" id="repair_types">
  	<h1>Repair Types</h1>
  	<table border="1" style="width:900px" id="tablepaging3" class="yui" align="center">
	<tr><th align="center"></th><th align="center">Name:</th>
	<th align="center">Price:</th><th align="center">Duration:</th></tr>
  	<c:forEach var="rt" items="${repair_types}" varStatus="loopStatus">    	
    <tr class="${loopStatus.index % 2 == 0 ? 'even' : 'odd'}">
    	<td><c:out value="${loopStatus.index + 1}"/></td>
    	<td>${rt.repairTypeName}</td> 
    	<td>${rt.repairTypePrice}</td>
    	<td>${rt.repairTypeDuration}</td>    	   	
    </tr>
  	</c:forEach>
  	</table>
  	<div id="pageNavPosition3" style="padding-top: 20px" align="center">
	</div>
	<script type="text/javascript"><!--
		var pager3 = new Pager('tablepaging3', 5);
		pager3.init();
		pager3.showPageNav('pager3', 'pageNavPosition3');
		pager3.showPage(1);
	</script>
  
  	<h2>Add New Repair Type</h2>
  	<form:form method="post" commandName="repairType" action="addRepairType" accept-charset="UTF-8">
  	<table>
  		<tr>
  			<td><label for="repairTypeNameInput">Name: </label></td>
  			<td><form:input path="repairTypeName" id="repairTypeNameInput"
  					maxlength="20"/></td>
  			<td><form:errors path="repairTypeName" cssClass="error" /></td>  			
  		</tr>
  		<tr>
  			<td><label for="repairTypePriceInput">Price: </label></td>
  			<td><form:input path="repairTypePrice" id="repairTypePriceInput"
  					maxlength="11"/></td>
  			<td><form:errors path="repairTypePrice" cssClass="error" /></td>  			
  		</tr>
  		<tr>
  			<td><label for="repairTypeDurationInput">Duration: </label></td>
  			<td><form:input path="repairTypeDuration" id="repairTypeDurationInput"
  					maxlength="2"/></td>
  			<td><form:errors path="repairTypeDuration" cssClass="error" /></td>  			
  		</tr>
  		<tr>
  			<td><button>Add</button></td>
  		</tr>  		
  	</table>
  	</form:form>
  	</div>
  	
  	<br><hr>
    
    <div class="content" id="users">
  	<h1>Users</h1>
  	<table border="1" style="width:900px" id="tablepaging4" class="yui" align="center">
	<tr><th align="center"></th><th align="center">Login:</th>
	<th align="center">Password:</th></tr>
  	<c:forEach var="u" items="${users}" varStatus="loopStatus">    	
    <tr class="${loopStatus.index % 2 == 0 ? 'even' : 'odd'}">
    	<td><c:out value="${loopStatus.index + 1}"/></td>
    	<td>${u.login}</td> 
    	<td>${u.password}</td>
    </tr>
  	</c:forEach>
  	</table>
  	<div id="pageNavPosition4" style="padding-top: 20px" align="center">
	</div>
	<script type="text/javascript"><!--
		var pager4 = new Pager('tablepaging4', 5);
		pager4.init();
		pager4.showPageNav('pager4', 'pageNavPosition4');
		pager4.showPage(1);
	</script>
  	
  	<h2>Add New User</h2>
  	<form:form method="post" commandName="user" action="addUser" accept-charset="UTF-8">
  	<table>
  		<tr>
  			<td><label for="loginInput">Login: </label></td>
  			<td><form:input path="login" id="loginInput" maxlength="50"/></td>
  			<td><form:errors path="login" cssClass="error" /></td>  			
  		</tr>
  		<tr>
  			<td><label for="passwordTextInput">Password: </label></td>
  			<td><form:input path="passwordText" id="passwordTextInput" maxlength="50"/></td>
  			<td><form:errors path="passwordText" cssClass="error" /></td>  			
  		</tr>
  		<tr>
  			<td><button>Add</button></td>
  		</tr>
  	</table>
  	</form:form>
  	</div>
  	
  	<br><hr>
  	
  	<div class="content" id="user_auths">
  	<h1>User Authorizations</h1>
  	<table border="1" style="width:900px" id="tablepaging5" class="yui" align="center">
	<tr><th align="center"></th><th align="center">Login:</th>
	<th align="center">Role:</th></tr>
  	<c:forEach var="ua" items="${user_authorizations}" varStatus="loopStatus">    	
    <tr class="${loopStatus.index % 2 == 0 ? 'even' : 'odd'}">
    	<td><c:out value="${loopStatus.index + 1}"/></td>
    	<td>${ua.user.login}</td> 
    	<td>
    		<c:choose>
  				<c:when test="${ua.role == 'ROLE_ADMIN'}">
  					<c:out value="Administrator"/>  					
  				</c:when>
  				<c:when test="${ua.role == 'ROLE_MANAGER'}">
  					<c:out value="Manager"/>  					
  				</c:when>
  				<c:otherwise>
  					<c:out value="Client"/>  					
  				</c:otherwise>
  			</c:choose>
    	</td>    	    	   	
    </tr>
  	</c:forEach>
  	</table>
  	<div id="pageNavPosition5" style="padding-top: 20px" align="center">
	</div>
	<script type="text/javascript"><!--
		var pager5 = new Pager('tablepaging5', 5);
		pager5.init();
		pager5.showPageNav('pager5', 'pageNavPosition5');
		pager5.showPage(1);
	</script>
  	
  	<h2>Add New User Authorization</h2>
  	<form:form method="post" commandName="userAuthorization" action="addUserAuthorization" accept-charset="UTF-8">
  	<table>
  		<tr>
  		<td>User: </td>  		
  		<td><select name="userId">
  			<option value="0"><c:out value="-Select user-" /></option>
  			<c:forEach var="u" items="${users}">
  				<c:choose>
  					<c:when test="${selected_user_authorization_user_id == u.userId}">
  						<option selected value="${u.userId}">
  							<c:out value="${u.login}"/>
  						</option>
  					</c:when>
  					<c:otherwise>
  						<option value="${u.userId}">
  							<c:out value="${u.login}"/>
  						</option>
  					</c:otherwise>
  				</c:choose>  				
  			</c:forEach>
  		</select></td>
  		<td>
  			<div class="error">
  				<c:out value="${message_user_authorization_user_id}"/>
  			</div>
  		</td>
  		</tr>
  		<tr>  		
  		<td>Role: </td>
  		<td><form:select path="role">
  			<form:option value=""><c:out value="-Select role-" /></form:option>
  			<c:forEach var="r" items="${user_roles}">
  				<form:option value="${r}">
  					<c:out value="${r}"/></form:option>
  			</c:forEach>
  		</form:select></td>
  		<td><form:errors path="role" cssClass="error" /></td>
  		</tr>
  		<tr>  		  
  		<td><button>Add</button></td>
  		</tr>
  	</table>
  	</form:form>
  	</div>
  	
  	<br><hr>
  	
  	<div class="content" id="clients">  
  	<h1>Clients</h1>
  	<table border="1" style="width:900px" id="tablepaging6" class="yui" align="center">
	<tr><th align="center"></th><th align="center">Name:</th>
	<th align="center">Login:</th></tr>
  	<c:forEach var="c" items="${clients}" varStatus="loopStatus">    	
    <tr class="${loopStatus.index % 2 == 0 ? 'even' : 'odd'}">
    	<td><c:out value="${loopStatus.index + 1}"/></td>
    	<td>${c.clientName}</td> 
    	<td>${c.clientUser.login}</td>    	    	   	
    </tr>
  	</c:forEach>
  	</table>
  	<div id="pageNavPosition6" style="padding-top: 20px" align="center">
	</div>
	<script type="text/javascript">
		var pager6 = new Pager('tablepaging6', 5);
		pager6.init();
		pager6.showPageNav('pager6', 'pageNavPosition6');
		pager6.showPage(1);
	</script>
  
  	<h2>Add New Client</h2>
  	<form:form method="post" commandName="client" action="addClient" accept-charset="UTF-8">
  	<table>
  		<tr>
  			<td><label for="clientNameInput">Name: </label></td>
  			<td><form:input path="clientName" id="clientNameInput" maxlength="50"/></td>
  			<td><form:errors path="clientName" cssClass="error" /></td>  			
  		</tr>
  		<tr>
  			<td>User:</td>
  			<td><select name="userId">
  				<option value="0"><c:out value="-Select user-" /></option>
  				<c:forEach var="u" items="${users}">
  					<c:choose>
  					<c:when test="${selected_client_user_id == u.userId}">
  						<option selected value="${u.userId}">
  							<c:out value="${u.login}"/>
  						</option>
  					</c:when>
  					<c:otherwise>
  						<option value="${u.userId}">
  							<c:out value="${u.login}"/>
  						</option>
  					</c:otherwise>
  					</c:choose>  					
  				</c:forEach>
  			</select></td>
  			<td>
  			<div class="error">
  				<c:out value="${message_client_user_id}"/>
  			</div>
  			</td>
  		</tr>
  		<tr>  		
  			<td><button>Add</button></td>
  		</tr>
  	</table>
  	</form:form>
  	</div>
  	
  	<br><hr>
  
    <div class="content" id="orders">
  	<h1>Orders</h1>
  	<table border="1" style="width:900px" id="tablepaging7" class="yui" align="center">
	<tr><th align="center"></th><th align="center">Client:</th>
	<th align="center">Repair Type:</th><th align="center">Machine:</th>
	<th align="center">Machine S/N:</th>
	<th align="center">Application Date:</th><th align="center">Status:</th></tr>
  	<c:forEach var="o" items="${orders}" varStatus="loopStatus">    	
    <tr class="${loopStatus.index % 2 == 0 ? 'even' : 'odd'}">
    	<td><c:out value="${loopStatus.index + 1}"/></td>
    	<td>${o.client.clientName}</td> 
    	<td>${o.repairType.repairTypeName}</td>
    	<td>${o.machine.machineServiceable.machineServiceableName}</td>
    	<td>${o.machine.machineSerialNumber}</td>
    	<td>${o.start.date}-${o.start.month + 1}-${o.start.year + 1900}</td>
    	<td>${o.status}</td>
    </tr>
  	</c:forEach>
  	</table>
  	<div id="pageNavPosition7" style="padding-top: 20px" align="center">
	</div>
	<script type="text/javascript"><!--
		var pager7 = new Pager('tablepaging7', 5);
		pager7.init();
		pager7.showPageNav('pager7', 'pageNavPosition7');
		pager7.showPage(1);
	</script>
  
  	<h2>Add New Order</h2>
  	<form:form method="post" commandName="order" action="addOrder" accept-charset="UTF-8">
  	<table>
  		<tr>
  			<td>Client:</td>
  			<td><select name="clientId">
  			<option value="0"><c:out value="-Select client-" /></option>
  			<c:forEach var="c" items="${clients}">
  				<c:choose>
  					<c:when test="${selected_order_client_id == c.clientId}">
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
  			</select></td>
  			<td>
  			<div class="error">
  				<c:out value="${message_order_client_id}"/>
  			</div>
  			</td>
  		</tr>
  		<tr>
  			<td>RepairType:</td>
  			<td><select name="repairTypeId">
  			<option value="0"><c:out value="-Select repair type-" /></option>
  			<c:forEach var="rt" items="${repair_types}">
  				<c:choose>
  					<c:when test="${selected_order_repair_type_id == rt.repairTypeId}">
  						<option selected value="${rt.repairTypeId}">
  							<c:out value="${rt.repairTypeName}"/>
  						</option>
  					</c:when>
  					<c:otherwise>
  						<option value="${rt.repairTypeId}">
  							<c:out value="${rt.repairTypeName}"/>
  						</option>
  					</c:otherwise>
  				</c:choose>  				
  			</c:forEach>
  			</select></td>
  			<td>
  			<div class="error">
  				<c:out value="${message_order_repair_type_id}"/>
  			</div>
  			</td>
  		</tr>
  		<tr>
  			<td>Machine S/N:</td>
  			<td><select name="machineId">
  			<option value="0"><c:out value="-Select machine-" /></option>
  			<c:forEach var="m" items="${machines}">
  				<c:choose>
  					<c:when test="${selected_order_machine_id == m.machineId}">
  						<option selected value="${m.machineId}">
  							<c:out value="${m.machineSerialNumber}"/>
  						</option>
  					</c:when>
  					<c:otherwise>
  						<option value="${m.machineId}">
  							<c:out value="${m.machineSerialNumber}"/>
  						</option>
  					</c:otherwise>
  				</c:choose>  				
  			</c:forEach>
  			</select></td>
  			<td>
  			<div class="error">
  				<c:out value="${message_order_machine_id}"/>
  			</div>
  			</td>
  		</tr>
  		<tr>  		
  			<td>Start date (dd-MM-yyyy):</td>
  			<td><input name="startDate" value="${entered_order_start}"/></td>                
  			<td>
  			<div class="error">
  				<c:out value="${message_order_start}"/>
  			</div>
  			</td>  			  		
  		</tr>
  		<tr>
  			<td><label for="statusInput">Status: </label></td>
  			<td><form:select path="status" id="statusInput">
  				<form:option value=""><c:out value="-Select order status-" /></form:option>
  				<form:option value="pending"><c:out value="pending" /></form:option>
  				<form:option value="started"><c:out value="started" /></form:option>
  				<form:option value="ready"><c:out value="ready" /></form:option>
  				<form:option value="finished"><c:out value="finished" /></form:option>
  			</form:select></td>
  			<td><form:errors path="status" cssClass="error" /></td>  			
  		</tr>
  		<tr>  
  			<td><button>Add</button></td>
  		</tr>
  	</table>
  	</form:form>
  	</div>
  	</div>
  	</div>
</body>
</html>