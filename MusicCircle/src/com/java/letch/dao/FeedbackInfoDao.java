package com.java.letch.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.java.letch.daoimp.FeedbackInfoDaoImp;
import com.java.letch.model.FeedbackInfo;
import com.java.letch.model.MenuInfo;
import com.java.letch.tools.DateHelper;

/***
 * 用户反馈信息的数据操作类
 * @author Administrator
 * 2017-10-09
 */
@Repository(value="FeedbackInfoDao")
public class FeedbackInfoDao implements FeedbackInfoDaoImp {

	@Resource(name="sessionFactory")
	private SessionFactory session;		//数据源
	
	//分页查询
	@Override
	public Map<String, Object> selectFeedBackPage(Map<String, Object> map) {
		try {
			//查询总条数的SQL
			StringBuffer sqlnum=new StringBuffer("select count(*) from feedbackinfo");
			//分页查询HQL
			StringBuffer hqlstr=new StringBuffer("from FeedbackInfo as b");
			
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
			map.put("feedbacklist", list);	//每页的数据
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

	//修改回复信息的方法
	@Override
	public boolean updateFeedbackReplyes(int id, String replyes) {
		try {
			String sql="update feedbackinfo set replyes=?,uptimes=?,stratus=1 where id=?";
			int num=session.getCurrentSession().createSQLQuery(sql).setString(0, replyes).setString(1, DateHelper.getNewData()).setInteger(2, id).executeUpdate();
			if(num>0){
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}
	
	//添加反馈信息
	@Override
	public boolean insertFeedbackOne(FeedbackInfo feed) {
		try {
			session.getCurrentSession().save(feed);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/*首页查询出5条最新的反馈信息*/
	@Override
	public List<FeedbackInfo> selectFeedbackInfoFive(int num) {
		try {
			String hql="select * from feedbackinfo ORDER BY id DESC LIMIT "+num;
			@SuppressWarnings("unchecked")
			List<FeedbackInfo> list=session.getCurrentSession().createSQLQuery(hql).addEntity(FeedbackInfo.class).list();
			return list;
		} catch (Exception e) {
			return null;
		}
	}
	
}
