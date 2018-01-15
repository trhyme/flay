<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
<title>弹窗广告设置添加</title>
<script type="text/javascript">
</script>
</head>
<body>
	<body>
		<div class="margin add_administrator" id="page_style">
	    <div class="add_style add_administrator_style">
	    <div class="title_name">弹窗广告信息添加</div>
	    <form action="addadverstodb" method="post" id="form-admin-add">
	    <ul>
	     <li class="clearfix">
		     <label class="label_name col-xs-2 col-lg-2"><i>*</i>标 题：</label>
		     <div class="formControls col-xs-6">
		     <input type="text" class="input-text col-xs-12" value="" placeholder="弹窗广告名称" id="advtitles" name="advtitles" datatype="*1-16" nullmsg="弹窗广告名称不为空" placeholder="弹窗广告名称"></div>
		    <div class="col-4"> <span class="Validform_checktip"></span></div>
	     </li>
	     <li class="clearfix">
	     <label class="label_name col-xs-2 col-lg-2"><i class="c-red">*</i>图 片：</label>
		 <div class="formControls col-xs-6">
		 <input type="hidden" id="picName" name="picName" readonly="readonly">
		 <input type="file" onchange="viewmypic()" name="cmplogoname_input" id="cmplogoname_input" class="input-text col-xs-12" nullmsg="图片不能为空" placeholder="请选择图片" accept="image/png,image/jpg,image/jpeg,image/gif" style="border:1px #f0f0f0 solid;">
		</div>
	     <div class="col-4"> <span class="Validform_checktip"></span></div>
	     </li>
	     <li class="clearfix">
		     <label class="label_name col-xs-2 col-lg-2"><i>*</i>H5路径：</label>
		     <div class="formControls col-xs-6">
		     <input type="text" class="input-text col-xs-12" value="" placeholder="H5页面路径" id="h5url" name="h5url" datatype="*" nullmsg="跳转路径不能为空" placeholder="点击跳转路径"></div>
		    <div class="col-4"> <span class="Validform_checktip"></span></div>
	     </li>
	     <li class="clearfix">
	       <label class="label_name col-xs-2 col-lg-2"><i class="c-red">*</i>可 见：</label>
	       	<div class="formControls  col-xs-6">
				<span class="select-box" style="width:150px;">
                    <select class="select" name="strausnum" id="strausrnum" size="1">
                        <option value="3">所有人</option>
                        <option value="1">普通用户</option>
                        <option value="2">陪玩</option>
                        
                    </select>
                </span>
			</div>
		  <div class="col-4"> <span class="Validform_checktip"></span></div>
	     </li>
	     <li class="clearfix">
		     <label class="label_name col-xs-2 col-lg-2"><i>*</i>开始时间：</label>
		     <div class="formControls col-xs-6">
		     <input type="date" class="input-text col-xs-12" value="" placeholder="开始时间" id="begintime" name="begintime" datatype="*" nullmsg="开始时间不能为空" placeholder="开始时间"></div>
		    <div class="col-4"> <span class="Validform_checktip"></span></div>
	     </li>
	     <li class="clearfix">
		     <label class="label_name col-xs-2 col-lg-2"><i>*</i>结束时间：</label>
		     <div class="formControls col-xs-6">
		     <input type="date" class="input-text col-xs-12" value="" placeholder="结束时间" id="endsstime" name="endsstime" datatype="*" nullmsg="结束时间不能为空" placeholder="结束时间"></div>
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