package com.java.letch.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.java.letch.model.TypesVoiceInfo;
import com.java.letch.pagestrtools.BannerInfoPageEntity;
import com.java.letch.pagestrtools.TypesVoiceInfoPageEntity;
import com.java.letch.service.TypesVoiceInfoService;

/***
 * 标签类型的请求类
 * @author Administrator
 * 20017-09-20
 */
@Controller
public class TypesVoiceInfoController {
	
	@Resource(name="TypesVoiceInfoService")
	private TypesVoiceInfoService typesVoiceInfoService;	//类型标签数据操作
	
	
	/***
	 * 后台查看数据分页
	 */
	@RequestMapping(value="typesvoicelist",method=RequestMethod.GET)
	public ModelAndView selectTypesListPageAll(ModelAndView view,HttpServletRequest request,Map<String, Object> map) throws Exception{
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
			map=this.typesVoiceInfoService.selectTypesPageList(map);
			view.addAllObjects(map);
		} catch (Exception e) {
		}
		
		view.setViewName(TypesVoiceInfoPageEntity.TYPESLISTPAGE);
		return view;
	}
	
	/**查看全部数据信息**/
	@RequestMapping(value="typesvoiceall",method=RequestMethod.GET)
	public ModelAndView selectTypesListAll(ModelAndView view,HttpServletRequest request,Map<String, Object> map) throws Exception{
		List<TypesVoiceInfo> list=this.typesVoiceInfoService.selectAllTypesList();
		map.put("typeslist", list);
		view.addAllObjects(map);
		view.setViewName(TypesVoiceInfoPageEntity.TYPESLISTPAGE);
		return view;
	}
	
	/**添加信息**/
	@RequestMapping(value="typesvoiceadd",method=RequestMethod.POST)
	public ModelAndView insertIntoTypes(ModelAndView view,String titles,TypesVoiceInfo typesvo,HttpServletRequest request,Map<String, Object> map) throws Exception{
		typesvo.setName(titles);
		//添加方法
		if(this.typesVoiceInfoService.insertIntoTypes(typesvo)){
			map.put("messages","添加成功");
		}else{
			map.put("messages","添加失败");
		}
		return selectTypesListAll(view, request, map);
	}
	 
	
	
}
