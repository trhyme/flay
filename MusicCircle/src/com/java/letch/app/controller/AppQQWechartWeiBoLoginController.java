package com.java.letch.app.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
import com.java.letch.app.voicechat.ChartEntity;
import com.java.letch.baiduapi.tool.AddressByIP;
import com.java.letch.model.AddressInfo;
import com.java.letch.model.IphoneConfigure;
import com.java.letch.model.MemberInfo;
import com.java.letch.model.UsersInfo;
import com.java.letch.service.AddressInfoService;
import com.java.letch.service.MemberInfoService;
import com.java.letch.service.UsersInfoService;
import com.java.letch.tools.DateHelper;
import com.java.letch.tools.IpAdrressTools;
import com.java.letch.tools.IpgetAdressTools;
import com.java.letch.tools.UuidGenerate;

import io.rong.RongCloud;
import io.rong.configure.ConfigureEntity;
import io.rong.models.TokenResult;


/***
 * APP用户的QQ第三方登录
 * @author Administrator
 * 2017-11-02
 */
@Controller
public class AppQQWechartWeiBoLoginController {
	
	//用户业务操作类
	@Resource(name="UsersInfoService")
	private UsersInfoService userinfoService;
	
	//3.会员信息的操作类
	@Resource(name="MemberInfoService")
	private MemberInfoService memberinfoService;

	//4.用户地理位置信息类
	@Resource(name="AddressInfoService")
	private AddressInfoService addressinfoService;
	
	//JSON数据转换工具
	private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").serializeNulls().create();
	private Map<String, Object> map=new HashMap<>();
	private AppReturnCodeInfo app=new AppReturnCodeInfo();
	
	/***1.QQ、微信、微博登录的接口***/
	@RequestMapping(value="appuserthirdlogin",method=RequestMethod.POST,produces="text/json;charset=UTF-8")
	@ResponseBody
	public String appuserQQWechartWeiBoLoginMethod(String openid,String tokenid,HttpServletRequest request) throws Exception{
		map.clear();
		app.setMap(map);
		int userRegisterI=-1;
		//1.查询是否存在
		try {
			UsersInfo user=new UsersInfo();
			user=this.userinfoService.selectQQWXWBbyOpenID(openid);
			if(user==null){
				user=new UsersInfo();
				//2.新增用户的信息
				user.setOmsg(0); 
				user.setUmsg(0);
				user.setAuth(1);
				user.setContribution(1);
				user.setIscheck(1);
				user.setIsstratus(1);
				user.setAge(0);
				user.setApponlyid(UuidGenerate.getUUID());	//生成APPID
				user.setOpenid(openid);
				user.setDate(DateHelper.getNewData());
				if(this.userinfoService.insertIntoUserToDB(user)){
					app.setCode(200);
					app.setMessage("授权成功");
					userRegisterI=user.getId();
					map.put("user", user);
				}else{
					app.setCode(300);
					app.setMessage("授权失败");
				}
			}else{
				user.setApponlyid(UuidGenerate.getUUID());
				userRegisterI=user.getId();
				//3.返回用户的信息
				app.setCode(202);
				app.setMessage("登录成功");
				/***融云获取ToKen***/
				String userName="初遇";
				String userPhoto="http://images.leqtch.com/images/leqtchchat.png";
				if(user!=null){
					if(user.getUsername()!=null){userName=user.getUsername();}
					if(user.getHeadpic()!=null){userPhoto=user.getHeadpic();}
					
				}
				
				//创建一块内存来存放对象内容
		        ByteArrayOutputStream bout = new ByteArrayOutputStream();
		        ObjectOutputStream oos = new ObjectOutputStream(bout);
		        //将对象转换成二进制内容存入到开辟的内存中(序列化)
		        oos.writeObject(user);
		        //读取内存块中的二进制内容
		        ByteArrayInputStream bin = new ByteArrayInputStream(bout.toByteArray());
		        //将二进制内容转换回对象 反序列化
		        ObjectInputStream ois = new ObjectInputStream(bin);
				UsersInfo user2=(UsersInfo) ois.readObject();
				user2.setPassword("******");
				map.put("user", user2);
				
				
				//获取token
				RongCloud rongCloud = RongCloud.getInstance(ConfigureEntity.appkey, ConfigureEntity.appSecret);
				TokenResult userGetTokenResult = rongCloud.user.getToken(user.getId()+"", userName, userPhoto);
				String token=userGetTokenResult.toString();
				System.out.println("getToken:  " + token);
				ChartEntity chartEntity=gson.fromJson(token, ChartEntity.class);
				map.put("chattoken", chartEntity);
				/***融云获取ToKen***/
				
				/***【iPhone判断支付字段】  0表示不屏蔽  ***/
				map.put("limmit", 0);
				List<IphoneConfigure> listIphone=this.userinfoService.selectIphoneConfigure();
				if(listIphone!=null && listIphone.size()>0){
					map.put("limmit", listIphone.get(0).getIphone());
				}
				//
				/***【iPhone判断支付字段】  0表示不屏蔽***/
				
				/**查询会员信息**/
				//3.会员记录信息
				MemberInfo member=this.memberinfoService.selectMemberOneByID(user2.getId());
				if(member!=null){
					//判断会员是否过期
					//计算会员天数
      				//1.会员开始时间--->到当前时间的天数
      				int num1=DateHelper.getDaysBetween(member.getBegintime(), DateHelper.getNewData());
      				//2.会员开始时间--->会员到期时间
      				int num2=DateHelper.getDaysBetween(member.getBegintime(), member.getEndestime());
      				if(num1<=num2){   		//1.会员还未到期
      					//map.put("member", member);
      					member.setDaysnum((num2-num1)+"");
      					member.setStrutus(1);
      					map.put("membercheck", 1);
      				}else{
      					member.setStrutus(0);
      					member.setDaysnum("0");
      					map.put("membercheck", 0);
						//map.put("member",null);
      				}
				}else{
					map.put("membercheck", 0);
					//map.put("member",null);
				}
			}
		} catch (Exception e) {
			System.out.println(e);
			app.setCode(400);
			app.setMessage("系统走神了");
		}finally{
			//1.获取IP地址，保存用户地址信息
			String ip=IpAdrressTools.getIpAdrress(request);
			System.out.println("IP:"+ip);
			//2.通过IP获取经纬度
			if(ip!=null && ip.length()>0){
				String[] jw=AddressByIP.getIPXY(ip);
				System.out.println("JW："+jw);
				System.out.println();
				if(jw!=null){
					String lgd=jw[0];	//经度
					String lad=jw[1];	//纬度
					AddressInfo address=new AddressInfo();
					if(userRegisterI>0){
						address=this.addressinfoService.selectAddressByUserID(userRegisterI);
						if(address==null){
							address=new AddressInfo();
						}
						address.setUid(userRegisterI);		//用户ID
						address.setLgd(lgd); 				//经度
						address.setLad(lad);				//纬度
						address.setIpadr(ip); 				//IP地址
						address.setAddtime(DateHelper.getNewData()); 	//时间
						try {
							address.setAddress(IpgetAdressTools.getAddresses("ip="+ip, "utf-8")); 	//地址
						} catch (Exception e2) {
						}
						//3.保存跟新位置信息
						this.addressinfoService.insertOrUpdateAddress(address);
					}
				}
			}else{
				//
			}
				
		}
		return gson.toJson(app);
	}
	
}
