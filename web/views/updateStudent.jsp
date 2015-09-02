<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Student update form</title>
</head>
<body>
<form action='${pageContext.request.contextPath}/handler' method='POST'>
    <table align='center'>
        <caption>Заполните форму</caption>
        <tr>
            <td align='right' valign='top'>
                Student id:
            </td>
            <td>
                <input type='text' name='id'>
            </td>
        </tr>
        <tr>
            <td align='right' valign='top'>
                Name:
            </td>
            <td>
                <input type='text' name='firstName'>
            </td>
        </tr>
        <tr>
            <td align='right' valign='top'>
                Surname:
            </td>
            <td>
                <input type='text' name='secondName'>
            </td>
        </tr>
        <tr>
            <td align='right' valign='top'>
                Year:
            </td>
            <td>
                <input type='text' name='enterYear'>
            </td>
        </tr>
        <tr>
            <td align='center' colspan='2'>
                <input type='submit' value='Update'>
                <input type='hidden' value='stUpdate' name='request'>
            </td>
        </tr>
    </table>
</form>
</body>
</html>
