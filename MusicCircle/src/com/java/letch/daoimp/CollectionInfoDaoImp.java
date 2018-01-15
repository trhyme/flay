package com.java.letch.daoimp;

import com.java.letch.model.CollectionInfo;

/***
 * 动态信息收藏信息操作接口
 * @author Administrator
 * 2017-10-24
 */
public interface CollectionInfoDaoImp {
	
	//1.添加信息
	public int insertCollectionToDB(CollectionInfo coll);
	
	//2.根据用户、动态编号查询出单挑数据
	public CollectionInfo selectOneConll(int userid,int cid);
	
	//3.查询总的收藏数
	public int selectCountNumByID(int userid, int voicesid);
	
	//4.根据编号修改总的收藏数
	public boolean updateVoicesCollNum(int voicesid,int count);
	
}
