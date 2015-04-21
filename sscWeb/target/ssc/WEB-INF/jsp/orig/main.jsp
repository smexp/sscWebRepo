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
                    <li ><a class="flatbtn-blu menuButton" id="buttonDate">Данные</a></li>  
                    <li><a class="flatbtn-blu menuButton" id="buttonTools" href="#">Настройки</a></li>  
                    <li><a class="flatbtn-blu menuButton" id="buttonAbout" href="#">О программе</a></li>  
                </ul>
            </div>

            <div class="controlPanels" id="tabs" >
                <ul>
                    <li><a href="#tabs-1">Вакансии</a></li>
                    <li><a href="#tabs-2">Параметры процесса</a></li>
                </ul>
                <div id="tabs-1" >
                    <div class="wrap">
                        <table class="head">
                            <tr>

                                <td>Last Name</td> 
                                <td>First Name</td>
                                <td>Email</td>
                                <td>Due</td>
                                <td>Web Site</td>
                            </tr>
                        </table>

                        <div class="inner_table"> 
                            <%@include file = "dataTable.jsp" %>
                        </div>
                        <div class="pagination">
                            <a href="#" class="first" data-action="first">&laquo;</a>
                            <a href="#" class="previous" data-action="previous">&lsaquo;</a>
                            <input type="label" readonly="readonly" data-max-page="40" />
                            <a href="#" class="next" data-action="next">&rsaquo;</a>
                            <a href="#" class="last" data-action="last">&raquo;</a>
                        </div>
                    </div>
                </div>

                <div id="tabs-2">
                    <p>Morbi tincidunt, dui sit amet facilisis feugiat, odio metus gravida ante, ut pharetra massa metus id nunc. Duis scelerisque molestie turpis. Sed fringilla, massa eget luctus malesuada, metus eros molestie lectus, ut tempus eros massa ut dolor. Aenean aliquet fringilla sem. Suspendisse sed ligula in ligula suscipit aliquam. Praesent in eros vestibulum mi adipiscing adipiscing. Morbi facilisis. Curabitur ornare consequat nunc. Aenean vel metus. Ut posuere viverra nulla. Aliquam erat volutpat. Pellentesque convallis. Maecenas feugiat, tellus pellentesque pretium posuere, felis lorem euismod felis, eu ornare leo nisi vel felis. Mauris consectetur tortor et purus.</p>
                </div>

            </div>
        </div>   

        <script>

            $("#buttonDate").click(
                    function() {
                        $.ajax({
                            url: "jsn",
                            type: "GET",
                            cache: false,
                            dataType: "json",
                            success: function(json) {
                                $("#contentTable").empty();
                                for (var i = 0; i < json.length; i++) {

                                    var $row = "<tr><td>" + json[i].id + "</td> <td>" + json[i].name + "</td> <td>" + json[i].companyName + "</td> <td>" + json[i].salary + "</td> <td>" + new Date(json[i].createdate).toLocaleString() + "</td> </tr>";
                                    $("#contentTable").append($row);
                                }

                            },
                            error: function() {
                                alert("Error");
                            }

                        });
                    });

            $(".pagination").jqPagination({
                paged: function(page) {
                    alert(page);
                }
            });

            $(document).ready(function()
            {
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
