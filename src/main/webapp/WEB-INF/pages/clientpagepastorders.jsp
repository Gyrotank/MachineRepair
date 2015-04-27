<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="springForm"%>
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
<script src="<c:url value="/resources/js/bootstrap-table-en-US.js" />"></script>
<c:choose>
  	<c:when test="${locale == 'ru'}">
  		<script src="<c:url value="/resources/js/bootstrap-table-ru-RU.js" />"></script>
  	</c:when>
  	<c:otherwise>
  		<script src="<c:url value="/resources/js/bootstrap-table-en-US.js" />"></script>
  	</c:otherwise>
</c:choose>

<title><spring:message code="label.clientpage.title" /></title>
</head>

<body>
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
		<p><a href="<c:url value="/index"/>"><spring:message code="label.clientpage.sidebar.index" /></a>
		<hr class="style-seven">
			<dl class="tabs vertical">
  			<dd class="active"><a href="<c:url value="/clientpage"/>">
  				<spring:message code="label.clientpage.sidebar.dashboard" /></a></dd>
  			<dd><a href="<c:url value="/clientpagecreateorders"/>">
  				<spring:message code="label.clientpage.sidebar.createOrders" /></a></dd>
  			<dd><a href="<c:url value="/clientpagecurrentorders"/>">
  				<spring:message code="label.clientpage.sidebar.currentOrders" /></a></dd>
  			<dd><a href="<c:url value="/clientpagepastorders"/>">
  				<spring:message code="label.clientpage.sidebar.pastOrders" /></a></dd>  			
		</dl>
		<hr class="style-seven">
		<p><a href="<c:url value="/logout"/>"><spring:message code="label.clientpage.sidebar.logout" /></a></p>
	</div>
	
	<div id="content">
	<br>
	<h1><spring:message code="label.clientpage.header.welcome" />  ${clientname}!</h1>
	<br>
	<div class="tabs-content">	
  	<div class="content" id="past_orders">
  	<h2><spring:message code="label.clientpage.yourPastOrders" /></h2>
	<c:choose>
	<c:when test="${empty my_past_orders}">
		<br>		
		<span style="font-size: 18px; line-height: 18px;">
		<c:out value="${message_past_orders}"/>
		<br><br><br>
		</span>		
	</c:when>
	<c:otherwise>
		<br>
		<form method="post" action="clientpagepastorders/pastorderspaging" accept-charset="UTF-8">
  		<table>
  		<tr>
  			<td style="width:5%" align="center"><spring:message code="label.clientpage.records" /></td>  			
  			<td style="width:10%" align="center">
  				<input name="pastOrdersPageStart" maxlength="5" size="8"
  				value="${past_orders_paging_first + 1}"/></td>  			
  			<td style="width:5%" align="center"><spring:message code="label.clientpage.to" /></td>  			
  			<td style="width:10%" align="center">
  				<input name="pastOrdersPageEnd" maxlength="5" size="8"
  				value="${past_orders_paging_last + 1}"/></td>
  			<td style="width:20%" align="center">
  			<spring:message code="label.clientpage.of" />
  			${past_orders_count}
  			<spring:message code="label.clientpage.total" /></td>
  			<td style="width:50%" align="left"><button><spring:message code="label.clientpage.buttonGo" /></button></td>  			
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
	<th align="center" data-sortable="true"><spring:message code="label.clientpage.yourOrders.repairType" /></th>
	<th align="center" data-sortable="true"><spring:message code="label.clientpage.yourOrders.sn" /></th>
	<th align="center" data-sortable="true"><spring:message code="label.clientpage.yourOrders.machineName" /></th>
	<th align="center" data-sortable="true"><spring:message code="label.clientpage.yourOrders.date" /></th>
	<th align="center" data-sortable="true"><spring:message code="label.clientpage.yourOrders.status" /></th></tr>
	</thead>
	<tbody>
  	<c:forEach var="po" items="${my_past_orders}" varStatus="loopStatus">
  	<tr class="${loopStatus.index % 2 == 0 ? 'even' : 'odd'}">
    	<td><c:out value="${loopStatus.index + 1}"/></td>    	 
    	<td>
    		<c:choose>
    			<c:when test="${locale == 'ru'}">
    				${po.repairType.repairTypeNameRu}
    			</c:when>
    			<c:otherwise>
    				${po.repairType.repairTypeName}
    			</c:otherwise>
    		</c:choose>
    	</td>
    	<td>${po.machine.machineSerialNumber}</td>
    	<td>${po.machine.machineServiceable.machineServiceableName}</td>
    	<td>${po.start}</td>
    	<td>
    		<c:choose>
  				<c:when test="${po.status == 'pending' && locale == 'ru'}">
  					<c:out value="в обработке"/>
  				</c:when>
  				<c:when test="${po.status == 'started' && locale == 'ru'}">
  					<c:out value="выполняется"/>
	  			</c:when>
  				<c:when test="${po.status == 'ready' && locale == 'ru'}">
  					<c:out value="готов"/>
  				</c:when>
  				<c:when test="${po.status == 'finished' && locale == 'ru'}">
  					<c:out value="завершен"/>
  				</c:when>
  				</c:choose>
    			<c:choose>
  				<c:when test="${po.status == 'pending' && locale == 'en'}">
	  				<c:out value="pending"/>
  				</c:when>
  				<c:when test="${po.status == 'started' && locale == 'en'}">
  					<c:out value="started"/>
  				</c:when>
	  			<c:when test="${po.status == 'ready' && locale == 'en'}">
  					<c:out value="ready"/>
  				</c:when>
  				<c:when test="${po.status == 'finished' && locale == 'en'}">
  					<c:out value="finished"/>
  				</c:when>
  			</c:choose>
    	</td>    	
    </tr>
  	</c:forEach>
  	</tbody>
  	</table>
  	</c:otherwise>
  	</c:choose>
  	</div>
  	</div>
  	</div>
</body>
</html>