package com.java.letch.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.java.letch.dao.SendcodeInfoDao;
import com.java.letch.model.SendcodeInfo;
import com.java.letch.serviceimp.SendcodeInfoServiceImp;

/***
 * 用户发送验证码
 * @author Administrator
 * 2017-10-16
 */
@Repository(value="SendcodeInfoService")
public class SendcodeInfoService implements SendcodeInfoServiceImp{

	@Resource(name="SendcodeInfoDao")
	private SendcodeInfoDao sendcodeInfoDao;		//用户发送验证码的数据操作类
	
	//保存或者修改
	@Override
	public boolean sevaSendCodeInfo(SendcodeInfo send) {
		return this.sendcodeInfoDao.sevaSendCodeInfo(send);
	}
	
	//通过手机号码和验证码查询信息
	@Override
	public boolean selectByphoneCode(String phone, String code) {
		return this.sendcodeInfoDao.selectByphoneCode(phone, code);
	}
	
	//根据手机号码和验证码同时查询出验证码信息
	@Override
	public SendcodeInfo selectByphoneBycode(String phone, String code) {
		return this.sendcodeInfoDao.selectByphoneBycode(phone, code);
	}
	
}
