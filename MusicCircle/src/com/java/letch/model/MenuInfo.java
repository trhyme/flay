package com.java.letch.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/***
 * 系统菜单信息
 * @author Administrator
 * 2017-09-13
 */
@SuppressWarnings("serial")
public class MenuInfo implements Serializable{
	public MenuInfo(){}
	
	//属性
	private int id;			//主键ID
	private String ids;		//前端ID
	private String title;	//菜单名称
	private String url;		//菜单路径，默认为#
	private String pid;		//父级菜单ID
	private int stats;		//是否删除
	private String icon;	//菜单图标
	private String remark;	//备注
	
	/**配置**/
	private Set<MenuUserInfo> usersMenu=new HashSet<MenuUserInfo>();
	
	public Set<MenuUserInfo> getUsersMenu() {
		return usersMenu;
	}
	public void setUsersMenu(Set<MenuUserInfo> usersMenu) {
		this.usersMenu = usersMenu;
	}
	public int getStats() {
		return stats;
	}
	public void setStats(int stats) {
		this.stats = stats;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
