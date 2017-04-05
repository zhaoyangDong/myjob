package servlet;

import java.io.IOException;
import java.sql.ResultSet;
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
        
		
		jdbc j=new jdbc();
		String sql="UPDATE film SET title='"+title+"',description='"+description+"' WHERE film_id='"+id+"'";
		
		try {
			j.jdbclj().executeUpdate(sql);
		    
		    jdbc j2=new jdbc();
		   ResultSet rs=j2.jdbclj().executeQuery("select language_id from film where film_id='"+id+"'");
		   while(rs.next()){
		 String s=  rs.getString("language_id");
		 int s1=Integer.parseInt(s);
         jdbc j3=new jdbc();
		 int rs2=j3.jdbclj().executeUpdate("update film set language_id="+id1+" where film_id="+id+"");
		 response.sendRedirect("select");
		
		       
		   }
		   
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

