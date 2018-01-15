package com.java.letch.serviceimp;

import com.java.letch.model.CommentGetupInfo;

/***
 * 评论点赞的信息业务操作类接口
 * @author Administrator
 * 2017-11-17
 */
public interface CommentGetupInfoServiceImp {
	
	/**1.点赞信息**/
	public int insertIntoCommentGetup(CommentGetupInfo comup) throws Exception;
	
}
