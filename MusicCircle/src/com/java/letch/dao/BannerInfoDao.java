package com.java.letch.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.java.letch.daoimp.BannerInfoDaoImp;
import com.java.letch.model.BannerInfo;
import com.java.letch.model.MenuInfo;

/***
 * Banner图数据操作类
 * @author Administrator
 * 2017-09-19
 */
@Repository(value="BannerInfoDao")
public class BannerInfoDao implements BannerInfoDaoImp {

	@Resource(name="sessionFactory")
	private SessionFactory session; //数据源
	
	/*后台分页查看信息*/
	@Override
	public Map<String, Object> selectBannerPage(Map<String, Object> map) {
		try {
			//查询总条数的SQL
			StringBuffer sqlnum=new StringBuffer("select count(*) from bannerinfo m where m.stratus=?");
			//分页查询HQL
			StringBuffer hqlstr=new StringBuffer("from BannerInfo as b where b.stratus=?");
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
			map.put("bannerlist", list);	//每页的数据
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

	/*添加Banner的方法*/
	@Override
	public boolean insertBannerToDB(BannerInfo banner) {
		try {
			session.getCurrentSession().save(banner);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/*根据编号查询信息*/
	@Override
	public BannerInfo selectBannerById(String id) {
		try {
			String hql="from BannerInfo where id=?";
			return (BannerInfo) session.getCurrentSession().createQuery(hql).setInteger(0, Integer.parseInt(id)).uniqueResult();
		} catch (Exception e) {
			return null;
		}
	}

	/*修改信息*/
	@Override
	public boolean updateBannerByBanner(BannerInfo banner) {
		try {
			session.getCurrentSession().update(banner);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**1.APP查询出最新四张Banner图片**/
	@SuppressWarnings("unchecked")
	@Override
	public List<BannerInfo> selectBannerList(int num) {
		try {
			if(num<=0){
				num=4;
			}
			String sql="from BannerInfo ORDER BY ordernum ,addtimes DESC LIMIT "+num;
			return session.getCurrentSession().createQuery(sql).list();
		} catch (Exception e) {
			return null;
		}
	}
	
	
}
