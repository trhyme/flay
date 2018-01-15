<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
<link href="<%=request.getContextPath() %>/css/shop.css" type="text/css" rel="stylesheet" />
<link href="<%=request.getContextPath() %>/css/Sellerber.css" type="text/css"  rel="stylesheet" />
<link href="<%=request.getContextPath() %>/css/bkg_ui.css" type="text/css"  rel="stylesheet" />
<link href="<%=request.getContextPath() %>/font/css/font-awesome.min.css"  rel="stylesheet" type="text/css" />
<script src="<%=request.getContextPath() %>/js/jquery-1.9.1.min.js" type="text/javascript" ></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/Validform/Validform.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/jquery.cookie.js"></script>
<script src="<%=request.getContextPath() %>/js/shopFrame.js" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/js/Sellerber.js" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/js/layer/layer.js" type="text/javascript"></script>
<title>菜单信息修改</title>
</head>
	<body>
		<div class="margin add_administrator" id="page_style">
	    <div class="add_style add_administrator_style">
	    <div class="title_name">菜单信息修改</div>
	    <form action="menuupdategodb" method="post" id="form-admin-add">
	    <ul>
	     <li class="clearfix">
	     <label class="label_name col-xs-2 col-lg-2"><i>*</i>菜单名称：</label>
	     <div class="formControls col-xs-6">
	     <input type="hidden" name="id" id="id" readonly="readonly" value="${onemenu.id}">
	     <input type="hidden" name="ids" id="ids" readonly="readonly" value="${onemenu.id}">
	     <input type="text" placeholder="菜单名称" class="input-text col-xs-12" value="${onemenu.title}" placeholder="" id="title" name="title" datatype="*2-16" nullmsg="菜单名称不能为空"></div>
	    <div class="col-4"> <span class="Validform_checktip"></span></div>
	     </li>
	     <li class="clearfix">
	     <label class="label_name col-xs-2 col-lg-2"><i class="c-red">*</i>菜单链接：</label>
		 <div class="formControls col-xs-6">
		 <input type="text" placeholder="菜单链接" id="url" name="url" autocomplete="off" value="${onemenu.url}" class="input-text col-xs-12" datatype="*" nullmsg="菜单链接不能为空">
		</div>
	     <div class="col-4"> <span class="Validform_checktip"></span></div>
	     </li>
	     
	     
	     <li class="clearfix">
	      <label class="label_name col-xs-2 col-lg-2"><i class="c-red">*</i>上级菜单：</label>
	      <div class="formControls col-xs-6"> <span class="select-box" style="width:150px;">
					<select class="select" id="pid" name="pid" size="1">
						<option value="${onemenu.pid}">--为父级菜单--</option>
						<c:forEach items="${printmenu}" var="pone">
							<option value="${pone.id}">${pone.title}</option>
						</c:forEach>
					</select>
					</span>
	         </div>
	     </li>
	     <li class="clearfix">
				<label class="label_name col-xs-2 col-lg-2">备&nbsp;注：</label>
				<div class="formControls col-xs-6">
					<textarea name="remark" id="remark" cols="" rows="" class="textarea col-xs-12" placeholder="说点什么...100个字符以内" dragonfly="true" onkeyup="checkLength(this);">${onemenu.remark}</textarea>
					<span class="wordage">剩余字数：<span id="sy" style="color:Red;">100</span>字</span>
				</div>
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
	</script>
</html>
