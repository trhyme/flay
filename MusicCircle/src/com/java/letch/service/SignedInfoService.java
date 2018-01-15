package com.java.letch.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.java.letch.dao.SignedInfoDao;
import com.java.letch.model.SignedInfo;
import com.java.letch.serviceimp.SignedInfoServiceImp;

/***
 * 用户信息的
 * @author Administrator
 * 2017-10-13
 */
@Repository(value="SignedInfoService")
public class SignedInfoService implements SignedInfoServiceImp {

	@Resource(name="SignedInfoDao")
	private SignedInfoDao signedinfoDao;		//签到数据操作类
	
	//1.添加签到信息
	@Override
	public boolean addSignedToDB(SignedInfo sign) {
		return this.signedinfoDao.addSignedToDB(sign);
	}

	//2.查询单条信息
	@Override
	public SignedInfo selectSignedInfoByID(int userid) {
		return this.signedinfoDao.selectSignedInfoByID(userid);
	}

}
