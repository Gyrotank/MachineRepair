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
  	
    <div class="content" id="orders">
  	<h1><spring:message code="label.adminpage.orders" /></h1>
  	<div class="success">
  		<c:out value="${message_order_added}"/>
  	</div>
  	<div class="error">
  		<c:out value="${message_order_not_added}"/>
  	</div>
  	<mycustomtags:tablepaging
  		action="adminpageorders/orderpaging" 
  		buttonName="orderPageNumber"
  		pages_count="${pages_count}"
  		page_number="${page_number}"
  		pages_size="${pages_size}" />  	
  	<br>
  	<table data-toggle="table" 
		data-classes="table table-hover table-condensed" 
    	data-striped="true"    		
		border="1" style="width:900px" align="center">
	<thead>
	<tr><th align="center" data-sortable="true"></th>
	<th align="center">
		<spring:message code="label.adminpage.orders.client" />
	</th>
	<th align="center">
		<spring:message code="label.adminpage.orders.repairType" />
	</th>
	<th align="center">
		<spring:message code="label.adminpage.orders.machine" />
	</th>
	<th align="center">
		<spring:message code="label.adminpage.orders.sn" />
	</th>
	<th align="center">
		<spring:message code="label.adminpage.orders.date" />
	</th>
	<th align="center">
		<spring:message code="label.adminpage.orders.status" />
	</th>
	<th align="center">
		<spring:message code="label.adminpage.orders.manager" />
	</th>
	<th align="center" data-sortable="false">
		<spring:message code="label.adminpage.orders.actions" />
	</th>
	</tr>
	</thead>
	<tbody>
  	<c:forEach var="o" items="${orders_short}" varStatus="loopStatus">    	
    <tr class="${loopStatus.index % 2 == 0 ? 'even' : 'odd'}">
    	<td><c:out value="${loopStatus.index + 1 + page_number * pages_size}"/></td>
    	<td>${o.client.clientName}</td> 
    	<td>    		
    		<c:choose>    			
  				<c:when test="${o.repairType.available == 0}">
  					<div class="disabled">
  						<c:choose>
						<c:when test="${locale == 'ru'}">
    						${o.repairType.repairTypeNameRu}
    					</c:when>
    					<c:otherwise>
    						${o.repairType.repairTypeName}
    					</c:otherwise>
    					</c:choose>
					</div>
  				</c:when>
  				<c:otherwise>
  					<c:choose>
    				<c:when test="${locale == 'ru'}">
    					${o.repairType.repairTypeNameRu}
    				</c:when>
    				<c:otherwise>
    					${o.repairType.repairTypeName}
    				</c:otherwise>
    				</c:choose>
    			</c:otherwise>    			
    		</c:choose>
    	</td>
    	<td>
    		<c:choose>
  			<c:when test="${o.machine.machineServiceable.available == 0}">
  				<div class="disabled">
					${o.machine.machineServiceable.machineServiceableName}
				</div>
  			</c:when>
  			<c:otherwise>
    			${o.machine.machineServiceable.machineServiceableName}
    		</c:otherwise>
    		</c:choose>
    	</td>
    	<td>${o.machine.machineSerialNumber}</td>
    	<td>${o.start.date}-${o.start.month + 1}-${o.start.year + 1900}</td>
    	<td>    		
    		<c:choose>
				<c:when test="${locale == 'ru'}">
					<c:out value="${o.status.orderStatusNameRu}"/>
				</c:when>
				<c:otherwise>
					<c:out value="${o.status.orderStatusName}"/>
				</c:otherwise>
  			</c:choose>
    	</td>
    	<td>${o.manager}</td>
    	<td>
    		<a href="<c:url value="updateorder/?order-id=${o.orderId}"/>">
  				<img src="resources/images/edit.png" width="24"></a>			
    	</td>
    </tr>
  	</c:forEach>
  	</tbody>
  	</table>
  	
  	<a name="add_new_order"></a>
  	<h2><spring:message code="label.adminpage.addNewOrder" /></h2>
  	<form:form method="post" commandName="order" action="addOrder" accept-charset="UTF-8">
  	<table>
  		<tr>
  			<td><label>
  			<spring:message code="label.adminpage.addNewOrder.clientFrom" />
  			</label></td>
  			<td><select name="clientId">
  			<option value="0">
  				<spring:message code="label.adminpage.addNewOrder.selectClient" />
  			</option>
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
  			</label></td>
  			<td><select name="machineId">
  			<option value="0">
  				<spring:message code="label.adminpage.addNewOrder.selectMachine" />
  			</option>
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
  			<td><label>
  				<spring:message code="label.adminpage.addNewOrder.status" />
  			</label></td>
  			<td><select name="orderStatusId">
  				<option value="0">
  					<spring:message code="label.adminpage.addNewOrder.selectStatus" />
  				</option>
  				<c:forEach var="os" items="${order_statuses}">
  				<c:choose>
  					<c:when test="${selected_order_order_status_id == os.orderStatusId}">
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
  			</select></td>
  			<td>
  			<div class="error">
  				<c:out value="${message_order_order_status_id}"/>
  			</div>
  			</td>  			
  		</tr>
  		<tr>
  			<td>
  				<label><spring:message code="label.adminpage.addNewOrder.manager" /></label>
  			</td>
  			<td><select name="manager">
  			<option value="-">
  				<spring:message code="label.adminpage.addNewOrder.selectManager" />
  			</option>
  			<c:forEach var="man" items="${managers}">
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