package com.java.letch.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.java.letch.pagestrtools.RechargeInfoPageEntity;
import com.java.letch.service.RechargeInfoService;

/***
 * 用户会员充值记录信息
 * @author Administrator
 * 2017-09-29
 */
@Controller
public class RechargeInfoController {
	
	@Resource(name="RechargeInfoService")
	private RechargeInfoService rechargeinfoService;		//用户会员充值记录类
	
	/**分页查询信息**/
	@RequestMapping(value="rechargeiflist",method=RequestMethod.GET)
	public ModelAndView selectRechargeInfoPage(ModelAndView view,HttpServletRequest request,Map<String, Object> map,
			String uphone,String uuid,String unames) throws Exception{
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
			map.put("uphone", uphone);	//手机号码
			map.put("uuid", uuid);		//用户编号
			map.put("unames",unames);	//用户昵称
			/***查询条件信息***/
			
			//调用分页查询的方法
			map=this.rechargeinfoService.selectRechargeInfoPage(map);
			view.addAllObjects(map);
		} catch (Exception e) {
		}
		view.setViewName(RechargeInfoPageEntity.RECHARGELIST);
		return view;
	}
	
}
