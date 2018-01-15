package com.java.letch.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.java.letch.model.AddressInfo;
import com.java.letch.service.AddressInfoService;

/***
 * 用户的地址操作类
 * @author Administrator
 * 2017-12-14
 */
@Controller
public class AddressInfoController {
	
	//地址业务处理类
	@Resource(name="AddressInfoService")
	private AddressInfoService addressinfoService;
	
	//JSON数据转换工具
	private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").serializeNulls().create();
		
	
	/***1.查询用户信息***/
	@RequestMapping(value="addressuserlist",method=RequestMethod.GET)
	@ResponseBody
	public ModelAndView selectAddressInfoPage(ModelAndView view,HttpServletRequest request,Map<String, Object> map) throws Exception{
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
			map.put("pagenum",30);			//每页条数（INT）
			map.put("bgstatrus", 1);		//状态,是否删除（INT）
			//调用分页查询的方法
			map=this.addressinfoService.selectPageList(map);
			//System.out.println(gson.toJson(map.get("addresslist")));
			List<AddressInfo> list=(List<AddressInfo>) map.get("addresslist");
			List<String> ss=new ArrayList<>();
			for(AddressInfo l:list){
				String str=l.getLgd()+","+l.getLad();
				ss.add(str);
			}
			map.put("strlist", gson.toJson(ss));
			view.addAllObjects(map);
		} catch (Exception e) {
			// TODO: handle exception
		}
		view.setViewName("/ADMINS/useramap");
		return view;
	}
	
}
