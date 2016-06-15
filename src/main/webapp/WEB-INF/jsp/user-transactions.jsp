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




<!-- Modal -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">Add transaction</h4>
      </div>
      <div class="modal-body">
      <form:form commandName="user" cssClass="form-horizontal">
      
      <div class="form-group" style="text-align: left; width: 800px; ">
      
			<label for="name" class="col-sm-2 control-label">Type:</label>
		<div class="col-sm-10">
      
      <label class="radio-inline">
  <input type="radio" name="inlineRadioOptions" id="inlineRadio1" value="option1"> influence
</label>
<label class="radio-inline">
  <input type="radio" name="inlineRadioOptions" id="inlineRadio2" value="option2"> expense
</label>    
      </div>		
	</div>	
        <div class="form-group" style="text-align: center; width: 800px; margin: 0 auto;">
			<label for="name" class="col-sm-2 control-label">Name:</label>
		<div class="col-sm-10">
			<form:input path="" cssClass="form-control" style="width: 350px" placeholder="Please type name of transaction" autofocus="autofocus"/>
		</div>
		
	</div>
	
	<div class="form-group" style="text-align: center; width: 800px; margin: 0 auto;">
			<label for="name" class="col-sm-2 control-label">Amount:</label>
		<div class="col-sm-10">
			<form:input path="" cssClass="form-control" style="width: 350px" placeholder="Please type amount" autofocus="autofocus"/>
		</div>
		
	</div>
	<div class="form-group" style="text-align: center; width: 800px; margin: 0 auto;">
			<label for="name" class="col-sm-2 control-label">Date:</label>
		<div class="col-sm-10">
			<form:input path="" cssClass="form-control" style="width: 350px" placeholder="Please type date" autofocus="autofocus"/>
		</div>
		
	</div>
	<div class="form-group" style="text-align: center; width: 800px; margin: 0 auto;">
			<label for="name" class="col-sm-2 control-label">Subcategory:</label>
		<div class="col-sm-10">
				<select class="form-control" style="text-align: left; width: 350px;">
				<c:forEach items="${user.accounts}" var="account">
		<c:forEach items="${account.transactions}" var="transaction">	
	  <option>${transaction.subcategory.name}</option>	 	  
	</c:forEach>
		</c:forEach>  
</select>
		</div>		
	</div>
	
	<div class="form-group" style="text-align: center; width: 800px; margin: 0 auto;">
			<label for="name" class="col-sm-2 control-label">Account:</label>
		<div class="col-sm-10">
				<select class="form-control" style="text-align: left; width: 350px;">
				<c:forEach items="${user.accounts}" var="account">
		<c:forEach items="${account.transactions}" var="transaction">	
	  <option>${account.name}</option>	 	  
	</c:forEach>
		</c:forEach>  
</select>
		</div>		
	</div>      
      </form:form>    
       
      
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        <button type="button" class="btn btn-success">Add</button>
      </div>
    </div>
  </div>
</div>