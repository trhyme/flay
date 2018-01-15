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
import com.java.letch.app.jpush.JpushClientUtil;
import com.java.letch.app.returnmodel.AppReturnCodeInfo;
import com.java.letch.model.CommentGetupInfo;
import com.java.letch.service.CommentGetupInfoService;
import com.java.letch.tools.DateHelper;

import cn.jpush.api.push.PushResult;

/***
 * APP评论点赞数据操作接口类
 * @author Administrator
 * 2017-11-17
 */
@Controller
public class AppCommentGetupInfoController {
	
	//点赞评论数据操作类
	@Resource(name="CommentGetupInfoService")
	private CommentGetupInfoService commentgetupinfoService;
	
	private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").serializeNulls().create();
	private Map<String, Object> map=new HashMap<>();
	private AppReturnCodeInfo app=new AppReturnCodeInfo();
	
	/****1.用户的点赞操作接口****/
	@RequestMapping(value="appgetupcomment",method=RequestMethod.POST,produces="text/json;charset=UTF-8")
	@ResponseBody
	public String appInsertCommentGetUp(String appid,int userid,int commentid) throws Exception{
		map.clear();
		try {
			CommentGetupInfo comup=new CommentGetupInfo();
			comup.setUserid(userid);
			comup.setCommid(commentid);
			comup.setAddtime(DateHelper.getNewData());
			int num=this.commentgetupinfoService.insertIntoCommentGetup(comup);
			app.setCode(200);
			app.setMessage("OK");
			map.put("count", num);
			app.setMap(map);
			
		} catch (Exception e) {
			app.setCode(300);
			app.setMessage("NO");
			map.put("count", 0);
			app.setMap(map);
		}
		return gson.toJson(app);
	}
	
}
