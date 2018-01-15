package com.java.letch.app.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.mp3.MP3AudioHeader;
import org.jaudiotagger.audio.mp3.MP3File;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.java.letch.app.ShortMessageCodeHttp;
import com.java.letch.app.returnmodel.AppReturnCodeInfo;
import com.java.letch.app.voicechat.ChartEntity;
import com.java.letch.baiduapi.tool.AddressByIP;
import com.java.letch.model.AddressInfo;
import com.java.letch.model.IphoneConfigure;
import com.java.letch.model.MemberInfo;
import com.java.letch.model.SendcodeInfo;
import com.java.letch.model.UsersInfo;
import com.java.letch.model.view.MatchUserView;
import com.java.letch.pagestrtools.UsersInfoPageEntity;
import com.java.letch.service.AddressInfoService;
import com.java.letch.service.MemberInfoService;
import com.java.letch.service.SendcodeInfoService;
import com.java.letch.service.UsersInfoService;
import com.java.letch.tools.BannerImageOSSTools;
import com.java.letch.tools.DateCompareTools;
import com.java.letch.tools.DateHelper;
import com.java.letch.tools.EncryptionTools;
import com.java.letch.tools.IpAdrressTools;
import com.java.letch.tools.IpgetAdressTools;
import com.java.letch.tools.NumberCodeTools;
import com.java.letch.tools.OSSClientUtil;
import com.java.letch.tools.UuidGenerate;

import io.rong.RongCloud;
import io.rong.configure.ConfigureEntity;
import io.rong.models.TokenResult;
import javazoom.jl.decoder.Bitstream;
import javazoom.jl.decoder.Header;
import net.sf.json.JSONObject;

/***
 * APP用户信息的接口请求类
 * @author Administrator
 * 2017-09-18
 */
@Controller
public class AppUserInfoController {
	
	//用户业务操作类
	@Resource(name="UsersInfoService")
	private UsersInfoService userinfoService;
	
	//用户的手机短信验证码
	@Resource(name="SendcodeInfoService")
	private SendcodeInfoService sendcodeinfoService;
	
	//3.会员信息的操作类
	@Resource(name="MemberInfoService")
	private MemberInfoService memberinfoService;
	
	//4.用户地理位置信息类
	@Resource(name="AddressInfoService")
	private AddressInfoService addressinfoService;
	
	//JSON数据转换工具
	private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").serializeNulls().create();
	
	private String codeMessage="【初遇】验证码：";
	private String codeFooters="。有效期6分钟。如果非本人操作请忽略。";
	
	private Map<String, Object> map = new HashMap<String, Object>();
	private AppReturnCodeInfo app=new AppReturnCodeInfo();
	
	/**发送验证码的接口请求**/
	@RequestMapping(value="appsendcode",method=RequestMethod.GET,produces="text/json;charset=UTF-8")
	@ResponseBody
	public String shortMessageCode(String phone,Map<String, Object> map,HttpServletResponse response,HttpServletRequest request) throws Exception{
		try {
			map.clear();
			response.setContentType("text/html;charset=UTF-8");
			response.setHeader("content-type","text/html;charset=UTF-8");
			//判断是否为手机号码
			Pattern p = Pattern.compile("^((13[0-9])|(15[^4])|(18[0-9])|(17[0-9])|(147))\\d{8}$");  
	        Matcher m = p.matcher(phone);  
	        if(m.matches()){
	        	/**检查是否注册过**/
	        	if(this.userinfoService.selectUserByPhone(phone)){
	        		map.put("code","300");
	        		map.put("message","该手机号码已经被注册");
	        	}else{
	        		//生成验证码(6位)
		        	String code=NumberCodeTools.getRandomNumberCode(6);
		        	System.out.println("验证码："+code);
		        	//可以发送验证码
		        	if(ShortMessageCodeHttp.sendCodeMeaage(phone,codeMessage+code+codeFooters)){
		        		//将验证码存Session
		        		HttpSession session=request.getSession();
		        		session.setAttribute(UsersInfoPageEntity.APPCODESESSION,code);	//验证码
		        		session.setAttribute(UsersInfoPageEntity.APPPHONESESSION,phone);
		        		//手机+验证码保存入库
		        		SendcodeInfo send=new SendcodeInfo();
		        		send.setPhone(phone);
		        		send.setCodes(code);
		        		send.setAddtime(DateHelper.getNewData());
		        		this.sendcodeinfoService.sevaSendCodeInfo(send);
		        		map.put("code", 200);
		        		map.put("message", "发送成功");
		        	}else{
		        		map.put("code",400);
		        		map.put("message", "发送失败");
		        	}
	        	}
	        }else{
	        	map.put("code",405);
        		map.put("message", "手机号码错误");
	        }
		} catch (Exception e) {
			map.put("code",404);
    		map.put("message", "发生错误");
		}
		return gson.toJson(map);
	}
	
	/**1.注册第一步：APP手机号码注册**/
	@RequestMapping(value="phoneregister",method=RequestMethod.POST,produces="text/json;charset=UTF-8")
	@ResponseBody
	public String phoneNumRegister(String phone,String codes,String pwds,HttpServletRequest request) throws Exception{
		Map<String, Object> map=new HashMap<>();
		AppReturnCodeInfo app=new AppReturnCodeInfo();
		map.clear();
		int userRegisterI=-1;
		try {
			//获取Session
			if(!this.sendcodeinfoService.selectByphoneCode(phone, codes)){
				app.setCode(300);
				app.setMessage("验证失效");
				map.put("code","300");
				map.put("message","验证码失效");
			}else{
					if(pwds.length()<6){
						app.setCode(302);
						app.setMessage("密码至少为6位");
					}else if(pwds.length()>16){
						app.setCode(303);
						app.setMessage("密码最多为16位");
					}else{
						//加密密码
						pwds=EncryptionTools.setBase64(pwds);
						//封装数据
						UsersInfo user=new UsersInfo();
						user.setPhone(phone);		//手机号码
						user.setPassword(pwds);		//密码
						user.setAuth(1);  			//是否可用
						user.setDate(DateHelper.getNewData());//注册时间
						//生成APPID
						user.setApponlyid(UuidGenerate.getUUID());
						//设置注册步骤
						user.setIsstratus(1);
						user.setOmsg(0);
						user.setUmsg(0);
						user.setGreethello("Hey!");
						
						//默认值
						user.setAge(18);
						user.setProvince("北京");
						user.setCity("北京市");
						user.setArea("东城区");
						
						//注册
						if(this.userinfoService.insertIntoUserToDB(user)){
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
							app.setCode(200);
							app.setMessage("注册成功");
							map.put("user", user2);
							app.setMap(map);
							userRegisterI=user2.getId();
						}else{
							app.setCode(400);
							app.setMessage("注册失败");
						}
					}
				}
		} catch (Exception e) {
			app.setCode(404);
			app.setMessage("系统走神了!");
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
	
	/**2.注册第二步：上传声音文件**/
	@RequestMapping(value="voicesregister",method=RequestMethod.POST,produces="text/json;charset=UTF-8")
	@ResponseBody
	public String voicesRegisterMetch(@RequestParam(value="voicesName") MultipartFile voicesName,String appid,HttpServletRequest request,HttpServletResponse response) throws Exception{
		response.setContentType("text/html;charset=utf-8");   
		Map<String, Object> value = new HashMap<String, Object>();
		AppReturnCodeInfo app=new AppReturnCodeInfo();
		map.clear();
		try {
			 BannerImageOSSTools ban=new BannerImageOSSTools();
		     String head = ban.updateHead(voicesName, 4,"voices",1);//此处是调用上传服务接口，4是需要更新的userId 测试数据。
		     value.put("datasrc", head);
		     System.out.println(head);
		     
		     UsersInfo user=new UsersInfo();
		     user=this.userinfoService.selectAppUserByAPPid(appid);
		     user.setVoicesurl(head);
		     user.setIsstratus(2);
		     app.setCode(200);
		     app.setMessage("声音上传成功");
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
		     value.put("user", user2);
		     app.setMap(value);
		     System.out.println(appid);
		     //根据AppID用户的真正信息
		     this.userinfoService.updateAppuserVoices(appid, head);
		     
		} catch (IOException e) {
			System.out.println(e);
			 app.setCode(300);
			 app.setMessage("声音上传失败");
		}
		return gson.toJson(app);
	}
	
	/**3.注册第三步：设置昵称、性别信息**/
	@RequestMapping(value="nicknameregister",method=RequestMethod.POST,produces="text/json;charset=UTF-8")
	@ResponseBody
	public String nicknameRegisterMethod(String appid,String nickname,String sex,String fredstate,HttpServletRequest request) throws Exception{
		if(sex==null || sex==""){
			sex="男";
		}
		//dc2725a9e0cf496ab0e7f310c25330d7
		//查询姓名是否存在
		List<UsersInfo> list=this.userinfoService.selectUserByUserName(nickname);
		UsersInfo user=this.userinfoService.selectAppUserByAPPid(appid);
		if(list!=null && list.size()>0){
			nickname=nickname+user.getNum();
		}
		/**** 【2017-12-12】 用户修改地址和年龄 ****/
		//1.省
		String province=request.getParameter("province");
		if(province!=null){
			user.setProvince(province);
		}
		//2.市
		String city=request.getParameter("city");
		if(city!=null){
			user.setCity(city);
		}
		//3.区
		String area=request.getParameter("area");
		if(area!=null){
			user.setArea(area);
		}
		//4.年龄
		String age=request.getParameter("birth");
		if(age!=null){
			user.setBirth(age);
		}
		//修改昵称、性别资料信息 (appid, nickname, sex,fredstate)
		if(this.userinfoService.updateAppuserNickname(appid, nickname, sex, fredstate, province, city, area, age)){
			map.clear();
			app.setMap(map);
			app.setCode(200);
			app.setMessage("注册成功");
			//根据APPID查询用户信息
			//UsersInfo user=this.userinfoService.selectAppUserByAPPid(appid);
			user.setUsername(nickname);
			user.setSex(sex);
			user.setFriendstate(fredstate);
			user.setIsstratus(3);
			if(user!=null){
				
				
				
				
				//修改信息
				
				
				/***融云获取ToKen***/
				String userName="初遇";
				String userPhoto="http://images.leqtch.com/images/leqtchchat.png";
				if(user!=null){
					if(user.getUsername()!=null){userName=user.getUsername();}
					if(user.getHeadpic()!=null){userPhoto=user.getHeadpic();}
				}
				user.setIsstratus(3);
				user.setUsername(userName);
				user.setSex(sex);
				
				
				
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
				/**查询会员信息**/
				//3.会员记录信息
				MemberInfo member=this.memberinfoService.selectMemberOneByID(user2.getId());
				if(member!=null){
					if(member.getStrutus()>0){
						//判断会员是否过期
						//计算会员天数
	      				//1.会员开始时间--->到当前时间的天数
	      				int num1=DateHelper.getDaysBetween(member.getBegintime(), DateHelper.getNewData());
	      				//2.会员开始时间--->会员到期时间
	      				int num2=DateHelper.getDaysBetween(member.getBegintime(), member.getEndestime());
	      				if(num1<=num2){   		//1.会员还未到期
	      					//map.put("member", member);
	      					map.put("membercheck", 1);
	      					member.setDaysnum((num2-num1)+"");
	      				}else{
	      					map.put("membercheck", 0);
	      					member.setDaysnum("0");
							//map.put("member",null);
	      				}
					}else{
						map.put("membercheck", 0);
					}
					
				}else{
					map.put("membercheck", 0);
					//map.put("member",null);
				}
				app.setMap(map);
			}
		}else{
			app.setCode(300);
			app.setMessage("注册失败，请重试");
		}
		return gson.toJson(app);
	}
	
	/** 4.APP登录的方法 **/
	@RequestMapping(value="phonelogin",method=RequestMethod.POST,produces="text/json;charset=UTF-8")
	@ResponseBody
	public String appUserLogin(String phone,String pwds,HttpServletRequest request) throws Exception{
		map = new HashMap<String, Object>();
		app=new AppReturnCodeInfo();
		int userRegisterI=-1;
		try {
			/*String phonetwo=request.getParameter("phone");
			System.out.println("电话:"+phonetwo);*/
			/*map.clear();
			app.setMap(map);*/
			System.out.println("电话："+phone);
			//根据手机号码查询用户
			UsersInfo user=this.userinfoService.selectUserForPhone(phone);
			if(user==null){
				app.setCode(300);
				app.setMessage("账号不存在");
			}else{
				//判断账号密码
				//System.out.println(pwds);
				pwds=EncryptionTools.setBase64(pwds);
				//System.out.println(pwds);
				if(pwds.equals(user.getPassword()) && phone.equals(user.getPhone())){
					/**给登录的用户设置唯一的登录APPID**/
					user.setApponlyid(UuidGenerate.getUUID());
					userRegisterI=user.getId();
					this.userinfoService.updateUserByUser(user);
					/*map.put("code","200");
					map.put("message", "登录成功");*/
					app.setCode(200);
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
          					member.setStrutus(1);
          					//剩余天数
          					member.setDaysnum((num2-num1)+"");
          					map.put("membercheck", 1);
          				}else{
          					member.setStrutus(0);
          					member.setDaysnum(0+"");
          					map.put("membercheck", 0);
    						//map.put("member",null);
          				}
					}else{
						map.put("membercheck", 0);
						//map.put("member",null);
					}
					app.setMap(map);
				}else{
					map.put("membercheck", 0);
					//map.put("member",null);
					app.setCode(301);
					app.setMessage("密码错误");
				}
			}
		} catch (Exception e) {
			map.put("membercheck", 0);
			//map.put("member",null);
			app.setCode(404);
			app.setMessage("系统走神了!");
		}finally{
			/*//1.获取IP地址，保存用户地址信息
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
			}*/
				
		}
		System.out.println("手机登陆");
		return gson.toJson(app);
	}
	
	
	/***5.修改个性签名接口***/
	@RequestMapping(value="updateusersign",method=RequestMethod.POST,produces="text/json;charset=UTF-8")
	@ResponseBody
	public String updateAppuserSign(String appid,String sign) throws Exception{
		map.clear();
		if(this.userinfoService.updateAppUserSign(appid, sign)){
			app.setCode(200);
			app.setMessage("修改签名成功");
			map.put("sign", sign);
			app.setMap(map);
		}else{
			app.setCode(300);
			app.setMessage("修改签名失败");
		}
		return gson.toJson(app);
	}
	
	/****6.忘记密码+【修改密码接口】****/
	@RequestMapping(value="userforgetpwds",method=RequestMethod.POST,produces="text/json;charset=UTF-8")
	@ResponseBody
	public String forgotPasswordTb(String phone,String code,String pwds) throws Exception{
		//首先查询出验证码
		SendcodeInfo send=this.sendcodeinfoService.selectByphoneBycode(phone, code);
		map.clear();
		if(send!=null){
			app.setMap(map);
			//判断验证码是否过期
			if(DateCompareTools.compareOne(send.getAddtime(), 6)){
				//密码加密
				pwds=EncryptionTools.setBase64(pwds);
				//执行修改密码操作
				if(this.userinfoService.updateAppUserPwds(phone, pwds)){
					//查询出用户数据
					//根据手机号码查询用户
					UsersInfo user=this.userinfoService.selectUserForPhone(phone);
					if(user!=null){
						if(user.getPassword().equals(pwds)){
							//修改成功
							app.setCode(200);
							app.setMessage("密码找回成功");	
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
							app.setMap(map);
						}else{
							app.setCode(400);
							app.setMessage("密码找回失败");
						}
					}
				}else{
					app.setCode(400);
					app.setMessage("密码找回失败");
				}
			}else{	//验证码过期
				app.setCode(301);
				app.setMessage("验证码过期");
			}
		}else{	//验证码错误
			app.setCode(300);
			app.setMessage("手机验证码错误");
		}
		return gson.toJson(app);
	}
	
	
	/**7.发送验证码的接口请求**/
	@RequestMapping(value="appsendcodetwo",method=RequestMethod.POST,produces="text/json;charset=UTF-8")
	@ResponseBody
	public String shortMessageCodeTwo(String phone,Map<String, Object> map,HttpServletResponse response,HttpServletRequest request) throws Exception{
		try {
			map.clear();
			app.setMap(map);
			response.setContentType("text/html;charset=UTF-8");
			response.setHeader("content-type","text/html;charset=UTF-8");
			//判断是否为手机号码
			Pattern p = Pattern.compile("^((13[0-9])|(15[^4])|(18[0-9])|(17[0-9])|(147))\\d{8}$");  
	        Matcher m = p.matcher(phone);  
	        if(m.matches()){
	        	//生成验证码(6位)
	        	String code=NumberCodeTools.getRandomNumberCode(6);
	        	System.out.println("验证码："+code);
	        	//可以发送验证码
	        	if(ShortMessageCodeHttp.sendCodeMeaage(phone,codeMessage+code+codeFooters)){
	        		//将验证码存Session
	        		HttpSession session=request.getSession();
	        		session.setAttribute(UsersInfoPageEntity.APPCODESESSION,code);	//验证码
	        		session.setAttribute(UsersInfoPageEntity.APPPHONESESSION,phone);
	        		//手机+验证码保存入库
	        		SendcodeInfo send=new SendcodeInfo();
	        		send.setPhone(phone);
	        		send.setCodes(code);
	        		send.setAddtime(DateHelper.getNewData());
	        		this.sendcodeinfoService.sevaSendCodeInfo(send);
	        		map.put("code", 200);
	        		map.put("message", "发送成功");
	        	}else{
	        		map.put("code",400);
	        		map.put("message", "发送失败");
	        	}
	        }else{
	        	map.put("code",405);
        		map.put("message", "手机号码错误");
	        }
		} catch (Exception e) {
			map.put("code",404);
    		map.put("message", "发生错误");
		}
		return gson.toJson(map);
	}
	
	
	/***8.修改基本信息接口***/
	@RequestMapping(value="updatebasicdata",method=RequestMethod.POST,produces="text/json;charset=UTF-8")
	@ResponseBody
	public String updateBasicData(String appid,int age,String birth,String height,String province,String city,String area) throws  Exception{
		map=new HashMap<>();
		System.out.println("年龄："+age);
		//调用修改的方法
		if(this.userinfoService.updateBasicData(appid, age, birth, height, null, null, province, city, area)){
			app.setMap(map);
			app.setCode(200);
			app.setMessage("基本资料修改成功");
			//查询出资料信息
			UsersInfo user=this.userinfoService.selectAppUserByAPPid(appid);
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
			user2.setProvince(province);
			user2.setCity(city);
			user2.setArea(area);
			user2.setBirth(birth);
			user2.setHeight(height);
			user2.setAge(age);
			map.put("user", user2);
			app.setMap(map);
		}else{
			app.setCode(300);
			app.setMessage("基本资料修改失败");
		}
		return gson.toJson(app);
	}
	
	/**9.个人资料修改介绍语音信息**/
	@RequestMapping(value="voicesupdate",method=RequestMethod.POST,produces="text/json;charset=UTF-8")
	@ResponseBody
	public String voicesUpdateMetch(@RequestParam(value="voicesName") MultipartFile voicesName,String appid,HttpServletRequest request,HttpServletResponse response) throws Exception{
		 map.clear();
		response.setContentType("text/html;charset=utf-8");   
		Map<String, Object> value = new HashMap<String, Object>();
		AppReturnCodeInfo app=new AppReturnCodeInfo();
		try {
			 app.setMap(map);
			 BannerImageOSSTools ban=new BannerImageOSSTools();
		     String head = ban.updateHead(voicesName, 4,"voices",1);//此处是调用上传服务接口，4是需要更新的userId 测试数据。
		     value.put("datasrc", head);
		     System.out.println(head);
		     app.setCode(200);
		     app.setMessage("声音修改成功");
		     app.setMap(value);
		     System.out.println(appid);
		     //根据AppID用户的真正信息
		     this.userinfoService.updateUserVoices(appid, head);
		} catch (IOException e) {
			 app.setCode(300);
			 app.setMessage("声音修改失败");
		}
		return gson.toJson(app);
	}
	
	
	/***10.修改用户的标签信息***/
	@RequestMapping(value="userlabelhupdate",method=RequestMethod.POST,produces="text/json;charset=UTF-8")
	@ResponseBody
	public String updateTypeidLabel(String appid,String label) throws Exception{
		map.clear();
		//修改
		if(this.userinfoService.updateLabelByAppID(appid, label)){
			app.setMap(map);
			app.setCode(200);
			app.setMessage("标签修改成功");
			map.put("label", label);
			app.setMap(map);
		}else{
			app.setCode(300);
			app.setMessage("标签修改失败");
		}
		return gson.toJson(app);
	}
	
	/***11.修改用户的打招呼内容设置***/
	@RequestMapping(value="userhelloupdate",method=RequestMethod.POST,produces="text/json;charset=UTF-8")
	@ResponseBody
	public String updateGreethelloMethod(String appid,String greethello) throws Exception{
		map.clear();
		//修改
		if(this.userinfoService.updateGreetHello(appid, greethello)){
			app.setCode(200);
			app.setMessage("招呼语设置成功");
			map.put("greethello", greethello);
			app.setMap(map);
		}else{
			app.setCode(300);
			app.setMessage("招呼语设置失败");
		}
		return gson.toJson(app);
	}
	
	/***12.用户匹配信息***/
	@RequestMapping(value="usermatching",method=RequestMethod.POST,produces="text/json;charset=UTF-8")
	@ResponseBody
	public String appuserMatching(String appid,int userid,String agebegin,String ageend,String sex,String cityarea,String height,String ischeck,
			HttpServletRequest request) throws Exception{
		map.clear();
		try {
			int nowpage=1;
			try {
				String page=request.getParameter("nowpage");
				if(page!=null){
					nowpage=Integer.parseInt(page);
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
			if(nowpage<=0){
				
				nowpage=1;
			}
			System.out.println("页数："+nowpage);
			map=this.userinfoService.matchingUserList(userid, agebegin, ageend, sex, cityarea, height, ischeck,nowpage,map);
			//map.put("userlist", list);
			app.setMap(map);
			app.setCode(200);
			app.setMessage("请求成功");
		} catch (Exception e) {
			app.setCode(300);
			app.setMessage("请求失败");
		}
		return gson.toJson(app);
	}
	
	
	/***13.修改职业信息接口***/
	@RequestMapping(value="userupdateworks",method=RequestMethod.POST,produces="text/json;charset=UTF-8")
	@ResponseBody
	public String appupdateWorksMethod(String appid,int userid,String works) throws Exception{
		map.clear();
		try {
			//调用修改
			if(this.userinfoService.updateUserWorks(userid, works)){
				app.setCode(200);
				app.setMessage("修改成功");
				map.put("work", works);
				app.setMap(map);
			}else{
				app.setCode(300);
				app.setMessage("修改失败");
			}
		} catch (Exception e) {
			app.setCode(400);
			app.setMessage("系统走神了");
		}
		return gson.toJson(app);
	}
	
	/***14.修改院校信息接口***/
	@RequestMapping(value="userupdateschool",method=RequestMethod.POST,produces="text/json;charset=UTF-8")
	@ResponseBody
	public String appupdateSchoolMethod(String appid,int userid,String school) throws Exception{
		map.clear();
		app.setMap(map);
		try {
			//调用修改
			if(this.userinfoService.updateUserSchool(userid, school)){
				app.setCode(200);
				app.setMessage("修改成功");
				map.put("work", school);
				app.setMap(map);
			}else{
				app.setCode(300);
				app.setMessage("修改失败");
			}
		} catch (Exception e) {
			app.setCode(400);
			app.setMessage("系统走神了");
		}
		return gson.toJson(app);
	}
	
	
	
	/**APP修改个人资料信息**/
	@RequestMapping(value="appuserupdate",method=RequestMethod.GET,produces="text/json;charset=UTF-8")
	@ResponseBody
	public String appUserUpdateData(Map<String,Object> map,String appid,AppReturnCodeInfo app,
			String username,String sign,String height,String workes,String city,String typevoice,String friendstate){
		try {
			map.clear();
			app.setMap(map);
			map=this.userinfoService.checkAppUserByAppID(appid, map);
			if(Integer.parseInt(map.get("code").toString())==200){
				//进行修改资料操作
				UsersInfo user=(UsersInfo) map.get("user");
				//user.setUsername(username);           //昵称
				user.setSign(sign); 				  //个性签名
				user.setHeight(height); 			  //身高
				//user.setConstellation(constellation); 
				user.setWorkes(workes);      		  //工作
				user.setTypes_id(typevoice); 		  //声音类型
				user.setFriendstate(friendstate); 	  //交友状态
				//执行修改操作  
				//开始反序列化
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
				
				if(this.userinfoService.updateAppUserByUser(user)){
					app.setCode(200);
					app.setMessage("个人资料修改成功");
					map.put("user", user2);
					app.setMap(map);
				}else{
					app.setCode(300);
					app.setMessage("个人资料修改失败");
				}
			}
		} catch (Exception e) {
			app.setCode(300);
			app.setMessage("个人资料修改失败");
		}
		return gson.toJson(app);
	}
	
	/***APP用户上传头像接口***/
	
	
	
	
	/***APP用户上传头像接口***/
	@RequestMapping(value="appuserphotoload",method=RequestMethod.POST,produces="text/json;charset=UTF-8")
	@ResponseBody
	public String appUserPhotoUpload(@RequestParam(value="fileName") MultipartFile fileName,String appid,HttpServletRequest request,HttpServletResponse response,AppReturnCodeInfo app,Map<String, Object> map) throws IOException{
		response.setContentType("text/html;charset=utf-8");   
		System.out.println("App调用头像上传");
		Map<String, Object> value = new HashMap<String, Object>();
		try {
			 BannerImageOSSTools ban=new BannerImageOSSTools();
		     String head = ban.updateHead(fileName, 4);//此处是调用上传服务接口，4是需要更新的userId 测试数据。
		     value.put("datasrc", head);
		     System.out.println(head);
		     app.setCode(200);
		     app.setMessage("头像上传成功");
		     
		     app.setMap(value);
		     System.out.println(appid);
		     //根据AppID修改头像信息
		     this.userinfoService.updateAppuserHeadPhoto(appid, head);
		} catch (IOException e) {
			 app.setCode(300);
			 app.setMessage("头像上传失败");
			 app.setMap(null);
		}
		return gson.toJson(app);
	}
	
	private String makeFileName(String filename,String id){  //2.jpg
        //为防止文件覆盖的现象发生，要为上传文件产生一个唯一的文件名
        return id + "_" + filename;
    }
	
	
	private String makePath(String filename,String savePath){
        //得到文件名的hashCode的值，得到的就是filename这个字符串对象在内存中的地址
        int hashcode = filename.hashCode();
        int dir1 = hashcode&0xf;  //0--15
        int dir2 = (hashcode&0xf0)>>4;  //0-15
        //构造新的保存目录
        String dir = savePath + "\\" + dir1 + "\\" + dir2;  //upload\2\3  upload\3\5
        //File既可以代表文件也可以代表目录
        File file = new File(dir);
        //如果目录不存在
        if(!file.exists()){
            //创建目录
            file.mkdirs();
        }
        return dir;
    }
	
	
	
	
	
	/*********7.修改交友状态的接口**********/
	@RequestMapping(value="updateuserfriendstate",method=RequestMethod.POST,produces="text/json;charset=UTF-8")
	@ResponseBody
	public String updateUserInfo(String appid,int userid,String friendstate) throws Exception{
		map.clear();
		app.setMap(map);
		try {
			if(this.userinfoService.updateFriendsState(userid, friendstate)){
				app.setCode(200);
				app.setMessage("修改成功");
				map.put("friendstate", friendstate);
				app.setMap(map);
			}else{
				app.setCode(300);
				app.setMessage("修改失败");
				map.put("friendstate", friendstate);
				app.setMap(map);
			}
		} catch (Exception e) {
			app.setCode(300);
			app.setMessage("修改失败");
			map.put("friendstate", friendstate);
			app.setMap(map);
		}
		return gson.toJson(app);
	}
	/*********7.修改交友状态的接口**********/
	
	
	/***8.修改用户昵称接口***/
	@RequestMapping(value="appupdateusername",method=RequestMethod.POST,produces="text/json;charset=UTF-8")
	@ResponseBody
	public String userUpdateUserName(String appid,int userid,String username) throws Exception{
		map.clear();
		try {
			if(username.length()>8){
				app.setCode(300);
				app.setMessage("不能超过8个字符");
			}else{
				//查询姓名是否存在
				List<UsersInfo> list=this.userinfoService.selectUserByUserName(username);
				if(list!=null && list.size()>0){
					app.setCode(301);
					app.setMessage("昵称已经被使用");
				}else{
					if(this.userinfoService.updateUserNameByID(userid, username)){
						app.setCode(200);
						app.setMessage("修改成功");
						map.put("username", username);
					}else{
						app.setCode(300);
						app.setMessage("修改失败");
					}
				}
			}
		} catch (Exception e) {
			app.setCode(300);
			app.setMessage("修改失败1-1");
		}
		app.setMap(map);
		return gson.toJson(app);
	}
	/***8.修改用户昵称接口***/
	
	
	/********9.用户上传极光推送的唯一手机标识********/
	@RequestMapping(value="appupdateonlyphone",method=RequestMethod.POST,produces="text/json;charset=UTF-8")
	@ResponseBody
	public String updateAndroidOrIosID(String appid,int userid,String phoneid) throws Exception{
		map.clear();
		try {
			if(this.userinfoService.updatePhoneOnlyID(phoneid, userid)){
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
		return gson.toJson(app);
	}
	/********9.用户上传极光推送的唯一手机标识********/
	
	/***10.iPhone的支付标识***/
	@RequestMapping(value="selectiphoneconfigure",method=RequestMethod.POST,produces="text/json;charset=UTF-8")
	@ResponseBody
	public String selectIphoneConfigure(String appid) throws Exception{
		map=new HashMap<>();
		try {
			app.setCode(200);
			app.setMessage("OK");
			map.put("limmit", 0);
			List<IphoneConfigure> listIphone=this.userinfoService.selectIphoneConfigure();
			if(listIphone!=null && listIphone.size()>0){
				map.put("limmit", listIphone.get(0).getIphone());
			}
		} catch (Exception e) {
			map.put("limmit", 1);
			app.setMessage("OK");
			app.setCode(300);
		}
		app.setMap(map);
		return gson.toJson(app);
	}
	/***10.iPhone的支付标识***/
	
	
	
}
