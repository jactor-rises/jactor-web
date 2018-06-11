<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ page session="false"%>

<table>
<c:forEach var="item" items="${mainItems}">
  <tr>
    <td>
     <div id='menu.item'>
       <a href='<c:url value="${item.request}" />'><fmt:message key="${item.name}" /></a>
     </div>
   </td>
 </tr>
</c:forEach>
</table>
