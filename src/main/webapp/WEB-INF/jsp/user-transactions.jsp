<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../tiles-template/taglib.jsp"%>
 
 <head>
   <style type="text/css">         
   tr.expensecolor { background: #ffe6e6; }
   tr.influencecolor { background: #b3ffb3; }

    </style> 
 </head>
<nav>
  <ul class="pager">
    <li class="previous"><a href="<spring:url value="" />"><span aria-hidden="true">&larr;</span> Previous Month</a></li>    
    <li class="next"><a href="<spring:url value="" />">Next Month <span aria-hidden="true">&rarr;</span></a></li>
  </ul>
</nav>
<div class="panel panel-default">
	<!-- Default panel contents -->
	<div class="panel-heading">Transactions</div>

	<div class="panel-body">
		<a href="<spring:url value="" />" class="btn btn-primary" type="button">Add transaction</a>
		<a href="<spring:url value="" />" class="btn btn-primary" type="button">Import bank transactions (CSV)</a>		
	</div>
	

	<!-- Table -->
	<table class="table">
		<tr>
			<td><b>Type</b></td>
			<td><b>Name</b></td>
			<td><b>Amount</b></td>
			<td><b>Date</b></td>
			<td><b>SubCategory</b></td>
			<td><b>Account</b></td>
			<td></td>
		</tr>
		<c:forEach items="${user.accounts}" var="account">
		<c:forEach items="${account.transactions}" var="transaction">
			<tr class="${transaction.type == 'expense' ? 'expensecolor' : 'influencecolor'}">
				<td style="vertical-align: middle;">${transaction.type}</td>
				<td style="vertical-align: middle;">${transaction.name}</td>
				<td style="vertical-align: middle;">${transaction.amount}</td>
				<td style="vertical-align: middle;">${transaction.date}</td>
				<td style="vertical-align: middle;">${transaction.subcategory.name}</td>
				<td style="vertical-align: middle;">${transaction.account.name}</td>
				<td>
				<div class="btn-group">
  <button type="button" class="btn btn-warning dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
    Change <span class="caret"></span>
  </button>
  <ul class="dropdown-menu">
    <li><a href="">Edit</a></li>
    <li role="separator" class="divider"></li>
    <li><a href="">Remove</a></li>
  </ul>
</div>
				</td>
			</tr>
			</c:forEach>
		</c:forEach>
	</table>

</div>