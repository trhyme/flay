package com.java.letch.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.java.letch.daoimp.BeckoningInfoDaoImp;
import com.java.letch.model.BeckoningInfo;
import com.java.letch.model.MenuInfo;

/***
 * 心动信息配置信息类
 * @author Administrator
 * 2017-09-26
 */
@Repository(value="BeckoningInfoDao")
public class BeckoningInfoDao implements BeckoningInfoDaoImp {

	@Resource(name="sessionFactory")
	private SessionFactory session; //数据源
	
	//分页查询出数据信息
	@Override
	public Map<String, Object> selectBeckPageList(Map<String, Object> map) {
		try {
			//查询总条数的SQL
			StringBuffer sqlnum=new StringBuffer("select count(*) from beckoninginfo");
			//分页查询HQL
			StringBuffer hqlstr=new StringBuffer("from BeckoningInfo as b");
			//根据SQL语句已经状态查询
			int count=selectBlogCount(sqlnum.toString());
			int countpage=(count-1)/Integer.parseInt(map.get("pagenum").toString())+1;
			int page=Integer.parseInt(map.get("nowpage").toString());
			int pagenum=Integer.parseInt(map.get("pagenum").toString());
			if(page<=0){
				page=1;
			}
			if(page>=countpage){
				page=countpage;
			}
			if(pagenum<=0){
				pagenum=15;
			}
			//分页查询语句排序
			hqlstr.append(" order by b.id desc");
			Query query=session.getCurrentSession().createQuery(hqlstr.toString());
			query.setFirstResult((page-1)*pagenum);		//分页开始
			query.setMaxResults(pagenum);		//每页条数
			@SuppressWarnings("unchecked")
			List<MenuInfo> list=query.list();
			map.put("nowpage",page);	//当前页数
			map.put("counts", count);	//总条数
			map.put("pagecount", pagenum);		//每页条数
			map.put("page",countpage);	//总页数
			map.put("becklist", list);	//每页的数据
			return null;
		} catch (Exception e) {
			return null;
		}
	}

	//查询总数
	private int selectBlogCount(String sql) {
		try {
			Object res=session.getCurrentSession().createSQLQuery(sql).uniqueResult();
			return Integer.parseInt(res.toString());
		} catch (Exception e) {
			return 0;
		}
	}

	//添加信息
	@Override
	public boolean insertIntoBeckToDB(BeckoningInfo beck) {
		try {
			session.getCurrentSession().save(beck);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	//根据编号查询单个信息
	@Override
	public BeckoningInfo selectById(int id) {
		try {
			String hql="from BeckoningInfo where id=?";
			return (BeckoningInfo) session.getCurrentSession().createQuery(hql).setInteger(0,id).uniqueResult();
		} catch (Exception e) {
			return null;
		}
	}

	//修改信息入库
	@Override
	public boolean updateBeckInfoToDB(BeckoningInfo beck) {
		try {
			session.getCurrentSession().update(beck);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
}
