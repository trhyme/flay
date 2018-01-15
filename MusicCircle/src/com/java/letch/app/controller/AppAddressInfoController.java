package com.java.letch.app.controller;

import java.util.HashMap;
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
import com.java.letch.model.AddressInfo;
import com.java.letch.service.AddressInfoService;
import com.java.letch.tools.DateHelper;
import com.java.letch.tools.IpAdrressTools;
import com.java.letch.tools.IpgetAdressTools;

/***
 * 用户经纬度的地址上传请求
 * @author Administrator
 * 2017-12-08
 */
@Controller
public class AppAddressInfoController {
	//4.用户地理位置信息类
	@Resource(name="AddressInfoService")
	private AddressInfoService addressinfoService;
	
	//JSON数据转换工具
	private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").serializeNulls().create();
	
	private Map<String, Object> map = new HashMap<String, Object>();
	private AppReturnCodeInfo app=new AppReturnCodeInfo();
	
	/***1.上传经纬度***/
	@RequestMapping(value="appuploadaddress",method=RequestMethod.POST,produces="text/json;charset=UTF-8")
	@ResponseBody
	public String insertIntoAdressMethod(String appid,int userid,String lgd,String lad,HttpServletRequest request) throws Exception{
		map.clear();
		try {
			AddressInfo address=new AddressInfo();
			address=this.addressinfoService.selectAddressByUserID(userid);
			if(address==null){
				address=new AddressInfo();
			}
			address.setUid(userid);		//用户ID
			address.setLgd(lgd); 				//经度
			address.setLad(lad);				//纬度
			//1.获取IP地址，保存用户地址信息
			String ip=IpAdrressTools.getIpAdrress(request);
			address.setIpadr(ip); 				//IP地址
			address.setAddtime(DateHelper.getNewData()); 	//时间
			try {
				address.setAddress(IpgetAdressTools.getAddresses("ip="+ip, "utf-8")); 	//地址
			} catch (Exception e2) {
			}
			//3.保存跟新位置信息
			if(this.addressinfoService.insertOrUpdateAddress(address)){
				app.setCode(200);
				app.setMessage("ok");
			}else{
				app.setCode(300);
				app.setMessage("no");
			}
		} catch (Exception e) {
			app.setCode(300);
			app.setMessage("no");
		}
		app.setMap(map);
		return gson.toJson(appid);
	}
	
}
