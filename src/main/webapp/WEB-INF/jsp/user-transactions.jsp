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
	<div class="panel-heading"><h1 class="panel-title">Transactions <span class="pull-right"># 06.2016</span></h1></div>

	<div class="panel-body">
		<a href="<spring:url value="" />" class="btn btn-primary" type="button" data-toggle="modal" data-target="#myModal">Add transaction</a>
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
		<c:forEach items="${userTransactions}" var="userTransactions">
		
			<tr class="${userTransactions.type == 'expense' ? 'expensecolor' : 'influencecolor'}">
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
    <li><a href="">Edit</a></li>
    <li role="separator" class="divider"></li>
    <li><a href="">Remove</a></li>
  </ul>
</div>
				</td>
			</tr>
			
		</c:forEach>
	</table>

</div>


<form:form commandName="TransactionForm" cssClass="form-horizontal">
	<!-- Modal -->
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
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
						<form:radiobutton path="type" value="influence" /> influence
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
<%-- 						<fmt:formatDate pattern='dd/MM/yyyy' type='date' value='${transaction.date}' var="formattedDate"/> --%>
<%-- 						<form:input path="date" cssClass="form-control" style="width: 350px" placeholder="Please type date" autofocus="autofocus" id="date" name="date" data-format="dd/MM/yyyy" type="text" value="${formattedDate}"/> --%>
<%-- 			        	<form:input path="date" cssClass="form-control" style="width: 350px" placeholder="Please type date" autofocus="autofocus"  value = "<fmt:formatDate value="${transaction.date}" pattern="dd-MM-yyyy" />"/>  --%>
						<form:input path="date" cssClass="form-control" style="width: 350px" placeholder="DD/MM/RRRR" autofocus="autofocus" data-options="formatter:myformatter,parser:myparser"/> 
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
