<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加页面</title>
</head>
<body>
<center>
<br><br><br><br><br>
<font size="4">添加界面</font>
<br>
<form action="bb" method="post">
<table cellpadding="0" cellspacing="0" border="1" >
<tr>
<td>
title&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:<input type="text" name="title" /><br>
</td>
</tr>
<tr>
<td>
description:<input type="text" name="description"/><br>
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

</table>
<br>
<center><input type="submit" value="添加"/></center>
</form>
</center>
</body>
</html>