package com.java.letch.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/***
 * 管理员用户信息
 * @author Administrator
 * 2017-09-13
 */
@SuppressWarnings("serial")
public class AdminsUserInfo implements Serializable{
	public AdminsUserInfo(){}
	/*属性*/
	private int admid;			//主键ID
	private String admstr;		//字符主键
	private String admname;		//后台管理员账号
	private String admadress;	//密码
	private String admemail;	//邮箱
	private String admphone;	//手机电话
	private String admphoto;	//头像
	private int admstate;		//状态1，可以，0不可以
	private String admtimes;	//创建时间
	private String admlasts;	//最后一次登录时间
	private String admlogip;	//登录IP
	private String admtocken;	//登录tocken
	private String admremark;	//备注
	
	/**用户的菜单权限**/
	private Set<MenuUserInfo> usersMenu=new HashSet<MenuUserInfo>();
	
	public Set<MenuUserInfo> getUsersMenu() {
		return usersMenu;
	}
	public void setUsersMenu(Set<MenuUserInfo> usersMenu) {
		this.usersMenu = usersMenu;
	}
	public int getAdmid() {
		return admid;
	}
	public void setAdmid(int admid) {
		this.admid = admid;
	}
	public String getAdmstr() {
		return admstr;
	}
	public void setAdmstr(String admstr) {
		this.admstr = admstr;
	}
	public String getAdmname() {
		return admname;
	}
	public void setAdmname(String admname) {
		this.admname = admname;
	}
	public String getAdmadress() {
		return admadress;
	}
	public void setAdmadress(String admadress) {
		this.admadress = admadress;
	}
	public String getAdmemail() {
		return admemail;
	}
	public void setAdmemail(String admemail) {
		this.admemail = admemail;
	}
	public String getAdmphone() {
		return admphone;
	}
	public void setAdmphone(String admphone) {
		this.admphone = admphone;
	}
	public String getAdmphoto() {
		return admphoto;
	}
	public void setAdmphoto(String admphoto) {
		this.admphoto = admphoto;
	}
	public int getAdmstate() {
		return admstate;
	}
	public void setAdmstate(int admstate) {
		this.admstate = admstate;
	}
	public String getAdmtimes() {
		return admtimes;
	}
	public void setAdmtimes(String admtimes) {
		this.admtimes = admtimes;
	}
	public String getAdmlasts() {
		return admlasts;
	}
	public void setAdmlasts(String admlasts) {
		this.admlasts = admlasts;
	}
	public String getAdmlogip() {
		return admlogip;
	}
	public void setAdmlogip(String admlogip) {
		this.admlogip = admlogip;
	}
	public String getAdmtocken() {
		return admtocken;
	}
	public void setAdmtocken(String admtocken) {
		this.admtocken = admtocken;
	}
	public String getAdmremark() {
		return admremark;
	}
	public void setAdmremark(String admremark) {
		this.admremark = admremark;
	}
	
}
