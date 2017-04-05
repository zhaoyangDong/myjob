<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
       <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>显示页面</title>
</head>
<body>
  <table cellpadding="0" cellspacing="0" border="1px">
   <tr>
    <th>film_id</th>
   <th>tile</th>
   <th>description</th>
   <th>language</th>
   <th>删除</th>
   <th>编辑</th>
  </tr> 
<c:forEach items="${list}" var="tom"><br>
<tr>
   <th> <input value="${tom.film_id}" style="border-style:none" readonly/></th>
   <th> <input value="${tom.tile}" style="border-style:none" readonly/></th>
   <th> <input value="${tom.description}" style="border-style:none" readonly/></th>
   <th> <input value="${tom.language}" style="border-style:none" readonly/></th>
   <th><a href="sc?film_id=${tom.film_id}&language=${tom.language}">删除</a></th>
   <th><a href="bianji.jsp?id=${tom.film_id}">编辑</a></th>
  </tr>
</c:forEach>
</table>

</body>
</html>