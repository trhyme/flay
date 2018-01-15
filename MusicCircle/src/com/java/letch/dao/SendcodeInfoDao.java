package com.java.letch.dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.java.letch.daoimp.SendcodeInfoDaoImp;
import com.java.letch.model.SendcodeInfo;

/***
 * 用户发送的验证码信息记录数据操作类
 * @author Administrator
 * 2017-10-16
 */
@Repository(value="SendcodeInfoDao")
public class SendcodeInfoDao implements SendcodeInfoDaoImp {

	@Resource(name="sessionFactory")
	private SessionFactory session; //数据源
	
	//保存发送验证码的信息
	@Override
	public boolean sevaSendCodeInfo(SendcodeInfo send) {
		try {
			session.getCurrentSession().saveOrUpdate(send);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	//通过手机号码和验证码查询信息
	@Override
	public boolean selectByphoneCode(String phone, String code) {
		try {
			String sql="select * from sendcodeinfo where phone=? and codes=?";
			@SuppressWarnings("unchecked")
			List<Object> list=session.getCurrentSession().createSQLQuery(sql).setString(0, phone).setString(1, code).list();
			if(list.size()>0){
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
		}
		return false;
	}

	//根据手机号码和验证码同时查询出验证码信息
	@Override
	public SendcodeInfo selectByphoneBycode(String phone, String code) {
		try {
			String hql="from SendcodeInfo where phone=? and codes=?";
			@SuppressWarnings("unchecked")
			List<SendcodeInfo> list=session.getCurrentSession().createQuery(hql).setString(0, phone).setString(1, code).list();
			SendcodeInfo s=new SendcodeInfo();
			if(list.size()>0){
				return list.get(0);
			}else{
				return null;
			}
		} catch (Exception e) {
			return null;
		}
	}
}
