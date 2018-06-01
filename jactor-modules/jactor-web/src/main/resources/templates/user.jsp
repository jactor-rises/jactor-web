<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ page session="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<html>

<head>
<title><fmt:message key="page.user" /></title>
</head>

<body>
<div id="page.text.left">
  <c:choose>
    <c:when test="${user != null}">
      <p><fmt:message key="page.user.name" />: ${user.firstName} ${user.surname}<br/></p>
      <p>
        ${user.addressLine1}<br/>

        <c:choose>
          <c:when test="{user.addressLine2 != null">
            ${user.addressLine2}<br/>
          </c:when>
        </c:choose>

        ${user.zipCode} ${user.city}<br/>
        <fmt:message key="${user.country}" />
      </p>

      <p>${user.description}</p>
    </c:when>
    <c:otherwise>
      <fmt:message key="page.user.nothing"/>
    </c:otherwise>
  </c:choose>
</div>
</body>
</html>
