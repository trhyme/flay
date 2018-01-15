package com.java.letch.daoimp;

import java.util.List;
import java.util.Map;

import com.java.letch.model.VoiceOfficialInfo;

/***
 * 声音的数据操作接口
 * @author Administrator
 * 2017-10-31
 */
public interface VoiceOfficialInfoDaoImp {
	
	//查看录制声音的文案消息
	public Map<String,Object> selectVoicesOfficialPage(Map<String, Object> map);
	
	//添加录制声音文案信息
	public boolean insertIntoVoicesOffer(VoiceOfficialInfo voicesoff);
	
	//根据编号查询出单挑信息
	public VoiceOfficialInfo selectVoicesOffByID(int id);
	
	//根据编号修改状态
	public boolean updateStatusByID(int id,int stratus);
	
	/***1.根据数量随机查询出文案信息***/
	public List<VoiceOfficialInfo> selectVoicesByNum(int num,int id);
	
}
