package com.java.letch.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.java.letch.pagestrtools.BroadcastInfoPageEntity;
import com.java.letch.service.BroadcastInfoService;

/***
 * 广播墙信息请求类
 * @author Administrator
 * 2017-09-27
 */
@Controller
public class BroadcastInfoController {
	
	@Resource(name="BroadcastInfoService")
	private BroadcastInfoService broadcastinfoService;			//广播墙业务处理操作类
	
	/*后台查看广播信息*/
	@RequestMapping(value="broadcastlist",method=RequestMethod.GET)
	public ModelAndView selectBroadcastPageList(ModelAndView view,HttpServletRequest request,Map<String, Object> map){
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
			map=this.broadcastinfoService.selectBroadcastPage(map);
			view.addAllObjects(map);
		} catch (Exception e) {}
		view.setViewName(BroadcastInfoPageEntity.BROADCASTLIST);
		return view;
	}
	
	/**修改广播信息的屏蔽信息**/
	@RequestMapping(value="broadcasstratus",method=RequestMethod.GET)
	public ModelAndView updateBroadcastStratus(HttpServletRequest request,ModelAndView view,String id,String stra,Map<String, Object> map) throws Exception{
		try {
			this.broadcastinfoService.updateBroadcastStratus(Integer.parseInt(id),Integer.parseInt(stra));
		} catch (Exception e) {
			return selectBroadcastPageList(view, request, map);
		}
		return selectBroadcastPageList(view, request, map);
	}
	
}
