package com.java.letch.app.socket.entity;

import java.io.Serializable;

/***
 * SOCKET长连接的用户参数消息类
 * @author Administrator
 * 2017-12-13
 */
@SuppressWarnings("serial")
public class SocketMessage implements Serializable{
	
	private Integer userid;		//用户ID
	private String sex;			//需要匹配的性别
	public Integer getUserid() {
		return userid;
	}
	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	
}
