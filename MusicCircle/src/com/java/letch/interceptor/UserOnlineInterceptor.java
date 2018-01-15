package com.java.letch.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.java.letch.model.AdminsUserInfo;
import com.java.letch.pagestrtools.AdminsUserPage;


/*********用户是否登录拦截器*********/
public class UserOnlineInterceptor implements HandlerInterceptor {
	
	/**请求到达之前执行,验证用户是否登录**/
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object handler) throws Exception {
		StringBuffer url=new StringBuffer(request.getRequestURI());
		url.append("?").append(request.getQueryString());
		
		HttpSession session = request.getSession();
		session.setAttribute(AdminsUserPage.AdminLogURL, url.toString().substring(url.toString().lastIndexOf("/"), url.toString().length()));
		System.out.println("访问地址："+url);
		
		AdminsUserInfo onlineuser=null;
		if(session.getAttribute(AdminsUserPage.AdminLoginSession)!=null){
			onlineuser=(AdminsUserInfo) session.getAttribute(AdminsUserPage.AdminLoginSession);
		}
		if(onlineuser!=null){
			session.setAttribute(AdminsUserPage.Message,AdminsUserPage.NotLogin);
			return true;
		}
		//如果未放行，那就跳转到登录页面
		request.getRequestDispatcher("404").forward(request, response);
		return false;
	}
	
	//请求处理完成之后,ModelAndView返回之前
	public void postHandle(HttpServletRequest request, HttpServletResponse response,
		Object handler, ModelAndView modelAndView) throws Exception {
		//System.out.println("请求完成之后，ModelAndView返回之前");
	}
	
	//整个请求执行完成之后处理的方法
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		//System.out.println("处理完成之后执行");
	}

}
