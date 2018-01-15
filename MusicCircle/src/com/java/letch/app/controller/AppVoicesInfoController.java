package com.java.letch.app.controller;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.java.letch.app.jpush.JpushClientUtil;
import com.java.letch.app.returnmodel.AppReturnCodeInfo;
import com.java.letch.model.AgreesInfo;
import com.java.letch.model.CollectionInfo;
import com.java.letch.model.CommentsInfo;
import com.java.letch.model.UserSetupInfo;
import com.java.letch.model.UsersInfo;
import com.java.letch.model.VoicesInfo;
import com.java.letch.model.view.VoicesUserView;
import com.java.letch.service.AgreesInfoService;
import com.java.letch.service.CollectionInfoService;
import com.java.letch.service.CommentsInfoService;
import com.java.letch.service.UserSetupInfoService;
import com.java.letch.service.UsersInfoService;
import com.java.letch.service.VoicesInfoService;
import com.java.letch.tools.BannerImageOSSTools;
import com.java.letch.tools.DateHelper;

import cn.jpush.api.push.PushResult;
import javazoom.jl.decoder.Bitstream;
import javazoom.jl.decoder.Header;

/***
 * APP的动态消息请求类
 * @author Administrator
 * 2017-10-20
 */
@Controller
public class AppVoicesInfoController {
	
	//动态信息业务操作类
	@Resource(name="VoicesInfoService")
	private VoicesInfoService voicesinfoservice;
	
	//用户业务操作类
	@Resource(name="UsersInfoService")
	private UsersInfoService userinfoService;
		
	
	//评论的信息业务类
	@Resource(name="CommentsInfoService")
	private CommentsInfoService commentsinfoService;
	
	//点赞数据操作类
	@Resource(name="AgreesInfoService")
	private AgreesInfoService agreesinfoService;
	
	//收藏动态的操作类
	@Resource(name="CollectionInfoService")
	private CollectionInfoService collectioninfoService;
	
	//声音信息操作类
	@Resource(name="VoicesInfoService")
	private VoicesInfoService voicesinfoService;  
	
	/**用户信息的设置数据操作类**/
	@Resource(name="UserSetupInfoService")
	private UserSetupInfoService usersetupinfoService;
	
	//JSON数据转换工具
	private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").serializeNulls().create();
	private Map<String, Object> map=new HashMap<>();
	private AppReturnCodeInfo app=new AppReturnCodeInfo();

	/***1.查看单条动态信息的详细信息***/
	@RequestMapping(value="voicesdetails",method=RequestMethod.POST,produces="text/json;charset=UTF-8")
	@ResponseBody
	public String selectVoicesById(String appid,int voicesid,int userid) throws Exception{
		map=new HashMap<>();
		try {
			//1.单条信息
			VoicesUserView voices=this.voicesinfoservice.selectVoicesUserTwo(voicesid,userid);
			app.setCode(200);
			app.setMessage("请求成功");
			map.put("voices", voices);
			//2.评论信息
			String thepagestr="1";
			int thepage=1;
			if(thepagestr==null || thepagestr==""){
				thepage=1;
			}else{
				thepage=Integer.parseInt(thepagestr);
			}
			Map<String, Object> map2=new HashMap<>();
			map2.put("nowpage", thepage);	//当前页数（INT）
			map2.put("pagenum",20);			//每页条数（INT）
			map2.put("bgstatrus", 1);		//状态,是否删除（INT）
			map2.put("userid",userid);		//用户ID
			//调用分页查询的方法
			map2=this.commentsinfoService.selectCommitsPage(map2, voicesid);
			map.put("commitmap", map2);
			app.setMap(map);
		} catch (Exception e) {
			map.clear();
			app.setMap(map);
			app.setCode(300);
			app.setMessage("请求失败");
		}
		return gson.toJson(app);
	}
	
	/***2.1.用户发表动态信息的接口【语音或者图片】***/
	@RequestMapping(value="voicesrelease",method=RequestMethod.POST,produces="text/json;charset=UTF-8")
	@ResponseBody
	public String insertIntoVoicesToDB(/*@RequestParam(value="voicesfile") MultipartFile voicesfile,	//语音文件
*/			/*@RequestParam(value="coverfile") MultipartFile coverfile,	//封面图片
*/			@RequestParam(value="photofile") MultipartFile[] photofile,	//照片组
			String appid,int userid,int types,String content,String address) throws Exception{
		try {
			System.out.println("+++++数量："+photofile.length+"+++++");
			map.clear();
			app.setMap(map);
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
			//上传封面图片
			/*if(coverfile!=null && coverfile.getSize()>0){
				BannerImageOSSTools ban=new BannerImageOSSTools();
			    String head1 = ban.updateHead(coverfile, 4,"voices",1);//此处是调用上传服务接口，4是需要更新的userId 测试数据。
			    voice.setPic(head1);
			}*/

			voice.setTypes_id(types+"");
				//遍历文件集合
				if(null != photofile && photofile.length > 0){ 
					//遍历并保存文件  
					List<String> list=new ArrayList<>();
		            for(MultipartFile file : photofile){  
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
		} catch (Exception e) {
			app.setCode(300);
			app.setMessage("发表失败1-1");
			System.out.println(e);
		}
		return gson.toJson(app);
	}
	
	
	/***2.2.用户发表动态信息的接口【文字】***/
	@RequestMapping(value="voicesreleasetwo",method=RequestMethod.POST,produces="text/json;charset=UTF-8")
	@ResponseBody
	public String insertIntoVoicesToTwoDB(
			String appid,int userid,String content,String address) throws Exception{
		try {
			map.clear();
			app.setMap(map);
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
			//上传封面图片
			/*if(coverfile!=null && coverfile.getSize()>0){
				BannerImageOSSTools ban=new BannerImageOSSTools();
			    String head1 = ban.updateHead(coverfile, 4,"voices",1);//此处是调用上传服务接口，4是需要更新的userId 测试数据。
			    voice.setPic(head1);
			}*/

			voice.setTypes_id(1+"");
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
		} catch (Exception e) {
			app.setCode(300);
			app.setMessage("发表失败1-1");
			System.out.println(e);
		}
		return gson.toJson(app);
	}
	
	
	private static HashMap<String, Lock> lockMap = new HashMap<String, Lock>();
	/***3.用户点赞的接口***/
	@RequestMapping(value="givethumbsup",method=RequestMethod.POST,produces="text/json;charset=UTF-8")
	@ResponseBody
	public String appuserInsertAgress(String appid,int userid,int voicesid) throws Exception{
		map.clear();
		app.setMap(map);
		try {
			//加锁
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
			
			/********【2017-12-04】加锁********/
			
			
			synchronized(lock) {
				//调用点赞方法
				AgreesInfo agress=new AgreesInfo();
				agress.setDate(DateHelper.getNewData());
				agress.setUsers_id(userid);
				agress.setVoices_id(voicesid);
				int num=this.agreesinfoService.insertAgressToDB(agress);
				if(num<0){
					app.setCode(301);
					app.setMessage("取消点赞");
					int dianzan=this.agreesinfoService.selectCountNumByID(userid, voicesid);
					map.put("count", dianzan);
					app.setMap(map);
					/**【推送评论信息】**/
					
				}else{
					app.setCode(200);
					app.setMessage("OK");
					map.put("count", num);
					app.setMap(map);
					/**【推送评论信息】**/
					//1.查询出用户的信息
					UserSetupInfo userset=this.usersetupinfoService.selectUserSetupByUserID(userid,2);
					if(userset==null){
						VoicesUserView voice=this.voicesinfoService.selectVoicesUser(voicesid);
						UsersInfo uu=this.userinfoService.selectAppUserByID(voice.getUsers_id());
						if(uu!=null && uu.getAndroid_ios()!=null){
							String ALERT = "评论通知：你的动态消息被点赞了";      
					        int result = JpushClientUtil.sendToRegistrationId(uu.getAndroid_ios(), ALERT, "动态点赞", ALERT, voicesid+"");
					        if(result>0){
					            System.out.println("ok");
					        }else{
					        	System.out.println("no");
					        }
						}
						/**【推送评论信息】**/
					}else{
						if(userset.getSetid()==1){
							VoicesUserView voice=this.voicesinfoService.selectVoicesUser(voicesid);
							UsersInfo uu=this.userinfoService.selectAppUserByID(voice.getUsers_id());
							if(uu!=null && uu.getAndroid_ios()!=null){
								String ALERT = "评论通知：你的动态消息被点赞了";      
						        int result = JpushClientUtil.sendToRegistrationId(uu.getAndroid_ios(), ALERT, "动态点赞", ALERT, voicesid+"");
						        if(result>0){
						            System.out.println("ok");
						        }else{
						        	System.out.println("no");
						        }
							}
							/**【推送评论信息】**/
						}
					}
					/**【推送评论信息】**/
					
					try {
						//2017-12-11 点赞入库评论信息
						CommentsInfo comm=new CommentsInfo();
						comm.setDate(DateHelper.getNewData());		//添加时间
						comm.setUsers_id(userid); 					//用户ID
						comm.setVoices_id(voicesid); 				//动态的ID
						comm.setInfo("");
						comm.setType(4); 		//评价类型
						comm.setTapcount(0);    //偷听量
						comm.setLength("");
						comm.setReplyuser(-1);
						this.commentsinfoService.insertIntoCommentsOne(comm);
					} catch (Exception e) {
						System.out.println(e);
					}
				}
			}
			
		} catch (Exception e) {
			app.setCode(300);
			app.setMessage("NO");
			app.setMap(map);
		}
		return gson.toJson(app);
	}
	/***3.用户点赞的接口***/
	
	/***4.动态的收藏***/
	@RequestMapping(value="giveconllection",method=RequestMethod.POST,produces="text/json;charset=UTF-8")
	@ResponseBody
	public String insertConllectionToDB(String appid,int userid,int voicesid) throws Exception{
		
		map.clear();
		app.setMap(map);
		try {
			CollectionInfo coll=new CollectionInfo();
			coll.setUserid(userid);
			coll.setVoicesid(voicesid);
			coll.setDate(DateHelper.getNewData());
			int num=this.collectioninfoService.insertCollectionToDB(coll);
			if(num<0){
				app.setCode(301);
				app.setMessage("OK");
				app.setMap(map);
			}else{
				app.setCode(200);
				app.setMessage("OK");
				map.put("count", num);
				app.setMap(map);
				/**【推送评论信息】**/
				//1.查询出用户的信息
				
				UserSetupInfo userset=this.usersetupinfoService.selectUserSetupByUserID(userid,1);
				if(userset==null){
					VoicesUserView voice=this.voicesinfoService.selectVoicesUser(voicesid);
					UsersInfo uu=this.userinfoService.selectAppUserByID(voice.getUsers_id());
					if(uu!=null && uu.getAndroid_ios()!=null){
						String ALERT = "评论通知：你的动态被收藏了";       
				        int result = JpushClientUtil.sendToRegistrationId(uu.getAndroid_ios(), ALERT, "动态点赞", ALERT, voicesid+"");
				        if(result>0){
				            System.out.println("ok");
				        }else{
				        	System.out.println("no");
				        }
					}
					/**【推送评论信息】**/
				}else{
					if(userset.getSetid()==1){
						VoicesUserView voice=this.voicesinfoService.selectVoicesUser(voicesid);
						UsersInfo uu=this.userinfoService.selectAppUserByID(voice.getUsers_id());
						if(uu!=null && uu.getAndroid_ios()!=null){
							String ALERT = "评论通知：你的动态被收藏了";       
					        int result = JpushClientUtil.sendToRegistrationId(uu.getAndroid_ios(), ALERT, "动态点赞", ALERT, voicesid+"");
					        if(result>0){
					            System.out.println("ok");
					        }else{
					        	System.out.println("no");
					        }
						}
						/**【推送评论信息】**/
					}
				}
				/**【推送评论信息】**/
			}
		} catch (Exception e) {
			app.setCode(300);
			app.setMessage("NO");
			app.setMap(map);
		}
		return gson.toJson(app);
	}
	/***4.动态的收藏***/
	
	/*******5.获取评论的信息的接口******/
	@RequestMapping(value="appgetcommitpage",method=RequestMethod.POST,produces="text/json;charset=UTF-8")
	@ResponseBody
	public String selectCommitsUserViewPage(String appid,int userid,int voicesid,int nowpage) throws Exception{
		map=new HashMap<>();
		app.setMap(map);
		try {
			if(nowpage<=0){
				nowpage=1;
			}
			Map<String, Object> map2=new HashMap<>();
			map2.put("nowpage", nowpage);	//当前页数（INT）
			map2.put("pagenum",20);			//每页条数（INT）
			map2.put("bgstatrus", 1);		//状态,是否删除（INT）
			map2.put("userid", userid);
			//调用分页查询的方法
			map2=this.commentsinfoService.selectCommitsPage(map2, voicesid);
			app.setMap(map2);
			app.setCode(200);
			app.setMessage("请求成功");
		} catch (Exception e) {
			System.out.println(e);
			app.setCode(300);
			app.setMessage("请求失败1-1");
		}
		return gson.toJson(app);
	}
	/*******5.获取评论的信息的接口******/
	
	/*******6.查看评论、回复的消息******/
	@RequestMapping(value="alonereplypage",method=RequestMethod.POST,produces="text/json;charset=UTF-8")
	@ResponseBody
	public String selectCommentVoicesPage(String appid,int userid,int nowpage) throws Exception{
		map=new HashMap<>();
		app.setMap(map);
		try {
			Map<String, Object> map2=new HashMap<>();
			map2.put("nowpage", nowpage);	//当前页数（INT）
			map2.put("pagenum",20);			//每页条数（INT）
			map2.put("bgstatrus", 1);		//状态,是否删除（INT）
			//调用分页查询的方法
			map2=this.commentsinfoService.selectCommitsUserViceosView(map2, userid);
			app.setMap(map2);
			app.setCode(200);
			app.setMessage("请求成功");
		} catch (Exception e) {
			System.out.println(e);
			app.setCode(300);
			app.setMessage("请求失败1-1");
		}
		return gson.toJson(app);
	}
	/*******6.查看评论、回复的消息******/
	
}
