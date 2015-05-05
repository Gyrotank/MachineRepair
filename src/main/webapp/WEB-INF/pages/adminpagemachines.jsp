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
	<div class="content active" id="machines">
	<h1><spring:message code="label.adminpage.machines" /></h1>
	<div class="success">
  		<c:out value="${message_machine_added}"/>
  	</div>
  	<div class="error">
  		<c:out value="${message_machine_not_added}"/>
  	</div>
	<form method="post" action="adminpagemachines/machinepaging" accept-charset="UTF-8">
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
  	<br> 
	<table data-toggle="table" 
		data-classes="table table-hover table-condensed" 
    	data-striped="true"
    	data-pagination="true"		
		border="1" style="width:900px" align="center">
	<thead>
	<tr><th align="center">  </th>
	<th align="center">
		<spring:message code="label.adminpage.machines.name" /></th>
	<th align="center">
		<spring:message code="label.adminpage.machines.sn" /></th>
	<th align="center">
		<spring:message code="label.adminpage.machines.year" /></th>
	<th align="center">
		<spring:message code="label.adminpage.machines.timesRepaired" /></th>
	<th align="center" data-sortable="false">
		<spring:message code="label.adminpage.machines.actions" />
	</th>
	</tr>
	</thead>
	<tbody>
  	<c:forEach var="m" items="${machines_short}" varStatus="loopStatus">    	
    <tr class="${loopStatus.index % 2 == 0 ? 'even' : 'odd'}">
    	<td><c:out value="${loopStatus.index + 1}"/></td>
    	<td>
    		<c:choose>
  			<c:when test="${m.machineServiceable.available == 0}">
  				<div class="disabled">
					${m.machineServiceable.machineServiceableName}
				</div>
  			</c:when>
  			<c:otherwise>
    			${m.machineServiceable.machineServiceableName}
    		</c:otherwise>
    		</c:choose>
    	</td> 
    	<td>${m.machineSerialNumber}</td>
    	<td>${m.machineYear}</td>
    	<td>${m.machineTimesRepaired}</td>
    	<td>
    		<a href="<c:url value="/updatemachine/?machine-id=${m.machineId}"/>">
  				<img src="resources/images/edit.png" width="24"></a>
<%-- 			<a href="<c:url value="/deletemachine/?machine-id=${m.machineId}"/>"  --%>
<%--     			onclick="return confirm('${dialog_delete_machine}')"> --%>
<!--   				<img src="resources/images/delete.png" width="24"></a> -->
    	</td>
    </tr>
  	</c:forEach>
  	</tbody>
  	</table>
  	
  	<div>
  	<a name="add_new_machine"></a>
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
  	</div>
  	</div>
  	
  	<br><hr class="style-seven">
</body>
</html>