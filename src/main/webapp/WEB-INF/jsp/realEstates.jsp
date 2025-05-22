<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://realEstate.timmax.com/functions" %>
<%--<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>--%>
<html>
<jsp:include page="headTag.jsp"/>
<body>
<jsp:include page="bodyHeader.jsp"/>
<section>
    <h3><spring:message code="realEstate.title"/></h3>
    <form method="get" action="realEstates/filter">
        <dl>
            <dt><label for="startSquare"><spring:message code="realEstate.startSquare"/>:</label></dt>
            <dd><input type="number" id="startSquare" name="startSquare" value="${param.startSquare}" step="0.01"></dd>
        </dl>
        <dl>
            <dt><label for="endSquare"><spring:message code="realEstate.endSquare"/>:</label></dt>
            <dd><input type="number" id="endSquare" name="endSquare" value="${param.endSquare}" step="0.01"></dd>
        </dl>
        <button type="submit"><spring:message code="realEstate.filter"/></button>
    </form>
    <hr/>

    <a href="realEstates/create"><spring:message code="realEstate.add"/></a>
    <hr>
    <table>
        <thead>
        <tr>
            <th><spring:message code="realEstate.address"/></th>
            <th><spring:message code="realEstate.square"/></th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <c:forEach items="${requestScope.realEstates}" var="realEstate">
            <jsp:useBean id="realEstate" type="com.timmax.realestate.dto.RealEstateDto"/>
            <tr>
                <td>${realEstate.address}</td>
                <td>${realEstate.square}</td>
                <td><a href="realEstates/update?id=${realEstate.id}"><spring:message code="common.update"/></a></td>
                <td><a href="realEstates/delete?id=${realEstate.id}"><spring:message code="common.delete"/></a></td>
            </tr>
        </c:forEach>
    </table>
</section>
<jsp:include page="bodyFooter.jsp"/>
</body>
</html>