<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <!--<link rel="stylesheet" type="text/css" media="all" href="${pageContext.request.contextPath}/static/css/jquery-ui.theme.min.css">-->


    <%--<link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">--%>
    <script src="${pageContext.request.contextPath}/static/js/jquery-1.11.0.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/jquery-ui-1.10.3.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/jquery.jqpagination.js"></script>


    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/jquery-ui.css">
    <link rel="stylesheet" type="text/css" media="all" href="${pageContext.request.contextPath}/static/css/style.css">
    <link rel="stylesheet" type="text/css" media="all" href="${pageContext.request.contextPath}/static/css/main.css">
    <link rel="stylesheet" type="text/css" media="all" href="${pageContext.request.contextPath}/static/css/jqpagination.css">
    <!--<script src="${pageContext.request.contextPath}/static/js/jquery.tablesorter.min.js"></script>-->


    <title>Главная страница</title>
    <script>
        $(function() {
            $("#tabs").tabs();
        });
        $(function() {
            $("#radio").buttonset();
        });

    </script>
</head>


<body>

<%--top_element--%>
<div id="topbar">
    <label id="loginInfo">Пользователь: <sec:authorize access="isAuthenticated()"><sec:authentication property="principal.username" /></sec:authorize></label>
    <a id="logout" class="flatbtn" href="${pageContext.request.contextPath}/logout">Выход</a>
</div>

<%--central_element--%>
<div id="centralbar">

    <%--left_panel--%>
    <div class="controlPanels leftControl">
        <h1 class="titlePanel">Управление:</h1>
        <ul>
            <li><a class="flatbtn-blu menuButton" id="buttonDate" href="${pageContext.request.contextPath}/main">Данные</a></li>
            <li><a class="flatbtn-blu menuButton" id="buttonTools" href="#">Настройки</a></li>
            <li><a class="flatbtn-blu menuButton" id="buttonAbout">Новый сканнер</a></li>
            <li><a class="flatbtn-blu menuButton" id="buttonState">Состояние</a></li>
        </ul>
    </div>

    <%--central_panel--%>
    <div class="controlPanels" id="tabs" >

        <%--central_tabs--%>
        <ul>
            <li><a href="#tabs-1">Вакансии</a></li>
            <li><a href="#tabs-2">Параметры процессов</a></li>
            <li><a href="#tabs-3">Дополнительная вкладка</a><span class="ui-icon ui-icon-close" role="presentation">Remove Tab</span></li>
        </ul>

        <%--tabs_body--%>

        <%--Tab1_general_info--%>
        <div id="tabs-1" >
            <div class="wrap">

                <%--head_table--%>
                <table class="head" id="sortTableHead">
                    <tr>

                        <td id="name">Название
                            <span id="picSort"></span>
                        </td>

                        <td id="companyName">Компания
                            <span id="picSort"></span>
                        </td>

                        <td id="salary">Зарплата
                            <span id="picSort"></span>
                        </td>

                        <td id="version">Кол-во обновлений
                            <span id="picSort"></span>
                        </td>

                        <td id="updatedate">Дата обновления
                            <span id="picSort"><img src="${pageContext.request.contextPath}/static/images/desc.gif"></span>
                        </td>
                    </tr>
                </table>

                <%--body_table--%>
                <div class="inner_table" id="inner_table">
                    <%@include file = "dataTable.jsp" %>
                </div>

                <%--pagination--%>
                <div class="pagination">
                    <a href="#" class="first" data-action="first">&laquo;</a>
                    <a href="#" class="previous" data-action="previous">&lsaquo;</a>
                    <input type="label" readonly="readonly" data-max-page="${maxPage}" />
                    <a href="#" class="next" data-action="next">&rsaquo;</a>
                    <a href="#" class="last" data-action="last">&raquo;</a>
                </div>

            </div>
        </div>

        <%--Tab2_system_info--%>
        <div id="tabs-2">
            <div class="wrap">

                <%--additional_info_table--%>
                <table class="scann_info_header">
                    <tr>
                        <td>Сканнеры текущего пользователя:</td>
                        <td><a id="newThread" class="flatbtn-gray" href="${pageContext.request.contextPath}/logout">Запустить новый процесс</a></td>
                    </tr>
                </table>

                <%--head_table--%>
                <table class="head">
                    <tr>
                        <td id="name">Имя потока</td>
                        <td id="searchWord">Состояние</td>
                        <td id="dateStart">Дата запуска</td>
                        <td id="version">Кол-во обновлений</td>
                        <td id="triggeredTime">Время срабатывания</td>
                    </tr>
                </table>

                <%--body_table--%>
                <div class="inner_table" >
                    <table id="scannersInfo">
                    </table>
                </div>

            </div>
        </div>

    </div>
</div>

<%--hidding_dialog_about_vacantion--%>
<div id="dialog" title="Информация о вакансии">
    <p> </p>
</div>

<%--java_scripts--%>
<script>
    // global variables
    var sorting = "updatedate";
    var oldSorting = "updatedate";      //last pressed button
    var typeSorting = "desc";
    var tabs = $( "#tabs" ).tabs();
    var tabCounter=4;

    var page_table = function(page) {
        getInfo(page, sorting, typeSorting);
    };

    //-------------------------------
    function getInfo(page, sorting, typeSorting) {
        $.ajax({
            url: "jsn",
            type: "GET",
            cache: false,
            data: {page: page, limit: '15', sorting: sorting, typeSorting: typeSorting},
            dataType: "json",
            success: function(json) {
                $("#contentTable").empty();
                for (var i = 0; i < json.length; i++) {

                    var $row = "<tr id=\"" + json[i].id + "\"><td > " + json[i].name + "</td> <td>" + json[i].companyName + "</td> <td>" + json[i].salary + "</td> <td>" + json[i].version + "</td> <td>" + new Date(json[i].updatedate).toLocaleString() + "</td></tr>";
                    $("#contentTable").append($row);
                }

            },
            error: function() {
                alert("Error");
            }

        });
    }
    //-------------------------------
    // close icon: removing the tab on click
    tabs.delegate( "span.ui-icon-close", "click", function() {
        var panelId = $( this ).closest( "li" ).remove().attr( "aria-controls" );
//        $( "#" + panelId ).remove();
        tabs.tabs( "refresh" );
    });


    //-------------------------------
    // add_new_scanner_function
    function addThread(name, userName) {
        $.ajax({
            url: "addThread",
            type: "GET",
            cache: false,
            data: {name: name, userName: userName},
            dataType: "json",
            success: function(json) {
                alert("Success jsn " + json.userName + " state=" + json.state);


            },
            error: function() {
                alert("Error add thread");
            }

        });
    }

    //-------------------------------------------
    // get_info_about_running_scanners_function
    function getCurrentThreads(name) {
        $.ajax({
            url: "getThreads",
            type: "GET",
            cache: false,
            data: {name: name},
            dataType: "json",
            success: function(json) {
                if (json.message == "None") {
                    alert("Ни одного сканера не запущено");
                }
                if (json.message == "Success") {
                    $("#scannersInfo").empty();
                    for (var i = 0; i < json.scanners.length; i++) {

                        var $row = "<tr id=\"" + json.scanners[i].name + "\"><td > " + json.scanners[i].name + " </td> <td> " + json.scanners[i].state + " </td><td> " + json.scanners[i].startTime + " </td><td> число " + json.scanners[i].count + " </td><td> " + json.scanners[i].triggeredTime + " </td></tr>";
                        $("#scannersInfo").append($row);
                    }
                    //alert("Запущен сканнер имя=" + json.scanners[0].name + " состояние " + json.scanners[0].state);
                }
                ;

            },
            error: function() {
                alert("Error return thread");
            }

        });
    }

    //-------------------------------------------

    //-------------------------------------------
    // click_on_buttonTools
    $("#buttonTools").click(function addTab(){
        //иницализируем вкладку

        var     id = "tabs-" + tabCounter,
                li = "<li><a href=\"#"+id+"\">"+id+"</a><span class=\"ui-icon ui-icon-close\" role=\"presentation\">Remove Tab</span></li>",
                tabContentHtml = "<p>HELLO</p>";

//добавление вкладки на форму

        $("#tabs").find("ul").append( li );
        $( "#tabs").append( "<div id=\"" + id + "\"><div class=\"wrap\"><p></p></div></div>" );
//         $( "#"+id).find(".wrap").append(tabContentHtml+"<p>"+id+"</p>"); //сдвигает нумератор
        tabs.tabs( "refresh" );

        tabCounter++;
    })

    //-------------------------------------------

    // events

    //------------------------------------------
    // update_info_event
    $("#buttonDate").click(getInfo(1, 'updatedate', 'desc'));

    //------------------------------------------
    // click_on_sort_record_event
    $("#sortTableHead tr td").click(
            function(page) {
                var selector;
                sorting = $(this).attr("id");
                if (oldSorting.toString() === sorting.toString())
                {

                    if (typeSorting.toString() === "desc") {
                        typeSorting = "asc";
                        selector = $('#sortTableHead').find("td#" + sorting).find('span#picSort');
                        selector.find('img').detach();
                        selector.append("<img src=\"${pageContext.request.contextPath}/static/images/asc.gif\">");
                    } else {
                        typeSorting = "desc";
                        selector = $('#sortTableHead').find("td#" + sorting).find('span#picSort');
                        selector.find('img').detach();
                        selector.append("<img src=\"${pageContext.request.contextPath}/static/images/desc.gif\">");
                    }
                }
                else
                {
                    $('#sortTableHead').find("td#" + oldSorting).find('span#picSort').find('img').detach();
                    oldSorting = sorting;

                    typeSorting = "desc";
                    $('#sortTableHead').find("td#" + sorting).find('span#picSort').append("<img src=\"${pageContext.request.contextPath}/static/images/desc.gif\">");
                }

                $('.pagination').jqPagination('option', 'current_page', 1);

            }
    );

    //------------------------------------------
    // open_record_event
    $("#inner_table").on("click", "#contentTable tr", function() {
                //alert("Info about vacancy " + $(this).attr("id"));
                var infoVacNum = $(this).attr("id");
                $("#dialog").children().remove();

        $.ajax({
                    url: "https://api.hh.ru/vacancies/"+infoVacNum,
                    type: "GET",
                    cache: false,
                    data: {},
                    dataType: "json",
                    success: function(json) {
                        //alert("success");
                        var sal;
                        if (json.salary == null){sal = "не указана"} else {sal = "от "+json.salary.from+" до "+json.salary.to+" "+json.salary.currency };
                        $("#dialog").append("<p>Вакансия "+infoVacNum+" "+json.name+"</p> <p>Компания: "+json.employer.name+" </p><p>Зарплата: "+sal+"</p>");
                        $("#dialog").append(json.description);
                        $( "#dialog" ).dialog({
                            resizable: false,
                            height:440,
                            width: 900,
                            modal: true});

                },
                error: function() {
            alert("Error");
        }

    });


            }
    );

    //-----------------------------------------
    // display_paging
    $(".pagination").jqPagination({
        paged: page_table,
        page_string: 'Страница {current_page} из {max_page}'
    });

    //------------------------------------------
    // add_new_scanner_event
    $("#buttonAbout").on("click", function() {
        addThread("JavaThread", "haruba");
    });


    //-----------------------------------------
    // get_info_about_running_scanners_event
    $("#buttonState").on("click", function() {
        getCurrentThreads("haruba");
    });
    //-----------------------------------------


    $(document).ready(function()
            {
                $("#buttonDate").click(getInfo(1, 'updatedate', 'desc'));

            }
    );
</script>


</body>


</html>
