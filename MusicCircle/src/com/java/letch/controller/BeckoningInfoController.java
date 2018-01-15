package com.java.letch.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.java.letch.model.BeckoningInfo;
import com.java.letch.pagestrtools.BeckoningInfoPageEntity;
import com.java.letch.service.BeckoningInfoService;

/***
 * 心动配置信息请求类
 * @author Administrator
 * 2017-09-26
 */
@Controller
public class BeckoningInfoController {
	
	@Resource(name="BeckoningInfoService")
	private BeckoningInfoService beckoninginfoService;		//心动信息业务操作类
	
	//JSON数据转换工具
	private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").serializeNulls().create();
	
	/**查看心动配置信息**/
	@RequestMapping(value="beckoninglist",method=RequestMethod.GET)
	public ModelAndView selectBeckonPageList(HttpServletRequest request,Map<String, Object> map,ModelAndView view) throws Exception{
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
			map=this.beckoninginfoService.selectBeckPageList(map);
			view.addAllObjects(map);
		} catch (Exception e) {}
		view.setViewName(BeckoningInfoPageEntity.BECKLISTPAGE);
		return view;
	}
	
	/**添加心动配置信息的页面**/
	@RequestMapping(value="beckoningaddpage",method=RequestMethod.GET)
	public ModelAndView insertBeckonPage(ModelAndView view) throws Exception{
		
		view.setViewName(BeckoningInfoPageEntity.BECKADDPAGE);
		return view;
	}
	
	/**添加到数据库**/
	@RequestMapping(value="beckoninsert",method=RequestMethod.POST)
	public ModelAndView insertBeckAddToDB(HttpServletRequest request,ModelAndView view,Map<String, Object> map,
			BeckoningInfo beck,String beginnum,String endnumes,String picName) throws Exception{
		//设置信息
		beck.setBeginnum(Integer.parseInt(beginnum));
		beck.setEndnumes(Integer.parseInt(endnumes));
		beck.setBeckurls(picName);
		//获取多选框信息
		String[] list =null;
		if(request.getParameterValues("contents")!=null){
			list=request.getParameterValues("contents");
			if(list.length>0){
				beck.setContents(gson.toJson(list));
			}
		}
		//添加信息
		if(this.beckoninginfoService.insertIntoBeckToDB(beck)){
			return selectBeckonPageList(request, map, view);
		}else{
			return insertBeckonPage(view);
		}
	}
	
	/**修改心动配置信息页面**/
	@RequestMapping(value="updatebaeckpage",method=RequestMethod.GET)
	public ModelAndView updateBeckGoPage(ModelAndView view,String id,Map<String, Object> map,HttpServletRequest request) throws Exception{
		//根据ID查询信息
		BeckoningInfo beck=this.beckoninginfoService.selectById(Integer.parseInt(id));
		if (beck!=null) {
			map.put("beck", beck);
			view.addAllObjects(map);
			view.setViewName(BeckoningInfoPageEntity.BECKUPDATEPAGE);
			return view;
		}else{
			return selectBeckonPageList(request, map, view);
		}
	}
	
	
	/**修改心动配置信息入库**/
	@RequestMapping(value="updatebecktodb",method=RequestMethod.POST)
	public ModelAndView updateBeckToDB(HttpServletRequest request,ModelAndView view,Map<String, Object> map,
			BeckoningInfo beck,String beginnum,String endnumes,String picName,String id,String contentsss) throws Exception{
		try {
			//设置信息
			beck.setBeginnum(Integer.parseInt(beginnum));
			beck.setEndnumes(Integer.parseInt(endnumes));
			beck.setBeckurls(picName);
			beck.setId(Integer.parseInt(id));
			//获取多选框信息
			String[] list =null;
			if(request.getParameterValues("contents")!=null){
				list=request.getParameterValues("contents");
				if(list.length>0){
					beck.setContents(gson.toJson(list));
				}
			}
			
			//修改信息
			if(this.beckoninginfoService.updateBeckInfoToDB(beck)){
				return selectBeckonPageList(request, map, view);
			}else{
				return updateBeckGoPage(view, id, map, request);
			}
		} catch (Exception e) {}
		return updateBeckGoPage(view, id, map, request);
	}
	
}
