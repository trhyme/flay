package com.java.letch.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.java.letch.model.MenuInfo;
import com.java.letch.pagestrtools.MenuInfoPageEntity;
import com.java.letch.service.MenuInfoService;
import com.java.letch.tools.UuidGenerate;

/***
 * 菜单请求类
 * @author Administrator
 * 2017-09-14
 */
@Controller
public class MenuInfoController {
	
	/*菜单数据业务类*/
	@Resource(name="MenuInfoService")
	private MenuInfoService menuInfoService;
	
	/**权限列表分页信息**/
	@RequestMapping(value="menulist",method=RequestMethod.GET)
	@ResponseBody
	public ModelAndView selectMenuList(ModelAndView view,HttpServletRequest request,HttpServletResponse response,Map<String, Object> map) throws Exception{
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
			map=this.menuInfoService.selectListByPage(map);
			view.addAllObjects(map);
			//map.put("datakey", DataHelper.getSimpDateMillis());
		} catch (Exception e) {
		}
		view.setViewName(MenuInfoPageEntity.MENULISTes);
		return view;
	}
	
	/**添加菜单信息的方法**/
	@RequestMapping(value="menuaddonepage",method=RequestMethod.GET)
	@ResponseBody
	public ModelAndView menuAddPage(ModelAndView view,Map<String, Object> map) throws Exception{
		map.put("printmenu",this.menuInfoService.selectMenuAllList());
		view.addAllObjects(map);
		view.setViewName(MenuInfoPageEntity.MENUADDPAGE);
		return view;
	}
	
	/**正式添加菜单信息**/
	@RequestMapping(value="menuaddgodb",method=RequestMethod.POST)
	@ResponseBody
	public ModelAndView addMenuToDB(ModelAndView view,HttpServletRequest request,HttpServletResponse response,Map<String, Object> map,
			MenuInfo menu,String title,String url,String pid,String remark) throws Exception{
		menu.setTitle(title);
		menu.setIds(UuidGenerate.getUUID());
		menu.setPid(pid);
		menu.setUrl(url);
		menu.setStats(1);
		if(this.menuInfoService.addMenutoDB(menu)){
			return selectMenuList(view, request, response, map);
		}else{
			return menuAddPage(view, map);
		}
	}
	
	/**修改菜单栏的页面**/
	@RequestMapping(value="menuupdatepage",method=RequestMethod.GET)
	@ResponseBody
	public ModelAndView gotoMenuUpdatePage(String id,ModelAndView view,HttpServletRequest request,HttpServletResponse response,Map<String, Object> map) throws Exception{
		//根据编号查询单条信息
		MenuInfo menu=this.menuInfoService.selectMenuById(id);
		if (menu==null) {
			return selectMenuList(view, request, response, map);
		}else{
			map.put("onemenu", menu);
		}
		//加载菜单信息
		map.put("printmenu",this.menuInfoService.selectMenuAllList());
		view.addAllObjects(map);
		view.setViewName(MenuInfoPageEntity.MENUUPDATEPAGE);
		
		return view;
	}
	
	/**修改的方法**/
	@RequestMapping(value="menuupdategodb",method=RequestMethod.POST)
	@ResponseBody
	public ModelAndView doMenuUpdate(ModelAndView view,HttpServletRequest request,HttpServletResponse response,Map<String, Object> map,
			MenuInfo menu,String title,String url,String pid,String remark,String id,String ids) throws Exception{
		menu.setTitle(title);
		menu.setIds(UuidGenerate.getUUID());
		menu.setPid(pid);
		menu.setUrl(url);
		menu.setId(Integer.parseInt(id));
		menu.setIds(ids);
		menu.setStats(1);
		if(this.menuInfoService.updateMenuByMenu(menu)){
			return selectMenuList(view, request, response, map);
		}else{
			return gotoMenuUpdatePage(id, view, request, response, map);
		}
	}
	
	/**根据用户编号和菜单编号删除关联信息**/
	@RequestMapping(value="menuuserdeldb",method=RequestMethod.POST)
	@ResponseBody
	public String deleteMenuUserToDB(HttpServletRequest request,HttpServletResponse response,String muid,String userid) throws Exception{
		if(this.menuInfoService.deleteMenuAndUser(muid, userid)){
			return "1";
		}else{
			return "0";
		}
	}
	
}
