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
import com.java.letch.model.UserSetupInfo;
import com.java.letch.service.UserSetupInfoService;
import com.java.letch.tools.DateHelper;

/***
 * APP用户设置的请求类
 * @author Administrator
 * 2017-11-07
 */
@Controller
public class AppUserSetupInfoController {
	
	//APP用户设置的消息推送请求类
	@Resource(name="UserSetupInfoService")
	private UserSetupInfoService usersetupinfoService;
	
	//JSON数据转换工具
	private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").serializeNulls().create();
		
	private Map<String, Object> map = new HashMap<String, Object>();
	private AppReturnCodeInfo app=new AppReturnCodeInfo();
	
	/**1.APP用户设置关闭或者开启的推送接口**/
	@RequestMapping(value="appsetupchange",method=RequestMethod.POST,produces="text/json;charset=UTF-8")
	@ResponseBody
	public String appUserSetupOpenOrClose(String appid,int userid,int states,int typeid) throws Exception{
		try {
			map.clear();
			app.setMap(map);
			//1.根据ID查询信息
			UserSetupInfo userset=this.usersetupinfoService.selectUserSetupByUserID(userid,typeid);
			if(userset==null){
				userset=new UserSetupInfo();
				//2.入库操作
				userset.setAddtime(DateHelper.getNewData());
				userset.setUid(userid);
				userset.setSetid(states);
				userset.setSettype(typeid);
				if(this.usersetupinfoService.insertUserSetupToDB(userset)){
					app.setCode(200);
					app.setMessage("设置成功");
				}else{
					app.setCode(300);
					app.setMessage("设置失败");
				}
			}else{
				//修改信息
				userset.setSetid(states);
				if(this.usersetupinfoService.insertUserSetupToDB(userset)){
					app.setCode(200);
					app.setMessage("设置成功");
				}else{
					app.setCode(300);
					app.setMessage("设置失败");
				}
			}
		} catch (Exception e) {
			app.setCode(300);
			app.setMessage("设置失败");
		}
		return gson.toJson(app);
	}
	
	
}
