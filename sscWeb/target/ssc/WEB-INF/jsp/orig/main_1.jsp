<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <!--<link rel="stylesheet" type="text/css" media="all" href="${pageContext.request.contextPath}/static/css/jquery-ui.theme.min.css">-->

        <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/jquery-ui.css">
        <script src="${pageContext.request.contextPath}/static/js/jquery-1.11.0.min.js"></script>
        <script src="${pageContext.request.contextPath}/static/js/jquery-ui-1.10.3.min.js"></script>
        <script src="${pageContext.request.contextPath}/static/js/jquery.jqpagination.js"></script>

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
        <div id="topbar">

            <label id="loginInfo">Пользователь: <sec:authorize access="isAuthenticated()"><sec:authentication property="principal.username" /></sec:authorize></label>
            <a id="logout" class="flatbtn" href="${pageContext.request.contextPath}/logout">Выход</a>
        </div>
        <div id="centralbar"> 

            <div class="controlPanels leftControl">
                <h1 class="titlePanel">Управление:</h1>
                <ul>  
                    <li><a class="flatbtn-blu menuButton" id="buttonDate" href="${pageContext.request.contextPath}/main">Данные</a></li>  
                    <li><a class="flatbtn-blu menuButton" id="buttonTools" href="#">Настройки</a></li>  
                    <li><a class="flatbtn-blu menuButton" id="buttonAbout" href="#">О программе</a></li>  
                </ul>
            </div>

            <div class="controlPanels" id="tabs" >
                <ul>
                    <li><a href="#tabs-1">Вакансии</a></li>
                    <li><a href="#tabs-2">Параметры процессов</a></li>
                </ul>
                <div id="tabs-1" >
                    <div class="wrap">
                        <table class="head">
                            <tr>

                                <td id="name">Название</td> 
                                <td id="companyName">Компания</td>
                                <td id="salary">Зарплата</td>
                                <td id="version">Кол-во обновлений</td>
                                <td id="updatedate">Дата обновления</td>
                            </tr>
                        </table>

                        <div class="inner_table"> 
                            <%@include file = "dataTable.jsp" %>
                        </div>
                        <div class="pagination">
                            <a href="#" class="first" data-action="first">&laquo;</a>
                            <a href="#" class="previous" data-action="previous">&lsaquo;</a>
                            <input type="label" readonly="readonly" data-max-page="${maxPage}" />
                            <a href="#" class="next" data-action="next">&rsaquo;</a>
                            <a href="#" class="last" data-action="last">&raquo;</a>
                        </div>
                    </div>
                </div>

                <div id="tabs-2">
                    <div class="wrap">
                        <tabe>
                        <tr>
                            <td>Запущенные процессы текущего пользователя:</td>
                            <td><a id="newThread" class="flatbtn-gray" href="${pageContext.request.contextPath}/logout">Запустить новый процесс</a></td>
                        </tr>
                        </table>
                        <table class="head">
                            <tr>

                                <td id="name">Имя потока</td> 
                                <td id="searchWord">Выражение поиска</td>
                                <td id="dateStart">Дата запуска</td>
                                <td id="version">Кол-во обновлений</td>
                                <td id="version">Время последнего срабатывания</td>
                            </tr>
                        </table>

                        <div class="inner_table"> 
                            <%@include file = "dataTable.jsp" %>
                        </div>
                    </div>
                </div>

            </div>
        </div>   

        <script>

            var page_table = function(page) {
                getInfo(page, sorting);
            };

            function getInfo(page, sorting) {
                $.ajax({
                    url: "jsn",
                    type: "GET",
                    cache: false,
                    data: {page: page, limit: '15', sorting: sorting},
                    dataType: "json",
                    success: function(json) {
                        $("#contentTable").empty();
                        for (var i = 0; i < json.length; i++) {

                            var $row = "<tr><td> " + json[i].name + "</td> <td>" + json[i].companyName + "</td> <td>" + json[i].salary + "</td> <td>" + json[i].version + "</td> <td>" + new Date(json[i].updatedate).toLocaleString() + "</td></tr>";
                            $("#contentTable").append($row);
                        }

                    },
                    error: function() {
                        alert("Error");
                    }

                });
            }

            $("#buttonDate").click(getInfo(1, 'updatedate'));

            $(".head tr td").click(
                    function(page) {
                        sorting = $(this).attr("id");
                        alert(sorting);
                        $('.pagination').jqPagination('option', 'current_page', 1)
                        //page_table(1);
                        //getInfo(1, sorting);
                        //$('.pagination').jqPagination({paged: 1});
                    }
            );

            $(".pagination").jqPagination({
                paged: page_table,
                page_string: 'Страница {current_page} из {max_page}'
            });
            
            $(document).ready(function()
            {
                sorting = "updatedate";
                //alert("ready");
                $('#contentTable tr').click(function() {
                    var href = $(this).find("a").attr("href");
                    if (href) {
//                        window.location = href;
                        window.open(href);
                    }
                });

            }
            );
        </script>


    </body>


</html>
