package servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;



public class bianji extends HttpServlet {
	

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session=request.getSession();
		
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
	
		String title=request.getParameter("title");
		String description=request.getParameter("description");
		String language=request.getParameter("language");
		String id=request.getParameter("id");
		System.out.println(title+description+language+id);
		
		jdbc j=new jdbc();
		String sql="UPDATE film SET title='"+title+"',description='"+description+"' WHERE film_id='"+id+"'";
		
		try {
			j.jdbclj().executeUpdate(sql);
		    response.sendRedirect("select");
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

