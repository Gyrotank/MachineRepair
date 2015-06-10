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

			<div class="content" id="user_auths">
				<h1>
					<spring:message code="label.adminpage.userAuthorizations" />
				</h1>
				<div class="success">
					<c:out value="${message_added}" />
				</div>
				<div class="error">
					<c:out value="${message_not_added}" />
				</div>
				<table data-toggle="table"
					data-classes="table table-hover table-condensed"
					data-striped="true" border="1" style="width: 900px; float: center">
					<thead>
						<tr>
							<th align="center"></th>
							<th align="center"><spring:message
									code="label.adminpage.userAuthorizations.login" /></th>
							<th align="center"><spring:message
									code="label.adminpage.userAuthorizations.role" /></th>
							<th align="center" data-sortable="false"><spring:message
									code="label.adminpage.userAuthorizations.actions" /></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="ua" items="${user_authorizations_short}"
							varStatus="loopStatus">
							<tr class="${loopStatus.index % 2 == 0 ? 'even' : 'odd'}">
								<td><c:choose>
										<c:when test="${ua.user.enabled == 0}">
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
										<c:when test="${ua.user.enabled == 0}">
											<div class="disabled">${ua.user.login}</div>
										</c:when>
										<c:otherwise>
  				${ua.user.login}
  			</c:otherwise>
									</c:choose></td>
								<td><c:choose>
										<c:when test="${ua.user.enabled == 0}">
											<div class="disabled">
												<c:choose>
													<c:when test="${locale == 'ru'}">
														<c:out value="${ua.role.descRu}" />
													</c:when>
													<c:otherwise>
														<c:out value="${ua.role.descEn}" />
													</c:otherwise>
												</c:choose>
											</div>
										</c:when>
										<c:otherwise>
											<c:choose>
												<c:when test="${locale == 'ru'}">
													<c:out value="${ua.role.descRu}" />
												</c:when>
												<c:otherwise>
													<c:out value="${ua.role.descEn}" />
												</c:otherwise>
											</c:choose>
										</c:otherwise>
									</c:choose></td>
								<td><a
									href="<c:url 
    			value="updateuserauthorization/?user-authorization-id=${ua.userAuthorizationId}"/>">
										<img src="resources/images/edit.png" width="24">
								</a></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<br>
				<mycustomtags:tablepaging
					action="adminpageuserauthorizations/userauthorizationpaging"
					buttonName="userAuthorizationPageNumber"
					pages_count="${pages_count}" page_number="${page_number}"
					pages_size="${pages_size}" />
			</div>
		</div>
	</div>
</body>
</html>