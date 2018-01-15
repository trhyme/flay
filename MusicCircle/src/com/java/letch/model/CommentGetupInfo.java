package com.java.letch.model;

import java.io.Serializable;

/***
 * 评论点赞数据表
 * @author Administrator
 * 2017-11-17
 */
@SuppressWarnings("serial")
public class CommentGetupInfo implements Serializable{
	public CommentGetupInfo(){}
	//属性
	private int id;			//主键ID
	private int userid;		//点赞用户ID
	private int commid;		//评论信息的ID
	private String addtime;	//点赞评论时间
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public int getCommid() {
		return commid;
	}
	public void setCommid(int commid) {
		this.commid = commid;
	}
	public String getAddtime() {
		return addtime;
	}
	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}
	
}
