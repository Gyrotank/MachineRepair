<%@ include file="tagsused.jsp" %>
			 
<html>
<head>
<%@ include file="header.jsp" %>

<title><spring:message code="label.adminpage.title" /></title>
</head>

<body>
	<h1 align = "center"><spring:message code="label.adminpage.header" /></h1>
	
	<div id="sidebar">
		<c:choose>
  			<c:when test="${locale == 'en'}">
  				<a href="?locale=en&amp;order-id=${order.orderId}">
  				<img src="${pageContext.servletContext.contextPath}/resources/images/usa.png" 
  					width="40"></a>
  			</c:when>
  			<c:otherwise>
  				<a href="?locale=en&amp;order-id=${order.orderId}">
  				<img src="${pageContext.servletContext.contextPath}/resources/images/usa.png" 
  					width="32"></a>
  			</c:otherwise>
		</c:choose>
		<c:choose>
  			<c:when test="${locale == 'ru'}">
  				<a href="?locale=ru&amp;order-id=${order.orderId}">
  				<img src="${pageContext.servletContext.contextPath}/resources/images/rus.png" 
  					width="40"></a>
  			</c:when>
  			<c:otherwise>
  				<a href="?locale=ru&amp;order-id=${order.orderId}">
  				<img src="${pageContext.servletContext.contextPath}/resources/images/rus.png" 
  					width="32"></a>
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
  		<dd class="active"><a href="<c:url value="/adminpage"/>">
			<spring:message code="label.adminpage.sidebar.dashboard" /></a></dd>
  		<dd><a href="<c:url value="/adminpagemachines"/>">
			<spring:message code="label.adminpage.sidebar.machines" /></a></dd>
  		<dd><a href="<c:url value="/adminpagemachinesserviceable"/>">
			<spring:message code="label.adminpage.sidebar.serviceableMachines" /></a></dd>
  		<dd><a href="<c:url value="/adminpagerepairtypes"/>">
			<spring:message code="label.adminpage.sidebar.repairTypes" /></a></dd>
  		<dd><a href="<c:url value="/adminpageusers"/>">
			<spring:message code="label.adminpage.sidebar.users" /></a></dd>
  		<dd><a href="<c:url value="/adminpageuserauthorizations"/>">
			<spring:message code="label.adminpage.sidebar.userAuths" /></a></dd>
  		<dd><a href="<c:url value="/adminpageclients"/>">
			<spring:message code="label.adminpage.sidebar.clients" /></a></dd>
  		<dd><a href="<c:url value="/adminpageorders"/>">
			<spring:message code="label.adminpage.sidebar.orders" /></a></dd>
	</dl>
		<hr class="style-seven">
		<p><a href="<c:url value="/logout"/>">
			<spring:message code="label.adminpage.sidebar.logout" /></a></p>
	</div>
	
	<div id="content">
	<div class="tabs-content">
	<div class="content">
	<h2><spring:message code="label.adminpage.updateOrder" /></h2>
	<form:form method="post" commandName="orderDTO" 
		action="updateOrder" accept-charset="UTF-8">
  	<table>
  		<tr>
  			<td><label>
  				<spring:message code="label.adminpage.orders.client" />
  			</label></td>
  			<td>
  				<input value="${orderCurrent.client.clientName}" maxlength="50" 
  					size="50" readonly="readonly" disabled="disabled"/>
  			</td>
  		</tr>
  		<tr>
  			<td><label>
  				<spring:message code="label.adminpage.orders.repairType" />
  			</label></td>
  			<td>
  				<c:choose>
    				<c:when test="${locale == 'ru'}">
    					<input value="${orderCurrent.repairType.repairTypeNameRu}" maxlength="50" 
  							size="50" readonly="readonly" disabled="disabled"/>
    				</c:when>
    				<c:otherwise>
    					<input value="${orderCurrent.repairType.repairTypeName}" maxlength="50" 
  							size="50" readonly="readonly" disabled="disabled"/>
    				</c:otherwise>
    			</c:choose>  				
  			</td>
  			<td>
  			<form:select path="repairTypeId">
  			<option value="0">
  				<spring:message code="label.adminpage.addNewOrder.selectRepairType" />
  			</option>
  			<c:forEach var="rt" items="${repair_types}">
  				<c:choose>
  					<c:when test="${orderDTO.repairTypeId == rt.repairTypeId}">
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
  			</form:select>
  			</td>
  			<td><form:errors path="repairTypeId" cssClass="error" /></td>
  		</tr>  		
  		<tr>  		
  			<td><label>
  				<spring:message code="label.adminpage.addNewOrder.date" />
  			</label></td>
  			<td>
  				<input value="${orderDTO.startDate}" maxlength="50" 
  					size="50" readonly="readonly" disabled="disabled"/>
  			</td>
  			<td><form:input path="startDate" value="${orderDTO.startDate}"/></td>                
  			<td><form:errors path="startDate" cssClass="error" /></td> 			  		
  		</tr>
  		<tr>
  			<td><label for="statusInput">
  				<spring:message code="label.adminpage.addNewOrder.status" />
  			</label></td>
  			<td>
  				<c:choose>
  				<c:when test="${locale == 'ru'}">  					
  					<input value="${orderCurrent.status.orderStatusNameRu}" maxlength="50" 
  					size="50" readonly="readonly" disabled="disabled"/>
  				</c:when>
  				<c:otherwise>
  					<input value="${orderCurrent.status.orderStatusName}" maxlength="50" 
  					size="50" readonly="readonly" disabled="disabled"/>
  				</c:otherwise>
  				</c:choose>    			
  			</td>
  			<td><form:select path="orderStatusId">
  				<option value="0">
  					<spring:message code="label.adminpage.addNewOrder.selectStatus" />
  				</option>
  				<c:forEach var="os" items="${order_statuses}">
  				<c:choose>
  					<c:when test="${orderDTO.orderStatusId == os.orderStatusId}">
  						<option selected value="${os.orderStatusId}">
  							<c:choose>
  							<c:when test="${locale == 'ru'}">
								<c:out value="${os.orderStatusNameRu}"/>
							</c:when>
							<c:otherwise>
								<c:out value="${os.orderStatusName}"/>
							</c:otherwise>
  							</c:choose>
  						</option>
  					</c:when>
  					<c:otherwise>
  						<option value="${os.orderStatusId}">
  							<c:choose>
  							<c:when test="${locale == 'ru'}">
								<c:out value="${os.orderStatusNameRu}"/>
							</c:when>
							<c:otherwise>
								<c:out value="${os.orderStatusName}"/>
							</c:otherwise>
  							</c:choose>
  						</option>
  					</c:otherwise>
  				</c:choose>  				
  			</c:forEach>
  			</form:select></td>
  			<td><form:errors path="orderStatusId" cssClass="error" /></td>  			
  		</tr>
  		<tr>
  			<td>
  				<label><spring:message code="label.adminpage.addNewOrder.manager" /></label>
  			</td>
  			<td>
  				<input value="${orderCurrent.manager}" maxlength="50" 
  					size="50" readonly="readonly" disabled="disabled"/>
  			</td>
  			<td><form:select path="manager">
  			<option value="-">
  				<spring:message code="label.adminpage.addNewOrder.selectManager" />
  			</option>
  			<c:forEach var="man" items="${managers}">
  				<c:choose>
  					<c:when test="${orderDTO.manager == man}">
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
  			</form:select></td>
  			<td><form:errors path="manager" cssClass="error" /></td>
  		</tr>
  		<tr>  
  			<td><button>
  				<spring:message code="label.adminpage.buttonUpdate" />
  			</button></td>
  		</tr>
  	</table>
  	</form:form>
  	<div class="success">
  		<c:out value="${message_order_updated}"/>
  	</div>
	<div class="error">
  		<c:out value="${message_order_not_updated}"/>
  	</div>
  	<div class="info">
  		<c:out value="${message_order_no_changes}"/>
  	</div>
	</div>
	</div>
	</div>
</body>
</html>