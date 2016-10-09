<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../tiles-template/taglib.jsp"%>

<div class="panel panel-default">
	<!-- Default panel contents -->
		<div class="panel-heading"><h1 class="panel-title">Account forecast for future months </h1></div>
		<div class="panel-body" >
<a href="<spring:url value="/user-accounts.html" />" class="btn btn-primary" type="button" >Back</a>

	</div>
<table class="table">
	<thead>
		<tr bgcolor="#efefef">
			<th><h1 class="panel-title">Graph: <span class="pull-right">Account name</span></h1> </th>
			
			
		</tr>
	</thead>
	<tbody>		
		<tr>
<td> ${summaryAccountsByMonths}


<div id="curve_chart" style="width: 1100px; height: 500px"></div>
</td>
		</tr>
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
                                                          
                                                          
                                               
                                                                   
          ['Month', 'Month Summary'],
          <c:forEach items="${summaryAccountsByMonths}" var="entry">
          ['${entry.key}',  ${entry.value}],
         
          </c:forEach>  
        ]);

        var options = {
          title: 'Forecasting',
          curveType: 'function',
          legend: { position: 'bottom' }
        };

        var chart = new google.visualization.LineChart(document.getElementById('curve_chart'));

        chart.draw(data, options);
      }
    </script>
  </head>


  
   
  
   