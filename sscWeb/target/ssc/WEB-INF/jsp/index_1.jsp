<%-- 
    Document   : index_2
    Created on : Aug 22, 2014, 5:57:18 PM
    Author     : yurys
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
 <head>
        <%@include file = "parts/includes.jsp" %>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <title>Train service</title>
    </head>

    <body> 
        <div class="wrapper">
            <%@include file = "parts/header.jsp" %>
            <div class="middle">
                <%@include file = "parts/sidebar.jsp" %>
                <div class="container">
                    <h2>Хеллоу Java : SBB/CFF/FFS</h2><br/>
                    <table>
                        <tr>
                            <td>
                                <h3>Swiss Federal Railways</h3> 
                                <h3>(SBB in German, CFF in French, FFS - Italian)</h3>
                                <p>Попытка вывести список</p>
                                <p>${sessionFactory}</p>
                                <c:forEach items="${vacancyList}" var="valueString">
                                    <tr>
                                        <td>${valueString.getName()}</td>
                                    </tr>
                                </c:forEach>
                        
                            </td>
                            <td>
                                <img src="${pageContext.request.contextPath}/static/images/welcome-train.jpg">
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
        </div>
        <%@include file = "parts/footer.jsp" %>
    </body>

</html>
