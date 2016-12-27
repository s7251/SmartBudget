<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap-theme.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.4/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<script type="text/javascript" 	src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.15.0/jquery.validate.min.js"></script>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>

   <style type="text/css">         
    .footer {
        clear:both;
        position:fixed;
        bottom:0;
        left:0;
        text-align:center;
        width:100%;
        height:20px;
       }     
    </style>   

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><tiles:getAsString name="title"/></title>
</head>

<body>
<%@ taglib uri="http://tiles.apache.org/tags-tiles-extras" prefix="tilesx" %>
<tilesx:useAttribute name="current"/>

<div class="container">
 <!-- Static navbar -->
      <nav class="navbar navbar-inverse">
        <div class="container-fluid">
          <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
              <span class="sr-only">Toggle navigation</span>
              <span class="icon-bar"></span>
              <span class="icon-bar"></span>
              <span class="icon-bar"></span>
            </button>            
            <a class="navbar-brand" href="/user-transactions"><span class="glyphicon glyphicon-piggy-bank" aria-hidden="true"></span>  Smart Budget     
            
            </a>
          </div>
          <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav">
               <security:authorize access="hasRole('ROLE_USER')">    
              <li class="${current == 'user-transactions' ? 'active' : ''}"><a href='<spring:url value="/user-transactions"/>'>Transactions</a></li>
              <li class="${current == 'user-accounts' ? 'active' : ''}"><a href='<spring:url value="/user-accounts"/>'>Accounts</a></li>
              <li class="${current == 'user-categories' ? 'active' : ''}"><a href='<spring:url value="/user-categories"/>'>Categories</a></li>              
              <li class="${current == 'user-budgetplan' ? 'active' : ''}"><a href='<spring:url value="/user-budgetplan"/>'>Budget Plan</a></li>                
               <li class="${current == 'user-reports' ? 'active' : ''}"><a href='<spring:url value="/user-reports"/>'>Reports</a></li>                 
               </security:authorize> 
            
            </ul>
        
            <ul class="nav navbar-nav navbar-right">   
              

           <security:authorize access="isAuthenticated()">
            <li class="${current == 'user-profile' ? 'active' : ''}"><a href="/user-profile"><span class="glyphicon glyphicon-user"></span> Hi <b>${user.name}${loginName}!</b></a></li>   
               <security:authorize access="hasRole('ROLE_ADMIN')">              
              <li class="${current == 'users' ? 'active' : ''}"><a href='<spring:url value="/users"/>'><span class="glyphicon glyphicon-cog"></span> Manage Users</a></li>     
       	      </security:authorize>   
           <li><a href="<spring:url value="/logout"/>"><span class="glyphicon glyphicon-log-out"></span> Sign Out </a></li>
            </security:authorize>           
           
           <security:authorize access="! isAuthenticated()">
                       <li class="${current == 'user-register' ? 'active' : ''}"><a href='<spring:url value="/user-register"/>'><span class="glyphicon glyphicon-user"></span> Create account</a></li>
                  <li class="${current == 'user-login' ? 'active' : ''}"><a href='<spring:url value="/user-login"/>'><span class="glyphicon glyphicon-log-in"></span> Sign in</a></li>
           </security:authorize>
            </ul>
          </div><!--/.nav-collapse -->
        </div><!--/.container-fluid -->
      </nav>      

<tiles:insertAttribute name="body"/>


<tiles:insertAttribute name="footer"/>


</div>
</body>
</html>