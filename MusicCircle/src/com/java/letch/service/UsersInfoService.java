package com.java.letch.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.java.letch.dao.UsersInfoDao;
import com.java.letch.model.IphoneConfigure;
import com.java.letch.model.UsersInfo;
import com.java.letch.model.view.MatchUserView;
import com.java.letch.model.view.VoicesUserView;
import com.java.letch.serviceimp.UsersInfoServiceImp;

/***
 * APP用户数据操作
 * @author Administrator
 * 2017-09-18
 */
@Repository(value="UsersInfoService")
public class UsersInfoService implements UsersInfoServiceImp{

	@Resource(name="UsersInfoDao")
	private UsersInfoDao usernfoDao;	//APP用户数据的操作类
	
	//后台分页查看APP用户信息
	@Override
	public Map<String, Object> selectUserListByPage(Map<String, Object> map) {
		return this.usernfoDao.selectUserListByPage(map);
	}

	/**APP操作**/
	/* 1.根据手机号码查询是否被注册 */
	@Override
	public boolean selectUserByPhone(String phone) {
		return this.usernfoDao.selectUserByPhone(phone);
	}

	/* 2.手机号码注册 */
	@Override
	public boolean insertIntoUserToDB(UsersInfo user) {
		return this.usernfoDao.insertIntoUserToDB(user);
	}

	/* 3.根据唯一NUM查询用户 */
	@Override
	public UsersInfo selectUserOneByNum(String num) {
		return this.usernfoDao.selectUserOneByNum(num);
	}

	/* 4.根据编号查询用户信息 */
	@Override
	public UsersInfo selectUserForPhone(String phone) {
		return this.usernfoDao.selectUserForPhone(phone);
	}

	/* 5.修改用户信息 */
	@Override
	public boolean updateUserByUser(UsersInfo user) {
		return this.usernfoDao.updateUserByUser(user);
	}

	/***6. 根据APPID判断是否登录 ***/
	@Override
	public UsersInfo selectAppUserByAPPid(String appid) {
		return this.usernfoDao.selectAppUserByAPPid(appid);
	}

	/**7.修改资料信息**/
	@Override
	public boolean updateAppUserByUser(UsersInfo user) {
		return this.usernfoDao.updateAppUserByUser(user);
	}
	
	/****判断是否APP登录根据APPID****/
	public Map<String,Object> checkAppUserByAppID(String appid,Map<String,Object> map){
		try {
			UsersInfo user=selectAppUserByAPPid(appid);
			if(user!=null){
				map.put("code",200);	//成功
				map.put("user", user);
			}else{
				map.put("user", null);
				map.put("code",300);	//用户AppID错误
			}
		} catch (Exception e) {
			map.put("user", null);
			map.put("code", 404);		//出错了
		}
		return map;
	}
	/****判断是否APP登录根据APPID****/

	//查询总条数的SQL
	@Override
	public int selectAllUserCounts() {
		return this.usernfoDao.selectAllUserCounts();
	}

	/**9.根据用户的主键ID查询信息**/
	@Override
	public UsersInfo selectAppUserByID(int id) {
		return this.usernfoDao.selectAppUserByID(id);
	}

	/*******10.查询出所有用户的电话号码******/
	@Override
	public List<String> selectAllPhone() {
		return this.usernfoDao.selectAllPhone();
	}

	/****11.根据AppID查询出关注用户的动态信息****/
	@Override
	public Map<String, Object> selectAppIndexFollowVoices(Map<String, Object> map, String appid) {
		return this.usernfoDao.selectAppIndexFollowVoices(map, appid);
	}

	/****12.根据用户的ID查询出对应的粉丝信息（分页）***/
	@Override
	public Map<String, Object> selectAppUserFansByID(Map<String, Object> map, int userid) {
		return this.usernfoDao.selectAppUserFansByID(map, userid);
	}

	/****13.首页查询最新动态****/
	@Override
	public Map<String, Object> selectAppIndexNowVoices(Map<String, Object> map) {
		return this.usernfoDao.selectAppIndexNowVoices(map);
	}

	/****14.根据APPID修改用户的头像信息****/
	@Override
	public boolean updateAppuserHeadPhoto(String appid, String url) {
		return this.usernfoDao.updateAppuserHeadPhoto(appid, url);
	}
	
	/****15.根据用户的APPID上传修改用户的资料声音****/
	@Override
	public boolean updateAppuserVoices(String appid, String url) {
		return this.usernfoDao.updateAppuserVoices(appid, url);
	}
	
	/****16.根据用户的APPID修改用户的昵称和性别信息****/
	@Override
	public boolean updateAppuserNickname(String appid, String nickname, String sex,String fredstate,String province,String city,String area,String birth) {
		return this.usernfoDao.updateAppuserNickname(appid, nickname, sex, fredstate, province, city, area, birth);
	}
	
	/***17.根据编号设置用户的个性签名***/
	@Override
	public boolean updateAppUserSign(String appid, String sign) {
		return this.usernfoDao.updateAppUserSign(appid, sign);
	}
	
	/***18.修改密码操作***/
	@Override
	public boolean updateAppUserPwds(String phone, String pwds) {
		return this.usernfoDao.updateAppUserPwds(phone, pwds);
	}
	
	/***19.修改用户的基本信息***/
	@Override
	public boolean updateBasicData(String appid, int age, String birth, String height, String workes, String school,
			String province, String city, String area) {
		return this.usernfoDao.updateBasicData(appid, age, birth, height, workes, school, province, city, area);
	}
	
	/***20.修改介绍声音***/
	@Override
	public boolean updateUserVoices(String appid, String voices) {
		return this.usernfoDao.updateUserVoices(appid, voices);
	}
	
	/***21.修改标签信息***/
	@Override
	public boolean updateLabelByAppID(String appid, String label) {
		return this.usernfoDao.updateLabelByAppID(appid, label);
	}
	
	/***22.修改用户的打招呼内容***/
	@Override
	public boolean updateGreetHello(String appid, String greethello) {
		return this.usernfoDao.updateGreetHello(appid, greethello);
	}
	
	/***23.匹配查询***/
	@Override
	public Map<String, Object> matchingUserList(int userid, String agebegin, String ageend, String sex, String cityarea,
			String height, String ischeck,int nowpage,Map<String, Object> map) {
		return this.usernfoDao.matchingUserList(userid, agebegin, ageend, sex, cityarea, height, ischeck,nowpage,map);
	}

	/***24.首页的【最热消息】***/
	@Override
	public Map<String, Object> appIndexHot(Map<String, Object> map) {
		return this.usernfoDao.appIndexHot(map);
	}
	
	/***25.我的发布动态查看***/
	@Override
	public Map<String, Object> selectVoicesMyself(Map<String, Object> map, int userid) {
		return this.usernfoDao.selectVoicesMyself(map, userid);
	}
	
	/***25_1.查看别人的动态信息***/
	@Override
	public Map<String, Object> selectVoicesYour(Map<String, Object> map, int userid, int seeuserid) {
		return this.usernfoDao.selectVoicesYour(map, userid, seeuserid);
	}
	
	/***26.修改动态的是否删除动态***/
	@Override
	public boolean deleteVoicesByID(int voicesid, int userid) {
		return this.usernfoDao.deleteVoicesByID(voicesid, userid);
	}
	
	/***27.修改用户的基本信息***/
	@Override
	public boolean updateAppOneUserByUser(UsersInfo user) {
		return this.usernfoDao.updateAppOneUserByUser(user);
	}
	
	/***28.后台删除用户的声音信息***/
	@Override
	public boolean updateDeleteUserVoices(int id) {
		return this.usernfoDao.updateDeleteUserVoices(id);
	}
	
	/****29.第三方登录：微信、微博、QQ****/
	@Override
	public UsersInfo selectQQWXWBbyOpenID(String openid) {
		return this.usernfoDao.selectQQWXWBbyOpenID(openid);
	}
	
	/***30.修改用户的职业信息***/
	@Override
	public boolean updateUserWorks(int id, String works) {
		return this.usernfoDao.updateUserWorks(id, works);
	}
	
	/***31.修改用户的院校信息***/
	@Override
	public boolean updateUserSchool(int id, String school) {
		return this.usernfoDao.updateUserSchool(id, school);
	}
	
	/****32.首页查询【最新】动态****/
	@Override
	public Map<String, Object> selectAppIndexSeeUserVoices(Map<String, Object> map, int seeuserid) {
		return this.usernfoDao.selectAppIndexSeeUserVoices(map, seeuserid);
	}

	/****33.获取iPhone的设置****/
	@Override
	public List<IphoneConfigure> selectIphoneConfigure() {
		return this.usernfoDao.selectIphoneConfigure();
	}
	
	/****34.设置iPhone用户App的支付方式****/
	@Override
	public boolean updateIphoneConfigure(int strat) {
		return this.usernfoDao.updateIphoneConfigure(strat);
	}
	
	/****35.随机获取一个用户信息****/
	@Override
	public UsersInfo selectRandomUserOne(int myselectuserid, String sex) {
		return this.usernfoDao.selectRandomUserOne(myselectuserid, sex);
	}
	
	/*****36.修改用户的交友状态******/
	@Override
	public boolean updateFriendsState(int userid, String friends) {
		return this.usernfoDao.updateFriendsState(userid, friends);
	}
	
	/*****37.用户的点击【收藏】信息****/
	@Override
	public Map<String, Object> selectCollectionVoicesPage(Map<String, Object> map,int userid) {
		return this.usernfoDao.selectCollectionVoicesPage(map,userid);
	}
	
	/*****38.修改用户的昵称*****/
	@Override
	public boolean updateUserNameByID(int userid, String username) {
		return this.usernfoDao.updateUserNameByID(userid, username);
	}
	
	/*****39.根据昵称查询用户信息******/
	@Override
	public List<UsersInfo> selectUserByUserName(String username) {
		return this.usernfoDao.selectUserByUserName(username);
	}
	
	/******40.修改手机唯一标识*****/
	@Override
	public boolean updatePhoneOnlyID(String phoneid, int userid) {
		return this.usernfoDao.updatePhoneOnlyID(phoneid, userid);
	}
	
}
