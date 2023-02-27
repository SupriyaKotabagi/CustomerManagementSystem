package com.techpalle.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.techpalle.dao.DataAccess;
import com.techpalle.model.Customer;
import com.techpalle.util.SuccessPage;

@WebServlet("/")
public class MyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String path = request.getServletPath();
		switch(path)
		{
		case "/logCustomer":
			validateCustomer(request,response);
			break;
		case "/regCustomer":
			insertCustomer(request,response);
			break;
		case"/reg":
		   getRegistrationPage(request,response);
			break;
	
		case "/log":
			getLoginPage(request,response);
			break;
		default:
			getStartUpPage(request,response);
			break;
		}
	}

	private void validateCustomer(HttpServletRequest request, HttpServletResponse response)
	{
		//Read the email and password from login.jsp page
		String e = request.getParameter("tbEmaillog");
		String p = request.getParameter("tbPasslog");
		
		//call the method present in dao
		boolean res = DataAccess.validateCustomer(e, p);
		
		//Condition and Redirect User to destination page (success)
		if(res)
		try 
		{
			RequestDispatcher rd = request.getRequestDispatcher("success.jsp");
			
            //Store the successPage class data inside RD
			SuccessPage sp = new SuccessPage();
			request.setAttribute("successDetails", sp);
			
			
			rd.forward(request, response);
		}
		catch (ServletException | IOException e1) 
		{
			e1.printStackTrace();
		}
		else
		{
			getLoginPage(request,response);
		} 
		
	}

	private void insertCustomer(HttpServletRequest request, HttpServletResponse response)
	{
		//Read the data from jsp page
		String n = request.getParameter("tbName");
		String e = request.getParameter("tbEmail");
		long m = Long.parseLong(request.getParameter("tbMobile")) ;
		String p = request.getParameter("tbPass");
		String s = request.getParameter("ddlStates");
		
		//Store that data in customer object
		Customer c = new Customer(n, e, m, p, s);
		//Call insertCustomer method parent in dao by passing above object
		DataAccess.insertCustomer(c);
		
		try
		{
			RequestDispatcher rd = request.getRequestDispatcher("customer_login.jsp");
			rd.forward(request, response);
		} 
		
		
		
		
		catch (ServletException e1)
		{
			e1.printStackTrace();
		} 
		catch (IOException e1)
		{
			e1.printStackTrace();
		}

	}

	private void getRegistrationPage(HttpServletRequest request, HttpServletResponse response) 
	{
		try 
		{
			RequestDispatcher rd = request.getRequestDispatcher("customer_registration.jsp");
	        rd.forward(request, response);
		}
		catch (ServletException e)
		{
			e.printStackTrace();
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
	}

	private void getLoginPage(HttpServletRequest request, HttpServletResponse response) 
	{
		try 
		{
			RequestDispatcher rd = request.getRequestDispatcher("customer_login.jsp");
	        rd.forward(request, response);
		}
		catch (ServletException e)
		{
			e.printStackTrace();
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	

	private void getStartUpPage(HttpServletRequest request, HttpServletResponse response) 
	{
		
		try 
		{
			RequestDispatcher rd = request.getRequestDispatcher("customer_management.jsp");
	        rd.forward(request, response);
		}
		catch (ServletException e)
		{
			e.printStackTrace();
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doGet(request, response);
	}

}

