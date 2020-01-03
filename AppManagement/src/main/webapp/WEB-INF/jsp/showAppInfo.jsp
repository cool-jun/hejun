<%--
  Created by IntelliJ IDEA.
  User: 13698
  Date: 2019/12/26
  Time: 20:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>show</title>
</head>
<body>
<div>
    <table border="1">
        <tr>
            <td>app编号</td>
            <td>app名称</td>
            <td>app语言</td>
            <td>app大小</td>
        </tr>
        <c:forEach items="${sessionScope.allAppInfo}" var="app">
            <tr>
                <td>${app.id}</td>
                <td>${app.softwareName}</td>
                <td>${app.interfacelanguage}</td>
                <td>${app.softwareSize}</td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>
