<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="icon" href="images/tomcat.png" type="image/x-icon"/>
<title>初遇分享</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="keywords" content="Fantastic Responsive web template, Bootstrap Web Templates, Flat Web Templates, Android Compatible web template,
	SmartPhone Compatible web template, free WebDesigns for Nokia, Samsung, LG, Sony Ericsson, Motorola web design" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=yes">  
<link rel="icon" href="../images/tomcat.png" type="image/x-icon"/>
<script type="application/x-javascript"> addEventListener("load", function() { setTimeout(hideURLbar, 0); }, false); function hideURLbar(){ window.scrollTo(0,1); } </script>
<!-- Custom Theme files -->
<link href="../css/bootstrap.css" type="text/css" rel="stylesheet" media="all">
<link href="../css/style.css" type="text/css" rel="stylesheet" media="all">   
<link href="../css/carousel.css" type="text/css" rel="stylesheet" media="all"> 
<link href="../css/font-awesome.css" rel="stylesheet"> <!-- font-awesome icons -->
<!-- //Custom Theme files --> 
<!-- js -->
<script src="../js/jquery-2.2.3.min.js"></script>  
<!-- //js -->
<!-- web-fonts -->  
<link href="//fonts.googleapis.com/css?family=Yanone+Kaffeesatz:200,300,400,700" rel="stylesheet">
<link href='//fonts.googleapis.com/css?family=Roboto:400,100,100italic,300,300italic,400italic,500,500italic,700,700italic,900,900italic' rel='stylesheet' type='text/css'>
<!-- //web-fonts -->
		<link rel="stylesheet" href="../css/api.css" />
        <link rel="stylesheet" href="../css/ct.css" />
        <style>
				.Headportrait{
					width: 55px;
					height: 55px;
					
				}
				.Headportrait img{
					height: 100%;
					width: 100%;
					border-radius: 5px;
				}
				.userinfo{
					margin: 2% 5%;
					width: 90%;
					height: 55px;
				}
				.userName{
					margin: 0px 12px;
					height: 20px;
					margin-right: 5%;
					width: 55%;
				}
				.userTime{
					margin-top: 10px;
					height: 20px;
					width: 50%;
				}
				.content{
					 margin: 2% 5%;
					 width: 90%;
					 word-wrap: break-word;
				}
				/*.flex-box{
					border: 1px solid red;
				}*/
				
				.img_Exhibition img{
					height: 100px;
					max-width:  155px;
					text-align: center;
				}
				.img_Exhibition_content{
					text-align: center;
					width: 90%;
					margin: 2% 5%;
				}
				.share_Exhibition_content{
					height: 100px;
					width: 90%;
					margin: 4% 6%;
				}
				.share img{
					width: 20px;
					height: 20px;
				}
				.comment img{
					width: 20px;
					height: 20px;
				}
        </style>

</head>
<body style="background-color:#eeeeee;" onclick="click_btn();">
	<div id="wrap" >
        	<div id="main" >
        		<div style="margin:5px;/* border:1px red solid; */box-shadow:0px 2px 5px 3px #aaa;background-color:#ffffff;">
    			<div style="margin-top:5px;"></div><br>
    			<div class="userinfo flex-box">
    				<div class="Headportrait flex-1">
						<img src="${voices.headpic}"/>
					</div>
					<div class="userName flex-3">
						<span style="font-size:14px;font-weight:bold;">${voices.username}</span>
							<div class="userTime flex-3">
								${voices.date}
							</div>	
					</div>
    			</div>
    			<div class=" content flex-1" style="margin-top:30px;">
    				${voices.info}
    			</div>
    			<div class="img_Exhibition_content flex-box" id="show_img" name="${voices.photo}" style="margin-top:20px;">
    				<!-- <div class="flex-1 img_Exhibition"><img src="../img/web1.png" /></div>
    				
    				<div class="flex-1 img_Exhibition"><img src="../img/web1.png"/></div> -->
    			</div>
    			<div style="margin-left:8px;">
    				<c:if test="${voices.voiceurl ne null}">
    				  		 <!-- 语音 -->
							<audio src="${voices.voiceurl}" controls="controls">
							</audio>
							<!-- <label>Your browser does not support the audio element.</label> -->
							<!-- 语音 -->
    				  </c:if>
    			</div>
    			
    			<div class="share_Exhibition_content flex-box" style="margin-top:18px;height:40px;">
    				  <div  class="share flex-3" style="height:35px;">
    				  	<img src="../img/zhanfa.png" />
    				  </div>
    				  <div class="comment flex-3 flex-box" style="text-align: right;height:35px;">
    				  		<div class="flex-1 "><img src="../img/724098532809413895.png"/>&nbsp;${voices.fabulous}</div>
    				  		<div class="flex-1 "><img src="../img/commit.png"/>&nbsp;${voices.commentnum}</div>
    				  		<div class="flex-1 "><img src="../img/178680863772151952.png"/>&nbsp;${voices.collection}</div>
    				  </div>
    				  
    			</div>
        	</div>
        	<div style="margin:5px;margin-top:-5px;/* border:1px red solid; */box-shadow:0px 2px 5px 3px #aaa;background-color:#ffffff;text-align:center;">
				<span style="margin:16px;">加载更多 . . .</span><br>
				<span style="margin:16px;margin-top:-3px;">↓ ↓ ↓ ↓</span>
			</div>
        	
        	</div>
        </div>

	<div style="position: fixed; bottom:0;background-color:#000000;width:100%;height:60px;opacity:0.9;">
		<div class="container" >
			<table style="width:100%;">
			
				<tr>
					<td>
						<img alt="" src="../images/logodownapp.png" width="50px" style="margin-top:5px;">
					</td>
					<td>
						<span style="font-size:15px;font-weight:bold;color:#fff;font-family:'微软雅黑';">邂逅你的52HZ</span><br>
						<span style="font-size:15px;font-weight:bold;color:#fff;font-family:'微软雅黑';">最走心的语音社交</span>
					</td>
					<td style="text-align:center;">
						<button class="btn btn-primary" style="width:86px;height:32px;font-family:'微软雅黑';" onclick="click_btn();">去看看</button>
					</td>
				</tr>
			</table>
			<!-- <p class="footer-class">下载ＡＰＰ </p> -->
		</div>
	</div>
   
	<script type="text/javascript" src="../js/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="../js/move-top.js"></script>
	<script type="text/javascript" src="../js/easing.js"></script>	
	<script type="text/javascript">
			jQuery(document).ready(function($) {
				show_images();
				
				$(".scroll").click(function(event){		
					event.preventDefault();
			
			$('html,body').animate({scrollTop:$(this.hash).offset().top},1000);
				});
			});
		function click_btn(){
			if (/(iPhone|iPad|iPod|iOS)/i.test(navigator.userAgent)) {
			    alert("手机iPhone");  
			    /* window.location.href ="iPhone.html"; */
			} else if (/(Android)/i.test(navigator.userAgent)) {
			    alert("手机Android"); 
			    /* window.location.href ="Android.html"; */
			} else {
				alert("网页")
			    /* window.location.href ="pc.html"; */
			};
		}
		
		
		function show_images(){
			
			
			$("#show_img").html("");
			var ssss=$("#show_img").attr("name");
			if(ssss==''){}else{
				
				var pixclist=ssss.split(",");///JSON.parse(index.id);
				var str="";
				for(var i=0;i<2;i++){
					str+='<div class="flex-1 img_Exhibition" style=""><img src="'+pixclist[i]+'" /></div>';
					/* str+='<li style="border:9px solid #fff;"><img alt="" src="'+pixclist[i]+'" style="width:100%;height:100%;"></li>'; */
				}
				$("#show_img").html(str);
			}
			
		}
		
		
		
	</script>
	<!-- //end-smooth-scrolling -->	
	<!-- smooth-scrolling-of-move-up -->
	<script type="text/javascript">
		$(document).ready(function() {
			/*
			var defaults = {
				containerID: 'toTop', // fading element id
				containerHoverID: 'toTopHover', // fading element hover id
				scrollSpeed: 1200,
				easingType: 'linear' 
			};
			*/
			
			$().UItoTop({ easingType: 'easeOutQuart' });
			
		});
	</script>
	<!-- //smooth-scrolling-of-move-up -->  
	<!--scrolling js--> 
	<script src="../js/jquery.nicescroll.js"></script>
	<script src="../js/scripts.js"></script> 
	<!--//scrolling js-->
	
	<!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="../js/bootstrap.js"></script>
	
</body>
</html>