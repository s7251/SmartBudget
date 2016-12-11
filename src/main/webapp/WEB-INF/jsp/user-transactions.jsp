<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../tiles-template/taglib.jsp"%>

<!-- Include Bootstrap Datepicker -->
<script src="//cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.3.0/js/bootstrap-datepicker.min.js"></script>
<link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.3.0/css/datepicker.min.css" />
<link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.3.0/css/datepicker3.min.css" />

 <head>
   <style type="text/css">         
   tr.expensecolor { background: #ffe6e6; }
   tr.incomecolor { background: #b3ffb3; }
   tr.alignpluscolor { background: white; }
   tr.alignminuscolor { background: white; }
   tr.transferpluscolor { background: white; }
   tr.transferminuscolor { background: white; }
   #eventForm .form-control-feedback {
    top: 0;
    right: -15px;
}
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
  <button type="button" class="close" data-dismiss="alert" aria-label="Cancel"><span aria-hidden="true">&times;</span></button>
  <strong>Forecast for actual month (expenses by subcategory): </strong> <br><c:forEach items="${subcategoriesForecast}" var="subforecast">${subforecast.key}: <strong><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${subforecast.value}" type="currency" currencySymbol="zł" pattern=" #,##0.00 ¤; -#,##0.00 ¤"/> </strong><br></c:forEach>
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
		<a href="<spring:url value="" />" class="btn btn-primary" type="button" data-toggle="modal" data-target="#internalTransferModal">Internal Transfer</a>
	</div>
	
	<ul class="nav nav-tabs">
  <li role="presentation" class="active"><a href="#">Home</a></li>
  <li role="presentation"><a href="#">Profile</a></li>
  <li role="presentation"><a href="#">Messages</a></li>
</ul>
	<!-- Table -->
	<table class="table">

		<tr>
			<td><b>Type</b></td>
			<td><b>Memo</b></td>
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
				<td style="vertical-align: middle;">${userTransactions.memo}</td>
				<td style="vertical-align: middle;"><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${userTransactions.amount}" type="currency" currencySymbol="zł" pattern=" #,##0.00 ¤; -#,##0.00 ¤"/></td>
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
					<button type="button" class="close" data-dismiss="modal" aria-label="Cancel">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">Edit transaction</h4>
				</div>
				<div class="modal-body">
					<div class="form-group" style="text-align: left; width: 600px;">

						<label for="memo" class="col-sm-2 control-label">Type:</label>
						<div class="col-sm-10">
						<form:radiobutton path="type" value="income" /> income
					    <form:radiobutton path="type" value="expense" /> expense	    
					   
						</div>
					</div>
					<div class="form-group"
						style="text-align: center; width: 600px; margin: 0 auto;">
						<label for="memo" class="col-sm-2 control-label">Memo:</label>
						<div class="col-sm-10">
							<form:input path="memo" cssClass="form-control" style="width: 350px" value="${userTransactions.memo}" placeholder="${userTransactions.memo}" autofocus="autofocus" />
						</div>
					</div>

					<div class="form-group"
						style="text-align: center; width: 600px; margin: 0 auto;">
						<label for="amount" class="col-sm-2 control-label">Amount:</label>
						<div class="col-sm-10">
							<form:input path="amount" value="${userTransactions.amount}" cssClass="form-control" style="width: 350px" placeholder="${userTransactions.amount}" autofocus="autofocus" />
						</div>
					</div>
					<div class="form-group"	style="text-align: center; width: 600px; margin: 0 auto;">
						<label for="date" class="col-sm-2 control-label">Date:</label>
						 <div class="col-sm-8">
            <div class="input-group input-append date">
                <form:input type="text" class="form-control datePicker" placeholder="DD.MM.RRRR" path="date" />
                <span class="input-group-addon add-on" ><span class="glyphicon glyphicon-calendar"></span></span>
            </div>
        </div>					
						</div>									
							<div class="form-group"	style="text-align: center; width: 600px; margin: 0 auto;">
						<label for="category" class="col-sm-2 control-label">Subcategory:</label>
						<div class="col-sm-10">
						<form:select class="form-control" path="subcategoryId"  style="text-align: left; width: 350px;">
									<form:option value="${userTransactions.subcategory.id}">${userTransactions.subcategory.name}</form:option>
									<form:options items="${subcategoriesMap}" />							
							</form:select>						
						</div>
					</div>

					<div class="form-group"	style="text-align: center; width: 600px; margin: 0 auto;">
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
					<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
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
		<td><b><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${monthSummary}" type="currency" currencySymbol="zł" pattern=" #,##0.00 ¤; -#,##0.00 ¤"/> </b></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		</tr>
	</table>

</div>


<form:form modelAttribute="TransactionForm" action="/user-transactions/${date}.html" cssClass="form-horizontal" id="form">
	<!-- Modal -->
	<div class="modal fade" id="addTransactionModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Cancel">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">Add transaction</h4>
				</div>
				<div class="modal-body">
					<div class="form-group" style="text-align: left; width: 600px;">

						<label for="type" class="col-sm-2 control-label">Type:</label>
						<div class="col-sm-10">
						<form:radiobutton path="type" value="income" name="type" /> income
					    <form:radiobutton path="type" value="expense" name="type" /> expense
						</div>
					</div>
					<div class="form-group"
						style="text-align: center; width: 600px; margin: 0 auto;">
						<label for="memo" class="col-sm-2 control-label">Memo:</label>
						<div class="col-sm-10">
							<form:input path="memo" cssClass="form-control" style="width: 350px" placeholder="Please type memo of transaction"	autofocus="autofocus" />
						</div>
					</div>

					<div class="form-group"
						style="text-align: center; width: 600px; margin: 0 auto;">
						<label for="amount" class="col-sm-2 control-label">Amount:</label>
						<div class="col-sm-10">
							<form:input path="amount" cssClass="form-control" style="width: 350px" placeholder="Please type amount" autofocus="autofocus" />
						</div>
					</div>
					<div class="form-group"	style="text-align: center; width: 600px; margin: 0 auto;">
						<label for="date" class="col-sm-2 control-label">Date:</label>
						 <div class="col-sm-8">
            <div class="input-group input-append date">
                <form:input type="text" class="form-control datePicker" placeholder="DD.MM.RRRR" path="date" />
                <span class="input-group-addon add-on" ><span class="glyphicon glyphicon-calendar"></span></span>
            </div>
        </div>					
						</div>										
							<div class="form-group"	style="text-align: center; width: 600px; margin: 0 auto;">
						<label for="category" class="col-sm-2 control-label">Subcategory:</label>
						<div class="col-sm-10">
						<form:select class="form-control" path="subcategoryId"  style="text-align: left; width: 350px;">
									<form:options items="${subcategoriesMap}" />							
							</form:select>					
						</div>
					</div>
					<div class="form-group"	style="text-align: center; width:600px; margin: 0 auto;">
						<label for="account" class="col-sm-2 control-label">Account:</label>
						<div class="col-sm-10">
							<form:select class="form-control" path="accountId"  style="text-align: left; width: 350px;">																
										<form:options items="${accountsMap}" />								
							</form:select>
						</div>
					</div>

				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
					<input type="submit" class="btn btn-success" value="Add" />
				</div>
			</div>
		</div>
	</div>
</form:form>

<form:form mehod="post" modelAttribute="InternalTransferForm" action="/internalTransferFormTransactions.html" cssClass="form-horizontal" id="form">
	<!-- Modal -->
	<div class="modal fade" id="internalTransferModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Cancel">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">Internal Transfer</h4>
				</div>
				<div class="modal-body">
					
					<div class="form-group"
						style="text-align: center; width: 600px; margin: 0 auto;">
						<label for="memo" class="col-sm-2 control-label">Memo:</label>
						<div class="col-sm-10">
							<form:input path="memo" cssClass="form-control" style="width: 350px" placeholder="Please type memo of transaction"	autofocus="autofocus" />
						</div>
					</div>

					<div class="form-group"
						style="text-align: center; width: 600px; margin: 0 auto;">
						<label for="amount" class="col-sm-2 control-label">Amount:</label>
						<div class="col-sm-10">
							<form:input path="amount" cssClass="form-control" style="width: 350px" placeholder="Please type amount" autofocus="autofocus" />
						</div>
					</div>
					<div class="form-group"	style="text-align: center; width: 600px; margin: 0 auto;">
						<label for="date" class="col-sm-2 control-label">Date:</label>
						<div class="col-sm-8">						
						<div class="input-group input-append date">
                <form:input type="text" class="form-control datePicker" placeholder="DD.MM.RRRR" path="date" />
                <span class="input-group-addon add-on" ><span class="glyphicon glyphicon-calendar"></span></span>
            </div>
						</div>						
						</div>									
							<div class="form-group"	style="text-align: center; width: 600px; margin: 0 auto;">
						<label for="fromAccount" class="col-sm-2 control-label">From:</label>
						<div class="col-sm-10">
						<form:select class="form-control" path="fromAccountId"  style="text-align: left; width: 350px;">
									<form:options items="${accountsMap}" />							
							</form:select>		
						
						</div>
					</div>

					<div class="form-group"	style="text-align: center; width: 600px; margin: 0 auto;">
						<label for="toAccount" class="col-sm-2 control-label">To:</label>
						<div class="col-sm-10">
							<form:select class="form-control" path="toAccountId"  style="text-align: left; width: 350px;">																
										<form:options items="${accountsMap}" />								
							</form:select>
						</div>
					</div>

				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
					<input type="submit" class="btn btn-success" value="Add" />
				</div>
			</div>
		</div>
	</div>
</form:form>
	
<script>
$(document).ready(function() {	
	jQuery.validator.addMethod(
		    "money",
		    function(value, element) {
		        var isValidMoney = /^\d{0,6}(\.\d{0,2})?$/.test(value);
		        return this.optional(element) || isValidMoney;
		    },
		    "Please type amount in 0.00 format (max 999999.99)"
		);
	var startDate = new Date(${year},  ${month}-1, 1); 
	var lastDate = new Date(${year}, ${month}, 0); 
    $('.datePicker')
        .datepicker({
            format: 'dd.mm.yyyy',            
            startDate: startDate,
            endDate: lastDate
        })          
    $('form').each(function() {  
        $(this).validate({       
        	rules: {
        		type: {
					required : true,								
				},
				memo: {
					required : true,
					minlength : 4,				
				},
				amount: {
					required : true,
 					number: true,
					min: 0.01,					
					money: true
				},
				date: {
					required : true,					
				},				 
			},					
			highlight: function(element) {
				$(element).closest('.form-group').removeClass('has-success').addClass('has-error');
			},
			unhighlight: function(element) {
				$(element).closest('.form-group').removeClass('has-error').addClass('has-success');
			},		
			messages: {		
				amount: {
					number: "Please type amount in 0.00 format"				     
				 },			
				type: {
					 required: ""				     
				 },
				date: {
					 required: ""				     
				 }			       
			    }
        });
    });

});
</script>

		