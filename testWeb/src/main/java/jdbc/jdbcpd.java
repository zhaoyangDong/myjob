package jdbc;

import java.io.IOException;


import java.sql.ResultSet;
import java.sql.SQLException;


import javax.servlet.ServletException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;






/**
 * Servlet implementation class jdbcpd
 */
@WebServlet("/jdbcpd")
public class jdbcpd extends HttpServlet {
	

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session=request.getSession();
		
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		String name=request.getParameter("username");
		String mima=request.getParameter("password");
		
		
		System.out.println(name+mima);
		//���ô���������sql���
		jdbc j=new jdbc();
		String sql="select mima from user where name='"+name+"'";
		String password ="";
		if(name!=null&&mima!=null&&name!=""&&mima!=""){

		try {
			
			ResultSet rs=j.jdbclj().executeQuery(sql);
			
			while(rs.next()){
		    password =rs.getString("mima");
	        System.out.println(password);
			
			}
			if(password.equals(mima)){
				
				session.setAttribute("yh", name);
				response.sendRedirect("2.jsp");
				
				}
			else{
				
				
				request.setAttribute("cw", "�˺Ż����������");
				request.getRequestDispatcher("1.jsp").forward(request, response);
		
				
			}
			
	         } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			
			
			request.setAttribute("cw1", "�˺����벻��Ϊ��");
			request.getRequestDispatcher("1.jsp").forward(request, response);
			
			
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
