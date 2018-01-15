package com.java.letch.app.voicechat;

import java.io.Serializable;

/***
 * 融云聊天ToKen类
 * @author Administrator
 * 2017-11-20
 */
@SuppressWarnings("serial")
public class ChartEntity implements Serializable{
	public ChartEntity(){}
	//属性
	private String code;
	private String token;
	private String userId;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
}
