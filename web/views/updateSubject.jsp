<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Subject update form</title>
</head>
<body>
<form action='${pageContext.request.contextPath}/handler' method='POST'>
    <table align='center'>
        <caption>Заполните форму</caption>
        <tr>
            <td align='right' valign='top'>
                Subject id:
            </td>
            <td>
                <input type='text' name='id'>
            </td>
        </tr>
        <tr>
            <td align='right' valign='top'>
                Student id:
            </td>
            <td>
                <input type='text' name='st_id'>
            </td>
        </tr>
        <tr>
            <td align='right' valign='top'>
                Title id:
            </td>
            <td>
                <input type='text' name='title'>
            </td>
        </tr>
        <tr>
            <td align='center' colspan='2'>
                <input type='submit' value='Update'>
                <input type='hidden' value='subUpdate' name='request'>
            </td>
        </tr>
    </table>
</form>
</body>
</html>