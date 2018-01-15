package com.java.letch.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.java.letch.dao.FeedbackInfoDao;
import com.java.letch.model.FeedbackInfo;
import com.java.letch.serviceimp.FeedbackInfoServiceImp;

/***
 * 用户反馈信息的业务操作类
 * @author Administrator
 * 2017-10-09
 */
@Repository(value="FeedbackInfoService")
public class FeedbackInfoService implements FeedbackInfoServiceImp {

	@Resource(name="FeedbackInfoDao")
	private FeedbackInfoDao feedbackinfoDao;	//用户反馈信息数据的操作类
	
	//分页查询信息
	@Override
	public Map<String, Object> selectFeedBackPage(Map<String, Object> map) {
		return this.feedbackinfoDao.selectFeedBackPage(map);
	}

	//修改回复信息的方法
	@Override
	public boolean updateFeedbackReplyes(int id, String replyes) {
		return this.feedbackinfoDao.updateFeedbackReplyes(id, replyes);
	}

	//添加反馈信息
	@Override
	public boolean insertFeedbackOne(FeedbackInfo feed) {
		return this.feedbackinfoDao.insertFeedbackOne(feed);
	}

	/*首页查询出5条最新的反馈信息*/
	@Override
	public List<FeedbackInfo> selectFeedbackInfoFive(int num) {
		return this.feedbackinfoDao.selectFeedbackInfoFive(num);
	}
	
}
