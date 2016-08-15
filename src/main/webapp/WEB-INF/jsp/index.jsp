<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
    <div class="jumbotron">
      <div class="container" style="text-align:center"> 
        <h1>Welcome to SmartBudget</h1>
        <p>This application will help you to manage your personal finances</p>     
        

        
        <br><br>
       <a href='<spring:url value="/user-login.html"/>'><span style="font-size:10.5em;" class="glyphicon glyphicon-piggy-bank"></span> </a>
         <p><b><a href='<spring:url value="/user-register.html"/>'>Create an account</a></b> or <b> <a href='<spring:url value="/user-login.html"/>'>sign in</a></b></p>
      </div>
    </div>