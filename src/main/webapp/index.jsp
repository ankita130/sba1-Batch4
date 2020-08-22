<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Corona Kit-Home</title>
</head>
<body>
<div>
<jsp:include page="header.jsp"/>
<hr/>
	<h2>Admin Login</h2>
	<form action="admin?action=login" method="post">
		<div>
			<div><label for="loginid">Enter login Id</label> </div>
			<div><input type="text" id="loginid" name="loginid"> </div>
		</div>
		<div>
			<div><label for="password">Enter password</label> </div>
			<div><input type="password" id="password" name="password"> </div>
		</div>
		<div>
			<div><input type="submit" value="Login"> </div>
			<div><input type="reset" value="Reset"> </div>
		</div>
	</form>
</div>
<hr/>
<div>
<%
    if(null!=request.getAttribute("errorMessageLoginFailure"))
    {
        out.println(request.getAttribute("errorMessageLoginFailure"));
    }
%>
</div>
<br>
<div>
	<a href="user?action=newuser"><button>Create Corona Kit To Purchase</button></a>
</div>
<br>
<br>
<hr/>	
	<jsp:include page="footer.jsp"/>
</body>
</html>