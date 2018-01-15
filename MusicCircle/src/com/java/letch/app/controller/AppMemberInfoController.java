package com.java.letch.app.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.java.letch.app.returnmodel.AppReturnCodeInfo;
import com.java.letch.baiduapi.tool.AddressByIP;
import com.java.letch.model.AddressInfo;
import com.java.letch.model.MemberGradeInfo;
import com.java.letch.model.MemberInfo;
import com.java.letch.service.AddressInfoService;
import com.java.letch.service.MemberGradeInfoService;
import com.java.letch.service.MemberInfoService;
import com.java.letch.tools.DateHelper;
import com.java.letch.tools.IpAdrressTools;
import com.java.letch.tools.IpgetAdressTools;

/***
 * 用户会员模块的接口请求类
 * @author Administrator
 * 2017-10-26
 */
@Controller
public class AppMemberInfoController {
	
	@Resource(name="MemberInfoService")
	private MemberInfoService memberinfoService;				//会员的业务操作信息
	
	@Resource(name="MemberGradeInfoService")
	private MemberGradeInfoService membergradeinfoService;		//会员的等级类别信息操作类
	
	//4.用户地理位置信息类
	@Resource(name="AddressInfoService")
	private AddressInfoService addressinfoService;
	
	//JSON数据转换工具
	private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").serializeNulls().create();
	private Map<String, Object> map=new HashMap<>();
	private AppReturnCodeInfo app=new AppReturnCodeInfo();
	
	/***1.查询出会员的等级制度信息***/
	@RequestMapping(value="appmembergradelist",method=RequestMethod.POST,produces="text/json;charset=UTF-8")
	@ResponseBody
	public String selectMemberGradeList(String appid) throws Exception{
		try {
			map.clear();
			app.setMap(map);
			List<MemberGradeInfo> list=this.membergradeinfoService.selectAllMemberGrade();
			map.put("membergrade", list);
			app.setCode(200);
			app.setMessage("请求成功");
			app.setMap(map);
		} catch (Exception e) {
			app.setCode(300);
			app.setMessage("请求失败");
		}
		return gson.toJson(app);
	}
	
	
	/***2.查询用户的会员的用户信息***/
	@RequestMapping(value="appselectusermember",method=RequestMethod.POST,produces="text/json;charset=UTF-8")
	@ResponseBody
	public String selectMemberByID(String appid,int userid,HttpServletRequest request) throws Exception{
		map.clear();
		try {
			try {
				//获取经纬度
				String lgd=request.getParameter("lgd");		//经度
				String lad=request.getParameter("lad");		//纬度
				if(lgd!=null && lad!=null){
					//1.获取IP地址，保存用户地址信息
					String ip=IpAdrressTools.getIpAdrress(request);
					AddressInfo address=new AddressInfo();
					address=this.addressinfoService.selectAddressByUserID(userid);
					if(address==null){
						address=new AddressInfo();
					}
					address.setUid(userid);		//用户ID
					address.setLgd(lgd); 				//经度
					address.setLad(lad);				//纬度
					address.setIpadr(ip); 				//IP地址
					address.setAddtime(DateHelper.getNewData()); 	//时间
					try {
						address.setAddress(IpgetAdressTools.getAddresses("ip="+ip, "utf-8")); 	//地址
					} catch (Exception e2) {
						System.out.println(e2);
					}
					//3.保存跟新位置信息
					this.addressinfoService.insertOrUpdateAddress(address);
				}
				
			} catch (Exception e) {
				// TODO: handle exception
			}
			/**查询会员信息**/
			//3.会员记录信息
			MemberInfo member=this.memberinfoService.selectMemberOneByID(userid);
			app.setCode(200);
			app.setMessage("请求成功");
			if(member!=null){
				//判断会员是否过期
				//计算会员天数
  				//1.会员开始时间--->到当前时间的天数
  				int num1=DateHelper.getDaysBetween(member.getBegintime(), DateHelper.getNewData());
  				//2.会员开始时间--->会员到期时间
  				int num2=DateHelper.getDaysBetween(member.getBegintime(), member.getEndestime());
  				if(num1<=num2){   		//1.会员还未到期
  					member.setStrutus(1);
  					member.setDaysnum((num2-num1)+"");
  					map.put("member", member);
  					map.put("membercheck", 1);
  				}else{
  					member.setStrutus(0);
  					member.setDaysnum("0");
  					map.put("membercheck", 0);
					map.put("member",null);
  				}
				
			}else{
				map.put("membercheck", 0);
				map.put("member",null);
			}
		} catch (Exception e) {
			app.setCode(300);
			app.setMessage("请求失败");
		}finally{
//			//1.获取IP地址，保存用户地址信息
//			String ip=IpAdrressTools.getIpAdrress(request);
//			System.out.println("IP:"+ip);
//			//2.通过IP获取经纬度
//			if(ip!=null && ip.length()>0){
//				String[] jw=AddressByIP.getIPXY(ip);
//				System.out.println("JW："+jw);
//				System.out.println();
//				if(jw!=null){
//					String lgd=jw[0];	//经度
//					String lad=jw[1];	//纬度
//					AddressInfo address=new AddressInfo();
//					if(userRegisterI>0){
//						address=this.addressinfoService.selectAddressByUserID(userRegisterI);
//						if(address==null){
//							address=new AddressInfo();
//						}
//						address.setUid(userRegisterI);		//用户ID
//						address.setLgd(lgd); 				//经度
//						address.setLad(lad);				//纬度
//						address.setIpadr(ip); 				//IP地址
//						address.setAddtime(DateHelper.getNewData()); 	//时间
//						try {
//							address.setAddress(IpgetAdressTools.getAddresses("ip="+ip, "utf-8")); 	//地址
//						} catch (Exception e2) {
//						}
//						//3.保存跟新位置信息
//						this.addressinfoService.insertOrUpdateAddress(address);
//					}
//				}
//			}else{
//				//
//			}
		}
		app.setMap(map);
		return gson.toJson(app);
	}
	
	
}
