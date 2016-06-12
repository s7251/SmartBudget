<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../tiles-template/taglib.jsp"%>

<c:if test="${param.success eq true}">
		<div class="alert alert-success">Success. Please Log In!</div>
	</c:if>

<br><br>
<form:form commandName="user" cssClass="form-horizontal">
	<div class="form-group" style="text-align: center; width: 500px; margin: 0 auto;">
		<h4><b>New Account</b></h4>
		</div>
	<div class="form-group" style="text-align: center; width: 500px; margin: 0 auto;">
			<label for="name" class="col-sm-2 control-label">Name:</label>
		<div class="col-sm-10">
			<form:input path="name" cssClass="form-control" style="width: 350px" placeholder="Please type your name" autofocus="autofocus"/>
		</div>
	</div>
		<div class="form-group" style="text-align: center; width: 500px; margin: 0 auto;">
		<label for="email" class="col-sm-2 control-label">Email:</label>
		<div class="col-sm-10">
			<form:input path="email" cssClass="form-control"  style="width: 350px" placeholder="Please type your email"/>
		</div>
	</div>
			<div class="form-group" style="text-align: center; width: 500px; margin: 0 auto;">
		<label for="password" class="col-sm-2 control-label">Password:</label>
		<div class="col-sm-10">
			<form:password path="password" cssClass="form-control" style="width: 350px" placeholder="Please type your password"/>
		</div>
	</div>
				<div class="form-actions" style="text-align: center; margin: 50;" >		
	<br>
			<input type="submit" value="Register" class="btn btn-primary"/>
		
	</div>
</form:form>

