package com.java.letch.pagestrtools;

/***
 * 管理员JSP页面信息
 * @author Administrator
 * 2017-09-13
 */
public class AdminsUserPage {
	/**主路径**/
	private static final String ADMIMPAGE="/ADMINS";
	
	/*登录页*/
	public static final String ADMINLOGIN=ADMIMPAGE+"/adminlogin";
	/*后台首页*/
	public static final String ADMININDEX=ADMIMPAGE+"/index";
	/*管理员用户列表页*/
	public static final String ADMINLIST=ADMIMPAGE+"/adminlist";
	/*管理员添加页面*/
	public static final String ADMINUSER_ADD=ADMIMPAGE+"/admin_add";
	/*管理员设置菜单权限*/
	public static final String ADMINMENU_PAGE=ADMIMPAGE+"/adminuser_menu";
	
	//关键字信息
	public static final String AdminLoginSession="adminusersession";   //保存的登录用户回话
	public static final String AdminLogURL="admloginurl";		//用户上一次的登录地址
	public static final String AdminLists="adminList";			//管理员列表信息
	//关键字信息
	
	/**提示语信息**/
	public static final String Message="message";	//登录提示信息
	public static final String LoginUserCheck="用户名错误";
	public static final String LoginuserNull="用户名账号错误";
	public static final String LoginNot="密码错误，请重新登录";
	public static final String NotLogin="尚未登录";
	public static final String USERMENUERROR="数据查询错误";
	public static final String USERNOTUSEING="改管理员不可用无法操作权限";
	public static final String USERADMINFORIN="该用户名已经存在";
	public static final String USERPWDNOTONE="两次密码不一致";
	public static final String USERINSERTNO="管理员注册失败";
	/**提示语信息**/
	
}
