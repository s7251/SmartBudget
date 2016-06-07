<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../tiles-template/taglib.jsp"%>

<table class="table">
	<thead>
		<tr>
			<th>Users</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${users}" var="user">
			<tr>
				<td><a href="<spring:url value="/users/${user.id}.html" />">
						<c:out value="${user.name}" />
				</a></td>
			</tr>
		</c:forEach>
	</tbody>
</table>