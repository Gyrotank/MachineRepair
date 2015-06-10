<%@ include file="tagsused.jsp"%>

<html>
<head>
<%@ include file="header.jsp"%>

<title><spring:message code="label.clientpage.title" /></title>
</head>

<body>

	<%@ include file="clientsidebar.jsp"%>

	<div id="content">
		<br>
		<h1>
			<spring:message code="label.clientpage.header.welcome" />
			${clientname}!
		</h1>
		<br>
		<div class="tabs-content">
			<div class="content" id="past_orders">
				<h2>
					<spring:message code="label.clientpage.yourPastOrders" />
				</h2>
				<c:choose>
					<c:when test="${empty my_past_orders}">
						<br>
						<span style="font-size: 18px; line-height: 18px;"> <c:out
								value="${message_past_orders}" /> <br>
						<br>
						<br>
						</span>
					</c:when>
					<c:otherwise>
						<table data-toggle="table"
							data-classes="table table-hover table-condensed"
							data-striped="true" border="1" style="width: 900px; float: center">
							<thead>
								<tr>
									<th align="center" data-sortable="true"></th>
									<th align="center" data-sortable="true"><spring:message
											code="label.clientpage.yourOrders.repairType" /></th>
									<th align="center" data-sortable="true"><spring:message
											code="label.clientpage.yourOrders.sn" /></th>
									<th align="center" data-sortable="true"><spring:message
											code="label.clientpage.yourOrders.machineName" /></th>
									<th align="center" data-sortable="true"><spring:message
											code="label.clientpage.yourOrders.date" /></th>
									<th align="center" data-sortable="true"><spring:message
											code="label.clientpage.yourOrders.status" /></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="po" items="${my_past_orders}"
									varStatus="loopStatus">
									<tr class="${loopStatus.index % 2 == 0 ? 'even' : 'odd'}">
										<td><c:out
												value="${loopStatus.index + 1 + page_number * pages_size}" /></td>
										<td><c:choose>
												<c:when test="${po.repairType.available == 0}">
													<div class="disabled">
														<c:choose>
															<c:when test="${locale == 'ru'}">
    					${po.repairType.repairTypeNameRu}
    				</c:when>
															<c:otherwise>
    					${po.repairType.repairTypeName}
    				</c:otherwise>
														</c:choose>
													</div>
												</c:when>
												<c:otherwise>
													<c:choose>
														<c:when test="${locale == 'ru'}">
    				${po.repairType.repairTypeNameRu}
    			</c:when>
														<c:otherwise>
    				${po.repairType.repairTypeName}
    			</c:otherwise>
													</c:choose>
												</c:otherwise>
											</c:choose></td>
										<td>${po.machine.machineSerialNumber}</td>
										<td><c:choose>
												<c:when
													test="${po.machine.machineServiceable.available == 0}">
													<div class="disabled">
														${po.machine.machineServiceable.machineServiceableName}</div>
												</c:when>
												<c:otherwise>
    			${po.machine.machineServiceable.machineServiceableName}
    		</c:otherwise>
											</c:choose></td>
										<td>${po.start}</td>
										<td><c:choose>
												<c:when test="${locale == 'ru'}">
													<c:out value="${po.status.orderStatusNameRu}" />
												</c:when>
												<c:otherwise>
													<c:out value="${po.status.orderStatusName}" />
												</c:otherwise>
											</c:choose></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
						<br>
						<mycustomtags:tablepaging
							action="clientpagepastorders/pastorderspaging"
							buttonName="pastOrdersPageNumber" pages_count="${pages_count}"
							page_number="${page_number}" pages_size="${pages_size}" />
						<br>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
	</div>
</body>
</html>