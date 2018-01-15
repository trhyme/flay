package com.java.letch.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.java.letch.dao.AdminsUserInfoDao;
import com.java.letch.model.AdminsUserInfo;
import com.java.letch.serviceimp.AdminsUserInfoServiceImp;

/***
 * 管理员信息操作类
 * @author Administrator
 * 2017-09-13
 */
@Repository(value="AdminsUserInfoService")
public class AdminsUserInfoService implements AdminsUserInfoServiceImp {

	@Resource(name="AdminsUserInfoDao")
	private AdminsUserInfoDao adminuserDao;
	
	/*根据账号查询信息*/
	@Override
	public AdminsUserInfo selectUserByName(String name) {
		return this.adminuserDao.selectUserByName(name);
	}

	/*根据信息查询全部管理员信息*/
	@Override
	public List<AdminsUserInfo> selectAdmin(){
		return this.adminuserDao.selectAdmin();
	}

	/*根据ID修改登录的时间*/
	@Override
	public boolean updateAdminLoginTime(int id) {
		return this.adminuserDao.updateAdminLoginTime(id);
	}
	
	/*根据ID查询信息*/
	@Override
	public AdminsUserInfo selectOneAdminById(String id){
		return this.adminuserDao.selectOneAdminById(id);
	}

	/*注册添加后台管理员员*/
	@Override
	public boolean insertIntoAdminUser(AdminsUserInfo user) {
		return this.adminuserDao.insertIntoAdminUser(user);
	}
	
}
