package com.java.letch.serviceimp;

import com.java.letch.model.IntimacyInfo;

/***
 * 用户亲密度的业务数据操作类
 * @author Administrator
 * 2017-11-23
 */
public interface IntimacyInfoServiceImp {
	//1.根据用户的ID查询亲密度记录
	public IntimacyInfo selectIntimacyInfoOne(int useridA,int useridB);
	
	//2.跟新修改数据信息
	public boolean insertOrUpdateIntimacy(IntimacyInfo tim);
	
}
