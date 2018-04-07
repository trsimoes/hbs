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
        url: "/balancebyday",
        dataType: "json",
        async: false,
        success: function(json) {
            console.log(json);
            data=json;
        }
    }).responseJSON;

    var plot = $.plot('#chart', data, options );


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
    $("div#createDateBalanceDiv").countTo({from: 0, to: latest.createDateTime});

});