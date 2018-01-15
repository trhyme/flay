package com.java.letch.model;

import java.io.Serializable;

/***
 * 动态信息收藏信息类
 * @author Administrator
 * 2017-10-24
 */
@SuppressWarnings("serial")
public class CollectionInfo implements Serializable{
	public CollectionInfo(){}
	/*属性*/
	private int id;				//主键
	private int userid;			//用户编号
	private int voicesid;		//动态ID编号
	private String date;		//日期
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
	public int getVoicesid() {
		return voicesid;
	}
	public void setVoicesid(int voicesid) {
		this.voicesid = voicesid;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
}
