package com.java.letch.model;

import java.io.Serializable;

/***
 * 用户信息审核认证信息类
 * @author Administrator
 * 2017-09-29
 */
@SuppressWarnings("serial")
public class ToexamineInfo implements Serializable{
	public ToexamineInfo(){}
	/*属性*/
	private int id;				//主键ID
	private int uid;			//用户主键
	private String uuid;		//用户唯一标示ID
	private String uphone;		//用户电话号码
	private String unames;		//用户昵称
	private String usex;		//性别
	private String certpic;		//认证头像图片
	private String piclist;		//展示图片集合
	private String addtime;		//添加认证时间
	private String updtime;		//修改时间
	private int stratus;		//认证情况（0待审核，1审核通过，2审核未通过）
	private int delstra;		//是否删除：1为未删除，0为不删除
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
	public String getUsex() {
		return usex;
	}
	public void setUsex(String usex) {
		this.usex = usex;
	}
	public String getCertpic() {
		return certpic;
	}
	public void setCertpic(String certpic) {
		this.certpic = certpic;
	}
	public String getPiclist() {
		return piclist;
	}
	public void setPiclist(String piclist) {
		this.piclist = piclist;
	}
	public String getAddtime() {
		return addtime;
	}
	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}
	public String getUpdtime() {
		return updtime;
	}
	public void setUpdtime(String updtime) {
		this.updtime = updtime;
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
