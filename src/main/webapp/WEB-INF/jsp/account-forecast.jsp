<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../tiles-template/taglib.jsp"%>

<div class="panel panel-default">
	<!-- Default panel contents -->
		<div class="panel-heading"><h1 class="panel-title">Account forecast for future months </h1></div>
		<div class="panel-body" >
<a href="<spring:url value="/user-accounts.html" />" class="btn btn-primary" type="button" >Back</a>

	</div>
<table class="table">
	<thead>
		<tr bgcolor="#efefef">
			<th><h1 class="panel-title">Graph: <span class="pull-right">Account name</span></h1> </th>
			
			
		</tr>
	</thead>
	<tbody>		
		<tr>
<td> ${summaryAccountsByMonths}</td>
		</tr>
	</tbody>
</table>

</div>

   

  
   
  
   