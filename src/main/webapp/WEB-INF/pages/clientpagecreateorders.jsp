<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="springForm"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
	"http://www.w3.org/TR/html4/loose.dtd">
	
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
  	<form method="post" action="createorderforfirstrepair" accept-charset="UTF-8">
  	<table>
  		<tr>
  		<td><spring:message code="label.clientpage.createFirstTimeOrder.machine" /></td>
  		<td><select name="machineServiceableId">
		<option value="0"><spring:message code="label.clientpage.createFirstTimeOrder.selectMachine" /></option>
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
  		<td><div class="error">
     		<c:out value="${message_first_repair_serviceable_id}"/>
   		</div></td>  		  		
  		</tr>
  		<tr>
  		<td><spring:message code="label.clientpage.createFirstTimeOrder.sn" /></td>
  		<td><input type="text" name="machineSerialNumber" maxlength="32"
  				 value="${first_repair_entered_serial_number}"/></td>
  		<td><div class="error">
  			<c:out value="${message_first_repair_serial_number}"/>
  		</div></td>
  		</tr>
  		<tr> 
  		<td><spring:message code="label.clientpage.createFirstTimeOrder.year" /></td>  		
  		<td><input type="text" name="machineYear" size="4" maxlength="4"
  				 value="${first_repair_selected_year}"/></td>
		<td><div class="error">
			<c:out value="${message_first_repair_year}"/>
		</div></td>
		</tr>
		<tr>
  		<td><spring:message code="label.clientpage.createFirstTimeOrder.repairType" /></td>
  		<td><select name="repairTypeId">
		<option value="0"><spring:message code="label.clientpage.createFirstTimeOrder.selectRepairType" /></option>
  		<c:forEach var="rt" items="${repair_types}">
  			<c:choose>
  			<c:when test="${first_repair_selected_repairtype_id == rt.repairTypeId}">
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
  		</select></td>
  		<td><div class="error">
  			<c:out value="${message_first_repair_repairtype_id}"/>
  		</div></td>
  		</tr>
  		<tr>
  			<td><button><spring:message code="label.clientpage.buttonCreate" /></button></td>
  		</tr>
  	</table>
  	</form>
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
  	<c:out value="${message_repeated_order}"/>
  	<form method="post" action="createorderforrepeatedrepair" accept-charset="UTF-8">
  	<table>
  		<tr>
  		<td><spring:message code="label.clientpage.createRepeatedOrder.sn" /></td>
  		<td><select name="machineSerialNumber">
  		<option value=""><spring:message code="label.clientpage.createRepeatedOrder.selectSN" /></option>
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
  		<td><div class="error">
  			<c:out value="${message_repeated_repair_serial_number}"/>
  		</div></td>
  		</tr> 
  		<tr> 		
		<td><spring:message code="label.clientpage.createRepeatedOrder.repairType" /></td>
		<td><select name="repairTypeId">
		<option value="0"><spring:message code="label.clientpage.createRepeatedOrder.selectRepairType" /></option>
  		<c:forEach var="rt" items="${repair_types}">
  			<c:choose>
  			<c:when test="${first_repair_selected_repairtype_id == rt.repairTypeId}">
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
  		</select></td>
  		<td><div class="error">
  			<c:out value="${message_repeated_repair_repairtype_id}"/>
  		</div></td>
  		</tr>
  		<tr>
  			<td><button><spring:message code="label.clientpage.buttonCreate" /></button></td>
  		</tr>
  	</table>
  	</form>
  	</c:otherwise>
  	</c:choose>
  	</div>
  	</div>
  	</div>
</body>
</html>