package com.java.letch.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.java.letch.pagestrtools.VoicesInfoPageEntity;
import com.java.letch.service.VoicesInfoService;

/***
 * 音频、视频、动态信息请求类CONtroller
 * @author Administrator
 * 2017-09-20
 */
@Controller
public class VoicesInfoController {

	@Resource(name="VoicesInfoService")
	private VoicesInfoService voicesInfoService;	//视音频业务操作处理类
	
	/*后台分页查询信息*/
	@RequestMapping(value="voicelist",method=RequestMethod.GET)
	public ModelAndView selectVoicesPageList(ModelAndView view,HttpServletRequest request,Map<String, Object> map) throws Exception{
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
			map=this.voicesInfoService.selectVoicesPageList(map);
			view.addAllObjects(map);
		} catch (Exception e) {}
		
		view.setViewName(VoicesInfoPageEntity.USERLISTPAGE);
		return view;
	}
	
	/*2.修改后台信息*/
	@RequestMapping(value="updatevoiceswork",method=RequestMethod.GET)
	public ModelAndView updateVoicesOneWork(ModelAndView view,HttpServletRequest request,Map<String, Object> map,int voicesid) throws Exception{
		this.voicesInfoService.updateVoicesWork(voicesid, 0);
		return selectVoicesPageList(view, request, map);
	}
	
}
