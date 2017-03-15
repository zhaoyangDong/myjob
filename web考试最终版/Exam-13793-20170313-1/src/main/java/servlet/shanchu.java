package servlet;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


	
public class shanchu extends HttpServlet {
		

		/**
		 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
		 */
		protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			HttpSession session=request.getSession();
			
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			
			String id1=request.getParameter("film_id");
			int id=Integer.parseInt(id1);
			String language=request.getParameter("language");
		    System.out.println(id+language+"0.0");
		    jdbc j=new jdbc();
		    String sql1="delete from film where film_id='"+id+"'";
		    try {
		    	//É¾³ýÍâ¼ü
		    	j.jdbclj().execute("set foreign_key_checks=0");
		    	//Ö´ÐÐÉ¾³ýÓï¾ä
				int rs=j.jdbclj().executeUpdate(sql1);
				//»¹Ô­Íâ¼ü
				j.jdbclj().execute("set foreign_key_checks=1");
				
				response.sendRedirect("select");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		    
		    		
		}

		/**
		 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
		 */
		protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			doGet(request, response);
		}

}
