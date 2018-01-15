package com.java.letch.daoimp;

import com.java.letch.model.UserSetupInfo;

/***
 * APP用户设置
 * @author Administrator
 * 2017-11-07
 */
public interface UserSetupInfoDaoImp {

	//1.添加设置信息
	public boolean insertUserSetupToDB(UserSetupInfo usersetup);
	
	//2.根据用户编号查询信息
	public UserSetupInfo selectUserSetupByUserID(int uid,int typeid);
}
