<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<jsp:include page="headTag.jsp"/>
<body>
<jsp:include page="bodyHeader.jsp"/>

<section>
  <jsp:useBean id="realEstate" type="com.timmax.realestate.model.RealEstate" scope="request"/>
  <h3>${realEstate.isNew() ? 'Create real estate' : 'Edit real estate'}</h3>
  <hr>
  <form method="post" action="realEstates">
    <input type="hidden" name="id" value="${realEstate.id}">
    <dl>
      <dt><label for="address">Address:</label></dt>
      <dd><input type="text" id="address" value="${realEstate.address}" size=40 name="address" required></dd>
    </dl>
    <dl>
      <dt><label for="square">Square:</label></dt>
      <dd><input type="number" id="square" value="${realEstate.square}" name="square" step="0.01" required></dd>
    </dl>
    <button type="submit">Save</button>
    <button type="button" onclick="window.history.back()">Cancel</button>
  </form>
</section>
<jsp:include page="bodyFooter.jsp"/>
</body>
</html>
