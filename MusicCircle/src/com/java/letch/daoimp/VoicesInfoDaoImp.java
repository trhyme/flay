package com.java.letch.daoimp;

import java.util.Map;

import com.java.letch.model.VoicesInfo;
import com.java.letch.model.view.VoicesUserView;

/***
 * 音频、视频、动态信息接口类
 * @author Administrator
 * 2017-09-20
 */
public interface VoicesInfoDaoImp {
	
	/*后台分页查询信息*/
	public Map<String, Object> selectVoicesPageList(Map<String,Object> map);
	
	//查询动态总数
	public int selectVoicesAllNum();
	
	/***1.根据动态ID查询评论的详细信息***/
	public VoicesUserView selectVoicesUser(int voicesid);
	
	/***2.添加数据信息***/
	public boolean insertVoicesToDB(VoicesInfo voice);
	
	/***3.根据用户的编号查询动态信息***/
	public Map<String, Object> selectVoicesByUserid(int userid);
	
	/***4.浏览量+1***/
	public boolean updateplaycount(int voicesid);
	
	/****5.修改动态的状态是否可用****/
	public boolean updateVoicesWork(int voicesid,int statrus);
	
	/***6.根据动态ID查询评论的详细信息2【更多详细】***/
	public VoicesUserView selectVoicesUserTwo(int voicesid,int userid);
	
}
