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
import com.java.letch.model.FollowsInfo;
import com.java.letch.model.UsersInfo;
import com.java.letch.service.FollowsInfoService;
import com.java.letch.service.MemberInfoService;
import com.java.letch.service.UsersInfoService;
import com.java.letch.tools.DateHelper;

/***
 * APP用户的关注信息请求
 * @author Administrator
 * 2017-09-26
 */
@Controller
public class AppFollowsInfoController {
	
	@Resource(name="FollowsInfoService")
	private FollowsInfoService followsinfoService;		//用户关注信息
	
	//用户业务操作类
	@Resource(name="UsersInfoService")
	private UsersInfoService userinfoService;
	
	//会员业务操作业务处理类
	@Resource(name="MemberInfoService")
	private MemberInfoService memberinfoService;
	
	//JSON数据转换工具
	private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").serializeNulls().create();
	
	private AppReturnCodeInfo app=new AppReturnCodeInfo();
	private Map<String, Object> map=new HashMap<>();
	
	/*1.添加关注信息接口*/
	@RequestMapping(value="appuserfollows",method=RequestMethod.POST,produces="text/json;charset=UTF-8")
	@ResponseBody
	public String appuserInsertAddFollow(String appid,int userid) throws Exception{
		try {
			//1.判断用户的APPID是否正确
			map=this.userinfoService.checkAppUserByAppID(appid, map);
			if(Integer.parseInt(map.get("code").toString())==200){
				UsersInfo userA=(UsersInfo) map.get("user");    
				if(userA!=null){
					//2.根据编号查询被关注用户
					UsersInfo userB=this.userinfoService.selectAppUserByID(userid);
					if(userB!=null){
						//3.判断已经是否关注,根据两个用户的主键编号
						FollowsInfo follow=this.followsinfoService.selectFollowsByTwoID(userA.getId(),userid);
						if(follow==null){
							follow=new FollowsInfo();
							follow.setDate(DateHelper.getNewData());
							follow.setUsers_id(userA.getId());			//关注的用户ID
							follow.setFollowsid(userB.getId());			//被关注用户ID
							//设置默认心动值: 10
							follow.setBecknum(10);
							//
							//4.关注信息添加入库
							if(this.followsinfoService.insertAddFollows(follow)){
								app.setCode(200);
								app.setMessage("关注成功");
								//5.判断是否互相关注
								FollowsInfo follow2=this.followsinfoService.selectFollowsByTwoID(userB.getId(),userA.getId());
								if(follow2!=null){
									//设置互相关注信息
									this.followsinfoService.updateFollowsMutualnumByTwoID(userA.getId(), userB.getId(),1);
								}
							}else{
								app.setCode(300);
								app.setMessage("关注失败");
							}
						}else{
							//已经关注
							//则取消关注
							this.followsinfoService.deleteFansByFollows(follow,userA.getId(), userB.getId());
							//设置互相关注信息
							this.followsinfoService.updateFollowsMutualnumByTwoID(userA.getId(), userB.getId(),0);
							app.setCode(200);
							app.setMessage("取消关注");
						}
					}else{
						//被关注用户不存在
						app.setCode(301);
						app.setMessage("被关注用户ID参数错误");
					}
				}else{
					//用户APPID错误
					app.setCode(400);
					app.setMessage("参数：AppID错误");
				}
				
			}
				
		} catch (Exception e) {}
		return gson.toJson(app);
	}
	
	
	/***2.用户查看粉丝信息接口***/
	@RequestMapping(value="appusershowfans",method=RequestMethod.GET,produces="text/json;charset=UTF-8")
	@ResponseBody
	public String selectFansByUserID(String userid,int nowpage) throws Exception{
		try {
			//1.根据APP_id编号查询出用户信息
			Map<String, Object> map=new HashMap<>();
			try {
				if(nowpage<=0){
					nowpage=1;
				}
				map.put("nowpage", nowpage);	//当前页数（INT）
				map.put("pagenum", 15);			//每页条数（INT）
				//调用分页查询的方法
				map=this.userinfoService.selectAppUserFansByID(map, Integer.parseInt(userid));
				app.setCode(200);
				app.setMessage("数据请求成功");
				app.setMap(map);
			} catch (Exception e) {
				app.setCode(300);
				app.setMessage("数据请求失败");
			}
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").serializeNulls().create();
			return gson.toJson(app, AppReturnCodeInfo.class);
		} catch (Exception e) {
			app.setCode(300);
			app.setMessage("数据请求失败1-1");
		}
		return gson.toJson(app, AppReturnCodeInfo.class);
	}
	
	
	/*****3.【心动我的：1】【相互心动：2】【我的心动：3】数据接口*****/
	@RequestMapping(value="appfollowinfolist",method=RequestMethod.POST,produces="text/json;charset=UTF-8")
	@ResponseBody
	public String selectFollowMePageList(String appid,int userid,int labelid,int nowpage) throws Exception{
		try {
			map=new HashMap<>();
			app.setCode(200);
			app.setMessage("请求成功");
			if(nowpage<=0){
				nowpage=1;
			}
			//1.判断是否会员
			int num=this.memberinfoService.checkMemberByUserID(userid);
			if(num<=0){
				num=0;
			}
			map.put("checkmember", num);
			map.put("nowpage", nowpage);	//当前页数（INT）
			map.put("pagenum", 20);			//每页条数（INT）
			if(labelid==1){			//【心动我的：1】
				map=this.followsinfoService.selectFollowsMePage(map, userid);
			}else if(labelid==2){  	//【相互心动：2】
				map=this.followsinfoService.selectFollowMutualPage(map, userid);
			}else if(labelid==3){	//【我的心动：3】
				map=this.followsinfoService.selectMyFollowsPage(map, userid);
			}
			
			app.setMap(map);
		} catch (Exception e) {
			System.out.println(e);
			app.setCode(300);
			app.setMessage("数据请求失败1-1");
		}
		
		return gson.toJson(app);
	}
	
	
	
	/*********4.查看是否心动的接口*******/
	@RequestMapping(value="queryuserfollows",method=RequestMethod.POST,produces="text/json;charset=UTF-8")
	@ResponseBody
	public String selectUserFollows(String appid,int userid,int usertwoid) throws Exception{
		map.clear();
		try {
			//1.查询出是否已经心动
			List<FollowsInfo> lis=this.followsinfoService.selectFollowList(userid,usertwoid);
			if(lis!=null && lis.size()>0){
				app.setCode(200);
				app.setMessage("请求成功");
				map.put("follows", 1);
			}else{
				app.setCode(300);
				app.setMessage("请求失败");
				map.put("follows", 0);
			}
		} catch (Exception e) {
			app.setCode(300);
			app.setMessage("请求失败");
			map.put("follows", 0);
		}
		app.setMap(map);
		return gson.toJson(app);
	}
	
	
	
}
