package com.java.letch.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.java.letch.daoimp.TypesVoiceInfoDaoImp;
import com.java.letch.model.TypesVoiceInfo;

/***
 * 类型标签信类
 * @author Administrator
 * 2017-09-20
 */

@Repository(value="TypesVoiceInfoDao")
public class TypesVoiceInfoDao implements TypesVoiceInfoDaoImp {

	@Resource(name="sessionFactory")
	private SessionFactory session; //数据源
	
	//查询全部标签信息
	@SuppressWarnings("unchecked")
	@Override
	public List<TypesVoiceInfo> selectAllTypesList() {
		try {
			return session.getCurrentSession().createQuery("from TypesVoiceInfo").list();
		} catch (Exception e) {
			return null;
		}
	}

	//分页查询信息
	@Override
	public Map<String, Object> selectTypesPageList(Map<String, Object> map) {
		try {
			//查询总条数的SQL
			StringBuffer sqlnum=new StringBuffer("select count(*) from TypesVoiceInfo");
			//分页查询HQL
			StringBuffer hqlstr=new StringBuffer("from TypesVoiceInfo as b");
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
				pagenum=10;
			}
			//分页查询语句排序
			hqlstr.append(" order by b.id desc");
			Query query=session.getCurrentSession().createQuery(hqlstr.toString());
			query.setFirstResult((page-1)*pagenum);		//分页开始
			query.setMaxResults(pagenum);		//每页条数
			@SuppressWarnings("unchecked")
			List<TypesVoiceInfo> list=query.list();
			map.put("nowpage",page);	//当前页数
			map.put("counts", count);	//总条数
			map.put("pagecount", pagenum);		//每页条数
			map.put("page",countpage);	//总页数
			map.put("typeslist", list);	//每页的数据
			return map;
		} catch (Exception e) {
			return map;
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

	//添加信息的方法
	@Override
	public boolean insertIntoTypes(TypesVoiceInfo types) {
		try {
			session.getCurrentSession().save(types);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
}
