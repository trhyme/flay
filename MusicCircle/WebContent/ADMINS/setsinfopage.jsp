<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>APP配置信息</title>
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta http-equiv="Cache-Control" content="no-siteapp" />
<link href="assets/css/bootstrap.min.css" rel="stylesheet" />
<link href="css/shop.css" type="text/css" rel="stylesheet" />
<link href="css/Sellerber.css" type="text/css"  rel="stylesheet" />
<link href="css/bkg_ui.css" type="text/css"  rel="stylesheet" />
<link href="font/css/font-awesome.min.css"  rel="stylesheet" type="text/css" />
<script src="js/jquery-1.9.1.min.js" type="text/javascript" ></script>
<script type="text/javascript" src="js/jquery.cookie.js"></script>
<script src="js/layer/layer.js" type="text/javascript" ></script>          
<script src="js/laydate/laydate.js" type="text/javascript"></script>
<script src="js/Sellerber.js" type="text/javascript"></script>
<script src="js/shopFrame.js" type="text/javascript"></script>
<script src="js/dist/echarts.js" type="text/javascript"></script>
<script src="js/jquery.nicescroll.js" type="text/javascript"></script>
</head>
<body>
	<div class="margin clearfix">
	 <div class="Configure_style">
	    <!--配置-->
	  <div class="Other_style mt20">
	  	
	    <div class="title_name">系统配置信息</div>
	    <div class="mt10">
	     <ul class="invoice deploy" style="">
	      <li class="name" style="background-color:#fde895;background-image:url('');">虚拟货币与人民币的换算</li>
	      <li class="operating">  
	       <span class=""><label>
	       		<span class="lbl" style="color:#f59d03;font-weight:bold;">
	       			￥1元/RMB = ${sets.ratenb}<span style="font-weight:normal;">虚拟币</span>
	       		</span></label>
	       </span>   
	      </li>
	      <li class="info">
	      	<form action="updateratenb" method="post" id="form-admin-add">
	      		<ul>
				     <li class="clearfix">
					     <label class="label_name col-xs-2 col-lg-2"><i></i>价格：</label>
					     <div class="formControls col-xs-6">
					     <input type="hidden" value="${sets.id}" id="idone" name="idone" readonly="readonly">
					     <input type="number" class="input-text col-xs-12" value="${sets.ratenb}" id="ratenb" name="ratenb" datatype="*1-16" nullmsg="**" placeholder="1元虚拟货币" style="width:100%;"></div>
					    <div class="col-4"> <span class="Validform_checktip"></span></div>
				     </li>
				     <li class="clearfix" style="text-align:left;">
				     	<label style="text-align:left;color:#f59d03;"><i></i>设置的是1元RMB与APP虚拟币的兑换率</label>
				     	
				     </li>
				     <li class="clearfix" style="text-align:center;">
				     	<br><input class="btn button_btn bg-deep-blue " type="submit" id="Add_Administrator" value="提交设置" style="background:#dd910c!important;border:1px #fca815 solid;">
					</li>
				     
				</ul>
			</form>
	      </li>
	     </ul>
	      <ul class="invoice deploy">
	      <li class="name">登录即送的虚拟币</li>
	      <li class="operating">  
	       <span class="">
	       	<label>
	       		<span class="lbl" style="font-weight:bold;color:#c1d209;">${sets.loginnb}<span style="font-weight:normal;">虚拟币</span></span></label></span>          
	         </li>
		      <li class="info">
		      	<form action="updateloginnb" method="post">
		      		<ul>
					     <li class="clearfix">
						     <label class="label_name col-xs-2 col-lg-2"><i></i>数值：</label>
						     <div class="formControls col-xs-6">
						     <input type="hidden" value="${sets.id}" id="idtwo" name="idtwo" readonly="readonly">
						     <input type="number" class="input-text col-xs-12" value="${sets.loginnb}" id="loginnb" name="loginnb" datatype="*1-16" nullmsg="**" placeholder="登录奖励" style="width:100%;"></div>
						    <div class="col-4"> <span class="Validform_checktip"></span></div>
					     </li>
					     <li class="clearfix" style="text-align:left;">
					     	<label style="text-align:left;color:#c1d209;"><i></i>设置用户登录APP所获得的积分</label>
					     	
					     </li>
					     <li class="clearfix" style="text-align:center;">
					     	<br><input class="btn button_btn bg-deep-blue " type="submit" id="Add_Administrator" value="提交设置" style="background:#c1d209!important;border:1px #c1d209 solid;">
						</li>
					     
					</ul>
				</form>
		      </li>
	     </ul>
	      <ul class="invoice deploy">
	      <li class="name">APP用户充值选项设置</li>
	      <li class="operating">  
	       <span class="">
	       	<label>
	       		<span class="lbl" style="font-weight:bold;color:#0cdd20;">&nbsp;${sets.rechargearr}</span></label></span>          
	      </li>
	      <li class="info">
	      		<form action="updaterechargearr" method="post">
		      		<ul>
					     <li class="clearfix">
						     <label class="label_name col-xs-2 col-lg-2"><i></i>积分：</label>
						     <div class="formControls col-xs-6">
						     <input type="hidden" value="${gifts.id}" id="idthree" name="idthree" readonly="readonly">
						     <input type="text" class="input-text col-xs-12" value="${sets.rechargearr}" id="rechargearr" name="rechargearr" datatype="*1-16" nullmsg="**" placeholder="英文逗号隔开" style="width:100%;"></div>
						    <div class="col-4"> <span class="Validform_checktip"></span></div>
					     </li>
					     <li class="clearfix" style="text-align:left;">
					     	<label style="text-align:left;color:#0cdd20;"><i></i>用户充值选项数组设置：英文逗号隔开</label>
					     </li>
					     <li class="clearfix" style="text-align:center;">
					     	<br><input class="btn button_btn bg-deep-blue " type="submit" id="Add_Administrator" value="提交设置" style="background:#0cdd20!important;border:1px #0cdd20 solid;">
						</li>
					</ul>
				</form>
	      </li>
	     </ul>
	     <ul class="invoice deploy">
	      <li class="name">用户偷听音频价格</li>
	      	<li class="operating">  
	       <span class="">
	       	<label>
	       		<span class="lbl" style="font-weight:bold;color:#dd0c9b;">${sets.tapnb}<span style="font-weight:normal;">系统虚拟币</span></span></label></span>          
	      </li>
	      <li class="info">
	      		<form action="updatetapnb" method="post">
		      		<ul>
					     <li class="clearfix">
						     <label class="label_name col-xs-2 col-lg-2"><i></i>数值：</label>
						     <div class="formControls col-xs-6">
						     <input type="hidden" value="${sets.id}" id="idfour" name="idfour" readonly="readonly">
						     <input type="number" class="input-text col-xs-12" value="${sets.tapnb}" id="rechargearr" name="rechargearr" datatype="*1-16" nullmsg="**" placeholder="偷看/听所需虚拟币" style="width:100%;"></div>
						    <div class="col-4"> <span class="Validform_checktip"></span></div>
					     </li>
					     <li class="clearfix" style="text-align:left;">
					     	<label style="text-align:left;color:#dd0c9b;"><i></i>设置APP用户偷听声音的系统币</label>
					     	
					     </li>
					     <li class="clearfix" style="text-align:center;">
					     	<br><input class="btn button_btn bg-deep-blue " type="submit" id="Add_Administrator" value="提交设置" style="background:#dd0c9b!important;border:1px #dd0c9b solid;">
						</li>
					</ul>
				</form>
	      </li>
	     </ul>
	     <ul class="invoice deploy">
	      <li class="name">用户发动态奖励系统币设置</li>
	      	<li class="operating">  
	       <span class="">
	       	<label>
	       		<span class="lbl" style="font-weight:bold;color:#0cd1dd;">${sets.rewardnb}<span style="font-weight:normal;">系统虚拟币</span></span></label></span>          
	      </li>
	      <li class="info">
	      		<form action="updaterewardnb" method="post">
		      		<ul>
					     <li class="clearfix">
						     <label class="label_name col-xs-2 col-lg-2"><i></i>数值：</label>
						     <div class="formControls col-xs-6">
						     <input type="hidden" value="${sets.id}" id="idfive" name="idfive" readonly="readonly">
						     <input type="number" class="input-text col-xs-12" value="${sets.rewardnb}" id="rewardnb" name="rewardnb" datatype="*1-16" nullmsg="**" placeholder="发单奖励信息" style="width:100%;"></div>
						    <div class="col-4"> <span class="Validform_checktip"></span></div>
					     </li>
					     <li class="clearfix" style="text-align:left;">
					     	<label style="text-align:left;color:#0cd1dd;"><i></i>设置用户发动态奖励系统币设置</label>
					     </li>
					     <li class="clearfix" style="text-align:center;">
					     	<br><input class="btn button_btn bg-deep-blue " type="submit" id="Add_Administrator" value="提交设置" style="background:#0cd1dd!important;border:1px #0cd1dd solid;">
						</li>
					     
					</ul>
				</form>
	      </li>
	     </ul>
	     
	     <!-- 蓝砖 -->
	     <ul class="invoice deploy" style="margin-top:11px;">
	      <li class="name" style="color:blue;">蓝砖信息配置</li>
	      	<li class="operating">  
	       <span class="">
	       	<label>
	       		<span class="lbl" style="font-weight:bold;color:blue;">￥1元/RMB = ${sets.bluebrick}<span style="font-weight:normal;">蓝砖</span></span></label></span>          
	      </li>
	      <li class="info">
	      		<form action="updatebluebrick" method="post">
		      		<ul>
					     <li class="clearfix">
						     <label class="label_name col-xs-2 col-lg-2"><i></i>数值：</label>
						     <div class="formControls col-xs-6">
						     <input type="hidden" value="${sets.id}" id="idsix" name="idsix" readonly="readonly">
						     <input type="number" class="input-text col-xs-12" value="${sets.bluebrick}" id="bluebrick" name="bluebrick" datatype="*1-16" nullmsg="**" placeholder="蓝砖数值" style="width:100%;"></div>
						    <div class="col-4"> <span class="Validform_checktip"></span></div>
					     </li>
					     <li class="clearfix" style="text-align:left;">
					     	<label style="text-align:left;color:blue;"><i></i>系统设置人民币与蓝砖的关系</label>
					     </li>
					     <li class="clearfix" style="text-align:center;">
					     	<br><input class="btn button_btn bg-deep-blue " type="submit" id="Add_Administrator" value="提交设置" style="background:#1309d2!important;border:1px #1309d2 solid;">
						</li>
					</ul>
				</form>
	      </li>
	     </ul>
	     <!-- 蓝砖 -->
	     
	     <!-- 红砖 -->
	     <ul class="invoice deploy" style="margin-top:11px;">
	      <li class="name" style="color:red;">红砖信息配置</li>
	      	<li class="operating">  
	       <span class="">
	       	<label>
	       		<span class="lbl" style="font-weight:bold;color:red;">￥1元/RMB = ${sets.redsbrick}<span style="font-weight:normal;">红砖</span></span></label></span>          
	      </li>
	      <li class="info">
	      		<form action="updateredsbrick" method="post">
		      		<ul>
					     <li class="clearfix">
						     <label class="label_name col-xs-2 col-lg-2"><i></i>数值：</label>
						     <div class="formControls col-xs-6">
						     <input type="hidden" value="${sets.id}" id="idseven" name="idseven" readonly="readonly">
						     <input type="number" class="input-text col-xs-12" value="${sets.redsbrick}" id="redsbrick" name="redsbrick" datatype="*1-16" nullmsg="**" placeholder="红砖数值" style="width:100%;"></div>
						    <div class="col-4"> <span class="Validform_checktip"></span></div>
					     </li>
					     <li class="clearfix" style="text-align:left;">
					     	<label style="text-align:left;color:red;"><i></i>系统设置人民币与红砖的关系</label>
					     </li>
					     <li class="clearfix" style="text-align:center;">
					     	<br><input class="btn button_btn bg-deep-blue " type="submit" id="Add_Administrator" value="提交设置" style="background:#d20909!important;border:1px #d20909 solid;">
						</li>
					     
					</ul>
				</form>
	      </li>
	     </ul>
	     <!-- 红砖 -->
	     
	     <!-- 蓝砖:红砖 -->
	     <ul class="invoice deploy" style="margin-top:11px;">
	      <li class="name"><span style="color:blue;">蓝砖</span>&nbsp;:&nbsp;<span style="color:red;">红砖</span></li>
	      	<li class="operating">  
	       <span class="">
	       	<label>
	       		<span class="lbl" style="font-weight:bold;color:blue;">${sets.bluerates}<span style="font-weight:normal;">蓝砖</span></span>=
	       		<span class="lbl" style="font-weight:bold;color:red;">${sets.redsrates}<span style="font-weight:normal;">红砖</span></span>
	       	</label>
	       </span>
	      </li>
	      <li class="info">
	      		<form action="updaterates" method="post">
		      		<ul>
					     <li class="clearfix">
						     <div class="formControls col-xs-6">
						     	<input type="hidden" value="${sets.id}" id="ideight" name="ideight" readonly="readonly">
						     	<table>
						     		<tr>
						     			<td align="left" style="vertical-align:middle">
						     				<input type="number" value="${sets.bluerates}" id="bluerates" name="bluerates" datatype="*1-16" nullmsg="**" placeholder="蓝砖数" style="width:75px;" align="left">
						     			</td>
						     			<td style="color:blue;">蓝</td>
						     			<td style="font-size:18px;font-weight:bold;">=</td>
						     			<td style="color:red;">红</td>
						     			<td>
						     				<input type="number" value="${sets.redsrates}" id="redsrates" name="redsrates" datatype="*1-16" nullmsg="**" placeholder="红砖数" style="width:75px;">
						     			</td>
						     		</tr>
						     	</table>
						     </div>
					     </li>
					     <li class="clearfix" style="text-align:left;">
					     	<label style="text-align:left;"><i></i>系统设置<span style="color:blue;">蓝砖</span>与<span style="color:red;">红砖</span>的兑换比率</label>
					     </li>
					     <li class="clearfix" style="text-align:center;">
					     	<br><input class="btn button_btn bg-deep-blue " type="submit" id="Add_Administrator" value="提交设置" style="background:#d20909!important;border:1px #d20909 solid;">
						</li>
					     
					</ul>
				</form>
	      </li>
	     </ul>
	     <!-- 蓝砖:红砖 -->
	  </div>
	  
	  
	  </div>
	 </div>
	 
	</div>
	</body>
	<script>
	 /*radio激发事件*/
	function Enable(){}
	function closes(){}
	 function Enable(ojb,id){
		    layer.alert('确认要开启吗？',{icon: 1},function(index){
			layer.msg('开启成功!',{icon:1,time:1000});
				
		});
	 }
	 function closes(ojb,id){
		    layer.alert('确认要关闭该支付功能吗？',function(index){
			layer.msg('以关闭!',{icon:1,time:1000});
				
			})
	}
	function invoice_Enable(){ $('.Reply_style').css('display','block');}
	function invoice_closes(){$('.Reply_style').css('display','none')}	
	
	</script>
</html>
