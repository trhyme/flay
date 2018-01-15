package com.java.letch.model;

import java.io.Serializable;

/***
 * 最近来访纪录信息表
 * @author Administrator
 * 2017-11-14
 */
@SuppressWarnings("serial")
public class RecentVisitInfo implements Serializable{
	public RecentVisitInfo(){}
	//属性
	private int id;					//主键ID
	private int userid;				//用户的ID
	private int userseeid;			//被查看用户的ID	
	private String addtimes;		//访问时间
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
	public int getUserseeid() {
		return userseeid;
	}
	public void setUserseeid(int userseeid) {
		this.userseeid = userseeid;
	}
	public String getAddtimes() {
		return addtimes;
	}
	public void setAddtimes(String addtimes) {
		this.addtimes = addtimes;
	}
	
}
