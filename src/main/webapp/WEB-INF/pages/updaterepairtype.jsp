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

	<mycustomtags:updatesidebar requestParamName="repair-type-id"
		requestParamValue="${entityCurrent.repairTypeId}" />

	<div id="content">
		<div class="tabs-content">
			<div class="content">
				<h2>
					<spring:message code="label.adminpage.updateRepairType" />
				</h2>
				<form:form method="post" commandName="entity"
					action="updateRepairType" accept-charset="UTF-8">
					<table>
						<tr>
							<td><label for="repairTypeNameInput"> <spring:message
										code="label.adminpage.repairTypes.nameForm" />
							</label></td>
							<td><input value="${entityCurrent.repairTypeName}"
								maxlength="50" size="50" readonly="readonly" disabled="disabled" />
							</td>
							<td><form:input path="repairTypeName"
									id="repairTypeNameInput" maxlength="20" /></td>
							<td><form:errors path="repairTypeName" cssClass="error" /></td>
						</tr>
						<tr>
							<td><label for="repairTypeNameInputRu"> <spring:message
										code="label.adminpage.repairTypes.nameRu" />
							</label></td>
							<td><input value="${entityCurrent.repairTypeNameRu}"
								maxlength="50" size="50" readonly="readonly" disabled="disabled" />
							</td>
							<td><form:input path="repairTypeNameRu"
									id="repairTypeNameInputRu" maxlength="20" /></td>
							<td><form:errors path="repairTypeNameRu" cssClass="error" /></td>
						</tr>
						<tr>
							<td><label for="repairTypePriceInput"> <spring:message
										code="label.adminpage.repairTypes.price" />
							</label></td>
							<td><input value="${entityCurrent.repairTypePrice}"
								maxlength="50" size="50" readonly="readonly" disabled="disabled" />
							</td>
							<td><form:input path="repairTypePrice"
									id="repairTypePriceInput" maxlength="11" /></td>
							<td><form:errors path="repairTypePrice" cssClass="error" /></td>
						</tr>
						<tr>
							<td><label for="repairTypeDurationInput"> <spring:message
										code="label.adminpage.repairTypes.duration" />
							</label></td>
							<td><input value="${entityCurrent.repairTypeDuration}"
								maxlength="50" size="50" readonly="readonly" disabled="disabled" />
							</td>
							<td><form:input path="repairTypeDuration"
									id="repairTypeDurationInput" maxlength="2" /></td>
							<td><form:errors path="repairTypeDuration" cssClass="error" /></td>
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