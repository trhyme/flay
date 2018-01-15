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
import com.java.letch.model.ReportInfo;
import com.java.letch.model.UsersInfo;
import com.java.letch.model.view.VoicesUserView;
import com.java.letch.service.ReportInfoService;
import com.java.letch.service.UsersInfoService;
import com.java.letch.service.VoicesInfoService;
import com.java.letch.tools.DateHelper;

/***
 * APP用户的举报信息请求类
 * @author Administrator
 * 2017-11-16
 */
@Controller
public class AppReportInfoController {
	
	//举报信息的业务操作类
	@Resource(name="ReportInfoService")
	private ReportInfoService reportinfoService;
	
	//用户信息的业务处理类
	@Resource(name="UsersInfoService")
	private UsersInfoService usersinfoService;
	
	//动态信息业务操作类
	@Resource(name="VoicesInfoService")
	private VoicesInfoService voicesinfoservice;
	
	private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").serializeNulls().create();
	private Map<String, Object> map=new HashMap<>();
	private AppReturnCodeInfo app=new AppReturnCodeInfo();
	
	
	/****1.添加举报信息****/
	@RequestMapping(value="userreportother",method=RequestMethod.POST,produces="text/json;charset=UTF-8")
	@ResponseBody
	public String appInsertOneReport(String appid,int userid,int voicesid,String reptypes) throws Exception{
		map.clear();
		try {
			//1.查询动态
			VoicesUserView voice=this.voicesinfoservice.selectVoicesUser(voicesid);
			//2.查询举报者信息
			UsersInfo user=this.usersinfoService.selectAppUserByID(userid);
			//3.查询被举报的用户信息
			UsersInfo buser=this.usersinfoService.selectAppUserByID(voice.getUsers_id());
			
			//4.封装数据入库
			ReportInfo rep=new ReportInfo();
			rep.setUid(userid);
			rep.setUuid(user.getNum());
			rep.setUphone(user.getPhone());
			rep.setUnames(user.getUsername());
			rep.setBeuid(buser.getId());
			rep.setBeuuid(buser.getNum());
			rep.setBeuphone(buser.getPhone());
			rep.setBeunames(buser.getUsername());
			rep.setReptypes(reptypes);		//举报类型
			rep.setRepconte(reptypes); 		//举报内容
			rep.setAddtimes(DateHelper.getNewData());
			rep.setStratues(0);
			if(this.reportinfoService.insertReportInfo(rep)){
				app.setCode(200);
				app.setMessage("举报成功");
			}else{
				app.setCode(300);
				app.setMessage("举报失败");
			}
		} catch (Exception e) {
			
		}
		return gson.toJson(app);
	}
	
	
}
