<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
<link href="css/shop.css" type="text/css" rel="stylesheet" />
<link href="css/Sellerber.css" type="text/css"  rel="stylesheet" />
<link href="css/bkg_ui.css" type="text/css"  rel="stylesheet" />
<link href="font/css/font-awesome.min.css"  rel="stylesheet" type="text/css" />
<script src="js/jquery-1.9.1.min.js" type="text/javascript" ></script>
<script type="text/javascript" src="js/Validform/Validform.min.js"></script>
<script type="text/javascript" src="js/jquery.cookie.js"></script>
<script src="js/shopFrame.js" type="text/javascript"></script>
<script src="js/Sellerber.js" type="text/javascript"></script>
<script src="js/layer/layer.js" type="text/javascript"></script>
<title>录制声音文案添加</title>
</head>
	<body>
		<div class="margin add_administrator" id="page_style">
	    <div class="add_style add_administrator_style">
	    <div class="title_name">录制声音文案添加</div>
	    <form action="voicesofferadd" method="post" id="form-admin-add" onsubmit="return checkLoad();">
	    <ul>
	     <li class="clearfix">
	     <label class="label_name col-xs-2 col-lg-2"><i>*</i>文案标题：</label>
	     <div class="formControls col-xs-6">
	     <input type="text" placeholder="文案标题" class="input-text col-xs-12" value="" id="title" name="title" datatype="*2-16" nullmsg="文案标题不能为空"></div>
	    <div class="col-4"> <span class="Validform_checktip"></span></div>
	     </li>
	     <li class="clearfix">
	      <label class="label_name col-xs-2 col-lg-2"><i class="c-red">*</i>是否可用：</label>
	      <div class="formControls col-xs-6"> <span class="select-box" style="width:150px;">
					<select class="select" id="strus" name="strus" size="1">
						<option value="1">可用</option>
						<option value="0">不可用</option>
					</select>
					</span>
	         </div>
	     </li>
	     <li class="clearfix">
				<label class="label_name col-xs-2 col-lg-2"><i class="c-red">*</i>文案内容：</label>
				<div class="formControls col-xs-6">
					<textarea name="infos" id="infos" cols="" rows="" class="textarea col-xs-12" placeholder="说点什么...100个字符以内" dragonfly="true" onkeyup="checkLength(this);" nullmsg="文案标题不能为空"></textarea>
					<span class="wordage">剩余字数：<span id="sy" style="color:Red;">100</span>字</span>
				</div>
				<div class="col-4"> <span class="Validform_checktip"></span></div>
			</li>
	         <li class="clearfix">
				<div class="col-xs-2 col-lg-2">&nbsp;</div>
				<div class="col-xs-6">
		  <input class="btn button_btn bg-deep-blue " type="submit" id="Add_Administrator" value="提 交">
	      <input name="reset" type="reset" class="btn button_btn btn-gray" value="取消重置" />
	      <a href="javascript:ovid()" onclick="javascript :history.back(-1);" class="btn btn-info button_btn"><i class="fa fa-reply"></i> 返回上一步</a>
				</div>
			</li>
	    </ul>
	    </form>
	    </div>
	    <div class="split_line"></div>
	    <div class="Notice_style l_f">
	      
	    </div>
	</div>
	</body>
	<script>
	
	/*******滚动条*******/
	$("body").niceScroll({  
		cursorcolor:"#888888",  
		cursoropacitymax:1,  
		touchbehavior:false,  
		cursorwidth:"5px",  
		cursorborder:"0",  
		cursorborderradius:"5px"  
	});
	//表单验证提交
	$("#form-admin-add").Validform({		
			 tiptype:2,
			callback:function(data){
			//form[0].submit();
			if(data.status==1){ 
	                layer.msg(data.info, {icon: data.status,time: 1000}, function(){ 
	                    location.reload();//刷新页面 
	                    });   
	            } 
	            else{ 
	               // layer.msg(data.info, {icon: data.status,time: 3000}); 
	            } 		  
				//var index =parent.$("#iframe").attr("src");
				//parent.layer.close(index);
				//
			}				
		});
	
	
	function checkLoad(){
		var x=$("#infos").val();
		if(x.replace(/[  ]/g,"")==""){
			$("#infos").focus();
			$("#infos").val("");
			$("#infos").attr("placeholder","文案内容不能为空");
			return false;
		}else{
			return true;
		}
	}
	
	//字数限制
	function checkLength(which) {
		var maxChars = 100; //
		if(which.value.length > maxChars){
		   layer.open({
		   icon:2,
		   title:'提示框',
		   content:'您输入的字数超过限制!',	
	    });
			// 超过限制的字数了就将 文本框中的内容按规定的字数 截取
			which.value = which.value.substring(0,maxChars);
			return false;
		}else{
			var curr = maxChars - which.value.length; //250 减去 当前输入的
			document.getElementById("sy").innerHTML = curr.toString();
			return true;
		}
	};	
	</script>
</html>
