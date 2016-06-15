<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../tiles-template/taglib.jsp"%>

<div class="panel panel-default">
	<!-- Default panel contents -->
		<div class="panel-heading"><h1 class="panel-title">User Reports </h1></div>
	<div class="panel-body">			
				<div class="btn-group">
  <button type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
   Generate report <span class="caret"></span>
  </button>
  <ul class="dropdown-menu">
    <li><a href="">Expenses by categories</a></li>
    <li><a href="">Influences by categories</a></li>
    <li><a href="">Balance of account</a></li> 

  </ul>
</div>
	</div>
	
<table class="table">
	<thead>
		<tr bgcolor="#efefef">
			<th>Graph:</th>
		</tr>
	</thead>
	<tbody>		
		<tr>
<td> <div  id="donutchart" style="width: 900px; height: 500px;"></div></td>
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
          ['Restaurant',     101.00],
          ['Barber',      25.00],
          ['Supermarket',  89.00],         
        ]);

        var options = {
          title: 'Expenses by categories in 06.2016',
          pieHole: 0.4,
        };

        var chart = new google.visualization.PieChart(document.getElementById('donutchart'));
        chart.draw(data, options);
      }
    </script>
  </head>
 