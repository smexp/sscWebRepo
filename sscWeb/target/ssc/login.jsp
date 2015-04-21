
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login to JSC</title>
        
        <link rel="shortcut icon" href="${pageContext.request.contextPath}/static/images/favicon.ico">
        <link rel="icon" href="${pageContext.request.contextPath}/static/images/favicon.ico">
        
        <link rel="stylesheet" type="text/css" media="all" href="${pageContext.request.contextPath}/static/css/style.css">
        <link rel="stylesheet" type="text/css" media="all" href="${pageContext.request.contextPath}/static/css/login.css">
        
        <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery-1.11.0.min.js"></script>
        <script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/static/js/jquery.leanModal.min.js"></script>

    </head>
    <body>
        <script type="text/javascript">var disp = "true"; </script>
        <div id="topbar">
        </div>
        <div id="w">
            <div id="content">
                <h1>Добро пожаловать!</h1>
                <p>Данный сервис позволяет выполнять автоматическое периодическое сканирование 
                    публикуемых вакансий с заданными параметрами поиска на популярном сайте hh.ru</p>
                <center><a href="#loginmodal" class="flatbtn" id="modaltrigger">Войти в систему</a></center>
            </div>
        </div>

        <div class="container">
            <div id="loginmodal" style="display:none;">
                <h1>Вход в систему</h1>
                <form id="loginform" class="form-signin" name="loginform" method="POST" action="<c:url value="/j_spring_security_check" />"> 
                    <p id="errorLoginMessage"><c:if test="${not empty param.error}">Ошибка входа: Неверный логин или пароль</c:if> </p>   
                    <label for="username">Имя пользователя:</label>
                    <input type="text" name="j_username" id="username" class="txtfield" tabindex="1">
                    <label for="password">Пароль:</label>
                    <input type="password" name="j_password" id="password" class="txtfield" tabindex="2">
                    <div class="center"><input type="submit" name="loginbtn" id="loginbtn" class="flatbtn-blu hidemodal" value="ВХОД" tabindex="3"></div>
                </form>
             </div>
        </div>

            <script type="text/javascript">
                $(function() {
                    $('#modaltrigger').leanModal({top: 110, overlay: 0.45, closeButton: ".hidemodal"});
                });

                $(document).ready(
                        function() {
                            $('#modaltrigger').leanModal({top: 90, overlay: 0.45});
            <c:if test="${not empty param.error}">
                            $('#modaltrigger').click();
            </c:if>

                        }
                );
        </script>
    </body>
</html>
