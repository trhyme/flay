package com.java.letch.app.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
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
import com.java.letch.model.SignedInfo;
import com.java.letch.service.SignedInfoService;
import com.java.letch.tools.DateHelper;

/***
 * 用户签到信息的数据接口请求
 * @author Administrator
 * 2017-10-13
 */
@Controller
public class AppSignedInfoController {
	
	@Resource(name="SignedInfoService")
	private SignedInfoService signedinfoService;		//用户签到业务操作类
	
	private Map<String, Object> map=new HashMap<>();	
	private AppReturnCodeInfo app=new AppReturnCodeInfo();
	//JSON数据转换工具
	private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").serializeNulls().create();
		
	
	/***1.用户签到的接口***/
	@RequestMapping(value="appusersignin",method=RequestMethod.POST,produces="text/json;charset=UTF-8")
	@ResponseBody
	public String appUserInsertToDB(int userid) throws Exception{
		//查询今天是否签到
		map.clear();
		app.setMap(map);
		Calendar now = Calendar.getInstance();
		int year=now.get(Calendar.YEAR);
		int moun=now.get(Calendar.MONTH) + 1;
		int days=now.get(Calendar.DAY_OF_MONTH);
		SignedInfo sign=this.signedinfoService.selectSignedInfoByID(userid);
		if(sign==null){
			sign=new SignedInfo();
			//1.添加
			sign.setUserid(userid);
			sign.setAddtime(DateHelper.getNewDataYMD());
			sign.setAddyear(year);
			sign.setAddmont(moun);
			sign.setAdddays(days);
			sign.setSignnum(1);
			sign.setRemarks(1);
			if(this.signedinfoService.addSignedToDB(sign)){
				app.setCode(200);
				app.setMessage("签到成功");
			}else{
				app.setCode(300);
				app.setMessage("签到失败");
			}
		}else{
			//2.判断今天是否签到
			if(sign.getAddyear()==year && sign.getAddmont()==moun && sign.getAdddays()==days){
				sign.setRemarks(1);
				app.setCode(202);
				app.setMessage("今天已经签到");
			}else{
				//判断是否连续签到
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
		        Date date=new Date();  
		        Calendar calendar = Calendar.getInstance();  
		        calendar.setTime(date);  
		        calendar.add(Calendar.DAY_OF_MONTH, -1);  
		        date = calendar.getTime();  
		        System.out.println(sdf.format(date));  
		        //时间判断
		      //3.今日没有签到
				
		        if((sdf.format(date)).equals(sign.getAddtime())){
		        	sign.setAddtime(DateHelper.getNewDataYMD());
					sign.setAddyear(year);
					sign.setAddmont(moun);
					sign.setAdddays(days);
					sign.setSignnum(sign.getSignnum()+1);
					sign.setRemarks(1);
					//修改今日签到
					app.setCode(203);
					app.setMessage("今天签到成功");
		        }else{
		        	sign.setAddtime(DateHelper.getNewDataYMD());
					sign.setAddyear(year);
					sign.setAddmont(moun);
					sign.setAdddays(days);
					sign.setSignnum(1);
					sign.setRemarks(1);
					//修改今日签到
					app.setCode(204);
					app.setMessage("未连续签到，签到重新计算");
		        }
			}
			
		}
		map.put("signedinfo", sign);
		app.setMap(map);
		return gson.toJson(app);
	}
	
	/***2.获取用户签到信息接口***/
	@RequestMapping(value="appusergotosign",method=RequestMethod.POST,produces="text/html;charset=UTF-8")
	@ResponseBody
	public String selectUserInsertToDB(int userid) throws Exception{
		//查询今天是否签到
		map.clear();
		app.setMap(map);
		Calendar now = Calendar.getInstance();
		int year=now.get(Calendar.YEAR);
		int moun=now.get(Calendar.MONTH) + 1;
		int days=now.get(Calendar.DAY_OF_MONTH);
		SignedInfo sign=this.signedinfoService.selectSignedInfoByID(userid);
		if(sign==null){
			app.setCode(200);
			app.setMessage("还未签到");
		}else{
			//2.判断今天是否签到
			if(sign.getAddyear()==year && sign.getAddmont()==moun && sign.getAdddays()==days){
				sign.setRemarks(1);
				app.setCode(202);
				app.setMessage("今天已经签到");
				map.put("signedinfo", sign);
			}else{
				app.setCode(300);
				app.setMessage("今天还未签到");
				map.put("signedinfo", sign);
			}
		}
		return gson.toJson(app);
	}
	
}
