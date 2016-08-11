<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../tiles-template/taglib.jsp"%>

<div class="panel panel-default">
	<!-- Default panel contents -->
		<div class="panel-heading"><h1 class="panel-title">List of users </h1></div>
	<div class="panel-body">
		<p>Below you can see list of users.</p>
	</div>	
	<!-- Table -->
	<table class="table table-striped">
		<tr>
			<td style="text-align: center;"><b>Name</b></td>
			<td style="text-align: center;"><b>Edit</b></td>
			<td style="text-align: center;"><b>Remove</b></td>									
		</tr>
		<c:forEach items="${users}" var="users">
			<tr>
				<td style="text-align: center; vertical-align: middle;"><a href="<spring:url value="/users/${users.id}.html" />"><c:out value="${users.name}" /></a></td>
				<td style="text-align: center; vertical-align: middle;">
				<c:if test="${(users.name != 'admin' )}">  
				<a href="<spring:url value="" />" class="btn btn-warning" type="button" data-toggle="modal" data-target="#renameUserModal${users.id}">Rename</a>    </c:if>      </td>					
				<td style="text-align: center; vertical-align: middle;">
				<c:if test="${(users.name != 'admin' )}"> <a href="<spring:url value="/users/removeuser/${users.id}.html" />" class="btn btn-danger" type="button">Remove</a></c:if>  </td>	
		
		<form:form mehod="post" modelAttribute="user" action="/renameUser.html" cssClass="form-horizontal">
			<form:hidden path="id" value="${users.id}" />
			<form:hidden path="password" value="${users.password}" />
			<form:hidden path="email" value="${users.email}" />
			<form:hidden path="enabled" value="${users.enabled}" />	
	<!-- Modal -->
	<div class="modal fade" id="renameUserModal${users.id}" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">Rename user</h4>
				</div>
				<div class="modal-body">
				
					<div class="form-group"
						style="text-align: center; width: 800px; margin: 0 auto;">
						<label for="name" class="col-sm-2 control-label">Name:</label>
						<div class="col-sm-10">
							<form:input path="name" cssClass="form-control" style="width: 350px" autofocus="autofocus" value="${users.name}"></form:input>
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
		
			</tr>
			
						
			
			
		</c:forEach>
	</table>
	</div>
	