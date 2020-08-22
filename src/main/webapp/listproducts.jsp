<%@page import="com.iiht.evaluation.coronokit.model.ProductMaster" %>
<%@page import="java.util.List" %>
<%@page import="java.util.Objects" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Corona Kit-All Products(Admin)</title>
</head>
<body>
<jsp:include page="header.jsp"/>
<hr/>

<h2>Admin Portal</h2>
<p align="right">
<a href="admin?action=logout"><button>Logout</button></a> 
</p>
<h3>AvailableProducts</h3>

<%
		// fetch the shared data
		List<ProductMaster> items =  (List<ProductMaster>) request.getAttribute("items");

	%>
	<table border="1" width="100%">
		<thead>
			<th>Product NAME</th>
			<th>Price</th>
			<th>Product Description</th>
			<th>Edit</th>
			<th>Delete</th>
		</thead>
		<tbody>
		    <%if(!Objects.equals(items,null)){%>
			<% for(ProductMaster item : items) { %>
			<tr>
				<td><%=item.getProductName()%></td>
				<td><%=item.getCost()%></td>
				<td><%=item.getProductDescription()%></td>
				<td><a href="admin?action=editproduct&id=<%=item.getId()%>"><button>Edit</button></a></td>
				<td><a href="admin?action=deleteproduct&id=<%=item.getId()%>"><button>Delete</button></a></td>
			</tr>
			<% }} else{%>
			<div>No Products Available</div>
			<% }%>
		</tbody>
    </table>

<a href="admin?action=newproduct"><button>Add New Products</button></a>

<hr/>	
	<jsp:include page="footer.jsp"/>
</body>
</html>