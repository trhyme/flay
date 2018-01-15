package com.java.letch.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.java.letch.pagestrtools.MemberInfoPageEntity;
import com.java.letch.service.MemberInfoService;

/***
 * 用户会员信息的请求类
 * @author Administrator
 * 2017-09-28
 */
@Controller
public class MemberInfoController {
	
	@Resource(name="MemberInfoService")
	private MemberInfoService memberinfoService;		//会员业务操作类
	
	/*后台查看信息列表*/
	@RequestMapping(value="memberlist")
	public ModelAndView selectMemberInfoPage(ModelAndView view,HttpServletRequest request,Map<String, Object> map) throws Exception{
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
			map=this.memberinfoService.selectMemberPage(map);
			view.addAllObjects(map);
		} catch (Exception e) {
		}
		view.setViewName(MemberInfoPageEntity.MEMBERLISTPAGE);
		return view;
	}
	
}
