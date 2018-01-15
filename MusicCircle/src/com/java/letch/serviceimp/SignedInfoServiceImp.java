package com.java.letch.serviceimp;

import com.java.letch.model.SignedInfo;

/***
 * 用户签到信息业务数据操作类
 * @author Administrator
 * 2017-10-13
 */
public interface SignedInfoServiceImp {
	
	//1.添加签到信息
	public boolean addSignedToDB(SignedInfo sign);
	
	//2.查询单条信息
	public SignedInfo selectSignedInfoByID(int userid);
	
}
