<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
<title>查看/编辑用户信息</title>
</head>
<body>
		<div class="margin add_administrator" id="page_style">
	    <div class="add_style add_administrator_style">
	    <div class="title_name">用户信息编辑</div>
	    <form action="appuseroneupdate" method="post" id="form-admin-add">
	    <ul>
	     <li class="clearfix">
	     <label class="label_name col-xs-2 col-lg-2"><i class="c-red">*</i>头像：</label>
		 <div class="formControls col-xs-6">
		 	<img alt="" src="${user.headpic}" style="width:50px;height:50px;">
		 	<input type="hidden" id="picName" name="picName" readonly="readonly">
		 	<input type="file" onchange="viewmypic()" name="cmplogoname_input" id="cmplogoname_input" class="input-text col-xs-12" nullmsg="图片不能为空" placeholder="请选择图片" accept="image/png,image/jpg,image/jpeg,image/gif" style="border:1px #f0f0f0 solid;">
		 </div>
	     <div class="col-4"> <span class="Validform_checktip"></span></div>
	     </li>
	     <li class="clearfix">
	     	
	     	<!-- 信息 -->
	     	<input id="id" value="${user.id}" name="id" type="hidden" readonly="readonly">
	     	<!-- 信息 -->
		     <label class="label_name col-xs-2 col-lg-2"><i>*</i>用户ID：</label>
		     <div class="formControls col-xs-6">
		     <input type="text" class="input-text col-xs-12" value="${user.num}" id="num" name="num" readonly="readonly"></div>
		     <!-- <input type="text" class="input-text col-xs-12" value="" placeholder="Banner图标题" id="titles" name="titles" datatype="*1-16" nullmsg="标题不能为空" placeholder="Banner图标题"></div> -->
		     <div class="col-4"> <span class="Validform_checktip"></span></div>
	     </li>
	     <li class="clearfix">
		     <label class="label_name col-xs-2 col-lg-2"><i>*</i>用户昵称：</label>
		     <div class="formControls col-xs-6">
		     <input type="text" class="input-text col-xs-12" value="${user.username}" id="username" name="username" ></div>
		     <div class="col-4"> <span class="Validform_checktip"></span></div>
	     </li>
	     <li class="clearfix">
		     <label class="label_name col-xs-2 col-lg-2"><i>*</i>手机号码：</label>
		     <div class="formControls col-xs-6">
		     <input type="text" class="input-text col-xs-12" value="${user.phone}" id="phone" name="phone" ></div>
		     <div class="col-4"> <span class="Validform_checktip"></span></div>
	     </li>
	     <li class="clearfix">
		     <label class="label_name col-xs-2 col-lg-2"><i>*</i>性别：</label>
		     <div class="formControls col-xs-6">
		     <input type="text" class="input-text col-xs-12" value="${user.sex}" id="sex" name="sex" ></div>
		     <div class="col-4"> <span class="Validform_checktip"></span></div>
	     </li>
	     <li class="clearfix">
		     <label class="label_name col-xs-2 col-lg-2"><i>*</i>年龄：</label>
		     <div class="formControls col-xs-6">
		     	<input type="number" class="input-text col-xs-12" value="${user.age}" id="age" name="age" >
		     </div>
		     <div class="col-4"> <span class="Validform_checktip"></span></div>
	     </li>
	     <li class="clearfix">
		     <label class="label_name col-xs-2 col-lg-2"><i>*</i>生日：</label>
		     <div class="formControls col-xs-6">
		     	<input type="text" class="input-text col-xs-12" value="${user.birth}" id="birth" name="birth" >
		     </div>
		     <div class="col-4"> <span class="Validform_checktip"></span></div>
	     </li>
	     <li class="clearfix">
		     <label class="label_name col-xs-2 col-lg-2"><i>*</i>身高：</label>
		     <div class="formControls col-xs-6">
		     	<input type="text" class="input-text col-xs-12" value="${user.height}" id="height" name="height" >
		     </div>
		     <div class="col-4"> <span class="Validform_checktip"></span></div>
	     </li>
	     <li class="clearfix">
		     <label class="label_name col-xs-2 col-lg-2"><i>*</i>工作：</label>
		     <div class="formControls col-xs-6">
		     	<input type="text" class="input-text col-xs-12" value="${user.workes}" id="workes" name="workes" >
		     </div>
		     <div class="col-4"><span class="Validform_checktip"></span></div>
	     </li>
	     <li class="clearfix">
		     <label class="label_name col-xs-2 col-lg-2"><i>*</i>学校：</label>
		     <div class="formControls col-xs-6">
		     	<input type="text" class="input-text col-xs-12" value="${user.school}" id="school" name="school" >
		     </div>
		     <div class="col-4"> <span class="Validform_checktip"></span></div>
	     </li>
	     <li class="clearfix">
		     <label class="label_name col-xs-2 col-lg-2"><i>*</i>家乡：</label>
		     <div class="formControls col-xs-6">
		     	<input type="text" class="input-text col-xs-12" value="${user.province}-${user.city}-${user.area}" id="" name="" >
		     </div>
		     <div class="col-4"> <span class="Validform_checktip"></span></div>
	     </li>
	     
	     <li class="clearfix">
		     <label class="label_name col-xs-2 col-lg-2"><i>*</i>兴趣爱好：</label>
		     <div class="formControls col-xs-6">
		     <input type="text" class="input-text col-xs-12" value="${user.types_id}" id="" name="" ></div>
		     <div class="col-4"> <span class="Validform_checktip"></span></div>
	     </li>
	     
	     <c:if test="${user.voicesurl ne null }">
	     <li class="clearfix">
	       <label class="label_name col-xs-2 col-lg-2"><i class="c-red">*</i>声音：</label>
	       	<div class="formControls  col-xs-6">
				<a href="${user.voicesurl}" target="_blank" style="color:blue;">声音签名</a>&nbsp;&nbsp;&nbsp;&nbsp;
				<a href="" style="color:red;">删除</a>
			</div>
		  <div class="col-4"> <span class="Validform_checktip"></span></div>
	     </li>
	     </c:if>
	     <li class="clearfix">
				<label class="label_name col-xs-2 col-lg-2">个性签名：</label>
				<div class="formControls col-xs-6">
					<textarea name="sign" id="sign" cols="" rows="" class="textarea col-xs-12" placeholder="说点什么...100个字符以内" dragonfly="true" onkeyup="checkLength(this);">${user.sign}</textarea>
					<span class="wordage">剩余字数：<span id="sy" style="color:Red;">100</span>字</span>
				</div>
			</li>
	         <li class="clearfix">
				<div class="col-xs-2 col-lg-2">&nbsp;</div>
				<div class="col-xs-6">
				  <input class="btn button_btn bg-deep-blue " type="submit" id="Add_Administrator" value="提交修改">
			      <input name="reset" type="reset" class="btn button_btn btn-gray" value="取消重置" />
			      <a href="javascript:ovid()" onclick="javascript :history.back(-1);" class="btn btn-info button_btn"><i class="fa fa-reply"></i> 返回上一步</a>
				</div>
			</li>
	    </ul>
	    
	    </form>
	    </div>
	    <div class="split_line">${meaasge}</div>
	    <div class="Notice_style l_f">
	      	<img alt="" id="cmplogo_changeshow" src="" style="max-height:400px;">
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
	                layer.msg(data.info, {icon: data.status,time: 3000}); 
	            } 		  
				var index =parent.$("#iframe").attr("src");
				parent.layer.close(index);
				//
			}				
		});
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
	//图片预览
	function viewmypic() {
	  	var f = document.getElementById("cmplogoname_input").files[0];
		var src = window.URL.createObjectURL(f);
		
		onclickLogoImgupload();
	}
	function onclickLogoImgupload(){
		var imgName=$("#cmplogoname_input").val();
		if(imgName==""){
			alert("请选择图片");
		}else{
			//请求上传
			$.ajaxFileUpload({
				url : "bannerajaxload",  
		        secureuri : false,
		        fileElementId : "cmplogoname_input", 
		        contentType:"application/json;charset=UTF-8",
		        dataType : "json", 
		        success : function(data, status){
		        	//alert(data.data)
		        	console.log(data.data);
		        	$("#picName").val(data.data);
		        	document.getElementById("cmplogo_changeshow").src = data.data;   //预览图片
		        },
		        error : function(data, status, e){
		        	$("#cmplogoname_input").text("");
		        	alert("上传失败");
		        }
			});
		}
	}
	</script>
	<!-- <script type="text/javascript" src="js/jquery-1.10.2.js"></script> -->
	<script type="text/javascript" src="js/ajaxfileupload.js"></script>
</html>