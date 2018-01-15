package com.java.letch.model;

import java.io.Serializable;

/***
 * 动态评论表
 * @author Administrator
 * 2017-10-19
 */
@SuppressWarnings("serial")
public class CommentsInfo implements Serializable{
	public CommentsInfo(){}
	//属性
	private int id;				//主键ID
	private String date;		//日期
	private String info;		//评论内容
	private int users_id;		//用户的编号
	private int voices_id;		//声音动态编号
	private int type;			//是否为弹幕 1、普通评价 2、弹幕评价 2.0 3、声评   4、表示点赞
	private String voiceurl;		//声评URL 2.0
	private String length;			//音频长度 2.0
	private int tapcount;		//点赞数
	
	//新增回复字段
	private Integer replyuser;	//回复给谁的ID
	
	
	public Integer getReplyuser() {
		return replyuser;
	}
	public void setReplyuser(Integer replyuser) {
		this.replyuser = replyuser;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public int getUsers_id() {
		return users_id;
	}
	public void setUsers_id(int users_id) {
		this.users_id = users_id;
	}
	public int getVoices_id() {
		return voices_id;
	}
	public void setVoices_id(int voices_id) {
		this.voices_id = voices_id;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getVoiceurl() {
		return voiceurl;
	}
	public void setVoiceurl(String voiceurl) {
		this.voiceurl = voiceurl;
	}
	public String getLength() {
		return length;
	}
	public void setLength(String length) {
		this.length = length;
	}
	public int getTapcount() {
		return tapcount;
	}
	public void setTapcount(int tapcount) {
		this.tapcount = tapcount;
	}
	
}
