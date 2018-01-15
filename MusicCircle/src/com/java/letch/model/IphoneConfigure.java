package com.java.letch.model;

import java.io.Serializable;

/***
 * iPhone的支付屏蔽设置
 * @author Administrator
 * 2017-11-20
 */
@SuppressWarnings("serial")
public class IphoneConfigure implements Serializable{
	public IphoneConfigure(){}
	//属性
	private int id;				//主键ID
	private int iphone;			//0表示屏蔽，1以上表示屏蔽
	private String remark;		//备注
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getIphone() {
		return iphone;
	}
	public void setIphone(int iphone) {
		this.iphone = iphone;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
