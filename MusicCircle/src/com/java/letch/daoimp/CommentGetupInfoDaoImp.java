package com.java.letch.daoimp;

import com.java.letch.model.CommentGetupInfo;

/***
 * 评论点赞的数据操作类接口
 * @author Administrator
 * 2017-11-17
 */
public interface CommentGetupInfoDaoImp {
	
	/**1.点赞信息**/
	public int insertIntoCommentGetup(CommentGetupInfo comup) throws Exception;
	
}
