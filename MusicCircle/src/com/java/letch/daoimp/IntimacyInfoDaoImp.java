package com.java.letch.daoimp;

import com.java.letch.model.IntimacyInfo;

/***
 * 亲密度数据信息操作接口
 * @author Administrator
 * 2017-11-23
 */
public interface IntimacyInfoDaoImp {
	//1.根据用户的ID查询亲密度记录
	public IntimacyInfo selectIntimacyInfoOne(int useridA,int useridB);
	
	//2.跟新修改数据信息
	public boolean insertOrUpdateIntimacy(IntimacyInfo tim);
	
}
