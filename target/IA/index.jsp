<%--
  Created by IntelliJ IDEA.
  User: zhiban
  Date: 2020-1-19
  Time: 13:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<head>
    <title>IAV2测试</title>
</head>
<body>

Welcome:<shiro:principal>
    <a href="log/findByPid">模糊查询str</a>

</shiro:principal>



<shiro:hasRole name="admin">
    <br><br>
    <a href="admin.jsp">admin</a>
</shiro:hasRole>
<shiro:hasRole name="user">
    <br><br>
    <a href="user.jsp">user</a>
</shiro:hasRole>
<br><br>
<a href="shiro/logout">注销登陆</a>
<br><br>
<a href="shiro/testShiroAnnotation">test shiroannotation</a>
</body>
</html>
