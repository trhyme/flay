<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>APP用户列表</title>
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
<style type="text/css">
 .template_prompt{ margin-top:200px; width:300px; margin-left:auto; margin-right:auto; text-align:center; font-size:16px;}
 .template_prompt h2{ color:#666; margin:20px 0px;}
 a{cursor:pointer;}
</style>
</head>
<body onload="onloads();">
	<!-- 展示详细信息 -->
	<div id="add_payment_style" style="display:none">
		<form id="payment_checkbox">
		 <ul class="margin payment_list  clearfix">
		  <li style="width:100%">
		  	<input type="hidden" id="phone_list" value="" readonly="readonly">
		  	<span style="color:#9ea0a1;">联系人电话:</span><span id="show_phone_span" style="color:blue;margin-left:14px;"></span>
		  </li>
		 </ul>
		 <ul class="margin payment_list  clearfix">
		 	<li style="width:100%;">
		 		<span>
		 			<label style="width:105px;color:#9ea0a1;">选择发送对象：</label>
		 			<label for="alluser" style="cursor:pointer;width:85px;"><input type="radio" id="alluser" name="checkuser" value="1all">全部用户</label>
		 			<label for="allmember" style="cursor:pointer;width:85px;"><input type="radio" id="allmember" name="checkuser" value="0member">所有会员</label>
		 		</span>
		 		
		 	</li>
		 </ul>
		 <ul class="margin payment_list  clearfix" id="show_picturelist">
		 	<li style="width:100%;">
		 		<textarea rows="" cols="" id="shmessage" placeholder="短信信息" style="width:100%;max-width:100%;max-height:185px;height:98px;min-height:98px;min-width:100%;border:1px #3cafe4 solid;padding:5px;font-size:13px;"></textarea>
		 	</li>
		 </ul>
		</form>
	</div>
	<!-- 展示详细信息 -->
	<!-- 展示详细信息2 -->
	<div id="add_payment_style1" style="display:none;height:100%;">
		<form>
		 <ul class="margin payment_list  clearfix" style="height:100%;">
		  <li style="width:100%;height:100%;">
		  	<table id="follow_fans_table" style="width:100%;border:0px #a3a2a2 solid;">
				<tr>
					<td>关注信息</td>
					<td style="width:2%;"></td>
					<td>粉丝信息</td>
				</tr>
				<tr>
					<td width="49%"style="background-color:#fff;height:100%;border-bottom:1px #a3a2a2 solid;">
						<iframe id="showlistFollow_iframe" style="width:100%;height:99%;border:0px;overflow: visible;border-top:1px #a3a2a2 solid;border-bottom:1px #a3a2a2 solid;">
				  			
				  		</iframe>
				  		<br>
					</td>
					<td></td>
					<td width="49%"style="background-color:#fff;height:100%;border-bottom:1px #a3a2a2 solid;">
						<iframe id="showlistFollow_iframe2" style="width:100%;height:99%;border:0px;border-top:1px #a3a2a2 solid;border-bottom:1px #a3a2a2 solid;">
				  		
				  		</iframe>
				  		<br>
					</td>
				</tr>
			</table>
		  	
		  </li>
		 </ul>
		</form>
	</div>
	<!-- 展示详细信息 2-->
	<div class="margin Competence_style" id="page_style">
	    <div class="operation clearfix">
				<form action="appuserpage" method="get">
				   <input id="uuid" name="uuid" type="number" value="${uuid}" placeholder="用户ID" style="float:left;margin-left:10px;"/>
				   <input id="uphone" name="uphone" value="${uphone}" type="text"   placeholder="手机号码" style="float:left;margin-left:20px;"/>
				   <input id="unames" name="unames" value="${unames}" type="text"   placeholder="用户昵称" style="float:left;margin-left:20px;"/>
				   <select id="stratus" name="stratus" class="select" style="margin-left:22px;height:28px;">
				   		<option value="-1">--账户状态--</option>
				   		<option value="1">正常</option>
				   		<option value="0">冻结</option>
				   </select>
				   <select id="huiyuan" name="huiyuan" class="select" style="margin-left:22px;height:28px;">
				   		<option value="-1">--会员状态--</option>
				   		<option value="1">是</option>
				   		<option value="0">否</option>
				   </select>
				   <input type="date" id="begintimes" name="begintimes" style="margin-left:22px;height:28px;">
				   <input type="date" id="endstimes" name="endstimes" style="margin-left:22px;height:28px;">
				   <button class="btn button_btn bg-deep-blue " onclick=""  type="submit" style="margin-left:20px;">
				   <i class="fa  fa-search"></i>搜&nbsp;&nbsp;索</button>
				   <a href="appuserpage">
					   <button class="btn button_btn bg-deep-blue " onclick=""  type="button" >
					   <i class="fa  fa-search"></i>清空条件刷新</button>
				   </a>
				   <a onclick="add_payment(this);" class="btn button_btn bg-deep-blue" title="发送短信"><i class="fa  fa-edit"></i>&nbsp;发送短信</a>
			   </form>
		</div>
	<div class="compete_list">
	       <table id="sample_table" class="table table_list table_striped table-bordered dataTable no-footer">
			 <thead>
				<tr>
				  <th width="30px"><label style="width:35px;"><input type="checkbox" class="ace" value=""><span class="lbl"></span></label></th>
				  <th>编号ID</th>
				  <th>头像</th>
				  <th>昵称</th>
				  <th>手机号</th>
				  <th>交友状态</th>
				  <th>会员名称</th>
				  <th>会员状态</th>
				  <th>声音</th>
				  <th>认证</th>
				  <th>性别</th>
				  <th>生日</th>
				  <th>家乡</th>
				  <!-- <th>蓝砖</th>
				  <th>红砖</th>
				  <th>充值</th>
				  <th>收入</th> -->
				  <th>时间</th>
				  <th class="hidden-280">操作</th>
				  <th>编辑</th>
	             </tr>
			    </thead>
	             <tbody>
	             	<c:forEach items="${appuserlist}" var="lists">
	             		<tr>
	             			<td width="30px">
	             				<label style="width:35px;"><input type="checkbox" class="ace" name="ace" value="${lists.phone}"><span class="lbl"></span></label>
	             			</td>
							<td>${lists.num}</td>
							<td>
								<img src="${lists.headpic}" style="width:37;height:37px;">
							</td>
							<td>${lists.username}</td>
							<td>${lists.phone}</td>
							<td>${lists.friendstate}</td>
							<td>
								<c:if test="${lists.megname ne null}">
									<span style="color:#f59e0c;">${lists.megname}</span><br>
									<span style="font-size:8px;">
										<font style="font-size:4px;">${lists.begintime}</font><br>
										<font style="font-size:4px;">${lists.endestime}</font></span>
								</c:if>
								<c:if test="${lists.megname eq null}">
									<span style="color:#3f3e3c;">无</span>
								</c:if>
							</td>
							<td>
								<c:if test="${lists.strutus eq null}">无</c:if>
								<c:if test="${lists.strutus eq 1}">
									<span style="color:#f59e0c;">正常</span></c:if>
								<c:if test="${lists.strutus eq 0}">
									<span style="color:#f50c0c;">到期</span></c:if>
							</td>
							<td>
								<c:if test="${lists.voicesurl ne null}">
									<a target="_blank" href="${lists.voicesurl}" style="color:blue;font-size:20px;">♫
									<span style="font-size:16px;color:blue;"><%-- .${fn:substring(lists.voicesurl, fn:length(lists.voicesurl)-3, fn:length(lists.voicesurl))} --%></span></a>
								</c:if>
								<c:if test="${lists.voicesurl eq null}">
									<span style="font-size:13px;color:red;">未录音</span>
								</c:if>
							</td>
							<td>${lists.ischeck}</td>
							<td>${lists.sex}</td>
							<td>${lists.birth}</td>
							<td>${lists.province}${lists.city}${lists.area}</td>
							<!-- <td></td>
							<td></td>
							<td></td>
							<td></td> -->
							<td>${lists.date}</td>
							<td>
								<a onclick="floowmenu(this);" name="${lists.id}" style="cursor:pointer;color:blue;">查看关注</a>
							</td>
							<td>
								<a href="lookinguserone?uid=${lists.id}" style="color:green;cursor:pointer;">编辑</a>
							</td>
							
						<%-- <td>
		                 <a title="编辑" onclick="Competence_modify('560')" href="menuupdatepage?id=${lists.id}" class="btn button_btn bg-deep-blue">编辑</a>        
		                 <a title="删除" href="javascript:;" onclick="Competence_del(this,'1')" class="btn button_btn btn-danger">删除</a>
						</td> --%>
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
	function onloads(){
		//alert($("#add_payment_style1").height());
		var ht=$("#add_payment_style1").height();
		ht=ht-140;
		$("#follow_fans_table").height(ht+"px");
	}
	
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
	
	//短信弹窗
	function add_payment(index ){
		
		//获取多选框的按钮
		var obj=document.getElementsByName('ace'); //选择所有name="'ace'"的对象，返回数组 
		//取到对象数组后，我们来循环检测它是不是被选中 
		var s=''; 
		for(var i=0; i<obj.length; i++){ 
			if(obj[i].checked){
				if(obj[i].value!=""){
					s+=obj[i].value+','; //如果选中，将value添加到变量s中 
				}else{
					
				}
			}
		} 
		//alert(s)
		if(s==""){
			layer.open({
				   icon:2,
				   title:'系统提示',
				   content:'至少需要选择一个绑定手机号码的用户才可发送短信哦',	
			    });
		}else{
			//显示手机号码
			s=s.substring(0,s.length-1);
			$("#phone_list").val(s);
			$("#show_phone_span").html(s.replace(/,/g,"&nbsp;&nbsp;&nbsp;&nbsp;"));
			$("#shmessage").focus();
			$("#toexamID").val(index.id);
		        layer.open({
		        type: 1,
		        title: '短信发送',
				maxmin: true, 
				shadeClose:false,
		        area : ['830px' , ''],
		        content:$('#add_payment_style'),
				btn:['确定发送','取消'],
				yes: function(index){
					var phoneAll=$("#phone_list").val();
					var message=$("#shmessage").val();
					//单选
					var val_check = $('input[name="checkuser"]:checked ').val();
					if($.trim(message)==""){
						layer.open({
							   icon:2,
							   title:'系统提示',
							   content:'短信内容不能为空哦',	
						    });
					}else{
						//获取信息并Ajax发送短信
						$.ajax({
							url:'sendphonecode',
						    type:'POST', //GET
						    async:true,    //或false,是否异步
						    data:{
						    	phoneall:phoneAll,message:message,checkuser:val_check
						    },
						    timeout:5000,    //超时时间
						    dataType:'text',    //返回的数据格式：json/xml/html/script/jsonp/text
						    success:function(data,textStatus,jqXHR){
						    	if(data==200){
						    		layer.open({
										   icon:1,
										   title:'系统提示',
										   content:'短信发送成功'	
									    });
						    	}else{
						    		layer.open({
										icon:2,
										title:'系统提示',
										content:'短信发送失败了'	
									});
						    	}
						    	//清空信息
						    	$("#shmessage").val("");
						    },
						    error:function(xhr,textStatus){
						    	layer.open({
									icon:2,
									title:'系统提示',
									content:'短信发送失败了!'	
								});
						    }
						});
						//window.location.href="updatetoexaminestratus?id="+toid+"&thepage="+npage+"&stra=1";
					}
					
					//alert($("#toexamID").val());
				}
			})	
			};
			function Paymentdetails(id){
			window.location.href = "Payment_details.html?="+id;
		}
		
	};
	
	
	//关注信息
	function floowmenu(index){
		$("#showlistFollow_iframe").attr("src","showuserfollow?uid="+index.name);
		$("#showlistFollow_iframe2").attr("src","showuserfans?uid="+index.name);
		
		layer.open({
	        type: 1,
	        title: '关注信息详情',
			maxmin: true, 
			shadeClose:false,
	        area : ['99%' , '99%'],
	        content:$('#add_payment_style1'),
			btn:['确定','取消'],
			yes: function(index){
				
			}
		});
	}
	
	
	</script>
	<script type="text/javascript" src="js/jquery.appuserpagination.js"></script>
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