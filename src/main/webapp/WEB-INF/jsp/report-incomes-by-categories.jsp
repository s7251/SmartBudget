<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../tiles-template/taglib.jsp"%>

<div class="panel panel-default">
	<!-- Default panel contents -->
		<div class="panel-heading"><h1 class="panel-title">Incomes by subcategories  </h1></div>
		<div class="panel-body" >
<a href="<spring:url value="/user-reports.html" />" class="btn btn-primary" type="button" >Back</a>
<a href="<spring:url value="/csv-report-incomes-by-subcategories/${date}.html" />" class="btn btn-primary" type="button" >Download CSV</a>
<span class="pull-right">
 <c:forEach items="${summaryOfAllAccounts}" var="entry">
         ${entry.key}: <b><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${entry.value}" type="currency"/> </b> <br>
           </c:forEach>-<br>
          <b>Summary of incomes: <fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${summary}" type="currency"/></b>
          </span>
	</div>
<table class="table">
	<thead>
		<tr bgcolor="#efefef">
		<th><h1 class="panel-title">Graph: <span class="pull-right">${date}</span></h1> </th>
		</tr>
	</thead>
	<tbody>		
		<tr>
<td> <div id="donutchart" style="width: 1100px; height: 500px;"></div>

</td>
		</tr>
	</tbody>
</table>

</div>

   
	 <head>
    <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
    <script type="text/javascript">
      google.charts.load("current", {packages:["corechart"]});
      google.charts.setOnLoadCallback(drawChart);
      
      function drawChart() {
        var data = google.visualization.arrayToDataTable([
          ['Subcategory', 'zł per month'],      
          <c:forEach items="${summaryOfAllAccounts}" var="entry">
          ['${entry.key}',   ${entry.value}],
           </c:forEach>          
          ]);           
        var options = {
          title: 'Incomes by subcategories in ' + '${date}',
          pieHole: 0.5,
   
        };
        var chart = new google.visualization.PieChart(document.getElementById('donutchart'));
        chart.draw(data, options);
      }
    </script>
  </head>
  
   