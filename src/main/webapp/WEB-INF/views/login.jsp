<%@ include file="jspf/taglibs.jspf"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>School Manager Project</title>
	<%@ include file="jspf/cssBootstrapLinks.jspf"%>
</head>
<body>
	<%@ include file="jspf/header.jspf"%>

	<div class="container">
		<div class="row">
			<div class="col-sm-12">
				<div>
					<h3>Login</h3>
					<p>Please login to start using the application</p>
					
					<c:if test="${param.error}">
					<div>Invalid username and password.</div>
					</c:if>
					
					<c:if test="${param.logout}">
					<div>You have been logged out.</div>
					</c:if>
					
					<form action='/login' method='POST'>
						<div><label> Email : <input type="text" name='email'></label></div>
						<div><label> Password : <input type="password" name='password'></label></div>
						<div><input type="submit" value='Sign in'></div>
					</form>
				</div>
			</div>
		</div>
	</div>

	<%@ include file="jspf/footer.jspf"%>

</body>
</html>