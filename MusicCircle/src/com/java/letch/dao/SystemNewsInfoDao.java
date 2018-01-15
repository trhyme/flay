package com.java.letch.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.java.letch.daoimp.SystemNewsInfoDaoImp;
import com.java.letch.model.SystemNewsInfo;

/***
 * 系统消息信息数据操作类
 * @author Administrator
 * 2017-11-16
 */
@Repository(value="SystemNewsInfoDao")
public class SystemNewsInfoDao implements SystemNewsInfoDaoImp {

	@Resource(name="sessionFactory")
	private SessionFactory session;		//数据源
	
	/**APP用户查询系统信息根据用户ID**/
	@Override
	public Map<String, Object> appSelectSystemNewPage(Map<String, Object> map, int userid) {
		try {
			//获取是否会员
			int memberid=Integer.parseInt(map.get("checkmember").toString());
			//查询总条数的SQL
			StringBuffer sqlnum=new StringBuffer("select count(1) from systemnewsinfo where userid=? or typeid=2");
			//分页查询HQL
			StringBuffer hqlstr=new StringBuffer("select * from systemnewsinfo as a where a.userid=? or typeid=2");
			//根据SQL语句查询
			if(memberid>0){  //是会员
				sqlnum.append(" or typeid=3");
				hqlstr.append(" or typeid=3");
			}
			int count=selectBlogCount(sqlnum.toString(),userid);
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
				pagenum=20;
			}
			//分页查询语句排序
			hqlstr.append(" order by a.id desc");
			Query query=session.getCurrentSession().createSQLQuery(hqlstr.toString()).addEntity(SystemNewsInfo.class).setInteger(0, userid);
			query.setFirstResult((page-1)*pagenum);		//分页开始
			query.setMaxResults(pagenum);		//每页条数
			List<SystemNewsInfo> list=query.list();
			map.put("nowpage",page);	//当前页数
			map.put("counts", count);	//总条数
			map.put("pagecount", pagenum);		//每页条数
			map.put("page",countpage);	//总页数
			map.put("systemnewslist", list);	//每页的数据
			return map;
		} catch (Exception e) {
			return map;
		}
		
		
	}

	//查询信息的总页数1
	private int selectBlogCount(String sql,int status) {
		try {
			Object res=session.getCurrentSession().createSQLQuery(sql).setInteger(0, status).uniqueResult();
			return Integer.parseInt(res.toString());
		} catch (Exception e) {
			return 0;
		}
	}

	/***1.后台查询显示系统信息记录***/
	@Override
	public Map<String, Object> selectSystemPageList(Map<String, Object> map) {
		try {
			//查询总条数的SQL
			StringBuffer sqlnum=new StringBuffer("select count(*) from systemnewsinfo m where m.stratus=?");
			//分页查询HQL
			StringBuffer hqlstr=new StringBuffer("from SystemNewsInfo as b where b.stratus=?");
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
				pagenum=10;
			}
			//分页查询语句排序
			hqlstr.append(" order by b.id desc");
			Query query=session.getCurrentSession().createQuery(hqlstr.toString()).setInteger(0, Integer.parseInt(map.get("bgstatrus").toString()));
			query.setFirstResult((page-1)*pagenum);		//分页开始
			query.setMaxResults(pagenum);		//每页条数
			@SuppressWarnings("unchecked")
			List<SystemNewsInfo> list=query.list();
			map.put("nowpage",page);	//当前页数
			map.put("counts", count);	//总条数
			map.put("pagecount", pagenum);		//每页条数
			map.put("page",countpage);	//总页数
			map.put("systemnewslist", list);	//每页的数据
			return map;
		} catch (Exception e) {
			return map;
		}
		
	}

	/***2.添加的系统消息的方法***/
	@Override
	public boolean insertIntoSystemOneToDB(SystemNewsInfo sys) {
		try {
			session.getCurrentSession().saveOrUpdate(sys);
			return true;
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
	}

	/***3.根据编号查询单个信息***/
	@Override
	public SystemNewsInfo selectSystemByOneID(int id) {
		try {
			String hql="from SystemNewsInfo where id=?";
			SystemNewsInfo sys=(SystemNewsInfo) this.session.getCurrentSession().createQuery(hql).setInteger(0, id).uniqueResult();
			if(sys!=null){
				return sys;
			}else{
				return null;
			}
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
		
	}

	/***4.删除单条系统信息的方法***/
	@Override
	public boolean deleteSystemNewsInfoByID(int id, int stratus) {
		try {
			String sql="update systemnewsinfo set stratus=? where id=?";
			int num=session.getCurrentSession().createSQLQuery(sql).setInteger(0, stratus).setInteger(1, id).executeUpdate();
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
