package com.iiht.evaluation.coronokit.filter;

import java.io.IOException;
import java.util.Objects;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet Filter implementation class AdminFilter
 */
@WebFilter("/admin")
public class AdminFilter implements Filter {

  
	public void destroy() {
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest servletRequest=(HttpServletRequest)request;
		HttpServletResponse servletResponse=(HttpServletResponse)response;	
	
		String sessionUserName=(String)servletRequest.getSession().getAttribute("username");
		String sessionPassword=(String)servletRequest.getSession().getAttribute("password");
		if(Objects.equals(sessionUserName,null)||Objects.equals(sessionPassword,null) )
		{
		String username=servletRequest.getParameter("loginid");
		String password=servletRequest.getParameter("password");
		
		sessionUserName=username;
		sessionPassword=password;
		}
		
		if(sessionUserName.equals("admin") &&sessionPassword.equals("admin"))
		{
		servletRequest.getSession().setAttribute("username",sessionUserName);		
		servletRequest.getSession().setAttribute("password",sessionPassword);
		chain.doFilter(request, response);
		}
		else
		{
		servletRequest.setAttribute("errorMessageLoginFailure", "Invalid username or password");
		RequestDispatcher dispatch = 
				servletRequest.getRequestDispatcher("index.jsp");
	    dispatch.forward(servletRequest, servletResponse);
		}

	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
	}

}
