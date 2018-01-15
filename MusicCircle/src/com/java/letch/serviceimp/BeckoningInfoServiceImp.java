package com.java.letch.serviceimp;

import java.util.Map;

import com.java.letch.model.BeckoningInfo;

/***
 * 心动信息业务操作类接口
 * @author Administrator
 * 2017-09-26
 */
public interface BeckoningInfoServiceImp {

	//分页查询出数据信息
	public Map<String, Object> selectBeckPageList(Map<String, Object> map);  
	
	//添加信息
	public boolean insertIntoBeckToDB(BeckoningInfo beck);
		
	//根据编号查询单个信息
	public BeckoningInfo selectById(int id);
	
	//修改信息入库
	public boolean updateBeckInfoToDB(BeckoningInfo beck);
		
}
