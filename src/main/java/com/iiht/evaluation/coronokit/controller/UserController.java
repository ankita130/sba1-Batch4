package com.iiht.evaluation.coronokit.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.iiht.evaluation.coronokit.dao.KitDao;
import com.iiht.evaluation.coronokit.dao.ProductMasterDao;
import com.iiht.evaluation.coronokit.model.CoronaKit;
import com.iiht.evaluation.coronokit.model.KitDetail;
import com.iiht.evaluation.coronokit.model.OrderSummary;
import com.iiht.evaluation.coronokit.model.ProductMaster;
import java.time.LocalDate;

@WebServlet("/user")
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private KitDao kitDao;
	private ProductMasterDao productMasterDao;
	private HttpSession session;

	public void setKitDAO(KitDao kitDAO) {
		this.kitDao = kitDAO;
	}

	public void setProductMasterDao(ProductMasterDao productMasterDao) {
		this.productMasterDao = productMasterDao;
	}

	public void init(ServletConfig config) {
		String jdbcDriver =  config.getServletContext().getInitParameter("jdbcDriver");
		String jdbcUrl     =     config.getServletContext().getInitParameter("jdbcUrl");
		String jdbcUsername =  config.getServletContext().getInitParameter("jdbcUsername");
		String jdbcPassword =  config.getServletContext().getInitParameter("jdbcPassword");
    	this.productMasterDao = new ProductMasterDao(jdbcDriver, jdbcUrl, jdbcUsername, jdbcPassword);
		
		this.kitDao = new KitDao(jdbcUrl, jdbcUsername, jdbcPassword);
		this.productMasterDao = new ProductMasterDao(jdbcDriver,jdbcUrl, jdbcUsername, jdbcPassword);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");
		
		String viewName = "";
		try {
			switch (action) {
			case "newuser":
				viewName = showNewUserForm(request, response);
				break;
			case "insertuser":
				viewName = insertNewUser(request, response);
				break;
			case "showproducts":
				viewName = showAllProducts(request, response);
				break;	
			case "addnewitem":
				viewName = addNewItemToKit(request, response);
				break;
			case "deleteitem":
				viewName = deleteItemFromKit(request, response);
				break;
			case "showkit":
				viewName = showKitDetails(request, response);
				break;
			case "placeorder":
				viewName = showPlaceOrderForm(request, response);
				break;
			case "saveorder":
				viewName = saveOrderForDelivery(request, response);
				break;	
			case "ordersummary":
				viewName = showOrderSummary(request, response);
				break;	
			case "endKitCreation":
				viewName = endKitCreation(request, response);
				break;		
			default : viewName = "notfound.jsp"; break;	
			}
		} catch (Exception ex) {
			
			throw new ServletException(ex.getMessage());
		}
			RequestDispatcher dispatch = 
					request.getRequestDispatcher(viewName);
			dispatch.forward(request, response);
	
	}

	private String endKitCreation(HttpServletRequest request, HttpServletResponse response) {
		session.invalidate();
		return "index.jsp";
	}

	private String showOrderSummary(HttpServletRequest request, HttpServletResponse response) {			
		return "ordersummary.jsp";
	}

	private String saveOrderForDelivery(HttpServletRequest request, HttpServletResponse response) {
		String customerName=request.getParameter("customerName");
		int orderNumber;
		String email=request.getParameter("email");
		String contactNumber=request.getParameter("phoneNumber");
		String deliveryAddress=request.getParameter("deliveryAddress");
		LocalDate orderDate=LocalDate.now();
		Double finalOrderAmount=Double.parseDouble((String)session.getAttribute("finalOrderAmount"));
		if(!Objects.equals(customerName,"")&&!Objects.equals(email,"") &&!Objects.equals(contactNumber,"") &&!Objects.equals(deliveryAddress,""))
		{
			
		List<OrderSummary> listOrderSummary;
	  	 if(Objects.equals(session.getAttribute("listOrderSummary"),null))
	  	 {
	  		listOrderSummary=new ArrayList<OrderSummary>();  
	  		orderNumber=1;
	  	 }
	  	 else
	  	 {
	  		listOrderSummary=(List<OrderSummary>)session.getAttribute("listOrderSummary");
	  		orderNumber=listOrderSummary.size()+1;
	     }
		CoronaKit coronaKit=new CoronaKit(orderNumber,customerName,email,contactNumber,finalOrderAmount,deliveryAddress,orderDate.toString(),true);
		OrderSummary newOrder=new OrderSummary();
		newOrder.setKitDetails((List<KitDetail>)session.getAttribute("addedItems"));
		newOrder.setCoronaKit(coronaKit);
		listOrderSummary.add(newOrder);
		session.setAttribute("listOrderSummary", listOrderSummary);
		request.setAttribute("successfullyAddedOrder",("Order with id "+orderNumber+" is placed for a total amount of "+finalOrderAmount));
		session.removeAttribute("addedItems");
		}
		else
			request.setAttribute("messageSomeDataMissing","Please enter data in all the fields");
		return "placeorder.jsp";
	}

	private String showPlaceOrderForm(HttpServletRequest request, HttpServletResponse response) {
		
		return "placeorder.jsp";
	}

	private String showKitDetails(HttpServletRequest request, HttpServletResponse response) {
		List<KitDetail> savedListOfOrder=(List<KitDetail>)session.getAttribute("addedItems");
			
		if(Objects.equals(savedListOfOrder,null))
		{
			request.setAttribute("messageKitIsEmpty","No product is added to the Kit");
		}
		return "showkit.jsp";
	}

	private String deleteItemFromKit(HttpServletRequest request, HttpServletResponse response) {
		int id=Integer.parseInt(request.getParameter("id"));
		Double finalAmount=Double.parseDouble((String)session.getAttribute("finalOrderAmount"));
		List<KitDetail> savedListOfOrder=(List<KitDetail>)session.getAttribute("addedItems");
	    for(KitDetail kitDetail:savedListOfOrder)
		{
			if(id==kitDetail.getProductId())
			{
				savedListOfOrder.remove(kitDetail);
				finalAmount=finalAmount-kitDetail.getAmount();
				break;
			}
				
		}
		session.setAttribute("addedItems",savedListOfOrder);
		session.setAttribute("finalOrderAmount",finalAmount.toString());
		return "showkit.jsp";
	}

	private String addNewItemToKit(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException {
	  Double finalOrderAmount=0.00;
	  boolean isItemThereInKit=false;
      String id=request.getParameter("Add");		
  	  int orderQuantity=Integer.parseInt(request.getParameter("numberOfItem"));
  	  ProductMaster itemToBeAdded=productMasterDao.fetchItemUsingId(id);
  	  
  	  session=request.getSession();
  	  List<KitDetail> coronaKit;
  	  if(Objects.equals(session.getAttribute("addedItems"),null))
  	  {
  		  coronaKit=new ArrayList<KitDetail>();  
  	  }
  	  else
  	  {
  		coronaKit=(List<KitDetail>)session.getAttribute("addedItems");
		  for(KitDetail order:coronaKit)
		  {
  		  if(order.getProductId()==Integer.parseUnsignedInt(id))
  		  {
  			  finalOrderAmount=finalOrderAmount+(orderQuantity*itemToBeAdded.getCost());
  			  order.setQuantity(order.getQuantity()+orderQuantity);  			  
  			  isItemThereInKit=true;
  			  break;  			  
  		  }    		       
		  
  	  }
  	  }
  	  
  	if(!isItemThereInKit)
	 {
  		KitDetail order=new KitDetail(Integer.parseUnsignedInt(id),itemToBeAdded.getProductName(),itemToBeAdded.getProductDescription(),orderQuantity,itemToBeAdded.getCost());
	    coronaKit.add(order);
	    finalOrderAmount=finalOrderAmount+(orderQuantity*itemToBeAdded.getCost());
	 }
     session.setAttribute("addedItems",coronaKit);
     session.setAttribute("finalOrderAmount",finalOrderAmount.toString());
  	 List<ProductMaster> availableItems=productMasterDao.getAvailableItems();
  	 request.setAttribute("items", availableItems);
  	 
     return "showproductstoadd.jsp";
	 }
	
	
	
	private String showAllProducts(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException {
		List<ProductMaster> availableItems=productMasterDao.getAvailableItems();
  		request.setAttribute("items", availableItems);
		return "showproductstoadd.jsp";
	}

	private String insertNewUser(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException {		
  		
    	String username=request.getParameter("customerName");
  		String email=request.getParameter("email");
  		String viewName="";
  		if(!(Objects.equals(username,null)) && !(Objects.equals(email,null)) &&(email.contains("@")))
  		{
  		session=request.getSession();
  		session.setAttribute("customerName",username);
  		session.setAttribute("email",email);
  		request.setAttribute("messageUserInfoStored",(username+ " You are all set to create your Corona kit"));
  		viewName= "newuser.jsp";
  		}
  		else
  		{
  		viewName= "error.jsp";
  		}
  		return viewName;
  	
  	   }

	private String showNewUserForm(HttpServletRequest request, HttpServletResponse response) {
		request.getSession().invalidate();
		return "newuser.jsp";

	}
}