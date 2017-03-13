package servlet;



import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.film;


	public class jdbcxz extends HttpServlet {
		

		/**
		 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
		 */
		protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			HttpSession session=request.getSession();
			
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
		    
			jdbc j=new jdbc();
			String sql="select film_id,title, description,name from film ,language where film.language_id=language.language_id";
			try {
				ResultSet rs=j.jdbclj().executeQuery(sql);
				System.out.println("ÄãºÃ");
				ArrayList<film> list=new ArrayList<film>();
			   while(rs.next()){
				   film f=new film();
				   f.setFilm_id(rs.getString("film_id"));
				   f.setTile(rs.getString("title"));
				   f.setDescription(rs.getString("description"));
				   f.setLanguage(rs.getString("name"));
				   list.add(f);   
			   }
			   
			   request.setAttribute("list", list);
			   request.getRequestDispatcher("success.jsp").forward(request, response);
				
				
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
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


