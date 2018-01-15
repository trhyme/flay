package com.java.letch.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.bson.util.StringRangeSet;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.java.letch.model.AdminsUserInfo;
import com.java.letch.model.BannerInfo;
import com.java.letch.pagestrtools.AdminsUserPage;
import com.java.letch.pagestrtools.BannerInfoPageEntity;
import com.java.letch.service.BannerInfoService;
import com.java.letch.tools.BannerImageOSSTools;
import com.java.letch.tools.DateHelper;

/***
 * Banner图后台管理类
 * @author Administrator
 * 2017-09-19
 */
@Controller
public class BannerInfoController {
	
	@Resource(name="BannerInfoService")
	private BannerInfoService bannerInfoService;
	
	/*后台分页查询*/
	@RequestMapping(value="bannerlist",method=RequestMethod.GET)
	@ResponseBody
	public ModelAndView selectBannerByPage(ModelAndView view,HttpServletRequest request,Map<String,Object> map) throws Exception{
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
			map=this.bannerInfoService.selectBannerPage(map);
			view.addAllObjects(map);
		} catch (Exception e) {
		}
		
		view.setViewName(BannerInfoPageEntity.BANNERLIST);
		return view;
	}
	
	/**添加Banner图信息页面**/
	@RequestMapping(value="addbannerpage",method=RequestMethod.GET)
	@ResponseBody
	public ModelAndView insertBannerInfo(ModelAndView view) throws Exception{
		
		view.setViewName(BannerInfoPageEntity.BANNERADD);
		return view;
	}
	
	/**上传Banner图的方法**/
	@RequestMapping(value="bannerajaxload",method=RequestMethod.POST)
	@ResponseBody
	public String bannerImgUpload(@RequestParam(value="cmplogoname_input") MultipartFile cmplogoname_input,HttpServletRequest request,HttpServletResponse response){
		response.setContentType("text/html;charset=utf-8");   
		Map<String, Object> value = new HashMap<String, Object>();
		   value.put("success", true);
		   value.put("errorCode", 0);
		   value.put("errorMsg", "");
		   try {
			   BannerImageOSSTools ban=new BannerImageOSSTools();
		     String head = ban.updateHead(cmplogoname_input, 4);//此处是调用上传服务接口，4是需要更新的userId 测试数据。
		     value.put("data", head);
		   } catch (IOException e) {
		     e.printStackTrace();
		     value.put("success", false);
		     value.put("errorCode", 200);
		     value.put("errorMsg", "图片上传失败");
		   }
		   Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").serializeNulls().create();
		   return gson.toJson(value);
	}
	
	/***后台添加Banner图的请求***/
	@RequestMapping(value="bannerinsert",method=RequestMethod.POST,produces="text/html;charset=UTF-8")
	@ResponseBody
	public ModelAndView insertBannerToDB(ModelAndView view,HttpServletRequest request,Map<String, Object> map,
			String titles,String picName,String ordernum,String remarks,String htmlurl,BannerInfo banner) throws Exception{
		banner.setTitle(titles);
		banner.setPicurl(picName);
		banner.setOrdernum(Integer.parseInt(ordernum));
		banner.setAddtimes(DateHelper.getNewData());
		banner.setStratus(1);
		banner.setOnlines(1);
		banner.setRemark(remarks);
		if(htmlurl.equals("")){
			htmlurl="http://www.leqtch.com/";
		}
		banner.setHtmlurl(htmlurl);
		HttpSession session=request.getSession();
		AdminsUserInfo user=null;
		if(session.getAttribute(AdminsUserPage.AdminLoginSession)!=null){
			user=(AdminsUserInfo) session.getAttribute(AdminsUserPage.AdminLoginSession);
		}
		if(user!=null){
			banner.setAdmids(user.getAdmid());
		}
		//调用添加的方法
		if(this.bannerInfoService.insertBannerToDB(banner)){
			return selectBannerByPage(view, request, map);
		}else{
			map.put("meaasge", "添加失败");
			view.setViewName(BannerInfoPageEntity.BANNERADD);
			view.addAllObjects(map);
			return view;
		}
	}
	
	/**修改Banner图信息的页面**/
	@RequestMapping(value="updatebannerpage",method=RequestMethod.GET,produces="text/html;charset=UTF-8")
	@ResponseBody
	public ModelAndView updateBannerAgo(ModelAndView view,String id,HttpServletRequest request,Map<String, Object> map) throws Exception{
		//根据编号查询信息
		BannerInfo banner=this.bannerInfoService.selectBannerById(id);
		if(banner!=null){
			map.put("oneBanner", banner);
			view.addAllObjects(map);
			view.setViewName(BannerInfoPageEntity.BANNERUPDATE);
		}else{
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
				map.put("pagenum", 9);			//每页条数（INT）
				map.put("bgstatrus", 1);		//状态,是否删除（INT）
				//调用分页查询的方法
				map=this.bannerInfoService.selectBannerPage(map);
				view.addAllObjects(map);
			} catch (Exception e) {
			}
			
			view.setViewName(BannerInfoPageEntity.BANNERLIST);
			return view;
		}
		return view;
	}
	
	/**执行修改操作**/
	@RequestMapping(value="bannerupdate",method=RequestMethod.POST)
	@ResponseBody
	public ModelAndView updateBannerToDB(ModelAndView view,HttpServletRequest request,Map<String, Object> map,
			String titles,String picName,String ordernum,String remarks,String htmlurl,BannerInfo banner,String id) throws Exception{
		banner.setId(Integer.parseInt(id));
		banner.setTitle(titles);
		banner.setPicurl(picName);
		banner.setOrdernum(Integer.parseInt(ordernum));
		banner.setAddtimes(DateHelper.getNewData());
		banner.setStratus(1);
		banner.setOnlines(1);
		banner.setRemark(remarks);
		if(htmlurl.equals("")){
			htmlurl="http://www.leqtch.com/";
		}
		banner.setHtmlurl(htmlurl);
		HttpSession session=request.getSession();
		AdminsUserInfo user=null;
		if(session.getAttribute(AdminsUserPage.AdminLoginSession)!=null){
			user=(AdminsUserInfo) session.getAttribute(AdminsUserPage.AdminLoginSession);
		}
		if(user!=null){
			banner.setAdmids(user.getAdmid());
		}
		//调用添加的方法
		if(this.bannerInfoService.updateBannerByBanner(banner)){
			return selectBannerByPage(view, request, map);
		}else{
			map.put("meaasge", "添加失败");
			view.setViewName(BannerInfoPageEntity.BANNERADD);
			view.addAllObjects(map);
			return view;
		}
	}
	
	
}
