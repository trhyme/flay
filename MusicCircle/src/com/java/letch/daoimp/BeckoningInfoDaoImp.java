package com.java.letch.daoimp;

import java.util.Map;

import com.java.letch.model.BeckoningInfo;

/***
 * 心动设置的数据库操作接口类
 * @author Administrator
 * 2017-09-26
 */
public interface BeckoningInfoDaoImp {
	
	//分页查询出数据信息
	public Map<String, Object> selectBeckPageList(Map<String, Object> map);  
	
	//添加信息
	public boolean insertIntoBeckToDB(BeckoningInfo beck);
	
	//根据编号查询单个信息
	public BeckoningInfo selectById(int id);

	//修改信息入库
	public boolean updateBeckInfoToDB(BeckoningInfo beck);
	
	
	
}
