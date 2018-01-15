package com.java.letch.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.java.letch.dao.CollectionInfoDao;
import com.java.letch.model.CollectionInfo;
import com.java.letch.serviceimp.CollectionInfoServiceImp;

/***
 * 收藏动态系信息操作
 * @author Administrator
 * 2017-10-24
 */
@Repository(value="CollectionInfoService")
public class CollectionInfoService implements CollectionInfoServiceImp {
	
	@Resource(name="CollectionInfoDao")
	private CollectionInfoDao collectioninfoDao;		//收藏数据信息操作类

	//1.添加信息
	@Override
	public int insertCollectionToDB(CollectionInfo coll) {
		return this.collectioninfoDao.insertCollectionToDB(coll);
	}

	//2.根据用户、动态编号查询出单挑数据
	@Override
	public CollectionInfo selectOneConll(int userid, int cid) {
		return this.collectioninfoDao.selectOneConll(userid, cid);
	}

	//3.查询总的收藏数
	@Override
	public int selectCountNumByID(int userid, int voicesid) {
		return this.collectioninfoDao.selectCountNumByID(userid, voicesid);
	}

	//4.根据编号修改总的收藏数
	@Override
	public boolean updateVoicesCollNum(int voicesid, int count) {
		return this.collectioninfoDao.updateVoicesCollNum(voicesid, count);
	}
	
}
