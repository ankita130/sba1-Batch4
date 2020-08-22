<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Corona Kit-Add New Product(Admin)</title>
</head>
<body>
<jsp:include page="header.jsp"/>
<hr/>

<p align="right">
<a href="admin?action=logout"><button>Logout</button></a> 
</p>
<h3>Enter Products Details</h3>
<form action="admin?action=insertproduct" method="post">
<label for="ItemName">Item Name</label>
<input type="text" name="itemName"/><br><br>
<label for="ItemPrice">Item Price</label>
<input type="text" name="itemPrice"/><br><br>
<label for="Itemstock">Item Description</label>
<input type="text" name="itemDescription"/><br><br>
<div>
<%
    if(null!=request.getAttribute("messageItemAdded"))
    {
        out.println(request.getAttribute("messageItemAdded"));
    }
%>
</div>
<input type="submit" name="submit" value="Add Item"/>
<input type="reset" name="submit" value="Reset"/>
<form>
<br>
<br>
<a href="admin?action=list">Go to Admin Portal to check list of products</a>

<hr/>	
	<jsp:include page="footer.jsp"/>
</body>
</html>