package com.java.letch.serviceimp;

import java.util.Map;

import com.java.letch.model.AddressInfo;

/***
 * 用户位置信息业务类接口
 * @author Administrator
 * 2017-12-06
 */
public interface AddressInfoServiceImp {

	//1.根据用户ID查询地址
	public AddressInfo selectAddressByUserID(int userid);
		
	//2.新增、更新地理位置信息
	public boolean insertOrUpdateAddress(AddressInfo adress);
	
	//3.后台查询
	public Map<String, Object> selectPageList(Map<String, Object> map);
	
}
