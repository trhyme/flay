package com.java.letch.model.view;

import java.io.Serializable;

/***
 * 来访纪录的逻辑视图
 * @author Administrator
 * 2017-11-14
 */
@SuppressWarnings("serial")
public class RecentVisitView implements Serializable{
	public RecentVisitView(){}
	//属性
	private int id;					//主键ID
	private int userid;				//用户的ID
	private int userseeid;			//被查看用户的ID	
	private String addtimes;		//访问时间
	
	//用户属性
	private String username;	//昵称
	private String headpic;		//头像
	private String sex;			//声音（男声，女声）
	private String province;	//省
	private String city;		//市
	private String area;		//区 
	private String sign;		//签名（约单语）
	private int age;			//年龄
	private String friendstate;	//新增交友状态字段（交友、恋爱）
	
	//新增字段
	public int checkmember=0;	//是否为会员信息
	
	public String getFriendstate() {
		return friendstate;
	}
	public void setFriendstate(String friendstate) {
		this.friendstate = friendstate;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
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
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	
	
	
}
