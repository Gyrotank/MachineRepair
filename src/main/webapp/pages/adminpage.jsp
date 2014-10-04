<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
			 "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Base View and Add (Basic)</title>

<style>
.error {
    color: #ff0000;
    font-style: italic;
    font-weight: bold;
}

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
</style>
</head>
<body>	
	<jsp:useBean id="now" class="java.util.Date" />
	<fmt:formatDate var="current_year" value="${now}" pattern="yyyy" />
	
	<h3 class="left"><a href="<c:url value="/index"/>">Home</a></h3>
	<h3 class="right"><a href="<c:url value="/logout"/>">Log out</a></h3>
	<br>
	<br>
	<br>
	<h1>Machines</h1>
	<table border="1" style="width:1000px">
	<tr><th align="center">Id:</th><th align="center">M_S_Name:</th>
	<th align="center">S/N:</th>
	<th align="center">Year:</th><th align="center">Times Repaired:</th></tr>
  	<c:forEach var="m" items="${machines}">    	
    <tr>
    	<td>${m.machineId}</td>
    	<td>${m.machineServiceable.machineServiceableName}</td> 
    	<td>${m.machineSerialNumber}</td>
    	<td>${m.machineYear}</td>
    	<td>${m.machineTimesRepaired}</td>    	
    </tr>
  	</c:forEach>
  	</table>
  	
	<h2>Add New Machine:</h2>
  	<form:form method="post" commandName="machine" action="addMachine">
  	<table>
  		<tr>
  			<td>Machine Name: </td>
  			<td><select name="machineServiceableId">  		
  				<option value="0"><c:out value="---" /></option>
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
  				</select></td>
  				<td>
  					<div class="error">
  						<c:out value="${message_machineserviceable_id}"/>
  					</div>
  				</td>
  		</tr>
  		<tr>
  			<td><label for="machineSerialNumberInput">S/N: </label></td>
  			<td><form:input path="machineSerialNumber" id="machineSerialNumberInput"/></td>
  			<td><form:errors path="machineSerialNumber" cssClass="error" /></td>
  		</tr>
  		<tr>  		
  			<td><label for="machineYearInput">Year: </label></td>
  			<td><form:select path="machineYear" id="machineYearInput">
  			<form:option value="0"><c:out value="---" /></form:option>
  			<c:forEach begin="1950" end="${current_year}" var="y">
   				<form:option value="${y}">${y}</form:option>
			</c:forEach>
			</form:select></td>
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
  	
  	<h1>Serviceable Machines</h1>
  	<table border="1" style="width:1000px">
	<tr><th align="center">Id:</th><th align="center">Name:</th>
	<th align="center">Trademark:</th><th align="center">Country:</th></tr>
  	<c:forEach var="ms" items="${machines_serviceable}">    	
    <tr>
    	<td>${ms.machineServiceableId}</td>
    	<td>${ms.machineServiceableName}</td> 
    	<td>${ms.machineServiceableTrademark}</td>
    	<td>${ms.machineServiceableCountry}</td>    	   	
    </tr>
  	</c:forEach>
  	</table>
  	
  	<h2>Add New Serviceable Machine:</h2>
  	<form:form method="post" commandName="machineServiceable" action="addMachineServiceable">
  	<table>
  		<tr>
  			<td><label for="machineServiceableNameInput">Name: </label></td>
  			<td><form:input path="machineServiceableName" id="machineServiceableNameInput"/></td>
  			<td><form:errors path="machineServiceableName" cssClass="error" /></td>  			
  		</tr>
  		<tr>
  			<td><label for="machineServiceableNameInput">Trademark: </label></td>
  			<td>
  			<form:input path="machineServiceableTrademark" id="machineServiceableTrademarkInput"/>
  			</td>
  			<td><form:errors path="machineServiceableTrademark" cssClass="error" /></td>
  		</tr>
  		<tr>
  			<td><label for="machineServiceableCountryInput">Country: </label></td>
  			<td>
  			<form:input path="machineServiceableCountry" id="machineServiceableCountryInput"/>
  			</td>
  			<td><form:errors path="machineServiceableCountry" cssClass="error" /></td>  			
  		</tr>
  		<tr> 		
  			<td><button>Add</button></td>
  		</tr>
  	</table>
  	</form:form>
  
  	<h1>Repair Types</h1>
  	<table border="1" style="width:1000px">
	<tr><th align="center">Id:</th><th align="center">Name:</th>
	<th align="center">Price:</th><th align="center">Duration:</th></tr>
  	<c:forEach var="rt" items="${repair_types}">		
    <tr>
    	<td>${rt.repairTypeId}</td>
    	<td>${rt.repairTypeName}</td> 
    	<td>${rt.repairTypePrice}</td>
    	<td>${rt.repairTypeDuration}</td>    	   	
    </tr>
  	</c:forEach>
  	</table>
  
  	<h2>Add New Repair Type</h2>
  	<form:form method="post" commandName="repairType" action="addRepairType">
  	<table>
  		<tr>
  			<td><label for="repairTypeNameInput">Name: </label></td>
  			<td><form:input path="repairTypeName" id="repairTypeNameInput"/></td>
  			<td><form:errors path="repairTypeName" cssClass="error" /></td>  			
  		</tr>
  		<tr>
  			<td><label for="repairTypePriceInput">Price: </label></td>
  			<td><form:input path="repairTypePrice" id="repairTypePriceInput"/></td>
  			<td><form:errors path="repairTypePrice" cssClass="error" /></td>  			
  		</tr>
  		<tr>
  			<td><label for="repairTypeDurationInput">Duration: </label></td>
  			<td><form:input path="repairTypeDuration" id="repairTypeDurationInput"/></td>
  			<td><form:errors path="repairTypeDuration" cssClass="error" /></td>  			
  		</tr>
  		<tr>
  			<td><button>Add</button></td>
  		</tr>  		
  	</table>
  	</form:form>
  
  	<h1>Users</h1>
  	<table border="1" style="width:1000px">
	<tr><th align="center">Id:</th><th align="center">Login:</th>
	<th align="center">Password:</th><th align="center">PasswordText:</th></tr>
  	<c:forEach var="u" items="${users}">    	
    <tr>
    	<td>${u.userId}</td>
    	<td>${u.login}</td> 
    	<td>${u.password}</td>
    	<td>${u.passwordText}</td>    	   	
    </tr>
  	</c:forEach>
  	</table>
  	
  	<h2>Add New User</h2>
  	<form:form method="post" commandName="user" action="addUser">
  	<table>
  		<tr>
  			<td><label for="loginInput">Login: </label></td>
  			<td><form:input path="login" id="loginInput"/></td>
  			<td><form:errors path="login" cssClass="error" /></td>  			
  		</tr>
  		<tr>
  			<td><label for="passwordTextInput">Password: </label></td>
  			<td><form:input path="passwordText" id="passwordTextInput"/></td>
  			<td><form:errors path="passwordText" cssClass="error" /></td>  			
  		</tr>
  		<tr>
  			<td><button>Add</button></td>
  		</tr>
  	</table>
  	</form:form>
  	
  	<h1>User Authorizations</h1>
  	<table border="1" style="width:1000px">
	<tr><th align="center">Id:</th><th align="center">Login:</th>
	<th align="center">Role:</th></tr>
  	<c:forEach var="ua" items="${user_authorizations}">    	
    <tr>
    	<td>${ua.userAuthorizationId}</td>
    	<td>${ua.user.login}</td> 
    	<td>${ua.role}</td>    	    	   	
    </tr>
  	</c:forEach>
  	</table>
  	
  	<h2>Add New User Authorization</h2>
  	<form:form method="post" commandName="userAuthorization" action="addUserAuthorization">
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
  
  	<h1>Clients</h1>
  	<table border="1" style="width:1000px">
	<tr><th align="center">Id:</th><th align="center">Name:</th>
	<th align="center">Login:</th></tr>
  	<c:forEach var="c" items="${clients}">    	
    <tr>
    	<td>${c.clientId}</td>
    	<td>${c.clientName}</td> 
    	<td>${c.clientUser.login}</td>    	    	   	
    </tr>
  	</c:forEach>
  	</table>
  
  	<h2>Add New Client</h2>
  	<form:form method="post" commandName="client" action="addClient">
  	<table>
  		<tr>
  			<td><label for="clientNameInput">Name: </label></td>
  			<td><form:input path="clientName" id="clientNameInput"/></td>
  			<td><form:errors path="clientName" cssClass="error" /></td>  			
  		</tr>
  		<tr>
  			<td>User:</td>
  			<td><select name="userId">
  				<option value="0"><c:out value="---" /></option>
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
  
  	<h1>Orders</h1>
  	<table border="1" style="width:1000px">
	<tr><th align="center">Id:</th><th align="center">Client:</th>
	<th align="center">RepairType:</th><th align="center">Machine:</th>
	<th align="center">Machine S/N:</th>
	<th align="center">Start:</th><th align="center">Status:</th></tr>
  	<c:forEach var="o" items="${orders}">    	
    <tr>
    	<td>${o.orderId}</td>
    	<td>${o.client.clientName}</td> 
    	<td>${o.repairType.repairTypeName}</td>
    	<td>${o.machine.machineServiceable.machineServiceableName}</td>
    	<td>${o.machine.machineSerialNumber}</td>
    	<td>${o.start}</td>
    	<td>${o.status}</td>
    </tr>
  	</c:forEach>
  	</table>
  
  	<h2>Add New Order</h2>
  	<form:form method="post" commandName="order" action="addOrder">
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
  			<td><label for="startInput">Start date (yyyy-MM-dd): </label></td>
  			<td><form:input path="start" id="startInput" placeholder="yyyy-MM-dd"/></td>                
  			<td><form:errors path="start" cssClass="error" /></td>  			  		
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
</body>
</html>