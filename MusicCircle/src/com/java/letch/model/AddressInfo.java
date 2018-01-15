package com.java.letch.model;

import java.io.Serializable;

/***
 * 用户位置的信息
 * @author Administrator
 * 2017-12-06
 */
@SuppressWarnings("serial")
public class AddressInfo implements Serializable{
	public AddressInfo(){}
	//属性
	private Integer id;			//主键ID
	private Integer uid;		//外键，用户ID
	private String lgd;			//经度
	private String lad;			//纬度
	private String address;		//地点
	private String ipadr;		//IP地址
	private String addtime;		//上报时间
	
	
	public String getIpadr() {
		return ipadr;
	}
	public void setIpadr(String ipadr) {
		this.ipadr = ipadr;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getUid() {
		return uid;
	}
	public void setUid(Integer uid) {
		this.uid = uid;
	}
	public String getLgd() {
		return lgd;
	}
	public void setLgd(String lgd) {
		this.lgd = lgd;
	}
	public String getLad() {
		return lad;
	}
	public void setLad(String lad) {
		this.lad = lad;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getAddtime() {
		return addtime;
	}
	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}
	
	
}
