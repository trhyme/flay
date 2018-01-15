package com.java.letch.model;

import java.io.Serializable;

/***
 * 用户反馈信息类
 * @author Administrator
 * 2017-10-09
 */
@SuppressWarnings("serial")
public class FeedbackInfo implements Serializable{
	public FeedbackInfo(){}
	/*属性*/
	private int id;				//主键ID
	private int uid;			//反馈用户主键
	private String uuid;		//反馈用户唯一标示ID
	private String uphone;		//反馈用户电话号码
	private String unames;		//反馈用户昵称
	private String conten;		//反馈内容
	private String addtime;		//时间
	private String replyes;		//回复内容
	private String uptimes;		//回复时间
	private int stratus;		//是否处理（0未处理，1已处理）
	private String remarks;		//备注
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getUphone() {
		return uphone;
	}
	public void setUphone(String uphone) {
		this.uphone = uphone;
	}
	public String getUnames() {
		return unames;
	}
	public void setUnames(String unames) {
		this.unames = unames;
	}
	public String getConten() {
		return conten;
	}
	public void setConten(String conten) {
		this.conten = conten;
	}
	public String getAddtime() {
		return addtime;
	}
	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}
	public String getReplyes() {
		return replyes;
	}
	public void setReplyes(String replyes) {
		this.replyes = replyes;
	}
	public String getUptimes() {
		return uptimes;
	}
	public void setUptimes(String uptimes) {
		this.uptimes = uptimes;
	}
	public int getStratus() {
		return stratus;
	}
	public void setStratus(int stratus) {
		this.stratus = stratus;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
}
