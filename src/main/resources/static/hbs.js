google.charts.load('current', {
'packages': ['corechart']
});
google.charts.setOnLoadCallback(drawChart);

function drawChart() {
var query = new google.visualization.Query('https://docs.google.com/spreadsheets/d/1SQHdELyI-DAVklk0TSMJrkqLKxX93QOvVEjTIVy78NE/edit?usp=sharing');
query.send(handleQueryResponse);
}

function handleQueryResponse(response) {
var options = {
  title: 'Home Banking Savings',
  hAxis: {title: 'Year',  titleTextStyle: {color: '#333'}},
  vAxis: {minValue: 0}
};
var data = response.getDataTable();
var chart = new google.visualization.AreaChart(document.getElementById('chart_div'));
chart.draw(data, options);
}
