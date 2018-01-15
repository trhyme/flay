package com.java.letch.model.view;

import java.io.Serializable;

/***
 * 新的回复信息查询
 * @author Administrator
 * 2017-11-20
 */
@SuppressWarnings("serial")
public class CommitsUserViceosView implements Serializable{
	public CommitsUserViceosView(){}
	//属性
	private int id;				//主键ID
	private String date;		//日期
	private String info;		//评论内容
	private int users_id;		//用户的编号
	private int voices_id;		//声音动态编号
	private int type;			//是否为弹幕 1、普通评价 2、弹幕评价 2.0 3、声评   4、点赞信息
	private String voiceurl;		//声评URL 2.0
	private String length;			//音频长度 2.0
	private int tapcount;		//点赞数
	private Integer replyuser;	//是否回复ID
	private String username;	//昵称
	private String headpic;		//头像
	private String sex;			//性别
	private String replyname;	//回复者姓名
	
	//动态的信息
	private String theinfo;		//动态的内容
	private String thevoiceurl;	//动态的录音地址
	private String thephoto;	//动态的图片组
	private String theusername;	//动态的发布者姓名
	private String theheadpic;	//动态发布者的头像
	private int theuserid;	//动态发布者的ID
	
	private Integer lengthvoices;	//动态语音的长度
	
	
	public Integer getLengthvoices() {
		return lengthvoices;
	}
	public void setLengthvoices(Integer lengthvoices) {
		this.lengthvoices = lengthvoices;
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
	public Integer getReplyuser() {
		return replyuser;
	}
	public void setReplyuser(Integer replyuser) {
		this.replyuser = replyuser;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getHeadpic() {
		return headpic;
	}
	public void setHeadpic(String headpic) {
		this.headpic = headpic;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getReplyname() {
		return replyname;
	}
	public void setReplyname(String replyname) {
		this.replyname = replyname;
	}
	public String getTheinfo() {
		return theinfo;
	}
	public void setTheinfo(String theinfo) {
		this.theinfo = theinfo;
	}
	public String getThevoiceurl() {
		return thevoiceurl;
	}
	public void setThevoiceurl(String thevoiceurl) {
		this.thevoiceurl = thevoiceurl;
	}
	public String getThephoto() {
		return thephoto;
	}
	public void setThephoto(String thephoto) {
		this.thephoto = thephoto;
	}
	public String getTheusername() {
		return theusername;
	}
	public void setTheusername(String theusername) {
		this.theusername = theusername;
	}
	public String getTheheadpic() {
		return theheadpic;
	}
	public void setTheheadpic(String theheadpic) {
		this.theheadpic = theheadpic;
	}
	public int getTheuserid() {
		return theuserid;
	}
	public void setTheuserid(int theuserid) {
		this.theuserid = theuserid;
	}
	
}
