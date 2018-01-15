package com.java.letch.daoimp;

import java.util.List;

import com.java.letch.model.AdminsUserInfo;

/***
 * 后台管理员数据操作DAO接口
 * @author Administrator
 * 2017-09-13
 */
public interface AdminsUserInfoDaoImp {
	/*根据账号查询信息*/
	public AdminsUserInfo selectUserByName(String name);
	
	/*根据信息查询全部管理员信息*/
	public List<AdminsUserInfo> selectAdmin();
	
	/*根据ID修改登录的时间*/
	public boolean updateAdminLoginTime(int id);
	
	/*根据ID查询信息*/
	public AdminsUserInfo selectOneAdminById(String id);
	
	/*注册添加后台管理员员*/
	public boolean insertIntoAdminUser(AdminsUserInfo user);
}
