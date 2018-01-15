package com.java.letch.serviceimp;

import java.util.List;
import java.util.Map;

import com.java.letch.model.IphoneConfigure;
import com.java.letch.model.UsersInfo;
import com.java.letch.model.view.MatchUserView;
import com.java.letch.model.view.VoicesUserView;

/***
 * APP用户的业务处理接口
 * @author Administrator
 * 2017-09-18
 */
public interface UsersInfoServiceImp {
	
	//后台分页查看APP用户信息
	public Map<String, Object> selectUserListByPage(Map<String, Object> map);
	
	/**APP操作**/
	/* 1.根据手机号码查询是否被注册 */
	public boolean selectUserByPhone(String phone);
	
	/* 2.手机号码注册 */
	public boolean insertIntoUserToDB(UsersInfo user);
	
	/* 3.根据唯一NUM查询用户 */
	public UsersInfo selectUserOneByNum(String num);
	
	/* 4.根据编号查询用户信息 */
	public UsersInfo selectUserForPhone(String phone);
	
	/* 5.修改用户信息 */
	public boolean updateUserByUser(UsersInfo user);
	
	/***6. 根据APPID判断是否登录 ***/
	public UsersInfo selectAppUserByAPPid(String appid);
	
	/**7.修改资料信息**/
	public boolean updateAppUserByUser(UsersInfo user);
	
	/**8.查询总的用户数据**/
	public int selectAllUserCounts();
	
	/**9.根据用户的主键ID查询信息**/
	public UsersInfo selectAppUserByID(int id);
	
	/*******10.查询出所有用户的电话号码******/
	public List<String> selectAllPhone();
	
	/****11.根据AppID查询出关注用户的动态信息****/
	public Map<String , Object> selectAppIndexFollowVoices(Map<String, Object> map,String appid);
	
	/****12.根据用户的ID查询出对应的粉丝信息（分页）***/
	public Map<String, Object> selectAppUserFansByID(Map<String, Object> map,int userid);
	
	/****13.首页查询最新动态(分页)****/
	public Map<String, Object> selectAppIndexNowVoices(Map<String, Object> map);
	
	/****14.根据APPID修改用户的头像信息****/
	public boolean updateAppuserHeadPhoto(String appid,String url);
	
	/****15.根据用户的APPID上传修改用户的资料声音****/
	public boolean updateAppuserVoices(String appid,String url);
	
	/****16.根据用户的APPID修改用户的昵称和性别信息****/
	public boolean updateAppuserNickname(String appid,String nickname,String sex,String fredstate,String province,String city,String area,String birth);
	
	/***17.根据编号设置用户的个性签名***/
	public boolean updateAppUserSign(String appid,String sign);
	
	/***18.修改密码操作***/
	public boolean updateAppUserPwds(String phone,String pwds);
	
	/***19.修改用户的基本信息***/
	public boolean updateBasicData(String appid,int age,String birth,String height,String workes,String school,String province,String city,String area);
	
	/***20.修改介绍声音***/
	public boolean updateUserVoices(String appid,String voices);
	
	/***21.修改标签信息***/
	public boolean updateLabelByAppID(String appid,String label);
	
	/***22.修改用户的打招呼内容***/
	public boolean updateGreetHello(String appid,String greethello);
	
	/***23.匹配查询***/
	public Map<String, Object> matchingUserList(int userid,String agebegin,String ageend,String sex,String cityarea,String height,String ischeck,int nowpage,Map<String, Object> map);
	
	/***24.首页的【最热消息】***/
	public Map<String, Object> appIndexHot(Map<String, Object> map);
	
	/***25.我的发布动态查看***/
	public Map<String, Object> selectVoicesMyself(Map<String, Object> map,int userid);
	
	/***25_1.查看别人的动态信息***/
	public Map<String, Object> selectVoicesYour(Map<String, Object> map,int userid,int seeuserid);
	
	/***26.修改动态的是否删除动态***/
	public boolean deleteVoicesByID(int voicesid,int userid);
	
	/***27.修改用户的基本信息***/
	public boolean updateAppOneUserByUser(UsersInfo user);
	
	/***28.后台删除用户的声音信息***/
	public boolean updateDeleteUserVoices(int id);
	
	/****29.第三方登录：微信、微博、QQ 查询根据OPENID****/
	public UsersInfo selectQQWXWBbyOpenID(String openid);
	
	/***30.修改用户的职业信息***/
	public boolean updateUserWorks(int id,String works);
	
	/***31.修改用户的院校信息***/
	public boolean updateUserSchool(int id,String school);
	
	/****32.首页查询【最新】动态****/
	public Map<String, Object> selectAppIndexSeeUserVoices(Map<String, Object> map,int seeuserid);
	
	/****33.获取iPhone的设置****/
	public List<IphoneConfigure> selectIphoneConfigure();
	
	/****34.设置iPhone用户App的支付方式****/
	public boolean updateIphoneConfigure(int strat);
	
	/****35.随机获取一个用户信息****/
	public UsersInfo selectRandomUserOne(int myselectuserid,String sex);
	
	/*****36.修改用户的交友状态******/
	public boolean updateFriendsState(int userid,String friends);
	
	/*****37.用户的点击【收藏】信息****/
	public Map<String, Object> selectCollectionVoicesPage(Map<String, Object> map,int userid);
	
	/*****38.修改用户的昵称*****/
	public boolean updateUserNameByID(int userid,String username);
	
	/*****39.根据昵称查询用户信息******/
	public List<UsersInfo> selectUserByUserName(String username);
	
	/******40.修改手机唯一标识*****/
	public boolean updatePhoneOnlyID(String phoneid,int userid);
	
	
}
