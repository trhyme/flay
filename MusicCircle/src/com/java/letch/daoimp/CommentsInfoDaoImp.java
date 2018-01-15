package com.java.letch.daoimp;

import java.util.Map;

import com.java.letch.model.CommentsInfo;
import com.java.letch.model.view.CommitsUserView;

/***
 * 声音评论信息
 * @author Administrator
 * 2017-0-19
 */
public interface CommentsInfoDaoImp {
	
	//1.用户添加评论信息
	public boolean insertIntoCommentsOne(CommentsInfo comm);
	
	//2.根据语音动态编号查询出评论信息
	public Map<String, Object> selectCommitsPage(Map<String, Object> map,int voicesid);
	
	//3.根据动态编号查询总的评论数
	public int selectCommentCountById(int voicesid);
	
	//4.跟新评论总数
	public boolean updateVoicesCommentnumById(int voicesid,int count);
	
	//5.根据编号查询单个信息
	public CommitsUserView seelctCommitsUserViewOne(int id);
	
	//6.根据用户编号查询出新的回复消息
	public Map<String, Object> selectCommitsUserViceosView(Map<String, Object> map,int userid);
	
}
