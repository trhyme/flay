package com.java.letch.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.java.letch.model.GiftsInfo;
import com.java.letch.pagestrtools.GiftsInfoPageEntity;
import com.java.letch.service.GiftsInfoService;

/***
 * 礼物信息请求类Controller
 * @author Administrator
 * 2017-09-20
 */
@Controller
public class GiftsInfoController {
	
	@Resource(name="GiftsInfoService")
	private GiftsInfoService giftsInfoService;		//礼物业务处理操作类

	/*后台查看礼物信息*/
	@RequestMapping(value="giftpagelist",method=RequestMethod.GET)
	public ModelAndView selectGiftPageList(ModelAndView view,HttpServletRequest request,Map<String, Object> map) throws Exception{
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
			map=this.giftsInfoService.selectGiftByPage(map);
			view.addAllObjects(map);
		} catch (Exception e) {}
		view.setViewName(GiftsInfoPageEntity.GIFTSLISTPAGE);
		return view;
	}
	
	/*添加礼物信息的页面*/
	@RequestMapping(value="giftsaddpage",method=RequestMethod.GET)
	public ModelAndView giftsAddPage(ModelAndView view) throws Exception{
		view.setViewName(GiftsInfoPageEntity.GIFTSADDPAGE);
		return view;
	}
	
	/**添加礼物到数据库**/
	@RequestMapping(value="giftsinsert",method=RequestMethod.POST)
	public ModelAndView insertIntoGiftToDB(ModelAndView view,Map<String, Object> map,GiftsInfo gift,HttpServletRequest request,
			String titles,String picName,String prices) throws Exception{
		try {
			gift.setName(titles);  //名称
			gift.setPic(picName);  //图片路径
			gift.setPrice(prices); //价格
			gift.setWork(1);       //可用
			//调用添加方法
			if(this.giftsInfoService.insertIntoGift(gift)){
				return selectGiftPageList(view, request, map);
			}else{
				return giftsAddPage(view);
			}
		} catch (Exception e) {
			return giftsAddPage(view);
		}
	}
	
	/**礼物编辑的页面跳转**/
	@RequestMapping(value="updategiftpage",method=RequestMethod.GET)
	public ModelAndView updateGiftPage(ModelAndView view,HttpServletRequest request,Map<String,Object> map,
			String id) throws Exception{
		//查询单条信息
		GiftsInfo gift=this.giftsInfoService.selectGiftsByID(Integer.parseInt(id));
		if(gift!=null){
			map.put("gifts", gift);
		}else{
			return selectGiftPageList(view, request, map);
		}
		view.addAllObjects(map);
		view.setViewName(GiftsInfoPageEntity.GIFTSUPDATE);
		return view;
	}
	
	/**修改礼物到数据库**/
	@RequestMapping(value="giftsupdates",method=RequestMethod.POST)
	public ModelAndView updateToGiftToDB(ModelAndView view,Map<String, Object> map,GiftsInfo gift,HttpServletRequest request,
			String titles,String picName,String prices,String giftid,String sorts) throws Exception{
		try {
			gift.setId(Integer.parseInt(giftid)); 	//主键ID
			gift.setWork(Integer.parseInt(sorts));
			gift.setName(titles);  //名称
			gift.setPic(picName);  //图片路径
			gift.setPrice(prices); //价格
			gift.setWork(1);       //可用
			//调用添加方法
			if(this.giftsInfoService.insertIntoGift(gift)){
				return selectGiftPageList(view, request, map);
			}else{
				return giftsAddPage(view);
			}
		} catch (Exception e) {
			return giftsAddPage(view);
		}
	}
	
}
