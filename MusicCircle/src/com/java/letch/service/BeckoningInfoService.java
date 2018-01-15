package com.java.letch.service;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.java.letch.dao.BeckoningInfoDao;
import com.java.letch.model.BeckoningInfo;
import com.java.letch.serviceimp.BeckoningInfoServiceImp;

/***
 * 心动信息业务处理类
 * @author Administrator
 * 2017-09-26
 */
@Repository(value="BeckoningInfoService")
public class BeckoningInfoService implements BeckoningInfoServiceImp {
	
	@Resource(name="BeckoningInfoDao")
	private BeckoningInfoDao beckoninginfoDao;		//心动信息数据操作
	
	//分页查询出数据信息
	@Override
	public Map<String, Object> selectBeckPageList(Map<String, Object> map) {
		return this.beckoninginfoDao.selectBeckPageList(map);
	}

	//添加信息
	@Override
	public boolean insertIntoBeckToDB(BeckoningInfo beck) {
		return this.beckoninginfoDao.insertIntoBeckToDB(beck);
	}

	//根据编号查询单个信息
	@Override
	public BeckoningInfo selectById(int id) {
		return this.beckoninginfoDao.selectById(id);
	}

	//修改信息入库
	@Override
	public boolean updateBeckInfoToDB(BeckoningInfo beck) {
		return this.beckoninginfoDao.updateBeckInfoToDB(beck);
	}

}
