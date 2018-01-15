package com.java.letch.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.java.letch.model.IphoneConfigure;
import com.java.letch.service.FeedbackInfoService;
import com.java.letch.service.MemberInfoService;
import com.java.letch.service.UsersInfoService;
import com.java.letch.service.VoicesInfoService;

import oracle.net.aso.n;

/***
 * 首页的请求类
 * @author Administrator
 * 2017-09-21
 */
@Controller
public class IndexController {
	
	@Resource(name="UsersInfoService")
	private UsersInfoService usersinfoService;     		//用户业务处理类
	
	@Resource(name="VoicesInfoService")
	private VoicesInfoService voicesinfoService;   		//动态声音业务操作类
	
	@Resource(name="FeedbackInfoService")
	private FeedbackInfoService feedbackinfoService;	//反馈的操作类
	
	@Resource(name="MemberInfoService")
	private MemberInfoService memberinfoService;		//会员操作类
	
	/**1.系统首页信息**/
	@RequestMapping(value="indexhome",method=RequestMethod.GET)
	public ModelAndView indexHomePage(ModelAndView view,Map<String, Object> map,HttpServletRequest request) throws Exception{
		
		//1.查询总用户数
		int usercount=this.usersinfoService.selectAllUserCounts();
		map.put("usernum", usercount);
		//2.查询总的动态数
		map.put("voicesunm", this.voicesinfoService.selectVoicesAllNum());
		//3.查询反馈信息
		map.put("feedback", this.feedbackinfoService.selectFeedbackInfoFive(5));
		//4.查询出最热的动态
		Map<String, Object> map2=new HashMap<>();
		String nowpages=request.getParameter("nowpage");
		int nowpag=1;
		if(nowpages!=null){
			nowpag=Integer.parseInt(nowpages);
		}
		map2.clear();
		/*map2.put("nowpage", nowpag);	//当前页数（INT）
		map2.put("pagenum", 10);			//每页条数（INT）
		map2.put("userid", "-1");
		map2=this.usersinfoService.appIndexHot(map2);
		map.put("voiceslist", map2.get("dynamiclist"));*/
		
		map2.put("nowpage", nowpag);	//当前页数（INT）
		map2.put("pagenum", 10);			//每页条数（INT）
		map2.put("userid", "-1");		//用户的编号
		//调用分页查询的方法
		map2=this.usersinfoService.selectAppIndexNowVoices(map2);
		map.put("voiceslist", map2.get("dynamiclist"));
		
		
		//5.查询出总的会员数量
		map.put("membercount", this.memberinfoService.selectMemberCount(1));
		
		//6.查询出配置文件
		List<IphoneConfigure> confList=this.usersinfoService.selectIphoneConfigure();
		map.put("iphoneconf",-1);
		if(confList!=null && confList.size()>0){
			map.put("iphoneconf", confList.get(0).getIphone());
		}
		view.addAllObjects(map);
		view.setViewName("/ADMINS/home");
		return view;
	}
	
	/***2.设置是否屏蔽iPhone的支付***/
	@RequestMapping(value="updateiphoneconfigure",method=RequestMethod.GET)
	public ModelAndView updateIphoneConfigure(ModelAndView view,Map<String, Object> map,HttpServletRequest request,int stratu) throws Exception{
		this.usersinfoService.updateIphoneConfigure(stratu);
		return indexHomePage(view, map, request);
	}
	
}
