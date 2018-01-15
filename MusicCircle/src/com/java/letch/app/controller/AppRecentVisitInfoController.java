package com.java.letch.app.controller;

import java.util.HashMap;
import java.util.List;
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
import com.java.letch.model.view.RecentVisitView;
import com.java.letch.service.MemberInfoService;
import com.java.letch.service.RecentVisitInfoService;
import com.java.letch.tools.DateHelper;

/***
 * 用户的来访纪录的APP请求信息类
 * @author Administrator
 * 2017-11-14
 */
@Controller
public class AppRecentVisitInfoController {
	
	//来访信息数据业务操作类
	@Resource(name="RecentVisitInfoService")
	private RecentVisitInfoService recentvisitinfoService;
	
	//会员业务操作业务处理类
	@Resource(name="MemberInfoService")
	private MemberInfoService memberinfoService;
	
	//JSON数据转换工具
	private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").serializeNulls().create();
	private Map<String, Object> map=new HashMap<>();
	private AppReturnCodeInfo app=new AppReturnCodeInfo();
		
	
	/***1.用户查看自己的来访人信息***/
	@RequestMapping(value="apprecenvisitlist",method=RequestMethod.POST,produces="text/json;charset=UTF-8")
	@ResponseBody
	public String appSelectRecentVisitPage(String appid,int userid,int nowpage) throws Exception{
		map.clear();
		try {
			//1.判断是否会员
			//int num=this.memberinfoService.checkMemberByUserID(userid);
			
			
			MemberInfo member=this.memberinfoService.selectMemberOneByID(userid);
			if(member!=null){
				//判断会员是否过期
				//计算会员天数
  				//1.会员开始时间--->到当前时间的天数
  				int num1=DateHelper.getDaysBetween(member.getBegintime(), DateHelper.getNewData());
  				//2.会员开始时间--->会员到期时间
  				int num2=DateHelper.getDaysBetween(member.getBegintime(), member.getEndestime());
  				if(num1<=num2){   		//1.会员还未到期
  					if(nowpage<=0){
  						nowpage=1;
  					}
  					member.setStrutus(1);
  					member.setDaysnum((num2-num1)+"");
  				}else{
  					member.setStrutus(0);
  					member.setDaysnum("0");
  					/*List<RecentVisitView> list=this.recentvisitinfoService.selectOneRecentVisitView(userid);
  					map.put("nowpage",1);	//当前页数
  					map.put("counts", 1);	//总条数
  					map.put("pagecount", 1);		//每页条数
  					map.put("page",1);	//总页数
  					map.put("checkmember", 0);
  					map.put("recentvisitlist",list);
  					app.setCode(200);
  					app.setMessage("数据请求成功");*/
  				}
  				map.put("nowpage", nowpage);	//当前页数（INT）
				map.put("pagenum", 20);			//每页条数（INT）
				//调用分页查询的方法
				map=this.recentvisitinfoService.selectRecentVisitView(map, userid);
				map.put("checkmember", member.getId());
				app.setCode(200);
				app.setMessage("数据请求成功");
				app.setMap(map);
			}else{
				/*	List<RecentVisitView> list=this.recentvisitinfoService.selectOneRecentVisitView(userid);
					map.put("nowpage",1);	//当前页数
					map.put("counts", 1);	//总条数
					map.put("pagecount", 1);		//每页条数
					map.put("page",1);	//总页数
					map.put("checkmember", 0);
					map.put("recentvisitlist",list);
					app.setCode(200);
					app.setMessage("数据请求成功");*/
				map.put("nowpage", nowpage);	//当前页数（INT）
				map.put("pagenum", 20);			//每页条数（INT）
				//调用分页查询的方法
				map=this.recentvisitinfoService.selectRecentVisitView(map, userid);
				map.put("checkmember", member.getId());
				app.setCode(200);
				app.setMessage("数据请求成功");
				app.setMap(map);
			}
		} catch (Exception e) {
			app.setCode(300);
			app.setMessage("数据请求失败");
		}
		
		
		app.setMap(map);
		return gson.toJson(app);
	}
	
	
}
