<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="flash" uri="http://sandbox.es/tags/flash-messages" %>
<%@ tag
    body-content="empty"
    isELIgnored="false"
    pageEncoding="UTF-8"
    description="Print level messages" %>
<%@ attribute
    name="level"
    required="true"
    type="es.sandbox.ui.messages.Level" %>

<c:set var="messages" value="${flash:levelMessages(level, pageContext.request)}"/>
<c:if test="${not empty messages}">
    <div data-level="${level}" class="${flash:levelCssClass(level, pageContext.request)}">
        <c:choose>
            <c:when test="${fn:length(messages) gt 1}">
                <ul>
                    <c:forEach var="message" items="${messages}">
                        <li data-timestamp="${message.timestamp}"><c:out value="${message.text}"
                                                                         escapeXml="false"/></li>
                    </c:forEach>
                </ul>
            </c:when>
            <c:otherwise>
                <c:forEach var="message" items="${messages}">
                    <span data-timestamp="${message.timestamp}"><c:out value="${message.text}"
                                                                       escapeXml="false"/></span>
                </c:forEach>
            </c:otherwise>
        </c:choose>
    </div>
</c:if>
