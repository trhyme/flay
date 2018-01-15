package com.java.letch.model;

import java.io.Serializable;

/***
 * APP首页Banner图信息类
 * @author Administrator
 * 2017-09-19
 */
@SuppressWarnings("serial")
public class BannerInfo implements Serializable{
	public BannerInfo(){}
	/*属性*/
	private int id;				//主键ID
	private String title;		//图片名称
	private String picurl;		//banner图路径
	private int ordernum;		//顺序
	private String addtimes;	//添加时间
	private int stratus;		//是否删除(0,删除，1未删除)
	private int onlines;		//是否下架(0 下架，1 未下架)
	private int admids;			//管理员ID
	private String remark;		//备注
	
	//新增字段
	private String htmlurl;		//HTML页面的链接
	
	public String getHtmlurl() {
		return htmlurl;
	}
	public void setHtmlurl(String htmlurl) {
		this.htmlurl = htmlurl;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPicurl() {
		return picurl;
	}
	public void setPicurl(String picurl) {
		this.picurl = picurl;
	}
	public int getOrdernum() {
		return ordernum;
	}
	public void setOrdernum(int ordernum) {
		this.ordernum = ordernum;
	}
	public String getAddtimes() {
		return addtimes;
	}
	public void setAddtimes(String addtimes) {
		this.addtimes = addtimes;
	}
	public int getStratus() {
		return stratus;
	}
	public void setStratus(int stratus) {
		this.stratus = stratus;
	}
	public int getOnlines() {
		return onlines;
	}
	public void setOnlines(int onlines) {
		this.onlines = onlines;
	}
	public int getAdmids() {
		return admids;
	}
	public void setAdmids(int admids) {
		this.admids = admids;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
