<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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
<script type="text/javascript" src="js/jquery.cookie.js"></script>
<script src="js/Sellerber.js" type="text/javascript"></script>
<script src="js/shopFrame.js" type="text/javascript"></script>
<script src="js/dist/echarts.js" type="text/javascript"></script>
<script src="js/jquery.nicescroll.js" type="text/javascript"></script>
<title>系统首页信息</title>
</head>
	<body  id="iframe_box">
		<div id="index_style" style="height:100%" class="clearfix">
		<div class="spacing_style" ></div>
		 <div class="margin-bottom clearfix ">
		  <div class="col-xs-2 col-sm-2 col-md-2 col-lg-4">
		   <a href="appuserpage" class="tile-button btn_Plate bg-deep-blue">
		   <div class="carousel Module_info">
		   <div class="left_img bg_color_bule">
		    <i class="fa fa-cny"></i>
		    <h3>用户总数</h3>
		   </div>
		   <div class="right_info">${usernum}人</div>
		   </div>
		   </a>
		  </div>
		   <div class="col-xs-2 col-sm-2 col-md-2 col-lg-4">
		   <a href="memberlist" class="tile-button btn_Plate bg-green">
		   <div class="carousel Module_info">
		   <div class="left_img bg_color_green">
		    <i class="fa  fa-comments-o"></i>
		    <h3>会员人数</h3>
		   </div>
		   <div class="right_info">${membercount}人</div>
		   </div>
		   </a>
		  </div>
		   <div class="col-xs-2 col-sm-2 col-md-2 col-lg-4">
		   <a href="voicelist" class="tile-button btn_Plate bg-red ">
		   <div class="carousel Module_info">
		   <div class="left_img bg_color_red">
		    <i class="fa fa-shopping-cart"></i>
		    <h3>动态内容消息</h3>
		   </div>
		   <div class="right_info">${voicesunm}条</div>
		   </div>
		   </a>
		  </div> 
		 <div class="col-xs-2 col-sm-2 col-md-2 col-lg-4">
		   <a href="javascript:ovid()" class="tile-button btn_Plate bg-orange">
		   <div class="carousel Module_info">
		   <div class="left_img bg_color_orange">
		    <i class="fa  fa-volume-up "></i>
		    <h3>数据待定</h3>
		   </div>
		   <div class="right_info">数据待定</div>
		   </div>
		   </a>
		  </div>
		  <div class="col-xs-2 col-sm-2 col-md-2 col-lg-4">
		   <a href="javascript:ovid()" class="tile-button btn_Plate bg-purple">
		   <div class="carousel Module_info">
		   <div class="left_img bg_color_purple">
		    <i class="fa  fa-clock-o "></i>
		    <h3>数据待定</h3>
		   </div>
		   <div class="right_info">数据待定</div>
		   </div>
		   </a>
		  </div>
		   <div class="col-xs-2 col-sm-2 col-md-2 col-lg-4">
		   <a href="javascript:ovid()" class="tile-button btn_Plate bg-yellow">
		   <div class="carousel Module_info">
		   <div class="left_img bg_color_yellow">
		    <i class="fa  fa-clock-o "></i>
		    <h3>数据待定</h3>
		   </div>
		   <div class="right_info">数据待定</div>
		   </div>
		   </a>
		  </div>
		 </div>
		 <div class="center  clearfix margin-bottom">
		 <!--店铺信息-->
		 <div class="col-xs-9">
		 <div class="Shops_info clearfix frame">
		  <div class="left_shop">
		  <div class="left_shop_logo">
		   <div class="shop_logo"><span class="bg_yuan"></span><img src="images/dp_logo.png" /></div>
		   <a href="#" class="btn bg-deep-blue paddings">进入首页</a>
		   </div>
		   <div class="Shops_content">
		   <p style="text-align:left;"><label class="name">系统名称：</label>初遇平台管理系统</p>
		   <p style="text-align:left;"><label class="name">公司名称：</label>乐圈移动娱乐成都有限公司</p>
		   <p style="text-align:left;"><label class="name">上线时间：</label>2017年09月19日</p>
		   <ul class="clearfix" style="text-align:left;">
		    <li><label class="name">APP名称：</label>初遇</li>
		    <li><label class="name">APP版本：</label>1.1版本</li>
		    </ul>
		   </div>
		  
		  
		  <div class="right_shop">
		   <p> 系统说明：</p>
		   <ul>
		   <li><label class="name">版本信息</label><span class="score">1.0.1</span></li>
		   <li><label class="name"> IP地址</label><span class="score" style="font-size:12px;">120.77.62.203</span></li>
		   <li><label class="name">域名</label><span class="score" style="font-size:12px;">app.leqcth.com</span></li>
		   </ul>
		  </div>
		  </div>
		  <div class="operating_style Quick_operation" >
		  <ul>
		  	<c:if test="${iphoneconf eq 0}">
			   <li class="submenu">
			   	<a href="updateiphoneconfigure?stratu=1" name="" class="btn" title="添加产品">
			   		<i class="fa  fa-edit"></i>&nbsp;屏蔽iPhone的微信、支付宝支付</a>
			   </li> 
		   </c:if>
		   <c:if test="${iphoneconf eq 1}">
			   <li class="submenu">
			   	<a href="updateiphoneconfigure?stratu=0" name="" class="btn" title="添加产品">
			   		<i class="fa  fa-edit"></i>&nbsp;开启iPhone的微信、支付宝支付</a>
			   </li> 
		   </c:if>
		   <!-- <li class="submenu"><a href="javascript:void(0)" name="add_Advertising.html" class="btn" title="添加广告"><i class="fa  fa-edit"></i>&nbsp;待定菜单按钮2</a></li>
		   <li class="submenu"><a href="javascript:void(0)" name="add_Article.html" class="btn" title="添加文章"><i class="fa  fa-edit"></i>&nbsp;待定菜单按钮3</a></li>
		   <li class="submenu"><a href="javascript:void(0)" name="add_Singlepag.html" class="btn" title="新增单页面"><i class="fa  fa-edit"></i>&nbsp;待定菜单按钮4</a></li>
		  	 -->
		  </ul>  
		  </div>
		 </div>
		 </div>
		 <div class=" col-xs-3">
		  <div class="admin_info frame clearfix">
		  <div class="title_name"><i></i>用户反馈信息记录 <a href="feedbacklist">+更多</a></div>
		  <table class="record_list table table_list">
		  <tbody>
		  	<c:forEach items="${feedback}" var="feed">
		   		<tr>
		   			<td>${feed.unames}</td>
		   			<td>
		   				<c:if test="${fn:length(feed.conten)>19}">
						    ${fn:substring(feed.conten,0,19)}...
						</c:if>
		   				<c:if test="${fn:length(feed.conten)<=19}">
						    ${feed.conten}
						</c:if>
		   			</td>
		   		</tr>
		   	</c:forEach>
		   </tbody>
		  </table>
		  </div>
		 </div>
		 </div>
		 <!---->
		 <div class="Order_form ">
		  <!-- <div class="col-xs-6 col-lg-7">
		  <div class="frame margin-right clearfix">
		  <div class="title_name"><i></i>APP信息资源查看</div>
		  <div class="clearfix">
		  <div class="col-xs-3 col-lg-6 ">   
		   <div class="prompt_name"><i class="icon_Order"></i>用户发单</div>
		   <ul class="padding list_info">   
		   <li>已发单总数 <a href="">(02)</a></li>
		   <li>已接单总数 <a href="">(32)</a></li>
		   <li>未接单总数 <a href="">(02)</a></li>
		   </ul>
		  </div>
		   <div class="col-xs-3 col-lg-6">    
		   <div class="prompt_name"><i class="icon_Promotions"></i>用户动态信息记录</div>
		   <ul class="padding list_info">   
		   <li>音频动态 &nbsp;<a href="">664</a></li>
		   <li>视频动态 &nbsp;<a href="">482</a></li>
		   <li>图片文字 &nbsp;<a href="">561</a></li>
		   </ul>
		  </div>
		   <div class="col-xs-3 col-lg-6">    
		   <div class="prompt_name"><i class="icon_Aftermarket"></i>消费信息记录</div>
		   <ul class="padding list_info"> 
		   <li>已充值总金额<a href="">￥6666</a></li>
		   <li>已消费总金额<a href="">￥5520</a></li>
		   <li>总系统币剩余<a href="">45556</a></li>  
		   </ul>
		  </div>
		   <div class="col-xs-3 col-lg-6 ">  
		   <div class="prompt_name"><i class="icon_Billing"></i>结算</div>
		    <ul class="padding list_info">  
		   <li>待支付 &nbsp;<a href="">(02)</a></li>
		   <li>待结算确认 &nbsp;<a href="">(32)</a></li>
		   </ul>
		  </div>
		  </div>
		  <div class="dd_echarts">
		   <div id="main" style="width:100%; height:225px"></div>
		  </div>
		  </div>
		  </div> -->
		  <div class="col-xs-6 ranking_style col-lg-5" >
		  <div class="frame clearfix">
		   <div class="title_name"><i></i>音频/视频/图片文字动态</div>
		   <table  class="table table_list ranking_list">
		    <thead>
		     <th width="50">排名</th>
		     <th>昵称</th>
		     <th>动态</th>
		     <th width="160">发表时间</th>
		    </thead>
		    <tbody>
		    	<%int x=0; %>
		    	<c:forEach items="${voiceslist}" var="voi">
		    		<%x+=1; %>
				     <tr>
				      <td ><em><%out.print(x); %></em></td>
				      <td>${voi.username}</td>
				      <td><a href="sharevoicesgeturl/${voi.id}" target="_blank">
				      	<c:if test="${fn:length(voi.info)>30}">
						    ${fn:substring(voi.info,0,30)}...
						</c:if>
				      	<c:if test="${fn:length(voi.info)<=30}">
						    ${voi.info}...
						</c:if>
				      </a></td>
				      <td>
				      	<a href="sharevoicesgeturl/${voi.id}" target="_blank">${voi.date}</a>
				      </td>
				     </tr>
				 </c:forEach>
		     
		    </tbody>
		   </table>
		  </div>
		  </div>
		 </div>
		</div>
	</body>
	<script>
	
	/***WebSocket***/
	
	/***WebSocket***/
	
	
	//设置框架
	 $(function() { 
		$("#index_style").frame({
			float : 'left',
			menu_nav:'.Quick_operation',
			color_btn:'.skin_select',
			Sellerber_menu:'.list_content',
			Sellerber_header:'.Sellerber_header',
		});
	});
	$("#iframe_box").niceScroll({  
		cursorcolor:"#888888",  
		cursoropacitymax:1,  
		touchbehavior:false,  
		cursorwidth:"5px",  
		cursorborder:"0",  
		cursorborderradius:"5px"  
	});
	/*********************/
	   require.config({
	            paths: {
	                echarts: './js/dist'
	            }
	        });
	        require(
	            [
	                'echarts',
					'echarts/theme/macarons',
	                'echarts/chart/line',   // 按需加载所需图表，如需动态类型切换功能，别忘了同时加载相应图表
	                'echarts/chart/bar'
	            ],
	            function (ec,theme) {
	                var myChart = ec.init(document.getElementById('main'),theme);
	               option = {
	    title : {
	        text: '当周信息记录',
	        subtext: '每周7天的交易记录'
	    },
	    tooltip : {
	        trigger: 'axis'
	    },
	    legend: {
	        data:['所有订单','已完成','未完成']
	    },
	    toolbox: {
	        show : true,
	        feature : {
	            mark : {show: true},
	            dataView : {show: true, readOnly: false},
	            magicType : {show: true, type: ['line', 'bar']},
	            restore : {show: true},
	            saveAsImage : {show: true}
	        }
	    },
	    calculable : true,
	    xAxis : [
	        {
	            type : 'category',
	            boundaryGap : false,
	            data : ['周一','周二','周三','周四','周五','周六','周日']
	        }
	    ],
	    yAxis : [
	        {
	            type : 'value',
	            axisLabel : {
	                formatter: '{value}单'
	            }
	        }
	    ],
	    series : [
	        {
	            name:'所有订单',
	            type:'line',
	            data:[110, 110, 150, 130, 125, 133, 106],
	            markPoint : {
	                data : [
	                    {type : 'max', name: '最大值'},
	                    {type : 'min', name: '最小值'}
	                ]
	            },
	            markLine : {
	                data : [
	                    {type : 'average', name: '平均值'}
	                ]
	            }
	        },
	        {
	            name:'已完成',
	            type:'line',
	            data:[110, 105, 140, 130, 110, 121, 100],
	            markPoint : {
	                data : [
	                    {name : '周最低', value : -2, xAxis: 1, yAxis: -1.5}
	                ]
	            },
	            markLine : {
	                data : [
	                    {type : 'average', name : '平均值'}
	                ]
	            }
	        },
			   {
	            name:'未完成',
	            type:'line',
	            data:[0, 5, 10, 0, 15, 12, 6],
	            markPoint : {
	                data : [
	                    {name : '周最低', value : -2, xAxis: 1, yAxis: -1.5}
	                ]
	            },
	            markLine : {
	                data : [
	                    {type : 'average', name : '平均值'}
	                ]
	            }
	        }
	    ]
	};
	                  
				myChart.setOption(option);
				}
				);
	</script>
</html>
