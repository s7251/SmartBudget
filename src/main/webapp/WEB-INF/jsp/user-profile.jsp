<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../tiles-template/taglib.jsp"%>

<div class="panel panel-default">
	<!-- Default panel contents -->
		<div class="panel-heading"><h1 class="panel-title">User Profile </h1></div>
	<div class="panel-body">			
			<c:if test="${(user.name != 'admin' )}"><a href="<spring:url value="/user-profile/removeprofile/${user.id}.html" />" class="btn btn-danger" type="button">Remove account</a></c:if> 		
	</div>
	
<table class="table">
	<thead>
		<tr bgcolor="#efefef">
			<th>Details:</th>
		</tr>
	</thead>
	<tbody>		
		<tr>
			<td><b>username:</b> ${user.name}</td>
		</tr>
		<tr>
			<td><b>id:</b> ${user.id}</td>
		</tr>
		<tr>
			<td><b>email:</b> ${user.email}</td>
		</tr>
	</tbody>
</table>

</div>