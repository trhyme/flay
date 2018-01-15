package com.java.letch.daoimp;

import com.java.letch.model.SendcodeInfo;

/***
 * 用户发送的验证码信息记录数据操作接口
 * @author Administrator
 * 2017-10-16
 */
public interface SendcodeInfoDaoImp {
	
	//保存信息
	public boolean sevaSendCodeInfo(SendcodeInfo send);
	
	//通过手机号码和验证码查询信息
	public boolean selectByphoneCode(String phone,String code);
	
	//根据手机号码和验证码同时查询出验证码信息
	public SendcodeInfo selectByphoneBycode(String phone,String code);
	
}
