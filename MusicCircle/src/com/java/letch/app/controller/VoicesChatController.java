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
import com.java.letch.model.UsersInfo;
import com.java.letch.service.UsersInfoService;

import io.rong.RongCloud;
import io.rong.configure.ConfigureEntity;
import io.rong.models.TokenResult;

/***
 * 融云聊天服务端
 * @author Administrator
 * 2017-11-20
 */
@Controller
public class VoicesChatController {
	
	//用户信息的业务处理类
	@Resource(name="UsersInfoService")
	private UsersInfoService usersinfoService;
	
	private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").serializeNulls().create();
	private Map<String, Object> map=new HashMap<>();
	private AppReturnCodeInfo app=new AppReturnCodeInfo();
	
	/**1.获取token的方法**/
	@RequestMapping(value="getcharttoken",method=RequestMethod.POST,produces="text/json;charset=UTF-8")
	@ResponseBody
	public String appChatGetTokenMethods(String appid,int userid) throws Exception{
		//获取用户信息
		UsersInfo user=this.usersinfoService.selectAppUserByID(userid);
		String userName="初遇";
		String userPhoto="http://images.leqtch.com/images/leqtchchat.png";
		if(user!=null){
			if(user.getUsername()!=null){userName=user.getUsername();}
			if(user.getHeadpic()!=null){userPhoto=user.getHeadpic();}
			
		}
		//获取token
		RongCloud rongCloud = RongCloud.getInstance(ConfigureEntity.appkey, ConfigureEntity.appSecret);
		TokenResult userGetTokenResult = rongCloud.user.getToken(userid+"", userName, userPhoto);
		String token=userGetTokenResult.toString();
		System.out.println("getToken:  " + token);
		return token;
	}
	
	/**2.根据用户编号获取用户的昵称、头像**/
	@RequestMapping(value="getchartuserinfo",method=RequestMethod.POST,produces="text/json;charset=UTF-8")
	@ResponseBody
	public String appChatGetUser(String appid,int lookuserid) throws Exception{
		map=new HashMap<>();
		app.setMap(map);
		try {
			UsersInfo user=this.usersinfoService.selectAppUserByID(lookuserid);
			if(user==null){
				map.put("userid", lookuserid);
				map.put("username", "初遇");
				map.put("userimgs", "http://images.leqtch.com/images/leqtchchat.png");
			}else{
				map.put("userid", lookuserid);
				map.put("username", user.getUsername()==null?"初遇":user.getUsername());
				map.put("userimgs", user.getHeadpic()==null?"http://images.leqtch.com/images/leqtchchat.png":user.getHeadpic());
			}
			
			app.setCode(200);
			app.setMessage("获取成功");
			app.setMap(map);
		} catch (Exception e) {
			app.setCode(300);
			app.setMessage("获取失败");
		}
		return gson.toJson(app);
	}
	
	
	
}
