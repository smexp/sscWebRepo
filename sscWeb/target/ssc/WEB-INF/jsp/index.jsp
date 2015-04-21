<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <%@include file = "parts/includes.jsp" %>
        <meta http-equiv="Content-Type" content="text/html; charset=utf8">
        <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/main.js"></script>
        <title>Home page</title>
    </head>

    <body> 
        <div class="wrapper">
            <%@include file = "parts/header.jsp" %>
            <div class="middle">
                <%@include file = "parts/sidebar.jsp" %>
                <div class="container">
                    <h2>Hello from HHScanner</h2><br/>
                    <table>
                        <tr>
                            <td>
                                <h3>Get ready to action</h3> 
                                <p>Press the Button</p>
                        
                            </td>
                            <td>
                                <!--<img src="${pageContext.request.contextPath}/static/images/welcome-train.jpg">-->
                                <p><input id="enterButton" class="btn btn-primary" type="button" value="Enter"></p>
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
        </div>
        <%@include file = "parts/footer.jsp" %>
    </body>

</html>
