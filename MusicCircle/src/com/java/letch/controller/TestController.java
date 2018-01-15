package com.java.letch.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/***
 * 测试Controller
 * @author 小王
 * 2017-09-13
 */
@Controller
public class TestController {
	public TestController(){}
	
	@RequestMapping(value="test_index",method=RequestMethod.GET)
	@ResponseBody
	public ModelAndView gotoIndex(ModelAndView view,HttpServletRequest request){
		
		view.setViewName("/test/indexjsp");
		
		return view;
	}
	
}
