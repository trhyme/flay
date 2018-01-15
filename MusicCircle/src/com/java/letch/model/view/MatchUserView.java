package com.java.letch.model.view;

import java.io.Serializable;

/***
 * 匹配用户的视图类信息
 * @author Administrator
 * 2017-10-23
 */

@SuppressWarnings("serial")
public class MatchUserView implements Serializable{
	public MatchUserView(){}
	//属性
	private int id;			//主键ID
	private String num;		//随机id（8-9位不重复的）
	private String phone;	//手机号
	private String username;	//昵称
	private String headpic;		//头像
	private String sex;			//声音（男声，女声）
	private String province;	//省
	private String city;		//市
	private String area;		//区 
	private String sign;		//签名（约单语）
	private int ischeck;		//是否认证  1、未认证  2、正在审核 3、已认证
	private String height;		//新版身高信息
	private String friendstate;	//新增交友状态字段（交友、恋爱）
	private int age;			//年龄
	private String voicesurl;	//新增用户的资料声音字段
	
	private Integer vipid;		//与会员 的ID
	private String megname;		//类型名称
	private Integer strutus;		//是否到期，0为到期，1为正常使用期
	
	
	//新加字段，判断该用户是否心动
	public int checkbeckoning=0;
	
	//2017-12-04 新增声音的长度字段
	public int length=0;
	
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
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
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
	public int getIscheck() {
		return ischeck;
	}
	public void setIscheck(int ischeck) {
		this.ischeck = ischeck;
	}
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
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
	public String getVoicesurl() {
		return voicesurl;
	}
	public void setVoicesurl(String voicesurl) {
		this.voicesurl = voicesurl;
	}
	
	public String getMegname() {
		return megname;
	}
	public void setMegname(String megname) {
		this.megname = megname;
	}
	public Integer getVipid() {
		return vipid;
	}
	public void setVipid(Integer vipid) {
		this.vipid = vipid;
	}
	public Integer getStrutus() {
		return strutus;
	}
	public void setStrutus(Integer strutus) {
		this.strutus = strutus;
	}
	
	
	
}
