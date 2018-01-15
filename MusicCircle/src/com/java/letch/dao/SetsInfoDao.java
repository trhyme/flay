package com.java.letch.dao;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.java.letch.daoimp.SetsInfoDaoImp;
import com.java.letch.model.SetsInfo;

/***
 * 配置信息数据操作类
 * @author Administrator
 * 2017-09-21
 */
@Repository(value="SetsInfoDao")
public class SetsInfoDao implements SetsInfoDaoImp {

	@Resource(name="sessionFactory")
	private SessionFactory session;		//数据源
	
	
	/**查询出配置信息**/
	@Override
	public SetsInfo selectSetsInfo() {
		try {
			String sql="from SetsInfo";
			return (SetsInfo) session.getCurrentSession().createQuery(sql).list().get(0);
		} catch (Exception e) {
			return null;
		}
	}

	/*1.修改货币兑换率信息*/
	@Override
	public boolean updateRatenb(int id, String ratenb) {
		try {
			String sql="update sets set ratenb=? where id=?";
			if(session.getCurrentSession().createSQLQuery(sql).setString(0, ratenb).setInteger(1, id).executeUpdate()>0){
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}

	/*2.修改用户登录的奖励信息*/
	@Override
	public boolean updateLoginnb(int id, String loginnb) {
		try {
			String sql="update sets set loginnb=? where id=?";
			if(session.getCurrentSession().createSQLQuery(sql).setString(0, loginnb).setInteger(1, id).executeUpdate()>0){
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}

	/*3.修改用户的偷听/看的所需金币*/
	@Override
	public boolean updateTapnb(int id, String tapnb) {
		try {
			String sql="update sets set tapnb=? where id=?";
			if(session.getCurrentSession().createSQLQuery(sql).setString(0, tapnb).setInteger(1, id).executeUpdate()>0){
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}

	/*4.修改用户发单信息*/
	@Override
	public boolean updateRewardnbs(int id, String rewardnb) {
		try {
			String sql="update sets set rewardnb=? where id=?";
			if(session.getCurrentSession().createSQLQuery(sql).setString(0, rewardnb).setInteger(1, id).executeUpdate()>0){
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}

	/*5.修改蓝砖与1元人民币的比例*/
	@Override
	public boolean updateBluebrick(int id, String bluebrick) {
		try {
			String sql="update sets set bluebrick=? where id=?";
			if(session.getCurrentSession().createSQLQuery(sql).setString(0, bluebrick).setInteger(1, id).executeUpdate()>0){
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}

	/*6.修改蓝砖与1元人民币的比例*/
	@Override
	public boolean updateRedsbrick(int id, String redsbrick) {
		try {
			String sql="update sets set redsbrick=? where id=?";
			if(session.getCurrentSession().createSQLQuery(sql).setString(0, redsbrick).setInteger(1, id).executeUpdate()>0){
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}
	
	/**7.修改蓝砖与红砖的兑换比率**/
	@Override
	public boolean updateRates(int id, String bluerates, String redsrates) {
		try {
			String sql="update sets set bluerates=?,redsrates=? where id=?";
			if(session.getCurrentSession().createSQLQuery(sql)
					.setString(0, bluerates).setString(1, redsrates).setInteger(2, id).executeUpdate()>0){
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}

}
