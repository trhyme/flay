package com.java.letch.model;

import java.io.Serializable;

/***
 * 用户去签到信息表
 * @author Administrator
 * 2017-10-13
 */
@SuppressWarnings("serial")
public class SignedInfo implements Serializable{
	public SignedInfo(){}
	/*属性*/
	private int id;			//主键ID
	private int userid;		//用户ID
	private String addtime;	//签到时间
	private int addyear;	//年
	private int addmont;	//月
	private int adddays;	//日
	private int signnum;	//签到次数
	private int remarks;	//备注
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
	public String getAddtime() {
		return addtime;
	}
	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}
	public int getAddyear() {
		return addyear;
	}
	public void setAddyear(int addyear) {
		this.addyear = addyear;
	}
	public int getAddmont() {
		return addmont;
	}
	public void setAddmont(int addmont) {
		this.addmont = addmont;
	}
	public int getAdddays() {
		return adddays;
	}
	public void setAdddays(int adddays) {
		this.adddays = adddays;
	}
	public int getSignnum() {
		return signnum;
	}
	public void setSignnum(int signnum) {
		this.signnum = signnum;
	}
	public int getRemarks() {
		return remarks;
	}
	public void setRemarks(int remarks) {
		this.remarks = remarks;
	}
	
}
