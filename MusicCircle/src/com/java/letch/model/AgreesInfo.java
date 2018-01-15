package com.java.letch.model;

import java.io.Serializable;

/***
 * 点赞信息表
 * @author Administrator
 * 2017-10-24
 */ 
@SuppressWarnings("serial")
public class AgreesInfo implements Serializable{
	public AgreesInfo(){}
	/*属性*/
	private int id;				//主键
	private String date;		//时间
	private int users_id;		//用户ID
	private int voices_id;		//动态ID
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
	
}
