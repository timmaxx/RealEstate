<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
  <title>Real estate</title>
  <link rel="stylesheet" href="css/style.css">
</head>
<body>
<section>
  <h3><a href="index.html">Home</a></h3>
  <hr>
  <h2>${param.action == 'create' ? 'Create real estate' : 'Edit real estate'}</h2>
  <jsp:useBean id="realEstate" type="com.timmax.realestate.model.RealEstate" scope="request"/>
  <form method="post" action="realEstates">
    <input type="hidden" name="id" value="${realEstate.id}">
    <dl>
      <dt>Address:</dt>
      <dd><input type="text" value="${realEstate.address}" size=40 name="address" required></dd>
    </dl>
    <dl>
      <dt>Square:</dt>
      <dd><input type="number" value="${realEstate.square}" name="square" step="0.01" required></dd>
    </dl>
    <button type="submit">Save</button>
    <button onclick="window.history.back()" type="button">Cancel</button>
  </form>
</section>
</body>
</html>
