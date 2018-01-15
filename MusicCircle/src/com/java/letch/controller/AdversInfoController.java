package com.java.letch.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.java.letch.model.AdversInfo;
import com.java.letch.pagestrtools.AdversInfoPageEntity;
import com.java.letch.pagestrtools.UsersInfoPageEntity;
import com.java.letch.service.AdversInfoService;

/***
 * 广告弹窗信息的
 * @author Administrator
 * 2017-09-22
 */
@Controller
public class AdversInfoController {
	
	@Resource(name="AdversInfoService")
	private AdversInfoService adversInfoService;
	
	/**弹窗广告管理信息**/
	@RequestMapping(value="adverslist",method=RequestMethod.GET)
	public ModelAndView selectPageList(ModelAndView view,HttpServletRequest request,Map<String, Object> map) throws Exception{
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
			map=this.adversInfoService.selectPageList(map);
			view.addAllObjects(map);
		} catch (Exception e) {}
		view.setViewName(AdversInfoPageEntity.ADVERSLISTPAGE);
		return view;
	}
	
	/*跳转添加的页面*/
	@RequestMapping(value="addadverspage",method=RequestMethod.GET)
	public ModelAndView insertAdversPage(ModelAndView view) throws Exception{
		view.setViewName(AdversInfoPageEntity.ADVERSADDPAGE);
		return view;
	}
	
	/**弹窗广告信息添加到数据库**/
	@RequestMapping(value="addadverstodb",method=RequestMethod.POST)
	public ModelAndView insertAdversToDB(ModelAndView view,HttpServletRequest request,Map<String, Object> map,AdversInfo adver,
			String advtitles,String picName,String h5url,String strausnum,String begintime,String endsstime) throws Exception{
		adver.setName(advtitles);
		adver.setPic(picName);
		adver.setUrl(h5url);
		adver.setWork(1);
		adver.setBegintime(begintime);
		adver.setEndsstime(endsstime);
		adver.setStraus(Integer.parseInt(strausnum));
		//调用添加的方法
		if(this.adversInfoService.insertIntoAdversDB(adver)){
			return selectPageList(view, request, map);
		}else{
			return insertAdversPage(view);
		}
	}
	
	/*修改弹窗广告的页面跳转*/
	@RequestMapping(value="adversupdatepage",method=RequestMethod.GET)
	public ModelAndView updateAdversAgoPage(ModelAndView view,HttpServletRequest request,Map<String, Object> map,String ids) throws Exception{
		if(ids!=null && !"".equals(ids)){
			//根据编号查询单条信息
			AdversInfo adv=this.adversInfoService.selectByIds(Integer.parseInt(ids));
			map.put("advone", adv);
			view.addAllObjects(map);
			view.setViewName(AdversInfoPageEntity.ADVERSUPDATE);
		}else{
			return selectPageList(view, request, map);
		}
		return view;
	}
	
	/**弹窗广告信息修改到数据库**/
	@RequestMapping(value="updateadverstodb",method=RequestMethod.POST)
	public ModelAndView updateAdversToDB(ModelAndView view,HttpServletRequest request,Map<String, Object> map,
			String advtitles,String picName,String h5url,String strausnum,String begintime,String endsstime,String vdaid) throws Exception{
		//根据编号查询单条信息
		AdversInfo adver=this.adversInfoService.selectByIds(Integer.parseInt(vdaid));
		adver.setName(advtitles);
		adver.setPic(picName);
		adver.setUrl(h5url);
		adver.setWork(1);
		adver.setBegintime(begintime);
		adver.setEndsstime(endsstime);
		adver.setStraus(Integer.parseInt(strausnum));
		//调用添加的方法
		if(this.adversInfoService.updateAdvers(adver)){
			return selectPageList(view, request, map);
		}else{
			return insertAdversPage(view);
		}
	}
	
	
}
