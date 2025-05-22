<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://realEstate.timmax.com/functions" %>
<%--<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>--%>
<html>
<jsp:include page="headTag.jsp"/>
<body>
<jsp:include page="bodyHeader.jsp"/>
<section>
    <h2>Real estates</h2>
    <form method="get" action="realEstates/filter">
        <dl>
            <dt><label for="startSquare">From Square (inclusive):</label></dt>
            <dd><input type="number" id="startSquare" name="startSquare" value="${param.startSquare}" step="0.01"></dd>
        </dl>
        <dl>
            <dt><label for="endSquare">To Square (exclusive):</label></dt>
            <dd><input type="number" id="endSquare" name="endSquare" value="${param.endSquare}" step="0.01"></dd>
        </dl>
        <button type="submit">Filter</button>
    </form>
    <hr/>

    <a href="realEstates/create">Add Real estate</a>
    <hr>
    <table>
        <thead>
        <tr>
            <th>Address</th>
            <th>Square</th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <c:forEach items="${requestScope.realEstates}" var="realEstate">
            <jsp:useBean id="realEstate" type="com.timmax.realestate.dto.RealEstateDto"/>
            <tr>
                <td>${realEstate.address}</td>
                <td>${realEstate.square}</td>
                <td><a href="realEstates/update?id=${realEstate.id}">Update</a></td>
                <td><a href="realEstates/delete?id=${realEstate.id}">Delete</a></td>
            </tr>
        </c:forEach>
    </table>
</section>
<jsp:include page="bodyFooter.jsp"/>
</body>
</html>