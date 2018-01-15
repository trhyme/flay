package com.java.letch.dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.java.letch.daoimp.MenuUserInfoDaoImp;
import com.java.letch.model.AdminsUserInfo;
import com.java.letch.model.MenuUserInfo;
/***
 * 菜单关联信息数据操作类
 * @author Administrator
 * 2017-09-15
 */
@Repository(value="MenuUserInfoDao")
public class MenuUserInfoDao implements MenuUserInfoDaoImp {

	@Resource(name="sessionFactory")
	private SessionFactory session;		//数据源
	
	//查询菜单关联信息根据用户编号
	@SuppressWarnings("unchecked")
	@Override
	public List<MenuUserInfo> selectMenuByUid(AdminsUserInfo user) {
		try {
			String hql="from MenuUserInfo where user.admid="+user.getAdmid();
			return session.getCurrentSession().createQuery(hql).list();
		} catch (Exception e) {
			return null;
		}
	}

	/**循环添加菜单关联信息**/
	@Override
	public boolean addMenuUserAll(String[] list, String userid) {
		try {
			for(int i=0;i<list.length;i++){
				System.out.println(list[i]);
				//查询是否存在
				String sql1="from MenuUserInfo where user.admid=? and menu.id=?";
				@SuppressWarnings("unchecked")
				List<MenuUserInfo> menulist=session.getCurrentSession().createQuery(sql1).
						setInteger(0, Integer.parseInt(userid)).setInteger(1, Integer.parseInt(list[i])).list();
				if(menulist.size()<=0){
					String sql2="INSERT INTO MenuUserInfo (admid,menid,strtu) VALUES ("+userid+","+list[i]+",1)";
					if(session.getCurrentSession().createSQLQuery(sql2).executeUpdate()>0){
						return true;
					}else{
						return false;
					}
				}
			}
		} catch (Exception e) {
			return false;
		}
		return false;
	}
	
}
