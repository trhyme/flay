package com.java.letch.serviceimp;

import java.util.List;

import com.java.letch.model.AdminsUserInfo;
import com.java.letch.model.MenuUserInfo;

/***
 * 关联菜单信息的业务操作接口
 * @author Administrator
 * 2017-09-15
 */
public interface MenuUserInfoServiceImp {
	
	//查询菜单关联信息根据用户编号
	public List<MenuUserInfo> selectMenuByUid(AdminsUserInfo user);
	
}
