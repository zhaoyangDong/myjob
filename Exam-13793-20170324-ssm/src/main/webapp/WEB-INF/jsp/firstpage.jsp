<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="cn.ssm.dto.page"%>
       <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<meta content="UTF-8">

<script src="js/jquery-1.7.2.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script type="text/javascript">

	function changePage(obj){
		if(isNaN(obj.value)){
			alert("必须是数字!");
			obj.value=${page.thispage}
			return;
		}else if(obj.value<=0 || obj.value>${page.countpage} ){
			alert("页码必须在有效范围内!");
			obj.value=${page.thispage}
			return;
		}else{
			window.location.href="<%=request.getContextPath()%>/selectfirstpage2?thispage="+obj.value;
		}
	};
	
	function delect(obj){
		var value=obj.value;
		if(confirm("是否删除！！！")){
		window.location.href="<%=request.getContextPath()%>/delect?"+value;
		}
	};
	
	function a1click(){
		alert("还未开发敬请期待！");
		
	};
	

	
  		
  		</script>
<style type="text/css">

#Container{
    width:1350px;
    margin:0 auto;
    background:#99CCFF;
   
}
#Header{
    height:80px;
    background:#99CCFF;
}
#logo{
    padding-left:50px;
    padding-top:20px;
    padding-bottom:50px;
}
#Content{
    height:500px;
    margin-top:20px;
    background:white;
    border:1px dashed black;
    
}
#Content-Left{
    height:480px;
    width:205px;
    margin:10px;
    float:left;
    background:#99CCFF;
}
#Content-Main{
    height:480px;
    width:1100px;
    margin:10px;
    float:left;
    background:#99CCFF;
    overflow:auto;
}

#Footer{
    height:40px;
    background:#99CCFF;
    margin-top:20px;
}
.Clear{
    clear:both;
}
body{
background-color: #FFFFCC;
}
input {
	background-color: #D9D9F3;
}
button {
	background-color: #D9D9F3;
	cursor: pointer;
	
}
table {
	background-color: #D9D9F3;
}

a{
    color:black;
    text-decoration: none;
    
  
}

</style>
</head>
<body>
<div id="Container">
    <div id="Header">
        <div id="logo"><font size="6">WINHERE</font>
              <c:if test="${username!=null}"> 
              &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; 
              &nbsp;  &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
              &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
               &nbsp;  &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
                &nbsp;  &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
                 &nbsp;  &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
                  &nbsp;  &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
                   &nbsp;  &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
                    &nbsp;  &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
                                            欢迎${username}登录！
                                            
     </c:if>
     <a href="<%=request.getContextPath()%>/zhuxiao">退出</a>
          
        </div>
    </div>
    <div id="Content">
        <div id="Content-Left">
        <font size="4">
        <a href="<%=request.getContextPath()%>/selectfirstpage">Customer 客户管理</a>
        <br><br>
        <a href="#" id="a1" onclick="a1click()">Film  设置</a><br>
        </font>
        
     
        </div>
        <div id="Content-Main"><font size="5">客户列表:</font> 
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        
        
        
        <font size="4"><a href="<%=request.getContextPath()%>/gonewcustomer">新建</a></font>
        <br>
        <center>
        <table cellpadding="0" cellspacing="0" border="1px">
   <tr>
    <th >操作</th>
    <th>firstName</th>
   <th>lastName</th>
   <th>address</th>
   <th>email</th>
   <th>customerId</th>
   <th>lastUpdate</th>
   
  </tr> 
  
<c:forEach items="${page.customerlist}" var="tom">
<tr>
 <th><a href="<%=request.getContextPath()%>/goupdatecustomer?&thispage=${page.thispage}&customerId=${tom.customerId}&address=${tom.address}&firstName=${tom.firstName}&lastName=${tom.lastName}&email=${tom.email}">编辑</a>
 
 
 |<button id="${tom.customerId}" value="customerId=${tom.customerId}&thispage=${page.thispage}" onclick="delect(this)" >删除</button></th>
 <th> <input value="${tom.firstName}" style="border-style:none" readonly/></th>
   <th> <input value="${tom.lastName}" style="border-style:none" readonly/></th>
   <th> <input value="${tom.address}" style="border-style:none"/></th>
   <th> <input value="${tom.email}" style="border-style:none" /></th>
   <th> <input value="${tom.customerId}" style="border-style:none" readonly/></th>
   <th> <input value="${tom.lastUpdate}" style="border-style:none" readonly/></th>
  </tr>
</c:forEach>

</table>
<br>
  
          <a href="<%=request.getContextPath()%>/selectfirstpage2?thispage=${page.firstpage}">首页</a>
        <a href="<%=request.getContextPath()%>/selectfirstpage2?thispage=${page.prepage}">上一页</a> 
        
         
         
         
         
         <%--  <!-- 分页第一种方法 -->
    <!-- 如果总页码 小于等于5执行-->
  	<c:if test="${page.countpage<=5}">
  		<c:set var="begin" value="1" scope="page"></c:set>
  		<c:set var="end" value="${page.countpage}" scope="page"></c:set>
  	</c:if>
  	
  <!-- 总页码大于5执行 
      if(当前页码<=3){
                                       显示 1 到 5
      }else if(当前页码>=总页码-2){
                                   显示总页码-4到总页码
     }else{
                                  当前页码-2到当前页码+2
  }
 -->
  	<c:if test="${page.countpage>5}">
		<c:choose>
			<c:when test="${page.thispage<=3}">
				<c:set var="begin" value="1" scope="page"></c:set>
  				<c:set var="end" value="5" scope="page"></c:set>
			</c:when>
			<c:when test="${page.thispage>=page.countpage-2}">
				<c:set var="begin" value="${page.countpage-4}" scope="page"></c:set>
  				<c:set var="end" value="${page.countpage}" scope="page"></c:set>
  			</c:when>
  			<c:otherwise>
  				<c:set var="begin" value="${page.thispage-2}" scope="page"></c:set>
  				<c:set var="end" value="${page.thispage+2}" scope="page"></c:set>
  			</c:otherwise>
		</c:choose>
  	</c:if>
  	
  	<c:forEach begin="${begin}" end="${end}" step="1" var="i">
  		<c:if test="${i == page.thispage}">
  			${i }
  		</c:if>
  		<c:if test="${i != page.thispage}">
  			<a href="<%=request.getContextPath()%>/selectfirstpage2?thispage=${i}">${i }</a>
  		</c:if>
  	</c:forEach>
    <!-- 结束 -->
    --%>

      <%
        page p=new page();
        p=(page)request.getAttribute("page");
        if(p.getCountpage()<=5){
        	for(int i=1;i<p.getCountpage();i++){
        		if(i==p.getThispage()){
        			out.println("<a>"+p.getThispage()+"</a>");
        		}else{
               out.println("<a  href="+request.getContextPath()+"/selectfirstpage2?thispage="+i+">"+i+"</a>");
        		}
           }
       }else{
    	   if(p.getThispage()<=3){
    		   for(int i=1;i<=5;i++){
    			   if(i==p.getThispage()){
    				   out.println("<a>"+p.getThispage()+"</a>");
    			   }else{
    				   out.println("<a  href="+request.getContextPath()+"/selectfirstpage2?thispage="+i+">"+i+"</a>");
    			   }
           	}
    	   }else if(p.getThispage()>=p.getCountpage()-2){
    		   for(int i=p.getCountpage()-4;i<=p.getCountpage();i++){
    			   if(i==p.getThispage()){
      				 out.println("<a>"+p.getThispage()+"</a>");
      			 }else{
      			 out.println("<a  href="+request.getContextPath()+"/selectfirstpage2?thispage="+i+">"+i+"</a>");
      			 }
    		   }
    	 }else{
    		 for(int i=p.getThispage()-2;i<=p.getThispage()+2;i++){
    			 if(i==p.getThispage()){
    				 out.println("<a>"+p.getThispage()+"</a>");
    			 }else{
    			 out.println("<a  href="+request.getContextPath()+"/selectfirstpage2?thispage="+i+">"+i+"</a>");
    			 }
    		 }
    	 }
       }
      %>
   
         
          <a href="<%=request.getContextPath()%>/selectfirstpage2?thispage=${page.nextpage}">下一页</a>
            <a href="<%=request.getContextPath()%>/selectfirstpage2?thispage=${page.lastpage}">尾页</a>
               跳到<input type="text" value="${page.thispage }" style="width: 40px" onchange="changePage(this)"/>页
                 共${page.countrow}条记录
                 共${page.countpage}页       
 </center>
      
        </div>
    </div>
    <div class="Clear"></div>
    <div id="Footer">&nbsp;&nbsp;&nbsp;Happy every day
</div>
</div>
</body>
</html>