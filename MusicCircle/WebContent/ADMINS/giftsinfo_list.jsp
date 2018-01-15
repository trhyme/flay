<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
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
<script src="js/jquery.dataTables.min.js"></script>
<script src="js/jquery.dataTables.bootstrap.js"></script>
<title>礼物信息管理</title>
</head>
<body>
	<div class="margin Competence_style" id="page_style">
	    <div class="operation clearfix">
		<a href="giftsaddpage"  class="btn button_btn bg-deep-blue" title="礼物信息添加"><i class="fa  fa-edit"></i>礼物信息添加</a>
	   <div class="search  clearfix">
	
	   <input name="" type="text"  class="form-control col-xs-8"/><button class="btn button_btn bg-deep-blue " onclick=""  type="button"><i class="fa  fa-search"></i>&nbsp;搜索</button>
	</div>
	</div>
	<div class="compete_list">
	       <table id="sample_table" class="table table_list table_striped table-bordered dataTable no-footer">
			 <thead>
				<tr>
				  <!-- <th class="center"><label><input type="checkbox" class="ace"><span class="lbl"></span></label></th> -->
				  <th>名称</th>
				  <th>图片</th>
				  <th>价格/虚拟币</th>
				  <th>是否可用(1可用)</th>
				  <th class="hidden-280">操作</th>
	             </tr>
			    </thead>
	             <tbody>
	             	<c:forEach items="${giftslist}" var="lists">
	             		<tr>
							<td>${lists.name}</td>
							<td>
								<img src="${lists.pic}" style="width:50px;height:50px;">
							</td>
							<td>${lists.price}</td>
							<td>${lists.work}</td>
							
						<td>
		                 <a title="编辑" href="updategiftpage?id=${lists.id}" class="btn button_btn bg-deep-blue">编辑</a>        
		                 <a title="删除" href="javascript:;" onclick="Competence_del(this,'1')" class="btn button_btn btn-danger">删除</a>
						</td>
					   </tr>
	             	</c:forEach>
			      </tbody>
		        </table>
		        <input style="display:none;" id="nowpage" name="nowpage" value="${nowpage}" readonly="readonly">
		        <input style="display:none;" id="counts" name="counts" value="${counts}" readonly="readonly">
		        <!-- 分页 -->
				<div align="left" style="background-color:#fff; width:100%; min-height:35px; border: 0px red solid; background-color:#fff; margin-top:12px;z-index:-1">
					<div id="Pagination" class="pagination"></div>
					<br style="clear:both;" />
				</div>
				<span>一共数据：${counts} 条</span>
				<!-- 分页 -->
		        
	     </div>
	</div>
	</body>
	<script>
	$(function(){
		$("#Competence_sort").click(function(){
			var option=$(this).find("option:selected").text();
			var value=$(this).val();
			if(value==0){
				  
				$("#sample_table tbody tr").show()
				}
				else{
			$("#sample_table tbody tr").hide().filter(":contains('"+(option)+"')").show();	
				}
			}).click();	
		});
	
	/*******滚动条*******/
	$("body").niceScroll({  
		cursorcolor:"#888888",  
		cursoropacitymax:1,  
		touchbehavior:false,  
		cursorwidth:"5px",  
		cursorborder:"0",  
		cursorborderradius:"5px"  
	});
	/*管理员-停用*/
	function Competence_close(obj,id){
		layer.confirm('确认要停用吗？',function(index){
			$(obj).parents("tr").find(".td-manage").prepend('<a style="text-decoration:none" class="btn button_btn btn-gray" onClick="Competence_start(this,id)" href="javascript:;" title="启用">启用</a>');
			$(obj).parents("tr").find(".td-status").html('<span class="label label-success label-sm">已停用</span>');
			$(obj).remove();
			layer.msg('已停用!',{icon: 5,time:1000});
		});
	}
	
	/*管理员-启用*/
	function Competence_start(obj,id){
		layer.confirm('确认要启用吗？',function(index){
			$(obj).parents("tr").find(".td-manage").prepend('<a style="text-decoration:none" class="btn button_btn  btn-Dark-success" onClick="Competence_close(this,id)" href="javascript:;" title="停用">停用</a>');
			$(obj).parents("tr").find(".td-status").html('<span class="label label-success label-sm">已启用</span>');
			$(obj).remove();
			layer.msg('已启用!',{icon: 6,time:1000});
		});
	}
	/****复选框选中******/
	$('table th input:checkbox').on('click' , function(){
						var that = this;
						$(this).closest('table').find('tr > td:first-child input:checkbox')
						.each(function(){
							this.checked = that.checked;
							$(this).closest('tr').toggleClass('selected');
						});
							
					});
	</script>
	<script type="text/javascript" src="js/jquery.appgiftspagination.js"></script>
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