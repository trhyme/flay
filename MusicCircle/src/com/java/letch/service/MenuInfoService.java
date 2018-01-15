package com.java.letch.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.java.letch.dao.MenuInfoDao;
import com.java.letch.dao.MenuUserInfoDao;
import com.java.letch.model.MenuInfo;
import com.java.letch.serviceimp.MenuInfoServiceImp;

/***
 * 菜单栏业务操作
 * @author Administrator
 *
 */
@Repository(value="MenuInfoService")
public class MenuInfoService implements MenuInfoServiceImp{
	
	@Resource(name="MenuInfoDao")
	private MenuInfoDao menuinfoDao;
	
	@Resource(name="MenuUserInfoDao")
	private MenuUserInfoDao menuuserinfoDao;

	/**分页查询**/
	@Override
	public Map<String, Object> selectListByPage(Map<String, Object> map) {
		return this.menuinfoDao.selectListByPage(map);
	}

	//查询出总的菜单信息
	@Override
	public List<MenuInfo> selectMenuAllList() {
		return this.menuinfoDao.selectMenuAllList();
	}

	//添加菜单信息
	@Override
	public boolean addMenutoDB(MenuInfo menu) {
		return this.menuinfoDao.addMenutoDB(menu);
	}

	//根据ID查询单条数据
	@Override
	public MenuInfo selectMenuById(String id) {
		return this.menuinfoDao.SelectMenuById(id);
	}

	//根据编号修改
	@Override
	public boolean updateMenuByMenu(MenuInfo menu) {
		return this.menuinfoDao.updateMenuByMenu(menu);
	}

	/**根据菜单编号和用户编号删除信息**/
	@Override
	public boolean deleteMenuAndUser(String mid, String userid) {
		return this.menuinfoDao.deleteMenuAndUser(mid, userid);
	}

	/**循环添加管理员菜单关联信息**/
	@Override
	public boolean addMenuUserAll(String[] list, String userid) {
		return this.menuuserinfoDao.addMenuUserAll(list, userid);
	}
	
}
