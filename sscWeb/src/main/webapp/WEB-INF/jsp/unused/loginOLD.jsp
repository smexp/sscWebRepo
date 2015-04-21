
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/jquery-ui.css">
        <script src="${pageContext.request.contextPath}/static/js/jquery-1.11.0.min.js"></script>
        <script src="${pageContext.request.contextPath}/static/js/jquery-ui-1.10.3.min.js"></script>
        <!--<link rel="stylesheet" href="/resources/demos/style.css">-->
        <script>
            $(function() {
                $("#dialog").dialog();
            });
        </script>
    </head>
    <body>
        <div id="dialog" title="Basic dialog">
            <p>This is the default dialog which is useful for displaying information. The dialog window can be moved, resized and closed with the 'x' icon.</p>
        </div>
    </body>
</html>
