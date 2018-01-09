<%@ page import="java.util.Calendar" %><%--

    Licensed to Jasig under one or more contributor license
    agreements. See the NOTICE file distributed with this work
    for additional information regarding copyright ownership.
    Jasig licenses this file to you under the Apache License,
    Version 2.0 (the "License"); you may not use this file
    except in compliance with the License.  You may obtain a
    copy of the License at the following location:

      http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing,
    software distributed under the License is distributed on an
    "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
    KIND, either express or implied.  See the License for the
    specific language governing permissions and limitations
    under the License.

--%>
<%
    Calendar calendar = Calendar.getInstance();
    int year = calendar.get(Calendar.YEAR);
    request.setAttribute("year", year);
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

      </div> <!-- END #content -->
      
      <footer>
        <div id="copyright">
          <p><spring:message code="copyright"/>${year}.&nbsp;&nbsp;<spring:message code="copyright.suffix"/></p>
          <p><spring:message code="poweredBy"/>&nbsp;&nbsp;<a href="http://localhost:9090/cas"><spring:message code="cas.index.title"/> <%=org.jasig.cas.CasVersion.getVersion()%></a></p>
        </div>
      </footer>

    </div> <!-- END #container -->
    
    <script type="text/javascript" src="https://code.jquery.com/jquery-1.10.2.min.js"></script>
    <script type="text/javascript" src="https://code.jquery.com/ui/1.10.3/jquery-ui.min.js"></script>
    
    <%-- 
        JavaScript Debug: A simple wrapper for console.log 
        See this link for more info: http://benalman.com/projects/javascript-debug-console-log/
    --%>
    <spring:theme code="cowboy.javascript-debug.file" var="cowboyJavascriptDebugFile" text="" />
    <script type="text/javascript" src="<c:url value="${cowboyJavascriptDebugFile}" />"></script>
    
    <spring:theme code="cas.javascript.file" var="casJavascriptFile" text="" />
    <script type="text/javascript" src="<c:url value="${casJavascriptFile}" />"></script>
  </body>
</html>

