package com.java.letch.model;

import java.io.Serializable;

/***
 * 会员用户充值信息
 * @author Administrator
 * 2017-09-28
 */
@SuppressWarnings("serial")
public class RechargeInfo implements Serializable{
	public RechargeInfo(){}
	/*属性*/
	private int id;				//主键ID
	private int uid;			//用户主键
	private String uuid;		//用户唯一标示ID
	private String addtime;		//充值时间
	private double addprice;	//充值费用
	private String addmode;		//充值方式
	private int megid;			//会员类型
	private String megname;		//类型名称
	private int stratus;		//是否生效：1为生效，0为未生效
	private int delstra;		//是否删除：1为未删除，0为删除
	private String remark;		//备注
	
	private String orderid;		//订单编号
	private String uphone;		//用户电话号码
	private String unames;		//用户昵称
	
	public String getOrderid() {
		return orderid;
	}
	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
	public String getUphone() {
		return uphone;
	}
	public void setUphone(String uphone) {
		this.uphone = uphone;
	}
	public String getUnames() {
		return unames;
	}
	public void setUnames(String unames) {
		this.unames = unames;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getAddtime() {
		return addtime;
	}
	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}
	public double getAddprice() {
		return addprice;
	}
	public void setAddprice(double addprice) {
		this.addprice = addprice;
	}
	public String getAddmode() {
		return addmode;
	}
	public void setAddmode(String addmode) {
		this.addmode = addmode;
	}
	public int getMegid() {
		return megid;
	}
	public void setMegid(int megid) {
		this.megid = megid;
	}
	public String getMegname() {
		return megname;
	}
	public void setMegname(String megname) {
		this.megname = megname;
	}
	public int getStratus() {
		return stratus;
	}
	public void setStratus(int stratus) {
		this.stratus = stratus;
	}
	public int getDelstra() {
		return delstra;
	}
	public void setDelstra(int delstra) {
		this.delstra = delstra;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
