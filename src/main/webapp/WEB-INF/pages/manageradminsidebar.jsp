<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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
		<p><a href="<c:url value="/adminhome"/>">
			<spring:message code="label.managerpage.sidebar.index" /></a></p>
		<hr class="style-seven">
			<p><a href="<c:url value="/adminpage"/>">
			<spring:message code="label.managerpage.sidebar.adminpage" /></a></p>
		<hr class="style-seven">				
			<dl class="tabs vertical">
			<dd class="active"><a href="<c:url value="/manageradminpage"/>">
  				<spring:message code="label.managerpage.sidebar.dashboard" /></a></dd>
  			<dd><a href="<c:url value="/manageradminpagependingorders"/>">
  				<spring:message code="label.managerpage.sidebar.pending" /></a></dd>
  			<dd><a href="<c:url value="/manageradminpageactiveorders"/>">
  				<spring:message code="label.managerpage.sidebar.active" /></a></dd>
		<hr class="style-seven">
		<p><a href="<c:url value="/logout"/>">
			<spring:message code="label.managerpage.sidebar.logout" /></a></p>
	</div>