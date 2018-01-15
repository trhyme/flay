package com.java.letch.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.java.letch.pagestrtools.ToexamineInfoPageEntity;
import com.java.letch.service.ToexamineInfoService;

/***
 * 用户信息审核认证信息请求
 * @author Administrator
 * 2017-09-29
 */
@Controller
public class ToexamineInfoController {
	
	@Resource(name="ToexamineInfoService")
	private ToexamineInfoService toexamineinfoService;		//用户审核信息的业务操作类
	
	/*分页查询信息*/
	@RequestMapping(value="toexaminelist",method=RequestMethod.GET)
	public ModelAndView selectToexaminePage(ModelAndView view,HttpServletRequest request,Map<String, Object> map,
			String uphone,String uuid,String unames,String stratus) throws Exception{
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
			
			/***查询条件信息***/
			map.put("uphone", uphone);		//手机号码
			map.put("uuid", uuid);			//用户编号
			map.put("unames",unames);		//用户昵称
			map.put("zhuangtai", stratus);	//状态
			/***查询条件信息***/
			
			//调用分页查询的方法
			map=this.toexamineinfoService.selectToexamineList(map);
			view.addAllObjects(map);
		} catch (Exception e) {
		}
		view.setViewName(ToexamineInfoPageEntity.TOEXAMINELISTPAGE);
		return view;
	}
	
	/**修改认证消息的审核状态**/
	@RequestMapping(value="updatetoexaminestratus",method=RequestMethod.GET)
	public ModelAndView updateToexamineStratus(ModelAndView view,HttpServletRequest request,Map<String, Object> map,
			String id,String thepage,String stra) throws Exception{
		request.setAttribute("thepage", thepage);  //设置当前页数
		//调用修改方法
		this.toexamineinfoService.updateToexamineStratus(Integer.parseInt(id),Integer.parseInt(stra));
		return selectToexaminePage(view, request, map,null,null,null,null);
	}
	
}
