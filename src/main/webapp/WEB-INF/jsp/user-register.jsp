<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../tiles-template/taglib.jsp"%>

<c:if test="${param.success eq true}">
		<div class="alert alert-success">Success. Please Log In!</div>
	</c:if>

<br><br>
<form:form commandName="user" cssClass="form-horizontal registerForm">
	<div class="form-group" style="text-align: center; width: 750px; margin: 0 auto;">
		<h4><b>New account</b></h4>
		</div>
	<div class="form-group" style="text-align: center; width: 750px; margin: 0 auto;">
			<label for="name" class="col-sm-4 control-label">Name:</label>
		<div class="col-sm-8">
			<form:input path="name" cssClass="form-control" style="width: 350px" placeholder="Please type your name" autofocus="autofocus"/>
		</div>
	</div>
		<div class="form-group" style="text-align: center; width: 750px; margin: 0 auto;">
		<label for="email" class="col-sm-4 control-label">Email:</label>
		<div class="col-sm-8">
			<form:input path="email" cssClass="form-control"  style="width: 350px" placeholder="Please type your email"/>
		</div>
	</div>
			<div class="form-group" style="text-align: center; width: 750px; margin: 0 auto;">
		<label for="password" class="col-sm-4 control-label">Password:</label>
		<div class="col-sm-8">
			<form:password path="password" cssClass="form-control" style="width: 350px" placeholder="Please type your password"/>
		</div>
	</div>
	<div class="form-group" style="text-align: center; width: 750px; margin: 0 auto;">
		<label for="password" class="col-sm-4 control-label">Re-type Password:</label>
		<div class="col-sm-8">
			<input type="password" name="password_again" id="password_again" placeholder="Please re-type your password" class="form-control" style="width: 350px" />
		</div>
	</div>
				<div class="form-actions" style="text-align: center; margin: 50;" >		
	<br>
			<input type="submit" value="Register" class="btn btn-primary"/>
		
	</div>
</form:form>

<script type="text/javascript">
$(document).ready(function() {
	
	$(".registerForm").validate(
		{
			rules: {
				name: {
					required : true,
					minlength : 4,	
					remote : {
						url: "<spring:url value='/user-available' />",
						type: "get",
						data: {
							name: function() {
								return $("#name").val();
							}
						}
					}
				},
				email: {
					required : true,
					email: true,
// 					remote : {
// 						url: "<spring:url value='/email-available' />",
// 						type: "get",
// 						data: {
// 							name: function() {
// 								return $("#email").val();
// 							}
// 						}
// 					}
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
			},		
			messages: {
				name: {
					remote: "This user exist in system!"
				},
				email: {
					remote: "This e-mail address exist in system!"
			}
			}
		}
	);
});
</script>
