package com.java.letch.app.socket;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.java.letch.app.socket.two.ServerSocketTwo;

/***
 * Web服务启动完成后执行
 * @author Administrator
 * 2017-12-13
 */
@SuppressWarnings("serial")
public class StartupServlet extends HttpServlet{
	public void init() throws ServletException {  
		try {
			/*System.out.println("=====> Socket服务启动Begin");*/
			//1.0
			/*Thread.sleep(100);
			FakeNameSocketServer.startSocketService();*/
			//2.0
			/*int port = 65432;  
	        ServerSocketTwo server = new ServerSocketTwo(port);  
	        server.start();  
	        System.out.println("开启：");*/
			/*System.out.println("=====> Socket服务启动成功");*/
		} catch (Exception e) {
			System.out.println("=====> Socket服务启动失败");
			e.printStackTrace();
		}
		
    }  
}
