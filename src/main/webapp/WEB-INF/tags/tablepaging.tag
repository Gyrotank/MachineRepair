<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ attribute name="action" required="true" type="java.lang.String" description="Name of form action" %>
<%@ attribute name="buttonName" required="true" type="java.lang.String" description="Name used by buttons" %>
<%@ attribute name="pages_count" required="true" type="java.lang.Long" description="Total page quantity" %>
<%@ attribute name="page_number" required="true" type="java.lang.Long" description="Ordinal number of page displayed" %>
<%@ attribute name="pages_size" required="true" type="java.lang.Long" description="Number of elements per page" %>

<form method="post" action="${action}"
	accept-charset="UTF-8">
	<table>
		<tr>
			<td align="left">
				<ul class="paging">
					<li>
						<button class="norm" onmouseover="this.className='over'"
							onmouseout="this.className='norm'" name="${buttonName}"
							value="${0}">
							<c:out value="<<"/>
  						</button>
					</li>
					<c:if test="${pages_count > 5}">
						<c:forEach var="i" begin="0" end="4">
							<c:if
								test="${page_number > 2 && (pages_count - page_number - 1) > 1}">
								<li>
									<button class="norm" onmouseover="this.className='over'"
										onmouseout="this.className='norm'" name="${buttonName}"
										value="${i + page_number - 2}">
										<c:out
											value="${1 + pages_size * (i + page_number - 2)} 
  								... ${pages_size * (i + page_number - 1)}" />
									</button>
								</li>
							</c:if>
							<c:if test="${page_number <= 2}">
								<li>
									<button class="norm" onmouseover="this.className='over'"
										onmouseout="this.className='norm'" name="${buttonName}"
										value="${i}">
										<c:out
											value="${1 + pages_size * i} ... ${pages_size * (i + 1)}" />
									</button>
								</li>
							</c:if>
							<c:if test="${(pages_count - page_number - 1) <= 1}">
								<li>
									<button class="norm" onmouseover="this.className='over'"
										onmouseout="this.className='norm'" name="${buttonName}"
										value="${i + pages_count - 5}">
										<c:out
											value="${1 + pages_size * (i + pages_count - 5)} 
  								... ${pages_size * (i + pages_count - 5 + 1)}" />
									</button>
								</li>
							</c:if>
						</c:forEach>
					</c:if>
					<c:if test="${pages_count <= 5}">
						<c:forEach var="i" begin="0" end="${pages_count - 1}">
							<li>
								<button class="norm" onmouseover="this.className='over'"
									onmouseout="this.className='norm'" name="${buttonName}"
									value="${i}">
									<c:out
										value="${1 + pages_size * i} ... ${pages_size * (i + 1)}" />
								</button>
							</li>
						</c:forEach>
					</c:if>
					<li>
						<button class="norm" onmouseover="this.className='over'"
							onmouseout="this.className='norm'" name="${buttonName}"
							value="${pages_count - 1}">
							<c:out value=">>" />
						</button>
					</li>
				</ul>
			</td>
		</tr>
	</table>
</form>