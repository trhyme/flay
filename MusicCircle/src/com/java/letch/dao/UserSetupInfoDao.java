package com.java.letch.dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.java.letch.daoimp.UserSetupInfoDaoImp;
import com.java.letch.model.UserSetupInfo;

/***
 * APP用户的设置消息推送的数据操作类
 * @author Administrator
 * 2017-11-07
 */
@Repository(value="UserSetupInfoDao")
public class UserSetupInfoDao implements UserSetupInfoDaoImp {

	@Resource(name="sessionFactory")
	private SessionFactory session;		//数据源
	
	//1.添加设置信息
	@Override
	public boolean insertUserSetupToDB(UserSetupInfo usersetup) {
		try {
			session.getCurrentSession().saveOrUpdate(usersetup);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	//2.根据用户编号查询信息
	@Override
	public UserSetupInfo selectUserSetupByUserID(int uid,int typeid) {
		try {
			String hql="from UserSetupInfo where uid=? and settype=?";
			@SuppressWarnings("unchecked")
			List<UserSetupInfo> list=session.getCurrentSession().createQuery(hql).setInteger(0, uid).setInteger(1, typeid).list();
			if(list!=null && list.size()>0){
				return list.get(0);
			}else{
				return null;
			}
		} catch (Exception e) {
			return null;
		}
	}

}
