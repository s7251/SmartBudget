<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../tiles-template/taglib.jsp"%>

<div class="panel panel-default">
	<!-- Default panel contents -->
		<div class="panel-heading"><h1 class="panel-title">Expenses by subcategories  </h1></div>
		<div class="panel-body" >
<a href="<spring:url value="/user-reports.html" />" class="btn btn-primary" type="button" >Back</a>
<a href="<spring:url value="/csv-report-expenses-in-time/${date}.html" />" class="btn btn-primary" type="button" >Download CSV</a>

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
	<span class="pull-right">
	<b>${entry.key}</b> = <fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${entry.value}" type="currency"/> </span>
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
          colors: ['red'],
          title: 'Expense transactions summary in time',
          hAxis: {title: 'Months in ${date}' ,  titleTextStyle: {color: '#333'}},
          vAxis: {minValue: 0},
          legend: 'none',
         
        };
        
        var chart = new google.visualization.AreaChart(document.getElementById('chart_div'));
        chart.draw(data, options);
      }
    </script>
  </head>
   