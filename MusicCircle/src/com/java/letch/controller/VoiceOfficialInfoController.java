package com.java.letch.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.java.letch.model.VoiceOfficialInfo;
import com.java.letch.pagestrtools.VoiceOfficialInfoPageEntity;
import com.java.letch.service.VoiceOfficialInfoService;
import com.java.letch.tools.DateHelper;


/***
 * 声音录制文案的请求类
 * @author Administrator
 * 2017-10-31
 */
@Controller
public class VoiceOfficialInfoController {
	
	//声音录制的业务操作类
	@Resource(name="VoiceOfficialInfoService")
	private VoiceOfficialInfoService voiceofficialinfoService;
	
	//1.请求查看录制的文案信息（分页）
	@RequestMapping(value="voicesofferlist",method=RequestMethod.GET)
	@ResponseBody
	public ModelAndView voiceOfficialPageList(ModelAndView view,HttpServletRequest request,Map<String, Object> map) throws Exception{
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
			map.put("pagenum",15);			//每页条数（INT）
			map.put("bgstatrus", 1);		//状态,是否删除（INT）
			//调用分页查询的方法
			map=this.voiceofficialinfoService.selectVoicesOfficialPage(map);
			view.addAllObjects(map);
		} catch (Exception e) {}
		view.setViewName(VoiceOfficialInfoPageEntity.VOICESOFFICIAPAGE);
		return view;
	}
	
	//2.添加声音录制文案的页面请求
	@RequestMapping(value="voicesoffaddpage",method=RequestMethod.GET)
	@ResponseBody
	public ModelAndView voicesOfferAddPage(ModelAndView view) throws Exception{
		view.setViewName(VoiceOfficialInfoPageEntity.VOICESOFFERADDPAGE);
		return view;
	}
	
	//3.添加文案消息
	@RequestMapping(value="voicesofferadd",method=RequestMethod.POST)
	@ResponseBody
	public ModelAndView voicesOfferAddToDB(ModelAndView view,VoiceOfficialInfo vices,HttpServletRequest request,Map<String, Object> map) throws Exception{
		try {
			vices.setTimes(DateHelper.getNewData());
			//添加
			if(this.voiceofficialinfoService.insertIntoVoicesOffer(vices)){
				return voiceOfficialPageList(view, request, map);
			}else{
				return voicesOfferAddPage(view);
			}
		} catch (Exception e) {
			return voicesOfferAddPage(view);
		}
	}
	
	//4.修改文案消息的页面
	@RequestMapping(value="updatevoicesoffpage",method=RequestMethod.GET)
	@ResponseBody
	public ModelAndView updateVoicesOffPage(ModelAndView view,HttpServletRequest request,String id,Map<String, Object> map) throws Exception{
		try {
			//根据编号查询
			VoiceOfficialInfo one=this.voiceofficialinfoService.selectVoicesOffByID(Integer.parseInt(id));
			map.put("voicesoffer", one);
			view.addAllObjects(map);
		} catch (Exception e) {
			
		}
		view.setViewName(VoiceOfficialInfoPageEntity.VOICESOFFERUPDATE);
		return view;
	}
	
	//5.修改文案消息
	@RequestMapping(value="voicesofferupdate",method=RequestMethod.POST)
	@ResponseBody
	public ModelAndView voicesOfferUpdateToDB(ModelAndView view,VoiceOfficialInfo vices,HttpServletRequest request,Map<String, Object> map) throws Exception{
		try {
			vices.setTimes(DateHelper.getNewData());
			//添加
			if(this.voiceofficialinfoService.insertIntoVoicesOffer(vices)){
				return voiceOfficialPageList(view, request, map);
			}else{
				return voicesOfferAddPage(view);
			}
		} catch (Exception e) {
			return voicesOfferAddPage(view);
		}
	}
	
	//6.删除文案信息
	@RequestMapping(value="voicesoffupdatestruts",method=RequestMethod.GET)
	@ResponseBody
	public ModelAndView voicesOfferUpdateStatrus(String id,String struts,ModelAndView view,HttpServletRequest request,Map<String, Object> map) throws Exception{
		//修改的方法
		this.voiceofficialinfoService.updateStatusByID(Integer.parseInt(id), Integer.parseInt(struts));
		
		return voiceOfficialPageList(view, request, map);
	}
	
	
}
