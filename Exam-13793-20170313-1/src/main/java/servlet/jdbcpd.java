package servlet;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


	public class jdbcpd extends HttpServlet {
		

		/**
		 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
		 */
		protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			HttpSession session=request.getSession();
			
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			String name1=request.getParameter("username");
			
			jdbc j=new jdbc();
			String sql="select first_name from customer where first_name='"+name1+"'";
			
			try {
				ResultSet rs=j.jdbclj().executeQuery(sql);
				
				if(rs.next()){
					
					response.sendRedirect("select");
				}else{
					request.setAttribute("cw", "用户名不存在");
					request.getRequestDispatcher("login.jsp").forward(request, response);
				}
				
				
				
				
				
				
				
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


