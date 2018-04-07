var data = [];
var totalPoints = 110;

$(function () {
    //Widgets count
    $('.count-to').countTo();

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
        }
    };

    var data = $.ajax({
        url: "http://localhost:8080/balancebyday",
        dataType: "json",
        async: false,
        success: function(json) {
            console.log(json);
            data=json;
        }
    }).responseJSON;

    var plot = $.plot('#chart', data, options );
});