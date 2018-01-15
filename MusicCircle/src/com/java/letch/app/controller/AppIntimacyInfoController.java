package com.java.letch.app.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.java.letch.model.FollowsInfo;
import com.java.letch.model.IntimacyInfo;
import com.java.letch.service.FollowsInfoService;
import com.java.letch.service.IntimacyInfoService;
import com.java.letch.tools.DateHelper;

/***
 * 用户亲密度的请求操作
 * @author Administrator
 * 2017-11-23
 */
@Controller
public class AppIntimacyInfoController {
	
	@Resource(name="IntimacyInfoService")
	private IntimacyInfoService intimacyinfoService;		//亲密度数据操作类
	
	@Resource(name="FollowsInfoService")
	private FollowsInfoService followsinfoService;		//用户关注信息
	
	private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").serializeNulls().create();
	private Map<String, Object> map=new HashMap<>();
	private AppReturnCodeInfo app=new AppReturnCodeInfo();
	
	/*1.请求亲密度操作*/
	@RequestMapping(value="appchatintimacyinto",method=RequestMethod.POST,produces="text/json;charset=UTF-8")
	@ResponseBody
	public String appChatInsertIntimacy(String appid,int useridA,int useridB,int lovenum) throws Exception{
		//4.判断关注状态
		//判断我是否关注对方
		IntimacyInfo tim=new IntimacyInfo();
		map.clear();
		app.setMap(map);
		FollowsInfo follow1=this.followsinfoService.selectFollowsByTwoID(useridA,useridB);
		if(follow1!=null){
			//判断对方是否关注我
			FollowsInfo follow2=this.followsinfoService.selectFollowsByTwoID(useridB,useridA);
			if(follow2!=null){
				//1.查询数据
				tim=this.intimacyinfoService.selectIntimacyInfoOne(useridA, useridB);
				if(lovenum>20){
					lovenum=20;
			   	}
				if(tim!=null){
					SimpleDateFormat fmt=new SimpleDateFormat("yyyy-MM-dd");
					Date date = fmt.parse(tim.getAddtime());
				     if((fmt.format(date).toString()).equals(fmt.format(new Date()).toString())){//格式化为相同格式
				    	 int old=tim.getAddnumber();
				    	 if(old>=80){
				    		 
				    	 }else{
				    		/* if(80-old>=20){
					    		 old=20;
					    	 }else{
					    		 old=80-old;
					    	 }*/
					    	 tim.setAddnumber(tim.getAddnumber()+lovenum);
					    	 tim.setIntimacye(tim.getIntimacye()+lovenum);
					    	 tim.setAddtime(DateHelper.getNewDataYMD());
				    	 }
				     }else {
				    	 tim.setAddnumber(lovenum);
				    	 tim.setIntimacye(tim.getIntimacye()+lovenum);
				    	 tim.setAddtime(DateHelper.getNewDataYMD());
				     }
				}else{
					tim=new IntimacyInfo();
			    	tim.setUseroneid(useridA);
			    	tim.setUsertwoid(useridB);
			    	tim.setIntimacye(10);
			    	tim.setAddtime(DateHelper.getNewDataYMD());
			    	tim.setAddnumber(0);
				}
				//2.跟新保存数据
				if(this.intimacyinfoService.insertOrUpdateIntimacy(tim)){
					app.setCode(200);
					app.setMessage("成功");
				}else{
					app.setCode(300);
					app.setMessage("失败");
				}
			}
		}
		map.put("intimacye", tim);
		app.setMap(map);
		return gson.toJson(app);
	}
	
	
}
