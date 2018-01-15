package com.java.letch.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.java.letch.dao.IntimacyInfoDao;
import com.java.letch.model.IntimacyInfo;
import com.java.letch.serviceimp.IntimacyInfoServiceImp;

/***
 * 用户亲密度操作信息
 * @author Administrator
 * 2017-11-23
 */
@Repository(value="IntimacyInfoService")
public class IntimacyInfoService implements IntimacyInfoServiceImp{
	
	@Resource(name="IntimacyInfoDao")
	private IntimacyInfoDao intimacyinfoDao;	//数据操作类
	
	//1.根据用户的ID查询亲密度记录
	@Override
	public IntimacyInfo selectIntimacyInfoOne(int useridA, int useridB) {
		return this.intimacyinfoDao.selectIntimacyInfoOne(useridA, useridB);
	}

	//2.跟新修改数据信息
	@Override
	public boolean insertOrUpdateIntimacy(IntimacyInfo tim) {
		return this.intimacyinfoDao.insertOrUpdateIntimacy(tim);
	}
	
}
