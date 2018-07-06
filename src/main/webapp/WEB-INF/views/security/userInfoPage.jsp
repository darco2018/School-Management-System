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
				<div>

					<h2>User Info Page</h2>
					
					<h3> Welcome : <span>${request.userPrincipal.name}</span> </h3>
					<span>${requestScope.userPrincipal.name}</span>
					<span>${requestScope.principal.name}</span>
					<span>${principal.name}</span>
					
					<b>This is protected page!</b> <br /> <br />
					
					
					<div>Presenting user info:</div>
					<div>${userInfo}</div>

				</div>
			</div>
		</div>
	</div>

	<%@ include file="../jspf/footer.jspf"%>

</body>
</html>