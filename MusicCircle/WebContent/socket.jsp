<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Socket测试</title>
</head>
<body>
	{"userid":24146,"sex":"MM"}<br/><input id="text" type="text"/>
<button onclick="send()">发送消息</button>
<hr/>
<button onclick="closeWebSocket()">关闭WebSocket连接</button>
<hr/>
<div id="message"></div>
</body>
<script type="text/javascript">
var websocket = null;
//判断当前浏览器是否支持WebSocket
if ('WebSocket' in window) {
							//120.77.62.203:8080
							//ws://120.77.62.203:8080/MusicCircle/websocket/24146WM
							//ws://app.leqtch.com/MusicCircle/websocket/24146WM
							//http://app.leqtch.com
websocket = new WebSocket("ws://app.leqtch.com/MusicCircle/websocket/24146WM");
}
else {
alert('当前浏览器 Not support websocket')
}
//连接发生错误的回调方法
websocket.onerror = function () {
setMessageInnerHTML("WebSocket连接发生错误");
};
//连接成功建立的回调方法
websocket.onopen = function () {
setMessageInnerHTML("WebSocket连接成功");
}
//接收到消息的回调方法
websocket.onmessage = function (event) {
	setMessageInnerHTML(event.data);
}
//连接关闭的回调方法
websocket.onclose = function () {
setMessageInnerHTML("WebSocket连接关闭");
}
//监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
window.onbeforeunload = function () {
closeWebSocket();
}
//将消息显示在网页上
function setMessageInnerHTML(innerHTML) {
	document.getElementById('message').innerHTML += innerHTML+"--";
}
//关闭WebSocket连接
function closeWebSocket() {
websocket.close();
}
//发送消息
function send() {
var message = document.getElementById('text').value;
websocket.send(message);
}
</script>
</html>