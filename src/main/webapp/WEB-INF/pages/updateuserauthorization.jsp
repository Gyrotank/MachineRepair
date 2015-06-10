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

	<mycustomtags:updatesidebar requestParamName="user-authorization-id"
		requestParamValue="${userAuthorizationDTOCurrent.userAuthorizationId}" />

	<div id="content">
		<div class="tabs-content">
			<div class="content">
				<h2>
					<spring:message code="label.adminpage.updateUserAuthorization" />
					${userAuthorizationDTOCurrent.user.login}
				</h2>
				<form:form method="post" commandName="userAuthorizationDTO"
					action="updateUserAuthorization" accept-charset="UTF-8">
					<table data-toggle="table"
						data-classes="table table-hover table-condensed"
						data-striped="true" border="1" style="width: 600px; float: center">
						<thead>
							<tr>
								<c:forEach var="ur" items="${userRoles}" varStatus="loopStatus">
									<th data-align="center" data-width="200"><c:choose>
											<c:when test="${locale == 'ru'}">
												<c:out value="${ur.descRu}" />
											</c:when>
											<c:otherwise>
												<c:out value="${ur.descEn}" />
											</c:otherwise>
										</c:choose></th>
								</c:forEach>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td><c:choose>
										<c:when test="${userAuthorizationDTOCurrent.isAdmin}">
											<input type="checkbox" checked readonly="readonly"
												disabled="disabled">
										</c:when>
										<c:otherwise>
											<input type="checkbox" readonly="readonly"
												disabled="disabled">
										</c:otherwise>
									</c:choose></td>
								<td><c:choose>
										<c:when test="${userAuthorizationDTOCurrent.isClient}">
											<input type="checkbox" checked readonly="readonly"
												disabled="disabled">
										</c:when>
										<c:otherwise>
											<input type="checkbox" readonly="readonly"
												disabled="disabled">
										</c:otherwise>
									</c:choose></td>
								<td><c:choose>
										<c:when test="${userAuthorizationDTOCurrent.isManager}">
											<input type="checkbox" checked readonly="readonly"
												disabled="disabled">
										</c:when>
										<c:otherwise>
											<input type="checkbox" readonly="readonly"
												disabled="disabled">
										</c:otherwise>
									</c:choose></td>
							</tr>
							<tr>
								<td><c:choose>
										<c:when test="${userAuthorizationDTOCurrent.isOnlyAdmin}">
											<form:checkbox path="isAdmin" id="isAdminInput"
												onclick="return false" checked="true" />
										</c:when>
										<c:when
											test="${userAuthorizationDTOCurrent.isAdmin 
  					&& !userAuthorizationDTOCurrent.isOnlyAdmin}">
											<form:checkbox path="isAdmin" id="isAdminInput"
												checked="true" />
										</c:when>
										<c:otherwise>
											<form:checkbox path="isAdmin" id="isAdminInput" />
										</c:otherwise>
									</c:choose></td>
								<td><c:choose>
										<c:when test="${userAuthorizationDTOCurrent.isClient}">
											<form:checkbox path="isClient" id="isClientInput"
												checked="true" />
										</c:when>
										<c:otherwise>
											<form:checkbox path="isClient" id="isClientInput" />
										</c:otherwise>
									</c:choose></td>
								<td><c:choose>
										<c:when test="${userAuthorizationDTOCurrent.isManager}">
											<form:checkbox path="isManager" id="isManagerInput"
												checked="true" />
										</c:when>
										<c:otherwise>
											<form:checkbox path="isManager" id="isManagerInput" />
										</c:otherwise>
									</c:choose></td>
							</tr>
						</tbody>
					</table>
					<p align="center">
						<button>
							<spring:message code="label.adminpage.buttonUpdate" />
						</button>
					</p>
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