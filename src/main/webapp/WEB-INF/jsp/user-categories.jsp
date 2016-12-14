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
			<td style="text-align: center; vertical-align: middle;"><b>Rename</b></td>
			<td style="text-align: center; vertical-align: middle;"><b>Remove</b></td>
			<td style="text-align: center; vertical-align: middle;"></td>
		</tr>
		<c:forEach items="${user.categories}" var="category">
			<tr class="${'categorycolor'}">
				<td style="text-align: left; vertical-align: middle;">${category.name}</td>
				<td style="text-align: center; vertical-align: middle;">	<a href="<spring:url value="" />" class="btn btn-primary" type="button" data-toggle="modal" data-target="#addSubcategoryModal${category.id}">Add subcategory</a>	</td>
				<td style="text-align: center; vertical-align: middle;"><a href="<spring:url value="" />" class="btn btn-warning" type="button" data-toggle="modal" data-target="#renameCategoryModal${category.id}">Rename</a>	</td>
				<td style="text-align: center; vertical-align: middle;"><a href="<spring:url value="" />" class="btn btn-danger" type="button" data-toggle="modal" data-target="#removeCategoryModal${category.id}">Remove</a>	</td>			
		<td>
		
			<form:form mehod="post" modelAttribute="subcategory" action="/addSubcategory.html" cssClass="form-horizontal" id="form">	
				<form:hidden path="categoryId" value="${category.id}" />
	<!-- Modal -->
	<div class="modal fade" id="addSubcategoryModal${category.id}" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Cancel">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">Add subcategory</h4>
				</div>
				<div class="modal-body">
				
					<div class="form-group"
						style="text-align: center; width: 600px; margin: 0 auto;">
						<label for="name" class="col-sm-2 control-label">Name:</label>
						<div class="col-sm-10">
							<form:input path="name" cssClass="form-control" style="width: 350px" placeholder="Please type name of subcategory"	autofocus="autofocus" />
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


<form:form mehod="post" modelAttribute="category" action="/renameCategory.html" cssClass="form-horizontal" id="form">	
<form:hidden path="id" value="${category.id}" />
		<!-- Modal -->
	<div class="modal fade" id="renameCategoryModal${category.id}" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Cancel">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">Rename Category</h4>
				</div>
				<div class="modal-body">
				
					<div class="form-group"
						style="text-align: center; width: 600px; margin: 0 auto;">
						<label for="name" class="col-sm-2 control-label">Name:</label>
						<div class="col-sm-10">
							<form:input path="name" cssClass="form-control" style="width: 350px" value="${category.name}"	autofocus="autofocus" />
						 </div>
					</div>
					
				</div>
				
				<div class="modal-footer">
				
					<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
					<input type="submit" class="btn btn-success" value="Rename" />
				</div>
			</div>
		</div>
	</div>
</form:form>

						<form:form mehod="post" modelAttribute="RemoveCategoryForm" action="/user-categories/removecategory.html" cssClass="form-horizontal" id="form">	
<form:hidden path="categoryId" value="${category.id}" />
		<!-- Modal -->
	<div class="modal fade" id="removeCategoryModal${category.id}" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Cancel">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">New subcategory for transactions from category ${category.name}:</h4>
				</div>
				<div class="modal-body">
				
						<div class="form-group"	style="text-align: center; width: 600px; margin: 0 auto;">
						<label for="category" class="col-sm-2 control-label">Subcategory:</label>
						<div class="col-sm-10">
						<form:select class="form-control" path="newSubcategoryId"  style="text-align: left; width: 350px;">									
									<form:options items="${subcategoriesMap}" />							
							</form:select>						
						</div>
					</div>
					
				</div>
				
				<div class="modal-footer">
				
					<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
					<input type="submit" class="btn btn-danger" value="Remove" />
				</div>
			</div>
		</div>
	</div>
</form:form>	
	</td>
		</tr>

		<c:forEach items="${category.subcategories}" var="subcategory">
			<tr class="${'subcategorycolor'}">
				<td style="text-align: left; vertical-align: middle;">- ${subcategory.name}</td>				
				<td></td>			
				<td style="text-align: center; vertical-align: middle;"><a href="<spring:url value="" />" class="btn btn-warning" type="button" data-toggle="modal" data-target="#renameSubcategoryModal${subcategory.id}">Rename</a></td>	
				<td style="text-align: center; vertical-align: middle;"><a href="<spring:url value="" />" class="btn btn-danger" type="button" data-toggle="modal" data-target="#removeSubcategoryModal${subcategory.id}">Remove</a>	</td>
			<td>		
						<form:form mehod="post" modelAttribute="RemoveSubcategoryForm" action="/user-categories/removesubcategory.html" cssClass="form-horizontal" id="form">	
<form:hidden path="subcategoryId" value="${subcategory.id}" />
<form:hidden path="categoryId" value="${category.id}" />
		<!-- Modal -->
	<div class="modal fade" id="removeSubcategoryModal${subcategory.id}" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Cancel">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">New subcategory for transactions from subcategory ${subcategory.name}:</h4>
				</div>
				<div class="modal-body">
				
						<div class="form-group"	style="text-align: center; width: 600px; margin: 0 auto;">
						<label for="category" class="col-sm-2 control-label">Subcategory:</label>
						<div class="col-sm-10">
						<form:select class="form-control" path="newSubcategoryId"  style="text-align: left; width: 350px;">
									<form:options items="${subcategoriesMap}" />							
							</form:select>						
						</div>
					</div>
					
				</div>
				
				<div class="modal-footer">
				
					<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
					<input type="submit" class="btn btn-danger" value="Remove" />
				</div>
			</div>
		</div>
	</div>
</form:form>	
			
						<form:form mehod="post" modelAttribute="subcategory" action="/renameSubcategory.html" cssClass="form-horizontal" id="form">	
<form:hidden path="id" value="${subcategory.id}" />
<form:hidden path="categoryId" value="${category.id}" />
		<!-- Modal -->
	<div class="modal fade" id="renameSubcategoryModal${subcategory.id}" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Cancel">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">Rename Subcategory</h4>
				</div>
				<div class="modal-body">
				
					<div class="form-group"
						style="text-align: center; width: 600px; margin: 0 auto;">
						<label for="name" class="col-sm-2 control-label">Name:</label>
						<div class="col-sm-10">
							<form:input path="name" cssClass="form-control" style="width: 350px" value="${subcategory.name}"	autofocus="autofocus" />
						 </div>
					</div>
					
				</div>
				
				<div class="modal-footer">
				
					<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
					<input type="submit" class="btn btn-success" value="Rename" />
				</div>
			</div>
		</div>
	</div>
</form:form>	
			</td>
			</tr>	
					
			</c:forEach>

		</c:forEach>
	
		</table>
		</div>
		
		<form:form mehod="post" modelAttribute="category" action="/addCategory.html" cssClass="form-horizontal" id="form">		
	<!-- Modal -->
	<div class="modal fade" id="addCategoryModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Cancel">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">Add category</h4>
				</div>
				<div class="modal-body">
				
					<div class="form-group"
						style="text-align: center; width: 600px; margin: 0 auto;">
						<label for="name" class="col-sm-2 control-label">Name:</label>
						<div class="col-sm-10">
							<form:input path="name" cssClass="form-control" style="width: 350px" placeholder="Please type name of category"	autofocus="autofocus" />
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


<script type="text/javascript">
$(document).ready(function() {

    $('form').each(function() {  
        $(this).validate({       
            rules: {				
				name: {
					required : true,
					minlength : 4,				
				},		
				newSubcategoryId: {
					required : true,								
				},
			},		
			
			highlight: function(element) {
				$(element).closest('.form-group').removeClass('has-success').addClass('has-error');
			},
			unhighlight: function(element) {
				$(element).closest('.form-group').removeClass('has-error').addClass('has-success');
			},		
        });
    });

});
</script>