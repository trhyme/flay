package com.java.letch.model.view;

import java.io.Serializable;

/***
 * 关注我的用户视图
 * @author Administrator
 * 2017-11-15
 */
@SuppressWarnings("serial")
public class MyFollowView implements Serializable{
	public MyFollowView(){}
	/*属性*/
	private int id;			//主键ID
	private String date;	//日期
	private int users_id;	//主用户AID编号
	private int followsid;	//被关注用户B的ID编号
	
	private int becknum;	//新增心动值字段
	private int mutualnum;	//是否相互关注(1表示互相关注，0表示未互相关注)
	
	//用户信息
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
	public int checkmember=0;	//判断是否为会员
	
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
	public int getBecknum() {
		return becknum;
	}
	public void setBecknum(int becknum) {
		this.becknum = becknum;
	}
	public int getMutualnum() {
		return mutualnum;
	}
	public void setMutualnum(int mutualnum) {
		this.mutualnum = mutualnum;
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
