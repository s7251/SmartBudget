<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../tiles-template/taglib.jsp"%>
 
 <head>
   <style type="text/css">         
   tr.categorycolor { background: #efefef; }
   tr.subcategorycolor { background: white; }

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
		<div class="panel-heading"><h1 class="panel-title">Budget Plan <span class="pull-right"># 06.2016</span></h1></div>
	<div class="panel-body">
	Below you can see budget plan of Categories.				
	</div>
	<!-- Table -->
	<table class="table">
		<tr>
			<td style="text-align: left; vertical-align: middle;"><b>Category Name</b></td>
			<td style="text-align: center; vertical-align: middle;"><b>Budget Value</b></td>
			<td style="text-align: center; vertical-align: middle;"><b>Spent</b></td>
			<td style="text-align: center; vertical-align: middle;"><b>Change Budget</b></td>
			
		</tr>
		<c:forEach items="${user.categories}" var="category">
			<tr class="${'categorycolor'}">
				<td style="text-align: left; vertical-align: middle;">${category.name}</td>
				<td></td>
				<td></td>
				<td></td>			
		</tr>
		<c:forEach items="${category.subcategories}" var="subcategory">		
			<tr class="${'subcategorycolor'}">
				<td style="text-align: left; vertical-align: middle;">- ${subcategory.name}</td>							
				<td style="text-align: center; vertical-align: middle;">
				<c:forEach items="${subcategory.subcategoryLimits}" var="subcategorylimits">
				${subcategorylimits.amount}
				</c:forEach>
				</td>					
				<td></td>	
				<td style="text-align: center; vertical-align: middle;"><a href="<spring:url value="" />" class="btn btn-primary" type="button">Change Budget</a>	</td>
				</tr>				
			</c:forEach>		
			
		</c:forEach>
	</table>
		
		</div>


