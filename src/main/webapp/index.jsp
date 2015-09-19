<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
  <title>Students_DB</title>
    <style type='text/css'>
        .div1 {
            width: 100%;
        }
        #result {
            margin: 0 auto;
            width: 50%;
            height: auto;
            text-align: center;
        }
    </style>
    <script src="js/script.js"></script>
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
          <form action='${pageContext.request.contextPath}/handler' method='GET' id='student_show'>
              Enter id: <input type='text' name='id'>
              <input type='button' value='Show' onclick='sendRequest("student_show")'>
          </form>
        </td>
        <td>
          <form action='handler' method='GET' id='student_show-all'>
            <input type='button' value='Show all' onclick='sendRequest("student_show-all")'>
          </form>
        </td>
        <td>
          <form action='handler' method='GET' id='subject_show'>
            Enter id: <input type='text' name='id'>
            <input type='button' value='Show' onclick='sendRequest("subject_show")'>
          </form>
        </td>
        <td colspan='2'>
          <form action='handler' method='GET' id='subject_show-all'>
            Enter student id: <input type='text' name='id'>
            <input type='button' value='Show all' onclick='sendRequest("subject_show-all")'>
          </form>
        </td>
      </tr>
      <tr>
        <td>
          <form action='handler' method='GET' id='student_delete'>
            Enter id: <input type='text' name='id'>
            <input type='button' value='Delete' onclick='sendRequest("student_delete")'>
          </form>
        </td>
        <td>
          <form action='${pageContext.request.contextPath}/handler' method='GET'>
            <input type='submit' value='Update'>
            <input type='hidden' name='action' value='student_update'>
          </form>
        </td>
        <td align='center'>
          <form action='' method='GET' id='subject_delete'>
            Enter id: <input type='text' name='id'>
            <input type='button' value='Delete' onclick='sendRequest("subject_delete")'>
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
  <div id="result">
  </div>
</body>
</html>
