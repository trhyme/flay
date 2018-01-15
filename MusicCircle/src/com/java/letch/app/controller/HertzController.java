package com.java.letch.app.controller;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.java.letch.app.redis.service.UserRedisService;
import com.java.letch.app.redis.tools.RedisUtil;
import com.java.letch.app.redis.view.UserRedisView;
import com.java.letch.app.returnmodel.AppReturnCodeInfo;
import com.java.letch.model.FollowsInfo;
import com.java.letch.model.UsersInfo;
import com.java.letch.service.FollowsInfoService;
import com.java.letch.service.UsersInfoService;
import com.java.letch.tools.BannerImageOSSTools;

import javazoom.jl.decoder.Bitstream;
import javazoom.jl.decoder.Header;
import redis.clients.jedis.Jedis;

/***
 * 52赫兹功能模块请求类
 * @author Administrator
 * 2017-10-17
 */
@Controller
public class HertzController {
	
	//用户业务操作类
	@Resource(name="UsersInfoService")
	private UsersInfoService userinfoService;
	
	//心动关注信息
	@Resource(name="FollowsInfoService")
	private FollowsInfoService followsinfoService;
	
	//JSON数据转换工具
	private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").serializeNulls().create();
	
	private String MANUSER_KEY="男";
	
	private String WOMANUSER_KEY="女";
	
	private Map<String, Object> map = new HashMap<String, Object>();
	private AppReturnCodeInfo app=new AppReturnCodeInfo();
	
	//加锁
	private static HashMap<String, Lock> lockMap = new HashMap<String, Lock>();
	
	/*1.用户进入52Hz处于在线状态*/
	@RequestMapping(value="hertzonline",method=RequestMethod.POST,produces="text/json;charset=UTF-8")
	@ResponseBody
	public String userOnline52Hz(@RequestParam(value="hertzname") MultipartFile hertzname,String appid,String sextype,HttpServletResponse response) throws Exception{
		try {
			//上传语音信息
			response.setHeader("content-type", "audio/mp3");
			BannerImageOSSTools ban=new BannerImageOSSTools();
		    String head = ban.updateHead(hertzname, 4,"voices",1);//此处是调用上传服务接口，4是需要更新的userId 测试数据。
		    System.out.println(head);
			//查询用户信息
			UsersInfo user=this.userinfoService.selectAppUserByAPPid(appid);
			//存入到REDIS
			UserRedisView userview=new UserRedisView();
			if(user!=null){
				userview.setId(user.getId()); 				//主键
				userview.setNum(user.getNum()); 			//唯一主键字段
				userview.setPhone(user.getPhone()==null?"":user.getPhone()); 		//手机号
				userview.setHeadpic(user.getHeadpic()==null?"http://images.leqtch.com/images/leqtchchat.png":user.getHeadpic()); 	//用户头像
				userview.setSex(user.getSex()==null?"":user.getSex()); 			//性别
				userview.setApponlyid(user.getApponlyid()==null?"":user.getApponlyid());	//用户App_ID
				userview.setVoicesurl(head); 				//设置语音链接
				userview.setUsername(user.getUsername()==null?"":user.getUsername()); 	//用户名称
				
				//2017-12-04
				//获取长度
				URLConnection con = null;
				try {
					if(head!=null){
					}else{
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
					    userview.setLength(time/1000+1);
					}
				} catch (Exception e) {
					userview.setLength(0);
					System.out.println("voices_Length："+e);
				}
				
			}
			String sex=user.getSex()==null?"女":user.getSex();
			String sex2="女";
			if(sex.equals("女")){
				sex2="女";
			}else{
				sex2="男";
			}
			//指定性别
			if(sextype!=null && !"".equals(sextype)){
				sex2=sextype;
				if(sex2.equals("男")){
					sex2="男";
				}else{
					sex2="女";
				}
			}
			
			map.clear();
			app.setMap(map);
			//操作Redis
			System.out.println("要匹配的："+sex2);
			System.out.println("一入库的："+sex);
			
			
			//同步锁
			Lock lock = lockMap.get(sex);
			if(null==lock){
				synchronized(lockMap) {
	                lock = lockMap.get(sex);
	                if (null == lock) {
	                    lock = new ReentrantLock();
	                    lockMap.put(sex, lock);
	                }
				}
			}
			
			synchronized(lock) {
				if(UserRedisService.insertRedisUser(userview,sex)){
					UserRedisView view=new UserRedisView();
					try {
						view=UserRedisService.selectRedisUserByKey(sex2);
					} catch (Exception e) {
						System.out.println(e);
					}
					if(view!=null){
						
						int m=1;
						for(int x=0;x<6;x++){
							if(view.getId()==userview.getId()){
								System.out.println("系统匹配"+x);
								view=UserRedisService.selectRedisUserByKey(sex2);
								m+=1;
							}else{
								break;
							}
						}
						/**随机匹配**/
						if(m>=5){
							app.setCode(300);
							app.setMessage("寻找好友失败");
						}else{
							/**匹配到了用户**/
							//1.查询出是否已经心动
							List<FollowsInfo> lis=this.followsinfoService.selectFollowList(user.getId(), view.getId());
							if(lis!=null && lis.size()>0){
								view.checkbeckoning=1;
							}
							/*String hqls="from FollowsInfo where users_id=? and followsid=?";
							List<FollowsInfo> lis=session.getCurrentSession().createQuery(hqls).setInteger(0, userid).setInteger(1, Integer.parseInt(o[1].toString())).list();
							if(lis!=null && lis.size()>0){
								m.checkbeckoning=1;
							}*/
							//存储数据
							map.put("hertz", view);
							app.setCode(200);
							app.setMessage("寻找好友成功");
							app.setMap(map);
						}
					}else{
						app.setCode(300);
						app.setMessage("寻找好友失败");
					}
				}else{
					System.out.println("3：");
					app.setCode(300);
					app.setMessage("寻找好友失败");
				}
			}
		} catch (Exception e) {
			System.out.println("2："+e);
			app.setCode(300);
			app.setMessage("寻找好友失败");
		}
		return gson.toJson(app);
	}
	
	
	
	public static void main(String[] args) {
		/*UserRedisView userview=new UserRedisView();
		userview.setId(1); 				//主键
		userview.setNum("1"); 			//唯一主键字段
		userview.setPhone("1823911401"); 		//手机号
		userview.setHeadpic("http://images.leqtch.com/images/leqtchchat.png"); 	//用户头像
		userview.setSex("男"); 			//性别
		userview.setApponlyid("132123165");	//用户App_ID
		userview.setVoicesurl("http://images.leqtch.com/images/leqtchchat.png"); 				//设置语音链接
		userview.setUsername("S1"); 	//用户名称
		
		System.out.println("1."+UserRedisService.insertRedisUser(userview,"男"));*/
		
		
		UserRedisView view2=new UserRedisView();
		view2=UserRedisService.selectRedisUserByKey("男");
		System.out.println(1);
		
	}
	
}
