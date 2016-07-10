<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../tiles-template/taglib.jsp"%>
 
 <div class="panel panel-default">
	<!-- Default panel contents -->
	<div class="panel-heading"><h1 class="panel-title">Accounts </h1></div>
	<div class="panel-body">
			<a href="<spring:url value="" />" class="btn btn-primary" type="button">Add Account</a>
			<a href="<spring:url value="" />" class="btn btn-primary" type="button">Internal Transfer</a>
	</div>
	<!-- Table -->
	<table class="table table-striped">
		<tr>
			<td style="text-align: center; vertical-align: middle;"><b>Name</b></td>
			<td style="text-align: center; vertical-align: middle;"><b>Value</b></td>
			<td style="text-align: center; vertical-align: middle;"><b>Align Balance</b></td>
			<td style="text-align: center; vertical-align: middle;"><b>Rename</b></td>			
			<td style="text-align: center; vertical-align: middle;"><b>Remove</b></td>
			
		</tr>		
		<c:forEach items="${summaryOfAccounts}" var="summary">		
			<tr>
				<td style="text-align: center; vertical-align: middle;">${summary.key}</td>
				<td style="text-align: center; vertical-align: middle;">${summary.value}</td>
				<td style="text-align: center; vertical-align: middle;"><a href="<spring:url value="" />" class="btn btn-primary" type="button">Align Balance</a></td>
				<td style="text-align: center; vertical-align: middle;"><a href="<spring:url value="" />" class="btn btn-warning" type="button">Rename</a></td>					
				<td style="text-align: center; vertical-align: middle;"><a href="<spring:url value="" />" class="btn btn-danger" type="button">Remove</a></td>		
			</tr>					
		</c:forEach>	
			<tr>
			<td style="text-align: center; vertical-align: middle;"><b></b></td>
			<td style="text-align: center; vertical-align: middle;"><b>Summary</b></td>
			<td style="text-align: center; vertical-align: middle;"><b></b></td>
			<td style="text-align: center; vertical-align: middle;"><b></b></td>
			<td style="text-align: center; vertical-align: middle;"><b></b></td>
			
		</tr>
			<tr>
			<td style="text-align: center; vertical-align: middle;"><b></b></td>
			<td style="text-align: center; vertical-align: middle;"><b>3626.00</b></td>
			<td style="text-align: center; vertical-align: middle;"><b></b></td>
			<td style="text-align: center; vertical-align: middle;"><b></b></td>
			<td style="text-align: center; vertical-align: middle;"><b></b></td>
			
		</tr>		
	</table>
</div>
		
