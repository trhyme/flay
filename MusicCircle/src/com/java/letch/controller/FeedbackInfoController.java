package com.java.letch.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.java.letch.pagestrtools.FeedbackInfoPageEntity;
import com.java.letch.service.FeedbackInfoService;

/***
 * 用户信息反馈请求
 * @author Administrator
 * 2017-10-09
 */
@Controller
public class FeedbackInfoController {
	
	@Resource(name="FeedbackInfoService")
	private FeedbackInfoService feedbackinfoService;	//反馈信息业务操作类
	
	/*后台反馈内容信息*/
	@RequestMapping(value="feedbacklist",method=RequestMethod.GET)
	public ModelAndView selectFeedbackPage(ModelAndView view,HttpServletRequest request,Map<String, Object> map) throws Exception{
		try {
			//当前页数
			String thepagestr=request.getParameter("thepage");
			int thepage=1;
			if(thepagestr==null || thepagestr==""){
				thepage=1;
			}else{
				thepage=Integer.parseInt(thepagestr);
			}
			map.put("nowpage", thepage);	//当前页数（INT）
			map.put("pagenum", 15);			//每页条数（INT）
			map.put("bgstatrus", 1);		//状态,是否删除（INT）
			//调用分页查询的方法
			map=this.feedbackinfoService.selectFeedBackPage(map);
			view.addAllObjects(map);
		} catch (Exception e) {
		}
		view.setViewName(FeedbackInfoPageEntity.FEEDBACKLIST);
		return view;
	}
	
	/**添加回复信息**/
	@RequestMapping(value="updatefeedbackrep",method=RequestMethod.POST)
	public ModelAndView updateFeedbackReplyes(ModelAndView view,HttpServletRequest request,Map<String, Object> map,
			String thepage,String replays,String feedid) throws Exception{
		request.setAttribute("thepage", thepage);	//设置当前页数
		this.feedbackinfoService.updateFeedbackReplyes(Integer.parseInt(feedid), replays);
		return selectFeedbackPage(view, request, map);
	}
	
}
