package com.java.letch.service;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.java.letch.dao.AddressInfoDao;
import com.java.letch.model.AddressInfo;
import com.java.letch.serviceimp.AddressInfoServiceImp;

/***
 * 用户位置信息业务类
 * @author Administrator
 * 2017-12-06
 */
@Repository(value="AddressInfoService")
public class AddressInfoService implements AddressInfoServiceImp{

	@Resource(name="AddressInfoDao")
	private AddressInfoDao addressinfoDao;	//地理位置数据操作类
	
	//1.根据用户ID查询地址
	@Override
	public AddressInfo selectAddressByUserID(int userid) {
		return this.addressinfoDao.selectAddressByUserID(userid);
	}

	//2.新增、更新地理位置信息
	@Override
	public boolean insertOrUpdateAddress(AddressInfo adress) {
		return this.addressinfoDao.insertOrUpdateAddress(adress);
	}

	//3.后台查询
	@Override
	public Map<String, Object> selectPageList(Map<String, Object> map) {
		return this.addressinfoDao.selectPageList(map);
	}

}
