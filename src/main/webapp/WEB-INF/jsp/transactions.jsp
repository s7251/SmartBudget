<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../tiles-template/taglib.jsp"%>
 
 <head>
   <style type="text/css">         
   tr.expensecolor { background: #ffe6e6; }
   tr.influencecolor { background: #b3ffb3; }

    </style> 
 </head>

<div class="panel panel-default">
	<!-- Default panel contents -->
	<div class="panel-heading">Transactions</div>
	<div class="panel-body">
		<p>Below you can see list of transactions.</p>
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
		</tr>
		<c:forEach items="${transactions}" var="transaction">
			<tr class="${transaction.type == 'expense' ? 'expensecolor' : 'influencecolor'}">
				<td>${transaction.type}</td>
				<td>${transaction.name}</td>
				<td>${transaction.amount}</td>
				<td>${transaction.date}</td>
				<td>${transaction.subcategory.name}</td>
				<td>${transaction.account.name}</td>
			</tr>
		</c:forEach>
	</table>

</div>