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
		<div class="panel-heading"><h1 class="panel-title">Categories </h1></div>
	<div class="panel-body">			
			<a href="<spring:url value="" />" class="btn btn-primary" type="button" data-toggle="modal" data-target="#addCategoryModal">Add category</a>						
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
		
		<form:form commandName="category" cssClass="form-horizontal">
	<!-- Modal -->
	<div class="modal fade" id="addCategoryModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">Add category</h4>
				</div>
				<div class="modal-body">
				
					<div class="form-group"
						style="text-align: center; width: 800px; margin: 0 auto;">
						<label for="name" class="col-sm-2 control-label">Name:</label>
						<div class="col-sm-10">
							<form:input path="name" cssClass="form-control" style="width: 350px" placeholder="Please type name of category"	autofocus="autofocus" />
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

		
