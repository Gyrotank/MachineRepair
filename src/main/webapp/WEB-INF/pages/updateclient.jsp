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

	<mycustomtags:updatesidebar requestParamName="client-id"
		requestParamValue="${entityCurrent.clientId}" />

	<div id="content">
		<div class="tabs-content">
			<div class="content">
				<h2>
					<spring:message code="label.adminpage.updateClient" />
				</h2>
				<form:form method="post" commandName="entity" action="updateClient"
					accept-charset="UTF-8">
					<table>
						<tr>
							<td><label for="clientNameInput"> <spring:message
										code="label.adminpage.clients.name" />
							</label></td>
							<td><input value="${entityCurrent.clientName}"
								maxlength="50" size="50" readonly="readonly" disabled="disabled" />
							</td>
							<td><form:input path="clientName" id="clientNameInput"
									maxlength="50" size="50" /></td>
							<td><form:errors path="clientName" cssClass="error" /></td>
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