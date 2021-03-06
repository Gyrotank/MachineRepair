<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<%@ attribute name="requestParamName" required="true" type="java.lang.String" description="Name of request parameter used in links" %>
<%@ attribute name="requestParamValue" required="true" type="java.lang.Long" description="Value of request parameter used in links" %>

<div id="sidebar">
	<c:choose>
		<c:when test="${locale == 'en'}">
			<a href="?locale=en&amp;${requestParamName}=${requestParamValue}"> <img
				src="${pageContext.servletContext.contextPath}/resources/images/usa.png"
				width="40"></a>
		</c:when>
		<c:otherwise>
			<a href="?locale=en&amp;${requestParamName}=${requestParamValue}"> <img
				src="${pageContext.servletContext.contextPath}/resources/images/usa.png"
				width="32"></a>
		</c:otherwise>
	</c:choose>
	<c:choose>
		<c:when test="${locale == 'ru'}">
			<a href="?locale=ru&amp;${requestParamName}=${requestParamValue}"> <img
				src="${pageContext.servletContext.contextPath}/resources/images/rus.png"
				width="40"></a>
		</c:when>
		<c:otherwise>
			<a href="?locale=ru&amp;${requestParamName}=${requestParamValue}"> <img
				src="${pageContext.servletContext.contextPath}/resources/images/rus.png"
				width="32"></a>
		</c:otherwise>
	</c:choose>
	<hr class="style-seven">
	<p>
		<a href="<c:url value="/index"/>"> <spring:message
				code="label.adminpage.sidebar.index" /></a>
	</p>
	<hr class="style-seven">
	<p>
		<a href="<c:url value="/manageradminpage"/>"> <spring:message
				code="label.adminpage.sidebar.managerpage" /></a>
	</p>
	<hr class="style-seven">
	<dl class="tabs vertical">
		<dd class="active">
			<a href="<c:url value="/adminpage"/>"> <spring:message
					code="label.adminpage.sidebar.dashboard" /></a>
		</dd>
		<dd>
			<a href="<c:url value="/adminpagemachines"/>"> <spring:message
					code="label.adminpage.sidebar.machines" /></a>
		</dd>
		<dd>
			<a href="<c:url value="/adminpagemachinesserviceable"/>"> <spring:message
					code="label.adminpage.sidebar.serviceableMachines" /></a>
		</dd>
		<dd>
			<a href="<c:url value="/adminpagerepairtypes"/>"> <spring:message
					code="label.adminpage.sidebar.repairTypes" /></a>
		</dd>
		<dd>
			<a href="<c:url value="/adminpageusers"/>"> <spring:message
					code="label.adminpage.sidebar.users" /></a>
		</dd>
		<dd>
			<a href="<c:url value="/adminpageuserauthorizations"/>"> <spring:message
					code="label.adminpage.sidebar.userAuths" /></a>
		</dd>
		<dd>
			<a href="<c:url value="/adminpageclients"/>"> <spring:message
					code="label.adminpage.sidebar.clients" /></a>
		</dd>
		<dd>
			<a href="<c:url value="/adminpageorders"/>"> <spring:message
					code="label.adminpage.sidebar.orders" /></a>
		</dd>
	</dl>
	<hr class="style-seven">
	<p>
		<a href="<c:url value="/logout"/>"> <spring:message
				code="label.adminpage.sidebar.logout" /></a>
	</p>
</div>