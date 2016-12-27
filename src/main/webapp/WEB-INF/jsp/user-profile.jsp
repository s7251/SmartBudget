<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../tiles-template/taglib.jsp"%>

<div class="panel panel-default">
	<!-- Default panel contents -->
		<div class="panel-heading"><h1 class="panel-title">User Profile </h1></div>
	<div class="panel-body">			
			<c:if test="${(user.name != 'admin' )}"><a href="<spring:url value="/user-profile/removeprofile/${user.id}" />" class="btn btn-danger triggerRemove" type="button">Remove profile</a></c:if> 
			<a href="<spring:url value="" />" class="btn btn-warning" type="button" data-toggle="modal" data-target="#changePasswordModal">Change password</a>		
			<a href="<spring:url value="" />" class="btn btn-warning" type="button" data-toggle="modal" data-target="#changeEmailModal">Change e-mail</a>	
	</div>
	
<table class="table">
	<thead>
		<tr bgcolor="#efefef">
			<th>Details:</th>
		</tr>
	</thead>
	<tbody>		
		<tr>
			<td><b>username:</b> ${user.name}</td>
		</tr>		
		<tr>
			<td><b>email:</b> ${user.email}</td>
		</tr>
		<tr>
			<td><b>permissions: </b> ${user.permissions}</td>
		</tr>
	</tbody>
</table>

</div>


<form:form mehod="post" modelAttribute="user" action="/change-password-from-profile" cssClass="form-horizontal form">
<form:hidden path="id" value="${user.id}" />
<form:hidden path="name" value="${user.name}" />
<form:hidden path="email" value="${user.email}" />
<form:hidden path="enabled" value="1" />
	<!-- Modal	 -->
	<div class="modal fade" id="changePasswordModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Cancel">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">Change your password:</h4>
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

<form:form mehod="post" modelAttribute="user" action="/change-email-from-profile" cssClass="form-horizontal form">
<form:hidden path="id" value="${user.id}" />
<form:hidden path="name" value="${user.name}" />
<form:hidden path="password" value="${user.password}" />
<form:hidden path="enabled" value="1" />
	<!-- Modal	 -->
	<div class="modal fade" id="changeEmailModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Cancel">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">Change your e-mail:</h4>
				</div>
				<div class="modal-body">
									
				<div class="form-group"
						style="text-align: center; width: 600px; margin: 0 auto;">
						<label for="email" class="col-sm-2 control-label">E-mail:</label>
						<div class="col-sm-10">
							<form:input path="email" cssClass="form-control" style="width: 350px" placeholder="Please type your new e-mail"	autofocus="autofocus" />
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

		<!-- Modal -->
<div class="modal fade" id="modalRemove" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
        <h4 class="modal-title" id="myModalLabel">Remove profile</h4>
      </div>
      <div class="modal-body">
        Are you sure? All user data will be removed!
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
        <a href="" class="btn btn-danger removeBtn">Remove</a>
      </div>
    </div>
  </div>
</div>

 <script>
$(document).ready(function() {   
	$(".triggerRemove").click(function(e) {
		e.preventDefault();
		$("#modalRemove .removeBtn").attr("href", $(this).attr("href"));
		$("#modalRemove").modal();
	});
    $('form').each(function() {  
        $(this).validate({       
        	rules: {							
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

    
