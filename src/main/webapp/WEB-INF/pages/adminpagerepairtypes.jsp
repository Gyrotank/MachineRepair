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
   
    <div class="content" id="repair_types">
  	<h1><spring:message code="label.adminpage.repairTypes" /></h1>
  	<div class="success">
  		<c:out value="${message_repair_type_added}"/>
  	</div>
  	<div class="error">
  		<c:out value="${message_repair_type_not_added}"/>
  	</div>
  	<form method="post" action="adminpagerepairtypes/repairtypepaging" accept-charset="UTF-8">
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
  	<br>
  	<table data-toggle="table" 
		data-classes="table table-hover table-condensed" 
    	data-striped="true"
    	data-pagination="true"
		border="1" style="width:900px" align="center">
	<thead>
	<tr><th align="center"></th>
	<th align="center">
		<spring:message code="label.adminpage.repairTypes.name" />
	</th>
	<th align="center">
		<spring:message code="label.adminpage.repairTypes.price" />
	</th>
	<th align="center">
		<spring:message code="label.adminpage.repairTypes.duration" />
	</th>
	<th align="center" data-sortable="false">
		<spring:message code="label.adminpage.repairTypes.actions" />
	</th>
	</tr>
	</thead>
	<tbody>
  	<c:forEach var="rt" items="${repair_types_short}" varStatus="loopStatus">    	
    <tr class="${loopStatus.index % 2 == 0 ? 'even' : 'odd'}">
    	<td>
    		<c:choose>
  			<c:when test="${rt.available == 0}">
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
    			<c:when test="${locale == 'ru'}">
    				<c:choose>
  					<c:when test="${rt.available == 0}">
  						<div class="disabled">
							${rt.repairTypeNameRu}
						</div>
  					</c:when>
  					<c:otherwise>
    					${rt.repairTypeNameRu}
    				</c:otherwise>
    				</c:choose>
    			</c:when>
    			<c:otherwise>
    				<c:choose>
  					<c:when test="${rt.available == 0}">
  						<div class="disabled">
							${rt.repairTypeName}
						</div>
  					</c:when>
  					<c:otherwise>
    					${rt.repairTypeName}
    				</c:otherwise>
    				</c:choose>
    			</c:otherwise>
    		</c:choose>
    	</td> 
    	<td>
    		<c:choose>
  			<c:when test="${rt.available == 0}">
  				<div class="disabled">
					${rt.repairTypePrice}
				</div>
  			</c:when>
  			<c:otherwise>
    			${rt.repairTypePrice}
    		</c:otherwise>
    		</c:choose>
    	</td>
    	<td>
    		<c:choose>
  			<c:when test="${rt.available == 0}">
  				<div class="disabled">
					${rt.repairTypeDuration}
				</div>
  			</c:when>
  			<c:otherwise>
    			${rt.repairTypeDuration}
    		</c:otherwise>
    		</c:choose>
    	</td>
    	<td>
    		<a href="<c:url value="updaterepairtype/?repair-type-id=${rt.repairTypeId}"/>">
  				<img src="resources/images/edit.png" width="24"></a>
			<c:choose>
  			<c:when test="${rt.available == 0}">
  				<a href="<c:url value="setRTAvailable/?repair-type-id=${rt.repairTypeId}"/>" 
    			onclick="return confirm('${dialog_available_repair_type}')">
  				<img src="resources/images/enable.png" width="24">
  				</a>
  			</c:when>
  			<c:otherwise>
  				<a href="<c:url value="setRTUnavailable/?repair-type-id=${rt.repairTypeId}"/>" 
    			onclick="return confirm('${dialog_not_available_repair_type}')">
  				<img src="resources/images/disable.png" width="24">
  				</a>
  			</c:otherwise>
			</c:choose>
    	</td>
    </tr>
  	</c:forEach>
  	</tbody>
  	</table>
  	
  	<a name="add_new_repair_type"></a>
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
  	</div>
  	</div>
</body>
</html>