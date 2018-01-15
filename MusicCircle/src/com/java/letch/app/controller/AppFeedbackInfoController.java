package com.java.letch.app.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.java.letch.app.returnmodel.AppReturnCodeInfo;
import com.java.letch.model.FeedbackInfo;
import com.java.letch.model.UsersInfo;
import com.java.letch.service.FeedbackInfoService;
import com.java.letch.service.UsersInfoService;
import com.java.letch.tools.DateHelper;

/***
 * 用户反馈信息APP请求类
 * @author Administrator
 * 2017-10-18
 */
@Controller
public class AppFeedbackInfoController {
	
	@Resource(name="FeedbackInfoService")
	private FeedbackInfoService feedbackinfoService;		//反馈信息的业务处理类
	
	@Resource(name="UsersInfoService")
	private UsersInfoService userinfoService;				//APP数据操作类信息
	
	private Map<String, Object> map = new HashMap<String, Object>();
	private AppReturnCodeInfo app=new AppReturnCodeInfo();
	
	//JSON数据转换工具
	private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").serializeNulls().create();
		
	/***1.添加反馈信息***/
	@RequestMapping(value="userfeedback",method=RequestMethod.POST,produces="text/json;charset=UTF-8")
	@ResponseBody
	public String insertFeedbackOne(String appid,String content) throws Exception{
		//查询出资料信息
		map.clear();
		UsersInfo user=this.userinfoService.selectAppUserByAPPid(appid);
		if(user!=null){
			FeedbackInfo feed=new FeedbackInfo();
			feed.setUid(user.getId());
			feed.setUuid(user.getNum());
			feed.setUphone(user.getPhone());
			feed.setUnames(user.getUsername());
			feed.setConten(content);
			feed.setAddtime(DateHelper.getNewData());
			feed.setStratus(0);
			if(this.feedbackinfoService.insertFeedbackOne(feed)){
				app.setCode(200);
				app.setMessage("反馈信息提交成功");
				app.setMap(map);
			}else{
				app.setCode(300);
				app.setMessage("反馈信息提交失败");
				app.setMap(map);
			}
		}else{
			app.setCode(300);
			app.setMessage("反馈信息提交失败");
			app.setMap(map);
		}
		return gson.toJson(app);
	}
	
}
