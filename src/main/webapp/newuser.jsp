<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Corona Kit-New User(user)</title>
</head>
<body>
<jsp:include page="header.jsp"/>
<hr/>

<h2>Enter User Information</h2>
<br>
<br>
<form action="user?action=insertuser" method="post">
<label for="username">Name</label>
<input type="text" name="customerName"  placeholder="Enter Name"/><br><br>
<label for="email">Email</label>
<input type="email" name="email"  placeholder="Enter Email"/><br><br>

<input type="submit" name="submit" value="Save"/>
<input type="reset" name="submit" value="Reset"/>
</form>
<br>
<%
    if(null!=request.getAttribute("messageUserInfoStored"))
    {
    %>
    
<div><%=request.getAttribute("messageUserInfoStored")%></div>
<br>
<div>
	<a href="user?action=showproducts"><button>Click Here to Add Products to CoronaKit</button></a>
</div>
   <%} %> 
<br>

<hr/>	
	<jsp:include page="footer.jsp"/>
</body>
</html>