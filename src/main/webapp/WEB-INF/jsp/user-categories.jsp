<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../tiles-template/taglib.jsp"%>
 
 <head>
   <style type="text/css">         
   tr.categorycolor { background: #efefef; }
   tr.subcategorycolor { background: white; }

    </style> 
 </head>

<div class="panel panel-default">
	<!-- Default panel contents -->
	<div class="panel-heading">Categories</div>
	<div class="panel-body">		
			<a href="<spring:url value="" />" class="btn btn-primary" type="button">Add category</a>			
	</div>
	<!-- Table -->
	<table class="table">
		<tr>
			<td style="text-align: left; vertical-align: middle;"><b>Category Name</b></td>
			<td style="text-align: center; vertical-align: middle;"><b>Add Subcategory</b></td>
			<td style="text-align: center; vertical-align: middle;"><b>Edit</b></td>
			<td style="text-align: center; vertical-align: middle;"><b>Remove</b></td>
			
		</tr>
		<c:forEach items="${user.categories}" var="category">
			<tr class="${'categorycolor'}">
				<td style="text-align: left; vertical-align: middle;">${category.name}</td>
				<td style="text-align: center; vertical-align: middle;"><a href="<spring:url value="" />" class="btn btn-primary" type="button">Add Subcategory</a>	</td>
				<td style="text-align: center; vertical-align: middle;"><a href="<spring:url value="" />" class="btn btn-warning" type="button">Edit</a>	</td>
				<td style="text-align: center; vertical-align: middle;"><a href="<spring:url value="" />" class="btn btn-danger" type="button">Remove</a>	</td>			
		</tr>
		<c:forEach items="${category.subcategories}" var="subcategory">
			<tr class="${'subcategorycolor'}">
				<td style="text-align: left; vertical-align: middle;">- ${subcategory.name}</td>				
				<td></td>			
				<td style="text-align: center; vertical-align: middle;"><a href="<spring:url value="" />" class="btn btn-warning" type="button">Edit</a></td>	
				<td style="text-align: center; vertical-align: middle;"><a href="<spring:url value="" />" class="btn btn-danger" type="button">Remove</a>	</td>
			</tr>
			</c:forEach>
		</c:forEach>
	</table>
		
		</div>

		
