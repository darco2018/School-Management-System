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
       
    <div class="container">
    	<h2>Login Page</h2>
      <form class="form-horizontal" name="f" action="<c:url value="/perform_login"/>" method='POST'>              
            <fieldset>
                <h3>Please log in</h3>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                
                 <!-- /login?error=true -->
                 <c:if test="${param.error == true}">
                 <div class="form-group row">
                 <div class="col-xs-2">
                		<div class="text-danger">    
                   			 Login Failed.<br>
                   			 Reason: ${invalidLogin}.
                		</div>
                </div>
               	</div>
                </c:if>
                
                <div class="form-group row">
					<div class="col-xs-2">
						<label for="username">Username:</label>
						<input type="text" class="form-control" name="username" value=''/>
					</div>
				
					<div class="col-xs-2">
						<label for="password">Password:</label>
						<input type="password" class="form-control" name="password" value=''/>
					</div>
				</div>
                
                <%@ include file="../jspf/formAddResetButtons.jspf"%>
            </fieldset>
        </form>
       
        
       
      <br>
      Username/password:
      <ul>
        <li>admin/admin</li>
        <li>student/student</li>
      </ul> 
  				 </div>
			</div>
		</div>
	</div>

	<%@ include file="../jspf/footer.jspf"%>

</body>
</html>