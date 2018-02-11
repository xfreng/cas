<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

</div> <!-- END #content -->

<%
    java.util.Calendar calendar = java.util.Calendar.getInstance();
    int year = calendar.get(java.util.Calendar.YEAR);
    request.setAttribute("year", year);
%>

<footer>
    <div id="copyright" class="container">
        <p><spring:message code="copyright"/>${year}.&nbsp;&nbsp;<spring:message code="copyright.suffix"/></p>
        <p><spring:message code="poweredBy"/>&nbsp;&nbsp;<a href="<spring:message code="cas.index.url" />"><spring:message code="cas.index.title"/> <%=org.jasig.cas.CasVersion.getVersion()%></a></p>
    </div>
</footer>

</div> <!-- END #container -->

<script type="text/javascript" src="https://cdn.bootcss.com/headjs/1.0.3/head.min.js"></script>
<spring:theme code="cas.javascript.file" var="casJavascriptFile" text="" />
<script type="text/javascript" src="<c:url value="${casJavascriptFile}" />"></script>

</body>
</html>

