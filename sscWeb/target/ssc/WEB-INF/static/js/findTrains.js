$(document).ready(function() {
    $.ajax({
        url: "/railwayWeb/findTrains/getStationNameList",
        type: 'GET',
        success: function(data) {
            setAutocomplete(data);
        }
    });

    $('#find-button').click(function() {
        var stationFrom = $("#station-from").val();
        var stationTo = $("#station-to").val();
        var dateString = $("#date").val();
        var mod = $('input[name=filter]:radio:checked').val();
        $.ajax({
            url: "/railwayWeb/findTrains/find",
            type: 'POST',
            data: ({
                stationFrom: stationFrom,
                stationTo: stationTo,
                dateString: dateString,
                mod: mod
            }),
            success: function(data) {
                $("#found-trains-table").html('');
                $(".operation-error").html('');
                $(".operation-msg").html('');
                if (data.msgBox.error != null) {
                    $(".operation-error").html(data.msgBox.error);
                }
                else {
                    var tableHeader = "<tr>\n\
                                                    <th width=\"100px\">id</th>\n\
                                                    <th width=\"200px\">Type</th>\n\
                                                    <th width=\"100px\">Path</th>\n\
                                                    <th width=\"100px\">Date</th>\n\
                                                    <th width=\"100px\">Free seats</th>\n\
                                                    <th width=\"250px\">Actions</th>\n\
                                                </tr>"
                    var tableBody = '';
                    for (var i in data.trainListDTO) {
                        var date = new Date(data.trainListDTO[i].date);
                        var y = date.getFullYear();
                        var m = date.getMonth() + 1;
                        var d = date.getDate();
                        if (m < 10)
                            m = "0" + m;
                        if (d < 10)
                            d = "0" + d;
                        var newDate = d + "." + m + "." + y;
                        var id = data.trainListDTO[i].id;
                        var trainTypeType = data.trainListDTO[i].trainTypeType;
                        var trainTypeId = data.trainListDTO[i].trainTypeId;
                        var pathId = data.trainListDTO[i].pathId;
                        var freeSeats = data.trainListDTO[i].freeSeats;
                        tableBody = tableBody +
                                "<tr>\n\
                                                    <td>" + id + "</td>\n\
                                                    <td><a class=\"show-popup\" href=\"/railwayWeb/trains/describeTrainType/" + trainTypeId + "\">" + trainTypeType + "</a></td>\n\
                                                    <td><a class=\"show-popup\" href=\"/railwayWeb/trains/describePath/" + pathId + "\">" + pathId + "</a></td>\n\
                                                    <td>" + newDate + "</td>\n\
                                                    <td>" + freeSeats + "</td>\n\
                                                    <td><a class=\"show-popup\" href=\"/railwayWeb/tickets/orderTrain/" + id + "\">Buy ticket</a></td>\n\
                                                </tr>"
                    }
                    $("#found-trains-table").html(tableHeader + tableBody);
                    $('.show-popup').bind('click', showPoppup);
                    if (data.msgBox.msg !== null)
                        $(".operation-msg").html(data.msgBox.msg);
                }
            }
        });
    })

    function showPoppup(event) {
        event.preventDefault();
        var uri = this.toString();
        var id = uri.substring(uri.lastIndexOf("/") + 1);
        $.ajax({
            url: this,
            type: 'GET',
            success: function(data) {
                if (uri.indexOf("describeTrainType") != -1) {
                    overlayTrainTypes(data, id);
                }
                if (uri.indexOf("describePath") != -1) {
                    overlayPaths(data, id);
                }
                if (uri.indexOf("orderTrain") != -1) {
                    overlayTicket(data, id);
                }
            }
        });
        var docHeight = $(document).height();
        var scrollTop = $(window).scrollTop(); //grab the px value from the top of the page to where you're scrolling      
        $('.overlay-bg').show().css({'height': docHeight}); //display your popup and set height to the page height
        $('.overlay-content').css({'top': scrollTop + 20 + 'px'}); //set the content 20px from the window top  
    }

    $('.close-btn').click(function() {
        $(".overlay-header").html('');
        $(".overlay-table").html('');
        $(".operation-msg").html('');
        $(".operation-error").html('');
        $('.overlay-bg').hide(); // hide the overlay
    });

    $('.overlay-bg').click(function() {
        $(".overlay-header").html('');
        $(".overlay-table").html('');
        $(".operation-msg").html('');
        $(".operation-error").html('');
        $('.overlay-bg').hide();
    })

    $('.overlay-content').click(function() {
        return false;
    });
});

function overlayTrainTypes(data, id) {
    var overlayHeader = "<h3>Train type: " + id + "</h3>";
    var tableHeader = "<tr><th width=\"100px\">id</th>\n\
                                              <th width=\"200px\">Type</th>\n\
                                              <th width=\"200px\">Cost multiplier</th>\n\
                                              <th width=\"200px\">Max seats</th>\n";
    var tableBody = "<tr><td>" + data.id + "</td>\n"
            + "<td>" + data.type + "</td>\n"
            + "<td>" + data.costMultiplier + "</td>\n"
            + "<td>" + data.maxSeats + "</td></tr>";
    $("#overlay-header").html(overlayHeader);
    $("#overlay-table").html(tableHeader + tableBody);
}

function overlayPaths(data, id) {
    var overlayHeader = "<h3>Path: " + id + "</h3>";
    var tableHeader = "<tr><th width=\"10%\">Number</th>\n\
                                <th width=\"20%\">Station</th>\n\
                                <th width=\"20%\">Time</th></tr>\n";
    var tableBody = ''
    for (var i in data) {
        var time = new Date(data[i].time);
        var h = time.getHours();
        var m = time.getMinutes();
        var tableBody = tableBody +
                "<tr><td>" + data[i].number + "</td>\n\
                             <td>" + data[i].stationName + "</td>\n\
                             <td>" + h + ":" + m + "</td></tr>";
    }
    $("#overlay-header").html(overlayHeader);
    $("#overlay-table").html(tableHeader + tableBody);
}

function overlayTicket(data, id) {
    var overlayHeader = "<h3>Please, fill the information about passenger:</h3>";

    var tableBody = "<tr><td>Train: </td><td id=\"ticket-train\">" + id + "</td></tr>\n\
                                 <tr><td id=\"ticket-name\">Name: </td><td><input id=\"name\" type=\"text\"/></td></tr>\n\
                                 <tr><td id=\"ticket-sec-name\">Second name: </td><td><input id=\"second-name\" type=\"text\"/></td></tr>\n\
                                 <tr><td id=\"ticket-passport\">Passport: </td><td><input id=\"passport\" type=\"text\"/></td></tr>\n\
                                 <tr><td colspan=\"2\"><a id=\"ajaxlink\" href=\"/railwayWeb/tickets/buyTicket\" \n\
                                onclick=\"return false\">Buy ticket</a></td></tr>"
    $("#overlay-header").html(overlayHeader);
    $("#overlay-table").html(tableBody);
    $('#ajaxlink').bind('click', processBuying);
}

function setAutocomplete(data) {
    $(".tags").autocomplete({
        source: data
    });
}

function processBuying() {
    var trainId = $("#ticket-train").text();
    var name = $("#name").val();
    var secondName = $("#second-name").val();
    var passport = $("#passport").val();
    $.ajax({
        url: this,
        type: 'POST',
        data: ({
            trainId: trainId,
            name: name,
            secondName: secondName,
            passport: passport
        }),
        success: function(data) {
            $(".operation-msg").html(data.msg);
            $(".operation-error").html(data.error);
        }
    });
}
