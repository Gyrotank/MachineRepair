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
<c:choose>
  	<c:when test="${locale == 'ru'}">
  		<script src="<c:url value="/resources/js/bootstrap-table-ru-RU.js" />"></script>
  	</c:when>
  	<c:otherwise>
  		<script src="<c:url value="/resources/js/bootstrap-table-en-US.js" />"></script>
  	</c:otherwise>
</c:choose>

<title><spring:message code="label.adminpage.title" /></title>

</head>

<body>
	
	<jsp:useBean id="now" class="java.util.Date" />
	<fmt:formatDate var="current_year" value="${now}" pattern="yyyy" />
		
	<h1 align = "center"><spring:message code="label.adminpage.header" /></h1>
	
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
		<p><a href="<c:url value="/index"/>">
			<spring:message code="label.adminpage.sidebar.index" /></a></p>
		<hr class="style-seven">		
		<p><a href="<c:url value="/managerpage"/>">
			<spring:message code="label.adminpage.sidebar.managerpage" /></a></p>
		<hr class="style-seven">
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
		<hr class="style-seven">
		<p><a href="<c:url value="/logout"/>">
			<spring:message code="label.adminpage.sidebar.logout" /></a></p>
	</div>
	
	<div id="content">
	<div class="tabs-content">
	<div class="content active" id="machines">
	<h1><spring:message code="label.adminpage.machines" /></h1>
	<div class="success">
  		<c:out value="${message_machine_added}"/>
  	</div>
  	<div class="error">
  		<c:out value="${message_machine_not_added}"/>
  	</div>
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
  	
  	<br><hr class="style-seven">
  	
  	<div class="content" id="serviceable_machines">
  	<h1><spring:message code="label.adminpage.serviceableMachines" /></h1>
  	<div class="success">
  		<c:out value="${message_machine_serviceable_added}"/>
  	</div>
  	<div class="error">
  		<c:out value="${message_machine_serviceable_not_added}"/>
  	</div>
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
    	<td>
    		<c:choose>
    			<c:when test="${locale == 'ru'}">
    				${ms.machineServiceableCountryRu}
    			</c:when>
    			<c:otherwise>
    				${ms.machineServiceableCountry}
    			</c:otherwise>
    		</c:choose>
    	</td>    	   	
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
  			<spring:message code="label.adminpage.serviceableMachines.countryForm" />
  			</label></td>
  			<td>
  			<form:input path="machineServiceableCountry" id="machineServiceableCountryInput"
  					maxlength="50"/>
  			</td>
  			<td><form:errors path="machineServiceableCountry" cssClass="error" /></td>  			
  		</tr>
  		<tr>
  			<td><label for="machineServiceableCountryRuInput">
  			<spring:message code="label.adminpage.serviceableMachines.countryRu" />
  			</label></td>
  			<td>
  			<form:input path="machineServiceableCountryRu" id="machineServiceableCountryRuInput"
  					maxlength="50"/>
  			</td>
  			<td><form:errors path="machineServiceableCountryRu" cssClass="error" /></td>  			
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
  	
  	<br><hr class="style-seven">
    
    <div class="content" id="repair_types">
  	<h1><spring:message code="label.adminpage.repairTypes" /></h1>
  	<div class="success">
  		<c:out value="${message_repair_type_added}"/>
  	</div>
  	<div class="error">
  		<c:out value="${message_repair_type_not_added}"/>
  	</div>
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
    	<td>    		
    		<c:choose>
    			<c:when test="${locale == 'ru'}">
    				${rt.repairTypeNameRu}
    			</c:when>
    			<c:otherwise>
    				${rt.repairTypeName}
    			</c:otherwise>
    		</c:choose>
    	</td> 
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
  				<spring:message code="label.adminpage.repairTypes.nameForm" />
  			</label></td>
  			<td><form:input path="repairTypeName" id="repairTypeNameInput"
  					maxlength="20"/></td>
  			<td><form:errors path="repairTypeName" cssClass="error" /></td>  			
  		</tr>
  		<tr>
  			<td><label for="repairTypeNameInputRu">
  				<spring:message code="label.adminpage.repairTypes.nameRu" />
  			</label></td>
  			<td><form:input path="repairTypeNameRu" id="repairTypeNameInputRu"
  					maxlength="20"/></td>
  			<td><form:errors path="repairTypeNameRu" cssClass="error" /></td>  			
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
  	
  	<br><hr class="style-seven">
    
    <div class="content" id="users">
  	<h1><spring:message code="label.adminpage.users" /></h1>
  	<div class="success">
  		<c:out value="${message_user_added}"/>
  	</div>
  	<div class="error">
  		<c:out value="${message_user_not_added}"/>
  	</div>
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
		<spring:message code="label.adminpage.users.password" /></th>
	<th align="center">
		<spring:message code="label.adminpage.users.actions" /></th></tr>
	</thead>
	<tbody>
  	<c:forEach var="u" items="${users_short}" varStatus="loopStatus">    	
    <tr class="${loopStatus.index % 2 == 0 ? 'even' : 'odd'}">
    	<td>
    		<c:choose>
  			<c:when test="${u.enabled == 0}">
  				<div class="disabled">
					<c:out value="${loopStatus.index + 1}"/>
				</div>
  			</c:when>
  			<c:otherwise>
  				<c:out value="${loopStatus.index + 1}"/>
  			</c:otherwise>
			</c:choose>    		
    	</td>
    	<td>
    		<c:choose>
  			<c:when test="${u.enabled == 0}">
  				<div class="disabled">
					${u.login}
				</div>
  			</c:when>
  			<c:otherwise>
  				${u.login}
  			</c:otherwise>
			</c:choose>    		
    	</td> 
    	<td>
    		<c:choose>
  			<c:when test="${u.enabled == 0}">
  				<div class="disabled">
					${u.password}
				</div>
  			</c:when>
  			<c:otherwise>
  				${u.password}
  			</c:otherwise>
			</c:choose>    		
    	</td>
    	<td>
    		<c:choose>
  			<c:when test="${u.enabled == 0}">
  				<spring:message code="label.adminpage.users.actions.enable" />
  			</c:when>
  			<c:otherwise>
  				<spring:message code="label.adminpage.users.actions.disable" />
  			</c:otherwise>
			</c:choose>    		
    	</td>
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
  	
  	<br><hr class="style-seven">
  	
  	<div class="content" id="user_auths">
  	<h1><spring:message code="label.adminpage.userAuthorizations" /></h1>
  	<div class="success">
  		<c:out value="${message_user_authorization_added}"/>
  	</div>
  	<div class="error">
  		<c:out value="${message_user_authorization_not_added}"/>
  	</div>
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
  				<c:when test="${ua.role == 'ROLE_ADMIN' && locale == 'ru'}">
  					<c:out value="Администратор"/>
  				</c:when>
  				<c:when test="${ua.role == 'ROLE_MANAGER' && locale == 'ru'}">
  					<c:out value="Менеджер"/>
  				</c:when>
  				<c:when test="${ua.role == 'ROLE_CLIENT' && locale == 'ru'}">
  					<c:out value="Клиент"/>
  				</c:when>
  			</c:choose>
    		<c:choose>
  				<c:when test="${ua.role == 'ROLE_ADMIN' && locale == 'en'}">
  					<c:out value="Administrator"/>
  				</c:when>
  				<c:when test="${ua.role == 'ROLE_MANAGER' && locale == 'en'}">
  					<c:out value="Manager"/>
  				</c:when>
  				<c:when test="${ua.role == 'ROLE_CLIENT' && locale == 'en'}">
  					<c:out value="Client"/>
  				</c:when>
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
  			<c:forEach var="uau" items="${user_authorizations_short_users}">
  				<c:choose>
  					<c:when test="${selected_user_authorization_user_id == uau.userId}">
  						<option selected value="${uau.userId}">
  							<c:out value="${uau.login}"/>
  						</option>
  					</c:when>
  					<c:otherwise>
  						<option value="${uau.userId}">
  							<c:out value="${uau.login}"/>
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
  					<c:choose>
  					<c:when test="${r == 'ROLE_ADMIN' && locale == 'ru'}">
  						<c:out value="Администратор"/>
  					</c:when>
  					<c:when test="${r == 'ROLE_MANAGER' && locale == 'ru'}">
  						<c:out value="Менеджер"/>
	  				</c:when>
  					<c:when test="${r == 'ROLE_CLIENT' && locale == 'ru'}">
  						<c:out value="Клиент"/>
  					</c:when>
  					</c:choose>
    				<c:choose>
  					<c:when test="${r == 'ROLE_ADMIN' && locale == 'en'}">
	  					<c:out value="Administrator"/>
  					</c:when>
  					<c:when test="${r == 'ROLE_MANAGER' && locale == 'en'}">
  						<c:out value="Manager"/>
  					</c:when>
	  				<c:when test="${r == 'ROLE_CLIENT' && locale == 'en'}">
  						<c:out value="Client"/>
  					</c:when>
  				</c:choose>
  				</form:option>
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
  	
  	<br><hr class="style-seven">
  	
  	<div class="content" id="clients">  
  	<h1><spring:message code="label.adminpage.clients" /></h1>
  	<div class="success">
  		<c:out value="${message_client_added}"/>
  	</div>
  	<div class="error">
  		<c:out value="${message_client_not_added}"/>
  	</div>
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
  	
  	<br><hr class="style-seven">
  
    <div class="content" id="orders">
  	<h1><spring:message code="label.adminpage.orders" /></h1>
  	<div class="success">
  		<c:out value="${message_order_added}"/>
  	</div>
  	<div class="error">
  		<c:out value="${message_order_not_added}"/>
  	</div>
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
	</th>
	<th align="center" data-sortable="true">
		<spring:message code="label.adminpage.orders.manager" />
	</th></tr>
	</thead>
	<tbody>
  	<c:forEach var="o" items="${orders_short}" varStatus="loopStatus">    	
    <tr class="${loopStatus.index % 2 == 0 ? 'even' : 'odd'}">
    	<td><c:out value="${loopStatus.index + 1}"/></td>
    	<td>${o.client.clientName}</td> 
    	<td>    		
    		<c:choose>
    			<c:when test="${locale == 'ru'}">
    				${o.repairType.repairTypeNameRu}
    			</c:when>
    			<c:otherwise>
    				${o.repairType.repairTypeName}
    			</c:otherwise>
    		</c:choose>
    	</td>
    	<td>${o.machine.machineServiceable.machineServiceableName}</td>
    	<td>${o.machine.machineSerialNumber}</td>
    	<td>${o.start.date}-${o.start.month + 1}-${o.start.year + 1900}</td>
    	<td>    		
    		<c:choose>
  				<c:when test="${o.status == 'pending' && locale == 'ru'}">
  					<c:out value="в обработке"/>
  				</c:when>
  				<c:when test="${o.status == 'started' && locale == 'ru'}">
  					<c:out value="выполняется"/>
	  			</c:when>
  				<c:when test="${o.status == 'ready' && locale == 'ru'}">
  					<c:out value="готов"/>
  				</c:when>
  				<c:when test="${o.status == 'finished' && locale == 'ru'}">
  					<c:out value="завершен"/>
  				</c:when>
  				</c:choose>
    			<c:choose>
  				<c:when test="${o.status == 'pending' && locale == 'en'}">
	  				<c:out value="pending"/>
  				</c:when>
  				<c:when test="${o.status == 'started' && locale == 'en'}">
  					<c:out value="started"/>
  				</c:when>
	  			<c:when test="${o.status == 'ready' && locale == 'en'}">
  					<c:out value="ready"/>
  				</c:when>
  				<c:when test="${o.status == 'finished' && locale == 'en'}">
  					<c:out value="finished"/>
  				</c:when>
  			</c:choose>
    	</td>
    	<td>${o.manager}</td>
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
  							<c:choose>
    							<c:when test="${locale == 'ru'}">
    								<c:out value="${rt.repairTypeNameRu}"/>
    							</c:when>
    							<c:otherwise>
    								<c:out value="${rt.repairTypeName}"/>
    							</c:otherwise>
    						</c:choose>  							
  						</option>
  					</c:when>
  					<c:otherwise>
  						<option value="${rt.repairTypeId}">
  							<c:choose>
    							<c:when test="${locale == 'ru'}">
    								<c:out value="${rt.repairTypeNameRu}"/>
    							</c:when>
    							<c:otherwise>
    								<c:out value="${rt.repairTypeName}"/>
    							</c:otherwise>
    						</c:choose> 
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
  				<form:option value="pending">  					
  					<c:choose>
    					<c:when test="${locale == 'ru'}">
    						<c:out value="в обработке"/>
    					</c:when>
    					<c:otherwise>
    						<c:out value="pending"/>
    					</c:otherwise>
    				</c:choose>
  				</form:option>
  				<form:option value="started">  					
  					<c:choose>
    					<c:when test="${locale == 'ru'}">
    						<c:out value="выполняется"/>
    					</c:when>
    					<c:otherwise>
    						<c:out value="started" />
    					</c:otherwise>
    				</c:choose>
  				</form:option>
  				<form:option value="ready">
  					<c:choose>
    					<c:when test="${locale == 'ru'}">
    						<c:out value="готов"/>
    					</c:when>
    					<c:otherwise>
    						<c:out value="ready" />
    					</c:otherwise>
    				</c:choose>
  				</form:option>
  				<form:option value="finished">
  					<c:choose>
    					<c:when test="${locale == 'ru'}">
    						<c:out value="завершен"/>
    					</c:when>
    					<c:otherwise>
    						<c:out value="finished" />
    					</c:otherwise>
    				</c:choose>
  				</form:option>
  			</form:select></td>
  			<td><form:errors path="status" cssClass="error" /></td>  			
  		</tr>
  		<tr>
  			<td>
  				<label><spring:message code="label.adminpage.addNewOrder.manager" /></label>
  			</td>
  			<td><select name="manager">
  			<option value="-">
  				<spring:message code="label.adminpage.addNewOrder.selectManager" />
  			</option>
  			<c:forEach var="man" items="${user_authorizations_managers}">
  				<c:choose>
  					<c:when test="${selected_order_manager == man}">
  						<option selected value="${man}">
  							<c:out value="${man}"/>
  						</option>
  					</c:when>
  					<c:otherwise>
  						<option value="${man}">
  							<c:out value="${man}"/>
  						</option>
  					</c:otherwise>
  				</c:choose>  				
  			</c:forEach>
  			</select></td>
  			<td>
  			<div class="error">
  				<c:out value="${message_order_manager}"/>
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
  	</div>
  	</div>
</body>
</html>