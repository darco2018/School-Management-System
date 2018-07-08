<%@ include file="../jspf/taglibs.jspf"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="../jspf/cssBootstrapLinks.jspf"%>
</head>
<body>
	<%@ include file="../jspf/header.jspf"%>

	<div class="container">
		<div class="row">
			<div class="col-sm-12">
				<div class="text-info" id="content">
				<h1>Developer's page</h1>
					
					<%--The <c:out /> tag ensures the username is escaped to avoid XSS vulnerabilities Regardless of how 
					an application renders user inputed values, it should ensure that the values are properly escaped. --%>	
					
					<h3> Welcome <c:out value="${pageContext.request.remoteUser}"/>!</h3>
					<div id="content">
					<strong>This is protected page!</strong><br>
					
					User principal: ${pageContext.request.userPrincipal.name} or ${requestScope.userPrincipal.name}<br>
					Login of user making request or null if user not authenticated: ${pageContext.request.remoteUser}<br>
					Authentication protecting the servlet. If the servlet is not authenticated null is returned.:${pageContext.request.authType}<br>
					--------------------------------------------------------------------------------<br>
					Current session connected with this request, or if no session creates one: ${pageContext.request.session.id}<br>
					More info can be read from session<br><br>
					--------------------------------------------------------------------------------<br>
					MIME type of request: ${pageContext.request.contentType}<br>
					Method with which request was made: ${pageContext.request.method}<br>
					--------------------------------------------------------------------------------<br>
					Attribute names as enumeration: ${pageContext.request.attributeNames}<br>
					Cookies array: ${pageContext.request.cookies}<br>
					Header names enumeration: ${pageContext.request.headerNames}<br>
					Parameters as Map: ${pageContext.request.parameterMap}<br>
					Parameters names as Enumeration: ${pageContext.request.parameterNames}<br>
					--------------------------------------------------------------------------------<br>
					Context path: ${pageContext.request.contextPath}<br>
					URL client used to make this request without query string: ${pageContext.request.requestURL}<br>
					Request URI (without query string):${pageContext.request.requestURI}<br>
					Servlet path: ${pageContext.request.servletPath}<br>
					Info path (between servlet path & query string: ${pageContext.request.pathInfo}<br>
					Query string: ${pageContext.request.queryString}<br>
					--------------------------------------------------------------------------------<br>
					Collection of all uploaded parts: <!-- pageContext.request.parts} --><br>
					Locale: ${pageContext.request.locale.displayCountry} ${pageContext.request.locale.displayLanguage}<br>
					Locales acceptable to client: ${pageContext.request.locales}<br>
					InputStream of the body --- <br>
					Retrieves the body of the request as character data using a BufferedReader. ---- <br>
					--------------------------------------------------------------------------------<br>
					IP address on which request was received: ${pageContext.request.localAddr}<br>
					IP of the client that sent request: ${pageContext.request.remoteAddr}<br>
					Fully qualified name of the client that sent request: ${pageContext.request.remoteHost}<br>
					Server name: ${pageContext.request.serverName}<br>	
					</div>
					
					<div>Presenting user info from controller:</div>
					<div>${userInfo}</div>
									
				
				</div>
			</div>
		</div>
	</div>

	<%@ include file="../jspf/footer.jspf"%>

</body>
</html>