package com.java.letch.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.java.letch.daoimp.SignedInfoDaoImp;
import com.java.letch.model.SignedInfo;

/***
 * 用户签到信息的数据操作类
 * @author Administrator
 * 2017-10-13
 */
@Repository(value="SignedInfoDao")
public class SignedInfoDao implements SignedInfoDaoImp {

	@Resource(name="sessionFactory")
	private SessionFactory session;		//数据源
	
	//1.添加签到信息
	@Override
	public boolean addSignedToDB(SignedInfo sign) {
		try {
			session.getCurrentSession().save(sign);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	//查询是否签到
	@SuppressWarnings("unchecked")
	private SignedInfo checkSignToday(int userid,int year,int mouth,int day){
		String sql="from signedinfo where userid=? and addyear=? and addmont=? and adddays=?";
		List<SignedInfo> list=new ArrayList<>();
		list=session.getCurrentSession().createQuery(sql).setInteger(0, userid).setInteger(1, year).setInteger(2, mouth)
				.setInteger(3, day).list();
		if(list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	//查询出用户的查询次数
	/**
	 * @param userid
	 * @return
	 */
	private int selectsignNum(int userid){
		try {
			String sql="select signnum from signedinfo where userid=? and id=(select MAX(id) from signedinfo where userid=?)";
			Object res=session.getCurrentSession().createSQLQuery(sql).setInteger(0, userid).setInteger(1, userid).uniqueResult();
			return Integer.parseInt(res.toString());
		} catch (Exception e) {
			return 0;
		}
	}
	
	//2.查询单条信息
	@Override
	public SignedInfo selectSignedInfoByID(int userid) {
		try {
			String hql="from SignedInfo where userid=?";
			List<SignedInfo> list=session.getCurrentSession().createQuery(hql).setInteger(0, userid).list();
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
