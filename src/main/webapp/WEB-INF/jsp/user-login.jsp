<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ include file="../tiles-template/taglib.jsp"%>
<style>
.form-signin {
  max-width: 330px;
  padding: 15px;
  margin: 0 auto;
}
.form-signin .form-signin-heading,
.form-signin .checkbox {
  margin-bottom: 10px;
}
.form-signin .checkbox {
  font-weight: normal;
}
.form-signin .form-control {
  position: relative;
  height: auto;
  -webkit-box-sizing: border-box;
     -moz-box-sizing: border-box;
          box-sizing: border-box;
  padding: 8px;
  font-size: 14px;
}
.form-signin .form-control:focus {
  z-index: 2;
}
.form-signin input[type="email"] {
  margin-bottom: -1px;
  border-bottom-right-radius: 0;
  border-bottom-left-radius: 0;
}
.form-signin input[type="password"] {
  margin-bottom: 10px;
  border-top-left-radius: 0;
  border-top-right-radius: 0;
}
</style>

<form class="form-signin" role="form" action="/j_spring_security_check"  method="POST">
<h4 class="form-signin-heading"><b>Please sign in</b></h4>
	<input type="text" name="j_username" class="form-control" placeholder="Please enter name" required autofocus> 
	<input type="password" name="j_password" class="form-control" placeholder="Please enter password" required> 
	<button class="btn btn-primary btn-block" type="submit">Sign in</button>
	<a href="<spring:url value="xxx" />" data-toggle="modal" data-target="#resetPasswordModal"><br>Forgot password?</a>
</form>


<form:form mehod="post" modelAttribute="ResetPasswordForm" action="/reset-password" cssClass="form-horizontal form">


	<!-- Modal	 -->
	<div class="modal fade" id="resetPasswordModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Cancel">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">Reset your password:</h4>
				</div>
				<div class="modal-body">
				
				<div class="form-group"
						style="text-align: center; width: 600px; margin: 0 auto;">
						<label for="name" class="col-sm-2 control-label">Name:</label>
						<div class="col-sm-10">
							<form:input path="name" cssClass="form-control" style="width: 350px" placeholder="Please type your account name"	autofocus="autofocus" />
						</div>
					</div>
									
				<div class="form-group"
						style="text-align: center; width: 600px; margin: 0 auto;">
						<label for="email" class="col-sm-2 control-label">E-mail:</label>
						<div class="col-sm-10">
							<form:input path="email" cssClass="form-control" style="width: 350px" placeholder="Please type your e-mail"	autofocus="autofocus" />
						</div>
					</div>
										
    		</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
					<input type="submit" class="btn btn-success" value="Please send me new password" />
				</div>
			</div>
		</div>
	</div>
</form:form>

<script type="text/javascript">
$(document).ready(function() {
	
	$(".form").validate(
		{
			rules: {
				name: {
					required : true,
					minlength : 4,						
				},
				email: {
					required : true,
					email: true,
				},							
			},
			highlight: function(element) {
				$(element).closest('.form-group').removeClass('has-success').addClass('has-error');
			},
			unhighlight: function(element) {
				$(element).closest('.form-group').removeClass('has-error').addClass('has-success');
			},	
			
		}
	);
});
</script>
