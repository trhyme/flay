package com.java.letch.service;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.java.letch.dao.CommentsInfoDao;
import com.java.letch.model.CommentsInfo;
import com.java.letch.model.view.CommitsUserView;
import com.java.letch.serviceimp.CommentsInfoServiceImp;

/***
 * 评论信息的液位操作类
 * @author Administrator
 * 2017-10-19
 */
@Repository(value="CommentsInfoService")
public class CommentsInfoService implements CommentsInfoServiceImp {

	@Resource(name="CommentsInfoDao")
	private CommentsInfoDao commentsinfoDao;		//评论信息数据操作类
	
	//1.用户添加评论信息
	@Override
	public boolean insertIntoCommentsOne(CommentsInfo comm) {
		return this.commentsinfoDao.insertIntoCommentsOne(comm);
	}

	//2.根据语音动态编号查询出评论信息
	@Override
	public Map<String, Object> selectCommitsPage(Map<String, Object> map,int voicesid) {
		return this.commentsinfoDao.selectCommitsPage(map,voicesid);
	}
	
	//3.根据动态编号查询总的评论数
	@Override
	public int selectCommentCountById(int voicesid) {
		return this.commentsinfoDao.selectCommentCountById(voicesid);
	}
	
	//4.跟新评论总数
	@Override
	public boolean updateVoicesCommentnumById(int voicesid,int count) {
		return this.commentsinfoDao.updateVoicesCommentnumById(voicesid,count);
	}

	//5.根据编号查询单个信息
	@Override
	public CommitsUserView seelctCommitsUserViewOne(int id) {
		return this.commentsinfoDao.seelctCommitsUserViewOne(id);
	}

	//6.根据用户编号查询出新的回复消息
	@Override
	public Map<String, Object> selectCommitsUserViceosView(Map<String, Object> map,int userid) {
		return this.commentsinfoDao.selectCommitsUserViceosView(map,userid);
	}

}
