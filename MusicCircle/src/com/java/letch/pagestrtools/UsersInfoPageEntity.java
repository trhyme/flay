package com.java.letch.pagestrtools;

/***
 * APP用户的数据常量信息
 * @author Administrator
 * 2017-09-18
 */
public class UsersInfoPageEntity {
	
	/**主路径**/
	private static final String USERPAGE="/ADMINS";
	
	//后台查看APP用户的信息信息页面
	public static final String USERLISTPAGE=USERPAGE+"/userapplist";
	
	//展示用户的关注信息以及被关注信息
	public static final String USERFOLLOWPAGE=USERPAGE+"/userfollow_list";
	
	//用户的粉丝
	public static final String USERFANSPAGE=USERPAGE+"/userfans_list2";
	
	//查看用户的详细信息
	public static final String USERLOOKINGPAGE=USERPAGE+"/userlooking";
	
	/*常量提示数据*/
	public static final String APPCODESESSION="appcode";	//APP注册短信验证码
	public static final String APPPHONESESSION="appphone";	//手机号码
	
	public static final String ShORTMESSAGECODE="";
	/*常量提示数据*/
	
	
}
