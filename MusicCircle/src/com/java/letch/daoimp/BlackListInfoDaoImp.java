package com.java.letch.daoimp;

import java.util.Map;

import com.java.letch.model.BlackListInfo;

/***
 * 黑名单的
 * @author Administrator
 * 2017-11-10
 */
public interface BlackListInfoDaoImp {
	
	//1.添加黑名称
	public boolean insertBlackListIntoDB(BlackListInfo black);
	
	//2.分页查询黑名单信息
	public Map<String, Object> seelctBlockListPage(Map<String, Object> map,int userid);
	
	//3.解除拉黑名单信息
	public boolean userBlockRemove(int userid,int blockuserid);
	
}
