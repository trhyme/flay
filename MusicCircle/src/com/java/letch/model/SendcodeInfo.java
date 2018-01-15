package com.java.letch.model;

import java.io.Serializable;

/***
 * 用户发送验证码的记录表
 * @author Administrator
 * 2017-10-16
 */
@SuppressWarnings("serial")
public class SendcodeInfo implements Serializable{
	public SendcodeInfo(){}
	//
	private int id;					//主键ID
	private String phone;			//手机号码
	private String codes;			//手机验证码
	private String addtime;			//发送时间
	private String remarks;			//备注
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getCodes() {
		return codes;
	}
	public void setCodes(String codes) {
		this.codes = codes;
	}
	public String getAddtime() {
		return addtime;
	}
	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
}
