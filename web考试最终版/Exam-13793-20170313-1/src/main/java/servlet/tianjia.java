package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class tianjia extends HttpServlet {
	

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session=request.getSession();
	    request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		
		String title =request.getParameter("title");
		String description =request.getParameter("description");
		String language =request.getParameter("language");
		
		System.out.println("去吧"+title+description+language);
		//1.首先添加language
		double b = Math.random() * 100;
		int language_id=(int) b;
		Timestamp  ts=new Timestamp(new Date().getTime());
		jdbc j=new jdbc();
		String sql="insert into language values("+language_id+",'"+language+"','"+ts+"')";
		
		//2.给film加数据
		double d = Math.random() * 10000;
		int film_id=(int) d;
		System.out.println(film_id+"珠江");

		 int id1=1;
	        if(language.equals("Italian")){
	        	id1=2;
	        }else if(language.equals("Japanese")){
	            id1=3;
	        }else if(language.equals("Mandarin")){
	        	id1=4;
	        }else if(language.equals("French")){
	        	id1=5;
	        }else if(language.equals("German")){
	        	id1=6;
	        }
		
		jdbc j1=new jdbc();
		String sql1="insert into film(film_id,title,description,language_id) values("+film_id+",'"+title+"','"+description+"',"+id1+")";
		
		
		
		
		
		try {
			int c=j.jdbclj().executeUpdate(sql);
			System.out.println(c);
			int a=j1.jdbclj().executeUpdate(sql1);
			System.out.println(a);
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