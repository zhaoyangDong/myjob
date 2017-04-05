<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>maven项目下的完整导航栏</title>
<style type="text/css">
*{
 margin: 0;
 padding: 0;
}
body{
   background-color: #AAAAAA;
}


#nav{
    background-color: #ccc;
    width: 100%;
    margin: 0 auto;
    height: 40px;
}
ul{
   position:relative;
   list-style: none;
   left: 8%;
   
}
ul li{
    
    float: left;
    padding: 0 20px;    
    line-height: 40px;
    font-family: "微软雅黑";
    
}

a{
  text-decoration: none;
  color: #000;
  display: block;
  padding: 0 20px;

}
a:HOVER {
	background-color: #930;
	color: #fff;
}

.l1{
  float: none;
  background-color: #ccc;
}
.l1 a:HOVER{
   color: #00f;
   background-color: #999;
}
.u1{
   display: none;
}

ul li:HOVER .u1{
	display: inline;
	
}

.d1{
   position:absolute;
   width: 36px;
   height:30px;
   margin: 5px 0px 0px 80px;
   

}

.d2{
   display:none;
   widows: 219px;
   height: 332px;
   
}

ul li:HOVER .d2{
	display: inline;
	
}



.d3{

    display:none;
    width: 350px;
    height: 210px;
    background-color: gray;
    
    
    
    
}
ul li:HOVER .d3{
	display: inline-block;
	
}

.i1{
   display: inline-block;
}


.d6{
   height: 80px;
   background-color: white;

}
.da1{
    position:relative;
    left: 36px;
    width: 140px;
}
.da2{
    position:relative;
    left: 36px;
    width: 140px;
}

.d6 a:HOVER{
   color: red;
   background-color: white;
}

.d7{
   display:none;
   background-color: #ccc;
   width:300px;
   height: 300px;
   
}
ul li:HOVER .d7{
	display: inline-block;
	
}
.u1{
    left: 10px;

}
.d{
   display: inline-block;
   font-size: 3px;
}

.x1{
   height: 170px;
   background-color: #ccc;

}
.d4{
   float: left;
}
.xa1{
    display: inline-block;
    font-size: 8px;
}
.xa1:HOVER {
	color: red;
	background-color: #ccc;
}

.x2a{
display:inline-block;
  position: relative;
  left: 100px;
}

.x2a:HOVER{
   color: red;
   background-color: gray;
   

}

ul li:HOVER .d3{
	display: inline-block;
	
}
.a1{
   font-size: 23px;
}

.a1:HOVER {
	background-color: #ccc;
	color: black;
}









</style>
</head>
<body>
<div id="nav">
     <div class="d1">
      <img  src="img/1.jpg">     
     </div>


<ul>
   <li><a href="#" class="a1">起点中文网</a>
        <ul class="u1">
            <li class="l1"><a href="#">起点女神网</a></li>
            <li class="l1"><a href="#">创世中文网</a></li>
            <li class="l1"><a href="#">云起书院</a></li>
            
        </ul>
   </li>
         
   
   
   <li><a href="#">手机阅读</a>
         <div class="d2">  
           <img src="img/2.jpg">
           <div class="d6">
               <a href="#" class="da1">app store 下载</a>
               <a href="#" class="da2">Android  下载</a>
           </div>
         </div>
   
   </li>
   
   <li><a href="#">最近阅读</a>
        
        <div class="d3">
            <div class="x1">
            <div class="d4">
            <img src="img/3.jpg" class="i1">
            </div>
             <a href="#" class="xa1">寒天地</a><br>
             <font size="1"> 最新章节:</font>
             <a href="#" class="xa1">第十三章 滴血换新河</a><br>
             <font size="1"> 看到:</font>
             <a href="#" class="xa1">第一章 沉沦低于九万年</a>
             <a href="#" class="xa1">继续阅读</a>
            </div>
           <div class="x2">
               <a href="#" class="x2a">查找全部></a>
           </div>
        </div>
        
   </li>
    
    
     <li><a href="#">快速导航</a>

       <div class="d7">
                 <p>分类频道</p>
            <ul class="u1">
                 <a href="#" class="d">玄幻</a>
                 <a href="#" class="d">武侠</a>
                 <a href="#" class="d">武侠</a>
                 <a href="#" class="d">仙侠</a>
                 <a href="#" class="d">都市</a>
                 <a href="#" class="d">历史</a>
                 <a href="#" class="d">军事</a>
                <a href="#" class="d">游戏</a>
                 
                 <a href="#" class="d">体育</a>
                <a href="#" class="d">科幻</a>
                 <a href="#" class="d">灵异</a>
                <a href="#" class="d">二次元</a>
                <a href="#" class="d">完本</a>
                 <a href="#" class="d">女生</a>
                <a href="#" class="d">三江</a>
            </ul>
           <p>其他</p>
            <a href="#" class="d">投稿</a>
                 <a href="#" class="d">签约</a>
                <a href="#" class="d">福利</a>
       </div>
    
   </li>
</ul>
</div>



</body>
</html>