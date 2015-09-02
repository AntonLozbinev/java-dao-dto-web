<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
  <title>Students_DB</title>
  <style type='text/css'>
    .div1 {
      width: 100%
    height: 100%;
    }
  </style>
</head>
<body>
  <div class='div1'>
    <table align='center'>
      <tr>
        <td colspan='2' align='center'>Students</td>
        <td colspan='3' align='center'>Subjects</td>
      </tr>
      <tr>
        <td>
          <form action='${pageContext.request.contextPath}/handler' method='GET'>
            Enter id: <input type='text' name='id'>
            <input type='submit' value='Show'>
            <input type='hidden' name='action' value='student_show'>
          </form>
        </td>
        <td>
          <form action='${pageContext.request.contextPath}/handler' method='GET'>
            <input type='submit' value='Show all'>
            <input type='hidden' name='action' value='student_show-all'>
          </form>
        </td>
        <td>
          <form action='${pageContext.request.contextPath}/handler' method='GET'>
            Enter id: <input type='text' name='id'>
            <input type='submit' value='Show'>
            <input type='hidden' name='action' value='subject_show'>
          </form>
        </td>
        <td colspan='2'>
          <form action='${pageContext.request.contextPath}/handler' method='GET'>
            Enter student id: <input type='text' name='id'>
            <input type='submit' value='Show all'>
            <input type='hidden' name='action' value='subject_show-all'>
          </form>
        </td>
      </tr>
      <tr>
        <td>
          <form action='${pageContext.request.contextPath}/handler' method='GET'>
            Enter id: <input type='text' name='id'>
            <input type='submit' value='Delete'>
            <input type='hidden' name='action' value='student_delete'>
          </form>
        </td>
        <td>
          <form action='${pageContext.request.contextPath}/handler' method='GET'>
            <input type='submit' value='Update'>
            <input type='hidden' name='action' value='student_update'>
          </form>
        </td>
        <td align='center'>
          <form action='${pageContext.request.contextPath}/handler' method='GET'>
            Enter id: <input type='text' name='id'>
            <input type='submit' value='Delete'>
            <input type='hidden' name='action' value='subject_delete'>
          </form>
        </td>
        <td align='center'>
          <form action='${pageContext.request.contextPath}/handler' method='GET'>
            <input type='submit' value='Update'>
            <input type='hidden' name='action' value='subject_update'>
          </form>
        </td>
        <td align='center'>
          <form action='${pageContext.request.contextPath}/handler' method='GET'>
            <input type='submit' value='Create'>
            <input type='hidden' name='action' value='subject_create'>
          </form>
        </td>
      </tr>
    </table>
  </div>
</body>
</html>
