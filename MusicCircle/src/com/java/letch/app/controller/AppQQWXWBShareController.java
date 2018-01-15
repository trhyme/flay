package com.java.letch.app.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.java.letch.app.returnmodel.AppReturnCodeInfo;
import com.java.letch.model.view.VoicesUserView;
import com.java.letch.pagestrtools.AppQQWXWBInfoPageEntity;
import com.java.letch.service.VoicesInfoService;

/***
 * 用户QQ、微信、微博分享请求
 * @author Administrator
 * 2017-11-02
 */
@Controller 	
public class AppQQWXWBShareController {

	//动态信息业务操作类
	@Resource(name="VoicesInfoService")
	private VoicesInfoService voicesinfoservice;
	
	//JSON数据转换工具
	private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").serializeNulls().create();
	private Map<String, Object> map=new HashMap<>();
	private AppReturnCodeInfo app=new AppReturnCodeInfo();
	
	/**1.动态分享拉取分享链接的接口**/
	@RequestMapping(value="sharevoicesgeturl",method=RequestMethod.POST,produces="text/json;charset=UTF-8")
	@ResponseBody
	public String appshareVoicesGetUrlByID(String appid,String voiceid,HttpServletRequest request) throws Exception{
		map.clear();
		app.setMap(map);
		try {
			String urlHead=request.getRequestURL().toString();
			System.out.println(urlHead);
			app.setCode(200);
			app.setMessage("请求成功");
			map.put("url", urlHead+"/"+voiceid);
			app.setMap(map);
		} catch (Exception e) {
			app.setCode(300);
			app.setMessage("请求失败");
		}
		return gson.toJson(app);
	}
	
	/**2.动态分享拉取分享链接的接口**/
	@RequestMapping(value="sharevoicesgeturl/{voiceid}",method=RequestMethod.GET,produces="text/html;charset=UTF-8")
	@ResponseBody
	public ModelAndView appshareVoicesGetUrlByIDPage(@PathVariable("voiceid")String voiceid,HttpServletRequest request,ModelAndView view) throws Exception{
		String urlHead=request.getRequestURL().toString();
		System.out.println(urlHead);
		System.out.println("ID:"+voiceid);
		
		//查询出单条信息
		VoicesUserView voices=this.voicesinfoservice.selectVoicesUser(Integer.parseInt(voiceid));
		map.clear();
		map.put("voices", voices);
		view.addAllObjects(map);
		view.setViewName("/apppage/voiceshare");
		return view;
	}
	
	/***3.APP分享下载软件链接 ***/
	@RequestMapping(value="softwarepage",method=RequestMethod.GET)
	@ResponseBody
	public ModelAndView appSoftwareDownloadMethod(ModelAndView view) throws Exception{
		
		//页面设置【待写广场分享页面】
		
		view.setViewName(AppQQWXWBInfoPageEntity.appsoftdown);
		return view;
	}
	
}
