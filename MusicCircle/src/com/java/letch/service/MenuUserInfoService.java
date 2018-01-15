package com.java.letch.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.java.letch.dao.MenuUserInfoDao;
import com.java.letch.model.AdminsUserInfo;
import com.java.letch.model.MenuUserInfo;
import com.java.letch.serviceimp.MenuUserInfoServiceImp;
/***
 * 关联菜单信息业务操作类
 * @author Administrator
 * 2017-09-15
 */
@Repository(value="MenuUserInfoService")
public class MenuUserInfoService implements MenuUserInfoServiceImp {

	@Resource(name="MenuUserInfoDao")
	private MenuUserInfoDao menuuserdao;
	
	//查询菜单关联信息根据用户编号
	@Override
	public List<MenuUserInfo> selectMenuByUid(AdminsUserInfo user) {
		return this.menuuserdao.selectMenuByUid(user);
	}

}
