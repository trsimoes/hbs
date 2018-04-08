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

    // 04/APR - 00H30
    var pad = "00";
    var createDateTime = new Date(latest.createDateTime)
    var day = (createDateTime.getDay() + 1) + "";
    day = pad.substring(0, pad.length - day.length) + day;
    var month = months[createDateTime.getMonth()];
    var hour = createDateTime.getHours() + "";
    hour = pad.substring(0, pad.length - hour.length) + hour;
    var minutes = createDateTime.getMinutes() + "";
    minutes = pad.substring(0, pad.length - minutes.length) + minutes;
    var lastUpdate = day + "/" + month + " " + hour + "H" + minutes;

    $("div#createDateBalanceDiv").text(lastUpdate);
}