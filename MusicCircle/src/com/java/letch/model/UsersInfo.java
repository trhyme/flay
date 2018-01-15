package com.java.letch.model;

import java.io.Serializable;

/***
 * APP用户信息类
 * @author Administrator
 * 2017-09-18
 */
@SuppressWarnings("serial")
public class UsersInfo implements Serializable{
	public UsersInfo(){}
	//属性
	private int id;			//主键ID
	private String num;		//随机id（8-9位不重复的）
	private String phone;	//手机号
	private String password;	//密码
	private String username;	//昵称
	private String headpic;		//头像
	private String sex;			//声音（男声，女声）
	private String birth;		//生日
	private String province;	//省
	private String city;		//市
	private String area;		//区 
	private String sign;		//签名（约单语）
	private String money;		//余额（收益）
	private Integer omsg;			//发单信息（如果有人上传录音则为1，否则为0） q
	private Integer umsg;			//粉丝信息（有人关注我则显示为1，否则为0） q
	private Integer auth;			//是否可用 0、未 1、已 (默认为1)
	private String date;		//注册日期
	private String recharge;	//余额（充值）
	private String openid;		//微信登录标识符
	private String price;		//声音价格 2.0
	private Integer contribution;	//贡献值 2.0
	private Integer ischeck;		//是否认证  1、未认证  2、正在审核 3、已认证
	private String types_id;	//标签2.0
	private String android_ios;	//安卓_IOS传入的ID
	
	/**新增字段**/
	private String apponlyid;	//安卓_IOS传入的ID，二代
	private String constellation;	//新版星座信息
	private String height;		//新版身高信息
	private String workes;		//新版工作信息
	private String friendstate;	//新增交友状态字段（交友、恋爱）
	
	private Integer isstratus;		//新增用户注册资料判断字段
	private String voicesurl;	//新增用户的资料声音字段
	private String school;		///新增院校字段
	private String greethello;	//新增用户的打招呼内容字段
	private Integer age;
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
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
	public String getBirth() {
		return birth;
	}
	public void setBirth(String birth) {
		this.birth = birth;
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
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	public Integer getOmsg() {
		return omsg;
	}
	public void setOmsg(Integer omsg) {
		this.omsg = omsg;
	}
	public Integer getUmsg() {
		return umsg;
	}
	public void setUmsg(Integer umsg) {
		this.umsg = umsg;
	}
	public Integer getAuth() {
		return auth;
	}
	public void setAuth(Integer auth) {
		this.auth = auth;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getRecharge() {
		return recharge;
	}
	public void setRecharge(String recharge) {
		this.recharge = recharge;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public Integer getContribution() {
		return contribution;
	}
	public void setContribution(Integer contribution) {
		this.contribution = contribution;
	}
	public Integer getIscheck() {
		return ischeck;
	}
	public void setIscheck(Integer ischeck) {
		this.ischeck = ischeck;
	}
	public String getTypes_id() {
		return types_id;
	}
	public void setTypes_id(String types_id) {
		this.types_id = types_id;
	}
	public String getAndroid_ios() {
		return android_ios;
	}
	public void setAndroid_ios(String android_ios) {
		this.android_ios = android_ios;
	}
	public String getApponlyid() {
		return apponlyid;
	}
	public void setApponlyid(String apponlyid) {
		this.apponlyid = apponlyid;
	}
	public String getConstellation() {
		return constellation;
	}
	public void setConstellation(String constellation) {
		this.constellation = constellation;
	}
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	public String getWorkes() {
		return workes;
	}
	public void setWorkes(String workes) {
		this.workes = workes;
	}
	public String getFriendstate() {
		return friendstate;
	}
	public void setFriendstate(String friendstate) {
		this.friendstate = friendstate;
	}
	public Integer getIsstratus() {
		return isstratus;
	}
	public void setIsstratus(Integer isstratus) {
		this.isstratus = isstratus;
	}
	public String getVoicesurl() {
		return voicesurl;
	}
	public void setVoicesurl(String voicesurl) {
		this.voicesurl = voicesurl;
	}
	public String getSchool() {
		return school;
	}
	public void setSchool(String school) {
		this.school = school;
	}
	public String getGreethello() {
		return greethello;
	}
	public void setGreethello(String greethello) {
		this.greethello = greethello;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	
	
}
