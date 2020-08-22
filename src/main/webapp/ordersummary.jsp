<%@page import="com.iiht.evaluation.coronokit.model.KitDetail" %>
<%@page import="com.iiht.evaluation.coronokit.model.OrderSummary" %>
<%@page import="com.iiht.evaluation.coronokit.model.CoronaKit" %>
<%@page import="java.util.List" %>
<%@page import="java.util.Objects" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Corona Kit-Order Summary(user)</title>
</head>
<body>
<jsp:include page="header.jsp"/>
<hr/>
<%
List<OrderSummary> listOrderSummary=(List<OrderSummary>)session.getAttribute("listOrderSummary");
int numberOfTable=0;
%>
<%for(OrderSummary orderSummary:listOrderSummary)
{ 
	numberOfTable++;
	CoronaKit coronaKit=orderSummary.getCoronaKit();
%>
<h6>Details of order<%= numberOfTable%></h6>
<label for="username">Name : </label>
<span><%=coronaKit.getPersonName()%></span><br>
<label for="email">Email : </label>
<span><%=coronaKit.getEmail()%></span><br>
<label for="phoneNumber">Contact Number : </label>
<span><%=coronaKit.getContactNumber()%></span><br>
<label for="deliveryAddress">Shipping Address : </label>
<span><%=coronaKit.getDeliveryAddress()%></span><br>
<label for="orderNumber">Order Number : </label>
<span><%=coronaKit.getId()%></span>
<label for="dateOfOrder">OrderDate : </label>
<span><%=coronaKit.getOrderDate()%></span>
<%List<KitDetail> listKitDetails= (List<KitDetail>)orderSummary.getKitDetails();%>
<table border="1" width="100%">
		<thead>
			<th>Name of the product</th>
			<th>Description of the product</th>
			<th>Price per product</th>
			<th>AddedQuantity</th>
			<th>Total Amount</th>
		</thead>
		<tbody>
			<% for(KitDetail item : listKitDetails) { %>
			<tr>
				<td><%=item.getProductName()%></td>
				<td><%=item.getProductDescription()%></td>
				<td><%=item.getAmount()%></td>
				<td><%=item.getQuantity()%></td>
				<%Double totalPrice=item.getAmount()*item.getQuantity(); %>	
				<td><%=totalPrice%></td>
					
			</tr>
	</tbody>
</table>
<%}} %>

<a href="user?action=showproducts"><button>Create CoronaKit</button></a>
<br>
<a href="user?action=endKitCreation"><button>Done With Shopping</button></a>
<hr/>	
	<jsp:include page="footer.jsp"/>
</body>
</html>