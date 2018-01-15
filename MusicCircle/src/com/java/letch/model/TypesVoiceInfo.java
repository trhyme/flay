package com.java.letch.model;

import java.io.Serializable;

/***
 * 类型标签信息
 * @author Administrator
 * 2017-09-20
 */
@SuppressWarnings("serial")
public class TypesVoiceInfo implements Serializable{
	public TypesVoiceInfo(){}
	
	/*属性*/
	private int id;			//主键ID
	private String  name;	//名称
	private int categorys_id;	//
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getCategorys_id() {
		return categorys_id;
	}
	public void setCategorys_id(int categorys_id) {
		this.categorys_id = categorys_id;
	}
	
}
