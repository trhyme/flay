package com.java.letch.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.java.letch.dao.VoicesInfoDao;
import com.java.letch.model.VoicesInfo;
import com.java.letch.model.view.VoicesUserView;
import com.java.letch.serviceimp.VoicesInfoServiceImp;

/***
 * 音频、视频、动态信息数据操作类
 * @author Administrator
 * 2017-09-20
 */
@Repository(value="VoicesInfoService")
public class VoicesInfoService implements VoicesInfoServiceImp {

	@Resource(name="VoicesInfoDao")
	private VoicesInfoDao voicesInfoDao;		//音频、视频、动态信息数据操作类
	
	/*后台分页查询信息*/
	@Override
	public Map<String, Object> selectVoicesPageList(Map<String, Object> map) {
		return this.voicesInfoDao.selectVoicesPageList(map);
	}

	//查询总数
	@Override
	public int selectVoicesAllNum() {
		return this.voicesInfoDao.selectVoicesAllNum();
	}
	
	/***1.根据动态ID查询评论的详细信息***/
	@Override
	public VoicesUserView selectVoicesUser(int voicesid) {
		return this.voicesInfoDao.selectVoicesUser(voicesid);
	}

	/***2.添加数据信息***/
	@Override
	public boolean insertVoicesToDB(VoicesInfo voice) {
		return this.voicesInfoDao.insertVoicesToDB(voice);
	}

	/***3.根据用户的编号查询动态信息***/
	@Override
	public Map<String, Object> selectVoicesByUserid(int userid) {
		return this.voicesInfoDao.selectVoicesByUserid(userid);
	}

	/***4.浏览量+1***/
	@Override
	public boolean updateplaycount(int voicesid) {
		return this.voicesInfoDao.updateplaycount(voicesid);
	}
	
	/****5.修改动态的状态是否可用****/
	@Override
	public boolean updateVoicesWork(int voicesid, int statrus) {
		return this.voicesInfoDao.updateVoicesWork(voicesid, statrus);
	}

	/***6.根据动态ID查询评论的详细信息2【更多详细】***/
	@Override
	public VoicesUserView selectVoicesUserTwo(int voicesid, int userid) {
		return this.voicesInfoDao.selectVoicesUserTwo(voicesid, userid);
	}
	
}
