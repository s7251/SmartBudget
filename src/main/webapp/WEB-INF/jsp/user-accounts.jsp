<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../tiles-template/taglib.jsp"%>
 
 <div class="panel panel-default">
	<!-- Default panel contents -->
	<div class="panel-heading"><h1 class="panel-title">Accounts </h1></div>
	<div class="panel-body">			
			<a href="<spring:url value="" />" class="btn btn-primary" type="button" data-toggle="modal" data-target="#addAccountModal">Add account</a>
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
				<td style="text-align: center; vertical-align: middle;">${summary.value.name}</td>
				<td style="text-align: center; vertical-align: middle;">${summary.value.summaryOfAccount}</td>
				<td style="text-align: center; vertical-align: middle;"><a href="<spring:url value="" />" class="btn btn-primary" type="button" data-toggle="modal" data-target="#alignBalance${summary.value.id}">Align Balance</a></td>
				<td style="text-align: center; vertical-align: middle;"><a href="<spring:url value="" />" class="btn btn-warning" type="button" data-toggle="modal" data-target="#editAccountModal${summary.value.id}">Rename</a></td>					
				<td style="text-align: center; vertical-align: middle;"><a href="<spring:url value="/user-accounts/removeaccount/${summary.key}.html" />" class="btn btn-danger" type="button">Remove</a></td>		
			
			
			<td width="0%">
			
			<form:form mehod="post" modelAttribute="AlignBalanceForm" action="/alignBalance.html" cssClass="form-horizontal">
			<form:hidden path="accountId" value="${summary.value.id}" />
	<!-- Modal -->
	<div class="modal fade" id="alignBalance${summary.value.id}" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">Alignment of Balance (${summary.value.name})</h4>
				</div>
				<div class="modal-body">
					<div class="form-group" style="text-align: left; width: 800px;">

						<label for="name" class="col-sm-2 control-label">Type:</label>
						<div class="col-sm-10">
						<form:radiobutton path="type" value="alignment -" /> alignment -
					    <form:radiobutton path="type" value="alignment +" /> alignment +	    
					   
						</div>
					</div>
					<div class="form-group"
						style="text-align: center; width: 800px; margin: 0 auto;">
						<label for="name" class="col-sm-2 control-label">Name:</label>
						<div class="col-sm-10">
							<form:input path="name" cssClass="form-control" style="width: 350px" placeholder="please type caouse of alignment" autofocus="autofocus" />
						</div>
					</div>

					<div class="form-group"
						style="text-align: center; width: 800px; margin: 0 auto;">
						<label for="amount" class="col-sm-2 control-label">Amount:</label>
						<div class="col-sm-10">
							<form:input path="amount" cssClass="form-control" style="width: 350px" placeholder="please type amount" autofocus="autofocus" />
						</div>
					</div>
					<div class="form-group"	style="text-align: center; width: 800px; margin: 0 auto;">
						<label for="date" class="col-sm-2 control-label">Date:</label>
						<div class="col-sm-10">						
						<form:input path="date" cssClass="form-control" style="width: 350px" placeholder="DD/MM/RRRR" autofocus="autofocus" data-options="formatter:myformatter,parser:myparser"/> 
						</div>						
						</div>														

				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
					<input type="submit" class="btn btn-success" value="Align Balance" />
				</div>
			</div>
		</div>
	</div>
</form:form>
			</td>
			
			
			</tr>		
			
			<form:form mehod="post" modelAttribute="account" action="/renameAccount.html" cssClass="form-horizontal">
			<form:hidden path="id" value="${summary.value.id}" />
	<!-- Modal -->
	<div class="modal fade" id="editAccountModal${summary.value.id}" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">Rename account</h4>
				</div>
				<div class="modal-body">
				
					<div class="form-group"
						style="text-align: center; width: 800px; margin: 0 auto;">
						<label for="name" class="col-sm-2 control-label">Name:</label>
						<div class="col-sm-10">
							<form:input path="name" cssClass="form-control" style="width: 350px" placeholder="${summary.value.name}" autofocus="autofocus" ></form:input>
						</div>
					</div>
					
					</div>
					<br><br>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
					<input type="submit" class="btn btn-success" value="Rename" />
				</div>
			</div>
		</div>
	</div>
</form:form>
			
						
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
 			<td style="text-align: center; vertical-align: middle;"><b>${summaryOfAllAccounts}</b></td> 
			<td style="text-align: center; vertical-align: middle;"><b></b></td>
			<td style="text-align: center; vertical-align: middle;"><b></b></td>
			<td style="text-align: center; vertical-align: middle;"><b></b></td>
			
		</tr>		
	</table>
</div>


<form:form mehod="post" modelAttribute="account" action="/addAccount.html" cssClass="form-horizontal">
	<!-- Modal -->
	<div class="modal fade" id="addAccountModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">Add account</h4>
				</div>
				<div class="modal-body">
				
					<div class="form-group"
						style="text-align: center; width: 800px; margin: 0 auto;">
						<label for="name" class="col-sm-2 control-label">Name:</label>
						<div class="col-sm-10">
							<form:input path="name" cssClass="form-control" style="width: 350px" placeholder="Please type name of account"	autofocus="autofocus" />
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

		
