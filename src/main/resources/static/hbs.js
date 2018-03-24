// Load the Visualization API and the piechart package.
google.charts.load('current', {'packages':['corechart']});

// Set a callback to run when the Google Visualization API is loaded.
google.charts.setOnLoadCallback(drawChart);

function drawChart() {
    var jsonData = $.ajax({
        url: "/chart",
        dataType: "json",
        async: false,
        success: function(json) {
            console.log(json);
            var data = new google.visualization.DataTable(json);
            var chart = new google.visualization.AreaChart(document.getElementById('chart_div'));
            chart.draw(data, null);
        }
    }
    ).responseText;
}
