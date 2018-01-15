package com.java.letch.model;

import java.io.Serializable;

/***
 * 会员等级信息表
 * @author Administrator
 * 2017-09-27
 */
@SuppressWarnings("serial")
public class MemberGradeInfo implements Serializable{
	public MemberGradeInfo(){}
	//属性
	private int id;
	private String mname;		//会员名称
	private double prices;		//会员月单价价格
	private String picurl;		//会员图片
	private int stratu;			//是否屏蔽
	private String remark;		//备注
	
	private int daynum;			//会员天数
	
	public int getDaynum() {
		return daynum;
	}
	public void setDaynum(int daynum) {
		this.daynum = daynum;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMname() {
		return mname;
	}
	public void setMname(String mname) {
		this.mname = mname;
	}
	public double getPrices() {
		return prices;
	}
	public void setPrices(double prices) {
		this.prices = prices;
	}
	public String getPicurl() {
		return picurl;
	}
	public void setPicurl(String picurl) {
		this.picurl = picurl;
	}
	public int getStratu() {
		return stratu;
	}
	public void setStratu(int stratu) {
		this.stratu = stratu;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
