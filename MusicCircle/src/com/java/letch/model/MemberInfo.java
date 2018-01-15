package com.java.letch.model;

import java.io.Serializable;

/***
 * 用户会员信息类
 * @author Administrator
 * 2017-09-28
 */
@SuppressWarnings("serial")
public class MemberInfo implements Serializable{
	public MemberInfo(){}
	
	/*属性*/
	private int id;				//主键ID
	private int uid;			//用户主键ID
	private String uuid;		//用户唯一标示ID
	private String addtime;		//添加时间
	private String upetime;		//修改时间
	private int megid;			//会员类型
	private String megname;		//类型名称
	private String daysnum;		//会员时间时长
	private String begintime;	//会员开始时间
	private String endestime;	//会员结束时间
	private int strutus;		//是否到期，0为到期，1为正常使用期
	private int disabls;		//是否禁用，0为禁用，1开启
	private double totalcost;	//会员合计金额
	private String remark;		//备注
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
	public String getUpetime() {
		return upetime;
	}
	public void setUpetime(String upetime) {
		this.upetime = upetime;
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
	public String getDaysnum() {
		return daysnum;
	}
	public void setDaysnum(String daysnum) {
		this.daysnum = daysnum;
	}
	public String getBegintime() {
		return begintime;
	}
	public void setBegintime(String begintime) {
		this.begintime = begintime;
	}
	public String getEndestime() {
		return endestime;
	}
	public void setEndestime(String endestime) {
		this.endestime = endestime;
	}
	public int getStrutus() {
		return strutus;
	}
	public void setStrutus(int strutus) {
		this.strutus = strutus;
	}
	public int getDisabls() {
		return disabls;
	}
	public void setDisabls(int disabls) {
		this.disabls = disabls;
	}
	public double getTotalcost() {
		return totalcost;
	}
	public void setTotalcost(double totalcost) {
		this.totalcost = totalcost;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
