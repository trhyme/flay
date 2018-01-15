package com.java.letch.model;

import java.io.Serializable;

/****
 * 用户亲密度的信息表
 * @author Administrator
 * 2017-11-23
 */
@SuppressWarnings("serial")
public class IntimacyInfo implements Serializable{
	public IntimacyInfo(){}
	//属性
	private int id;			//主键ID
	private int useroneid;	//用户A的ID
	private int usertwoid;	//用户B的ID
	private int intimacye;	//亲密度
	private String addtime;	//最后一次（对聊天数的）加亲密度时间
	private int addnumber;	//最后一次（对聊天数的）加亲密度多少
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUseroneid() {
		return useroneid;
	}
	public void setUseroneid(int useroneid) {
		this.useroneid = useroneid;
	}
	public int getUsertwoid() {
		return usertwoid;
	}
	public void setUsertwoid(int usertwoid) {
		this.usertwoid = usertwoid;
	}
	public int getIntimacye() {
		return intimacye;
	}
	public void setIntimacye(int intimacye) {
		this.intimacye = intimacye;
	}
	public String getAddtime() {
		return addtime;
	}
	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}
	public int getAddnumber() {
		return addnumber;
	}
	public void setAddnumber(int addnumber) {
		this.addnumber = addnumber;
	}
	
}
