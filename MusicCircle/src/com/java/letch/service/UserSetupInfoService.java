package com.java.letch.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.java.letch.dao.UserSetupInfoDao;
import com.java.letch.model.UserSetupInfo;
import com.java.letch.serviceimp.UserSetupInfoServiceImp;

/***
 * APP用户的设置消息推送的数据操作类
 * @author Administrator
 * 2017-11-07
 */
@Repository(value="UserSetupInfoService")
public class UserSetupInfoService implements UserSetupInfoServiceImp {

	@Resource(name="UserSetupInfoDao")
	private UserSetupInfoDao usersetupinfoDao;		//APP用户信息推送设置类
	
	//1.添加设置信息
	@Override
	public boolean insertUserSetupToDB(UserSetupInfo usersetup) {
		return this.usersetupinfoDao.insertUserSetupToDB(usersetup);
	}

	//2.根据用户编号查询信息
	@Override
	public UserSetupInfo selectUserSetupByUserID(int uid,int typeid) {
		return this.usersetupinfoDao.selectUserSetupByUserID(uid,typeid);
	}

}
