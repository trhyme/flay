package com.java.letch.dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.java.letch.daoimp.AdminsUserInfoDaoImp;
import com.java.letch.model.AdminsUserInfo;
import com.java.letch.tools.DateHelper;

/***
 * 管理员信息数据操作DAO类
 * @author Administrator
 * 2017-09-13
 */
@Repository(value="AdminsUserInfoDao")
public class AdminsUserInfoDao implements AdminsUserInfoDaoImp {

	@Resource(name="sessionFactory")
	private SessionFactory session; //数据源
	
	/*根据账号查询信息*/
	@Override
	public AdminsUserInfo selectUserByName(String name) {
		try {
			String hql="from AdminsUserInfo as auser where auser.admname=?";
			return (AdminsUserInfo) session.getCurrentSession().createQuery(hql).setString(0, name).list().get(0);
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}

	/*根据信息查询全部管理员信息*/
	@SuppressWarnings("unchecked")
	@Override
	public List<AdminsUserInfo> selectAdmin() {
		try {
			return session.getCurrentSession().createQuery("from AdminsUserInfo").list();
		} catch (Exception e) {
			return null;
		}
	}

	/*根据ID修改登录的时间*/
	@Override
	public boolean updateAdminLoginTime(int id) {
		try {
			String sql="update AdminsUserInfo set admlasts='"+DateHelper.getNewData()+"' where admid=?";
			if((session.getCurrentSession().createSQLQuery(sql).setInteger(0, id).executeUpdate())>0){
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}

	/*根据ID查询信息*/
	@Override
	public AdminsUserInfo selectOneAdminById(String id) {
		try {
			String hql="from AdminsUserInfo where admid=?";
			return (AdminsUserInfo) session.getCurrentSession().createQuery(hql).setInteger(0, Integer.parseInt(id)).uniqueResult();
		} catch (Exception e) {
			return null;
		}
	}

	/*注册添加后台管理员员*/
	@Override
	public boolean insertIntoAdminUser(AdminsUserInfo user) {
		try {
			session.getCurrentSession().save(user);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
