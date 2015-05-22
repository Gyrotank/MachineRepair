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

			<div class="content" id="users">
				<h1>
					<spring:message code="label.adminpage.users" />
				</h1>
				<div class="success">
					<c:out value="${message_added}" />
					<c:out value="${message_enable_disable_succeeded}" />
				</div>
				<div class="error">
					<c:out value="${message_not_added}" />
					<c:out value="${message_enable_disable_failed}" />
				</div>
				<table data-toggle="table"
					data-classes="table table-hover table-condensed"
					data-striped="true" border="1" style="width: 900px" align="center">
					<thead>
						<tr>
							<th align="center"></th>
							<th align="center"><spring:message
									code="label.adminpage.users.login" /></th>
							<th align="center"><spring:message
									code="label.adminpage.users.actions" /></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="u" items="${entities}" varStatus="loopStatus">
							<tr class="${loopStatus.index % 2 == 0 ? 'even' : 'odd'}">
								<td><c:choose>
										<c:when test="${u.enabled == 0}">
											<div class="disabled">
												<c:out
													value="${loopStatus.index + 1 + page_number * pages_size}" />
											</div>
										</c:when>
										<c:otherwise>
											<c:out
												value="${loopStatus.index + 1 + page_number * pages_size}" />
										</c:otherwise>
									</c:choose></td>
								<td><c:choose>
										<c:when test="${u.enabled == 0}">
											<div class="disabled">${u.login}</div>
										</c:when>
										<c:otherwise>
  				${u.login}
  			</c:otherwise>
									</c:choose></td>
								<td><c:choose>
										<c:when test="${u.enabled == 0}">
											<a href="<c:url value="enable/?user_id=${u.userId}"/>"
												onclick="return confirm('${dialog_enable_user}')"> <img
												src="resources/images/enable.png" width="24">
											</a>
										</c:when>
										<c:otherwise>
											<a href="<c:url value="disable/?user_id=${u.userId}"/>"
												onclick="return confirm('${dialog_disable_user}')"> <img
												src="resources/images/disable.png" width="24">
											</a>
										</c:otherwise>
									</c:choose></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<br>
				<mycustomtags:tablepaging action="adminpageusers/userpaging"
					buttonName="userPageNumber" pages_count="${pages_count}"
					page_number="${page_number}" pages_size="${pages_size}" />
				<br> <a name="add_new_user"></a>
				<h2>
					<spring:message code="label.adminpage.addNewUser" />
				</h2>
				<form:form method="post" commandName="dataObject" action="addUser"
					accept-charset="UTF-8">
					<table>
						<tr>
							<td><label for="loginInput"> <spring:message
										code="label.adminpage.users.login" /></label></td>
							<td><form:input path="login" id="loginInput" maxlength="50" /></td>
							<td><form:errors path="login" cssClass="error" /></td>
						</tr>
						<tr>
							<td><label for="passwordTextInput"> <spring:message
										code="label.adminpage.users.password" /></label></td>
							<td><form:input path="passwordText" id="passwordTextInput"
									maxlength="50" /></td>
							<td><form:errors path="passwordText" cssClass="error" /></td>
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