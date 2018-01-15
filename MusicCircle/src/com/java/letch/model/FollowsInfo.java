package com.java.letch.model;

import java.io.Serializable;

/**
 * 用户关注信息类
 * @author Administrator
 * 2017-09-26
 */
@SuppressWarnings("serial")
public class FollowsInfo implements Serializable{
	public FollowsInfo(){}
	/*属性*/
	private int id;			//主键ID
	private String date;	//日期
	private int users_id;	//主用户AID编号
	private int followsid;	//被关注用户B的ID编号
	
	/**新增字段**/
	private int becknum;	//新增心动值字段
	private int mutualnum;	//是否相互关注(1表示互相关注，0表示未互相关注)
	
	public int getMutualnum() {
		return mutualnum;
	}
	public void setMutualnum(int mutualnum) {
		this.mutualnum = mutualnum;
	}
	public int getBecknum() {
		return becknum;
	}
	public void setBecknum(int becknum) {
		this.becknum = becknum;
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
	public int getUsers_id() {
		return users_id;
	}
	public void setUsers_id(int users_id) {
		this.users_id = users_id;
	}
	public int getFollowsid() {
		return followsid;
	}
	public void setFollowsid(int followsid) {
		this.followsid = followsid;
	}
	
}
