package com.java.letch.model;

import java.io.Serializable;

/***
 * 用户与菜单关联信息
 * @author Administrator
 * 2017-09-14
 */ 
@SuppressWarnings("serial")
public class MenuUserInfo implements Serializable{
	public MenuUserInfo(){}
	/*属性*/
	private int id;		//主键ID
	//private int admid;	//管理员ID
	//private int menid;	//菜单ID
	private int strtu;	//会否启用，1为启用，0为禁用
	
	//配置
	private AdminsUserInfo user;
	private MenuInfo menu;
	
	
	public AdminsUserInfo getUser() {
		return user;
	}
	public void setUser(AdminsUserInfo user) {
		this.user = user;
	}
	public MenuInfo getMenu() {
		return menu;
	}
	public void setMenu(MenuInfo menu) {
		this.menu = menu;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getStrtu() {
		return strtu;
	}
	public void setStrtu(int strtu) {
		this.strtu = strtu;
	}
	
}
