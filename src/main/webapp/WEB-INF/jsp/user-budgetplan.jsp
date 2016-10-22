<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../tiles-template/taglib.jsp"%>

 <head>
   <style type="text/css">         
   tr.categorycolor { background: #efefef; }
   tr.subcategorycolor { background: white; }
   tr.subcategorycolorinactive { background: #ffe6e6; }

    </style> 
 </head>
<nav>
  <ul class="pager">
    <li class="previous"><a href="<spring:url value="/user-budgetplan/${prevMonthNav}.html" />"><span aria-hidden="true">&larr;</span> Previous Month</a></li>     
    <li class="next"><a href="<spring:url value="/user-budgetplan/${nextMonthNav}.html"/>">Next Month <span aria-hidden="true">&rarr;</span></a></li>
  </ul>
</nav>
<div class="panel panel-default">
	<!-- Default panel contents -->
	<div class="panel-heading"><h1 class="panel-title">Transactions <span class="pull-right">${date}<c:if test="${empty date}">${actualMonth}</c:if></span></h1></div>
	<div class="panel-body">
	Below you can see budget plan of Categories.				
	</div>
	<!-- Table -->
	<table class="table">
		<tr>
			<td style="text-align: left; vertical-align: middle;"><b>Category Name</b></td>
			<td style="text-align: center; vertical-align: middle;"><b>Budget Value</b></td>
			<td style="text-align: center; vertical-align: middle;"><b>Spent</b></td>
				<td style="text-align: center; vertical-align: middle;"><b>Set Budget</b></td>
			<td style="text-align: center; vertical-align: middle;"><b>Change Budget</b></td>
		
			
		</tr>
		<c:forEach items="${user.categories}" var="category">
			<tr class="${'categorycolor'}">
				<td style="text-align: left; vertical-align: middle;">${category.name}</td>
				<td></td>
				<td></td>
				<td></td>	
				<td></td>			
		</tr>
		<c:forEach items="${category.subcategories}" var="subcategory">	
			<c:if test="${fn:length(subcategory.subcategoryLimits) == 0 }">	
			<tr class="${'subcategorycolorinactive'}">
			</c:if>
			<c:if test="${fn:length(subcategory.subcategoryLimits) > 0 }">	
			<tr class="${'subcategorycolor'}">
			</c:if>
				<td style="text-align: left; vertical-align: middle;">- ${subcategory.name}</td>							
				<td style=" text-align: center; vertical-align: middle;">
				<c:forEach items="${subcategory.subcategoryLimits}" var="subcategorylimits">
				${subcategorylimits.amount}	
								
				<form:form mehod="post" modelAttribute="subcategorylimit" action="/changeSubcategoryLimit.html" cssClass="form-horizontal">		
		<form:hidden path="subcategoryId" value="${subcategory.id}" />
		<form:hidden path="id" value="${subcategorylimits.id}" />
		<c:if test="${not empty date}"><form:hidden path="date" value="${date}" /></c:if>
 		<c:if test="${empty date}"><form:hidden path="date" value="${actualMonth}" /></c:if>
	<!-- Modal -->
	<div class="modal fade" id="changeSubcategoryLimitModal${subcategory.id}" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 style=" text-align: left;" class="modal-title" id="myModalLabel">Change subcategory budget  </h4>
				</div>
				<div class="modal-body">
				
					<div class="form-group"
						style="text-align: center; width: 800px; margin: 0 auto;">
						<label for="amount" class="col-sm-2 control-label">Value:</label>
						<div class="col-sm-10">
							<form:input path="amount" value="${subcategorylimits.amount}"cssClass="form-control" style="width: 350px" placeholder="Please set value"	autofocus="autofocus" />
						</div>
					</div>
					
					</div>
					
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
					<input type="submit" class="btn btn-success" value="Change" />
				</div>
			</div>
		</div>
	</div>
</form:form>		
				</c:forEach>
				</td>					
				<td style="text-align: center; vertical-align: middle;">
				<c:forEach items="${subcategory.subcategoryLimits}" var="subcategorylimits">
				${subcategorylimits.summaryOfSpentMoney}
				
			
				
				</c:forEach>
				</td>	
<td style="text-align: center; vertical-align: middle;">

<c:if test="${fn:length(subcategory.subcategoryLimits) == 0 }">
<a href="<spring:url value="/user-budgetplan/addsubcategorylimit/${subcategory.id}.html" />" class="btn btn-primary" type="button"  data-toggle="modal" data-target="#addSubcategoryLimitModal${subcategory.id}">Set Budget</a>	
	</c:if>		</td>	

				<td style="text-align: center; vertical-align: middle;">
					<c:if test="${fn:length(subcategory.subcategoryLimits) > 0 }">
				<a href="<spring:url value="/user-budgetplan/changesubcategorylimit/${subcategory.id}.html" />" class="btn btn-primary" type="button"  data-toggle="modal" data-target="#changeSubcategoryLimitModal${subcategory.id}">Change Budget</a>	
				</c:if>
				</td>
							
							
							
						
				
									<form:form mehod="post" modelAttribute="subcategorylimit" action="/addSubcategoryLimit.html" cssClass="form-horizontal">		
		<form:hidden path="subcategoryId" value="${subcategory.id}" />		
		<c:if test="${not empty date}"><form:hidden path="date" value="${date}" /></c:if>
 		<c:if test="${empty date}"><form:hidden path="date" value="${actualMonth}" /></c:if>
	<!-- Modal -->
	<div class="modal fade" id="addSubcategoryLimitModal${subcategory.id}" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">Set budget to subcategory </h4>
				</div>
				<div class="modal-body">
				
					<div class="form-group"
						style="text-align: center; width: 800px; margin: 0 auto;">
						<label for="amount" class="col-sm-2 control-label">Value:</label>
						<div class="col-sm-10">
							<form:input path="amount" cssClass="form-control" style="width: 350px" placeholder="Please set value"	autofocus="autofocus" />
						</div>
					</div>
					
					</div>
					<br><br>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
					<input type="submit" class="btn btn-success" value="Set" />
				</div>
			</div>
		</div>
	</div>
</form:form>

		
			</c:forEach>
			
			
			
		</c:forEach>
	</table>
		
		</div>
		
		
	
		
		




		
		
