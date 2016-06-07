<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../tiles-template/taglib.jsp"%>

<table class="table">
	<thead>
		<tr>
			<th>User details</th>
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