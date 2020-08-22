<%@page import="com.iiht.evaluation.coronokit.model.ProductMaster" %>
<%@page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Corona Kit-Edit Product(Admin)</title>
</head>
<body>
<jsp:include page="header.jsp"/>
<hr/>

<p align="right">
<a href="admin?action=logout"><button>Logout</button></a> 
</p>
<h3>Product Details</h3>
<form action="admin?action=updateproduct" method="post">

<%
ProductMaster item=(ProductMaster)request.getAttribute("itemToBeUpdated");
%>
<label for="itemName">Item Name</label>
<input type="text" name="itemName" value="<%=item.getProductName()%>" /><br><br>
<label for="ItemPrice">Item Price</label>
<input type="text" name="price" value="<%=item.getCost() %>"/><br><br>
<label for="Itemstock">Item Description</label>
<input type="text" name="itemDescription" value="<%=item.getProductDescription()%>"/><br><br>
<br>
<input type="submit" name="submit" value="Save"/>
</form>

<hr/>	
	<jsp:include page="footer.jsp"/>
</body>
</html>