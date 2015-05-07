<%@ include file="tagsused.jsp" %>

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
  	
  	<table border="1" data-toggle="table" 
		data-classes="table table-hover table-condensed" 
    	data-striped="true"    		
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
					<c:out value="${loopStatus.index + 1 + page_number * pages_size}"/>
				</div>
  			</c:when>
  			<c:otherwise>
    			<c:out value="${loopStatus.index + 1 + page_number * pages_size}"/>
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
  	<br>
  	<mycustomtags:tablepaging
  		action="adminpagemachinesserviceable/machineserviceablepaging" 
  		buttonName="machineServiceablePageNumber"
  		pages_count="${pages_count}"
  		page_number="${page_number}"
  		pages_size="${pages_size}" />  	
  	
  	<br>
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