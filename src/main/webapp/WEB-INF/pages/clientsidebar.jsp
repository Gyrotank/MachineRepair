<div id="sidebar">
	<c:choose>
		<c:when test="${locale == 'en'}">
			<a href="?locale=en"><img src="resources/images/usa.png"
				width="40"></a>
		</c:when>
		<c:otherwise>
			<a href="?locale=en"><img src="resources/images/usa.png"
				width="32"></a>
		</c:otherwise>
	</c:choose>
	<c:choose>
		<c:when test="${locale == 'ru'}">
			<a href="?locale=ru"><img src="resources/images/rus.png"
				width="40"></a>
		</c:when>
		<c:otherwise>
			<a href="?locale=ru"><img src="resources/images/rus.png"
				width="32"></a>
		</c:otherwise>
	</c:choose>
	<hr class="style-seven">
	<p>
		<a href="<c:url value="/clienthome"/>"> <spring:message
				code="label.clientpage.sidebar.index" /></a>
	<hr class="style-seven">
	<dl class="tabs vertical">
		<dd class="active">
			<a href="<c:url value="/clientpage"/>"> <spring:message
					code="label.clientpage.sidebar.dashboard" /></a>
		</dd>
		<dd>
			<a href="<c:url value="/clientpagecreateorders"/>"> <spring:message
					code="label.clientpage.sidebar.createOrders" /></a>
		</dd>
		<dd>
			<a href="<c:url value="/clientpagecurrentorders"/>"> <spring:message
					code="label.clientpage.sidebar.currentOrders" /></a>
		</dd>
		<dd>
			<a href="<c:url value="/clientpagepastorders"/>"> <spring:message
					code="label.clientpage.sidebar.pastOrders" /></a>
		</dd>
	</dl>
	<hr class="style-seven">
	<p>
		<a href="<c:url value="/logout"/>"><spring:message
				code="label.clientpage.sidebar.logout" /></a>
	</p>
</div>