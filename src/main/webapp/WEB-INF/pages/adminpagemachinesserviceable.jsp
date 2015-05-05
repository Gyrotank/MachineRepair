<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
			 "http://www.w3.org/TR/html4/loose.dtd">
			 
<html>
<head>
<%@ include file="header.jsp" %>

<title><spring:message code="label.adminpage.title" /></title>
</head>

<body>
<h1 align = "center"><spring:message code="label.adminpage.header" /></h1>
	
	<%@ include file="adminsidebar.jsp" %>
	
	<div id="content">
	<div class="tabs-content">
		
  	<div class="content" id="serviceable_machines">
  	<h1><spring:message code="label.adminpage.serviceableMachines" /></h1>
  	<div class="success">
  		<c:out value="${message_machine_serviceable_added}"/>
  	</div>
  	<div class="error">
  		<c:out value="${message_machine_serviceable_not_added}"/>
  	</div>
  	<form method="post" action="adminpagemachinesserviceable/machineserviceablepaging" 
  		accept-charset="UTF-8">
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
  	<br>
  	<table border="1" data-toggle="table" 
		data-classes="table table-hover table-condensed" 
    	data-striped="true"
    	data-pagination="true"		
		border="1" style="width:900px" align="center">
	<thead>
	<tr><th align="center"></th>
	<th align="center">
		<spring:message code="label.adminpage.serviceableMachines.name" /></th>
	<th align="center">
		<spring:message code="label.adminpage.serviceableMachines.trademark" /></th>
	<th align="center">
		<spring:message code="label.adminpage.serviceableMachines.country" /></th>
	<th align="center" data-sortable="false">
		<spring:message code="label.adminpage.serviceableMachines.actions" />
	</th></tr>
	</thead>
	<tbody>
  	<c:forEach var="ms" items="${machines_serviceable_short}" varStatus="loopStatus">    	
    <tr class="${loopStatus.index % 2 == 0 ? 'even' : 'odd'}">
    	<td>
    		<c:choose>
  			<c:when test="${ms.available == 0}">
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
  			<c:when test="${ms.available == 0}">
  				<div class="disabled">
					${ms.machineServiceableName}
				</div>
  			</c:when>
  			<c:otherwise>
    			${ms.machineServiceableName}
    		</c:otherwise>
    		</c:choose>
    	</td> 
    	<td>
    		<c:choose>
  			<c:when test="${ms.available == 0}">
  				<div class="disabled">
					${ms.machineServiceableTrademark}
				</div>
  			</c:when>
  			<c:otherwise>
    			${ms.machineServiceableTrademark}
    		</c:otherwise>
    		</c:choose>
    	</td>
    	<td>
    		<c:choose>
    			<c:when test="${locale == 'ru'}">
    				<c:choose>
  					<c:when test="${ms.available == 0}">
  						<div class="disabled">
							${ms.machineServiceableCountryRu}
						</div>
  					</c:when>
  					<c:otherwise>
    					${ms.machineServiceableCountryRu}
    				</c:otherwise>
    				</c:choose>
    			</c:when>
    			<c:otherwise>
    				<c:choose>
  					<c:when test="${ms.available == 0}">
  						<div class="disabled">
							${ms.machineServiceableCountry}
						</div>
  					</c:when>
  					<c:otherwise>
    					${ms.machineServiceableCountry}
    				</c:otherwise>
    				</c:choose>
    			</c:otherwise>
    		</c:choose>
    	</td>
    	<td>
    		<a href="<c:url 
    			value="/updatemachineserviceable/?machine-serviceable-id=${ms.machineServiceableId}"/>">
  				<img src="resources/images/edit.png" width="24"></a>
			<c:choose>
  			<c:when test="${ms.available == 0}">
  				<a href="<c:url value="setMSAvailable/?machine-serviceable-id=${ms.machineServiceableId}"/>" 
    			onclick="return confirm('${dialog_available_machine_serviceable}')">
  				<img src="resources/images/enable.png" width="24">
  				</a>
  			</c:when>
  			<c:otherwise>
  				<a href="<c:url value="setMSUnavailable/?machine-serviceable-id=${ms.machineServiceableId}"/>" 
    			onclick="return confirm('${dialog_not_available_machine_serviceable}')">
  				<img src="resources/images/disable.png" width="24">
  				</a>
  			</c:otherwise>
			</c:choose>
    	</td>
    </tr>
  	</c:forEach>
  	</tbody>
  	</table>
  	
  	<div>
  	<a name="add_new_serviceable_machine"></a>
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
  	</div>
  	</div>
</body>
</html>