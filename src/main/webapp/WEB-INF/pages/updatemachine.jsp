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

	<mycustomtags:updatesidebar requestParamName="machine-id"
		requestParamValue="${entityCurrent.machineId}" />

	<div id="content">
		<div class="tabs-content">
			<div class="content">
				<h2>
					<spring:message code="label.adminpage.updateMachine" />
				</h2>
				<form:form method="post" commandName="entity"
					action="updateMachine" accept-charset="UTF-8">
					<table>
						<tr>
							<td><label for="machineSerialNumberInput"> <spring:message
										code="label.adminpage.machines.name" />
							</label></td>
							<td><input
								value="${entityCurrent.machineServiceable.machineServiceableName}"
								maxlength="32" size="32" readonly="readonly" disabled="disabled" />
							</td>
						</tr>
						<tr>
							<td><label for="machineSerialNumberInput"> <spring:message
										code="label.adminpage.addNewMachine.sn" />
							</label></td>
							<td><input value="${entityCurrent.machineSerialNumber}"
								maxlength="32" size="32" readonly="readonly" disabled="disabled" />
							</td>
							<td><form:input path="machineSerialNumber"
									id="machineSerialNumberInput" size="32" maxlength="32" /></td>
							<td><form:errors path="machineSerialNumber" cssClass="error" /></td>
						</tr>
						<tr>
							<td><label for="machineYearInput"> <spring:message
										code="label.adminpage.addNewMachine.year" />
							</label></td>
							<td><input value="${entityCurrent.machineYear}"
								maxlength="4" size="4" readonly="readonly" disabled="disabled" />
							</td>
							<td><form:input size="4" minlength="4" maxlength="4"
									path="machineYear" id="machineYearInput" /></td>
							<td><form:errors path="machineYear" cssClass="error" /></td>
						</tr>
						<tr>
							<td><label for="machineTimesRepairedInput"> <spring:message
										code="label.adminpage.addNewMachine.timesRepaired" />
							</label></td>
							<td><input value="${entityCurrent.machineTimesRepaired}"
								maxlength="3" size="3" readonly="readonly" disabled="disabled" />
							</td>
							<td><form:input size="3" maxlength="3"
									path="machineTimesRepaired" id="machineTimesRepairedInput" /></td>
							<td><form:errors path="machineTimesRepaired"
									cssClass="error" /></td>
						</tr>
						<tr>
							<td><button>
									<spring:message code="label.adminpage.buttonUpdate" />
								</button></td>
						</tr>
					</table>
				</form:form>
				<div class="success">
					<c:out value="${message_entity_updated}" />
				</div>
				<div class="error">
					<c:out value="${message_entity_not_updated}" />
				</div>
				<div class="info">
					<c:out value="${message_entity_no_changes}" />
				</div>
			</div>
		</div>
	</div>
</body>
</html>