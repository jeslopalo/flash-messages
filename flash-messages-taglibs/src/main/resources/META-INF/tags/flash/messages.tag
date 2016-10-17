<%@ tag
    body-content="empty"
    description="Print status messages"
    pageEncoding="UTF-8"
    isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="flash" uri="http://sandbox.es/tags/flash-messages" %>
<c:forEach var="level" items="${flash:levels(pageContext.request)}">
    <flash:level level="${level}"/>
</c:forEach>
