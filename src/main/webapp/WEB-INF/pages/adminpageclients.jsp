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
	<div class="content" id="clients">  
  	<h1><spring:message code="label.adminpage.clients" /></h1>
  	<div class="success">
  		<c:out value="${message_client_added}"/>
  	</div>
  	<div class="error">
  		<c:out value="${message_client_not_added}"/>
  	</div>
  	<form method="post" action="adminpageclients/clientpaging" accept-charset="UTF-8">
  		<table>
  		<tr>
  			<td style="width:5%" align="center">
  				<spring:message code="label.adminpage.records" />
  			</td>  			
  			<td style="width:10%" align="center">
  				<input name="clientPageStart" maxlength="5" size="8"
  				value="${clients_paging_first + 1}"/></td>  			
  			<td style="width:5%" align="center">
  				<spring:message code="label.adminpage.to" />
  			</td>  			
  			<td style="width:10%" align="center">
  				<input name="clientPageEnd" maxlength="5" size="8"
  				value="${clients_paging_last + 1}"/></td>
  			<td style="width:20%" align="center">
  			<spring:message code="label.adminpage.of" />
  			${clients_count}
  			<spring:message code="label.adminpage.total" /></td>
  			<td style="width:50%" align="left"><button>
  				<spring:message code="label.adminpage.buttonGo" />
  			</button></td>  			
  		</tr>  		
  		</table>
  	</form>
  	<table data-toggle="table" 
		data-classes="table table-hover table-condensed" 
    	data-striped="true"
    	data-pagination="true"
    	data-search="true"
		border="1" style="width:900px" align="center">
	<thead>
	<tr><th align="center" data-sortable="true"></th>
	<th align="center" data-sortable="true">
		<spring:message code="label.adminpage.clients.name" />
	</th>
	<th align="center" data-sortable="true">
		<spring:message code="label.adminpage.clients.login" />
	</th></tr>
	</thead>
	<tbody>
  	<c:forEach var="c" items="${clients_short}" varStatus="loopStatus">    	
    <tr class="${loopStatus.index % 2 == 0 ? 'even' : 'odd'}">
    	<td><c:out value="${loopStatus.index + 1}"/></td>
    	<td>${c.clientName}</td> 
    	<td>${c.clientUser.login}</td>    	    	   	
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
<!-- !!!ONLY 100 USERS ARE FETCHED AS OF NOW!!! -->
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