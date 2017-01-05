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
    <li class="previous"><a href="<spring:url value="/user-transactions/${prevMonthNav}" />"><span aria-hidden="true">&larr;</span> Previous Month</a></li>     
    <li class="next"><a href="<spring:url value="/user-transactions/${nextMonthNav}"/>">Next Month <span aria-hidden="true">&rarr;</span></a></li>
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
	<c:if test="${firstView!=true}">  
  <li role="presentation"><a href="/user-transactions/${date}">All accounts</a></li>
  </c:if>
  <c:if test="${firstView==true}">  
  <li role="presentation"><a href="/user-transactions">All accounts</a></li>
  </c:if>
  <c:forEach items="${accountsMap}" var="accountsMap"> 
  <li role="presentation" ><a href="/user-transactions/${date}/${accountsMap.key}" > ${accountsMap.value} </a></li>

   </c:forEach>


</ul>
	<!-- Table -->
	<table class="table">

		<tr>
			<td><b>Type </b></td>
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
     <li><a href="<spring:url value="" />" data-toggle="modal" data-target="#splitTransaction${userTransactions.id}">Split</a></li>  
       </c:if>      
 
    <li role="separator" class="divider"></li>
    <li><a href="<spring:url value="/user-transactions/removetransaction/${userTransactions.id}/${date}${actualMonth}/${accountId}" />" >Remove</a></li>    
 
  </ul>
</div>
				</td>
			
			<td width="0%">
			
			<form:form mehod="post" modelAttribute="TransactionForm" action="/splitTransaction/${date}/${accountId}" cssClass="form-horizontal">
			<form:hidden path="id" value="${userTransactions.id}" />
			<form:hidden path="type" value="${userTransactions.type}" />
			<form:hidden path="date" value="${userTransactions.date}" />
			<form:hidden path="accountId" value="${userTransactions.account.id}" />
			
			
	<!-- Modal -->
	<div class="modal fade" id="splitTransaction${userTransactions.id}" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Cancel">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">Split transaction:</h4>
				</div>
				<div class="modal-body">
<span class="label label-warning"><b>Warning!</b> All data of this transaction will be lost!</span>
					<div class="alert alert-warning" role="alert">					
					<b>Type:</b> ${userTransactions.type}<br>
					<b>Memo:</b> ${userTransactions.memo}<br>
					<b>Amount:</b> <div id="amountToSplit">${userTransactions.amount}</div><br>
					<b>Date:</b> ${userTransactions.date}<br>
					<b>Subcategory:</b>${userTransactions.subcategory}<br>
					<b>Account:</b> ${userTransactions.account}<br>
					<b>Summary:</b> <div id="tempSummary">0.0</div><br>
					</div>
							<div class="form-group"
						style="text-align: center; width: 600px; margin: 0 auto;">				
						<div class="col-sm-10">
							<p class="navbar-text"><b>First split:</b></p>
						</div>
					</div>
							
					<div class="form-group"
						style="text-align: center; width: 600px; margin: 0 auto;">
						<label for="firstSplitMemo" class="col-sm-2 control-label">Memo:</label>
						<div class="col-sm-10">
							<form:input path="firstSplitMemo" cssClass="form-control" style="width: 350px" placeholder="Please type memo of first split" autofocus="autofocus" />
						</div>
					</div>
					
					<div class="form-group"
						style="text-align: center; width: 600px; margin: 0 auto;">
						<label for="firstSplitAmount" class="col-sm-2 control-label">Amount:</label>
						<div class="col-sm-10">						
							<form:input id="firstSplitAmount${userTransactions.id}" path="firstSplitAmount" value="${userTransactions.amount}" cssClass="form-control" style="width: 350px" placeholder="${userTransactions.amount}" autofocus="autofocus" />
						</div>
					</div>
					
								<div class="form-group"	style="text-align: center; width: 600px; margin: 0 auto;">
						<label for="category" class="col-sm-2 control-label">Subcategory:</label>
						<div class="col-sm-10">
						<form:select class="form-control" path="firstSplitSubcategoryId"  style="text-align: left; width: 350px;">								
									<form:options items="${subcategoriesMap}" />							
							</form:select>						
						</div>
					</div>
					
					<div class="form-group"
						style="text-align: center; width: 600px; margin: 0 auto;">					
						<div class="col-sm-10">
							<p class="navbar-text"><b>Secondary split:</b></p>
						</div>
					</div>
					
						<div class="form-group"
						style="text-align: center; width: 600px; margin: 0 auto;">
						<label for="secondarySplitMemo" class="col-sm-2 control-label">Memo:</label>
						<div class="col-sm-10">
							<form:input path="secondarySplitMemo" cssClass="form-control" style="width: 350px" placeholder="Please type memo of first split" autofocus="autofocus" />
						</div>
					</div>
					
					
										<div class="form-group"
						style="text-align: center; width: 600px; margin: 0 auto;">
						<label for="secondarySplitAmount" class="col-sm-2 control-label">Amount:</label>
						<div class="col-sm-10">
						
							<form:input id="secondarySplitAmount${userTransactions.id}" path="secondarySplitAmount" value="${userTransactions.amount}" cssClass="form-control" style="width: 350px" placeholder="${userTransactions.amount}" autofocus="autofocus" />
						</div>
					</div>
																
						<div class="form-group"	style="text-align: center; width: 600px; margin: 0 auto;">
						<label for="category" class="col-sm-2 control-label">Subcategory:</label>
						<div class="col-sm-10">
						<form:select class="form-control" path="secondarySplitSubcategoryId"  style="text-align: left; width: 350px;">									
									<form:options items="${subcategoriesMap}" />							
							</form:select>						
						</div>
					</div>

			
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
					<input id="split${userTransactions.id}" type="submit" class="split btn btn-success" value="Split" />
				</div>
			</div>
		</div>
		<script type="text/javascript">
		$(document).ready(function() {
		  $( "#split${userTransactions.id}" ).click(function() { 
		    	var splitedAmounts = parseFloat($('#splitTransaction${userTransactions.id} #firstSplitAmount${userTransactions.id}').val()) + parseFloat($('#splitTransaction${userTransactions.id} #secondarySplitAmount${userTransactions.id}').val());
		        var amountToSplit = parseFloat($('#splitTransaction${userTransactions.id} #amountToSplit').text());
		        var result1 = !(splitedAmounts <= amountToSplit);
		        
		        if(result1) {
		        	var sum = splitedAmounts - amountToSplit;
		        	alert("Kwota jest za duża o " + sum + " zł!");
		        }
		        
				var result2 = splitedAmounts < amountToSplit;
		        
		        if(result2) {
		        	var sum = (amountToSplit - splitedAmounts);
		        	alert("Kwota jest za mała o " + sum + " zł!");
		        }
		    });
		  
		  $('#firstSplitAmount${userTransactions.id}, #secondarySplitAmount${userTransactions.id}').change(function(){	  
			  var splitedAmounts = parseFloat($('#splitTransaction${userTransactions.id} #firstSplitAmount${userTransactions.id}').val()) + parseFloat($('#splitTransaction${userTransactions.id} #secondarySplitAmount${userTransactions.id}').val());
			  $('#splitTransaction${userTransactions.id} #tempSummary').text(splitedAmounts);
			  
		  });
		});
		</script>
	</div>
</form:form>
			
			<form:form mehod="post" modelAttribute="TransactionForm" action="/editTransaction/${date}/${accountId}" cssClass="form-horizontal">
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
					<h4 class="modal-title" id="myModalLabel">Edit transaction: </h4>
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
									<form:options items="${subcategoriesMap}" />							
							</form:select>						
						</div>
					</div>

					<div class="form-group"	style="text-align: center; width: 600px; margin: 0 auto;">
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
		<td style="text-align: right;"><b>month summary:</b>
		<br>
		<b> balance at the beginning of the month:</b>
		<br>
		<b>balance at the end of the month:</b>
		</td>
		<td><b><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${monthSummary}" type="currency" currencySymbol="zł" pattern=" #,##0.00 ¤; -#,##0.00 ¤"/> </b>
		<br>
		<b> <fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${balanceAtBeggining}" type="currency" currencySymbol="zł" pattern=" #,##0.00 ¤; -#,##0.00 ¤"/></b>
		<br>
		<b> <fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${balanceAtEnd}" type="currency" currencySymbol="zł" pattern=" #,##0.00 ¤; -#,##0.00 ¤"/></b>
		</td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		</tr>
	</table>

</div>


<form:form modelAttribute="TransactionForm" action="/user-transactions/${date}/${accountId}" cssClass="form-horizontal" id="form">
	<!-- Modal -->
	<div class="modal fade" id="addTransactionModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Cancel">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">Add transaction <c:if test="${not empty accountId}"> (${accountName})</c:if></h4>
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
				
				<c:if test="${empty accountId}">
 
					<div class="form-group"	style="text-align: center; width:600px; margin: 0 auto;">
						<label for="account" class="col-sm-2 control-label"> Account:</label>
						<div class="col-sm-10">
							<form:select class="form-control" path="accountId"  style="text-align: left; width: 350px;">																
										<form:options items="${accountsMap}"/>											
							</form:select>
						</div>
					</div>
   				</c:if>
   			<c:if test="${not empty accountId}">
  				 <form:hidden path="accountId" value="${accountId}" />
  				 
   				</c:if>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
					<input type="submit" class="btn btn-success" value="Add" />
				</div>
			</div>
		</div>
	</div>
	
</form:form>

<form:form mehod="post" modelAttribute="InternalTransferForm" action="/internalTransferFormTransactions" cssClass="form-horizontal" id="form">
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
	
	$('a[href="' + this.location.pathname + '"]').parents('li,ul').addClass('active');
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
				firstSplitMemo: {
					required : true,
					minlength : 4,				
				},
				secondarySplitMemo: {
					required : true,
					minlength : 4,				
				},
				amount: {
					required : true,
 					number: true,
					min: 0.01,					
					money: true
				},
				firstSplitAmount: {
					required : true,
 					number: true,
					min: 0.01,					
					money: true,
				},
				secondarySplitAmount: {
					required : true,
 					number: true,
					min: 0.01,					
					money: true,					
				},
				date: {
					required : true,					
				},
				fromAccountId: {
					required : true,					
				},
				toAccountId: {
					required : true,					
				},
				accountId: {
					required : true,					
				},
				subcategoryId: {
					required : true,					
				},	
				firstSplitSubcategoryId: {
					required : true,					
				},
				secondarySplitSubcategoryId: {
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
					number: "Please type amount in 0.00 format "				     
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
    
    $( "#add" ).click(function() {
    	var memo = $("#memo").val();
    	var data = {memo : memo, amount : 100};
    	
    	//Ajax sample
        $.ajax({
            headers: { 
                'Accept': 'application/json',
                'Content-Type': 'application/json' 
            },
            'type': 'POST',
            'url': '/user-subtransaction/add.html',
            'data': JSON.stringify(data),
            'dataType': 'json',
            'success' : function() {
        	    alert( "Data Saved");
        	} 
        });
    });
    
    $( "#get" ).click(function() {
    	
    	//Ajax sample
        $.ajax({
            headers: { 
                'Accept': 'application/json',
                'Content-Type': 'application/json' 
            },
            'type': 'GET',
            'url': '/user-subtransaction/getData',       
            'dataType': 'json',
            'success' : function() {
        	    alert( "Recieved data");
        	} 
        });
    });
   
});
</script>



		