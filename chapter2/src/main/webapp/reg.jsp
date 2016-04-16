<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="BASE" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body>
<div>注册</div>
<form action="reg" method="GET">
    用户名：<input type="text" name="username" value="${user.username}"/><br/>
    密码：<input type="password" name="password" value="${user.password}"/><br/>
    <input type="submit" value="注册"/>
</form>
</body>
</html>
