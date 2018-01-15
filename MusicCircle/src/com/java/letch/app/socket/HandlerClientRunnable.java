package com.java.letch.app.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;

/***
 * 多线程业务逻辑处理类
 * @author Administrator
 * 2017-12-13
 */
public class HandlerClientRunnable implements Runnable{

	private Socket socket; 		//Socket类
	
	public HandlerClientRunnable(Socket socket) { 
		this.socket = socket; 
	} 
	
	@Override
	public void run() {
		BufferedReader in = null; 
		PrintWriter out = null; 
		try {
			//包装socket的输入流 
			in = new BufferedReader(new InputStreamReader(this.socket.getInputStream())); 
			//包装socket的输出流 
			out = new PrintWriter(this.socket.getOutputStream(), true); 
			String content = null; 
			while (true) { 
				//不断的读取客户端发送过来的数据 
				System.out.println("====> 不断读取客户端发送过来的数据===> read client send content"); 
				content = in.readLine(); 
				//如果读到的数据为空，就继续读 
				if (content == null) { 
					System.out.println("====> 读取数据为空  ====> read client send content is null "); 
					break; 
				}
				
				//读取到数据
				System.out.println(" ====>读取到数据  ====> read client send content: " + content); 
				
				out.println("Hello"   + new Date(System.currentTimeMillis()));
				//如果读到数据是NAME_1 【数据：用户信息 ,做业务操作】
				/*if (content.equalsIgnoreCase(NAME_1)) { 
				out.println("Hello " + NAME_1 + ” ” + new Date(System.currentTimeMillis())); 
				} else { 
				out.println(“Hello ” + NAME_2 + ” ” + new Date(System.currentTimeMillis())); 
				} */
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			//先关闭socket的输入流 
			if (in != null) { 
				try { 
					in.close(); 
					in = null; 
				} catch (IOException e) { 
					e.printStackTrace(); 
				} 
			} 
			//先关闭socket的输出流 
			if (out != null) { 
				out.close(); 
				out = null; 
			} 
			//关闭socket 
			if (socket != null) { 
				try { 
					socket.close(); 
					socket = null; 
				} catch (IOException e) { 
					e.printStackTrace(); 
				} 
			} 
		}
	}

}
