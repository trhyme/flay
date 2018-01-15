package com.java.letch.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.java.letch.dao.VoiceOfficialInfoDao;
import com.java.letch.model.VoiceOfficialInfo;
import com.java.letch.serviceimp.VoiceOfficialInfoServiceImp;

/***
 * 录制声音的
 * @author Administrator
 * 2017-10-31
 */
@Repository(value="VoiceOfficialInfoService")
public class VoiceOfficialInfoService implements VoiceOfficialInfoServiceImp {

	//声音录制的数据操作类
	@Resource(name="VoiceOfficialInfoDao")
	private VoiceOfficialInfoDao voiceofficialinfoDao;
	
	@Override
	public Map<String, Object> selectVoicesOfficialPage(Map<String, Object> map) {
		return this.voiceofficialinfoDao.selectVoicesOfficialPage(map);
	}

	//添加录制声音文案信息
	@Override
	public boolean insertIntoVoicesOffer(VoiceOfficialInfo voicesoff) {
		return this.voiceofficialinfoDao.insertIntoVoicesOffer(voicesoff);
	}
	
	//根据编号查询出单挑信息
	@Override
	public VoiceOfficialInfo selectVoicesOffByID(int id) {
		return this.voiceofficialinfoDao.selectVoicesOffByID(id);
	}
	
	//根据编号修改状态
	@Override
	public boolean updateStatusByID(int id, int stratus) {
		return this.voiceofficialinfoDao.updateStatusByID(id, stratus);
	}

	/***1.根据数量随机查询出文案信息***/
	@Override
	public List<VoiceOfficialInfo> selectVoicesByNum(int num, int id) {
		return this.voiceofficialinfoDao.selectVoicesByNum(num, id);
	}
	
	
}
