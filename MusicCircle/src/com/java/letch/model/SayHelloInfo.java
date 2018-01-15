package com.java.letch.model;

import java.io.Serializable;

/***
 * 用户的打招呼记录信息表
 * @author Administrator
 * 2017-12-12
 */
@SuppressWarnings("serial")
public class SayHelloInfo implements Serializable{
	public SayHelloInfo(){}
	//属性
	private Integer id;				//主键ID
	private Integer userid;			//打招呼用户编号
	private Integer sayuserid;		//被打招呼的用户ID
	private String addtime;			//时间
	private Integer addyear;		//年
	private Integer addmont;		//月
	private Integer adddays;		//日
	private Integer signnum;		//打招呼
	private Integer remarks;			//备注
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getUserid() {
		return userid;
	}
	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	public Integer getSayuserid() {
		return sayuserid;
	}
	public void setSayuserid(Integer sayuserid) {
		this.sayuserid = sayuserid;
	}
	public String getAddtime() {
		return addtime;
	}
	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}
	public Integer getAddyear() {
		return addyear;
	}
	public void setAddyear(Integer addyear) {
		this.addyear = addyear;
	}
	public Integer getAddmont() {
		return addmont;
	}
	public void setAddmont(Integer addmont) {
		this.addmont = addmont;
	}
	public Integer getAdddays() {
		return adddays;
	}
	public void setAdddays(Integer adddays) {
		this.adddays = adddays;
	}
	public Integer getSignnum() {
		return signnum;
	}
	public void setSignnum(Integer signnum) {
		this.signnum = signnum;
	}
	public Integer getRemarks() {
		return remarks;
	}
	public void setRemarks(Integer remarks) {
		this.remarks = remarks;
	}
	
	
	
}
