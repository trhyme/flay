<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<style type="text/css">
		body, html,#allmap {width: 100%;height:97%;overflow: hidden;margin:0;font-family:"微软雅黑";}
	</style>
	<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=adSON2fE9GRIacyodV4uiCmvCCoK9L4A"></script>
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
	<script src="js/jquery.dataTables.min.js"></script>
	<script src="js/jquery.dataTables.bootstrap.js"></script>
	
	<script type="text/javascript" src="http://union.mapbar.com/apis/maps/free?f=mapi&v=31.3&k=aCW9cItqL6QpaR0pLh8mcBEmcn4eb79hMYF5MXTrEeJsT7J6T7P9TRD7El==@7aMhs7F4s54ecLYLQpaB7T7h95MpsccQpMEMFp79W9pRs5qRp9cWphcraC0pFslFAV="></script>  
<title>用户地图展示</title>
</head>
<body>
	<div id="allmap"></div>
	<div style="height:40px;">
		<!-- 分页 -->
		<input style="display:none;" id="nowpage" name="nowpage" value="${nowpage}" readonly="readonly">
		<input style="display:none;" id="counts" name="counts" value="${counts}" readonly="readonly">
		<div align="left" style="width:100%; min-height:50px; border: 0px red solid; margin-top:2px;z-index:-1;">
			<div id="Pagination" class="pagination"></div>
			<div id="jsonArray">
			</div>
			<br style="clear:both;" />
		</div>
		<span>一共数据：${counts} 条</span>
		<!-- 分页 -->
	</div>
	
	<script type="text/javascript">
		/* // 百度地图API功能
		var map = new BMap.Map("allmap");
		var point = new BMap.Point(104.06, 30.67);
		map.centerAndZoom(point, 6);
		map.enableScrollWheelZoom(true); //开启鼠标滚轮缩放
		
		 */
		
		 // 初始化地图  
		 var maplet = new Maplet("allmap");  
         maplet.centerAndZoom(new MPoint(104.06,30.67), 2);  
         maplet.addControl(new MStandardControl());  
         maplet.showOverview(false); 
         
         
         // 秘密数据  
         //var message = ["白金海岸度假酒店","椰子大观园","东郊椰林","文昌孔庙","八门湾红树林","宋氏故居","铜鼓岭","石头公园","张云逸纪念馆","七洲列岛","月亮湾","七星岭位"];  

         // 五个标注的经纬度  
        /*  var latlons = ["110.81175, 19.53714","110.79571,19.55129", "110.79338,19.56138",  
                        "110.73344,19.60574", "110.81292,19.63778",  
                        "110.90329,19.72414", "111.03018,19.64755",  
                        "111.02919,19.64479",  
                        "110.7907, 19.56886","111.2561, 19.95932",  
                        "111.02651,19.67995","111.65088,20.11463"];   */
         
         
         
         var data1='${strlist}';  
        var data=JSON.parse(data1);
        var message=data;
      // 增加标注到地图  
         for(var i=0; i<data.length; i++) { 
             var newMarker = new MMarker(  
                 new MPoint(data[i]),  
                 new MIcon("http://images.leqtch.com/images/mapthere.png",20,30),  
                 new MInfoWindow("<b>#" + (i+1) + "</b>","")  
             );  
             maplet.addOverlay(newMarker);  
             newMarker.closureData = i;
             MEvent.addListener(newMarker, "click", function(marker) {  
                 // 闭包机制，在每个标注的click事件处理程序中可以访问到该  
                 // 处理函数外部的变量message。  
                 marker.info = new MInfoWindow(  
                     "<b>" + (message[marker.closureData]) + "</b>",  
                     message[marker.closureData]  
                 );
                 marker.openInfoWindow();  
             })  
         }  
     // 增加标注到地图  
        
		
		//创建小狐狸
		/* var pt = new BMap.Point(104.06, 30.77);
		var myIcon = new BMap.Icon("http://lbsyun.baidu.com/jsdemo/img/fox.gif", new BMap.Size(300,157));
		var marker2 = new BMap.Marker(pt,{icon:myIcon});  // 创建标注
		map.addOverlay(marker2);              // 将标注添加到地图中 */
	</script>
	<script type="text/javascript" src="js/jquery.appuserpagination2.js"></script>
	<script type="text/javascript">
		$(function(){
			var nowpage=$("#nowpage").val();  //当前页数
			var counts=$("#counts").val();	  //总数
			// 创建分页元素
			$("#Pagination").pagination(counts, {
				num_edge_entries: 1,
				num_display_entries: 30,
				current_page:nowpage-1,
				items_per_page:30,
				num_display_entries:30,
				callback: pageselectCallback  //回调函数
			});
		});
	</script>
</body>
</html>