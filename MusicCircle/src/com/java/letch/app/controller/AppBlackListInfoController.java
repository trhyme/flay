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
import com.java.letch.model.BlackListInfo;
import com.java.letch.service.BlackListInfoService;
import com.java.letch.tools.DateHelper;

import oracle.net.aso.a;

/***
 * APP黑名单接口操作
 * @author Administrator
 * 2017-11-10
 */
@Controller
public class AppBlackListInfoController {
	
	//黑名单数据操作类
	@Resource(name="BlackListInfoService")
	private BlackListInfoService blacklistinfoService;
	
	private Map<String, Object> map=new HashMap<>();	
	private AppReturnCodeInfo app=new AppReturnCodeInfo();
	//JSON数据转换工具
	private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").serializeNulls().create();
	
	/***1.添加拉黑名单信息***/
	@RequestMapping(value="appblackuserinsert",method=RequestMethod.POST,produces="text/json;charset=UTF-8")
	@ResponseBody
	public String appInsertBlackInfo(String appid,int userid,int twouserid) throws Exception{
		BlackListInfo black=new BlackListInfo();
		black.setMainuser(userid);
		black.setSecondid(twouserid);
		black.setAddtimes(DateHelper.getNewData());
		map.clear();
		if(this.blacklistinfoService.insertBlackListIntoDB(black)){
			app.setCode(200);
			app.setMessage("加入黑名单成功");
		}else{
			app.setCode(300);
			app.setMessage("加入黑名单失败");
		}
		app.setMap(map);
		return gson.toJson(app);
	}
	
	/***2.分页查询黑名单数据，根据ID***/
	@RequestMapping(value="appblackpagelist",method=RequestMethod.POST,produces="text/json;charset=UTF-8")
	@ResponseBody
	public String blockListPage(String appid,int userid,int nowpage) throws Exception{
		map.clear();
		try {
			if(nowpage<=0){
				nowpage=1;
			}
			map.put("nowpage", nowpage);	//当前页数（INT）
			map.put("pagenum", 20);			//每页条数（INT）
			//调用分页查询的方法
			map=this.blacklistinfoService.seelctBlockListPage(map, userid);
			app.setCode(200);
			app.setMessage("数据请求成功");
			app.setMap(map);
		} catch (Exception e) {
			app.setCode(300);
			app.setMessage("数据请求失败");
		}
		return gson.toJson(app);
	}
	
	/****3.移除黑名单信息****/
	@RequestMapping(value="appblackuserremove",method=RequestMethod.POST,produces="text/json;charset=UTF-8")
	@ResponseBody
	public String blockUserRemove(String appid,int userid,int blockuserid) throws Exception{
		map.clear();
		app.setMap(map);
		try {
			if(this.blacklistinfoService.userBlockRemove(userid, blockuserid)){
				app.setCode(200);
				app.setMessage("解除成功");
			}else{
				app.setCode(300);
				app.setMessage("解除失败");
			}
		} catch (Exception e) {
			app.setCode(300);
			app.setMessage("解除失败");
		}
		return gson.toJson(app);
	}
	
}
