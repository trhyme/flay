package com.java.letch.app.listener;

import java.io.IOException;

import javax.servlet.ServletContext;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

import com.java.letch.app.socket.FakeNameSocketServer;

/***
 * 程序启动的监听器
 * @author Administrator
 * 2017-12-13
 */
@Component
public class StartupListener implements ApplicationContextAware, ServletContextAware,
	InitializingBean, ApplicationListener<ContextRefreshedEvent>{

	//Log日志
	//protected Logger logger = LogManager.getLogger(StartupListener.class);
	
	@Override
	public void setApplicationContext(ApplicationContext arg0) throws BeansException {
		//System.out.println("1 =>");
		
	}
	
	@Override
	public void setServletContext(ServletContext arg0) {
		//System.out.println("2 => StartupListener.setServletContext");
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		//System.out.println("3 => StartupListener.afterPropertiesSet");
	}
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent evt) {
		//System.out.println("4.1 => StartupListener.onApplicationEvent");
		try {
			/*FakeNameSocketServer.startSocketService();
			System.out.println("=====> Socket服务启动成功");*/
		} catch (Exception e) {
			System.out.println("=====> Socket服务启动失败");
			e.printStackTrace();
		}
		
        if (evt.getApplicationContext().getParent() == null) {
        	//System.out.println("4.2 => StartupListener.onApplicationEvent");
        }
	}

	
	public static void main(String[] args) {
		try {
			FakeNameSocketServer.startSocketService();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
