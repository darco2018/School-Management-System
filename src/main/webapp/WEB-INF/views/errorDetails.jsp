<%@ include file="jspf/taglibs.jspf"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="jspf/cssBootstrapLinks.jspf"%>
</head>
<body>
	<%@ include file="jspf/header.jspf"%>

	<div class="container">
		<div class="row">
			<div class="col-sm-12">
				<div>
					<h1>ERROR DETAILS</h1>
					
					<div id="content">
						<p>${message}</p>
						
						<p>Page: ${url} </p>
						<p>Status: ${status} </p>
						<p>Occurred: ${timestamp} </p>
						<p>Message: ${exception.message} </p>
						
						
    					Failed URL: ${url}
    					Exception:  ${exception.message}
       					 <c:forEach items="${exception.stackTrace}" var="ste">    
        						${ste} 
   	 					</c:forEach>
 						 
					</div>
				</div>
			</div>
		</div>
	</div>

	<%@ include file="jspf/footer.jspf"%>

</body>
</html>