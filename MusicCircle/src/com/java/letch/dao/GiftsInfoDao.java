package com.java.letch.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.java.letch.daoimp.GiftsInfoDaoImp;
import com.java.letch.model.GiftsInfo;
import com.java.letch.model.UsersInfo;

/***
 * 礼物信息的数据操作类
 * @author Administrator
 * 2017-09-20
 */
@Repository(value="GiftsInfoDao")
public class GiftsInfoDao implements GiftsInfoDaoImp {

	@Resource(name="sessionFactory")
	private SessionFactory session;		//数据源
	
	/*分页查询礼物信息*/
	@Override
	public Map<String, Object> selectGiftByPage(Map<String, Object> map) {
		try {
			//查询总条数的SQL
			StringBuffer sqlnum=new StringBuffer("select count(*) from gifts m where m.work=?");
			//分页查询HQL
			StringBuffer hqlstr=new StringBuffer("from GiftsInfo as b where b.work=?");
			//根据SQL语句已经状态查询
			int count=selectBlogCount(sqlnum.toString(),Integer.parseInt(map.get("bgstatrus").toString()));
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
			Query query=session.getCurrentSession().createQuery(hqlstr.toString()).setInteger(0, Integer.parseInt(map.get("bgstatrus").toString()));
			query.setFirstResult((page-1)*pagenum);		//分页开始
			query.setMaxResults(pagenum);		//每页条数
			@SuppressWarnings("unchecked")
			List<UsersInfo> list=query.list();
			map.put("nowpage",page);	//当前页数
			map.put("counts", count);	//总条数
			map.put("pagecount", pagenum);		//每页条数
			map.put("page",countpage);	//总页数
			map.put("giftslist", list);	//每页的数据
			return map;
			
		} catch (Exception e) {
			System.out.println(e);
			return map;
		}
	}
	private int selectBlogCount(String sql,int status) {
		try {
			Object res=session.getCurrentSession().createSQLQuery(sql).setInteger(0, status).uniqueResult();
			return Integer.parseInt(res.toString());
		} catch (Exception e) {
			return 0;
		}
	}
	
	/*修改或者添加的方法*/
	@Override
	public boolean insertIntoGift(GiftsInfo gift) {
		try {
			session.getCurrentSession().saveOrUpdate(gift);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	/*根据编号查询单条礼物信息*/
	@Override
	public GiftsInfo selectGiftsByID(int id) {
		try {
			String hql="from GiftsInfo as g where g.id=?";
			return (GiftsInfo) session.getCurrentSession().createQuery(hql).setInteger(0, id).uniqueResult();
		} catch (Exception e) {
			return null;
		}
	}
	
	
}
