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
import com.java.letch.model.VoiceOfficialInfo;
import com.java.letch.service.VoiceOfficialInfoService;

/***
 * APP调用声音录制的接口请求
 * @author Administrator
 * 2017-11-01
 */
@Controller
public class AppVoiceOfficialInfoController {

	//声音录制的业务操作类
	@Resource(name="VoiceOfficialInfoService")
	private VoiceOfficialInfoService voiceofficialinfoService;
	
	//JSON数据转换工具
	private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").serializeNulls().create();
	private Map<String, Object> map=new HashMap<>();
	private AppReturnCodeInfo app=new AppReturnCodeInfo();
			
	
	/***1.请求单条文案接口***/
	@RequestMapping(value="voicesofferone",method=RequestMethod.GET,produces="text/html;charset=UTF-8")
	@ResponseBody
	public String voicesOfferGetOne(int id) throws Exception{
		map.clear();
		app.setMap(map);
		try {
			List<VoiceOfficialInfo> list=this.voiceofficialinfoService.selectVoicesByNum(1, id);
			if(list!=null){
				map.put("voiceoffer",list.get(0));
			}else{
				map.put("voiceoffer",null);
			}
			app.setCode(200);
			app.setMessage("请求成功");
		} catch (Exception e) {
			app.setCode(300);
			app.setMessage("请求失败");
		}
		return gson.toJson(app);
	} 
	
}	
