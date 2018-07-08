<%@ include file="../jspf/taglibs.jspf"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>School Manager Project</title>
<%@ include file="../jspf/cssBootstrapLinks.jspf"%>
</head>
<body>
	<%@ include file="../jspf/header.jspf"%>

	<div class="container">
		<div class="row">
			<div class="col-sm-12">
				<h3>Do you really want to log out?</h3>
				<div class="form-group row">
				<div class="col-xs-1">
				
				<%--  The CSRF protection requires you to send a CSRF token with any form you send. If you use Spring MVC forms (like <form:form> then Spring Security adds the token by default, but if you use a regular form you need to add the token yoursel --%>
					<c:url var="logoutUrl" value="${pageContext.request.contextPath}/logout"/>
					<form:form action="${logoutUrl}" > 
						<input type="submit" class="btn btn-info   btn-primary" value="LOG OUT">
					</form:form>
				</div>
				<div class="form-group row">
				<div class="col-xs-1">
					<form action='${pageContext.request.contextPath}/' method="GET">
						<input type="submit" class="btn btn-info  btn-primary"  value="CANCEL">
					</form>
				</div>       
				</div>  
			</div>
			</div>
		</div>
	</div>

	<%@ include file="../jspf/footer.jspf"%>

</body>
</html>
