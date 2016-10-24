<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../tiles-template/taglib.jsp"%>

 
 <head>
   <style type="text/css">         
   tr.expensecolor { background: #ffe6e6; }
   tr.incomecolor { background: #b3ffb3; }
   tr.alignpluscolor { background: white; }
   tr.alignminuscolor { background: white; }
   tr.transferpluscolor { background: white; }
   tr.transferminuscolor { background: white; }
    </style> 
 </head>

<nav>
  <ul class="pager">
    <li class="previous"><a href="<spring:url value="/user-transactions/${prevMonthNav}.html" />"><span aria-hidden="true">&larr;</span> Previous Month</a></li>     
    <li class="next"><a href="<spring:url value="/user-transactions/${nextMonthNav}.html"/>">Next Month <span aria-hidden="true">&rarr;</span></a></li>
  </ul>
</nav>
<c:if test="${not empty subcategoriesForecast}">
<div class="alert alert-info alert-dismissible" role="alert">
  <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
  <strong>Forecast for actual month (expenses by subcategory): </strong> <br><c:forEach items="${subcategoriesForecast}" var="subforecast">${subforecast.key}:<strong> ${subforecast.value}</strong><br></c:forEach>
</div></c:if>
<!-- <div class="alert alert-warning alert-dismissible" role="alert">
  <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
  <strong>Attention! probably unexpected expenses:</strong> .
</div> -->

<div class="panel panel-default">
	<!-- Default panel contents -->
	<div class="panel-heading"><h1 class="panel-title">Transactions <span class="pull-right">${date}<c:if test="${empty date}">${actualMonth}</c:if></span></h1></div>
	

	<div class="panel-body">
		<a href="<spring:url value="" />" class="btn btn-primary" type="button" data-toggle="modal" data-target="#addTransactionModal">Add transaction</a>
		
	</div>
	

	<!-- Table -->
	<table class="table">
		<tr>
			<td><b>Type</b></td>
			<td><b>Name</b></td>
			<td><b>Amount</b></td>
			<td><b>Date</b></td>
			<td><b>Subcategory</b></td>
			<td><b>Account</b></td>
			<td></td>
			<td></td>
		</tr>
		<c:forEach items="${userTransactions}" var="userTransactions">
		

<tr>
			<c:choose>
			
    <c:when test="${userTransactions.type == 'expense'}">
      <tr class="expensecolor">
    </c:when>
    <c:when test="${userTransactions.type == 'income'}">
       <tr class="incomecolor">
    </c:when>   
       <c:when test="${userTransactions.type == 'alignment +'}">
       <tr class="alignpluscolor">
    </c:when>   
           <c:when test="${userTransactions.type == 'alignment -'}">
       <tr class="alignminuscolor">
    </c:when>      
     <c:when test="${userTransactions.type == 'internal transfer +'}">
       <tr class="transferpluscolor">
    </c:when>   
           <c:when test="${userTransactions.type == 'internal transfer -'}">
       <tr class="transferminuscolor">
    </c:when> 
</c:choose>			
				<td style="vertical-align: middle;">${userTransactions.type}</td>
				<td style="vertical-align: middle;">${userTransactions.name}</td>
				<td style="vertical-align: middle;">${userTransactions.amount}</td>
				<td style="vertical-align: middle;">${userTransactions.date}</td>
				<td style="vertical-align: middle;">${userTransactions.subcategory.name}</td>
				<td style="vertical-align: middle;">${userTransactions.account.name}</td>
				<td>
				<div class="btn-group">
  <button type="button" class="btn btn-warning dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
    Change <span class="caret"></span>
  </button>
  <ul class="dropdown-menu">

   <c:if test="${(userTransactions.type == 'expense' || userTransactions.type == 'income')}">  
     <li><a href="<spring:url value="" />" data-toggle="modal" data-target="#editTransaction${userTransactions.id}">Edit</a></li> 
       </c:if>      
 
    <li role="separator" class="divider"></li>
    <li><a href="<spring:url value="/user-transactions/removetransaction/${userTransactions.id}/${date}${actualMonth}.html" />" >Remove</a></li>    
 
  </ul>
</div>
				</td>
			
			<td width="0%">
			
			<form:form mehod="post" modelAttribute="TransactionForm" action="/editTransaction/${date}.html" cssClass="form-horizontal">
			<form:hidden path="id" value="${userTransactions.id}" />
	<!-- Modal -->
	<div class="modal fade" id="editTransaction${userTransactions.id}" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">Edit transaction</h4>
				</div>
				<div class="modal-body">
					<div class="form-group" style="text-align: left; width: 800px;">

						<label for="name" class="col-sm-2 control-label">Type:</label>
						<div class="col-sm-10">
						<form:radiobutton path="type" value="income" /> income
					    <form:radiobutton path="type" value="expense" /> expense	    
					   
						</div>
					</div>
					<div class="form-group"
						style="text-align: center; width: 800px; margin: 0 auto;">
						<label for="name" class="col-sm-2 control-label">Name:</label>
						<div class="col-sm-10">
							<form:input path="name" cssClass="form-control" style="width: 350px" value="${userTransactions.name}" placeholder="${userTransactions.name}" autofocus="autofocus" />
						</div>
					</div>

					<div class="form-group"
						style="text-align: center; width: 800px; margin: 0 auto;">
						<label for="amount" class="col-sm-2 control-label">Amount:</label>
						<div class="col-sm-10">
							<form:input path="amount" value="${userTransactions.amount}" cssClass="form-control" style="width: 350px" placeholder="${userTransactions.amount}" autofocus="autofocus" />
						</div>
					</div>
					<div class="form-group"	style="text-align: center; width: 800px; margin: 0 auto;">
						<label for="date" class="col-sm-2 control-label">Date:</label>
						<div class="col-sm-10">						
						<form:input path="date" cssClass="form-control" style="width: 350px" placeholder="DD.MM.RRRR" autofocus="autofocus" data-options="formatter:myformatter,parser:myparser"/> 
						</div>						
						</div>									
							<div class="form-group"	style="text-align: center; width: 800px; margin: 0 auto;">
						<label for="category" class="col-sm-2 control-label">Subcategory:</label>
						<div class="col-sm-10">
						<form:select class="form-control" path="subcategoryId"  style="text-align: left; width: 350px;">
									<form:option value="${userTransactions.subcategory.id}">${userTransactions.subcategory.name}</form:option>
									<form:options items="${subcategoriesMap}" />							
							</form:select>
							
						
						</div>
					</div>

					<div class="form-group"	style="text-align: center; width: 800px; margin: 0 auto;">
						<label for="account" class="col-sm-2 control-label">Account:</label>
						<div class="col-sm-10">
							<form:select class="form-control" path="accountId"  style="text-align: left; width: 350px;">		
							<form:option value="${userTransactions.account.id}">${userTransactions.account.name}</form:option>														
										<form:options items="${accountsMap}" />								
							</form:select>
						</div>
					</div>

				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
					<input type="submit" class="btn btn-success" value="Edit" />
				</div>
			</div>
		</div>
	</div>
</form:form>
			</td>
			</tr>
			
		</c:forEach>
		<tr>
		<td></td>
		<td style="text-align: right;"><b>month summary:</b></td>
		<td><b>${monthSummary}</b></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		</tr>
	</table>

</div>


<form:form commandName="TransactionForm" cssClass="form-horizontal">
	<!-- Modal -->
	<div class="modal fade" id="addTransactionModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">Add transaction</h4>
				</div>
				<div class="modal-body">
					<div class="form-group" style="text-align: left; width: 800px;">

						<label for="name" class="col-sm-2 control-label">Type:</label>
						<div class="col-sm-10">
						<form:radiobutton path="type" value="income" /> income
					    <form:radiobutton path="type" value="expense" /> expense
						</div>
					</div>
					<div class="form-group"
						style="text-align: center; width: 800px; margin: 0 auto;">
						<label for="name" class="col-sm-2 control-label">Name:</label>
						<div class="col-sm-10">
							<form:input path="name" cssClass="form-control" style="width: 350px" placeholder="Please type name of transaction"	autofocus="autofocus" />
						</div>
					</div>

					<div class="form-group"
						style="text-align: center; width: 800px; margin: 0 auto;">
						<label for="amount" class="col-sm-2 control-label">Amount:</label>
						<div class="col-sm-10">
							<form:input path="amount" cssClass="form-control" style="width: 350px" placeholder="Please type amount" autofocus="autofocus" />
						</div>
					</div>
					<div class="form-group"	style="text-align: center; width: 800px; margin: 0 auto;">
						<label for="date" class="col-sm-2 control-label">Date:</label>
						<div class="col-sm-10">						
						<form:input path="date" cssClass="form-control" style="width: 350px" placeholder="DD.MM.RRRR" autofocus="autofocus" data-options="formatter:myformatter,parser:myparser"/> 
						</div>						
						</div>									
							<div class="form-group"	style="text-align: center; width: 800px; margin: 0 auto;">
						<label for="category" class="col-sm-2 control-label">Subcategory:</label>
						<div class="col-sm-10">
						<form:select class="form-control" path="subcategoryId"  style="text-align: left; width: 350px;">
									<form:options items="${subcategoriesMap}" />							
							</form:select>
							
						
						</div>
					</div>

					<div class="form-group"	style="text-align: center; width: 800px; margin: 0 auto;">
						<label for="account" class="col-sm-2 control-label">Account:</label>
						<div class="col-sm-10">
							<form:select class="form-control" path="accountId"  style="text-align: left; width: 350px;">																
										<form:options items="${accountsMap}" />								
							</form:select>
						</div>
					</div>

				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
					<input type="submit" class="btn btn-success" value="Add" />
				</div>
			</div>
		</div>
	</div>
</form:form>
