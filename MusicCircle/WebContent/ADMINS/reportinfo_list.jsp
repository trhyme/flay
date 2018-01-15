<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf_8">
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
<title>用户举报信息管理</title>
<style type="text/css">
 .template_prompt{ margin-top:200px; width:300px; margin-left:auto; margin-right:auto; text-align:center; font-size:16px;}
 .template_prompt h2{ color:#666; margin:20px 0px;}
</style>
<body>

<!-- 审核内容详情 -->
<div class="margin" id="page_style">
 <div class="margin clearfix">
 <div class="defray_style">
    <div class="border clearfix">
     <span class="l_f">
        <!-- <a href="javascript:ovid()" onclick="add_payment()" class="btn button_btning bg-deep-blue"><i class="fa fa-credit-card"></i>&nbsp;添加支付方式</a> -->
       </span>
    </div>
 </div>
</div>
	
	<!-- 展示详细信息 -->
	<div id="add_payment_style" style="display:none">
	<form id="payment_checkbox">
	 <ul class="margin payment_list  clearfix">
	  <li style="width:100%">
	  	<input type="hidden" id="toexamID" value="">
	  	<img alt="" id="titilePic" src="" style="max-height:160px;cursor:pointer;margin-left:34%;margin-top:10px;" align="middle">
	  </li>
	 </ul>
	 <ul class="margin payment_list  clearfix" id="show_picturelist"></ul>
	 
	 </form>
	</div>
	<!-- 审核内容详情 -->
	<div class="compete_list">
	        <table id="sample_table" class="table table_list table_striped table-bordered dataTable no-footer">
			 <thead>
				<tr>
				  <!-- <th class="center"><label><input type="checkbox" class="ace"><span class="lbl"></span></label></th> -->
				  <th>举报人编号</th>
				  <th>举报人电话</th>
	              <th>举报人昵称</th>
	              <th>举报类型</th>
	              <th>举报内容</th>
	              <th>举报时间</th>
	              <th>被举报人</th>
	              <th>被举报人编号</th>
	              <th>被举报人电话</th>
	              <th>回复内容</th>
	              <th>回复时间</th>
	              
				  <th class="hidden-280">处理</th>
	             </tr>
			    </thead>
	             <tbody>
	             	<c:forEach items="${reportlist}" var="lists">
	             		<tr>
						<td>${lists.uuid}</td>
						<td>${lists.uphone}</td>
						<td>${lists.unames}</td>
						<td>${lists.reptypes}</td>
						<td>${lists.repconte}</td>
						<td>${lists.addtimes}</td>
						<td>${lists.beunames}</td>
						<td>${lists.beuuid}</td>
						<td>${lists.beuphone}</td>
						<td>${lists.replytie}</td>
						<td>${lists.updtimes}</td>
						<td>
							<a href="showonereport?id=${lists.id}" style="cursor:pointer;"><img src="images/report_do.png" id="${lists.id}" style="width:24px;"></a>
						</td>
						
						<%-- <td>
							<img onclick="add_payment(this);" src="images/eye_show.png" id="${lists.id}" style="width:24px;cursor:pointer;" name="${lists.certpic}" alt="${lists.piclist}" title='${lists.piclist}'>
						</td> --%>
						<%-- <td>
							<c:if test="${lists.stratus eq 1}"><span style="color:blue;">已认证</span></c:if>
							<c:if test="${lists.stratus eq 0}"><span style="color:red;">审核中</span></c:if>
							<c:if test="${lists.stratus eq 2}"><span style="color:#df5c04;">未通过</span></c:if>
						</td>
						<td>
		                 <a title="通过" href="updatetoexaminestratus?id=${lists.id}&thepage=${nowpage}&stra=1" class="btn button_btn bg-deep-blue">通过</a>        
		                 <a title="拒绝" href="updatetoexaminestratus?id=${lists.id}&thepage=${nowpage}&stra=2" class="btn button_btn btn-danger">拒绝</a>
						</td> --%>
					   </tr>
	             	</c:forEach>
			      </tbody>
		        </table>
		        <input style="display:none;" id="nowpage" name="nowpage" value="${nowpage}" readonly="readonly">
		        <input style="display:none;" id="counts" name="counts" value="${counts}" readonly="readonly">
		        <!-- 分页 -->
				<div align="left" style="background-color:#fff; width:720px; min-height:35px; border: 0px red solid; background-color:#fff; margin-top:12px;z-index:-1">
					<div id="Pagination" class="pagination"></div>
					<br style="clear:both;" />
				</div>
				<!-- 分页 -->
	     </div>
	</div>
</body>
<script>
function select_payment(ojb,id){
	if($('input[name="checkbox"]').prop("checked")){
		/*  $('.add_content').css('display','block'); */
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
		
		 /* $('.add_content').css('display','none'); */
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
	//alert(index.name);
	var pic=index.name;
	$("#titilePic").attr("src",pic);
	var pixclist=JSON.parse(index.title);
	var str="";
	for(var i=0;i<pixclist.length;i++){
		str+='<li style="width:195px;"><img alt="" src="'+pixclist[i]+'" style="height:100px;max-width:180px;"></li>';
	}
	$("#show_picturelist").html(str);
	$("#toexamID").val(index.id);
        layer.open({
        type: 1,
        title: '【照片审核】---照片认证、展示照片认证',
		maxmin: true, 
		shadeClose:false,
        area : ['830px' , ''],
        content:$('#add_payment_style'),
		btn:['确定认证','取消'],
		yes: function(index){
			var toid=$("#toexamID").val();
			var npage=$("#nowpage").val();
			window.location.href="updatetoexaminestratus?id="+toid+"&thepage="+npage+"&stra=1";
			//alert($("#toexamID").val());
		}
	})	
	};
	function Paymentdetails(id){
	window.location.href = "Payment_details.html?="+id;
};
</script>
	<script type="text/javascript" src="js/jquery.reportinfopagination.js"></script>
	<script type="text/javascript">
		$(function(){
			var nowpage=$("#nowpage").val();  //当前页数
			var counts=$("#counts").val();	  //总数
			// 创建分页元素
			$("#Pagination").pagination(counts, {
				num_edge_entries: 1,
				num_display_entries: 15,
				current_page:nowpage-1,
				items_per_page:15,
				num_display_entries:15,
				callback: pageselectCallback  //回调函数
			});
		});
	</script>
</html>
