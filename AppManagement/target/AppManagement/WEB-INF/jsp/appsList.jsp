<%--
  Created by IntelliJ IDEA.
  User: 13698
  Date: 2019/12/27
  Time: 14:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>appsList</title>
    <script type="text/javascript" src="${pageContext.request.contextPath}/statics/js/jquery.min.js"></script>
    <script type="text/javascript">
        $(function () {
            $('p>a').click(function () {
                var pageNow = $(this).attr('data');
                $('#pageNow').val(pageNow);
                $('form').submit();
            })
        })
    </script>
</head>
<body>
<form action="selectAppsByPage.do" method="post">
    <label>名字</label>
    <input type="text" name="softwareName" value="${param.softwareName}">
    <label>语言</label>
    <select name="interfacelanguage">
        <option value="-1">--请选择--</option>
            <option value="java"
                    <c:if test="${param.interfacelanguage == 'java'}">
                        selected="selected"
                    </c:if>
            >java</option>
            <option value="c++"
                    <c:if test="${param.interfacelanguage == 'c++'}">
                        selected="selected"
                    </c:if>
            >c++</option>
            <option value="c"
                    <c:if test="${param.interfacelanguage == 'c'}">
                        selected="selected"
                    </c:if>
            >c</option>
        </select>
        <input type="hidden" value="1" name="pageNow" id="pageNow">
        <input type="submit" value="查询">
</form>
<br>
<table border="1" style="width: 80%">
    <tr>
        <td>app编号</td>
        <td>app名称</td>
        <td>app语言</td>
        <td>app大小</td>
    </tr>
    <c:forEach items="${pageInfo.list}" var="app">
        <tr>
            <td>${app.id}</td>
            <td>${app.softwareName}</td>
            <td>${app.interfacelanguage}</td>
            <td>${app.softwareSize}</td>
        </tr>
    </c:forEach>
</table>
<p>
    <a data="1" href="javascript:void(0);">首页</a>
    <a data="${pageInfo.pageNow-1 == 0 ? 1 : pageInfo.pageNow-1 }" href="javascript:void(0);">上一页</a>
    <span>${pageInfo.pageNow}/${pageInfo.totalPage}</span>
    <a data="${pageInfo.pageNow+1 > pageInfo.totalPage ? pageInfo.totalPage : pageInfo.pageNow+1}" href="javascript:void(0);">下一页</a>
    <a data="${pageInfo.totalPage}" href="javascript:void(0);">尾页</a>
</p>
</body>
</html>
