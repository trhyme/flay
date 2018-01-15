package com.java.letch.model;

import java.io.Serializable;

/***
 * 系统消息信息数据表
 * @author Administrator
 * 2017-11-16
 */
@SuppressWarnings("serial")
public class SystemNewsInfo implements Serializable{
	public SystemNewsInfo(){}
	//属性
	private int id;				//主键ID
	private String title;		//系统消息标题
	private String infos;		//系统内容
	private int userid;			//指定发给用户ID
	private int typeid;			//消息用户类型【1：指定用户，2：所有用户，3：会员用户】
	private String imgurl;		//封面链接
	private String htmlurl;		//跳转页面链接
	private int stratus;		//是否删除[1未删除，0删除]
	private String addtime;		//消息时间
	private String remark;		//备注
	
	public String getAddtime() {
		return addtime;
	}
	public void setAddtime(String addtime) {
		this.addtime = addtime;
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
	public String getInfos() {
		return infos;
	}
	public void setInfos(String infos) {
		this.infos = infos;
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public int getTypeid() {
		return typeid;
	}
	public void setTypeid(int typeid) {
		this.typeid = typeid;
	}
	public String getImgurl() {
		return imgurl;
	}
	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}
	public String getHtmlurl() {
		return htmlurl;
	}
	public void setHtmlurl(String htmlurl) {
		this.htmlurl = htmlurl;
	}
	public int getStratus() {
		return stratus;
	}
	public void setStratus(int stratus) {
		this.stratus = stratus;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
