<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../tiles-template/taglib.jsp"%>

<div class="panel panel-default">
	<!-- Default panel contents -->
	<div class="panel-heading">List of Users</div>
	<div class="panel-body">
		<p>Below you can see list of users.</p>
	</div>	
	<!-- Table -->
	<table class="table table-striped">
		<tr>
			<td style="text-align: center;"><b>Name</b></td>
			<td style="text-align: center;"><b>Edit</b></td>
			<td style="text-align: center;"><b>Remove</b></td>									
		</tr>
		<c:forEach items="${users}" var="user">
			<tr>
				<td style="text-align: center; vertical-align: middle;"><a href="<spring:url value="/users/${user.id}.html" />"><c:out value="${user.name}" /></a></td>
				<td style="text-align: center; vertical-align: middle;"><a href="<spring:url value="" />" class="btn btn-warning" type="button">Edit</a></td>					
				<td style="text-align: center; vertical-align: middle;"><a href="<spring:url value="" />" class="btn btn-danger" type="button">Remove</a></td>	
			</tr>
		</c:forEach>
	</table>
	</div>
	