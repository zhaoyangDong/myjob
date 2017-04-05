package cn.ssm.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.plaf.synth.SynthStyle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.ssm.dto.Film;
import cn.ssm.dto.address;
import cn.ssm.dto.customer;
import cn.ssm.dto.page;
import cn.ssm.service.Filmservice;

@Controller
public class Firstcontroller {
	
	@Autowired
	private Filmservice filmservice;
	
	//�жϵ�½
	  @RequestMapping("/login")
	     public String login(HttpServletRequest request,HttpServletResponse response){
	    	HttpSession session= request.getSession();
	     	String username=request.getParameter("username");
	     if(filmservice.findcustomer(username).isEmpty()){
	    	 request.setAttribute("cw", "�û������󣡣�");
	    	 return "login";
	    	 
	     }
	     
	     session.setAttribute("username",username);
	     
	     return "redirect:selectfirstpage";
	     	
	  }
	  
	
	  @RequestMapping("/selectfirstpage")
	     public String selectfirstpage(HttpServletRequest request,
	    		 HttpServletResponse response,Map<String, Object> map){
	    	HttpSession session= request.getSession();
	    	int thispage=1;
	    	 page p=new page();
	         //--��ǰҳ
	        p.setThispage(thispage);
	        //--ÿҳ��¼��
	        int rowperpage = 15;
	        p.setRowperpage(rowperpage);
	        //--���ж�����
	        int countrow = filmservice.selectcustomercount();
	        p.setCountrow(countrow);
	        //--���ж���ҳ
	        int countpage = countrow/rowperpage+(countrow%rowperpage==0?0:1);
	        p.setCountpage(countpage);
	        //--��ҳ
	        p.setFirstpage(1);
	        //--βҳ
	        p.setLastpage(countpage);
	        //--��һҳ
	        p.setPrepage(thispage==1?1:thispage-1);
	        //��һҳ 
	        p.setNextpage(thispage == countpage ? countpage : thispage+1);
	        //--��ǰҳ���� 
	        p.setCustomerlist( filmservice.findallcustomer((thispage-1)*rowperpage));
	        map.put("page",p);
	          
	    	
	     return "firstpage";
	     	
	  }
	  
	  @RequestMapping("/selectfirstpage2")
	     public String selectfirstpage2(HttpServletRequest request,
	    		 HttpServletResponse response,Map<String, Object> map){
	    	HttpSession session= request.getSession();
	    	String page=request.getParameter("thispage");
	    	int thispage=Integer.parseInt(page);
	    	 page p=new page();
	         //--��ǰҳ
	        p.setThispage(thispage);
	        //--ÿҳ��¼��
	        int rowperpage = 15;
	        p.setRowperpage(rowperpage);
	        //--���ж�����
	        int countrow = filmservice.selectcustomercount();
	        p.setCountrow(countrow);
	        //--���ж���ҳ
	        int countpage = countrow/rowperpage+(countrow%rowperpage==0?0:1);
	        p.setCountpage(countpage);
	        //--��ҳ
	        p.setFirstpage(1);
	        //--βҳ
	        p.setLastpage(countpage);
	        //--��һҳ
	        p.setPrepage(thispage==1?1:thispage-1);
	        //��һҳ 
	        p.setNextpage(thispage == countpage ? countpage : thispage+1);
	        //--��ǰҳ���� 
	        p.setCustomerlist( filmservice.findallcustomer((thispage-1)*rowperpage));
	        map.put("page",p);
	          
	    	
	     return "firstpage";
	     	
	  }
	//��������ҳ��
	    @RequestMapping("/gonewcustomer")
	    public String gonewcustomer(HttpServletRequest request,
	    		HttpServletResponse response,Map<String, Object> map){
	    	
	    	page p=new page();
	    	
	    	p.setCustomerlist(filmservice.findaddress());
	    	 map.put("page", p);
	    	 return "newcustomer";
	    }
	    
	  //����customer�ķ���
	    @RequestMapping("/zengjia")
	    public String zengjia(HttpServletRequest request,
	    		HttpServletResponse response,Map<String, Object> map){
	            String firstname=request.getParameter("firstname");
	            String lastname=request.getParameter("lastname");
	            String email=request.getParameter("emal");
	            String address=request.getParameter("address");
	    	    
	            String addressid1=filmservice.findaddressid(address);
	            int addressid=Integer.parseInt(addressid1);
	    	     customer c=new customer();
	    	     c.setStoreId(2);
	    	     c.setFirstName(firstname);
	    	     c.setLastName(lastname);
	    	     c.setEmail(email);
	    	     c.setAddressId(addressid);
	    	     c.setCreateDate("2017-03-24 22:04:37");
	    	     filmservice.insertcustomer(c);
	    	     
	            
	    	 return "redirect:selectfirstpage";
	    }
	    
	    
	    //ɾ��customer�ķ���
	      @RequestMapping("/delect")
	      public String delect(HttpServletRequest request,HttpServletResponse response){
	      	HttpSession session=request.getSession();
	      	String customerId=request.getParameter("customerId");
	      	int customerid=Integer.parseInt(customerId);
	      	filmservice.deletecustomer(customerid);
	      	String thispage=request.getParameter("thispage");
	      	request.setAttribute("thispage", thispage);
	      	return "forward:selectfirstpage2";
	      }
	    
	      
	      //����updatecustomerҳ��ȥ��
	      @RequestMapping("/goupdatecustomer")
	      public String goupdatecustomer(HttpServletRequest request,HttpServletResponse response,
	    		  Map<String, Object> map){
	      	HttpSession session=request.getSession();
	      	
	      	String customerId=request.getParameter("customerId");
	      	String address=request.getParameter("address");
	      	String firstName=request.getParameter("firstName");
	      	String lastName=request.getParameter("lastName");
	      	String email=request.getParameter("email");
	        
	      	customer c=new customer();
	      	int customerid=Integer.parseInt(customerId);
	      	c.setCustomerId(customerid);
	      	c.setFirstName(firstName);
	      	c.setLastName(lastName);
	      	c.setEmail(email);
	      	c.setAddress(address);
	      	
	      	String thispage=request.getParameter("thispage");
	    	int thispage1=Integer.parseInt(thispage); 
	    	
	      	page p=new page();
	      	p.setThispage(thispage1);
	    	p.setCustomerlist(filmservice.findaddress());
	    	
	    	
	    	map.put("page", p);
	      	map.put("customer", c);
	      	
	      	return "updatecustomer";
	      }
	    
	    
	    //����customer�ķ���
	      @RequestMapping("/updatecustomer")
	      public String updatecustomer(HttpServletRequest request,HttpServletResponse response){
	      	HttpSession session=request.getSession();
	      	String thispage=request.getParameter("thispage");
	      	
	      	String customerId=request.getParameter("customerId");
	      	String firstName=request.getParameter("firstName");
	      	String lastName=request.getParameter("lastName");
	      	String email=request.getParameter("email");
	      	String address=request.getParameter("address");
	      	
	      	customer c= new customer();
	      	int customerid=Integer.parseInt(customerId);
	      	c.setCustomerId(customerid);
	      	c.setFirstName(firstName);
	      	c.setLastName(lastName);
	      	c.setEmail(email);
	      	String addressId=filmservice.findaddressid(address);
	      	int addressid=Integer.parseInt(addressId);
	      	c.setAddressId(addressid);
	      	
	      	filmservice.updatecustomer(c);
	      	request.setAttribute("thispage", thispage);
	      	
	      	return "forward:selectfirstpage2";
	      }

	      //ע������
    @RequestMapping("/zhuxiao")
    public String zhuxiao(HttpServletRequest request,HttpServletResponse response){
    	HttpSession session=request.getSession();
    	session.invalidate();
    	return "login";
    }


}
