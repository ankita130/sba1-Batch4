<%@page import="com.iiht.evaluation.coronokit.model.KitDetail" %>
<%@page import="java.util.List" %>
<%@page import="java.util.Objects" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Corona Kit-My Kit(user)</title>
</head>
<body>
<jsp:include page="header.jsp"/>
<hr/>

<h1>Display Item Added Corona Kit</h1>
<%
		List<KitDetail> orders =  (List<KitDetail>) session.getAttribute("addedItems");
	%>
<p align="right">
<a href="user?action=showproducts" align=><button>Continue Shopping</button></a></p>

	<table border="1" width="100%">
		<thead>
			<th>Name of the product</th>
			<th>Description of the product</th>
			<th>Price per product</th>
			<th>AddedQuantity</th>
			<th>Total Amount</th>
			<th>Action</th>
		</thead>
		<%if(!Objects.equals(orders,null)) {%>
		<tbody>
			<% for(KitDetail item : orders) { %>
			<tr>
				<td><%=item.getProductName()%></td>
				<td><%=item.getProductDescription()%></td>
				<td><%=item.getAmount()%></td>
				<td><%=item.getQuantity()%></td>
				<%Double totalPrice=item.getAmount()*item.getQuantity(); %>	
				<td><%=totalPrice%></td>	
				<td><a href="user?action=deleteitem&id=<%=item.getProductId()%>"><button>Delete</button></a></td>
						
			</tr>
			<% }} %>
					</tbody>
		<%if(null!=request.getAttribute("messageKitIsEmpty")){%>
		<div><%=request.getAttribute("messageKitIsEmpty")%></div>
		<%} %>
</table>
<%if(Objects.equals(request.getAttribute("messageKitIsEmpty"),null)){%>
<a href="user?action=placeorder"><button>Place Order</button></a>
<%} %>
<hr/>	
	<jsp:include page="footer.jsp"/>
</body>
</html>