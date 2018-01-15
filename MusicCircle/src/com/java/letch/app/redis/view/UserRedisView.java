package com.java.letch.app.redis.view;

import java.io.Serializable;

/***
 * REDIS的存储用户对象视图类
 * @author Administrator
 * 2017-10-17
 */
@SuppressWarnings("serial")
public class UserRedisView implements Serializable{
	public UserRedisView(){}
	/*属性*/
	private int id;			//主键ID
	private String num;		//随机id（8-9位不重复的）
	private String phone;	//手机号
	private String headpic;		//头像
	private String sex;			//声音（男声，女声）
	private String apponlyid;	//安卓_IOS传入的ID，二代
	private String voicesurl;	//新增用户的资料声音字段
	
	private String username;	//新增用户名称字段
	
	//2017-12-04 新增声音长度字段
	private Integer length;	
	
	//是否关注字段
	public Integer checkbeckoning=0;
	
	
	public Integer getLength() {
		return length;
	}
	public void setLength(Integer length) {
		this.length = length;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
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
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
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
	public String getApponlyid() {
		return apponlyid;
	}
	public void setApponlyid(String apponlyid) {
		this.apponlyid = apponlyid;
	}
	public String getVoicesurl() {
		return voicesurl;
	}
	public void setVoicesurl(String voicesurl) {
		this.voicesurl = voicesurl;
	}
	
}
