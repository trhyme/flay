package com.java.letch.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.java.letch.app.ShortMessageCodeHttp;
import com.java.letch.model.UsersInfo;
import com.java.letch.pagestrtools.UsersInfoPageEntity;
import com.java.letch.service.FollowsInfoService;
import com.java.letch.service.UsersInfoService;
import com.java.letch.thread.SendMessageThread;

/***
 * APP用户信息表 users
 * @author Administrator
 * 2017-09-18
 */
@Controller
public class UsersInfoController {
	public UsersInfoController(){}
	//JSON数据转换工具
	private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").serializeNulls().create();
	
	@Resource(name="UsersInfoService")
	private UsersInfoService userinfoService;		//APP数据操作类信息
	
	@Resource(name="FollowsInfoService")
	private FollowsInfoService followsinfoService;  //关注与粉丝信息类
	
	@Resource(name="SendMessageThread")
	private SendMessageThread sendmessageThread;	//发送线程信息
	
	/*后台管理用户页面*/
	@RequestMapping(value="/appuserpage",method=RequestMethod.GET)
	@ResponseBody
	public ModelAndView selectAppUsersPageList(ModelAndView view,HttpServletRequest request,Map<String, Object> map,
			String uphone,String uuid,String unames,String stratus,String huiyuan,String begintimes,String endstimes) throws Exception{
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
			map.put("pagenum",15);			//每页条数（INT）
			map.put("bgstatrus", 1);		//状态,是否删除（INT）
			
			/***查询条件信息***/
			map.put("uphone", uphone);		//手机号码
			map.put("uuid", uuid);			//用户编号
			map.put("unames",unames);		//用户昵称
			map.put("zhuangtai", stratus);	//状态
			map.put("huiyuan", huiyuan);	//是否会员
			map.put("begintimes", begintimes);	//注册开始时间
			map.put("endstimes", endstimes);	//注册结束时间
			System.out.println(begintimes+":"+endstimes);
			/***查询条件信息***/
			
			//调用分页查询的方法
			map=this.userinfoService.selectUserListByPage(map);
			view.addAllObjects(map);
		} catch (Exception e) {}
		view.setViewName(UsersInfoPageEntity.USERLISTPAGE);
		return view;
	}
	
	/***************给APP用户发送短信的请求*****************/
	@RequestMapping(value="sendphonecode",method=RequestMethod.POST)
	@ResponseBody
	public String sendShortMessageCode(String phoneall,String message,String checkuser) throws Exception{
		//判断是否为全发短信
		if(checkuser!=null && !"".equals(checkuser)){
			if(checkuser.equals("1all")){
				/**开启线程，全发短信**/
				this.sendmessageThread.doRunThread(message);
			}
			return "200";
		}else{
			if(phoneall!=null && !"".equals(phoneall)){
				//发送短信
				if(ShortMessageCodeHttp.sendCodeMeaage(phoneall, message)){
					return "200";
				}else{
					return "300";
				}
			}else{
				return "300";
			}
		}
		
	}
	/***************给APP用户发送短信的请求*****************/
	
	/**后台查看用户的关注信息2017-10-11**/
	@RequestMapping(value="showuserfollow",method=RequestMethod.GET)
	public ModelAndView showFollowUserList(ModelAndView view,Map<String, Object> map,String uid,String thepage,HttpServletRequest request) throws Exception{
		//查询出粉丝信息
		try {
			//当前页数
			String thepagestr=thepage;
			int thepage2=1;
			if(thepagestr==null || thepagestr==""){
				thepage2=1;
			}else{
				thepage2=Integer.parseInt(thepagestr);
			}
			map.put("nowpage", thepage2);	//当前页数（INT）
			map.put("pagenum",15);			//每页条数（INT）
			map.put("bgstatrus", 1);		//状态,是否删除（INT）
			map.put("userID", uid);
			//调用分页查询的方法
			map=this.followsinfoService.selectFollowsByID(map, Integer.parseInt(uid));
			view.addAllObjects(map);
		} catch (Exception e) {}
		view.setViewName(UsersInfoPageEntity.USERFOLLOWPAGE);
		return view;
	}
	
	
	/**后台查看用户的粉丝信息2017-10-11**/
	@RequestMapping(value="showuserfans",method=RequestMethod.GET)
	public ModelAndView showFansUserList(ModelAndView view,Map<String, Object> map,String uid,String thepage,HttpServletRequest request) throws Exception{
		//查询出粉丝信息
		try {
			//当前页数
			String thepagestr=thepage;
			int thepage2=1;
			if(thepagestr==null || thepagestr==""){
				thepage2=1;
			}else{
				thepage2=Integer.parseInt(thepagestr);
			}
			map.put("nowpage", thepage2);	//当前页数（INT）
			map.put("pagenum",15);			//每页条数（INT）
			map.put("bgstatrus", 1);		//状态,是否删除（INT）
			map.put("userID", uid);
			//调用分页查询的方法
			map=this.followsinfoService.selectFansByID(map, Integer.parseInt(uid));
			view.addAllObjects(map);
		} catch (Exception e) {}
		view.setViewName(UsersInfoPageEntity.USERFANSPAGE);
		return view;
	}
	
	
	/***后台查看用户的详细信息***/
	@RequestMapping(value="lookinguserone",method=RequestMethod.GET)
	public ModelAndView selectOrUpdateUser(ModelAndView view,String uid,Map<String, Object> map) throws Exception{
		//1.根据ID查询用户信息
		System.out.println(uid);
		UsersInfo user=this.userinfoService.selectAppUserByID(Integer.parseInt(uid));
		
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
		map.put("user", user2);
		view.addAllObjects(map);
		view.setViewName(UsersInfoPageEntity.USERLOOKINGPAGE);
		return view;
	}
	/***后台查看用户的详细信息***/
	
	/**后台修改用户信息**/
	@RequestMapping(value="appuseroneupdate",method=RequestMethod.POST)
	public ModelAndView updateAppuserByAll(ModelAndView view,UsersInfo user,Map<String, Object> map,HttpServletRequest request) throws Exception{
		
		System.out.println(user.getNum());
		//调用修改的方法
		if(this.userinfoService.updateAppOneUserByUser(user)){
			
		}
		return selectAppUsersPageList(view, request, map,null,null,null,null,null,null,null);
	}
	/**后台修改用户信息**/
	
	//
	
}
