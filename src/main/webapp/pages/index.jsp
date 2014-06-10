<%--
  Created by IntelliJ IDEA.
  User: bardl
  Date: 15.05.2014
  Time: 07:41
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Statistics and Value Reporting</title>
</head>
<body>
    <h3>Observations</h3>
    <li><a href="./observe/observedmethods/{prefix}/{name}">template listing of operations (./observe/observedmethods/{prefix}/{name})</a></li>
    <b>receipt-control-test</b>
    <li><a href="./observe/observedmethods/template-prefix/org.valuereporter.Welcome.ping">Ping</a>
    <li><a href="./observe/observedmethods/template-prefix/org.valuereporter.Welcome.hello">Helo</a>

    <h3>SLA</h3>
   <li>SLA reporting: <a href="./gui/sla?prefix=template-prefix&methodName=org.valuereporter.Welcome.ping">SLA reporting on Welcome.ping</a></li>

</body>
</html>
