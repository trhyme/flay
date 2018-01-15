package com.java.letch.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.java.letch.daoimp.ReportInfoDaoImp;
import com.java.letch.model.MenuInfo;
import com.java.letch.model.ReportInfo;
import com.java.letch.tools.DateHelper;

/***
 * 用户举报信息的数据操作类
 * @author Administrator
 *
 */
@Repository(value="ReportInfoDao")
public class ReportInfoDao implements ReportInfoDaoImp{
	
	@Resource(name="sessionFactory")
	private SessionFactory session;		//数据源

	/*分页查询信息*/
	@Override
	public Map<String, Object> selectReportByPage(Map<String, Object> map) {
		try {
			//查询总条数的SQL
			StringBuffer sqlnum=new StringBuffer("select count(*) from reportinfo");
			//分页查询HQL
			StringBuffer hqlstr=new StringBuffer("from ReportInfo as b");
			
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
			map.put("reportlist", list);	//每页的数据
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

	/*根据编号查询单条信息*/
	@Override
	public ReportInfo selectReportByID(int id) {
		try {
			String hql="from ReportInfo where id=?";
			return (ReportInfo) session.getCurrentSession().createQuery(hql).setInteger(0, id).uniqueResult();
		} catch (Exception e) {
			return null;
		}
	}

	/**修改回复信息**/
	@Override
	public boolean updateReplytieByID(int id, String reply, int stra) {
		try {
			String sql="update reportinfo set replytie=?,updtimes=?,stratues=? where id=?";
			int num=session.getCurrentSession().createSQLQuery(sql).setString(0, reply).setString(1, DateHelper.getNewData()).setInteger(2, stra).setInteger(3, id).executeUpdate();
			if(num>0){
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
		}
		return false;
	}

	/***1.添加举报信息***/
	@Override
	public boolean insertReportInfo(ReportInfo rep) {
		try {
			session.getCurrentSession().saveOrUpdate(rep);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
