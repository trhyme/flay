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
<link rel="stylesheet" href="css/pagination.css">
<link rel="stylesheet" href="css/font-awesome.css">
<link rel="stylesheet" href="css/font-awesome.min.css">
<script src="js/jquery-1.9.1.min.js" type="text/javascript" ></script>
<script type="text/javascript" src="js/jquery.cookie.js"></script>
<script src="js/shopFrame.js" type="text/javascript"></script>
<script src="js/Sellerber.js" type="text/javascript"></script>
<script src="js/layer/layer.js" type="text/javascript"></script>
<script src="js/laydate/laydate.js" type="text/javascript"></script>
<title>标签类型信息</title>
</head>
<body>
	<div class="margin order_style" id="page_style">
		<div class="margin clearfix">
		 <div class="defray_style">
		    <div class="border clearfix">
		     <span class="l_f"  >
		        <a style="background-color:#fff;font-size:16px;"><i class="fa fa-credit-card"></i>类型标签信息列表</a>
		       </span>
		    </div>
		  </div>
		</div>
		<div class="sum_style margin-bottom ">
		 <ul class="clearfix">
		 	<c:forEach items="${typeslist}" var="types">
		 		<li class="col-xs-3 " style="width:180px;min-width:180px;"><div class="sum_yifu Amount" style="background-color:#4fccfa;border:1px #2ec1f8 solid;min-width:150px;width:150px;margin:20px 20px 10 10;text-align:center;">
		 			<span style="color:#fff;">${types.name}</span></div>
		 		</li>
		 	</c:forEach>
		  <!-- <li class="col-xs-3 "><div class="sum_zone Amount"><span><em>￥</em>354465</span></div></li>
		  <li class="col-xs-3 "><div class="sum_yifu Amount"><span><em>￥</em>35465</span></div></li>
		  <li class="col-xs-3 "><div class="sum_daifu Amount"><span><em>￥</em>3545</span></div></li>
		  <li class="col-xs-3 "><div class="sum_tuikuan Amount"><span><em>￥</em>3545</span></div></li> -->
		 </ul>
		</div>
		
		<!-- 添加信息 -->
		<div id="add_payment_style" style="display:block;">
			<form action="typesvoiceadd" method="post" id="form-admin-add" onsubmit="return checkAction();">
			 <ul class="margin payment_list  clearfix">
			  	<li class=" clearfix"><span>
			  	<label style="font-size:15px;font-weight:bold;">添加类型标签：</label>
			  		<input type="text" placeholder="Banner图标题" id="titles" name="titles" datatype="*1-16" nullmsg="标题不能为空" placeholder="Banner图标题"/>
			  		</span>
			  		
			  	</li> 
			  	<li class=" clearfix"><span style="margin-left:20px;">
			  	<label>&nbsp;${messages}</label>
			  		<input name="" type="submit" class="btn button_btning bg-deep-blue" style="height:28px; width:100px;" value="添加标签信息"/>
			  		</span>
			  	</li> 
			 </ul>
		     
			 </form>
		</div>
		
	</div>
</body>
<script type="text/javascript">
	function checkAction(){
		var titles=$("#titles").val();
		if(titles.replace(/[  ]/g,"")==""){
			$('#titles').focus();
			$('#titles').attr("placeholder","标题不能为空");
			return false;
		}else{
			return true;
		}
	}

	function add_payment(index ){
	    layer.open({
		    type: 1,
		    title: '添加支付方式',
			maxmin: true, 
			shadeClose:false,
		    area : ['830px' , ''],
		    content:$('#add_payment_style'),
			btn:['确定','取消'],
			yes: function(index){
				var checkbox=$('input[name="checkbox"]');		    			
				if(checkbox.length){
				 var b = false;				
				 for(var i=0; i<checkbox.length; i++){
					if(checkbox[i].checked){
						b = true;
						layer.alert('添加成功！',{
		           title: '提示框',				
				  icon:0,		
				  })  
				  layer.close(index);
				   break;	
					}
				
				 }
				 if(!b){
					   layer.alert('请选择所需要的支付方式！',{
		           title: '提示框',				
				icon:0,		
				  }); 
		
				 }
			  }
				else{
								
				}	
			}
		})	
	};
</script>
</html>