<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../tiles-template/taglib.jsp"%>

<div class="panel panel-default">
	<!-- Default panel contents -->
		<div class="panel-heading"><h1 class="panel-title">Incomes by subcategories  </h1></div>
		<div class="panel-body" >
<a href="<spring:url value="/user-reports.html" />" class="btn btn-primary" type="button" >Back</a>

	</div>
<table class="table">
	<thead>
		<tr bgcolor="#efefef">
		<th><h1 class="panel-title">Graph: <span class="pull-right">${date}</span></h1> </th>
		</tr>
	</thead>
	<tbody>		
		<tr>
		
<td> 


 <div id="chart_div" style="width: 100%; height: 500px;"></div>
</td>
		</tr>
		<c:forEach items="${transactionsInTime}" var="entry">
	<tr>
	<td>
	<b>${entry.key}</b> = ${entry.value}
	</td>
	</tr>
	</c:forEach>	
	</tbody>
</table>

</div>


  <head>
    <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
    <script type="text/javascript">
      google.charts.load('current', {'packages':['corechart']});
      google.charts.setOnLoadCallback(drawChart);

      function drawChart() {
        var data = google.visualization.arrayToDataTable([
          ['Date', 'Month Summary'],
          <c:forEach items="${transactionsInTime}" var="entry">
          ['${entry.key}',   ${entry.value}],
           </c:forEach>    
          
        ]);

        var options = {
          colors: ['blue'],
          title: 'Income transactions summary in time',
          hAxis: {title: 'Months in ${date}' ,  titleTextStyle: {color: '#333'}},
          vAxis: {minValue: 0},
          legend: 'none',
         
        };
        
        var chart = new google.visualization.AreaChart(document.getElementById('chart_div'));
        chart.draw(data, options);
      }
    </script>
  </head>
   