<%@ include file="tagsused.jsp" %>

<html>
<head>
<%@ include file="header.jsp" %>

<title><spring:message code="label.adminpage.title" /></title>
</head>

<body>
<h1 align = "center"><spring:message code="label.adminpage.header" /></h1>
	
	<%@ include file="adminsidebar.jsp" %>
	
	<div id="content">
	<div class="tabs-content">
	<div class="content" id="clients">  
  	<h1><spring:message code="label.adminpage.clients" /></h1>
  	<div class="success">
  		<c:out value="${message_client_added}"/>
  	</div>
  	<div class="error">
  		<c:out value="${message_client_not_added}"/>
  	</div>
  	<mycustomtags:tablepaging
  		action="adminpageclients/clientpaging" 
  		buttonName="clientPageNumber"
  		pages_count="${pages_count}"
  		page_number="${page_number}"
  		pages_size="${pages_size}" />
  	<br>
  	<table data-toggle="table" 
		data-classes="table table-hover table-condensed" 
    	data-striped="true"    	    	
		border="1" style="width:900px" align="center">
	<thead>
	<tr><th align="center"></th>
	<th align="center">
		<spring:message code="label.adminpage.clients.name" />
	</th>
	<th align="center">
		<spring:message code="label.adminpage.clients.login" />
	</th>
	<th align="center">
		<spring:message code="label.adminpage.clients.actions" />
	</th></tr>
	</thead>
	<tbody>
  	<c:forEach var="c" items="${clients_short}" varStatus="loopStatus">    	
    <tr class="${loopStatus.index % 2 == 0 ? 'even' : 'odd'}">
    	<td><c:out value="${loopStatus.index + 1 + page_number * pages_size}"/></td>
    	<td>${c.clientName}</td> 
    	<td>${c.clientUser.login}</td>
    	<td>
    		<a href="<c:url value="/updateclient/?client-id=${c.clientId}"/>">
  				<img src="resources/images/edit.png" width="24"></a>			
    	</td>
    </tr>
  	</c:forEach>
  	</tbody>
  	</table>
  	
  	<a name="add_new_client"></a>
  	<h2><spring:message code="label.adminpage.addNewClient" /></h2>
  	<form:form method="post" commandName="client" action="addClient" accept-charset="UTF-8">
  	<table>
  		<tr>
  			<td><label for="clientNameInput">
  				<spring:message code="label.adminpage.clients.name" />
  			</label></td>
  			<td><form:input path="clientName" id="clientNameInput" maxlength="50"/></td>
  			<td><form:errors path="clientName" cssClass="error" /></td>  			
  		</tr>
  		<tr>
  			<td><label><spring:message code="label.adminpage.addNewClient.user" /></label></td>
  			<td><select name="userId">
  				<option value="0">
  					<spring:message code="label.adminpage.addNewClient.selectUser" />
  				</option>
  				<c:forEach var="u" items="${users}">
  					<c:choose>
  					<c:when test="${selected_client_user_id == u.userId}">
  						<option selected value="${u.userId}">
  							<c:out value="${u.login}"/>
  						</option>
  					</c:when>
  					<c:otherwise>
  						<option value="${u.userId}">
  							<c:out value="${u.login}"/>
  						</option>
  					</c:otherwise>
  					</c:choose>  					
  				</c:forEach>
  			</select></td>
  			<td>
  			<div class="error">
  				<c:out value="${message_client_user_id}"/>
  			</div>
  			</td>
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