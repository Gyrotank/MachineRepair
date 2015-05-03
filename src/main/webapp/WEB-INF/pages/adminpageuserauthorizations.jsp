<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
			 "http://www.w3.org/TR/html4/loose.dtd">
			 
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<link href="<c:url value="/resources/css/general.css"/>" rel="stylesheet" type="text/css" />
<link href="<c:url value="/resources/css/ufd-base.css"/>" rel="stylesheet" type="text/css" />
<link href="<c:url value="/resources/css/plain.css"/>" rel="stylesheet" type="text/css" />
<link href="<c:url value="/resources/css/bootstrap.min.css"/>" rel="stylesheet" />
<link href="<c:url value="/resources/css/bootstrap-table.css"/>" rel="stylesheet" />

<script src="<c:url value="/resources/js/jquery-1.11.2.min.js" />"></script>
<script src="<c:url value="/resources/js/jquery-ui.js" />"></script>
<script src="<c:url value="/resources/js/jquery.ui.ufd.js" />"></script>
<script src="<c:url value="/resources/js/bootstrap.min.js" />"></script>
<script src="<c:url value="/resources/js/bootstrap-table.js" />"></script>
<c:choose>
  	<c:when test="${locale == 'ru'}">
  		<script src="<c:url value="/resources/js/bootstrap-table-ru-RU.js" />"></script>
  	</c:when>
  	<c:otherwise>
  		<script src="<c:url value="/resources/js/bootstrap-table-en-US.js" />"></script>
  	</c:otherwise>
</c:choose>

<title><spring:message code="label.adminpage.title" /></title>
</head>
<body>
<h1 align = "center"><spring:message code="label.adminpage.header" /></h1>
	
	<div id="sidebar">
		<c:choose>
  			<c:when test="${locale == 'en'}">
  				<a href="?locale=en"><img src="resources/images/usa.png" width="40"></a>
  			</c:when>
  			<c:otherwise>
  				<a href="?locale=en"><img src="resources/images/usa.png" width="32"></a>
  			</c:otherwise>
		</c:choose>
		<c:choose>
  			<c:when test="${locale == 'ru'}">
  				<a href="?locale=ru"><img src="resources/images/rus.png" width="40"></a>
  			</c:when>
  			<c:otherwise>
  				<a href="?locale=ru"><img src="resources/images/rus.png" width="32"></a>
  			</c:otherwise>
		</c:choose>
		<hr class="style-seven">
		<p><a href="<c:url value="/index"/>">
			<spring:message code="label.adminpage.sidebar.index" /></a></p>
		<hr class="style-seven">		
		<p><a href="<c:url value="/managerpage"/>">
			<spring:message code="label.adminpage.sidebar.managerpage" /></a></p>
		<hr class="style-seven">
	<dl class="tabs vertical">
  		<dd class="active"><a href="<c:url value="/adminpage"/>">
			<spring:message code="label.adminpage.sidebar.dashboard" /></a></dd>
  		<dd><a href="<c:url value="/adminpagemachines"/>">
			<spring:message code="label.adminpage.sidebar.machines" /></a></dd>
  		<dd><a href="<c:url value="/adminpagemachinesserviceable"/>">
			<spring:message code="label.adminpage.sidebar.serviceableMachines" /></a></dd>
  		<dd><a href="<c:url value="/adminpagerepairtypes"/>">
			<spring:message code="label.adminpage.sidebar.repairTypes" /></a></dd>
  		<dd><a href="<c:url value="/adminpageusers"/>">
			<spring:message code="label.adminpage.sidebar.users" /></a></dd>
  		<dd><a href="<c:url value="/adminpageuserauthorizations"/>">
			<spring:message code="label.adminpage.sidebar.userAuths" /></a></dd>
  		<dd><a href="<c:url value="/adminpageclients"/>">
			<spring:message code="label.adminpage.sidebar.clients" /></a></dd>
  		<dd><a href="<c:url value="/adminpageorders"/>">
			<spring:message code="label.adminpage.sidebar.orders" /></a></dd>
	</dl>
		<hr class="style-seven">
		<p><a href="<c:url value="/logout"/>">
			<spring:message code="label.adminpage.sidebar.logout" /></a></p>
	</div>
	
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
  	<form method="post" action="adminpageuserauthorizations/userauthorizationpaging"
  		accept-charset="UTF-8">
  		<table>
  		<tr>
  			<td style="width:5%" align="center">
  				<spring:message code="label.adminpage.records" /></td>  			
  			<td style="width:10%" align="center"><input name="userAuthorizationPageStart" 
  				maxlength="5" size="8"
  				value="${user_authorizations_paging_first + 1}"/></td>  			
  			<td style="width:5%" align="center">
  				<spring:message code="label.adminpage.to" /></td>  			
  			<td style="width:10%" align="center"><input name="userAuthorizationPageEnd" 
  				maxlength="5" size="8"
  				value="${user_authorizations_paging_last + 1}"/></td>
  			<td style="width:20%" align="center">
  				<spring:message code="label.adminpage.of" />
  				${user_authorizations_count}
  				<spring:message code="label.adminpage.total" /></td>
  			<td style="width:50%" align="left"><button>
  				<spring:message code="label.adminpage.buttonGo" /></button></td>  			
  		</tr>  		
  		</table>
  	</form>
  	<br>
  	<table data-toggle="table" 
		data-classes="table table-hover table-condensed" 
    	data-striped="true"
    	data-pagination="true"    	
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
					<c:out value="${loopStatus.index + 1}"/>
				</div>
  			</c:when>
  			<c:otherwise>
  				<c:out value="${loopStatus.index + 1}"/>
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
<%-- 			<a href="<c:url value="delete/?user-authorization-id=${ua.userAuthorizationId}"/>"  --%>
<%--     			onclick="return confirm('${dialog_delete_user_authorization}')"> --%>
<!--   				<img src="resources/images/delete.png" width="24"></a> -->
    	</td>
    </tr>
  	</c:forEach>
  	</tbody>
  	</table>
  	
  	<a name="add_new_user_authorization"></a>
  	<h2><spring:message code="label.adminpage.addNewUserAuthorization" /></h2>
  	<form:form method="post" commandName="userAuthorization" 
  		action="addUserAuthorization" accept-charset="UTF-8">
  	<table>
  		<tr>
  		<td><label>
  			<spring:message code="label.adminpage.addNewUserAuthorization.userFrom" />
  			${user_authorizations_paging_first + 1} 
  			<spring:message code="label.adminpage.addNewUserAuthorization.userTo" />
  			${user_authorizations_paging_last + 1})&nbsp</label></td>  		
  		<td><select name="userId">
  			<option value="0">
  				<spring:message code="label.adminpage.addNewUserAuthorization.selectUser" />
  			</option>
  			<c:forEach var="uau" items="${user_authorizations_short_users}">
  				<c:choose>
  					<c:when test="${selected_user_authorization_user_id == uau.userId}">
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
  		</select></td>
  		<td>
  			<div class="error">
  				<c:out value="${message_user_authorization_user_id}"/>
  			</div>
  		</td>
  		</tr>
  		<tr>  		
  		<td><label>
  		<spring:message code="label.adminpage.userAuthorizations.role" />
  		</label></td>
  		<td><select name="role">
  			<option value="">
  				<label>
  					<spring:message code="label.adminpage.addNewUserAuthorization.selectRole" />
  				</label>
  			</option>
  			<c:forEach var="r" items="${user_roles}">
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
  			</c:forEach>
  		</select></td>
  		<td>
  			<div class="error">
  				<c:out value="${message_user_authorization_role}"/>
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