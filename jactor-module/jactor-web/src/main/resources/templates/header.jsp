<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ page session="false"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="language" value="${pageContext.response.locale}" />

<c:choose>
  <c:when test="${language == 'no_NO_no'}">
    <a href="home.do"><img title="jactor-rises" src="<c:url value="images/icons/holmenkollen.jpeg" />"></a>
  </c:when>
  <c:when test="${language == 'th_TH'}">
    <a href="home.do"><img title="jactor-rises" src="<c:url value="images/icons/wat.gif" />"></a>
  </c:when>
  <c:otherwise>
    <a href="home.do"><img title="jactor-rises" src="<c:url value="images/icons/bigben.gif" />"></a>
  </c:otherwise>
</c:choose>

<div id="language">
<table>
  <form method="post" action="${action}">
  <c:choose>
    <c:when test="${language == 'no_NO_no'}">
      <img src='<c:url value="/images/icons/flag.nor.chosen.gif" />' />
    </c:when>
    <c:otherwise>
      <input type="hidden" name="locale" value="no_NO_no">
      <input type="image" alt="Norsk" src='<c:url value="images/icons/flag.nor.choose.gif"/>' >
    </c:otherwise>
  </c:choose>
  <c:forEach var="parameter" items="${parameters}">
    <input type="hidden" name="${parameter.name}" value="${parameter.value}">
  </c:forEach>
  </form>
  <form method="post" action="${action}">
  <c:choose>
    <c:when test="${language == 'en_GB'}">
      <img src='<c:url value="/images/icons/flag.eng.chosen.gif" />' />
    </c:when>
    <c:otherwise>
      <input type="hidden" name="locale" value="en_GB">
      <input type="image" alt="English" src="<c:url value="images/icons/flag.eng.choose.gif"/>" >
    </c:otherwise>
  </c:choose>
  <c:forEach var="parameter" items="${parameters}">
    <input type="hidden" name="${parameter.name}" value="${parameter.value}">
  </c:forEach>
  </form>
  <form method="post" action="${action}">
  <c:choose>
    <c:when test="${language == 'th_TH'}">
      <img src='<c:url value="/images/icons/flag.thai.chosen.gif" />' />
    </c:when>
    <c:otherwise>
      <input type="hidden" name="locale" value="th_TH">
      <input type="image" alt="Thai" src='<c:url value="images/icons/flag.thai.choose.gif"/>' >
    </c:otherwise>
  </c:choose>
  <c:forEach var="parameter" items="${parameters}">
    <input type="hidden" name="${parameter.name}" value="${parameter.value}">
  </c:forEach>
  </form>
</table>
</div>
