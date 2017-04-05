package servlet;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;






public class jdbc {
	

		public Statement jdbclj() throws ClassNotFoundException, SQLException, FileNotFoundException, IOException{
			//1.注册数据库驱动
			 //DriverManager.registerDriver(new Driver());
			 Properties prop=new Properties();
			
			// InputStream inputStream = jdbc.class.getClassLoader().getResourceAsStream("config.properties");
			// prop.load(inputStream);
			 
			 //类加载器。获取当前的路径
			prop.load(new FileReader(jdbc.class.getClassLoader().getResource("config.properties").getPath()));
	 
		    Class.forName(prop.getProperty("dirver"));
		    //2.获取数据库连接
					Connection conn=DriverManager.getConnection(prop.getProperty("url"), prop.getProperty("user"), prop.getProperty("password"));
			        //3.获取传输器对象
					Statement   stat=conn.createStatement();
					
					return stat;
		

		}
}
