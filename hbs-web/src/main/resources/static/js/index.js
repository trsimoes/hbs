var data = [];
var totalPoints = 110;
var months = ["JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC"];
var options = {
    series: {
        shadowSize: 0
    },
    grid: {
        borderColor: '#f3f3f3',
        borderWidth: 1,
        tickColor: '#f3f3f3'
    },
    lines: {
        fill: true
    },
    xaxis: {
        mode: "categories",
        tickLength: 0
    },
    legend: {
         noColumns: 2,
         container:$("#chart-legend"),
         labelBoxBorderColor: "#000000",
     }
};

$(function () {

    //Widgets count
    $('.count-to').countTo();

    updateChart("/chart/last10days", "BALANCE LAST 10 DAYS");

    updateInfoBoxes();
});

function updateChart(paramUrl, paramLabel) {

    var data = $.ajax({
        url: paramUrl,
        dataType: "json",
        async: false,
        success: function(json) {
            data=json;
        }
    }).responseJSON;

    var plot = $.plot('#chart', data, options );

    $("div#chart-name").text(paramLabel);
};

function updateInfoBoxes() {


    var latest = $.ajax({
            url: "/latest",
            dataType: "json",
            async: false,
            success: function(json) {
                latest=json;
            }
        }).responseJSON;

    $("div#accountBalanceDiv").countTo({from: 0, to: latest.accountBalance});
    $("div#creditBalanceDiv").countTo({from: 0, to: latest.creditBalance});
    $("div#euroticketBalanceDiv").countTo({from: 0, to: latest.euroticketBalance});
    $("div#overallBalanceDiv").countTo({from: 0, to: latest.overallBalance});

    var lastSnapshotDateTime = new Date(latest.createDateTime);
    var now = new Date();
    var diffMs = (now - lastSnapshotDateTime);
    var diffMins = Math.round(((diffMs % 86400000) % 3600000) / 60000);

    var lastUpdate = diffMins + " min ago";

    $("div#createDateBalanceDiv").text(lastUpdate);
}