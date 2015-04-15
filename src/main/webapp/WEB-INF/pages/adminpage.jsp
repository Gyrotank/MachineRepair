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

<title><spring:message code="label.adminpage.title" /></title>

</head>

<body>
	
	<jsp:useBean id="now" class="java.util.Date" />
	<fmt:formatDate var="current_year" value="${now}" pattern="yyyy" />
	
	<h1 align = "center"><spring:message code="label.adminpage.header" /></h1>
	
	<div id="sidebar">
		<p><a href="<c:url value="/index"/>">
			<spring:message code="label.adminpage.sidebar.index" /></a></p>
		<hr>		
		<p><a href="<c:url value="/managerpage"/>">
			<spring:message code="label.adminpage.sidebar.managerpage" /></a></p>
		<hr>
	<dl class="tabs vertical">
  		<dd class="active"><a href="#machines">
			<spring:message code="label.adminpage.sidebar.machines" /></a></dd>
  		<dd><a href="#serviceable_machines">
			<spring:message code="label.adminpage.sidebar.serviceableMachines" /></a></dd>
  		<dd><a href="#repair_types">
			<spring:message code="label.adminpage.sidebar.repairTypes" /></a></dd>
  		<dd><a href="#users">
			<spring:message code="label.adminpage.sidebar.users" /></a></dd>
  		<dd><a href="#user_auths">
			<spring:message code="label.adminpage.sidebar.userAuths" /></a></dd>
  		<dd><a href="#clients">
			<spring:message code="label.adminpage.sidebar.clients" /></a></dd>
  		<dd><a href="#orders">
			<spring:message code="label.adminpage.sidebar.orders" /></a></dd>
	</dl>
		<hr>
		<p><a href="<c:url value="/logout"/>">
			<spring:message code="label.adminpage.sidebar.logout" /></a></p>
	</div>
	
	<div id="content">
	<div class="tabs-content">
	<div class="content active" id="machines">
	<h1><spring:message code="label.adminpage.machines" /></h1>
	<form method="post" action="adminpage/machinepaging" accept-charset="UTF-8">
  		<table>
  		<tr>
  			<td style="width:5%" align="center">
  				<spring:message code="label.adminpage.records" /></td>  			
  			<td style="width:10%" align="center">
  				<input name="machinePageStart" maxlength="5" size="8"
  				value="${machines_paging_first + 1}"/></td>  			
  			<td style="width:5%" align="center">
  				<spring:message code="label.adminpage.to" /></td>  			
  			<td style="width:10%" align="center">
  				<input name="machinePageEnd" maxlength="5" size="8"
  				value="${machines_paging_last + 1}"/></td>
  			<td style="width:20%" align="center">
  				<spring:message code="label.adminpage.of" /> ${machines_count} 
				<spring:message code="label.adminpage.total" /></td>
  			<td style="width:50%" align="left"><button>
  				<spring:message code="label.adminpage.buttonGo" /></button></td>  			
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
	<tr><th align="center" data-sortable="true">  </th>
	<th align="center" data-sortable="true">
		<spring:message code="label.adminpage.machines.name" /></th>
	<th align="center" data-sortable="true">
		<spring:message code="label.adminpage.machines.sn" /></th>
	<th align="center" data-sortable="true">
		<spring:message code="label.adminpage.machines.year" /></th>
	<th align="center" data-sortable="true">
		<spring:message code="label.adminpage.machines.timesRepaired" /></th></tr>
	</thead>
	<tbody>
  	<c:forEach var="m" items="${machines_short}" varStatus="loopStatus">    	
    <tr class="${loopStatus.index % 2 == 0 ? 'even' : 'odd'}">
    	<td><c:out value="${loopStatus.index + 1}"/></td>
    	<td>${m.machineServiceable.machineServiceableName}</td> 
    	<td>${m.machineSerialNumber}</td>
    	<td>${m.machineYear}</td>
    	<td>${m.machineTimesRepaired}</td>    	
    </tr>
  	</c:forEach>
  	</tbody>
  	</table>
  	
  	<div>
	<h2><spring:message code="label.adminpage.addNewMachine" /></h2>
  	<form:form method="post" commandName="machine" action="addMachine" accept-charset="UTF-8">
  	<table>
  		<tr>
  			<td><label for="machineServiceableId">
  				<spring:message code="label.adminpage.addNewMachine.machineNameFrom" />
  					${machines_paging_first + 1}
  				<spring:message code="label.adminpage.addNewMachine.machineNameTo" />
  					${machines_paging_last + 1})&nbsp</label></td>
  			<td>
  				<script type="text/javascript">
  					$("#machineServiceableId").ufd();
				</script>
  				<select id="machineServiceableId" name="machineServiceableId">
  				<option value="0">
  					<spring:message code="label.adminpage.addNewMachine.selectMachineName" />
  				</option>
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
  			<td><label for="machineSerialNumberInput">
  				<spring:message code="label.adminpage.addNewMachine.sn" />
  				</label></td>
  			<td><form:input path="machineSerialNumber" id="machineSerialNumberInput"
  					maxlength="32" /></td>
  			<td><form:errors path="machineSerialNumber" cssClass="error" /></td>
  		</tr>
  		<tr>  		
  			<td><label for="machineYearInput">
  			<spring:message code="label.adminpage.addNewMachine.year" />
  			</label></td>
  			<td><form:input size="4" minlength="4" maxlength="4"
  				 path="machineYear" id="machineYearInput"/></td>
  			<td><form:errors path="machineYear" cssClass="error" /></td>  			
		</tr>
		<tr>
  			<td><label for="machineTimesRepairedInput">
  			<spring:message code="label.adminpage.addNewMachine.timesRepaired" />
  			</label></td>
  			<td><form:input size="5" maxlength="3"
  				 path="machineTimesRepaired" id="machineTimesRepairedInput"/></td>
  			<td><form:errors path="machineTimesRepaired" cssClass="error" /></td>  		
  		</tr>
  		<tr>
  			<td><button><spring:message code="label.adminpage.buttonAdd" />
  				</button></td>
  		</tr>
  	</table>
  	</form:form>  	
  	</div>
  	</div>
  	
  	<br><hr>
  	
  	<div class="content" id="serviceable_machines">
  	<h1><spring:message code="label.adminpage.serviceableMachines" /></h1>
  	<form method="post" action="adminpage/machineserviceablepaging" accept-charset="UTF-8">
  		<table>
  		<tr>
  			<td style="width:5%" align="center">
  				<spring:message code="label.adminpage.records" /></td>  			
  			<td style="width:10%" align="center">
  				<input name="machineServiceablePageStart" maxlength="5" size="8"
  				value="${machines_serviceable_paging_first + 1}"/></td>  			
  			<td style="width:5%" align="center">
  				<spring:message code="label.adminpage.to" /></td>  			
  			<td style="width:10%" align="center">
  				<input name="machineServiceablePageEnd" maxlength="5" size="8"
  				value="${machines_serviceable_paging_last + 1}"/></td>
  			<td style="width:20%" align="center">
  				<spring:message code="label.adminpage.of" />
  				${machines_serviceable_count}
  				<spring:message code="label.adminpage.total" /></td>
  			<td style="width:50%" align="left">
  			<button><spring:message code="label.adminpage.buttonGo" /></button></td>
  		</tr>  		
  		</table>
  	</form>
  	<table border="1" data-toggle="table" 
		data-classes="table table-hover table-condensed" 
    	data-striped="true"
    	data-pagination="true"
		data-search="true"
		border="1" style="width:900px" align="center">
	<thead>
	<tr><th align="center" data-sortable="true"></th>
	<th align="center" data-sortable="true">
		<spring:message code="label.adminpage.serviceableMachines.name" /></th>
	<th align="center" data-sortable="true">
		<spring:message code="label.adminpage.serviceableMachines.trademark" /></th>
	<th align="center" data-sortable="true">
		<spring:message code="label.adminpage.serviceableMachines.country" /></th></tr>
	</thead>
	<tbody>
  	<c:forEach var="ms" items="${machines_serviceable_short}" varStatus="loopStatus">    	
    <tr class="${loopStatus.index % 2 == 0 ? 'even' : 'odd'}">
    	<td><c:out value="${loopStatus.index + 1}"/></td>
    	<td>${ms.machineServiceableName}</td> 
    	<td>${ms.machineServiceableTrademark}</td>
    	<td>${ms.machineServiceableCountry}</td>    	   	
    </tr>
  	</c:forEach>
  	</tbody>
  	</table>
  	
  	<div>
  	<h2><spring:message code="label.adminpage.addNewServiceableMachine" /></h2>
  	<form:form method="post" commandName="machineServiceable" action="addMachineServiceable" accept-charset="UTF-8">
  	<table>
  		<tr>
  			<td><label for="machineServiceableNameInput">
  				<spring:message code="label.adminpage.serviceableMachines.name" /></label></td>
  			<td><form:input path="machineServiceableName" id="machineServiceableNameInput"
  					maxlength="50"/></td>
  			<td><form:errors path="machineServiceableName" cssClass="error" /></td>  			
  		</tr>
  		<tr>
  			<td><label for="machineServiceableNameInput">
  				<spring:message code="label.adminpage.serviceableMachines.trademark" />
  				</label></td>
  			<td>
  			<form:input path="machineServiceableTrademark" id="machineServiceableTrademarkInput"
  					maxlength="50"/>
  			</td>
  			<td><form:errors path="machineServiceableTrademark" cssClass="error" /></td>
  		</tr>
  		<tr>
  			<td><label for="machineServiceableCountryInput">
  			<spring:message code="label.adminpage.serviceableMachines.country" />
  			</label></td>
  			<td>
  			<form:input path="machineServiceableCountry" id="machineServiceableCountryInput"
  					maxlength="50"/>
  			</td>
  			<td><form:errors path="machineServiceableCountry" cssClass="error" /></td>  			
  		</tr>
  		<tr> 		
  			<td><button>
  			<spring:message code="label.adminpage.buttonAdd" />
  			</button></td>
  		</tr>
  	</table>
  	</form:form>
  	</div>
  	</div>
  	
  	<br><hr>
    
    <div class="content" id="repair_types">
  	<h1><spring:message code="label.adminpage.repairTypes" /></h1>
  	<form method="post" action="adminpage/repairtypepaging" accept-charset="UTF-8">
  		<table>
  		<tr>
  			<td style="width:5%" align="center">
  			<spring:message code="label.adminpage.records" /></td>  			
  			<td style="width:10%" align="center">
  				<input name="repairTypePageStart" maxlength="5" size="8"
  				value="${repair_types_paging_first + 1}"/></td>  			
  			<td style="width:5%" align="center">
  				<spring:message code="label.adminpage.to" />
  			</td>  			
  			<td style="width:10%" align="center">
  				<input name="repairTypePageEnd" maxlength="5" size="8"
  				value="${repair_types_paging_last + 1}"/></td>
  			<td style="width:20%" align="center">
  			<spring:message code="label.adminpage.of" />
  			${repair_types_count}
  			<spring:message code="label.adminpage.total" /></td>
  			<td style="width:50%" align="left"><button>
  				<spring:message code="label.adminpage.buttonGo" />
  			</button></td>  			
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
	<th align="center" data-sortable="true">
		<spring:message code="label.adminpage.repairTypes.name" />
	</th>
	<th align="center" data-sortable="true">
		<spring:message code="label.adminpage.repairTypes.price" />
	</th>
	<th align="center" data-sortable="true">
		<spring:message code="label.adminpage.repairTypes.duration" />
	</th></tr>
	</thead>
	<tbody>
  	<c:forEach var="rt" items="${repair_types_short}" varStatus="loopStatus">    	
    <tr class="${loopStatus.index % 2 == 0 ? 'even' : 'odd'}">
    	<td><c:out value="${loopStatus.index + 1}"/></td>
    	<td>${rt.repairTypeName}</td> 
    	<td>${rt.repairTypePrice}</td>
    	<td>${rt.repairTypeDuration}</td>    	   	
    </tr>
  	</c:forEach>
  	</tbody>
  	</table>
  	  
  	<h2><spring:message code="label.adminpage.addNewRepairType" /></h2>
  	<form:form method="post" commandName="repairType" 
  		action="addRepairType" accept-charset="UTF-8">
  	<table>
  		<tr>
  			<td><label for="repairTypeNameInput">
  				<spring:message code="label.adminpage.repairTypes.name" />
  			</label></td>
  			<td><form:input path="repairTypeName" id="repairTypeNameInput"
  					maxlength="20"/></td>
  			<td><form:errors path="repairTypeName" cssClass="error" /></td>  			
  		</tr>
  		<tr>
  			<td><label for="repairTypePriceInput">
  				<spring:message code="label.adminpage.repairTypes.price" />
  			</label></td>
  			<td><form:input path="repairTypePrice" id="repairTypePriceInput"
  					maxlength="11"/></td>
  			<td><form:errors path="repairTypePrice" cssClass="error" /></td>  			
  		</tr>
  		<tr>
  			<td><label for="repairTypeDurationInput">
  				<spring:message code="label.adminpage.repairTypes.duration" />
  			</label></td>
  			<td><form:input path="repairTypeDuration" id="repairTypeDurationInput"
  					maxlength="2"/></td>
  			<td><form:errors path="repairTypeDuration" cssClass="error" /></td>  			
  		</tr>
  		<tr>
  			<td><button><spring:message code="label.adminpage.buttonAdd" /></button></td>
  		</tr>  		
  	</table>
  	</form:form>
  	</div>
  	
  	<br><hr>
    
    <div class="content" id="users">
  	<h1><spring:message code="label.adminpage.users" /></h1>
  	<form method="post" action="adminpage/userpaging" accept-charset="UTF-8">
  		<table>
  		<tr>
  			<td style="width:5%" align="center">
  				<spring:message code="label.adminpage.records" /></td>  			
  			<td style="width:10%" align="center">
  				<input name="userPageStart" maxlength="5" size="8"
  				value="${users_paging_first + 1}"/></td>  			
  			<td style="width:5%" align="center">
  				<spring:message code="label.adminpage.to" /></td>  			
  			<td style="width:10%" align="center">
  				<input name="userPageEnd" maxlength="5" size="8"
  				value="${users_paging_last + 1}"/></td>
  			<td style="width:20%" align="center">
  			<spring:message code="label.adminpage.of" />
  			${users_count}
  			<spring:message code="label.adminpage.total" /></td>
  			<td style="width:50%" align="left"><button>
  				<spring:message code="label.adminpage.buttonGo" />
  			</button></td>  			
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
	<th align="center" data-sortable="true">
		<spring:message code="label.adminpage.users.login" /></th>
	<th align="center" data-sortable="true">
		<spring:message code="label.adminpage.users.password" /></th></tr>
	</thead>
	<tbody>
  	<c:forEach var="u" items="${users_short}" varStatus="loopStatus">    	
    <tr class="${loopStatus.index % 2 == 0 ? 'even' : 'odd'}">
    	<td><c:out value="${loopStatus.index + 1}"/></td>
    	<td>${u.login}</td> 
    	<td>${u.password}</td>
    </tr>
  	</c:forEach>
  	</tbody>
  	</table>
  	  	
  	<h2><spring:message code="label.adminpage.addNewUser" /></h2>
  	<form:form method="post" commandName="user" action="addUser" accept-charset="UTF-8">
  	<table>
  		<tr>
  			<td><label for="loginInput">
  				<spring:message code="label.adminpage.users.login" /></label></td>
  			<td><form:input path="login" id="loginInput" maxlength="50"/></td>
  			<td><form:errors path="login" cssClass="error" /></td>  			
  		</tr>
  		<tr>
  			<td><label for="passwordTextInput">
  				<spring:message code="label.adminpage.users.password" /></label></td>
  			<td><form:input path="passwordText" id="passwordTextInput" maxlength="50"/></td>
  			<td><form:errors path="passwordText" cssClass="error" /></td>  			
  		</tr>
  		<tr>
  			<td><button><spring:message code="label.adminpage.buttonAdd" /></button></td>
  		</tr>
  	</table>
  	</form:form>
  	</div>
  	
  	<br><hr>
  	
  	<div class="content" id="user_auths">
  	<h1><spring:message code="label.adminpage.userAuthorizations" /></h1>
  	<form method="post" action="adminpage/userauthorizationpaging" accept-charset="UTF-8">
  		<table>
  		<tr>
  			<td style="width:5%" align="center">
  				<spring:message code="label.adminpage.records" /></td>  			
  			<td style="width:10%" align="center"><input name="userAuthorizationPageStart" 
  				maxlength="5" size="8"
  				value="${user_authorizations_paging_first + 1}"/></td>  			
  			<td style="width:5%" align="center">
  				<spring:message code="label.adminpage.to" /></td>  			
  			<td style="width:10%" align="center"><input name="userAuthorizationPageEnd" 
  				maxlength="5" size="8"
  				value="${user_authorizations_paging_last + 1}"/></td>
  			<td style="width:20%" align="center">
  				<spring:message code="label.adminpage.of" />
  				${user_authorizations_count}
  				<spring:message code="label.adminpage.total" /></td>
  			<td style="width:50%" align="left"><button>
  				<spring:message code="label.adminpage.buttonGo" /></button></td>  			
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
	<th align="center" data-sortable="true">
		<spring:message code="label.adminpage.userAuthorizations.login" /></th>
	<th align="center" data-sortable="true">
		<spring:message code="label.adminpage.userAuthorizations.role" /></th></tr>
	</thead>
	<tbody>
  	<c:forEach var="ua" items="${user_authorizations_short}" varStatus="loopStatus">    	
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
  	</tbody>
  	</table>
  	  	
  	<h2><spring:message code="label.adminpage.addNewUserAuthorization" /></h2>
  	<form:form method="post" commandName="userAuthorization" 
  		action="addUserAuthorization" accept-charset="UTF-8">
  	<table>
  		<tr>
  		<td><label>
  			<spring:message code="label.adminpage.addNewUserAuthorization.userFrom" />
  			${users_paging_first + 1} 
  			<spring:message code="label.adminpage.addNewUserAuthorization.userTo" />
  			${users_paging_last + 1})&nbsp</label></td>  		
  		<td><select name="userId">
  			<option value="0">
  				<spring:message code="label.adminpage.addNewUserAuthorization.selectUser" />
  			</option>
  			<c:forEach var="ua" items="${user_authorizations_short}">
  				<c:choose>
  					<c:when test="${selected_user_authorization_user_id == ua.user.userId}">
  						<option selected value="${ua.user.userId}">
  							<c:out value="${ua.user.login}"/>
  						</option>
  					</c:when>
  					<c:otherwise>
  						<option value="${ua.user.userId}">
  							<c:out value="${ua.user.login}"/>
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
  		<td><label>
  		<spring:message code="label.adminpage.userAuthorizations.role" />
  		</label></td>
  		<td><form:select path="role">
  			<form:option value="">
  				<label>
  					<spring:message code="label.adminpage.addNewUserAuthorization.selectRole" />
  				</label>
  			</form:option>
  			<c:forEach var="r" items="${user_roles}">
  				<form:option value="${r}">
  					<c:out value="${r}"/></form:option>
  			</c:forEach>
  		</form:select></td>
  		<td><form:errors path="role" cssClass="error" /></td>
  		</tr>
  		<tr>  		  
  		<td><button>
  		<spring:message code="label.adminpage.buttonAdd" />
  		</button></td>
  		</tr>
  	</table>
  	</form:form>
  	</div>
  	
  	<br><hr>
  	
  	<div class="content" id="clients">  
  	<h1><spring:message code="label.adminpage.clients" /></h1>
  	<form method="post" action="adminpage/clientpaging" accept-charset="UTF-8">
  		<table>
  		<tr>
  			<td style="width:5%" align="center">
  				<spring:message code="label.adminpage.records" />
  			</td>  			
  			<td style="width:10%" align="center">
  				<input name="clientPageStart" maxlength="5" size="8"
  				value="${clients_paging_first + 1}"/></td>  			
  			<td style="width:5%" align="center">
  				<spring:message code="label.adminpage.to" />
  			</td>  			
  			<td style="width:10%" align="center">
  				<input name="clientPageEnd" maxlength="5" size="8"
  				value="${clients_paging_last + 1}"/></td>
  			<td style="width:20%" align="center">
  			<spring:message code="label.adminpage.of" />
  			${clients_count}
  			<spring:message code="label.adminpage.total" /></td>
  			<td style="width:50%" align="left"><button>
  				<spring:message code="label.adminpage.buttonGo" />
  			</button></td>  			
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
	<th align="center" data-sortable="true">
		<spring:message code="label.adminpage.clients.name" />
	</th>
	<th align="center" data-sortable="true">
		<spring:message code="label.adminpage.clients.login" />
	</th></tr>
	</thead>
	<tbody>
  	<c:forEach var="c" items="${clients_short}" varStatus="loopStatus">    	
    <tr class="${loopStatus.index % 2 == 0 ? 'even' : 'odd'}">
    	<td><c:out value="${loopStatus.index + 1}"/></td>
    	<td>${c.clientName}</td> 
    	<td>${c.clientUser.login}</td>    	    	   	
    </tr>
  	</c:forEach>
  	</tbody>
  	</table>
  	  
  	<h2><spring:message code="label.adminpage.addNewClient" /></h2>
  	<form:form method="post" commandName="client" action="addClient" accept-charset="UTF-8">
  	<table>
  		<tr>
  			<td><label for="clientNameInput">
  				<spring:message code="label.adminpage.clients.name" />
  			</label></td>
  			<td><form:input path="clientName" id="clientNameInput" maxlength="50"/></td>
  			<td><form:errors path="clientName" cssClass="error" /></td>  			
  		</tr>
  		<tr>
  			<td><label><spring:message code="label.adminpage.addNewClient.userFrom" />
  				${users_paging_first + 1} 
  				<spring:message code="label.adminpage.addNewClient.userTo" />
  				${users_paging_last + 1})&nbsp</label></td>
  			<td><select name="userId">
  				<option value="0">
  					<spring:message code="label.adminpage.addNewClient.selectUser" />
  				</option>
  				<c:forEach var="u" items="${users_short}">
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
  			<td><button>
  				<spring:message code="label.adminpage.buttonAdd" />
  			</button></td>
  		</tr>
  	</table>
  	</form:form>
  	</div>
  	
  	<br><hr>
  
    <div class="content" id="orders">
  	<h1><spring:message code="label.adminpage.orders" /></h1>
  	<form method="post" action="adminpage/orderpaging" accept-charset="UTF-8">
  		<table>
  		<tr>
  			<td style="width:5%" align="center">
  				<spring:message code="label.adminpage.records" />
  			</td>  			
  			<td style="width:10%" align="center">
  				<input name="orderPageStart" maxlength="5" size="8"
  				value="${orders_paging_first + 1}"/></td>  			
  			<td style="width:5%" align="center">
  				<spring:message code="label.adminpage.to" />
  			</td>  			
  			<td style="width:10%" align="center">
  				<input name="orderPageEnd" maxlength="5" size="8"
  				value="${orders_paging_last + 1}"/></td>
  			<td style="width:20%" align="center">
  				<spring:message code="label.adminpage.of" />
  				${orders_count}
  				<spring:message code="label.adminpage.total" /></td>
  			<td style="width:50%" align="left"><button>
  				<spring:message code="label.adminpage.buttonGo" />
  			</button></td>  			
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
	<th align="center" data-sortable="true">
		<spring:message code="label.adminpage.orders.client" />
	</th>
	<th align="center" data-sortable="true">
		<spring:message code="label.adminpage.orders.repairType" />
	</th>
	<th align="center" data-sortable="true">
		<spring:message code="label.adminpage.orders.machine" />
	</th>
	<th align="center" data-sortable="true">
		<spring:message code="label.adminpage.orders.sn" />
	</th>
	<th align="center" data-sortable="true">
		<spring:message code="label.adminpage.orders.date" />
	</th>
	<th align="center" data-sortable="true">
		<spring:message code="label.adminpage.orders.status" />
	</th></tr>
	</thead>
	<tbody>
  	<c:forEach var="o" items="${orders_short}" varStatus="loopStatus">    	
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
  	</tbody>
  	</table>
  	  
  	<h2><spring:message code="label.adminpage.addNewOrder" /></h2>
  	<form:form method="post" commandName="order" action="addOrder" accept-charset="UTF-8">
  	<table>
  		<tr>
  			<td><label>
  			<spring:message code="label.adminpage.addNewOrder.clientFrom" />
  			${clients_paging_first + 1}
  			<spring:message code="label.adminpage.addNewOrder.clientTo" /> 
  			${clients_paging_last + 1})&nbsp</label></td>
  			<td><select name="clientId">
  			<option value="0">
  				<spring:message code="label.adminpage.addNewOrder.selectClient" />
  			</option>
  			<c:forEach var="c" items="${clients_short}">
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
  			<td><label>
  				<spring:message code="label.adminpage.orders.repairType" />
  			</label></td>
  			<td><select name="repairTypeId">
  			<option value="0">
  				<spring:message code="label.adminpage.addNewOrder.selectRepairType" />
  			</option>
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
  			<td><label><spring:message code="label.adminpage.addNewOrder.machineFrom" />
  			${machines_paging_first + 1}
  			<spring:message code="label.adminpage.addNewOrder.machineTo" />
  			${machines_paging_last + 1})&nbsp</label></td>
  			<td><select name="machineId">
  			<option value="0">
  				<spring:message code="label.adminpage.addNewOrder.selectMachine" />
  			</option>
  			<c:forEach var="m" items="${machines_short}">
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
  			<td><label>
  				<spring:message code="label.adminpage.addNewOrder.date" />
  			</label></td>
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
  			<td><form:select path="status" id="statusInput">
  				<form:option value="">
  					<spring:message code="label.adminpage.addNewOrder.selectStatus" />
  				</form:option>
  				<form:option value="pending"><c:out value="pending" /></form:option>
  				<form:option value="started"><c:out value="started" /></form:option>
  				<form:option value="ready"><c:out value="ready" /></form:option>
  				<form:option value="finished"><c:out value="finished" /></form:option>
  			</form:select></td>
  			<td><form:errors path="status" cssClass="error" /></td>  			
  		</tr>
  		<tr>  
  			<td><button>
  				<spring:message code="label.adminpage.buttonAdd" />
  			</button></td>
  		</tr>
  	</table>
  	</form:form>
  	</div>
  	</div>
  	</div>
</body>
</html>