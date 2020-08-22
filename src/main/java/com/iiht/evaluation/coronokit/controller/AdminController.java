package com.iiht.evaluation.coronokit.controller;

import java.io.IOException;
import java.sql.SQLException;
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

import com.iiht.evaluation.coronokit.dao.ProductMasterDao;
import com.iiht.evaluation.coronokit.model.ProductMaster; 

@WebServlet("/admin")
public class AdminController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ProductMasterDao productMasterDao;
	private HttpSession session; 
	
	
	public void setProductMasterDao(ProductMasterDao productMasterDao) {
		this.productMasterDao = productMasterDao;
	}
	public void init(ServletConfig config) {
		String jdbcDriver =  config.getServletContext().getInitParameter("jdbcDriver");
		String jdbcUrl     =     config.getServletContext().getInitParameter("jdbcUrl");
		String jdbcUsername =  config.getServletContext().getInitParameter("jdbcUsername");
		String jdbcPassword =  config.getServletContext().getInitParameter("jdbcPassword");
    	this.productMasterDao = new ProductMasterDao(jdbcDriver, jdbcUrl, jdbcUsername, jdbcPassword);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action =  request.getParameter("action");
		String viewName = "";
		try {
			switch (action) {
			case "login" : 
				viewName = adminLogin(request, response);
				break;
			case "newproduct":
				viewName = showNewProductForm(request, response);
				break;
			case "insertproduct":
				viewName = insertProduct(request, response);
				break;
			case "deleteproduct":
				viewName = deleteProduct(request, response);
				break;
			case "editproduct":
				viewName = showEditProductForm(request, response);
				break;
			case "updateproduct":
				viewName = updateProduct(request, response);
				break;
			case "list":
				viewName = listAllProducts(request, response);
				break;	
			case "logout":
				viewName = adminLogout(request, response);
				break;
			case "closesession":
				viewName=adminLogout(request, response);
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

	private String adminLogout(HttpServletRequest request, HttpServletResponse response) {
		session.invalidate(); 
	    	return "index.jsp";
	       
	}

	private String listAllProducts(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException {
		String viewName="";
		String sessionUserName=(String)session.getAttribute("username");
		String sessionPassword=(String)session.getAttribute("password");
		
		if(sessionUserName.equals("admin") &&sessionPassword.equals("admin"))
		{
		List<ProductMaster> availableItems=productMasterDao.getAvailableItems();
		request.setAttribute("items", availableItems);
		viewName= "listproducts.jsp";
		}
		
		else
		viewName="index.jsp";
		return viewName;
	}

	private String updateProduct(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException{
		String viewName="";
		String itemName=request.getParameter("itemName");
		String price=request.getParameter("price");
		String stockValue=request.getParameter("itemDescription");		
		int itemId=Integer.parseInt((String)session.getAttribute("itemId"));
		String sessionUserName=(String)session.getAttribute("username");
		String sessionPassword=(String)session.getAttribute("password");
		
		if(sessionUserName.equals("admin") &&sessionPassword.equals("admin"))
		{
		if(productMasterDao.updateItem(itemId,itemName,price,stockValue))
		{
			List<ProductMaster> availableItems=productMasterDao.getAvailableItems();
			request.setAttribute("items", availableItems);
			viewName= "listproducts.jsp";
		}
		}
		else
		{
			viewName="index.jsp";
		}
		return viewName;
	}

	private String showEditProductForm(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException {
		
		String id=request.getParameter("id");
		ProductMaster fetchedItem=productMasterDao.fetchItemUsingId(id);
		String viewName="";
		String sessionUserName=(String)session.getAttribute("username");
		String sessionPassword=(String)session.getAttribute("password");
		
		if(sessionUserName.equals("admin") &&sessionPassword.equals("admin"))
		{
		session.setAttribute("itemId",id);	
		request.setAttribute("itemToBeUpdated", fetchedItem);
		viewName="editproduct.jsp";
		}
		else
		{
		viewName="index.jsp";
		}
		return viewName;
	}

	private String deleteProduct(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException {
		String viewName="";	
		int itemId=Integer.parseInt((String)request.getParameter("id"));
		String sessionUserName=(String)session.getAttribute("username");
		String sessionPassword=(String)session.getAttribute("password");
		
		if(sessionUserName.equals("admin") &&sessionPassword.equals("admin"))
		{
		if(productMasterDao.deleteItem(itemId))
		{
			
			List<ProductMaster> availableItems=productMasterDao.getAvailableItems();
			request.setAttribute("items", availableItems);
			viewName= "listproducts.jsp";
		}
		}
		else
		{
			viewName="index.jsp";
		}
		return viewName;
	}

	private String insertProduct(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException {
		int rowAdded=0;
		String viewName="";
		String sessionUserName=(String)session.getAttribute("username");
		String sessionPassword=(String)session.getAttribute("password");
		
		if(sessionUserName.equals("admin") &&sessionPassword.equals("admin"))
		{
			String itemName=request.getParameter("itemName");
		    String itemPrice=request.getParameter("itemPrice");
		    String itemDescription=request.getParameter("itemDescription");
		    rowAdded=productMasterDao.addItem(itemName, itemPrice, itemDescription);
		    if(rowAdded>0)
		    {
		    	request.setAttribute("messageItemAdded",("Succesfully added"+rowAdded+"product : "+itemName));
		    }
		viewName= "newproduct.jsp";
		}
		else
		viewName="index.jsp";
	    
	    
		return viewName;
	}

	private String showNewProductForm(HttpServletRequest request, HttpServletResponse response) {
		
		return "newproduct.jsp";
	}

	private String adminLogin(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException {
		String viewName="listproducts.jsp";
		session=request.getSession();List<ProductMaster> availableItems=productMasterDao.getAvailableItems();
		request.setAttribute("items", availableItems);

		return viewName;
	}

	
}