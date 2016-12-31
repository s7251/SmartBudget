<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../tiles-template/taglib.jsp"%>

<div class="panel panel-default">
	<!-- Default panel contents -->
		<div class="panel-heading"><h1 class="panel-title">Account forecast for future months </h1></div>
		<div class="panel-body" >
<a href="<spring:url value="/user-accounts" />" class="btn btn-primary" type="button" >Back</a>

	</div>
<table class="table">
	<thead>
		<tr bgcolor="#efefef">
			<th><h1 class="panel-title">Graph: <span class="pull-right">${accountName}</span></h1> </th>		
		</tr>
	</thead>
	<tbody>	
	<tr>
<td>
<!-- <div id="chart_div" style="width: 100%; height: 500px;"></div> -->
<div id="curve_chart" style="width: 1100px; height: 500px"></div>
</td>		
</tr>
	<c:forEach items="${dataForForecast}" var="summary">
	<tr>
	<td>
	<span class="text-primary pull-right" >
	<b>${summary.key}</b> = <fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${summary.value}" type="currency" currencySymbol="zł" pattern=" #,##0.00 ¤; -#,##0.00 ¤"/>
	</span> 
	</td>
	</tr>
	</c:forEach>	
	<c:forEach items="${forecastDataByAccount}" var="summary">
	<tr>
	<td>
	<span class="text-danger pull-right">
	<b>${summary.key}</b> = <fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${summary.value}" type="currency" currencySymbol="zł" pattern=" #,##0.00 ¤; -#,##0.00 ¤"/>
	</span> 
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
          ['Month', 'Month Summary', 'Forecast'],
          <c:forEach items="${dataForForecast}" var="entry">
          ['${entry.key}',  ${entry.value}, null],  
          </c:forEach> 
          <c:forEach items="${forecastDataByAccount}" var="entry">
          ['${entry.key}', null , ${entry.value}],  
          </c:forEach> 
       
        
           
        ]);
        
        var options = {
          title: 'Forecasting',
          curveType: 'function',
          legend: { position: 'bottom' },
          tooltip: { trigger: 'yes' },
          pointSize: 4,
          series: {
          
              1: { lineDashStyle: [4, 4] },
                        
            },
         
        };
        
        var formatter = new google.visualization.NumberFormat({decimalSymbol: ',',groupingSymbol: '.', negativeColor: 'red', negativeParens: true, suffix: ' zł '});
        formatter.format(data, 1);
        var chart = new google.visualization.LineChart(document.getElementById('curve_chart'));       
        chart.draw(data, options);        
        /* chart.setSelection([
                            {row:11, column:1}                           
                        ]); */
        
      }
    </script>
    
     <!-- <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
    <script type="text/javascript">
      google.charts.load('current', {'packages':['corechart']});
      google.charts.setOnLoadCallback(drawChart);

      function drawChart() {
        var data = google.visualization.arrayToDataTable([
                                                          ['Month', 'Prievious Month Summary', 'Forecast'],
                                                          <c:forEach items="${forecastDataByAccount}" var="entry">
                                                          ['${entry.key}',  ${entry.value}, null],    
                                                        
                                                          </c:forEach>  
                                                          
                                                          ['2018-1-1', null , 898038],
                                                          ['2019-2-1', null , 988037],
                                                          ['2019-3-1', null , 1888037],
        ]);

        var options = {
          title: 'Company Performance',
          hAxis: {title: 'Year',  titleTextStyle: {color: '#333'}},
          vAxis: {minValue: 0},
          interpolateNulls: true,
          
        };

        var chart = new google.visualization.AreaChart(document.getElementById('chart_div'));
        chart.draw(data, options);
      }
    </script> -->
  </head>


   
  
   