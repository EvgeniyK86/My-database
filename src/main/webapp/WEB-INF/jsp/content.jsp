<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Title</title>
</head>
<body>
<div>
    <span>CONTENT РУССКИЙ</span>
    <p>Size: ${requestScope.serviceStations.size()}</p>
    <p>Id: ${requestScope.serviceStations.get(0).id()}</p>
    <p>Id: ${requestScope.serviceStations[0].description()}</p>
    <p>JSESSIONID: ${cookie.get("JSESSIONID")}</p>
    <p>PARAM id: ${param.id}</p>
    <p>HEADER: ${header["cookie"]}</p>
    <p>IS NOT EMPTY: ${not empty serviceStations}</p>
</div>
</body>
</html>
