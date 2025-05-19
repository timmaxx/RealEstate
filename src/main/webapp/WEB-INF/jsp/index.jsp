<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setBundle basename="messages.app"/>

<html>
<jsp:include page="headTag.jsp"/>
<body>
<jsp:include page="bodyHeader.jsp"/>
<br>
<section>
    <form method="post" action="users">
        <fmt:message key="app.login"/>: <select name="userId">
        <option value="100000" selected>User</option>
        <option value="100001">Admin</option>
    </select>
        <button type="submit"><fmt:message key="common.select"/></button>
    </form>
</section>
<jsp:include page="bodyFooter.jsp"/>
</body>
</html>