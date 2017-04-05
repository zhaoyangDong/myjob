<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登陆界面</title>
</head>
<body>
<br><br><br><br><br>
<center><font size="8">欢迎登录</font></center>
<br><br><br><br>
<form action="denglu" method="post">
<table align="center" border="1px" cellspacing="0" height="200px" width="400px">
<tr>
<th height="50px">帐号:<input type="text" name="username"/></th>
</tr>
<tr height="50px">
<th>
<input type="submit" value="登录"/>
</th>
</tr>
</table>

</form>
<br>
${cw}
<center>
</center>
</body>
</html>