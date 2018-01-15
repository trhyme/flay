package com.java.letch.serviceimp;

import java.util.List;
import java.util.Map;

import com.java.letch.model.FeedbackInfo;

/***
 * 用户反馈信息的业务操作类接口
 * @author Administrator
 * 2017-10-09
 */
public interface FeedbackInfoServiceImp {

	//分页查询
	public Map<String, Object> selectFeedBackPage(Map<String, Object> map);
	
	//修改回复信息的方法
	public boolean updateFeedbackReplyes(int id,String replyes);
	
	//添加反馈信息
	public boolean insertFeedbackOne(FeedbackInfo feed);
	
	/*首页查询出5条最新的反馈信息*/
	public List<FeedbackInfo> selectFeedbackInfoFive(int num);
	
}
