package com.java.letch.app.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.annotation.Resource;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/***
 * Socket长连接服务
 * @author Administrator
 * 2017-12-13
 */
public class FakeNameSocketServer {
	public static final int PORT = 4399;		//长连接端口
	//启动Socket服务
	public static void startSocketService() throws IOException{
		ServerSocket server = null; 
		try {
			server = new ServerSocket(PORT); 
			System.out.println("==>Socket服务启动成功");
			Socket socket = null; //创建线程池和消息队列都是有界的，因此无论有多少个客户并发连接数量有多大，都不会把系统资源消耗完,指定线程池和任务队列大小
			NameServerHandlerExecutePool singleExecutor = new NameServerHandlerExecutePool(100, 1000); 
			
			 
			while (true) { 
				socket = server.accept(); 
				//把任务交给线程执行器 
				singleExecutor.execute(new HandlerClientRunnable(socket)); 
			} 
			
		} catch (Exception e) {
			System.out.println(e);
			System.out.println("==> Socket启动失败");
		}finally{
			if (server != null) { 
				server.close(); 
				server = null; 
			}
		}
	}
	
	
}
