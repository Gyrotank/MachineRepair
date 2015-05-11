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

			<div class="content" id="current_orders">
				<h2>
					<spring:message code="label.clientpage.yourCurrentOrders" />
				</h2>
				<div class="success">
					<c:out value="${message_payment_succeeded}" />
				</div>
				<div class="error">
					<c:out value="${message_payment_failed}" />
				</div>
				<c:choose>
					<c:when test="${empty my_current_orders}">
						<br>
						<span style="font-size: 18px; line-height: 18px;"> <c:out
								value="${message_current_orders}" /> <br>
						<br>
						</span>
					</c:when>
					<c:otherwise>
						<table data-toggle="table"
							data-classes="table table-hover table-condensed"
							data-striped="true" border="1" style="width: 900px"
							align="center">
							<thead>
								<tr>
									<th align="center" data-sortable="true" data-switchable="false"></th>
									<th align="center" data-sortable="true" data-switchable="false">
										<spring:message code="label.clientpage.yourOrders.repairType" />
									</th>
									<th align="center" data-sortable="true" data-switchable="false">
										<spring:message code="label.clientpage.yourOrders.sn" />
									</th>
									<th align="center" data-sortable="true" data-switchable="false">
										<spring:message code="label.clientpage.yourOrders.machineName" />
									</th>
									<th align="center" data-sortable="true" data-switchable="false">
										<spring:message code="label.clientpage.yourOrders.date" />
									</th>
									<th align="center" data-sortable="true" data-switchable="false">
										<spring:message code="label.clientpage.yourOrders.status" />
									</th>
									<th align="center" data-switchable="false"><spring:message
											code="label.clientpage.yourOrders.actions" /></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="co" items="${my_current_orders}"
									varStatus="loopStatus">
									<tr class="${loopStatus.index % 2 == 0 ? 'even' : 'odd'}">
										<td><c:out
												value="${loopStatus.index + 1 + page_number * pages_size}" /></td>
										<td><c:choose>
												<c:when test="${co.repairType.available == 0}">
													<div class="disabled">
														<c:choose>
															<c:when test="${locale == 'ru'}">
    					${co.repairType.repairTypeNameRu}
    				</c:when>
															<c:otherwise>
    					${co.repairType.repairTypeName}
    				</c:otherwise>
														</c:choose>
													</div>
												</c:when>
												<c:otherwise>
													<c:choose>
														<c:when test="${locale == 'ru'}">
    				${co.repairType.repairTypeNameRu}
    			</c:when>
														<c:otherwise>
    				${co.repairType.repairTypeName}
    			</c:otherwise>
													</c:choose>
												</c:otherwise>
											</c:choose></td>
										<td>${co.machine.machineSerialNumber}</td>
										<td><c:choose>
												<c:when
													test="${co.machine.machineServiceable.available == 0}">
													<div class="disabled">
														${co.machine.machineServiceable.machineServiceableName}</div>
												</c:when>
												<c:otherwise>
    			${co.machine.machineServiceable.machineServiceableName}
    		</c:otherwise>
											</c:choose></td>
										<td>${co.start}</td>
										<td><c:choose>
												<c:when test="${locale == 'ru'}">
													<c:out value="${co.status.orderStatusNameRu}" />
												</c:when>
												<c:otherwise>
													<c:out value="${co.status.orderStatusName}" />
												</c:otherwise>
											</c:choose></td>
										<c:if
											test="${co.status.orderStatusName == 'ready' && locale == 'en'}">
											<td align="center"><a
												href="<c:url value="pay/?order_id=${co.orderId}" />"
												onclick="return confirm('${dialog_pay_order} ${co.repairType.repairTypePrice}?')">
													<img src="resources/images/pay_en.png" width="24">
											</a></td>
										</c:if>
										<c:if
											test="${co.status.orderStatusName == 'ready' && locale == 'ru'}">
											<td align="center"><a
												href="<c:url value="pay/?order_id=${co.orderId}" />"
												onclick="return confirm('${dialog_pay_order} ${co.repairType.repairTypePrice}?')">
													<img src="resources/images/pay_ru.png" width="24">
											</a></td>
										</c:if>
									</tr>
								</c:forEach>
							</tbody>
						</table>
						<br>
						<mycustomtags:tablepaging
							action="clientpagecurrentorders/currentorderspaging"
							buttonName="currentOrdersPageNumber" pages_count="${pages_count}"
							page_number="${page_number}" pages_size="${pages_size}" />
					</c:otherwise>
				</c:choose>
			</div>
		</div>
	</div>
</body>
</html>