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
			<div class="content active" id="machines">
				<h1>
					<spring:message code="label.adminpage.machines" />
				</h1>
				<div class="success">
					<c:out value="${message_added}" />
				</div>
				<div class="error">
					<c:out value="${message_not_added}" />
				</div>
				<table data-toggle="table"
					data-classes="table table-hover table-condensed"
					data-striped="true" border="1" style="width: 900px" align="center">
					<thead>
						<tr>
							<th align="center"></th>
							<th align="center"><spring:message
									code="label.adminpage.machines.name" /></th>
							<th align="center"><spring:message
									code="label.adminpage.machines.sn" /></th>
							<th align="center"><spring:message
									code="label.adminpage.machines.year" /></th>
							<th align="center"><spring:message
									code="label.adminpage.machines.timesRepaired" /></th>
							<th align="center" data-sortable="false"><spring:message
									code="label.adminpage.machines.actions" /></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="m" items="${entities}"
							varStatus="loopStatus">
							<tr class="${loopStatus.index % 2 == 0 ? 'even' : 'odd'}">
								<td><c:out
										value="${loopStatus.index + 1 + page_number * pages_size}" /></td>
								<td><c:choose>
										<c:when test="${m.machineServiceable.available == 0}">
											<div class="disabled">
												${m.machineServiceable.machineServiceableName}</div>
										</c:when>
										<c:otherwise>
    			${m.machineServiceable.machineServiceableName}
    		</c:otherwise>
									</c:choose></td>
								<td>${m.machineSerialNumber}</td>
								<td>${m.machineYear}</td>
								<td>${m.machineTimesRepaired}</td>
								<td><a
									href="<c:url value="/updatemachine/?machine-id=${m.machineId}"/>">
										<img src="resources/images/edit.png" width="24">
								</a></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<br>
				<mycustomtags:tablepaging action="adminpagemachines/machinepaging"
					buttonName="machinePageNumber" pages_count="${pages_count}"
					page_number="${page_number}" pages_size="${pages_size}" />

				<br>
				<div>
					<a name="add_new_machine"></a>
					<h2>
						<spring:message code="label.adminpage.addNewMachine" />
					</h2>
					<form:form method="post" commandName="dataObject"
						action="addMachine" accept-charset="UTF-8">
						<table>
							<tr>
								<td><label for="machineServiceableId"> <spring:message
											code="label.adminpage.addNewMachine.machineNameFrom" />
								</label></td>
								<td><script type="text/javascript">
									$("#machineServiceableId").ufd();
								</script> <form:select id="machineServiceableId" path="machineServiceableId">
										<option value="0">
											<spring:message
												code="label.adminpage.addNewMachine.selectMachineName" />
										</option>
										<c:forEach var="ms" items="${machines_serviceable}">
											<c:choose>
												<c:when
													test="${dataObject.machineServiceableId == ms.machineServiceableId}">
													<option selected value="${ms.machineServiceableId}">
														<c:out value="${ms.machineServiceableName}" />
													</option>
												</c:when>
												<c:otherwise>
													<option value="${ms.machineServiceableId}">
														<c:out value="${ms.machineServiceableName}" />
													</option>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</form:select></td>
								<td><form:errors path="machineServiceableId"
										cssClass="error" /></td>
							</tr>
							<tr>
								<td><label for="machineSerialNumberInput"> <spring:message
											code="label.adminpage.addNewMachine.sn" />
								</label></td>
								<td><form:input path="machineSerialNumber"
										id="machineSerialNumberInput" maxlength="32" /></td>
								<td><form:errors path="machineSerialNumber"
										cssClass="error" /></td>
							</tr>
							<tr>
								<td><label for="machineYearInput"> <spring:message
											code="label.adminpage.addNewMachine.year" />
								</label></td>
								<td><form:input size="4" minlength="4" maxlength="4"
										path="machineYear" id="machineYearInput" /></td>
								<td><form:errors path="machineYear" cssClass="error" /></td>
							</tr>
							<tr>
								<td><label for="machineTimesRepairedInput"> <spring:message
											code="label.adminpage.addNewMachine.timesRepaired" />
								</label></td>
								<td><form:input size="5" maxlength="3"
										path="machineTimesRepaired" id="machineTimesRepairedInput" /></td>
								<td><form:errors path="machineTimesRepaired"
										cssClass="error" /></td>
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

	<br>
	<hr class="style-seven">
</body>
</html>