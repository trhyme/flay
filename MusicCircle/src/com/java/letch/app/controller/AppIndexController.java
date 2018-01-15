package com.java.letch.app.controller;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.util.WebUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.java.letch.app.returnmodel.AppReturnCodeInfo;
import com.java.letch.model.AddressInfo;
import com.java.letch.model.BannerInfo;
import com.java.letch.model.FollowsInfo;
import com.java.letch.model.IntimacyInfo;
import com.java.letch.model.MemberInfo;
import com.java.letch.model.RecentVisitInfo;
import com.java.letch.model.ToexamineInfo;
import com.java.letch.model.UsersInfo;
import com.java.letch.model.VoicesInfo;
import com.java.letch.service.AddressInfoService;
import com.java.letch.service.BannerInfoService;
import com.java.letch.service.FollowsInfoService;
import com.java.letch.service.IntimacyInfoService;
import com.java.letch.service.MemberInfoService;
import com.java.letch.service.RecentVisitInfoService;
import com.java.letch.service.ToexamineInfoService;
import com.java.letch.service.UsersInfoService;
import com.java.letch.service.VoicesInfoService;
import com.java.letch.tools.BannerImageOSSTools;
import com.java.letch.tools.DateHelper;
import com.java.letch.tools.RangeMatchUtilTools;
import com.mongodb.util.Hash;

import io.rong.RongCloud;
import io.rong.configure.ConfigureEntity;
import io.rong.models.CheckOnlineResult;
import javazoom.jl.decoder.Bitstream;
import javazoom.jl.decoder.Header;

/***
 * APP首页的数据接口
 * @author Administrator
 * 2017-09-19
 */
@Controller
public class AppIndexController {
	
	//首页Banner图信息类
	@Resource(name="BannerInfoService")
	private BannerInfoService bannerInfoService;
	
	//动态信息业务操作类
	@Resource(name="VoicesInfoService")
	private VoicesInfoService voicesinfoservice;
	
	//用户信息的业务处理类
	@Resource(name="UsersInfoService")
	private UsersInfoService usersinfoService;
	
	//用户的会员消息
	@Resource(name="MemberInfoService")
	private MemberInfoService memberinfoService;
	
	//用户认证信息
	@Resource(name="ToexamineInfoService")
	private ToexamineInfoService toexamineinfoService;
	
	//用户访问信息类
	@Resource(name="RecentVisitInfoService")
	private RecentVisitInfoService recentvisitinfoService;
	
	//亲密度数据操作类
	@Resource(name="IntimacyInfoService")
	private IntimacyInfoService intimacyinfoService;	
	
	//4.用户地理位置信息类
	@Resource(name="AddressInfoService")
	private AddressInfoService addressinfoService;
	
	@Resource(name="FollowsInfoService")
	private FollowsInfoService followsinfoService;		//用户关注信息
	
	//JSON数据转换工具
	private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").serializeNulls().create();
	private Map<String, Object> map=new HashMap<>();
	private AppReturnCodeInfo app=new AppReturnCodeInfo();
	
	/**1.APP首页数据接口**/
	@RequestMapping(value="appindexdata",method=RequestMethod.GET,produces="text/json;charset=UTF-8")
	@ResponseBody
	public String appIndexPageData(Map<String,Object> map,String appid,int userid) throws Exception{
		try {
			//1.查询出首页Banner图信息4张
			List<BannerInfo> bannerList=this.bannerInfoService.selectBannerList(4);
			map.clear();
			map.put("bannerList", bannerList);
			//2.查询出首页动态信息数据
			Map<String, Object> map2=new HashMap<>();
			map2.put("nowpage", 1);	//当前页数（INT）
			map2.put("pagenum", 10);			//每页条数（INT）
			map2.put("userid", userid);
			//调用分页查询的方法
			map2=this.usersinfoService.selectAppIndexNowVoices(map2);
			app.setCode(200);
			app.setMessage("数据请求成功");
			map.put("newindex", map2);
			app.setMap(map);
			
		} catch (Exception e) {
			app.setCode(300);
			app.setMessage("数据请求失败");
		}
		return gson.toJson(app);
	}
	
	
	/***2.首页的关注信息接口***/
	@RequestMapping(value="appindexfollow",method=RequestMethod.GET,produces="text/json;charset=UTF-8")
	@ResponseBody
	public String appIndexFollowData(String appid,int userid,int nowpage) throws Exception{
		//1.根据APP_id编号查询出用户信息
		Map<String, Object> map=new HashMap<>();
		app=new AppReturnCodeInfo();
		try {
			if(nowpage<=0){
				nowpage=1;
			}
			map.put("nowpage", nowpage);	//当前页数（INT）
			map.put("pagenum", 10);			//每页条数（INT）
			map.put("userid", userid);
			//调用分页查询的方法
			map=this.usersinfoService.selectAppIndexFollowVoices(map, userid+"");
			app.setCode(200);
			app.setMessage("数据请求成功");
			app.setMap(map);
		} catch (Exception e) {
			app.setCode(300);
			app.setMessage("数据请求失败");
		}
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").serializeNulls().create();
		return gson.toJson(app, AppReturnCodeInfo.class);
	}
	
	/*****3.首页最新消息的动态数据接口****/
	@RequestMapping(value="appindexinfomation",method=RequestMethod.GET,produces="text/json;charset=UTF-8")
	@ResponseBody
	public String appIndexNewInfoData(String appid,int userid,int nowpage) throws Exception{
		map.clear();
		app=new AppReturnCodeInfo();
		try {
			if(nowpage<=0){
				nowpage=1;
			}
			
			map.put("nowpage", nowpage);	//当前页数（INT）
			map.put("pagenum", 10);			//每页条数（INT）
			map.put("userid", userid);		//用户的编号
			//调用分页查询的方法
			map=this.usersinfoService.selectAppIndexNowVoices(map);
			app.setCode(200);
			app.setMessage("数据请求成功");
			app.setMap(map);
		} catch (Exception e) {
			app.setCode(300);
			app.setMessage("数据请求失败");
			app.setMap(map);
		}
		return gson.toJson(app, AppReturnCodeInfo.class);
	}
	/*****3.首页最新消息的动态数据接口****/
	
	/***4.热门的动态的接口***/
	@RequestMapping(value="voicesindexhot",method=RequestMethod.POST,produces="text/json;charset=UTF-8")
	@ResponseBody
	public String appIndexHotVoices(String appid,int userid,int nowpage) throws Exception{
		map.clear();
		app=new AppReturnCodeInfo();
		try {
			if(nowpage<=0){
				nowpage=1;
			}
			map.put("nowpage", nowpage);	//当前页数（INT）
			map.put("pagenum", 10);			//每页条数（INT）
			map.put("userid", userid);		//用户的编号
			//调用分页查询的方法
			map=this.usersinfoService.appIndexHot(map);
			app.setCode(200);
			app.setMessage("数据请求成功");
			app.setMap(map);
		} catch (Exception e) {
			app.setCode(300);
			app.setMessage("数据请求失败");
			app.setMap(map);
		}
		return gson.toJson(app);
	}
	/***4.热门的动态的接口***/
	
	
	/***5.用户查看自己的发布动态***/
	@RequestMapping(value="myselfvoices",method=RequestMethod.POST,produces="text/json;charset=UTF-8")
	@ResponseBody
	public String appuserLookVoices(String appid,int userid,int nowpage) throws Exception{
		try {
			if(nowpage<=0){
				nowpage=1;
			}
			map.clear();
			map.put("nowpage", nowpage);	//当前页数（INT）
			map.put("pagenum", 10);			//每页条数（INT）
			map.put("userid", userid);		//用户的编号
			//调用分页查询的方法
			map=this.usersinfoService.selectVoicesMyself(map, userid);
			app.setCode(200);
			app.setMessage("数据请求成功");
			app.setMap(map);
		} catch (Exception e) {
			app.setCode(300);
			app.setMessage("数据请求失败");
			app.setMap(map);
		}
		return gson.toJson(app);
	}
	/***5.用户查看自己的发布动态***/
	
	
	/******5-1.用户查看别人的动态信息*****/
	@RequestMapping(value="yourselfvoices",method=RequestMethod.POST,produces="text/json;charset=UTF-8")
	@ResponseBody
	public String appuserLookYourVoices(String appid,int userid,int seeuserid,int nowpage) throws Exception{
		try {
			if(nowpage<=0){
				nowpage=1;
			}
			map.clear();
			map.put("nowpage", nowpage);	//当前页数（INT）
			map.put("pagenum", 10);			//每页条数（INT）
			map.put("userid", userid);		//用户的编号
			//调用分页查询的方法
			map=this.usersinfoService.selectVoicesYour(map, seeuserid, userid);
			app.setCode(200);
			app.setMessage("数据请求成功");
			app.setMap(map);
		} catch (Exception e) {
			app.setCode(300);
			app.setMessage("数据请求失败");
			app.setMap(map);
		}
		return gson.toJson(app);
	}
	/******5-1.用户查看别人的动态信息*****/
	
	
	/***6.用户删除自己的动态信息***/
	@RequestMapping(value="deleteonevoice",method=RequestMethod.POST,produces="text/json;charset=UTF-8")
	@ResponseBody
	public String deleteVoicesByID(String appid,int userid,int voicesid) throws Exception{
		try {
			map.clear();
			app.setMap(map);
			if(this.usersinfoService.deleteVoicesByID(voicesid, userid)){
				app.setCode(200);
				app.setMessage("删除成功");
			}else{
				app.setCode(300);
				app.setMessage("删除失败");
			}
		} catch (Exception e) {
			app.setCode(300);
			app.setMessage("删除失败");
		}
		return gson.toJson(app);
	}
	/***6.用户删除自己的动态信息***/
	
	
	/***7.查看发表动态的用户的信息***/
	@RequestMapping(value="userseeuser",method=RequestMethod.POST,produces="text/json;charset=UTF-8")
	@ResponseBody
	public String appuserSeeOtherUser(String appid,int userid,int seeuserid) throws Exception{
		map.clear();
		app.setMap(map);
		try {
			//1.根据编号查询出用户的基本信息
			UsersInfo user=this.usersinfoService.selectAppUserByID(seeuserid);
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
			map.put("seeuser", user2);
			//2.会员
			MemberInfo member=this.memberinfoService.selectMemberOneByID(seeuserid);
			//计算会员是否到期
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
  					map.put("member",member!=null?member.getMegid():0);		//会员信息
  				}else{
  					member.setStrutus(0);
  					member.setDaysnum("0");
  					map.put("member",0);		//会员信息
  				}
			}else{
				map.put("member",0);		//会员信息
			}
			
			
			//3.查看动态信息
			Map<String, Object> mapvoi=new HashMap<>();
			mapvoi=this.voicesinfoservice.selectVoicesByUserid(seeuserid);
			map.put("voicescount", mapvoi.get("voicescount"));	//总条数
			map.put("onevioces", mapvoi.get("voices"));
			//4.查看照片认证信息
			ToexamineInfo toexam=this.toexamineinfoService.selectToexamByUserid(seeuserid);
			map.put("toexamphoto", toexam!=null?toexam.getPiclist():"");
			//5.用户是否在线
			// 检查用户在线状态 方法 
			RongCloud rongCloud = RongCloud.getInstance(ConfigureEntity.appkey, ConfigureEntity.appSecret);
			CheckOnlineResult userCheckOnlineResult = rongCloud.user.checkOnline(seeuserid+"");
			System.out.println("checkOnline:  " + userCheckOnlineResult.toString());
			map.put("useronline", userCheckOnlineResult.getStatus());
			//6.用户之间的距离
			AddressInfo admy=this.addressinfoService.selectAddressByUserID(userid);
			AddressInfo adyour=this.addressinfoService.selectAddressByUserID(seeuserid);
			double range=100D;
			if(admy!=null && adyour!=null){
				range=RangeMatchUtilTools.GetDistance(Double.parseDouble(admy.getLad()), Double.parseDouble(admy.getLgd()), Double.parseDouble(adyour.getLad()), Double.parseDouble(adyour.getLgd()));
				range+=100;
			}		
			map.put("distance", range/1000D);
			
			//7.自己是否为会员
			MemberInfo member2=this.memberinfoService.selectMemberOneByID(userid);
			//计算会员是否到期
			if(member2!=null){
				//判断会员是否过期
				//计算会员天数
  				//1.会员开始时间--->到当前时间的天数
  				int numm1=DateHelper.getDaysBetween(member2.getBegintime(), DateHelper.getNewData());
  				//2.会员开始时间--->会员到期时间
  				int numm2=DateHelper.getDaysBetween(member2.getBegintime(), member2.getEndestime());
  				if(numm1<=numm2){   		//1.会员还未到期
  					member2.setStrutus(1);
  					map.put("mymember",member2!=null?member2.getMegid():0);		//会员信息
  				}else{
  					member2.setStrutus(0);
  					map.put("mymember",0);		//会员信息
  				}
			}else{
				map.put("mymember",0);		//会员信息
			}
			
			//8.被查看用户的声音长 度
			try {
				URLConnection con = null;
			    URL urlfile = new URL(user.getVoicesurl());
			    try {
			        con = urlfile.openConnection();
			    } catch (IOException e) {
			    	System.out.println(e);
			    	e.printStackTrace();
			    }
			    int b = con.getContentLength();//
			    BufferedInputStream bis = new BufferedInputStream(con.getInputStream());
			    Bitstream bt = new Bitstream(bis);
			    Header h = bt.readFrame();
			    int time = (int) h.total_ms(b);
			    map.put("voiceslength", (time/1000+1)+"");
			} catch (Exception e) {
				map.put("voiceslength", 0);
			}
			
			
			//9.用户是否关注
			//判断我是否关注对方
			FollowsInfo follow1=this.followsinfoService.selectFollowsByTwoID(userid,seeuserid);
			if(follow1!=null){
				map.put("checkfollow", 1);
			}else{
				map.put("checkfollow", 0);
			}
			
			app.setCode(200);
			app.setMap(map);
			app.setMessage("请求成功");
		} catch (Exception e) {
			app.setCode(200);
			app.setMessage("请求成功");
		}finally{
			/***添加用户数据访问记录***/
			RecentVisitInfo receninfo=new RecentVisitInfo();
			receninfo.setUserid(userid);
			receninfo.setUserseeid(seeuserid);
			receninfo.setAddtimes(DateHelper.getNewData());
			this.recentvisitinfoService.insertIntoRecentInfo(receninfo);
			/***添加用户数据访问记录***/
			
			/****增加亲密度****/
			IntimacyInfo tim=new IntimacyInfo();
			tim=this.intimacyinfoService.selectIntimacyInfoOne(userid, seeuserid);
			if(tim!=null){
				tim.setIntimacye(tim.getIntimacye()+5);
				this.intimacyinfoService.insertOrUpdateIntimacy(tim);
			}
			/****增加亲密度****/
		}
		
		return gson.toJson(app);
	}
	/***7.查看发表动态的用户的信息***/
	
	
	/*******8.查看用户的发表的动态信息接口*******/
	@RequestMapping(value="appseeothervoices",method=RequestMethod.POST,produces="text/json;charset=UTF-8")
	@ResponseBody
	public String appuserOtherUserVoices(Map<String,Object> map,String appid,int userid,int seeuserid) throws Exception{
		try {
			//2.查询出首页动态信息数据
			Map<String, Object> map2=new HashMap<>();
			map2.put("nowpage", 1);	//当前页数（INT）
			map2.put("pagenum", 10);			//每页条数（INT）
			map2.put("userid", userid);
			//map2.put("seeuserid", seeuserid);	//被查看用户的ID
			//调用分页查询的方法
			map2=this.usersinfoService.selectAppIndexSeeUserVoices(map2, seeuserid);
			app.setCode(200);
			app.setMessage("请求成功");
			app.setMap(map2);
		} catch (Exception e) {
			app.setCode(300);
			app.setMessage("请求失败");
		}
		return gson.toJson(app);
	}
	
	/*******8.查看用户的发表的动态信息接口*******/
	
	
	private static HashMap<String, Lock> lockMap = new HashMap<String, Lock>();
	/********9.发表动态信息接口【图文、语音一同发表】********/
	@RequestMapping(value="userpublishstatement",method=RequestMethod.POST,produces="text/json;charset=UTF-8")
	@ResponseBody
	public String fileUplowdMethod(HttpServletRequest request,HttpServletResponse response,
			String appid,int userid,int types,String content,String address) throws Exception{
		map.clear();
		app.setMap(map);
		try {
			VoicesInfo voice=new VoicesInfo();
			voice.setPlaycount(0);
			voice.setWork(1);
			voice.setDate(DateHelper.getNewData());
			voice.setAddress(address);
			//voice.setTitle(title);
			voice.setInfo(content);
			voice.setUsers_id(userid);
			voice.setCategorys_id(0);
			voice.setSort(0);
			voice.setFabulous(0);
			voice.setCollection(0);
			voice.setCommentnum(0);
			voice.setTypes_id(types+"");
			// 检测是否为上传请求
		    String contentType = request.getContentType();
		    if (contentType != null && contentType.toLowerCase().startsWith("multipart/")) {
		        MultipartHttpServletRequest multipartRequest =
		                WebUtils.getNativeRequest(request, MultipartHttpServletRequest.class);
		        List<MultipartFile> files = new ArrayList<>();
		        files=multipartRequest.getFiles("files");
		        if(files!=null && files.size()>0){
		        	//遍历并保存文件  
					List<String> list=new ArrayList<>();
		            for(MultipartFile file : files){  
		            	String originalFilename = file.getOriginalFilename();
		          	  	String substring = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
		            	System.out.println("文件后缀："+substring);
		            	if(substring.equals(".mp3")){
		            		BannerImageOSSTools ban=new BannerImageOSSTools();
						    String head2 = ban.updateHead(file, 4,"voices",1);//此处是调用上传服务接口，4是需要更新的userId 测试数据。
						    /*//获取长度
					        CommonsMultipartFile cf= (CommonsMultipartFile)file; 
					        DiskFileItem fi = (DiskFileItem)cf.getFileItem(); 
					        File f = fi.getStoreLocation();
					        Clip clip = AudioSystem.getClip();
					        AudioInputStream ais = AudioSystem.getAudioInputStream((InputStream) file);
					        clip.open(ais);
					        System.out.println("时长："+clip.getMicrosecondLength() / 1000000D + " s" );//获取音频文件时
						    voice.setLength(clip.getMicrosecondLength() / 1000000D+"");*/
						    voice.setVoiceurl(head2);
						    URLConnection con = null;
						    URL urlfile = new URL(head2);
						    try {
						        con = urlfile.openConnection();
						    } catch (IOException e) {
						    	System.out.println(e);
						    	e.printStackTrace();
						    }
						    int b = con.getContentLength();//
						    BufferedInputStream bis = new BufferedInputStream(con.getInputStream());
						    Bitstream bt = new Bitstream(bis);
						    Header h = bt.readFrame();
						    int time = (int) h.total_ms(b);
						    voice.setLength((time/1000+1)+"");
						    
		            	}else{
		            		BannerImageOSSTools ban=new BannerImageOSSTools();
			    		    String head3 = ban.updateHead(file, 4);//此处是调用上传服务接口，4是需要更新的userId 测试数据。
			    		    list.add(head3);
		            	}
		            }  
		            if(list.size()>0){
		            	String str=gson.toJson(list);
		            	str=str.replace("\"", "");
		            	str=str.replace("[", "");
		            	str=str.replace("]", "");
		            	voice.setPhoto(str);
		            }
		        }
		        
		        /********【2017-12-04】加锁********/
				//同步锁
				Lock lock = lockMap.get(userid);
				if(null==lock){
					synchronized(lockMap) {
		                lock = lockMap.get(userid);
		                if (null == lock) {
		                    lock = new ReentrantLock();
		                    lockMap.put(userid+"", lock);
		                }
					}
				}
		        
				synchronized(lock) {
					//添加数据
					if(this.voicesinfoservice.insertVoicesToDB(voice)){
						app.setCode(200);
						app.setMessage("发表成功");
						map.put("voices", voice);
						app.setMap(map);
					}else{
						app.setCode(300);
						app.setMessage("发表失败");
						map.put("voices", voice);
						app.setMap(map);
					}
			        System.out.println("数量："+files.size());
				}
		    }
		} catch (Exception e) {
			app.setCode(301);
			app.setMessage("发表失败1-1");
			System.out.println(e);
		}
		return gson.toJson(app);
	}
	/********9.发表动态信息接口【图文、语音一同发表】********/
	
	
	
	
	
	/*********10.【关注】+【最新】+【热门】统一接口********/
	@RequestMapping(value="userindexsquare",method=RequestMethod.POST,produces="text/json;charset=UTF-8")
	@ResponseBody
	public String indexSquareVoices(String appid,int userid,int nowpage,int types) throws Exception{
		map.clear();
		app=new AppReturnCodeInfo();
		app.setMap(map);
		try {
			if(types==1){			//1：最新
				return appIndexNewInfoData(appid, userid, nowpage);
			}else if(types==2){		//2：关注
				return appIndexFollowData(appid, userid, nowpage);
			}else if(types==3){		//3：热门
				return appIndexHotVoices(appid, userid, nowpage);
			}else if(types==4){		//4：收藏
				return appIndexCollectionInfoData(appid, userid, nowpage);
			}
		} catch (Exception e) {
			app.setCode(300);
			app.setMessage("获取失败");
		}
		return appIndexNewInfoData(appid, userid, nowpage);
	}
	/*********10.【关注】+【最新】+【热门】统一接口********/
	
	/***我的收藏【收藏】***/
	public String appIndexCollectionInfoData(String appid,int userid,int nowpage) throws Exception{
		map.clear();
		app=new AppReturnCodeInfo();
		try {
			if(nowpage<=0){
				nowpage=1;
			}
			
			map.put("nowpage", nowpage);	//当前页数（INT）
			map.put("pagenum", 10);			//每页条数（INT）
			map.put("userid", userid);		//用户的编号
			//调用分页查询的方法
			map=this.usersinfoService.selectCollectionVoicesPage(map,userid);
			app.setCode(200);
			app.setMessage("数据请求成功");
			app.setMap(map);
		} catch (Exception e) {
			app.setCode(300);
			app.setMessage("数据请求失败");
			app.setMap(map);
		}
		return gson.toJson(app, AppReturnCodeInfo.class);
	}
	/***我的收藏【收藏】***/
	
	
	/*************11.【广场Banner图接口】**************/
	@RequestMapping(value="indexbannerlist",method=RequestMethod.POST,produces="text/json;charset=UTF-8")
	@ResponseBody
	public String appIndexBannerMethod(String appid,int userid) throws Exception{
		map.clear();
		app=new AppReturnCodeInfo();
		try {
			//1.查询出首页Banner图信息4张
			List<BannerInfo> bannerList=this.bannerInfoService.selectBannerList(4);
			map.clear();
			map.put("bannerList", bannerList);
			app.setCode(200);
			app.setMessage("数据请求成功");
			app.setMap(map);
		} catch (Exception e) {
			app.setCode(300);
			app.setMessage("数据请求失败");
		}
		return gson.toJson(app);
	}
	/*************11.【广场Banner图接口】**************/
	
	
	
}
