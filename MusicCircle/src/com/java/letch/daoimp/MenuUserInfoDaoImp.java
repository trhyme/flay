package com.java.letch.daoimp;

import java.util.List;

import com.java.letch.model.AdminsUserInfo;
import com.java.letch.model.MenuUserInfo;

/***
 * 菜单信息与用户管理数据操作接口
 * @author Administrator
 * 2017-09-15
 */
public interface MenuUserInfoDaoImp {
	
	//查询菜单关联信息根据用户编号
	public List<MenuUserInfo> selectMenuByUid(AdminsUserInfo user);
	
	/**循环添加关联信息**/
	public boolean addMenuUserAll(String[] list,String userid);
	
}
