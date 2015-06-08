<%@ include file="tagsused.jsp"%>

<html>
<head>
<%@ include file="header.jsp"%>

<title><spring:message code="label.adminpage.title" /></title>
</head>

<body>
	<h1 align="center">
		<spring:message code="label.adminpage.header" />
	</h1>

	<%@ include file="adminsidebar.jsp"%>

	<div id="content">
		<div class="tabs-content">

			<div class="content" id="orders">
				<h1>
					<spring:message code="label.adminpage.orders" />
				</h1>
				<div class="success">
					<c:out value="${message_added}" />
				</div>
				<div class="error">
					<c:out value="${message_not_added}" />
				</div>
				<table data-toggle="table"
					data-classes="table table-hover table-condensed"
					data-striped="true" border="1" 
					style="width: 900px; margin: 0 auto; float: center">
					<thead>
						<tr>
							<th align="center" data-sortable="true"></th>
							<th align="center"><spring:message
									code="label.adminpage.orders.client" /></th>
							<th align="center"><spring:message
									code="label.adminpage.orders.repairType" /></th>
							<th align="center"><spring:message
									code="label.adminpage.orders.machine" /></th>
							<th align="center"><spring:message
									code="label.adminpage.orders.sn" /></th>
							<th align="center"><spring:message
									code="label.adminpage.orders.date" /></th>
							<th align="center"><spring:message
									code="label.adminpage.orders.status" /></th>
							<th align="center"><spring:message
									code="label.adminpage.orders.manager" /></th>
							<th align="center" data-sortable="false"><spring:message
									code="label.adminpage.orders.actions" /></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="o" items="${entities}" varStatus="loopStatus">
							<tr class="${loopStatus.index % 2 == 0 ? 'even' : 'odd'}">
								<td><c:out
										value="${loopStatus.index + 1 + page_number * pages_size}" /></td>
								<td>${o.client.clientName}</td>
								<td><c:choose>
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
									</c:choose></td>
								<td><c:choose>
										<c:when test="${o.machine.machineServiceable.available == 0}">
											<div class="disabled">
												${o.machine.machineServiceable.machineServiceableName}</div>
										</c:when>
										<c:otherwise>
    			${o.machine.machineServiceable.machineServiceableName}
    		</c:otherwise>
									</c:choose></td>
								<td>${o.machine.machineSerialNumber}</td>
								<td>${o.start.date}-${o.start.month + 1}-${o.start.year + 1900}</td>
								<td><c:choose>
										<c:when test="${locale == 'ru'}">
											<c:out value="${o.status.orderStatusNameRu}" />
										</c:when>
										<c:otherwise>
											<c:out value="${o.status.orderStatusName}" />
										</c:otherwise>
									</c:choose></td>
								<td>${o.manager}</td>
								<td><a
									href="<c:url value="updateorder/?order-id=${o.orderId}"/>">
										<img src="resources/images/edit.png" width="24">
								</a></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<br>
				<mycustomtags:tablepaging action="adminpageorders/orderpaging"
					buttonName="orderPageNumber" pages_count="${pages_count}"
					page_number="${page_number}" pages_size="${pages_size}" />
				<br> <a id="add_new_order"></a>
				<h2>
					<spring:message code="label.adminpage.addNewOrder" />
				</h2>
				<form:form method="post" commandName="clientSearchQuery" action="searchClients"
					accept-charset="UTF-8">
					<c:if test="${empty message_search_results}">
						<br>
					</c:if>
					<c:if test="${not empty message_search_results}">
						<c:out value="${message_search_results}" />
					</c:if>
					<table>
						<tr>
							<td><label>
								<spring:message
									code="label.adminpage.addNewOrder.clientSearchForm" />
							</label></td>
							<td>
								<form:input path="searchQueryArgument" 
									value="${clientSearchQuery.searchQueryArgument}"/>
							</td>							
							<td><button>
								<spring:message code="label.adminpage.buttonSearch" />
							</button></td>
						</tr>
					</table>
				</form:form>
				<form:form method="post" commandName="dataObject" action="addOrder"
					accept-charset="UTF-8">
					<table>
						<tr>
							<td><label> <spring:message
										code="label.adminpage.addNewOrder.clientFrom" />
							</label></td>
							<td><form:select path="clientId">
									<form:option value="0">
										<spring:message
											code="label.adminpage.addNewOrder.selectClient" />
									</form:option>
									<form:options items="${clients}" />
								</form:select></td>
							<td><form:errors path="clientId" cssClass="error" /></td>
						</tr>
						<tr>
							<td><label> <spring:message
										code="label.adminpage.orders.repairType" />
							</label></td>
							<td><form:select path="repairTypeId">
									<form:option value="0">
										<spring:message
											code="label.adminpage.addNewOrder.selectRepairType" />
									</form:option>
									<form:options items="${repair_types}" />
								</form:select></td>
							<td><form:errors path="repairTypeId" cssClass="error" /></td>
						</tr>
						<tr>
							<td><label><spring:message
										code="label.adminpage.addNewOrder.machineFrom" /> </label></td>
							<td><form:select path="machineId">
									<form:option value="0">
										<spring:message
											code="label.adminpage.addNewOrder.selectMachine" />
									</form:option>
									<form:options items="${machines}" />
								</form:select></td>
							<td><form:errors path="machineId" cssClass="error" /></td>
						</tr>
						<tr>
							<td><label> <spring:message
										code="label.adminpage.addNewOrder.date" />
							</label></td>
							<td><form:input path="startDate"
									value="${dataObject.startDate}" /></td>
							<td><form:errors path="startDate" cssClass="error" /></td>
						</tr>
						<tr>
							<td><label> <spring:message
										code="label.adminpage.addNewOrder.status" />
							</label></td>
							<td><form:select path="orderStatusId">
									<form:option value="0">
										<spring:message
											code="label.adminpage.addNewOrder.selectStatus" />
									</form:option>
									<form:options items="${order_statuses}" />
								</form:select></td>
							<td><form:errors path="orderStatusId" cssClass="error" /></td>
						</tr>
						<tr>
							<td><label><spring:message
										code="label.adminpage.addNewOrder.manager" /></label></td>
							<td><form:select path="manager">
									<form:option value="-">
										<spring:message
											code="label.adminpage.addNewOrder.selectManager" />
									</form:option>
									<form:options items="${managers}" />
								</form:select></td>
							<td><form:errors path="manager" cssClass="error" /></td>
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