package com.java.letch.model.view;

import java.io.Serializable;

/***
 * 用户的粉丝逻辑视图
 * @author Administrator
 * 2017-10-13
 */
@SuppressWarnings("serial")
public class AppUserFansView implements Serializable{
	public AppUserFansView(){}
	/*视图属性*/
	private int id;				//用户编号
	private String num;			//字符唯一主键编号
	private String username;	//用户昵称
	private String headpic;		//用户头像
	private String sign;		//个性签名
	private int followsid;		//用户的ID
	private int mutualnum;		//是否互相关注（0否，1是）
	
	public int getFollowsid() {
		return followsid;
	}
	public void setFollowsid(int followsid) {
		this.followsid = followsid;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
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
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public int getMutualnum() {
		return mutualnum;
	}
	public void setMutualnum(int mutualnum) {
		this.mutualnum = mutualnum;
	}
	
}
