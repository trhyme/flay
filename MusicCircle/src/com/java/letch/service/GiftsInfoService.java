package com.java.letch.service;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.java.letch.dao.GiftsInfoDao;
import com.java.letch.model.GiftsInfo;
import com.java.letch.serviceimp.GiftsInfoServiceImp;

/***
 * 礼物信息业务操作类
 * @author Administrator
 * 2017-09-20
 */
@Repository(value="GiftsInfoService")
public class GiftsInfoService implements GiftsInfoServiceImp {

	@Resource(name="GiftsInfoDao")
	private GiftsInfoDao giftsInfoDao;
	
	/*分页查询礼物信息*/
	@Override
	public Map<String, Object> selectGiftByPage(Map<String, Object> map) {
		return this.giftsInfoDao.selectGiftByPage(map);
	}

	/*修改或者添加的方法*/
	@Override
	public boolean insertIntoGift(GiftsInfo gift) {
		return this.giftsInfoDao.insertIntoGift(gift);
	}

	/*根据编号查询单条礼物信息*/
	@Override
	public GiftsInfo selectGiftsByID(int id) {
		return this.giftsInfoDao.selectGiftsByID(id);
	}
	
}
