<%@ include file="../jspf/taglibs.jspf"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="../jspf/cssBootstrapLinks.jspf"%>
<title>Access Denied</title>
</head>
<body>
	<%@ include file="../jspf/header.jspf"%>

	<div class="container">
		<div class="row">
			<div class="col-sm-12">
				<div>
					<h1>accessDeniedPage("/403")</h1>
					<p>When the user has logged in as XX.
	        // But access a page that requires role YY,
	        // AccessDeniedException will be thrown.</p>

					<h3>${message}</h3>

					<div>${userInfo}</div>
					<br>
					<div>${devInfo}</div>

				</div>
			</div>
		</div>
	</div>

	<%@ include file="../jspf/footer.jspf"%>

</body>
</html>