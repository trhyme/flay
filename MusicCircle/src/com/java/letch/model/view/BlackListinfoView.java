package com.java.letch.model.view;

import java.io.Serializable;

/***
 * 拉黑名单的视图信息类
 * @author Administrator
 * 2017-11-10
 */
@SuppressWarnings("serial")
public class BlackListinfoView implements Serializable{
	public BlackListinfoView(){}
	//属性
	private int id;				//主键ID
	private int mainuser;		//用户ID
	private int secondid;		//被加入黑名单的用户ID
	private String addtimes;	//时间
	private String remarkes;	//备注字段
		
	//拉黑用户信息
	private String username;	//用户昵称
	private String headpic;		//用户头像
	private String sex;			//性别
	
	
	//新增字段
	public int checkmember=0;	//判断是否为会员
	
	
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
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
	
	
	
}
