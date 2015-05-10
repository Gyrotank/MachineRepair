<%@ include file="tagsused.jsp" %>
	
<html>
<head>
<%@ include file="header.jsp" %>

<title><spring:message code="label.clientpage.title" /></title>
</head>

<body>
<jsp:useBean id="now" class="java.util.Date" />
	<fmt:formatDate var="current_year" value="${now}" pattern="yyyy" />
	
	<%@ include file="clientsidebar.jsp" %>
	
	<div id="content">
	<br>
	<h1><spring:message code="label.clientpage.header.welcome" />  ${clientname}!</h1>
	<br>
	<div class="tabs-content">
	<div class="content active" id="create_orders">
	<h2><spring:message code="label.clientpage.createFirstTimeOrder" /></h2>
	<div class="success">
  		<c:out value="${message_first_repair_created}"/>
  	</div>
  	<div class="error">
  		<c:out value="${message_first_repair_not_created}"/>
  	</div>
  	<form:form method="post" commandName="orderCreateFirstDTO" 
  		action="createorderforfirstrepair" accept-charset="UTF-8">
  	<table>
  		<tr>
  		<td><spring:message code="label.clientpage.createFirstTimeOrder.machine" /></td>
  		<td><form:select path="machineServiceableId">
		<option value="0"><spring:message code="label.clientpage.createFirstTimeOrder.selectMachine" /></option>
  		<c:forEach var="ms" items="${machines_serviceable}">
  			<c:choose>
  			<c:when test="${orderCreateFirstDTO.machineServiceableId == ms.machineServiceableId}">
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
  		</form:select></td>
  		<td><form:errors path="machineServiceableId" cssClass="error" /></td>  		  		
  		</tr>
  		<tr>
  		<td><spring:message code="label.clientpage.createFirstTimeOrder.sn" /></td>
  		<td><form:input type="text" path="machineSerialNumber" maxlength="32"
  				 value="${orderCreateFirstDTO.machineSerialNumber}"/></td>
  		<td><form:errors path="machineSerialNumber" cssClass="error" /></td>
  		</tr>
  		<tr> 
  		<td><spring:message code="label.clientpage.createFirstTimeOrder.year" /></td>  		
  		<td><form:input type="text" path="machineYear" size="4" maxlength="4"
  				 value="${orderCreateFirstDTO.machineYear}"/></td>
		<td><form:errors path="machineYear" cssClass="error" /></td>
		</tr>
		<tr>
  		<td><spring:message code="label.clientpage.createFirstTimeOrder.repairType" /></td>
  		<td><form:select path="repairTypeId">
		<option value="0"><spring:message code="label.clientpage.createFirstTimeOrder.selectRepairType" /></option>
  		<c:forEach var="rt" items="${repair_types}">
  			<c:choose>
  			<c:when test="${orderCreateFirstDTO.repairTypeId == rt.repairTypeId}">
  				<option selected value="${rt.repairTypeId}">  				
  				<c:choose>
    				<c:when test="${locale == 'ru'}">
    					<c:out value="${rt.repairTypeNameRu} (${rt.repairTypePrice})"/>
    				</c:when>
    				<c:otherwise>
    					<c:out value="${rt.repairTypeName} (${rt.repairTypePrice})" />
    				</c:otherwise>
    			</c:choose>
  				</option>
  			</c:when>
  			<c:otherwise>
  				<option value="${rt.repairTypeId}">
  				<c:choose>
    				<c:when test="${locale == 'ru'}">
    					<c:out value="${rt.repairTypeNameRu} (${rt.repairTypePrice})"/>
    				</c:when>
    				<c:otherwise>
    					<c:out value="${rt.repairTypeName} (${rt.repairTypePrice})" />
    				</c:otherwise>
    			</c:choose>
  				</option>
  			</c:otherwise>
  			</c:choose>  			
  		</c:forEach>
  		</form:select></td>
  		<td><form:errors path="repairTypeId" cssClass="error" /></td>
  		</tr>
  		<tr>
  			<td><button><spring:message code="label.clientpage.buttonCreate" /></button></td>
  		</tr>
  	</table>
  	</form:form>
  	<br>
  	<c:choose>
	<c:when test="${(past_orders_count == 0) && (current_orders_count == 0)}">				
	</c:when>
	<c:otherwise>
  	<h2><spring:message code="label.clientpage.createRepeatedOrder" /></h2>
  		<div class="success">
  		<c:out value="${message_repeated_repair_created}"/>
  	</div>
  	<div class="error">
  		<c:out value="${message_repeated_repair_not_created}"/>
  	</div>  	
  	<form:form method="post" commandName="orderCreateRepeatedDTO" 
  		action="createorderforrepeatedrepair" accept-charset="UTF-8">
  	<table>
  		<tr>
  		<td><spring:message code="label.clientpage.createRepeatedOrder.sn" /></td>
  		<td><form:select path="machineSerialNumber">
  		<option value=""><spring:message code="label.clientpage.createRepeatedOrder.selectSN" /></option>
  		<c:forEach var="sn" items="${my_machines_serial_numbers}">
  			<c:choose>
  			<c:when test="${orderCreateRepeatedDTO.machineSerialNumber == sn}">
  				<option selected value="${sn}"><c:out value="${sn}" /></option>
  			</c:when>
  			<c:otherwise>
  				<option value="${sn}"><c:out value="${sn}" /></option>
  			</c:otherwise>
  			</c:choose>  			
  		</c:forEach>
  		</form:select>
  		</td>
  		<td><form:errors path="machineSerialNumber" cssClass="error" /></td>
  		</tr> 
  		<tr> 		
		<td><spring:message code="label.clientpage.createRepeatedOrder.repairType" /></td>
		<td><form:select path="repairTypeId">
		<option value="0"><spring:message code="label.clientpage.createRepeatedOrder.selectRepairType" /></option>
  		<c:forEach var="rt" items="${repair_types}">
  			<c:choose>
  			<c:when test="${orderCreateRepeatedDTO.repairTypeId == rt.repairTypeId}">
  				<option selected value="${rt.repairTypeId}">  				
  				<c:choose>
    				<c:when test="${locale == 'ru'}">
    					<c:out value="${rt.repairTypeNameRu} (${rt.repairTypePrice})"/>
    				</c:when>
    				<c:otherwise>
    					<c:out value="${rt.repairTypeName} (${rt.repairTypePrice})" />
    				</c:otherwise>
    			</c:choose>
  				</option>
  			</c:when>
  			<c:otherwise>
  				<option value="${rt.repairTypeId}">
  				<c:choose>
    				<c:when test="${locale == 'ru'}">
    					<c:out value="${rt.repairTypeNameRu} (${rt.repairTypePrice})"/>
    				</c:when>
    				<c:otherwise>
    					<c:out value="${rt.repairTypeName} (${rt.repairTypePrice})" />
    				</c:otherwise>
    			</c:choose>
  				</option>
  			</c:otherwise>
  			</c:choose>
  		</c:forEach>
  		</form:select></td>
  		<td><form:errors path="repairTypeId" cssClass="error" /></td>
  		</tr>
  		<tr>
  			<td><button><spring:message code="label.clientpage.buttonCreate" /></button></td>
  		</tr>
  	</table>
  	</form:form>
  	</c:otherwise>
  	</c:choose>
  	</div>
  	</div>
  	</div>
</body>
</html>