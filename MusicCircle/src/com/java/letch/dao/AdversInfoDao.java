package com.java.letch.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.java.letch.daoimp.AdversInfoDaoImp;
import com.java.letch.model.AdversInfo;
import com.java.letch.model.UsersInfo;

/***
 * 弹窗广告数据操作类
 * @author Administrator
 * 2017-09-22
 */
@Repository(value="AdversInfoDao")
public class AdversInfoDao implements AdversInfoDaoImp {

	@Resource(name="sessionFactory")
	private SessionFactory session;		//数据源
	
	//后台分页查看信息
	@Override
	public Map<String, Object> selectPageList(Map<String, Object> map) {
		try {
			//查询总条数的SQL
			StringBuffer sqlnum=new StringBuffer("select count(*) from advers");
			//分页查询HQL
			StringBuffer hqlstr=new StringBuffer("from AdversInfo as b");
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
			List<UsersInfo> list=query.list();
			map.put("nowpage",page);	//当前页数
			map.put("counts", count);	//总条数
			map.put("pagecount", pagenum);		//每页条数
			map.put("page",countpage);	//总页数
			map.put("adverslist", list);	//每页的数据
			return map;
			
		} catch (Exception e) {
			System.out.println(e);
			return map;
		}
	}

	private int selectBlogCount(String sql) {
		try {
			Object res=session.getCurrentSession().createSQLQuery(sql).uniqueResult();
			return Integer.parseInt(res.toString());
		} catch (Exception e) {
			return 0;
		}
	}

	
	//添加入库的方法
	@Override
	public boolean insertIntoAdversDB(AdversInfo adver) {
		try {
			session.getCurrentSession().save(adver);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	//根据编号查询信息
	@Override
	public AdversInfo selectByIds(int ids) {
		try {
			String hql="from AdversInfo where id=?";
			return (AdversInfo) session.getCurrentSession().createQuery(hql).setInteger(0, ids).uniqueResult();
		} catch (Exception e) {
			return null;
		}
	}

	/**修改信息的方法**/
	@Override
	public boolean updateAdvers(AdversInfo adver) {
		try {
			session.getCurrentSession().update(adver);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	
	
}
