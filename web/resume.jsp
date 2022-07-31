<%--
  Created by IntelliJ IDEA.
  User: aleksandrvinokurov
  Date: 31.07.2022
  Time: 11:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Resume</title>
</head>
<body>

<h1>Resumes</h1>

<table style="border: 1px solid black">
    <tr style="background: darkseagreen">
        <th>uuid</th>
        <th>full name</th>
    </tr>
    <c:forEach items="${resumes}" var="resumesList">
        <tr><th>${resumesList.uuid}</th>
        <th>${resumesList.fullName}</th></tr>
    </c:forEach>
</table>



</body>
</html>
