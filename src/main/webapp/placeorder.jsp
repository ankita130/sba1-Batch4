<%@page import="java.util.Objects" %>
<%@page import="com.iiht.evaluation.coronokit.model.KitDetail" %>
<%@page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Corona Kit-Place Order(user)</title>
</head>
<body>
<jsp:include page="header.jsp"/>
<hr/>
<h3>Enter Details:</h3>
<form action="user?action=saveorder" method="post">
<label for="username">Name</label>
<input type="text" name="customerName"  value=<%=session.getAttribute("customerName")%>></input><br><br>
<label for="email">Email</label>
<input type="email" name="email"  value=<%=session.getAttribute("email")%>></input><br><br>
<label for="phoneNumber">Contact Number</label>
<input type="text" name="phoneNumber"  placeholder="Enter Phone Number"></input><br><br>
<label for="deliveryAddress">Shipping Address</label>
<input type="text" name="deliveryAddress"  placeholder="Enter The Shipping Address"></input><br><br>
<%
		List<KitDetail> orders =  (List<KitDetail>) session.getAttribute("addedItems");
%>
<%if(!Objects.equals(orders,null)) { %>
<input type="submit" name="submit" value="Confirm"/>
<br>
</form>
<%
}
   if(!Objects.equals(request.getAttribute("messageSomeDataMissing"),null))
    {
    %>
    
<div><%=request.getAttribute("messageSomeDataMissing")%></div>
<%} %>
<br>
<br>
<%
    if(null!=request.getAttribute("successfullyAddedOrder"))
    {
    %>
    
<div><%=request.getAttribute("successfullyAddedOrder")%></div>
<br>
<br>
<form action="test"></form>
<a href="user?action=ordersummary"><button>Go To Summary</button></a>

   <%} %>

<a href="user?action=showkit"><button>Go To Kit</button></a>
<a href="user?action=showproducts"><button>Add Some More Products</button></a>
<hr/>	
	<jsp:include page="footer.jsp"/>
</body>
</html>