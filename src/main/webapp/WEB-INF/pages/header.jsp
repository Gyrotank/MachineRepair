<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<link href="<c:url value="/resources/css/general.css"/>"
	rel="stylesheet" type="text/css" />
<link href="<c:url value="/resources/css/ufd-base.css"/>"
	rel="stylesheet" type="text/css" />
<link href="<c:url value="/resources/css/plain.css"/>" rel="stylesheet"
	type="text/css" />
<link href="<c:url value="/resources/css/bootstrap.min.css"/>"
	rel="stylesheet" />
<link href="<c:url value="/resources/css/bootstrap-table.css"/>"
	rel="stylesheet" />

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