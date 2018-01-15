package com.java.letch.dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.java.letch.daoimp.IntimacyInfoDaoImp;
import com.java.letch.model.IntimacyInfo;

/***
 * 用户的亲密度数据库操作
 * @author Administrator
 * 2017-11-23
 */
@Repository(value="IntimacyInfoDao")
public class IntimacyInfoDao implements IntimacyInfoDaoImp{

	@Resource(name="sessionFactory")
	private SessionFactory session; //数据源
	
	
	//1.根据用户的ID查询亲密度记录
	@Override
	public IntimacyInfo selectIntimacyInfoOne(int useridA, int useridB) {
		try {
			String sql="select * from intimacyinfo where (useroneid="+useridA+" and usertwoid="+useridB+") or (useroneid="+useridB+" and usertwoid="+useridA+") LIMIT 1";
			List<IntimacyInfo> list=this.session.getCurrentSession().createSQLQuery(sql).addEntity(IntimacyInfo.class).list();
			if(list!=null && list.size()>0){
				return list.get(0);
			}else{
				return null;
			}
		} catch (Exception e) {
			System.err.println(e);
			return null;
		}
	}

	//2.跟新修改数据信息
	@Override
	public boolean insertOrUpdateIntimacy(IntimacyInfo tim) {
		try {
			session.getCurrentSession().saveOrUpdate(tim);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	
	
}
