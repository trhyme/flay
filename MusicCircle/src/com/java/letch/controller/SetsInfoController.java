package com.java.letch.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.java.letch.model.SetsInfo;
import com.java.letch.pagestrtools.SetsInfoPageEntity;
import com.java.letch.service.SetsInfoService;

/***
 * 设置信息的请求COntroller
 * @author Administrator
 * 2017-09-21
 */
@Controller
public class SetsInfoController {
	
	@Resource(name="SetsInfoService")
	private SetsInfoService setsInfoService;		//配置信息业务操作类
	
	/**查看设置信息内容**/
	@RequestMapping(value="setsinfopage",method=RequestMethod.GET)
	public ModelAndView selectSetsInfoPage(ModelAndView view,Map<String, Object> map) throws Exception{
		SetsInfo sets=this.setsInfoService.selectSetsInfo();
		map.put("sets", sets);
		view.addAllObjects(map);
		view.setViewName(SetsInfoPageEntity.SETSINFOPAGE);
		return view;
	}
	
	/**根据编号修改1元人民币的虚拟货币兑换率**/
	@RequestMapping(value="updateratenb",method=RequestMethod.POST)
	public ModelAndView updateSetsRatenb(ModelAndView view,Map<String, Object> map,String idone,String ratenb) throws Exception{
		if(ratenb!=null && ratenb!="" && !"".equals(ratenb)){
			this.setsInfoService.updateRatenb(Integer.parseInt(idone), ratenb);
		}
		return selectSetsInfoPage(view, map);
	}
	
	/**根据编号修改登录奖励币**/
	@RequestMapping(value="updateloginnb",method=RequestMethod.POST)
	public ModelAndView updateLoginnb(ModelAndView view,Map<String, Object> map,String idtwo,String loginnb) throws Exception{
		if(loginnb!=null && loginnb!="" && !"".equals(loginnb)){
			this.setsInfoService.updateLoginnb(Integer.parseInt(idtwo), loginnb);
		}
		return selectSetsInfoPage(view, map);
	}
	
	/**根据编号修改登录奖励币**/
	@RequestMapping(value="updaterechargearr",method=RequestMethod.POST)
	public ModelAndView updateRechargearr(ModelAndView view,Map<String, Object> map,String idthree,String rechargearr) throws Exception{
		if(rechargearr!=null && rechargearr!="" && !"".equals(rechargearr)){
			this.setsInfoService.updateLoginnb(Integer.parseInt(idthree),rechargearr);
		}
		return selectSetsInfoPage(view, map);
	}
	
	/**根据编号修改偷听/看的所需系统币**/
	@RequestMapping(value="updatetapnb",method=RequestMethod.POST)
	public ModelAndView updateTapnb(ModelAndView view,Map<String, Object> map,String idfour,String tapnb) throws Exception{
		if(tapnb!=null && tapnb!="" && !"".equals(tapnb)){
			this.setsInfoService.updateTapnb(Integer.parseInt(idfour),tapnb);
		}
		return selectSetsInfoPage(view, map);
	}
	
	/**根据编号修改偷听/看的奖励信息**/
	@RequestMapping(value="updaterewardnb",method=RequestMethod.POST)
	public ModelAndView updateRewardnb(ModelAndView view,Map<String, Object> map,String idfive,String rewardnb) throws Exception{
		if(rewardnb!=null && rewardnb!="" && !"".equals(rewardnb)){
			this.setsInfoService.updateRewardnbs(Integer.parseInt(idfive), rewardnb);
		}
		return selectSetsInfoPage(view, map);
	}
	
	/**修改1元人民币与蓝砖的兑换率**/
	@RequestMapping(value="updatebluebrick",method=RequestMethod.POST)
	public ModelAndView updateBluebrick(ModelAndView view,Map<String, Object> map,String idsix,String bluebrick) throws Exception{
		if(bluebrick!=null && bluebrick!="" && !"".equals(bluebrick)){
			this.setsInfoService.updateBluebrick(Integer.parseInt(idsix), bluebrick);
		}
		return selectSetsInfoPage(view, map);
	}
	
	/**修改1元RNM与红砖的兑换率**/
	@RequestMapping(value="updateredsbrick",method=RequestMethod.POST)
	public ModelAndView updateRedsbrick(ModelAndView view,Map<String, Object> map,String idseven,String redsbrick) throws Exception{
		if(redsbrick!=null && redsbrick!="" && !"".equals(redsbrick)){
			this.setsInfoService.updateRedsbrick(Integer.parseInt(idseven),redsbrick);
		}
		return selectSetsInfoPage(view, map);
	}
	
	/**修改蓝砖与红砖的兑换比率**/
	@RequestMapping(value="updaterates",method=RequestMethod.POST)
	public ModelAndView updateRates(ModelAndView view,Map<String, Object> map,String ideight,String bluerates,String redsrates) throws Exception{
		if(bluerates!=null && bluerates!="" && !"".equals(bluerates)){
			if(redsrates!=null && redsrates!="" && !"".equals(redsrates)){
				this.setsInfoService.updateRates(Integer.parseInt(ideight), bluerates, redsrates);
			}
		}
		return selectSetsInfoPage(view, map);
	}
	
}
