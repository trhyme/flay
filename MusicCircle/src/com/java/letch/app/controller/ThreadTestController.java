package com.java.letch.app.controller;


import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.java.letch.service.ThreadTestTwoManger;



/***
 * 线程测试请求类
 * @author Administrator
 * 2017-11-30
 */
@Controller
public class ThreadTestController {
	
	//线程任务类
	@Resource(name="ThreadTestTwoManger")
	private ThreadTestTwoManger threadtestTwoManger;
	
	//线程测试请求
	@RequestMapping(value="threaddemo",method=RequestMethod.GET)
	@ResponseBody
	public String threadPoolTest() throws Exception{
		for (int i = 0; i < 500; i++) {
            //模拟并发500条记录
			this.threadtestTwoManger.processOrders(i+"");
        }

		return "OK";
	}
	
}
