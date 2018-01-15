package com.java.letch.model;

import java.io.Serializable;

/***
 * 黑名单信息类
 * @author Administrator
 * 2017-11-10
 */
@SuppressWarnings("serial")
public class BlackListInfo implements Serializable{
	public BlackListInfo(){}
	//属性
	private int id;				//主键ID
	private int mainuser;		//用户ID
	private int secondid;		//被加入黑名单的用户ID
	private String addtimes;	//时间
	private String remarkes;	//备注字段
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getMainuser() {
		return mainuser;
	}
	public void setMainuser(int mainuser) {
		this.mainuser = mainuser;
	}
	public int getSecondid() {
		return secondid;
	}
	public void setSecondid(int secondid) {
		this.secondid = secondid;
	}
	public String getAddtimes() {
		return addtimes;
	}
	public void setAddtimes(String addtimes) {
		this.addtimes = addtimes;
	}
	public String getRemarkes() {
		return remarkes;
	}
	public void setRemarkes(String remarkes) {
		this.remarkes = remarkes;
	}
	
}
