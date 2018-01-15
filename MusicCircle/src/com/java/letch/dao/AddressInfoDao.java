package com.java.letch.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.java.letch.daoimp.AddressInfoDaoImp;
import com.java.letch.model.AddressInfo;

/***
 * 用户的地理位置信息操作
 * @author Administrator
 * 2017-12-06
 */
@Repository(value="AddressInfoDao")
public class AddressInfoDao implements AddressInfoDaoImp{

	@Resource(name="sessionFactory")
	private SessionFactory session; //数据源
	
	//1.根据用户ID查询地址
	@Override
	public AddressInfo selectAddressByUserID(int userid) {
		try {
			String hql="from AddressInfo where uid=?";
			@SuppressWarnings("unchecked")
			List<AddressInfo> list=session.getCurrentSession().createQuery(hql).setInteger(0, userid).list();
			if(list!=null && list.size()>0){
				return list.get(0);
			}else{
				return null;
			}
		} catch (Exception e) {
			return null;
		}
	}

	//2.新增、更新地理位置信息
	@Override
	public boolean insertOrUpdateAddress(AddressInfo adress) {
		try {
			session.getCurrentSession().saveOrUpdate(adress);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	//3.后台查询
	@Override
	public Map<String, Object> selectPageList(Map<String, Object> map) {
		try {
			//查询总条数的SQL
			StringBuffer sqlnum=new StringBuffer("select count(*) from addressinfo");
			//分页查询HQL
			StringBuffer hqlstr=new StringBuffer("from AddressInfo");
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
				pagenum=30;
			}
			//分页查询语句排序
			hqlstr.append(" order by id desc");
			Query query=session.getCurrentSession().createQuery(hqlstr.toString());
			query.setFirstResult((page-1)*pagenum);		//分页开始
			query.setMaxResults(pagenum);		//每页条数
			@SuppressWarnings("unchecked")
			List<AddressInfo> list=query.list();
			map.put("nowpage",page);	//当前页数
			map.put("counts", count);	//总条数
			map.put("pagecount", pagenum);		//每页条数
			map.put("page",countpage);	//总页数
			map.put("addresslist", list);	//每页的数据
			return map;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
	
	/*查询总数*/
	private int selectBlogCount(String sql) {
		try {
			Object res=session.getCurrentSession().createSQLQuery(sql).uniqueResult();
			return Integer.parseInt(res.toString());
		} catch (Exception e) {
			return 0;
		}
	}

}
