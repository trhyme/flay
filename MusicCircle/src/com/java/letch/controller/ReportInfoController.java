package com.java.letch.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.java.letch.model.ReportInfo;
import com.java.letch.pagestrtools.ReportInfoPageEntity;
import com.java.letch.service.ReportInfoService;

/***
 * 用户举报信息的请求类操作
 * @author Administrator
 * 2017-10-09
 */
@Controller
public class ReportInfoController {

	@Resource(name="ReportInfoService")
	private ReportInfoService reportinfoService;		//用户举报信息的业务
	
	/*后台分页查询*/
	@RequestMapping(value="reportlist",method=RequestMethod.GET)
	public ModelAndView selectReportPage(ModelAndView view,HttpServletRequest request,Map<String, Object> map) throws Exception{
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
			map=this.reportinfoService.selectReportByPage(map);
			view.addAllObjects(map);
		} catch (Exception e) {
		}
		
		view.setViewName(ReportInfoPageEntity.REPORTLISTPAGE);
		return view;
	}
	
	/**根据编号查看详细信息页面**/
	@RequestMapping(value="showonereport",method=RequestMethod.GET)
	public ModelAndView selectOneReportByID(ModelAndView view,HttpServletRequest request,String id,Map<String, Object> map) throws Exception{
		try {
			ReportInfo rep=new ReportInfo();
			rep=this.reportinfoService.selectReportByID(Integer.parseInt(id));
			if(rep!=null){
				map.put("repone", rep);
				view.setViewName(ReportInfoPageEntity.REPORTSELECTONE);
				return view;
			}
		} catch (Exception e) {
		}
		return selectReportPage(view, request, map);
		
	}
	
	/**举报信息的回复信息请求**/
	@RequestMapping(value="updatereplyrep",method=RequestMethod.POST)
	public ModelAndView updateReplyReportByID(ModelAndView view,HttpServletRequest request,int repid,String remark,Map<String, Object> map) throws Exception{
		if(remark!=null && !"".equals(remark)){
			if(this.reportinfoService.updateReplytieByID(repid, remark, 1)){
				
			}
		}else{
			return selectReportPage(view, request, map);
		}
		
		System.out.println(repid+":"+remark);
		return selectReportPage(view, request, map);
	}
	
	
	
}
