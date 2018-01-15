package com.java.letch.tools;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/***获取Bean对象***/
public class ApplicationHelper implements ApplicationContextAware{

	private static ApplicationContext applicationContext;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		ApplicationHelper.applicationContext=applicationContext;
	}

	public static ApplicationContext getApplicationContext(){
		return applicationContext;
	}
	
	//获取
	public static Object getBean(String beanName){
		System.out.println(applicationContext);
		return applicationContext.getBean(beanName);
	}
	
	
}
