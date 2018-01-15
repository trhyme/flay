<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
<link rel="icon" href="images/tomcat.png" type="image/x-icon"/>
<link href="ADMINS/css/shop.css" type="text/css" rel="stylesheet" />
<link href="ADMINS/skin/default/skin.css" rel="stylesheet" type="text/css" id="skin" />
<link href="ADMINS/css/Sellerber.css" type="text/css"  rel="stylesheet" />
<link href="ADMINS/css/bkg_ui.css" type="text/css" rel="stylesheet" />
<link href="ADMINS/font/css/font-awesome.min.css"  rel="stylesheet" type="text/css" />
<script src="ADMINS/js/jquery-1.9.1.min.js" type="text/javascript" ></script>
<script src="ADMINS/js/layer/layer.js" type="text/javascript"></script>
<script src="ADMINS/js/bootstrap.min.js" type="text/javascript"></script>
<script src="ADMINS/js/Sellerber.js" type="text/javascript"></script>
<script src="ADMINS/js/shopFrame.min.js" type="text/javascript"></script>
<script src="ADMINS/js/jquery.nicescroll.js" type="text/javascript"></script>
<script type="text/javascript" src="ADMINS/js/jquery.cookie.js"></script> 
<script type="text/javascript" src="js/${menuJS}"></script>	

<title>初遇--后台管理系统</title>
</head>
	<body>
		<div class="Sellerber" id="Sellerber">
	 	<!--顶部-->
	  	<div class="Sellerber_header apex clearfix" id="Sellerber_header">
	   	<div class="l_f logo header"><!-- <img src="images/logo_03.png" /> --></div>
	   	<div class="r_f Columns_top clearfix header">
	   	<!--<div class="time_style l_f"><i class="fa  fa-clock-o"></i><span id="time"></span></div>-->
	   
	   	<div class="news l_f "><a href="#" class="fa  fa-bell "></a><em>5</em></div>
	     <div class="administrator l_f">
	       <img src="images/avatar.png"  width="36px"/><span class="user-info">欢迎你,超级管理员</span><i class="glyph-icon fa  fa-caret-down"></i>
	       <ul class="dropdown-menu">
	        <li><a href="#"><i class="fa fa-user"></i>个人信息</a></li>
	        <li><a href="#"><i class="fa fa-cog"></i>系统设置</a></li>
	        <li><a href="javascript:void(0)" id="Exit_system"><i class="fa fa-user-times"></i>退出</a></li>
	       		</ul>
	     		</div>
	      
	   		</div>
	  	</div>
	<!--左侧-->
	  <div class="Sellerber_left menu" id="menuBar">
	    <div class="show_btn" id="rightArrow"><span></span></div>
	    <div class="side_title"><a title="隐藏" class="close_btn"><span></span></a></div>
	    <div class="menu_style" id="menu_style">
	    <div class="list_content">
	    <div class="skin_select">
	      <ul class="dropdown-menu dropdown-caret">
	         <li><a class="colorpick-btn selected" href="javascript:ovid()" data-val="default"  id="default" style="background-color:#222A2D" ></a></li>
	         <li><a class="colorpick-btn" href="javascript:ovid()" data-val="blue" style="background-color:#438EB9;" ></a></li>
	         <li><a class="colorpick-btn" href="javascript:ovid()" data-val="green" style="background-color:#72B63F;" ></a></li>
	         <li><a class="colorpick-btn" href="javascript:ovid()" data-val="gray" style="background-color:#D0D0D0;" ></a></li>
	         <li><a class="colorpick-btn" href="javascript:ovid()" data-val="red" style="background-color:#FF6868;" ></a></li>
	         <li><a class="colorpick-btn" href="javascript:ovid()" data-val="purple" style="background-color:#6F036B;" ></a></li>
	        </ul>     
	     </div>
	     <div class="search_style">
	        <form action="#" method="get" class="sidebar_form">
			 <div class="input-group">
				<input type="text" name="q" class="form-control"><span class="input-group-btn"><a class="btn_flat" href="javascript:" onclick=""><i class="fa fa-search"></i></a></span>
			 </div>
	        </form>
	     </div>
	     <!--左侧菜单栏目-->
	     <div class="column_list" id="column_list"></div>  
	    </div>
	  </div>
	 </div>
	<!--内容-->
	  <div class="Sellerber_content" id="contents">
	    <div class="breadcrumbs" id="breadcrumbs">
	       <a id="js-tabNav-prev" class="radius btn-default left_roll" href="javascript:;"><i class="fa fa-backward"></i></a>
	       <div class="breadcrumb_style clearfix">
		  <ul class="breadcrumb clearfix" id="min_title_list">
	        <li class="active home"><span title="我的桌面" data-href="index.html"><i class="fa fa-home home-icon"></i>首页</span></li>
	      </ul>
	      </div>
	       <a id="js-tabNav-next" class="radius btn-default right_roll" href="javascript:;"><i class="fa fa-forward"></i></a>
	       <div class="btn-group radius roll-right">
			 <a class="dropdown tabClose" data-toggle="dropdown" aria-expanded="false">页签操作<i class="fa fa-caret-down" style="padding-left: 3px;"></i></a>
				<ul class="dropdown-menu dropdown-menu-right" id="dropdown_menu">
					<li><a class="tabReload" href="javascript:void();">刷新当前</a></li>
					<li><a class="tabCloseCurrent" href="javascript:void();">关闭当前</a></li>
					<li><a class="tabCloseAll" href="javascript:void();">全部关闭(首页)</a></li>
					<li><a class="tabCloseOther" href="javascript:void();">除此之外全部关闭</a></li>
				</ul>
			</div>
			<a href="javascript:void()" class="radius roll-right fullscreen"><i class="fa fa-arrows-alt"></i></a>
	    </div>
	  <!--具体内容-->  
	  <div id="iframe_box" class="iframe_content">
	  <div class="show_iframe index_home" id="show_iframe">
	       <iframe scrolling="yes" class="simei_iframe" frameborder="0" src="indexhome" name="iframepage" data-href="index.html"></iframe>
	       </div>
	      </div>
	  </div>
	<!--底部-->
	  <div class="Sellerber_bottom info" id="bottom">
	  <span class="l_f">版权所有：成都乐圈科技有限公司</span>
	  <span id="time" class="r_f"></span>
	  </div> 
	 </div>
	</body>
<script>
//设置框架
 $(function() {  		 
	 $("#Sellerber").frame({
		float : 'left',
		color_btn:'.skin_select',//伸缩按钮
		logo_img:'images/logo_01.jpg',//logo地址链接
		header:0,//顶部高度
		bottom:30,//底部高度
		menu:200,//菜单栏宽度
		Sellerber_menu:'.list_content', //左侧栏目
		Sellerber_header:'.Sellerber_header',//顶部栏目	
	  
	 }); 
});
//时间设置
  function currentTime(){ 
   var weekday=new Array(7)
	weekday[0]="星期一"
	weekday[1]="星期二"
	weekday[2]="星期三"
	weekday[3]="星期四"
	weekday[4]="星期五"
	weekday[5]="星期六"
	weekday[6]="星期日"	
    var d=new Date(),str='';	
    str+=d.getFullYear()+'年'; 
    str+=d.getMonth() + 1+'月'; 
    str+=d.getDate()+'日'; 
    str+=d.getHours()+'时'; 
    str+=d.getMinutes()+'分'; 
    str+= d.getSeconds()+'秒'+'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;';
	str+=weekday[d.getDay()-1]; 
    return str; 
} 
setInterval(function(){$('#time').html(currentTime)},1000); 
$('#Exit_system').on('click', function(){
      layer.confirm('是否确定退出系统？', {
     btn: ['是','否'] ,//按钮
	 icon:2,
    }, 
	function(){
	  location.href="adminloginout";
        
    });
});
</script>
<script type="text/javascript">
$("#menu_style").niceScroll({  
	cursorcolor:"#888888",  
	cursoropacitymax:1,  
	touchbehavior:false,  
	cursorwidth:"5px",  
	cursorborder:"0",  
	cursorborderradius:"5px"  
}); 


</script>
</html>

