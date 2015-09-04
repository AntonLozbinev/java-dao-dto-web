<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>

<html>
<head>
    <title>Request result</title>
</head>
<body>
    <div align=center>
        <%! String result; List<Object> objects; Object object;%>
        <% result = (String) request.getAttribute("result");
            objects = (List<Object>) request.getAttribute("objects");
            object = request.getAttribute("object");
            if (result != null) {
                out.println("<p>" + result + "</p>");
            }
            if (object != null) {
                out.println("<p>" + object.toString() + "</p>");
            }
            if (objects != null) {
                for (Object object : objects) {
                    out.println("<p>" + object.toString() + "</p>");
                }
            }
        %>
        <a href='../index.jsp'>На главную</a>
    </div>
</body>
</html>
