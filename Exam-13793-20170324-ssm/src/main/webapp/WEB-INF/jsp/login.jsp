<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style type="text/css">
table{
    background-image: url("<%=request.getContextPath() %>/img/1.jpg");
 
}
body {
	background-image: url("<%=request.getContextPath() %>/img/6.jpg");
}

</style>
</head>
<body>
<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font size="6">13793 董朝阳</font>
<center>
<br><br>
<br><br>
<br><br>
<form action="<%=request.getContextPath()%>/login" method="post">
<font size="6">欢迎登录<br></font>
<table align="center" border="1px" cellspacing="0" height="300px" width="500px">
<tr>
<th align="left" height="40px">销售管理系统</th>
</tr>
<tr>
<th height="100px">
帐号:<input type="text" name="username"/><br>&nbsp;${cw}<br>
密码:<input type="text" name="password" value="密码可以不用填写" readonly/></th>
</tr>
<tr height="50px">
<th>
<input type="submit" value="登录"/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" ><font color="black ">注册</font></a>
</th>
</tr>
</table>
</form>
</center>


</body>
</html>