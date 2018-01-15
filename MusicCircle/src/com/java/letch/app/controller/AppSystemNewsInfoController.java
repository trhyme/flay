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
import com.java.letch.model.MemberInfo;
import com.java.letch.service.MemberInfoService;
import com.java.letch.service.SystemNewsInfoService;
import com.java.letch.tools.DateHelper;

/***
 * APP用户的系统消息操作接口
 * @author Administrator
 * 2017-11-16
 */
@Controller
public class AppSystemNewsInfoController {
	
	//系统消息数据业务类
	@Resource(name="SystemNewsInfoService")
	private SystemNewsInfoService systemnewsinfoService;
	
	//会员业务操作业务处理类
	@Resource(name="MemberInfoService")
	private MemberInfoService memberinfoService;
	
	private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").serializeNulls().create();
	private Map<String, Object> map=new HashMap<>();
	private AppReturnCodeInfo app=new AppReturnCodeInfo();
		
	/***1.用户获取信息【分页】***/
	@SuppressWarnings("finally")
	@RequestMapping(value="appusersystemlist",method=RequestMethod.POST,produces="text/json;charset=UTF-8")
	@ResponseBody
	public String appSelectSystemNewPage(String appid,int userid,int nowpage) throws Exception{
		map=new HashMap<>();
		try {
			if(nowpage<=0){
				nowpage=1;
			}
			//1.判断是否会员
			int num=0;
			
			MemberInfo member=this.memberinfoService.selectMemberOneByID(userid);
			if(member!=null){
				//判断会员是否过期
				//计算会员天数
  				//1.会员开始时间--->到当前时间的天数
  				int num1=DateHelper.getDaysBetween(member.getBegintime(), DateHelper.getNewData());
  				//2.会员开始时间--->会员到期时间
  				int num2=DateHelper.getDaysBetween(member.getBegintime(), member.getEndestime());
  				if(num1<=num2){   		//1.会员还未到期
  					num=member.getId();
  					member.setStrutus(1);
  					member.setDaysnum((num2-num1)+"");
  				}else{
  					num=0;
  					member.setDaysnum("0");
  					member.setStrutus(0);
  				}
			}else{
				num=0;
			}
			map.put("checkmember", num);
			map.put("nowpage", nowpage);	//当前页数（INT）
			map.put("pagenum", 20);			//每页条数（INT）
			map=this.systemnewsinfoService.appSelectSystemNewPage(map, userid);
			app.setCode(200);
			app.setMessage("数据请求成功");
			app.setMap(map);
		} catch (Exception e) {
			app.setCode(300);
			app.setMessage("数据请求失败1-1");
			app.setMap(map);
		}finally{
			return gson.toJson(app);
		}
		
	}
	
}
