package com.java.letch.app.controller;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

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
import com.java.letch.model.CommentsInfo;
import com.java.letch.model.IntimacyInfo;
import com.java.letch.model.UserSetupInfo;
import com.java.letch.model.UsersInfo;
import com.java.letch.model.VoicesInfo;
import com.java.letch.model.view.CommitsUserView;
import com.java.letch.model.view.VoicesUserView;
import com.java.letch.service.CommentsInfoService;
import com.java.letch.service.IntimacyInfoService;
import com.java.letch.service.UserSetupInfoService;
import com.java.letch.service.UsersInfoService;
import com.java.letch.service.VoicesInfoService;
import com.java.letch.tools.BannerImageOSSTools;
import com.java.letch.tools.DateHelper;

import cn.jpush.api.push.PushResult;
import javazoom.jl.decoder.Bitstream;
import javazoom.jl.decoder.Header;

/***
 * 评论信息的APP请求接口请求
 * @author Administrator
 * 2017-10-20
 */
@Controller
public class AppCommentsInfoController {
	
	@Resource(name="CommentsInfoService")
	private CommentsInfoService commentsinfoService;		//评论信息的业务操作类
	
	@Resource(name="VoicesInfoService")
	private VoicesInfoService voicesinfoService;			//声音动态信息数据操作类
	
	/**用户信息的设置数据操作类**/
	@Resource(name="UserSetupInfoService")
	private UserSetupInfoService usersetupinfoService;
	
	//亲密度数据操作类
	@Resource(name="IntimacyInfoService")
	private IntimacyInfoService intimacyinfoService;	
	
	//用户业务操作类
	@Resource(name="UsersInfoService")
	private UsersInfoService userinfoService;
	
	//JSON数据转换工具
	private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").serializeNulls().create();
	private Map<String, Object> map=new HashMap<>();
	private AppReturnCodeInfo app=new AppReturnCodeInfo();
		
	/***1.添加评论的请求【文字评论】***/
	@RequestMapping(value="usercommentinto",method=RequestMethod.POST,produces="text/json;charset=UTF-8")
	@ResponseBody
	public String appuserInsertComment(String appid,int userid,
			String content,int voicesid,int replyuserid) throws Exception{
		try {
			map.clear();
			app.setMap(map);
			CommentsInfo comm=new CommentsInfo();
			if(content!="" && content!=null){
				comm.setInfo(content);
			}
			comm.setDate(DateHelper.getNewData());		//添加时间
			comm.setUsers_id(userid); 					//用户ID
			comm.setVoices_id(voicesid); 				//动态的ID
			comm.setType(1); 		//评价类型
			comm.setTapcount(0);    //偷听量
			comm.setLength("0");
			comm.setReplyuser(replyuserid); 	//是否回复
			
//			//遍历文件集合
//			if(null != filename && filename.length > 0){ 
//				//遍历并保存文件  
//	            for(MultipartFile file : filename){  
//	            	BannerImageOSSTools ban=new BannerImageOSSTools();
//	    		    String head = ban.updateHead(file, 4);//此处是调用上传服务接口，4是需要更新的userId 测试数据。
//	    		    comm.setType(3);
//	    		    comm.setVoiceurl(head); 		//设置声评的路径
//	            }  
//			}
			//执行入库操作 
			if(this.commentsinfoService.insertIntoCommentsOne(comm)){
				VoicesUserView voice=this.voicesinfoService.selectVoicesUser(voicesid);
				System.out.println("1新增评论编号："+comm.getId());
				//查询出添加的新数据
				CommitsUserView comment=this.commentsinfoService.seelctCommitsUserViewOne(comm.getId());
				map.put("commentmap", comment);
				app.setMap(map);
				//跟新动态的评论数
				int count=this.commentsinfoService.selectCommentCountById(voicesid);
				if(count>=0){
					//跟新
					this.commentsinfoService.updateVoicesCommentnumById(voicesid, count);
					
					/**【推送评论信息】**/
					//1.查询出用户的信息
					UserSetupInfo userset=this.usersetupinfoService.selectUserSetupByUserID(userid,2);
					if(userset==null){
						//查询用户信息
						UsersInfo uu=this.userinfoService.selectAppUserByID(voice.getUsers_id());
						if(uu!=null && uu.getAndroid_ios()!=null){
							String ALERT = "评论通知：你的动态消息被评论了"; 
					        int result = JpushClientUtil.sendToRegistrationId(uu.getAndroid_ios(), ALERT, "动态点赞", ALERT, voicesid+"");
					        if(result>0){
					            System.out.println("ok");
					        }else{
					        	System.out.println("no");
					        }
						}
						//2.回复的信息
						if(replyuserid>0){
							UsersInfo uu2=this.userinfoService.selectAppUserByID(userid);
							if(uu2!=null && uu2.getAndroid_ios()!=null){
								String ALERT = "评论通知：你的动态消息被评论了"; 
						        int result = JpushClientUtil.sendToRegistrationId(uu2.getAndroid_ios(), ALERT, "动态点赞", ALERT, voicesid+"");
						        if(result>0){
						            System.out.println("ok");
						        }else{
						        	System.out.println("no");
						        }
							}
						}
						//3.
						if(voice.getUsers_id()!=replyuserid){
							UsersInfo uu3=this.userinfoService.selectAppUserByID(replyuserid);
							if(uu3!=null && uu3.getAndroid_ios()!=null){
								String ALERT = "评论通知：你的动态消息被评论了"; 
						        int result = JpushClientUtil.sendToRegistrationId(uu3.getAndroid_ios(), ALERT, "动态点赞", ALERT, voicesid+"");
						        if(result>0){
						            System.out.println("ok");
						        }else{
						        	System.out.println("no");
						        }
							}
						}
				        
					/**【推送评论信息】**/
					}else{
						if(userset.getSetid()==1){
							UsersInfo uu=this.userinfoService.selectAppUserByID(voice.getUsers_id());
							if(uu!=null && uu.getAndroid_ios()!=null){
								String ALERT = "评论通知：你的动态消息被评论了";    
						        int result = JpushClientUtil.sendToRegistrationId(uu.getAndroid_ios(), ALERT, "动态点赞", ALERT, voicesid+"");
						        if(result>0){
						            System.out.println("ok");
						        }else{
						        	System.out.println("no");
						        }
							}
						}
						//2.回复的信息
						if(replyuserid>0){
							UsersInfo uu2=this.userinfoService.selectAppUserByID(userid);
							if(uu2!=null && uu2.getAndroid_ios()!=null){
								String ALERT = "评论通知：你的动态消息被评论了"; 
						        int result = JpushClientUtil.sendToRegistrationId(uu2.getAndroid_ios(), ALERT, "动态点赞", ALERT, voicesid+"");
						        if(result>0){
						            System.out.println("ok");
						        }else{
						        	System.out.println("no");
						        }
							}
						}
						//3.
						if(voice.getUsers_id()!=replyuserid){
							UsersInfo uu3=this.userinfoService.selectAppUserByID(replyuserid);
							if(uu3!=null && uu3.getAndroid_ios()!=null){
								String ALERT = "评论通知：你的动态消息被评论了"; 
						        int result = JpushClientUtil.sendToRegistrationId(uu3.getAndroid_ios(), ALERT, "动态点赞", ALERT, voicesid+"");
						        if(result>0){
						            System.out.println("ok");
						        }else{
						        	System.out.println("no");
						        }
							}
						}
					}
					/**【推送评论信息】**/
				}
				app.setCode(200);
				app.setMessage("评论成功");
				
				/****增加亲密度****/
				IntimacyInfo tim=new IntimacyInfo();
				tim=this.intimacyinfoService.selectIntimacyInfoOne(userid, voice.getUsers_id());
				if(tim!=null){
					tim.setIntimacye(tim.getIntimacye()+5);
					this.intimacyinfoService.insertOrUpdateIntimacy(tim);
				}
				/****增加亲密度****/
				
			}else{
				app.setCode(300);
				app.setMessage("评论失败");
			}
		} catch (Exception e) {
			app.setCode(300);
			app.setMessage("评论失败");
		}finally {
			
		}
		return gson.toJson(app);
	}
	
	/***2.添加评论的请求【语音评论】***/
	@RequestMapping(value="uservoicescommentinto",method=RequestMethod.POST,produces="text/json;charset=UTF-8")
	@ResponseBody
	public String appuserInsertVoicesComment(@RequestParam(value="filename") MultipartFile filename,String appid,int userid,
			String content,int voicesid,int replyuserid) throws Exception{
		try {
			map.clear();
			app.setMap(map);
			CommentsInfo comm=new CommentsInfo();
			if(content!="" && content!=null){
				comm.setInfo(content);
			}
			comm.setDate(DateHelper.getNewData());		//添加时间
			comm.setUsers_id(userid); 					//用户ID
			comm.setVoices_id(voicesid); 				//动态的ID
			comm.setType(3); 		//评价类型
			comm.setTapcount(0);    //偷听量
			comm.setReplyuser(replyuserid);
			//遍历文件集合
			if(null != filename ){ 
				/*//遍历并保存文件  
	            for(MultipartFile file : filename){  */
	            	BannerImageOSSTools ban=new BannerImageOSSTools();
	    		    //String head = ban.updateHead(filename, 4);//此处是调用上传服务接口，4是需要更新的userId 测试数据。
	    		    String head = ban.updateHead(filename, 4,"voices",1);
	    		    
	    		    comm.setType(3);
	    		    comm.setVoiceurl(head); 		//设置声评的路径
	    		    
	    		    
	    		    URLConnection con = null;
				    URL urlfile = new URL(head);
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
	    		    comm.setLength((time/1000+1)+"");
	           /* } */ 
			}
			//执行入库操作 
			if(this.commentsinfoService.insertIntoCommentsOne(comm)){
				VoicesUserView voice=this.voicesinfoService.selectVoicesUser(voicesid);
				System.out.println("2新增评论编号："+comm.getId());
				//查询出添加的新数据
				CommitsUserView comment=this.commentsinfoService.seelctCommitsUserViewOne(comm.getId());
				map.put("commentmap", comment);
				app.setMap(map);
				
				//跟新动态的评论数
				int count=this.commentsinfoService.selectCommentCountById(voicesid);
				if(count>=0){
					//跟新
					this.commentsinfoService.updateVoicesCommentnumById(voicesid, count);
					
					/**【推送评论信息】**/
					//1.查询出用户的信息
					UserSetupInfo userset=this.usersetupinfoService.selectUserSetupByUserID(userid,2);
					if(userset==null){
						UsersInfo uu=this.userinfoService.selectAppUserByID(voice.getUsers_id());
						if(uu!=null && uu.getAndroid_ios()!=null){
							String ALERT = "评论通知：你的动态消息被评论了";    
					        int result = JpushClientUtil.sendToRegistrationId(uu.getAndroid_ios(), ALERT, "动态点赞", ALERT, voicesid+"");
					        if(result>0){
					            System.out.println("ok");
					        }else{
					        	System.out.println("no");
					        }
						}
						//2.回复的信息
						if(replyuserid>0){
							UsersInfo uu2=this.userinfoService.selectAppUserByID(userid);
							if(uu2!=null && uu2.getAndroid_ios()!=null){
								String ALERT = "评论通知：你的动态消息被评论了"; 
						        int result = JpushClientUtil.sendToRegistrationId(uu2.getAndroid_ios(), ALERT, "动态点赞", ALERT, voicesid+"");
						        if(result>0){
						            System.out.println("ok");
						        }else{
						        	System.out.println("no");
						        }
							}
						}
						//3.
						if(voice.getUsers_id()!=replyuserid){
							UsersInfo uu3=this.userinfoService.selectAppUserByID(replyuserid);
							if(uu3!=null && uu3.getAndroid_ios()!=null){
								String ALERT = "评论通知：你的动态消息被评论了"; 
						        int result = JpushClientUtil.sendToRegistrationId(uu3.getAndroid_ios(), ALERT, "动态点赞", ALERT, voicesid+"");
						        if(result>0){
						            System.out.println("ok");
						        }else{
						        	System.out.println("no");
						        }
							}
						}
				        /**【推送评论信息】**/
					}else{
						if(userset.getSetid()==1){
							UsersInfo uu=this.userinfoService.selectAppUserByID(voice.getUsers_id());
							if(uu!=null && uu.getAndroid_ios()!=null){
								String ALERT = "评论通知：你的动态消息被评论了";    
						        int result = JpushClientUtil.sendToRegistrationId(uu.getAndroid_ios(), ALERT, "动态点赞", ALERT, voicesid+"");
						        if(result>0){
						            System.out.println("ok");
						        }else{
						        	System.out.println("no");
						        }
							}
							//2.回复的信息
							if(replyuserid>0){
								UsersInfo uu2=this.userinfoService.selectAppUserByID(userid);
								if(uu2!=null && uu2.getAndroid_ios()!=null){
									String ALERT = "评论通知：你的动态消息被评论了"; 
							        int result = JpushClientUtil.sendToRegistrationId(uu2.getAndroid_ios(), ALERT, "动态点赞", ALERT, voicesid+"");
							        if(result>0){
							            System.out.println("ok");
							        }else{
							        	System.out.println("no");
							        }
								}
							}
							//3.
							if(voice.getUsers_id()!=replyuserid){
								UsersInfo uu3=this.userinfoService.selectAppUserByID(replyuserid);
								if(uu3!=null && uu3.getAndroid_ios()!=null){
									String ALERT = "评论通知：你的动态消息被评论了"; 
							        int result = JpushClientUtil.sendToRegistrationId(uu3.getAndroid_ios(), ALERT, "动态点赞", ALERT, voicesid+"");
							        if(result>0){
							            System.out.println("ok");
							        }else{
							        	System.out.println("no");
							        }
								}
							}
							/**【推送评论信息】**/
						}
					}
					/**【推送评论信息】**/
				}
				/**【推送评论信息】**/
				//1.查询出用户的信息
				UserSetupInfo userset=this.usersetupinfoService.selectUserSetupByUserID(userid,2);
				if(userset==null){
					UsersInfo uu=this.userinfoService.selectAppUserByID(voice.getUsers_id());
					if(uu!=null && uu.getAndroid_ios()!=null){
						String ALERT = "评论通知：你的动态消息被评论了";    
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
						UsersInfo uu=this.userinfoService.selectAppUserByID(voice.getUsers_id());
						if(uu!=null && uu.getAndroid_ios()!=null){
							String ALERT = "评论通知：你的动态消息被评论了";    
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
				app.setCode(200);
				app.setMessage("评论成功");
				
				/****增加亲密度****/
				IntimacyInfo tim=new IntimacyInfo();
				tim=this.intimacyinfoService.selectIntimacyInfoOne(userid, voice.getUsers_id());
				if(tim!=null){
					tim.setIntimacye(tim.getIntimacye()+5);
					this.intimacyinfoService.insertOrUpdateIntimacy(tim);
				}
				/****增加亲密度****/
				
				
			}else{
				app.setCode(300);
				app.setMessage("评论失败");
			}
		} catch (Exception e) {
			app.setCode(300);
			app.setMessage("评论失败");
		}
		return gson.toJson(app);
	}
	
}
