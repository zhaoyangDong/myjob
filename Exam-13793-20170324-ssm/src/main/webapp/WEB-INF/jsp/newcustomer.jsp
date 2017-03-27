<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="cn.ssm.dto.page"%>
       <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
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

button {
	background-color: #D9D9F3;
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
        <div id="Content-Left"> <font size="4">
        <a href="<%=request.getContextPath()%>/selectfirstpage">Customer 客户管理</a>
        <br><br>
        <a href="#" id="a1" onclick="a1click()">Film  设置</a><br>
        </font>
        
        
     
        </div>
        <div id="Content-Main"><font size="4">创建Customer</font><br>
           基本信息<br><br><br>
           
           <form action="<%=request.getContextPath()%>/zengjia">
           First Name*:<input type="text" name="firstname" width="80px"><br><br>
           &nbsp;Last Name*:<input type="text" name="lastname"><br><br>
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Emal*:<input type="text" name="emal"><br><br>
           &nbsp;&nbsp;&nbsp;Address*:
                        <select name="address">
                        <c:forEach items="${page.customerlist}" var="tom">
                         <option>${tom.address}</option>
                         </c:forEach>
                         </select>
                          <br><br>
           &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
           &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          
          
           <input type="submit" value="新建"/>
           <input type="reset" value="取消"/>
           
           </form>
                  
        
        
        
        
        </div>
    </div>
    <div class="Clear"></div>
    <div id="Footer">&nbsp;&nbsp;&nbsp;Happy every day
</div>
</div>
</body>
</html>