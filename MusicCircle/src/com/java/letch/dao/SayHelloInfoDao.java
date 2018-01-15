package com.java.letch.dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.java.letch.daoimp.SayHelloInfoDaoImp;
import com.java.letch.model.SayHelloInfo;

/***
 * 用户打招呼数据操作类
 * @author Administrator
 * 2017-12-12
 */
@Repository(value="SayHelloInfoDao")
public class SayHelloInfoDao implements SayHelloInfoDaoImp{

	@Resource(name="sessionFactory")
	private SessionFactory session;		//数据源
	
	//1.添加信息
	@Override
	public boolean addSayHelloToDB(SayHelloInfo say) {
		try {
			session.getCurrentSession().save(say);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	//2.查询单条数据
	@Override
	public SayHelloInfo selectSayHelloByID(int userid, int sayuserid) {
		try {
			String hql="from SayHelloInfo where userid=? and sayuserid=?";
			List<SayHelloInfo> list=session.getCurrentSession().createQuery(hql).setInteger(0, userid).setInteger(1, sayuserid).list();
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
