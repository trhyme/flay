package com.java.letch.daoimp;

import java.util.List;
import java.util.Map;

import com.java.letch.model.MenuInfo;

/***
 * 后台菜单栏数据操作接口
 * @author Administrator
 * 2017-09-13
 */
public interface MenuInfoDaoImp {
	
	//分页查询信息
	public Map<String, Object> selectListByPage(Map<String, Object> map);
	//查询出总的菜单信息
	public List<MenuInfo> selectMenuAllList();
	//添加菜单信息
	public boolean addMenutoDB(MenuInfo menu);
	//根据ID查询单条数据
	public MenuInfo SelectMenuById(String id);
	//根据编号修改
	public boolean updateMenuByMenu(MenuInfo menu);
	
	/**根据菜单编号和用户编号删除信息**/
	public boolean deleteMenuAndUser(String mid,String userid);
	
}
