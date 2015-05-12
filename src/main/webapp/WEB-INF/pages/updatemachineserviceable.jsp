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

	<mycustomtags:updatesidebar requestParamName="machine-serviceable-id"
		requestParamValue="${entityCurrent.machineServiceableId}" />

	<div id="content">
		<div class="tabs-content">
			<div class="content">
				<h2>
					<spring:message code="label.adminpage.updateMachineServiceable" />
				</h2>
				<form:form method="post" commandName="entity"
					action="updateMachineServiceable" accept-charset="UTF-8">
					<table>
						<tr>
							<td><label for="machineServiceableNameInput"> <spring:message
										code="label.adminpage.serviceableMachines.name" /></label></td>
							<td><input
								value="${entityCurrent.machineServiceableName}"
								maxlength="50" size="50" readonly="readonly" disabled="disabled" />
							</td>
							<td><form:input path="machineServiceableName"
									id="machineServiceableNameInput" size="50" maxlength="50" /></td>
							<td><form:errors path="machineServiceableName"
									cssClass="error" /></td>
						</tr>
						<tr>
							<td><label for="machineServiceableNameTrademark"> <spring:message
										code="label.adminpage.serviceableMachines.trademark" />
							</label></td>
							<td><input
								value="${entityCurrent.machineServiceableTrademark}"
								size="50" maxlength="50" readonly="readonly" disabled="disabled" />
							</td>
							<td><form:input path="machineServiceableTrademark"
									id="machineServiceableTrademarkInput" size="50" maxlength="50" />
							</td>
							<td><form:errors path="machineServiceableTrademark"
									cssClass="error" /></td>
						</tr>
						<tr>
							<td><label for="machineServiceableCountryInput"> <spring:message
										code="label.adminpage.serviceableMachines.countryForm" />
							</label></td>
							<td><input
								value="${entityCurrent.machineServiceableCountry}"
								size="50" maxlength="50" readonly="readonly" disabled="disabled" />
							</td>
							<td><form:input path="machineServiceableCountry"
									id="machineServiceableCountryInput" size="50" maxlength="50" />
							</td>
							<td><form:errors path="machineServiceableCountry"
									cssClass="error" /></td>
						</tr>
						<tr>
							<td><label for="machineServiceableCountryRuInput"> <spring:message
										code="label.adminpage.serviceableMachines.countryRu" />
							</label></td>
							<td><input
								value="${entityCurrent.machineServiceableCountryRu}"
								size="50" maxlength="50" readonly="readonly" disabled="disabled" />
							</td>
							<td><form:input path="machineServiceableCountryRu"
									id="machineServiceableCountryRuInput" size="50" maxlength="50" />
							</td>
							<td><form:errors path="machineServiceableCountryRu"
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