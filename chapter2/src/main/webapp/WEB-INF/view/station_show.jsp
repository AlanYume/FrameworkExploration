<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="BASE" value="${pageContext.request.contextPath}"/>

<html>
<head>
    <title>车站信息展示</title>
</head>
<body>

<h1>车站表</h1>

<table>
    <tr>
        <th>车站id</th>
        <th>车站名</th>
        <th>经过的列车名</th>
        <th>到站时间点</th>
    </tr>
    <c:forEach var="station" items="${stationList}">
        <tr>
            <td>${station.id}</td>
            <td>${station.stationName}</td>
            <td>${station.trainNameList}</td>
            <td>${station.trainTimeList}</td>
            <td>
                <a href="${BASE}/station_edit?id=${station.id}">编辑</a>
                <a href="${BASE}/station_delete?id=${station.id}">删除</a>
            </td>
        </tr>
    </c:forEach>
</table>
<div>查询列车（从始发站到目的地站）</div>
<form action="train">
    始发站：<input type="text" name="stationStart"/><br/>
    终点站：<input type="text" name="stationEnd"/><br/>
    <input type="submit" value="查询"/>
</form>
</body>
</html>