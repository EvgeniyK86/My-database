<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
    <title>Услуги</title>
</head>
<body>
<%@ include file="header.jsp"%>

<h1>Предоставляемые услуги: </h1>
<ul>
    <c:if test="${not empty requestScope.services}">
        <c:forEach var="service" items="${requestScope.services}">
            <li>${fn:toLowerCase(service.typeOfService)}</li>
        </c:forEach>
    </c:if>
</ul>
</body>
</html>
