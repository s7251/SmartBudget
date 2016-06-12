<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../tiles-template/taglib.jsp"%>

<div class="panel panel-default">
	<!-- Default panel contents -->
	<div class="panel-heading">User Profile</div>
	<div class="panel-body">			
			<a href="<spring:url value="" />" class="btn btn-danger" type="button">Remove account</a>		
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