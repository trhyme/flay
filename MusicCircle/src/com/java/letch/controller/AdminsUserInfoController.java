package com.java.letch.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.java.letch.model.AdminsUserInfo;
import com.java.letch.model.MenuInfo;
import com.java.letch.model.MenuUserInfo;
import com.java.letch.pagestrtools.AdminsUserPage;
import com.java.letch.service.AdminsUserInfoService;
import com.java.letch.service.MenuInfoService;
import com.java.letch.service.MenuUserInfoService;
import com.java.letch.tools.DateHelper;
import com.java.letch.tools.EncryptionTools;
import com.java.letch.tools.UuidGenerate;


/***
 * 管理员请求操作类
 * @author Administrator
 * 2017-09-13
 */
@Controller
public class AdminsUserInfoController {
	
	//管理员信息处理操作类
	@Resource(name="AdminsUserInfoService")
	private AdminsUserInfoService adminsuserService;
	//菜单关联信息操作
	@Resource(name="MenuUserInfoService")
	private MenuUserInfoService menuuserinfoService;
	//菜单信息操作
	@Resource(name="MenuInfoService")
	private MenuInfoService menuinfoService;
	
	/**登录页面**/
	@RequestMapping(value="admin_login",method=RequestMethod.GET)
	@ResponseBody
	public ModelAndView gotoLoginPage(ModelAndView view) throws Exception{
		
		view.setViewName(AdminsUserPage.ADMINLOGIN);
		return view;
	}
	
	/**后台管理系统首页**/
	@RequestMapping(value="admin_index",method=RequestMethod.GET)
	@ResponseBody
	public ModelAndView gotoIndexPage(ModelAndView view,HttpServletRequest request,HttpServletResponse response,Map<String, Object> map) throws Exception{
		//获取登录的用户信息  查询出菜单信息
		HttpSession session=request.getSession();
		AdminsUserInfo user=null;
		if(session.getAttribute(AdminsUserPage.AdminLoginSession)!=null){
			user=(AdminsUserInfo) session.getAttribute(AdminsUserPage.AdminLoginSession);
		}
		/**菜单栏信息处理**/
		StringBuffer sbf=new StringBuffer();
		for(MenuUserInfo m:user.getUsersMenu()){
			sbf.append("{").append("id:"+"'"+m.getMenu().getId()+"',");
			if(m.getMenu().getPid()==null){
				sbf.append("pid:"+"'"+0+"',");
			}else{
				sbf.append("pid:"+"'"+m.getMenu().getPid()+"',");
			}
			sbf.append("url:'"+m.getMenu().getUrl()+"',")
				.append("icon:'"+m.getMenu().getIcon()+"',")
				.append("title:'"+m.getMenu().getTitle()+"',")
				.append("},");
		}
		
		StringBuffer nstr=new StringBuffer("var data={files:[");
		if(!(sbf.toString().length()<=0)){
			nstr.append(sbf.substring(0, sbf.toString().length()-1)).append("]};");
		}
		
		//获取项目路径
		ServletContext s1=request.getServletContext();
		String temp=s1.getRealPath("/");
		/**权限文件**/
		File file=new File(temp+"js/"+user.getAdmstr()+".js");
		if(!file .exists()  && !file .isDirectory()){       
	        FileOutputStream in = new FileOutputStream(file); 
	        OutputStreamWriter writer = new OutputStreamWriter(in, "UTF-8");
            try {  
            	writer.append(nstr.toString());
            	writer.close();
                file.createNewFile();
                map.put("menuJS", user.getAdmstr()+".js");
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
		        
		}else{  
	        FileOutputStream in = new FileOutputStream(file);  
	        OutputStreamWriter writer = new OutputStreamWriter(in, "UTF-8");
            try {  
            	writer.append(nstr.toString());
            	writer.close();
                file.createNewFile();
                map.put("menuJS", user.getAdmstr()+".js");
            } catch (IOException e) {  
                e.printStackTrace();  
            }
		}  
		/**权限文件**/
		view.addAllObjects(map);
		
		view.setViewName(AdminsUserPage.ADMININDEX);
		return view;
	}
	
	@RequestMapping(value="adminlogin",method=RequestMethod.GET)
	@ResponseBody
	public ModelAndView gotoLoginSelect(ModelAndView view,HttpServletRequest request,HttpServletResponse response,Map<String, Object> map) throws Exception{
		StringBuffer url=new StringBuffer(request.getRequestURI());
		url.append("?").append(request.getQueryString());
		
		HttpSession session = request.getSession();
		session.setAttribute(AdminsUserPage.AdminLogURL, url.toString().substring(url.toString().lastIndexOf("/"), url.toString().length()));
		
		AdminsUserInfo onlineuser=null;
		if(session.getAttribute(AdminsUserPage.AdminLoginSession)!=null){
			onlineuser=(AdminsUserInfo) session.getAttribute(AdminsUserPage.AdminLoginSession);
		}
		if(onlineuser!=null){
			session.setAttribute(AdminsUserPage.Message,AdminsUserPage.NotLogin);
			//return new ModelAndView("redirect:/admin_index");
			return gotoIndexPage(view, request, response, map);
		}
		//如果未放行，那就跳转到登录页面
		request.getRequestDispatcher("admin_login").forward(request, response);
		return gotoLoginPage(view);
		
		
	}
	
	/**登录的方法**/
	@RequestMapping(value="adminlogin",method=RequestMethod.POST)
	@ResponseBody
	public ModelAndView gotoLoginSelect(ModelAndView view,String username,String userpwd,
			HttpServletRequest request,HttpServletResponse response,Map<String, Object> map) throws Exception{
		//获取账号信息
		System.out.println(username+":"+userpwd);
		if(username==null){
			map.put(AdminsUserPage.Message, AdminsUserPage.LoginUserCheck);
			view.addAllObjects(map);
			view.setViewName(AdminsUserPage.ADMINLOGIN);
			return view;
		}else{
			//根据账号查询信息
			AdminsUserInfo user=this.adminsuserService.selectUserByName(username);
			if(user==null){
				//账号密码错误
				map.put(AdminsUserPage.Message, AdminsUserPage.LoginuserNull);
				map.put("names", username);
				view.addAllObjects(map);
				view.setViewName(AdminsUserPage.ADMINLOGIN);
				return view;
			}else{//判断密码与账号信息时候一致
				//密码加密
				userpwd=EncryptionTools.setBase64(userpwd);
				if(user.getAdmname().equals(username) && user.getAdmadress().equals(userpwd) && user.getAdmstate()==1){
					//保存信息
					HttpSession sessin=request.getSession();
					//修改登录时间
					this.adminsuserService.updateAdminLoginTime(user.getAdmid());
					AdminsUserInfo userNew=new AdminsUserInfo();
					userNew=user;
					userNew.setAdmadress("******");
					
					sessin.setAttribute(AdminsUserPage.AdminLoginSession, userNew);
					
					/**菜单栏信息处理**/
					StringBuffer sbf=new StringBuffer();
					for(MenuUserInfo m:user.getUsersMenu()){
						sbf.append("{").append("id:"+"'"+m.getMenu().getIds()+"',");
						if(m.getMenu().getPid()==null){
							sbf.append("pid:"+"'"+0+"',");
						}else{
							sbf.append("pid:"+"'"+m.getMenu().getPid()+"',");
						}
						sbf.append("url:'"+m.getMenu().getUrl()+"',")
							.append("icon:'"+m.getMenu().getIcon()+"',")
							.append("title:'"+m.getMenu().getTitle()+"',")
							.append("},");
					}
					
					StringBuffer nstr=new StringBuffer("var data={files:[");
					if(sbf.toString().length()<=0){
						
					}else{
						nstr.append(sbf.substring(0, sbf.toString().length()-1)).append("]};");
					}
					
					
					//获取项目路径
					ServletContext s1=request.getServletContext();
					String temp=s1.getRealPath("/");
					System.out.println("temp:"+temp+"js/"+user.getAdmstr()+".js");
					/**权限文件**/
					File file=new File(temp+"js/"+user.getAdmstr()+".js");
					if(!file .exists()  && !file .isDirectory()){       
				        FileOutputStream in = new FileOutputStream(file); 
				        OutputStreamWriter writer = new OutputStreamWriter(in, "UTF-8");
			            try {  
			            	writer.append(nstr.toString());
			            	writer.close();
			                file.createNewFile();
			                map.put("menuJS", user.getAdmstr()+".js");
			            } catch (IOException e) {  
			                e.printStackTrace();  
			            }  
					        
					}else{  
				        FileOutputStream in = new FileOutputStream(file);  
				        OutputStreamWriter writer = new OutputStreamWriter(in, "UTF-8");
			            try {  
			            	writer.append(nstr.toString());
			            	writer.close();
			                file.createNewFile();
			                map.put("menuJS", user.getAdmstr()+".js");
			            } catch (IOException e) {  
			                e.printStackTrace();  
			            }
					}  
					/**权限文件**/
					view.addAllObjects(map);
					return gotoLoginSelect(view,request,response,map);
				}else{
					map.put(AdminsUserPage.Message, AdminsUserPage.LoginNot);
					map.put("names", username);
					view.addAllObjects(map);
					view.setViewName(AdminsUserPage.ADMINLOGIN);
					return view;
				}
			}
		}
	}
	
	/**获取管理员信息列表**/
	@RequestMapping(value="adminuserlist",method=RequestMethod.GET)
	@ResponseBody
	public ModelAndView getSelectList(ModelAndView view,HttpServletRequest request,HttpServletResponse response,Map<String, Object> map) throws Exception{
		List<AdminsUserInfo> list=this.adminsuserService.selectAdmin();
		map.put("adminList", list);
		view.addAllObjects(map);
		view.setViewName(AdminsUserPage.ADMINLIST);
		return view;
	}
	
	/**添加管理员信息页面**/
	@RequestMapping(value="adminuseradd",method=RequestMethod.GET)
	@ResponseBody
	public ModelAndView addAdminUser(ModelAndView view,HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		view.setViewName(AdminsUserPage.ADMINUSER_ADD);
		return view;
	}

	/**给用户设置权限页面**/
	@RequestMapping(value="usermenupage",method=RequestMethod.GET)
	@ResponseBody
	public ModelAndView adminUserToMenuPage(ModelAndView view,String userid,Map<String, Object> map,HttpServletRequest request,HttpServletResponse response) throws Exception{
		//根据标号查询用户信息
		AdminsUserInfo user=this.adminsuserService.selectOneAdminById(userid);
		if(user==null){
			map.put("umessage", AdminsUserPage.USERMENUERROR);
			return getSelectList(view, request, response, map);
		}else{
			map.put("umuser", user);
			//判断管理员是否可用
			if(user.getAdmstate()==0){ //不可用
				map.put("umessage", AdminsUserPage.USERMENUERROR);
				return getSelectList(view, request, response, map);
			}
			
			//根据全部查询菜单信息
			List<MenuInfo> menuuser=this.menuinfoService.selectMenuAllList();
			map.put("allmenus", menuuser);
			view.addAllObjects(map);
			view.setViewName(AdminsUserPage.ADMINMENU_PAGE);
			return view;
		}
	}
	
	/**设置请求**/
	@RequestMapping(value="menuinsertall",method=RequestMethod.POST)
	@ResponseBody
	public ModelAndView addMenuAndUser(ModelAndView view,HttpServletRequest request,HttpServletResponse response,String userid,Map<String, Object> map) throws Exception{
		//获取多选框信息
		String[] list = request.getParameterValues("menuAdduser");
		if(list.length>0){
			//添加
			this.menuinfoService.addMenuUserAll(list, userid);
		}
		return getSelectList(view, request, response, map);
	}
	
	/**管理员入库信息**/
	@RequestMapping(value="adminuseraddDB",method=RequestMethod.POST)
	@ResponseBody
	public ModelAndView insertIntoAdminToDB(ModelAndView view,HttpServletRequest request,HttpServletResponse response,
			String username,String userpassword,String newpassword2,String usertel,String email,String remarks,Map<String, Object> map) throws Exception{
		//根据账号查询用户信息
		//根据账号查询信息
		AdminsUserInfo user=this.adminsuserService.selectUserByName(username);
		if(user==null){
			//判断密码是否存在
			if(!userpassword.equals(newpassword2)){  //2次密码不一致
				map.put("message", AdminsUserPage.USERPWDNOTONE);
			}else{
				user=new AdminsUserInfo();
				user.setAdmstr(UuidGenerate.getUUID());
				user.setAdmname(username);
				user.setAdmadress(EncryptionTools.setBase64(userpassword));
				user.setAdmemail(email);
				user.setAdmphone(usertel);
				user.setAdmstate(1);
				user.setAdmremark(remarks);
				user.setAdmtimes(DateHelper.getNewData());
				//调用添加的方法
				if(this.adminsuserService.insertIntoAdminUser(user)){
					return getSelectList(view, request, response, map);
				}else{
					map.put("message", AdminsUserPage.USERINSERTNO);
				}
			}
			
		}else{
			//提示该用户已经存在
			map.put("message","【"+username+"】"+AdminsUserPage.USERADMINFORIN);
		}
		
		view.addAllObjects(map);
		view.setViewName(AdminsUserPage.ADMINUSER_ADD);
		return view;
	}
	
	/**退出登录的方法**/
	@RequestMapping(value="adminloginout",method=RequestMethod.GET)
	@ResponseBody
	public ModelAndView adminUserNotLogin(ModelAndView view,HttpServletRequest request,HttpServletResponse response) throws Exception{
		//获取session
		HttpSession session=request.getSession();
		AdminsUserInfo user=null;
		if(session.getAttribute(AdminsUserPage.AdminLoginSession)!=null){
			user=(AdminsUserInfo) session.getAttribute(AdminsUserPage.AdminLoginSession);
		}
		if(user!=null){
			@SuppressWarnings("rawtypes")
			Enumeration em = request.getSession().getAttributeNames();
			while(em.hasMoreElements()){
			   request.getSession().removeAttribute(em.nextElement().toString());
			}
		}
		return new ModelAndView("redirect:admin_login");
	}
	
}
