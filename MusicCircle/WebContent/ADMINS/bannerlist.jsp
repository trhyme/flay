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
<link rel="stylesheet" href="css/pagination.css">
<link rel="stylesheet" href="css/font-awesome.css">
<link rel="stylesheet" href="css/font-awesome.min.css">
<script src="js/jquery-1.9.1.min.js" type="text/javascript" ></script>
<script type="text/javascript" src="js/jquery.cookie.js"></script>
<script src="js/shopFrame.js" type="text/javascript"></script>
<script src="js/Sellerber.js" type="text/javascript"></script>
<script src="js/layer/layer.js" type="text/javascript"></script>
<script src="js/laydate/laydate.js" type="text/javascript"></script>
<title>Banner图列表</title>
<style type="text/css">
 .template_prompt{ margin-top:200px; width:300px; margin-left:auto; margin-right:auto; text-align:center; font-size:16px;}
 .template_prompt h2{ color:#666; margin:20px 0px;}
</style>
</head>
	<body>
		<div class="margin" id="page_style">
		 <div class="margin clearfix">
		 <div class="defray_style">
		    <div class="border clearfix">
		     <span class="l_f">
		        <a href="addbannerpage" class="btn button_btning bg-deep-blue"><i class="fa fa-credit-card"></i>&nbsp;添加设置Banner图</a>
		       </span>
		    </div>
		    <!--列表-->
		    <div class="defray_list cover_style clearfix" >
		     <div class="type_title">App首页Banner图</div>
		      <div class="defray_content clearfix  margin20">
		       	<c:forEach items="${bannerlist}" var="lists">
		       		<ul class="defray_info" style="height:370px;">
				       	<li class="defray_name">${lists.title}</li>
				        <li class="name_logo">
				        <a style="cursor:pointer;" href="${lists.htmlurl}" target="_blank">
				        	<img src="${lists.picurl}"  width="100%" height="160px;" /> 
				        </a>
				        </li>
				        <li class="description" style="min-height:50px;">${lists.remark}</li>
				        
				        <li class="operating ptb10" style="height:20px;">
				        <!-- <a href="javascript:ovid()" class="btn button_btns btn-danger"><i class="fa fa-trash"></i>下 架</a> -->
				         <a href="updatebannerpage?id=${lists.id}&thepage=${nowpage}" class="btn button_btns btn-Dark-success"><i class="fa  fa-edit "></i>修 改</a>
				        </li>
		       		</ul>
		       	</c:forEach>
		       		
		       <input style="display:none;" id="nowpage" name="nowpage" value="${nowpage}" readonly="readonly">
		        <input style="display:none;" id="counts" name="counts" value="${counts}" readonly="readonly">
		      </div>
		      <div class="annotate alert-danger">
		      	 App的首页Banner图信息
		      </div>
		    </div>
		    <!-- 分页 -->
				<div align="left" style="background-color:#fff; width:720px; min-height:35px; border: 0px red solid; background-color:#fff; margin-top:12px;z-index:-1">
					<div id="Pagination" class="pagination"></div>
					<br style="clear:both;" />
				</div>
				<!-- 分页 -->
		 </div>
		</div>
		<div id="add_payment_style" style="display:none">
		<form id="payment_checkbox">
			
		 <ul class="margin payment_list  clearfix">
		  <li>
		  <label><input name="checkbox" type="checkbox" class="ace" id="checkbox" onclick="select_payment(this,'123')"><span class="lbl"><img src="product_img/black/yinglian.jpg"  width="120px" height="100%" /> </span></label>
		  </li>
		  <li>
		  <label><input name="checkbox" type="checkbox" class="ace" id="checkbox" onclick="select_payment(this,'125')"><span class="lbl"><img src="product_img/black/yozhif.jpg"  width="120px" height="100%" /></label>
		  </li>
		  <li>
		  <label><input name="checkbox" type="checkbox" class="ace" id="checkbox" onclick="select_payment(this,'126')"><span class="lbl"><img src="product_img/black/caifut.jpg"  width="120px" height="100%" /></label>
		  </li>
		  <li>
		  <label><input name="checkbox" type="checkbox" class="ace" id="checkbox" onclick="select_payment(this,'127')"><span class="lbl"><img src="product_img/black/weixin.jpg"  width="120px" height="100%" /></label>
		  </li>
		   <li>
		  <label><input name="checkbox" type="checkbox" class="ace" id="checkbox" onclick="select_payment(this,'127')"><span class="lbl"><img src="product_img/black/zhifb.jpg"  width="120px" height="100%" /></label>
		  </li>
		 </ul>
		 
		 </form>
		</div>
				
		</div>
		
	</body>
	<script>
	function select_payment(ojb,id){
		if($('input[name="checkbox"]').prop("checked")){
			 $('.add_content').css('display','block');
			/*  var num=0;
			var str="";
			  $(".add_content input[type$='text']").each(function(n){
	          if($(this).val()=="")
	          { 
				layer.alert(str+=""+$(this).attr("name")+"不能为空！\r\n",{
	            title: '提示框',				
			    icon:0,								
	          }); 
			    num++;
	            return false;            
	          } 
			 });
			  if(num>0){  return false;}	*/
			}
		else{
			
			 $('.add_content').css('display','none');
			}
	}
	/*字数限制*/
	function checkLength(which) {
		var maxChars = 200; //
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
	/**添加支付方式0**/
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
		function Paymentdetails(id){
		window.location.href = "Payment_details.html?="+id;
	};
	</script>
	<script type="text/javascript" src="js/jquery.bannerpagination.js"></script>
	<script type="text/javascript">
		$(function(){
			var nowpage=$("#nowpage").val();  //当前页数
			var counts=$("#counts").val();	  //总数
			// 创建分页元素
			$("#Pagination").pagination(counts, {
				num_edge_entries: 1,
				num_display_entries: 10,
				current_page:nowpage-1,
				items_per_page:10,
				num_display_entries:10,
				callback: pageselectCallback  //回调函数
			});
		});
	</script>
</html>
