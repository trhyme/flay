package com.java.letch.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.java.letch.daoimp.BroadcastInfoDaoImp;
import com.java.letch.model.MenuInfo;

/***
 * 广播信息数据操作类
 * @author Administrator
 * 2017-09-27
 */
@Repository(value="BroadcastInfoDao")
public class BroadcastInfoDao implements BroadcastInfoDaoImp {

	@Resource(name="sessionFactory")
	private SessionFactory session; //数据源
	
	//分页查询
	@Override
	public Map<String, Object> selectBroadcastPage(Map<String, Object> map) {
		try {
			//查询总条数的SQL
			StringBuffer sqlnum=new StringBuffer("select count(*) from broadcastinfo");
			//分页查询HQL
			StringBuffer hqlstr=new StringBuffer("from BroadcastInfo as b");
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
			map.put("broadcastlist", list);	//每页的数据
			return map;
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

	/*修改是否屏蔽信息*/
	@Override
	public boolean updateBroadcastStratus(int id, int stratus) {
		try {
			String sql="update broadcastinfo set stratus=? where id=?";
			int num=this.session.getCurrentSession().createSQLQuery(sql).setInteger(0, stratus).setInteger(1, id).executeUpdate();
			if(num>0){
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}
	
}
