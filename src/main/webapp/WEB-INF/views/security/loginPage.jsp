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
					<h3>Login Page</h3>
					
       
       <!-- /login?error=true -->
      <c:if test="${request.getParameter('error') == 'true'}">
      		<div style="color:red;margin:10px 0px;">
         		Login Failed!!!
         	<div/>
         Reason :
      <%--  <span th:if="${session!= null and #session.getAttribute('SPRING_SECURITY_LAST_EXCEPTION') != null}"
            th:utext="${session.getAttribute('SPRING_SECURITY_LAST_EXCEPTION').message}">
                Static summary
         </span> --%>

      </c:if>
       
          
          
     <!-- <form action="<c:url value="/j_spring_security_check"></c:url>" method="post" role="form"> -->
      <h3>Enter user name and password:</h3>
      <form name='f' action="<c:url value="/j_spring_security_check"></c:url>" method='POST'>
         <table>
            <tr>
               <td>User:</td>
               <td><input type='text' name='username' value=''></td>
            </tr>
            <tr>
               <td>Password:</td>
               <td><input type='password' name='password' /></td>
            </tr>
            <tr>
               <td><input name="submit" type="submit" value="submit" /></td>
            </tr>
         </table>
      </form>
       
      <br>
      Username/pass:
      <ul>
        <li>admin/admin</li>
        <li>student/student</li>
      </ul> 
      <ul>
        <li>dbuser1/123</li>
        <li>dbadmin1/123</li>
      </ul>  
       
       
  				 </div>
			</div>
		</div>
	</div>

	<%@ include file="../jspf/footer.jspf"%>

</body>
</html>