package com.java.letch.app.returnmodel;

import java.io.Serializable;
import java.util.Map;

/***
 * APP接口返回的实体类信息
 * @author Administrator
 * 2017-10-10
 */
@SuppressWarnings("serial")
public class AppReturnCodeInfo implements Serializable{
	public AppReturnCodeInfo(){}
	//属性
	private int code;					//状态码
	private String message;				//说明	
	private Map<String, Object> map;	//数据
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Map<String, Object> getMap() {
		return map;
	}
	public void setMap(Map<String, Object> map) {
		this.map = map;
	}
	
}
