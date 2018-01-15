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
import com.java.letch.model.SayHelloInfo;
import com.java.letch.service.SayHelloInfoService;
import com.java.letch.tools.DateHelper;

/***
 * APP打招呼的请求
 * @author Administrator
 * 2017-12-12
 */
@Controller
public class AppSayHelloInfoController {
	
	@Resource(name="SayHelloInfoService")
	private SayHelloInfoService sayhelloinfoService;	//数据业务操作
	
	private Map<String, Object> map=new HashMap<>();	
	private AppReturnCodeInfo app=new AppReturnCodeInfo();
	//JSON数据转换工具
	private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").serializeNulls().create();
		
	/****1.用户打招呼记录信息****/
	@RequestMapping(value="appsayhellomethod",method=RequestMethod.POST,produces="text/json;charset=UTF-8")
	@ResponseBody
	public String appuserSayHelloInfoToMethod(String appid,int userid,int sayuserid) throws Exception{
		//查询今天是否打招呼
		map.clear();
		app.setMap(map);
		Calendar now = Calendar.getInstance();
		int year=now.get(Calendar.YEAR);
		int moun=now.get(Calendar.MONTH) + 1;
		int days=now.get(Calendar.DAY_OF_MONTH);
		SayHelloInfo say=this.sayhelloinfoService.selectSayHelloByID(userid, sayuserid);
		if(say==null){
			say=new SayHelloInfo();
			say.setUserid(userid);
			say.setSayuserid(sayuserid);
			say.setAddyear(year);
			say.setAddmont(moun);
			say.setAdddays(days);
			say.setSignnum(1);
			say.setRemarks(1);
			say.setAddtime(DateHelper.getNewData());
			if(this.sayhelloinfoService.addSayHelloToDB(say)){
				app.setCode(200);
				app.setMessage("打招呼成功");
			}else{
				app.setCode(300);
				app.setMessage("打招呼失败");
			}
		}else{
			//2.判断今天是否打招呼
			if(say.getAddyear()==year && say.getAddmont()==moun && say.getAdddays()==days){
				say.setRemarks(1);
				app.setCode(202);
				app.setMessage("今天已经打过招呼");
			}else{
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
		        Date date=new Date();  
		        Calendar calendar = Calendar.getInstance();  
		        calendar.setTime(date);  
		        calendar.add(Calendar.DAY_OF_MONTH, -1);  
		        date = calendar.getTime();  
		        System.out.println(sdf.format(date));  
		        
		        if((sdf.format(date)).equals(say.getAddtime())){
		        	say.setAddtime(DateHelper.getNewDataYMD());
		        	say.setAddyear(year);
		        	say.setAddmont(moun);
		        	say.setAdddays(days);
		        	say.setSignnum(say.getSignnum()+1);
		        	say.setRemarks(1);
					//修改今日签到
					app.setCode(200);
					app.setMessage("打招呼成功");
		        }else{
		        	say.setAddtime(DateHelper.getNewDataYMD());
		        	say.setAddyear(year);
		        	say.setAddmont(moun);
		        	say.setAdddays(days);
		        	say.setRemarks(1);
					//修改今日签到
					app.setCode(200);
					app.setMessage("打招呼成功");
		        }
		        
			}
		}
		map.put("helloinfo", say);
		app.setMap(map);
		return gson.toJson(app);
	}
	
}
