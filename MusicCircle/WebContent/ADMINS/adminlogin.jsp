<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
<meta name="keywords" content="初遇">
<meta name="keywords" content="初遇后台">
<meta name="keywords" content="乐圈">
<meta name="keywords" content="乐圈初遇">
<meta name="keywords" content="初遇App">
<link rel="icon" href="images/tomcat.png" type="image/x-icon"/>
<link href="ADMINS/css/shop.css" type="text/css" rel="stylesheet" />
<link href="ADMINS/skin/default/skin.css" rel="stylesheet" type="text/css" id="skin" />
<link href="ADMINS/css/Sellerber.css" type="text/css"  rel="stylesheet" />
<link href="ADMINS/css/bkg_ui.css" type="text/css"  rel="stylesheet" />
<link href="ADMINS/font/css/font-awesome.min.css"  rel="stylesheet" type="text/css" />
<script src="ADMINS/js/jquery-1.9.1.min.js" type="text/javascript" ></script>
<script src="ADMINS/js/layer/layer.js" type="text/javascript"></script>
<script src="ADMINS/js/bootstrap.min.js" type="text/javascript"></script>
<script src="ADMINS/js/Sellerber.js" type="text/javascript"></script>
<script src="ADMINS/js/shopFrame.js" type="text/javascript"></script>
<script type="text/javascript" src="ADMINS/js/jquery.cookie.js"></script>
<title>登录--初遇后台管理系统</title>
</head>
<body class="login-layout Reg_log_style">
<div class="logintop">    
    <span>欢迎登录初遇后台管理平台</span>    
    <ul>
    <li><a href="http://www.leqtch.com/" target="_blank">首页</a></li>
    <li><a href="#">帮助</a></li>
    <li><a href="#">关于</a></li>
    </ul>    
    </div>
    <div class="loginbody">
		<div class="login-container">
			<div class="center"> <img src="images/logo.png" /></div>
									<div class="space-6"></div>
									<div class="position-relative">
										<div id="login-box" class="login-box widget-box no-border visible">				
		                                  <div class="login-main">
		                                  <div class="clearfix">
			                                  <div class="login_icon"><img src="images/login_img.png" /></div>
												<form class="" action="adminlogin" method="post" style=" width:300px; float:right; margin-right:50px;">
			                                    <h4 class="title_name"><img src="images/login_title.png" /></h4>
													<fieldset>
													<ul>
													   <li class="frame_style form_error"><label class="user_icon"></label><input name="username" type="text" data-name="用户名" placeholder="用户名" id="username" value="${names}"/><i></i></li>
													   <li class="frame_style form_error"><label class="password_icon"></label><input name="userpwd" type="password" placeholder="密码"  data-name="密码" id="userpwd"/><i></i></li>
													   <!-- 验证码暂停用 -->
													   <!-- <li class="frame_style form_error"><label class="Codes_icon"></label><input name="" type="text"   data-name="验证码" id="Codes_text"/><i>验证码</i><div class="Codes_region"><img src="images/yanzhengma.png" width="100%" height="38px"></div></li> -->  
													  </ul>
													    <div class="space"></div>
							                              <div class="clearfix">
							                                  <label class="inline">
							                                      <!-- <input type="checkbox" class="ace"> -->
							                                      <span class="lbl" style="color:red;">${message}</span>
							                                  </label>
							
							                                  <button type="submit" class="login_btn" id="login_btn"> 登&nbsp;&nbsp;&nbsp;录 </button>
							                              </div>
							
							                              <div class="space-4"></div>
							                          </fieldset>
							                      </form>
											</div>
		                      <div class="social-or-login center">
		                          <span class="bigger-110">乐圈APP管理平台</span>
		                      </div>
		
		                      <div class="social-login ">
		                      	初遇APP后台管理系统 -- 乐圈科技
		                      </div>
		                  </div><!-- /login-main -->
		
		          
		          <!-- /widget-body -->
		          </div><!-- /login-box -->
		      </div><!-- /position-relative -->
		  </div>
 	</div>
    <div class="loginbm">版权所有  2017 <a href="#"></a> </div><strong></strong>
	</body>
	<script type="text/javascript">
	$('#login_btn').on('click', function(){
		     var num=0;
			 var str="";
	     $("input[type$='text'],input[type$='password']").each(function(n){
	          if($(this).val()=="")
	          {
	               
				   layer.alert(str+=""+$(this).attr("data-name")+"不能为空！\r\n",{
	                title: '温馨提示',				
					icon:0,								
	          }); 
			    num++;
	            return false;            
	          } 
			 });
			  if(num>0){  return false;}	 	
	          else{
				  layer.alert('请求成功！',{
	               title: '系统提示',				
				   icon:1,		
				  });
				  
				  //调用登录的方法
				  layer.close(index);	
			  }		  		     								
		});
	  $(document).ready(function(){
		 $("input[type='text'],input[type='password']").blur(function(){
	        var $el = $(this);
	        var $parent = $el.parent();
	        $parent.attr('class','frame_style').removeClass(' form_error');
	        if($el.val()==''){
	            $parent.attr('class','frame_style').addClass(' form_error');
	        }
	    });
		$("input[type='text'],input[type='password']").focus(function(){		
			var $el = $(this);
	        var $parent = $el.parent();
	        $parent.attr('class','frame_style').removeClass(' form_errors');
	        if($el.val()==''){
	            $parent.attr('class','frame_style').addClass(' form_errors');
	        } else{
				 $parent.attr('class','frame_style').removeClass(' form_errors');
			}
		});
	 })
	</script>
	
</html>
