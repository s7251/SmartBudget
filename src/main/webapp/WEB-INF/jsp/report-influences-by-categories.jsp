<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../tiles-template/taglib.jsp"%>

<div class="panel panel-default">
	<!-- Default panel contents -->
		<div class="panel-heading"><h1 class="panel-title">Influences by categories </h1></div>
		<div class="panel-body" >
<a href="<spring:url value="/user-reports.html" />" class="btn btn-primary" type="button" >Back</a>
<span class="pull-right">
 <c:forEach items="${summaryOfAllAccounts}" var="entry">
         ${entry.key}: <b>${entry.value}</b> <br>
           </c:forEach>
          </span>
	</div>
<table class="table">
	<thead>
		<tr bgcolor="#efefef">
			<th>Graph:</th>
		</tr>
	</thead>
	<tbody>		
		<tr>
<td> <div  id="donutchart" style="width: 1100px; height: 500px;"></div>

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
          title: 'Influences by categories',
          pieHole: 0.4,
        };
        var chart = new google.visualization.PieChart(document.getElementById('donutchart'));
        chart.draw(data, options);
      }
    </script>
  </head>
  
   