<%@page import="com.iiht.evaluation.coronokit.model.ProductMaster" %>
<%@page import="com.iiht.evaluation.coronokit.model.KitDetail" %>
<%@page import="java.util.List" %>
<%@page import="java.util.Objects" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Corona Kit-All Products(user)</title>
</head>
<body>
<jsp:include page="header.jsp"/>
<hr/>

<h1>Display Products</h1>
<%
        List<ProductMaster> items =  (List<ProductMaster>) request.getAttribute("items");	
	%>
    <a href="user?action=showkit"><button>Show Kit</button></a>
	<table border="1" width="100%">
	
		<thead>
			<th>NAME</th>
			<th>Price</th>
			<th>Available Stock</th>
			<th>Enter Quantity</th>
			<th>Action to Perform</th>
		</thead>
		
		<tbody>
		<%if(!Objects.equals(items,null)){%>
			<% for(ProductMaster item : items) { %>
			<form action="user?action=addnewitem" method="post">
			<tr>
				<td><%=item.getProductName()%></td>
				<td><%=item.getCost()%></td>
				<td><%=item.getProductDescription()%></td>			
				<td><input type="number" name="numberOfItem"/></td>
				<%request.setAttribute("itemClicked",item); %>
				<td><button type="submit" name="Add" value=<%=item.getId()%>>Add to Kit</button></td>
				
			</tr>
			</form>
			<% }}
		     else{%>
		     <div>No Product Is Available</div>		   
		     <%} %>
			
		</tbody>
</table>
<%List<KitDetail> orders =  (List<KitDetail>) session.getAttribute("addedItems"); 
if(!Objects.equals(orders,null)){%>
<a href="user?action=placeorder"><button>Place Order</button></a>
<%} %>
<hr/>	
	<jsp:include page="footer.jsp"/>
</body>
</html>