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
   
  	<div class="content" id="user_auths">
  	<h1><spring:message code="label.adminpage.userAuthorizations" /></h1>
  	<div class="success">
  		<c:out value="${message_user_authorization_added}"/>
  	</div>
  	<div class="error">
  		<c:out value="${message_user_authorization_not_added}"/>
  	</div>  	
  	<table data-toggle="table" 
		data-classes="table table-hover table-condensed" 
    	data-striped="true"    	   	
		border="1" style="width:900px" align="center">
	<thead>
	<tr><th align="center"></th>
	<th align="center">
		<spring:message code="label.adminpage.userAuthorizations.login" /></th>
	<th align="center">
		<spring:message code="label.adminpage.userAuthorizations.role" /></th>
	<th align="center" data-sortable="false">
		<spring:message code="label.adminpage.userAuthorizations.actions" />
	</th></tr>
	</thead>
	<tbody>
  	<c:forEach var="ua" items="${user_authorizations_short}" varStatus="loopStatus">    	
    <tr class="${loopStatus.index % 2 == 0 ? 'even' : 'odd'}">
    	<td>
    		<c:choose>
    		<c:when test="${ua.user.enabled == 0}">
  				<div class="disabled">
					<c:out value="${loopStatus.index + 1 + page_number * pages_size}"/>
				</div>
  			</c:when>
  			<c:otherwise>
  				<c:out value="${loopStatus.index + 1 + page_number * pages_size}"/>
  			</c:otherwise>
  			</c:choose>
    	</td>
    	<td>
    		<c:choose>
    		<c:when test="${ua.user.enabled == 0}">
  				<div class="disabled">
					${ua.user.login}
				</div>
  			</c:when>
  			<c:otherwise>
  				${ua.user.login}
  			</c:otherwise>
  			</c:choose>
    	</td> 
    	<td>
    		<c:choose>
    		<c:when test="${ua.user.enabled == 0}">
    		<div class="disabled">
    		<c:choose>
  				<c:when test="${locale == 'ru'}">
  					<c:out value="${ua.role.descRu}"/>
  				</c:when>
  				<c:otherwise>
  					<c:out value="${ua.role.descEn}"/>
  				</c:otherwise>  				
  			</c:choose>
  			</div>
  			</c:when>
  			<c:otherwise>
  				<c:choose>
  				<c:when test="${locale == 'ru'}">
  					<c:out value="${ua.role.descRu}"/>
  				</c:when>
  				<c:otherwise>
  					<c:out value="${ua.role.descEn}"/>
  				</c:otherwise>  				
  			</c:choose>
  			</c:otherwise>
  			</c:choose>  			   		
    	</td>
    	<td>
    		<a href="<c:url 
    			value="updateuserauthorization/?user-authorization-id=${ua.userAuthorizationId}"/>">
  				<img src="resources/images/edit.png" width="24"></a>
    	</td>
    </tr>
  	</c:forEach>
  	</tbody>
  	</table>
  	<br>
  	<mycustomtags:tablepaging
  		action="adminpageuserauthorizations/userauthorizationpaging" 
  		buttonName="userAuthorizationPageNumber"
  		pages_count="${pages_count}"
  		page_number="${page_number}"
  		pages_size="${pages_size}" />  	
  	<br>
  	<a name="add_new_user_authorization"></a>
  	<h2><spring:message code="label.adminpage.addNewUserAuthorization" /></h2>
  	<form:form method="post" commandName="userAuthorizationAddDTO" 
  		action="addUserAuthorization" accept-charset="UTF-8">
  	<table>
  		<tr>
  		<td><label>
  			<spring:message code="label.adminpage.addNewUserAuthorization.userFrom" />
  			</label></td>  		
  		<td><form:select path="userId">
  			<option value="0">
  				<spring:message code="label.adminpage.addNewUserAuthorization.selectUser" />
  			</option>
  			<c:forEach var="uau" items="${user_authorizations_short_users}">
  				<c:choose>
  					<c:when test="${userAuthorizationAddDTO.userId == uau.userId}">
  						<option selected value="${uau.userId}">
  							<c:out value="${uau.login}"/>
  						</option>
  					</c:when>
  					<c:otherwise>
  						<option value="${uau.userId}">
  							<c:out value="${uau.login}"/>
  						</option>
  					</c:otherwise>
  				</c:choose>  				
  			</c:forEach>
  		</form:select></td>
  		<td><form:errors path="userId" cssClass="error" /></td>
  		</tr>
  		<tr>  		
  		<td><label>
  			<spring:message code="label.adminpage.userAuthorizations.role" />
  		</label></td>
  		<td><form:select path="role">
  			<option value="">
  				<spring:message code="label.adminpage.addNewUserAuthorization.selectRole" />  				
  			</option>
  			<c:forEach var="r" items="${user_roles}">
  			<c:choose>
  			<c:when test="${userAuthorizationAddDTO.role == r.role}">
  				<option selected value="${r.role}">
  					<c:choose>
  					<c:when test="${locale == 'ru'}">
  						<c:out value="${r.descRu}"/>
  					</c:when>
  					<c:otherwise>
  						<c:out value="${r.descEn}"/>
	  				</c:otherwise>  					
  					</c:choose>    				
  				</option>
  			</c:when>
  			<c:otherwise>
  				<option value="${r.role}">
  					<c:choose>
  					<c:when test="${locale == 'ru'}">
  						<c:out value="${r.descRu}"/>
  					</c:when>
  					<c:otherwise>
  						<c:out value="${r.descEn}"/>
	  				</c:otherwise>  					
  					</c:choose>    				
  				</option>
  			</c:otherwise>
  			</c:choose>
  			</c:forEach>
  		</form:select></td>
  		<td><form:errors path="role" cssClass="error" /></td>
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