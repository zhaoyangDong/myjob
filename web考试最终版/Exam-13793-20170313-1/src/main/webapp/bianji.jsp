<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>编辑页面</title>
</head>
<body>
<br><br><br><br><br><br><br><br>
<center>
<font size="4">编辑</font>
<form action="aa" method="post">
<input value="<%=request.getParameter("id") %>" name="id" type="hidden"/>
<table border="1">
<tr>
<td>
title&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:<input type="text" name="title"/>
</td>
</tr>
<tr>
<td>
description:<input type="text" name="description"/>
</td>
</tr>
<tr>
<td>
language&nbsp;&nbsp;&nbsp;:<select name="language">
<option>English</option>
<option>Italian</option>
<option>Japanese</option>
<option>Mandarin</option>
<option>French</option>
<option>German</option>

</select>
</td>
</tr>
<tr>
<td>
<center><input type="submit" value="提交"/></center>
</td>
</tr>
</table>
</form>
</center>










</body>
</html>