package com.java.letch.serviceimp;

import com.java.letch.model.SendcodeInfo;

/***
 * 短信发送
 * @author Administrator
 * 2017-10-16
 */
public interface SendcodeInfoServiceImp {
	//保存信息
	public boolean sevaSendCodeInfo(SendcodeInfo send);
	
	//通过手机号码和验证码查询信息
	public boolean selectByphoneCode(String phone,String code);
	
	//根据手机号码和验证码同时查询出验证码信息
	public SendcodeInfo selectByphoneBycode(String phone,String code);
	
}
