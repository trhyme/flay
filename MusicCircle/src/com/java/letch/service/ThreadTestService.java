package com.java.letch.service;

import java.util.Date;

import org.springframework.stereotype.Repository;

import com.java.letch.tools.DateHelper;

/***
 * 线程工作的业务类
 * @author Administrator
 * 2017-11-30
 */
@Repository(value="ThreadTestService")
public class ThreadTestService implements Runnable{

	//模拟订单编号
	private String orderID;
	
	//线程工作
	@Override
	public void run() {
		//模拟在数据库插入数据
		System.out.println(orderID+".="+DateHelper.getNewData()+"=-----开始处理："+new Date()+"-----");
	}


	public String getOrderID() {
		return orderID;
	}

	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}
	
	
	
}
