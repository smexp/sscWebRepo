

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <title>Modal Login Window Demo</title>
        <link rel="shortcut icon" href="${pageContext.request.contextPath}/static/images/favicon.ico">
        <link rel="icon" href="${pageContext.request.contextPath}/static/images/favicon.ico">
        <link rel="stylesheet" type="text/css" media="all" href="${pageContext.request.contextPath}/static/css/bootstrap.css">
        <link rel="stylesheet" type="text/css" media="all" href="${pageContext.request.contextPath}/static/css/style_1.css">
        <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery-1.11.0.min.js"></script>
        <script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/static/js/jquery.leanModal.min.js"></script>
        <!-- jQuery plugin leanModal under MIT License http://leanmodal.finelysliced.com.au/ -->
    </head>

    <body>
        <div class="container-fluid">
            <div>
                <h1></h1>
            </div>
            <div id="w">
                <div id="content">
                    <h1>Добро пожаловать!</h1>
                    <p>Just click the login link below to expand the modal window. This is only a demo so the input form will not load anything, but it is easy to connect into a backend system.</p>
                    <center><a href="#loginmodal" class="btn btn-primary" id="modaltrigger">Войти в систему</a></center>
                </div>
            </div>
        </div>


        <div id="loginmodal" style="display:none;">
            <h1>User Login</h1>
            <form id="loginform" class="form-horizontal" name="loginform" method="post" action="${pageContext.request.contextPath}/registration/newUser">
                <label for="username">Username:</label>
                <input type="text" name="username" id="username" class="txtfield" tabindex="1">

                <label for="password">Password:</label>
                <input type="password" name="password" id="password" class="txtfield" tabindex="2">

                <div class="center"><input type="submit" name="loginbtn" id="loginbtn" onclick="location.href='${pageContext.request.contextPath}/login'" class="btn btn-primary" value="ВХОД" tabindex="3"></div>
            </form>
        </div>
        <script type="text/javascript">
            $(function() {
                $('#loginform').submit(function(e) {
                    return false;
                });

                $('#modaltrigger').leanModal({top: 110, overlay: 0.45, closeButton: ".hidemodal"});
            });
        </script>
    </body>
</html>
