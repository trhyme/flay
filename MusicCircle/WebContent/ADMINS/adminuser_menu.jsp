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
<script src="js/jquery-1.9.1.min.js" type="text/javascript" ></script>
<script type="text/javascript" src="js/jquery.cookie.js"></script>
<script src="js/shopFrame.min.js" type="text/javascript"></script>
<script src="js/Sellerber.js" type="text/javascript"></script>
<script src="js/layer/layer.js" type="text/javascript"></script>
<script src="js/jquery.dataTables.min.js"></script>
<script src="js/jquery.dataTables.bootstrap.js"></script>
<title>管理员权限管理</title>
<script type="text/javascript">
$(function(){
	$(".middle_deletes").click(function(){
		var ids=$(this).attr('id');
		var uid=$(this).attr('name');
		$(this).remove();
		$.ajax({
			url:"menuuserdeldb?muid="+ids+"&userid="+uid,
			type:"post",
			dataType:"text",
			success:function(data){
				if(data==1){
					$(this).remove();
				}else{
					alert('删除失败');
				}
			},error:function(){
				alert('删除错误');
			}
		});
	});
	
	//改变样式
	$('.middle_click_a').click(function(){
		$(this).toggleClass("middle2");
	});
});
</script>
<style type="text/css">
	.middle_click_a .middle{background-color:#fbf7ee;border:1px pink solid;padding:10px 20px;cursor:pointer;}
	.middle2{background-color:green;border:1px pink solid;padding:10px 20px;cursor:pointer;}
</style>
</head>
	<body>
		<form action="menuinsertall" method="post">
		<div class="margin Configure_style " id="Competence">
		<div class="h_products_list">
		<div class=" add_Competence_style l_f width50" id="add_Competence_style">
		  <div class="title_name">菜单权限---<span style="color:red;">修改权限后需重新登录</span></div>
		   <ul class="add_Authority_style">
		    <li class="clearfix"><label class="label_name col-xs-2 col-lg-2">用户账号：</label><span class="col-xs-9">
		    	<input name="" type="text" value="${umuser.admname}" class="col-xs-5" readonly="readonly" style="background-color:#eaeded;"/></span>
		    </li>
		    <li class="clearfix"><label class="col-xs-2 col-lg-2 label_name" for="form-field-1">菜单权限：<br><b style="color:red;">选择移除</b></label><br>
		    <div class="col-xs-9 admin_name clearfix">
		       <c:forEach items="${umuser.usersMenu}" var="mone">
		       	<a class="middle_deletes" name="${umuser.admid}" id="${mone.menu.id}" style="cursor:pointer;"><label class="middle">
		       		<span class="lbl">${mone.menu.title}</span>
		       	</label>	
		       	</a>
		       </c:forEach>
			</div>
			</li>
		   </ul>
		</div>
		<div class="Competence_list ">
		<div class="title_name">菜单权限分配</div>
		  <div class="list_cont clearfix">
		  <div class="list_height">
		  	
			   <div class="clearfix col-lg-6 ">
			    <dl class="Competence_name"> 
			     <!-- <dt class="Columns_One"><label class="middle"><input class="ace" type="checkbox" id="id-disable-check8"><span class="lbl">测试</span></label></dt> -->
			     
			     <dd class="permission_list clearfix">
			     <input type="hidden" name="userid" id="userid" readonly="readonly" value="${umuser.admid}">
			     <c:forEach items="${allmenus}" var="mones">
			     	<a class="middle_click_a">
			     	<label class="middle">
			     		<input class="ace" type="checkbox" id="id-disable-check9" name="menuAdduser" value="${mones.id}"><span class="lbl">${mones.title}</span></label></a>
			     </c:forEach>
			     </dd>
			     
			    </dl>
			   </div>
			
		   
		   
		   
		   </div>
		  </div>
		</div>
		</div>
		<!--按钮操作-->
		<div class="Button_operation btn_width">
		    <button class="btn button_btn btn-danger" type="submit">提 交</button>
		    <button class="btn button_btn bg-gray" type="button">取消添加</button>
		    <a href="javascript:ovid()" onclick="javascript :history.back(-1);" class="btn btn-info button_btn"><i class="fa fa-reply"></i> 返回上一步</a>
		 </div>
		</div>
		</form>
	</body>
	<script type="text/javascript">
	
	
	$(function() { 
			
		$("#Competence").frame({
			color_btn:'.skin_select',
			Sellerber_menu:'.add_Competence_style  ',
			page_content:' .Competence_list ',//内容
			header:65,//顶部高度
			menu:450,//栏宽度
		});
	});
	/*******滚动条*******/
	$(".Competence_list .list_cont ").niceScroll({  
		cursorcolor:"#888888",  
		cursoropacitymax:1,  
		touchbehavior:true,  
		cursorwidth:"5px",  
		cursorborder:"0",  
		cursorborderradius:"5px"  
	});
	 $(".list_height").height($(window).height()-170);  
	 //当文档窗口发生改变时 触发  
	    $(window).resize(function(){ 
		 $(".list_height").height($(window).height()-170);
	});
	/*字数限制*/
	function checkLength(which) {
		var maxChars = 200; //
		if(which.value.length > maxChars){
		   layer.open({
		   icon:2,
		   title:'提示框',
		   content:'您出入的字数超多限制!',	
	    });
			// 超过限制的字数了就将 文本框中的内容按规定的字数 截取
			which.value = which.value.substring(0,maxChars);
			return false;
		}else{
			var curr = maxChars - which.value.length; //200 减去 当前输入的
			document.getElementById("sy").innerHTML = curr.toString();
			return true;
		}
	};
	/*按钮复选框选择*/
	$(function(){
		$(".Competence_name dt input:checkbox").click(function(){
			$(this).closest("dl").find("dd input:checkbox").prop("checked",$(this).prop("checked"));
		});
		$(".permission_list input:checkbox").click(function(){
			var l =$(this).parent().parent().find("input:checked").length;
			if($(this).prop("checked")){
				$(this).closest("dl").find("dt input:checkbox").prop("checked",true);
				$(this).parents(".Competence_name").find("dt").first().find("input:checkbox").prop("checked",true);
			}
			else{
				if(l==0){
					$(this).closest("dl").find("dt input:checkbox").prop("checked",false);
				}			
			}		
		});
	});
	
	</script>
</html>

