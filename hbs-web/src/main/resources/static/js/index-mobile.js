$(function () {

    //Widgets count
    $('.count-to').countTo();

    updateInfoBoxes();
});

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