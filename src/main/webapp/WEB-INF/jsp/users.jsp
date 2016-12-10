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
			<td style="text-align: center;"><b>E-mail</b></td>			
			<td style="text-align: center;"><b>Rename</b></td>
			<td style="text-align: center;"><b>Change Password</b></td>
			<td style="text-align: center;"><b>Remove</b></td>									
		</tr>
		<c:forEach items="${users}" var="users">
			<tr>
				<td style="text-align: center; vertical-align: middle;"><a href="<spring:url value="/users/${users.id}.html" />"><c:out value="${users.name}" /></a></td>
				<td style="text-align: center; vertical-align: middle;"><a href="<spring:url value="" />" class="btn btn-warning" type="button" data-toggle="modal" data-target="#changeEmailModal${users.id}">Change e-mail</a>
				
				<form:form mehod="post" modelAttribute="user" action="/change-email.html" cssClass="form-horizontal form">
<form:hidden path="id" value="${users.id}" />
<form:hidden path="name" value="${users.name}" />
<form:hidden path="password" value="${users.password}" />
<form:hidden path="enabled" value="1" />
	<!-- Modal	 -->
	<div class="modal fade" id="changeEmailModal${users.id}" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Cancel">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel" style="text-align: left">Change ${users.name} e-mail: </h4>
				</div>
				<div class="modal-body">
									
				<div class="form-group"
						style="text-align: center; width: 600px; margin: 0 auto;">
						<label for="email" class="col-sm-2 control-label">E-mail:</label>
						<div class="col-sm-10">
							<form:input path="email" cssClass="form-control" style="width: 350px" placeholder="Please type your new e-mail"	autofocus="autofocus" value="${users.email}" />
						</div>
					</div>
										
    		</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
					<input type="submit" class="btn btn-success" value="Change Password" />
				</div>
			</div>
		</div>
	</div>
</form:form>
				</td>
				<td style="text-align: center; vertical-align: middle;">
				
				<c:if test="${(users.name != 'admin' )}">  
				<a href="<spring:url value="" />" class="btn btn-warning" type="button" data-toggle="modal" data-target="#renameUserModal${users.id}">Rename</a>    </c:if>      </td>					
				<td style="text-align: center; vertical-align: middle;"><a href="<spring:url value="" />" class="btn btn-warning" type="button" data-toggle="modal" data-target="#changePasswordModal${users.id}">Change password</a>	
				
					<form:form mehod="post" modelAttribute="user" action="/change-password.html" cssClass="form-horizontal form">
<form:hidden path="id" value="${users.id}" />
<form:hidden path="name" value="${users.name}" />
<form:hidden path="email" value="${users.email}" />
<form:hidden path="enabled" value="1" />
	<!-- Modal	 -->
	<div class="modal fade" id="changePasswordModal${users.id}" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Cancel">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel" style="text-align: left">Change ${users.name} password: </h4>
				</div>
				<div class="modal-body">
									
				<div class="form-group"
						style="text-align: center; width: 600px; margin: 0 auto;">
						<label for="password" class="col-sm-2 control-label">Password:</label>
						<div class="col-sm-10">
							<form:password path="password" cssClass="form-control" style="width: 350px" placeholder="Please type your new password"	autofocus="autofocus" />
						</div>
					</div>
					
					<div class="form-group"
						style="text-align: center; width: 600px; margin: 0 auto;">
						<label for="password" class="col-sm-2 control-label">Re-type:</label>
						<div class="col-sm-10">
							<input type="password" name="password_again" id="password_again" class="form-control" style="width: 350px" placeholder="Please re-type your new password"	autofocus="autofocus" />
						</div>
					</div>
    		</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
					<input type="submit" class="btn btn-success" value="Change Password" />
				</div>
			</div>
		</div>
	</div>
</form:form>
				
				</td>
				<td style="text-align: center; vertical-align: middle;">
				<c:if test="${(users.name != 'admin' )}"> <a href="<spring:url value="/users/removeuser/${users.id}.html" />" class="btn btn-danger" type="button">Remove</a></c:if>  
		
		<form:form mehod="post" modelAttribute="user" action="/renameUser.html" cssClass="form-horizontal form">
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
					<h4 class="modal-title" id="myModalLabel" style="text-align: left">Rename user</h4>
				</div>
				<div class="modal-body">
				
					<div class="form-group"
						style="text-align: center; width: 600px; margin: 0 auto;">
						<label for="name" class="col-sm-2 control-label">Name:</label>
						<div class="col-sm-10">
							<form:input path="name" cssClass="form-control" style="width: 350px" autofocus="autofocus" value="${users.name}"></form:input>
						</div>
					</div>
					
					</div>
					
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
					<input type="submit" class="btn btn-success" value="Rename" />
				</div>
			</div>
		</div>
	</div>
</form:form>
		</td>	
			</tr>		
		</c:forEach>
	</table>
	</div>
	


    <script>
$(document).ready(function() {   
    $('form').each(function() {  
        $(this).validate({       
        	rules: {	
				name: {
					required : true,
					minlength : 4,				
				},			
				email: {
					required : true,
					email: true,
				},
				password: {
					required : true,
					minlength : 4,
				},
				password_again: {
					required : true,
					minlength : 4,
					equalTo: "#password"
				},
			},				
			highlight: function(element) {
				$(element).closest('.form-group').removeClass('has-success').addClass('has-error');
			},
			unhighlight: function(element) {
				$(element).closest('.form-group').removeClass('has-error').addClass('has-success');
			}	
			
        });
    });

});
</script>

    