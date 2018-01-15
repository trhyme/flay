package com.java.letch.serviceimp;

import java.util.Map;

import com.java.letch.model.GiftsInfo;

/***
 * 礼物业务处理操作接口
 * @author Administrator
 * 2017-09-20
 */
public interface GiftsInfoServiceImp {
	
	/*分页查询礼物信息*/
	public Map<String, Object> selectGiftByPage(Map<String, Object> map);
	
	/*修改或者添加的方法*/
	public boolean insertIntoGift(GiftsInfo gift);
	
	/*根据编号查询单条礼物信息*/
	public GiftsInfo selectGiftsByID(int id);
	
}
