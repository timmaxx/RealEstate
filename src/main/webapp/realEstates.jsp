<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://realEstate.timmax.com/functions" %>
<%--<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>--%>
<html>

<head>
    <title>Real estate list</title>
    <link rel="stylesheet" href="css/style.css">
</head>

<body>
<section>
    <h3><a href="index.html">Home</a></h3>
    <hr/>
    <h2>Real estates</h2>
    <form method="get" action="realEstates">
        <input type="hidden" name="action" value="filter">
        <dl>
            <dt>From Square (inclusive):</dt>
            <dd><input type="float" name="startSquare" value="${param.startSquare}"></dd>
        </dl>
        <dl>
            <dt>To Square (exclusive):</dt>
            <dd><input type="float" name="endSquare" value="${param.endSquare}"></dd>
        </dl>
        <button type="submit">Filter</button>
    </form>
    <hr/>

    <a href="realEstates?action=create">Add Real estate</a>
    <br><br>
    <table border="1" cellpadding="8" cellspacing="0">
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
                <td><a href="realEstates?action=update&id=${realEstate.id}">Update</a></td>
                <td><a href="realEstates?action=delete&id=${realEstate.id}">Delete</a></td>
            </tr>
        </c:forEach>
    </table>
</section>
</body>
</html>