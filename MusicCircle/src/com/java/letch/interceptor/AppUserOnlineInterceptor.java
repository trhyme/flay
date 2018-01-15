package com.java.letch.interceptor;

import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.java.letch.app.returnmodel.AppReturnCodeInfo;
import com.java.letch.model.UsersInfo;
import com.java.letch.pagestrtools.AdminsUserPage;
import com.java.letch.service.UsersInfoService;

/***
 * APP用户是否在线的拦截器
 * @author Administrator
 * 2017-09-26
 */
public class AppUserOnlineInterceptor implements HandlerInterceptor{

	//JSON数据转换工具
	private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").serializeNulls().create();
		
	
	@Resource(name="UsersInfoService")
	private UsersInfoService usersinfoService;		//用户信息类
	
	/***请求执行信息***/
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object handler) throws Exception {
		response.setHeader("Content-type", "text/html;charset=UTF-8");
		StringBuffer url=new StringBuffer(request.getRequestURI());
		url.append("?").append(request.getQueryString());
		System.out.println("APP访问地址："+url);
		String appid=request.getParameter("appid");
		System.out.println(appid);
		
		//根据APP端上传的APPID判断是否存在该用户
		UsersInfo user=this.usersinfoService.selectAppUserByAPPid(appid);
		if(user!=null){
			return true;
		}else{
			//返回数据
			PrintWriter out=response.getWriter();
			AppReturnCodeInfo app=new AppReturnCodeInfo();
			app.setCode(5000);
			app.setMessage("登录过期，请重新登录");
			out.println(gson.toJson(app));
			return false;
		}
	}
	
	
	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		
	}


}
