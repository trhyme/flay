package com.java.letch.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.java.letch.model.MemberGradeInfo;
import com.java.letch.pagestrtools.MemberGradeInfoPageEntity;
import com.java.letch.service.MemberGradeInfoService;

/***
 * 会员等级信息请求类
 * @author Administrator
 * 2017-09-27
 */
@Controller
public class MemberGradeInfoController {
	
	@Resource(name="MemberGradeInfoService")
	private MemberGradeInfoService membergradeinfoService;		//会员类别信息操作类
	
	//分页查询信息
	@RequestMapping(value="membergradelist",method=RequestMethod.GET)
	public ModelAndView selectMemberGradePage(ModelAndView view,HttpServletRequest request,Map<String, Object> map) throws Exception{
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
			map=this.membergradeinfoService.selectMemberGradePage(map);
			view.addAllObjects(map);
		} catch (Exception e) {}
		view.setViewName(MemberGradeInfoPageEntity.MEMBERGRADLISTPAGE);
		return view;
	}
	
	/*添加会员类别信息页面*/
	@RequestMapping(value="membergradeaddpage",method=RequestMethod.GET)
	public ModelAndView insertMemberGradePage(ModelAndView view) throws Exception{
		view.setViewName(MemberGradeInfoPageEntity.MEMBERGRADEADDPAGE);
		return view;
	}
	
	/**会员类别添加信息**/
	@RequestMapping(value="membergradeinsert",method=RequestMethod.POST)
	public ModelAndView insertMemberGradeToDB(ModelAndView view,HttpServletRequest request,Map<String, Object> map
			,String mname,String prices,String picName,String remark,String daynum) throws Exception{
		try {
			MemberGradeInfo member=new MemberGradeInfo();
			//设置数据
			member.setMname(mname);
			member.setPrices(Double.parseDouble(prices));
			member.setPicurl(picName);
			member.setStratu(1);        
			member.setRemark(remark);
			member.setDaynum(Integer.parseInt(daynum));
			if(this.membergradeinfoService.insertMemberGradeToDB(member)){
				return selectMemberGradePage(view, request, map);
			}else{
				return insertMemberGradePage(view);
			}
		} catch (Exception e) {
			System.out.println(e);
			return insertMemberGradePage(view);
		}
	}
	
	
	/**会员类型修改**/
	@RequestMapping(value="membergradeupdatepage",method=RequestMethod.GET)
	public ModelAndView updateMemberGradePage(ModelAndView view,String id,Map<String, Object> map) throws Exception{
		//查询信息
		MemberGradeInfo membergrad=this.membergradeinfoService.selectMemberGradeOneByID(Integer.parseInt(id));
		map.put("membergrad", membergrad);
		view.addAllObjects(map);
		view.setViewName(MemberGradeInfoPageEntity.MEMBERGRADUPDATE);
		return view;
	}
	
	/**会员等级信息修改**/
	@RequestMapping(value="membergradeupdate",method=RequestMethod.POST)
	public ModelAndView updateMemberGradeByOne(ModelAndView view,HttpServletRequest request,Map<String, Object> map
			,String mname,String prices,String picName,String remark,String id,String daynum) throws Exception{
		MemberGradeInfo member=new MemberGradeInfo();
		//设置数据
		member.setId(Integer.parseInt(id));
		member.setMname(mname);
		member.setPrices(Double.parseDouble(prices));
		member.setPicurl(picName);
		member.setStratu(1);        
		member.setRemark(remark);
		member.setDaynum(Integer.parseInt(daynum));
		//修改的方法
		this.membergradeinfoService.updateMemberGradeByOne(member);
		return selectMemberGradePage(view, request, map);
	}
	
	/**修改会员等级是否可使用状态**/
	@RequestMapping(value="membergdupdatestr",method=RequestMethod.GET)
	public ModelAndView updateMemberGradeStratus(ModelAndView view,HttpServletRequest request,Map<String, Object> map,String id,String stra) throws Exception{
		this.membergradeinfoService.updateMemberGradeStratus(Integer.parseInt(id), Integer.parseInt(stra));
		return selectMemberGradePage(view, request, map);
	}
	
}
