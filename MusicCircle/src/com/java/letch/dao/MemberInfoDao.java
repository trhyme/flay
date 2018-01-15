package com.java.letch.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.java.letch.daoimp.MemberInfoDaoImp;
import com.java.letch.model.MemberInfo;
import com.java.letch.model.MenuInfo;

/***
 * 用户会员信息的数据操作接口类
 * @author Administrator
 * 2017-09-28
 */
@Repository(value="MemberInfoDao")
public class MemberInfoDao implements MemberInfoDaoImp{

	@Resource(name="sessionFactory")
	private SessionFactory session;		//数据源
	
	/**分页查询数据信息**/
	@Override
	public Map<String, Object> selectMemberPage(Map<String, Object> map) {
		try {
			//查询总条数的SQL
			StringBuffer sqlnum=new StringBuffer("select count(*) from memberinfo");
			//分页查询HQL
			StringBuffer hqlstr=new StringBuffer("from MemberInfo as b");
			
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
			map.put("memberlist", list);	//每页的数据
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

	
	/**1.根据用户的编号查询会员信息**/
	@Override
	public MemberInfo selectMemberOneByID(int userid) {
		try {
			String hql="from MemberInfo where uid=?";
			MemberInfo me=(MemberInfo) session.getCurrentSession().createQuery(hql).setInteger(0, userid).uniqueResult();
			return me;
		} catch (Exception e) {
			return null;
		}
	}
	
	//查询出会员的总数量
	@Override
	public int selectMemberCount(int strtus) {
		try {
			String sql="select count(1) from memberinfo where strutus="+strtus;
			Object res=session.getCurrentSession().createSQLQuery(sql).uniqueResult();
			return Integer.parseInt(res.toString());
		} catch (Exception e) {
			return 0;
		}
	}

	/***2.添加数据***/
	@Override
	public boolean insertIntoMemberInfoDB(MemberInfo member) {
		try {
			session.getCurrentSession().saveOrUpdate(member);
			return true;
		} catch (Exception e) {
			return false;
		}
	};
	
}
