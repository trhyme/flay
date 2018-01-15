package com.java.letch.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.java.letch.daoimp.MenuInfoDaoImp;
import com.java.letch.model.MenuInfo;
/***
 * 后台菜单栏数据操作类
 * @author Administrator
 * 2017-09-13
 */
@Repository(value="MenuInfoDao")
public class MenuInfoDao implements MenuInfoDaoImp {

	@Resource(name="sessionFactory")
	private SessionFactory session;		//数据源
	/**分页查询信息**/
	@Override
	public Map<String, Object> selectListByPage(Map<String, Object> map) {
		try {
			//查询总条数的SQL
			StringBuffer sqlnum=new StringBuffer("select count(*) from MenuInfo m where m.stats=?");
			//分页查询HQL
			StringBuffer hqlstr=new StringBuffer("from MenuInfo as b where b.stats=?");
			
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
			List<MenuInfo> list=query.list();
			map.put("nowpage",page);	//当前页数
			map.put("counts", count);	//总条数
			map.put("pagecount", pagenum);		//每页条数
			map.put("page",countpage);	//总页数
			map.put("menulist", list);	//每页的数据
			return map;
			
		} catch (Exception e) {
			return map;
		}
	}
	
	//查询总数
	private int selectBlogCount(String sql,int status) {
		try {
			Object res=session.getCurrentSession().createSQLQuery(sql).setInteger(0, status).uniqueResult();
			return Integer.parseInt(res.toString());
		} catch (Exception e) {
			return 0;
		}
	}

	//查询出总的菜单信息
	@SuppressWarnings("unchecked")
	@Override
	public List<MenuInfo> selectMenuAllList() {
		try {
			return session.getCurrentSession().createQuery("from MenuInfo").list();
		} catch (Exception e) {
			return null;
		}
	}

	//添加菜单信息
	@Override
	public boolean addMenutoDB(MenuInfo menu) {
		try {
			session.getCurrentSession().save(menu);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	//根据ID查询单条数据
	@Override
	public MenuInfo SelectMenuById(String id) {
		try {
			String hql="from MenuInfo where id=?";
			return (MenuInfo) session.getCurrentSession().createQuery(hql).setInteger(0, Integer.parseInt(id)).uniqueResult();
			
		} catch (Exception e) {
			return null;
		}
	}

	//根据编号修改
	@Override
	public boolean updateMenuByMenu(MenuInfo menu) {
		try {
			session.getCurrentSession().saveOrUpdate(menu);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**根据菜单编号和用户编号删除信息**/
	@Override
	public boolean deleteMenuAndUser(String mid, String userid) {
		try {
			String sql="delete from MenuUserInfo where admid="+Integer.parseInt(userid)+" and menid="+Integer.parseInt(mid);
			if(session.getCurrentSession().createSQLQuery(sql).executeUpdate()>0){
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}
	
}
