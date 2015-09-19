<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>

<html>
<head>
    <title>Request result</title>
</head>
<body>
    <div align=center>
        <%! String result;%>
        <% result = (String) request.getAttribute("result");
            if (result != null) {
                out.println("<p>" + result + "</p>");
            }
        %>
        <a href='../index.jsp'>На главную</a>
    </div>
</body>
</html>
