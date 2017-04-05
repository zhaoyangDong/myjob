<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>注册页面</title>
<script src="js/jquery-1.7.2.min.js"></script>
<script type="text/javascript">

$(document).ready(function(){
	
	
	
	 $("#1").bind("blur",function(){
		 var a=true;
		 var b=$("#1").val();
		 if(a){
			if( b.match( /^[\u4E00-\u9FA5]{1,}$/) ){
			 }else{
				a=false;
				alert("请输入汉字");
			 }
		 }
		 
      if(a){ $.ajax({
		  	  type:"POST",
		  	  url:"zhuce",
		  	  data:{"name":$("#1").val(),},
		  	  success:function(msg){
		  		  if(msg=="true"){
		  		
		  		     $("#p1").text("该用户被注册！");
		  		  }else{
		  		    $("#p1").text("请继续输入密码");}
		  		    }
		  		  });
       
      }
		  		 });
	 
	 
	 
	 $("#2").bind("blur",function(){
			var a=$("#2").val();
			if(a.length<6||a.length>8){
				alert("密码应该是6到8位");
			}else{
				
			}
			
		});

	});

</script>
</head>

<body>
<br><br><br><br><br>
<center><font size="8">请注册</font></center>
<br><br><br><br>
<form action="tianjia" method="post" onsubmit="return true">
<table align="center" border="1px" cellspacing="0" height="200px" width="400px">
<tr>
<th height="80px">帐号:<input id="1" type="text" name="username"/>
<font color="red" size="2"><div id="p1">请输入汉字</div>  </font>
</th>
</tr>
<tr>
<th>密码:<input id="2" type="password" name="password"/>
<font size="1"><div id="p2">请输入6到8位密码</div> </font>
</th>
</tr>
<tr height="50px">
<th>
<input type="submit" value="注册"/>
<input type="reset" value="清除">
</th>
</tr>
</table>
</form>
</body>
</html>