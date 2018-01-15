package com.java.letch.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.java.letch.dao.CommentGetupInfoDao;
import com.java.letch.model.CommentGetupInfo;
import com.java.letch.serviceimp.CommentGetupInfoServiceImp;

/***
 * 评论点赞信息的业务操作类
 * @author Administrator
 * 2017-11-17
 */
@Repository(value="CommentGetupInfoService")
public class CommentGetupInfoService implements CommentGetupInfoServiceImp {

	//评论点赞信息数据操作类
	@Resource(name="CommentGetupInfoDao")
	private CommentGetupInfoDao commentgetupinfoDao;		
	
	/**1.点赞信息**/
	@Override
	public int insertIntoCommentGetup(CommentGetupInfo comup) throws Exception {
		return this.commentgetupinfoDao.insertIntoCommentGetup(comup);
	}

}
