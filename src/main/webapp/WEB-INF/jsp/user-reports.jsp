<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../tiles-template/taglib.jsp"%>  

    <link rel='stylesheet prefetch' href='http://netdna.bootstrapcdn.com/twitter-bootstrap/3.3.6/css/bootstrap-combined.min.css'>
<link rel='stylesheet prefetch' href='https://s3-us-west-2.amazonaws.com/s.cdpn.io/2708/bootstrap-datetimepicker.min.css'>

<div class="panel panel-default">
	<!-- Default panel contents -->
		<div class="panel-heading"><h1 class="panel-title">User Reports </h1></div>
	<div class="panel-body">			
				<div class="btn-group">
  <button type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
   Generate report <span class="caret"></span>
  </button>
  <ul class="dropdown-menu">
    <li><a href="<spring:url value="" />" data-toggle="modal" data-target="#incomeReport">Incomes by subcategories</a></li>
    <li><a href="<spring:url value="" />" data-toggle="modal" data-target="#expenseReport">Expenses by subcategories</a></li>
    <li><a href="">Transactions in time</a></li> 
  </ul>
</div>	</div>	
</div>
  


<form:form mehod="post" modelAttribute="ReportForm" action="/report-incomes-by-categories.html" cssClass="form-horizontal">
	<!-- Modal -->	
	<div class="modal fade" id="incomeReport" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">Incomes by subcategories</h4>
				</div>
				<div class="modal-body">
				
					<div class="form-group"
						style="text-align: center; width: 800px; margin: 0 auto;">
						<label for="name" class="col-sm-2 control-label">Date:</label>
						<div class="col-sm-10">
							<form:input path="date" cssClass="form-control" style="width: 350px" placeholder="MM.YYYY"	autofocus="autofocus" />
						</div>
					</div>			
					
						
					</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
					<input type="submit" class="btn btn-success" value="Generate Report" />
				</div>
			</div>
		</div>
	</div>
</form:form>

<form:form mehod="post" modelAttribute="ReportForm" action="/report-expenses-by-categories.html" cssClass="form-horizontal">
	<!-- Modal -->	
	<div class="modal fade" id="expenseReport" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">Expenses by categories</h4>
				</div>
				<div class="modal-body">
				
					<div class="form-group"
						style="text-align: center; width: 800px; margin: 0 auto;">
						<label for="name" class="col-sm-2 control-label">Date:</label>
						<div class="col-sm-10">
							<form:input path="date" cssClass="form-control" style="width: 350px" placeholder="MM.YYYY"	autofocus="autofocus" />
						</div>
					</div>			
					
						
					</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
					<input type="submit" class="btn btn-success" value="Generate Report" />
				</div>
			</div>
		</div>
	</div>
</form:form>


