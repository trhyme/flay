package com.java.letch.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.java.letch.model.SystemNewsInfo;
import com.java.letch.pagestrtools.SystemNewsInfoPageEntity;
import com.java.letch.service.SystemNewsInfoService;
import com.java.letch.tools.DateHelper;

/***
 * 系统消息的Controller
 * @author Administrator
 * 2017-11-21
 */
@Controller
public class SystemNewsInfoController {
	
	//系统消息类
	@Resource(name="SystemNewsInfoService")
	private SystemNewsInfoService systemnewsinfoService;
	
	/***1.网页查询***/
	@RequestMapping(value="systempagelist",method=RequestMethod.GET)
	@ResponseBody
	public ModelAndView selectSystemNewsInfoPage(ModelAndView view,Map<String, Object> map,HttpServletRequest request) throws Exception{
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
			map.put("pagenum", 10);			//每页条数（INT）
			map.put("bgstatrus", 1);		//状态,是否删除（INT）
			//调用分页查询的方法
			map=this.systemnewsinfoService.selectSystemPageList(map);
			view.addAllObjects(map);
		} catch (Exception e) {
			// TODO: handle exception
		}
		view.setViewName(SystemNewsInfoPageEntity.SYSTEMLISTPAGE);
		return view;
	}
	
	/***2.发送系统消息的页面跳转***/
	@RequestMapping(value="systeminsertpage",method=RequestMethod.GET)
	@ResponseBody
	public ModelAndView insertSystemNewInfoPage(ModelAndView view) throws Exception{
		view.setViewName(SystemNewsInfoPageEntity.SYSTEMNEWSINSERTPAGE);
		return view;
	}
	
	/****3.添加系统消息入库的方法****/
	@RequestMapping(value="insertintosystemnews",method=RequestMethod.POST)
	@ResponseBody
	public ModelAndView insertIntoSystemNewsIntoToDB(ModelAndView view,HttpServletRequest request,Map<String, Object> map,
			String titles,String picName,String htmlurl,String ordernum,String remarks,SystemNewsInfo sys) throws Exception{
		sys.setTitle(titles);
		sys.setInfos(remarks);
		sys.setImgurl(picName);
		sys.setHtmlurl(htmlurl);
		sys.setTypeid(Integer.parseInt(ordernum));
		sys.setAddtime(DateHelper.getNewData());
		sys.setStratus(1);
		if(this.systemnewsinfoService.insertIntoSystemOneToDB(sys)){
			return selectSystemNewsInfoPage(view, map, request);
		}else{
			return insertSystemNewInfoPage(view);
		}
	}
	
	/****4.修改系统消息的页面请求****/
	@RequestMapping(value="updatetosystempage",method=RequestMethod.GET)
	@ResponseBody
	public ModelAndView updateSystemNewPage(ModelAndView view,int id,Map<String, Object> map) throws Exception{
		//查询出单条信息
		SystemNewsInfo sys=this.systemnewsinfoService.selectSystemByOneID(id);
		map.put("systeminfo", sys);
		view.setViewName(SystemNewsInfoPageEntity.SYSTEMUPDATEPAGE);
		return view;
	}
	
	/***5.修改的方法***/
	@RequestMapping(value="updateintosystemnews",method=RequestMethod.POST)
	@ResponseBody
	public ModelAndView updateIntoSystemNewsInfoDB(ModelAndView view,HttpServletRequest request,Map<String, Object> map,
			String titles,String picName,String htmlurl,String ordernum,String remarks,SystemNewsInfo sys,String ids) throws Exception{
		sys.setTitle(titles);
		sys.setInfos(remarks);
		sys.setImgurl(picName);
		sys.setHtmlurl(htmlurl);
		sys.setTypeid(Integer.parseInt(ordernum));
		sys.setAddtime(DateHelper.getNewData());
		sys.setStratus(1);
		sys.setId(Integer.parseInt(ids));
		if(this.systemnewsinfoService.insertIntoSystemOneToDB(sys)){
			return selectSystemNewsInfoPage(view, map, request);
		}else{
			return insertSystemNewInfoPage(view);
		}
	}
	
	/***6.删除接口***/
	@RequestMapping(value="deletesystemone",method=RequestMethod.GET)
	@ResponseBody
	public ModelAndView deleteSystemNewsInfoByID(ModelAndView view,HttpServletRequest request,Map<String, Object> map,String id,String stratus) throws Exception{
		if(this.systemnewsinfoService.deleteSystemNewsInfoByID(Integer.parseInt(id), Integer.parseInt(stratus))){
			return selectSystemNewsInfoPage(view, map, request);
		}else{
			return updateSystemNewPage(view, Integer.parseInt(id), map);
		}
	}
	
}
