package com.java.letch.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.java.letch.daoimp.RecentVisitInfoDaoImp;
import com.java.letch.model.MemberInfo;
import com.java.letch.model.RecentVisitInfo;
import com.java.letch.model.view.AppIndexFollowView;
import com.java.letch.model.view.RecentVisitView;
import com.java.letch.tools.DateHelper;

/***
 * 来访数据的信息记录类
 * @author Administrator
 * 2017-11-14
 */
@Repository(value="RecentVisitInfoDao")
public class RecentVisitInfoDao implements RecentVisitInfoDaoImp {

	@Resource(name="sessionFactory")
	private SessionFactory session;		//数据源
	
	/**1.新增来访纪录**/
	@Override
	public boolean insertIntoRecentInfo(RecentVisitInfo receninfo) {
		try {
			session.getCurrentSession().saveOrUpdate(receninfo);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**2.查询单条用户的来访信息**/
	@SuppressWarnings("unchecked")
	@Override
	public List<RecentVisitView> selectOneRecentVisitView(int userid) {
		try {
			String sql="select * from recentvisitview where userseeid="+userid+" ORDER BY id DESC LIMIT 1;";
			List<Object[]> query=session.getCurrentSession().createSQLQuery(sql).list();
			if(query.size()>0){
				List<RecentVisitView> list=new ArrayList<>();
				
				for(Object[] o:query){
					RecentVisitView v=new RecentVisitView();
					if(o[0]!=null){v.setId(Integer.parseInt(o[0].toString()));}
					if(o[1]!=null){v.setUserid(Integer.parseInt(o[1].toString()));}
					if(o[2]!=null){v.setUserseeid(Integer.parseInt(o[2].toString()));}
					if(o[3]!=null){v.setAddtimes(o[3].toString());}
					if(o[4]!=null){v.setUsername(o[4].toString());}
					if(o[5]!=null){v.setHeadpic(o[5].toString());}
					if(o[6]!=null){v.setSex(o[6].toString());}
					if(o[7]!=null){v.setAge(Integer.parseInt(o[7].toString()));}
					if(o[8]!=null){v.setProvince(o[8].toString());}
					if(o[9]!=null){v.setCity(o[9].toString());}
					if(o[10]!=null){v.setArea(o[10].toString());}
					if(o[11]!=null){v.setSign(o[11].toString());}
					if(o[12]!=null){v.setFriendstate(o[12].toString());}
					//3.会员记录信息
					String hql3="from MemberInfo where uid=?";
					MemberInfo member=(MemberInfo) session.getCurrentSession().createQuery(hql3).setInteger(0, Integer.parseInt(o[1].toString())).uniqueResult();
					if(member!=null){
						//判断会员是否过期
						//计算会员天数
          				//1.会员开始时间--->到当前时间的天数
          				int num1=DateHelper.getDaysBetween(member.getBegintime(), DateHelper.getNewData());
          				//2.会员开始时间--->会员到期时间
          				int num2=DateHelper.getDaysBetween(member.getBegintime(), member.getEndestime());
          				if(num1<=num2){   		//1.会员还未到期
          					member.setStrutus(1);
          					v.checkmember=1;
          				}else{
          					member.setStrutus(0);
          					v.checkmember=0;
          				}
					}else{
						v.checkmember=0;
					}
					list.add(v);
				}
				return list;
			}else{
				return null;
			}
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}

	/**3.分页查询查询来访信息**/
	@Override
	public Map<String, Object> selectRecentVisitView(Map<String, Object> map,int userid) {
		try {
			//查询总条数的SQL
			StringBuffer sqlnum=new StringBuffer("select count(1) from recentvisitview where userseeid=?");
			//分页查询HQL
			StringBuffer hqlstr=new StringBuffer("select * from recentvisitview as a where a.userseeid=?");
			//根据SQL语句查询
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
			Query query=session.getCurrentSession().createSQLQuery(hqlstr.toString()).setInteger(0,userid);
			query.setFirstResult((page-1)*pagenum);		//分页开始
			query.setMaxResults(pagenum);		//每页条数
			@SuppressWarnings("unchecked")
			List<Object[]> queryList=query.list();
			List<RecentVisitView> list=new ArrayList<>();
			if(queryList.size()>0){
				for(Object[] o:queryList){
					RecentVisitView v=new RecentVisitView();
					if(o[0]!=null){v.setId(Integer.parseInt(o[0].toString()));}
					if(o[1]!=null){v.setUserid(Integer.parseInt(o[1].toString()));}
					if(o[2]!=null){v.setUserseeid(Integer.parseInt(o[2].toString()));}
					if(o[3]!=null){v.setAddtimes(o[3].toString());}
					if(o[4]!=null){v.setUsername(o[4].toString());}
					if(o[5]!=null){v.setHeadpic(o[5].toString());}
					if(o[6]!=null){v.setSex(o[6].toString());}
					if(o[7]!=null){v.setAge(Integer.parseInt(o[7].toString()));}
					if(o[8]!=null){v.setProvince(o[8].toString());}
					if(o[9]!=null){v.setCity(o[9].toString());}
					if(o[10]!=null){v.setArea(o[10].toString());}
					if(o[11]!=null){v.setSign(o[11].toString());}
					if(o[12]!=null){v.setFriendstate(o[12].toString());}
					
					//会员判断
					//3.会员记录信息
					String hql3="from MemberInfo where uid=?";
					MemberInfo member=(MemberInfo) session.getCurrentSession().createQuery(hql3).setInteger(0, Integer.parseInt(o[1].toString())).uniqueResult();
					if(member!=null){
						//判断会员是否过期
						//计算会员天数
          				//1.会员开始时间--->到当前时间的天数
          				int num1=DateHelper.getDaysBetween(member.getBegintime(), DateHelper.getNewData());
          				//2.会员开始时间--->会员到期时间
          				int num2=DateHelper.getDaysBetween(member.getBegintime(), member.getEndestime());
          				if(num1<=num2){   		//1.会员还未到期
          					member.setStrutus(1);
          					v.checkmember=1;
          				}else{
          					member.setStrutus(0);
          					v.checkmember=0;
          				}
					}else{
						v.checkmember=0;
					}
					
					list.add(v);
				}
			}
			map=new HashMap<>();
			map.put("nowpage",page);	//当前页数
			map.put("counts", count);	//总条数
			map.put("pagecount", pagenum);		//每页条数
			map.put("page",countpage);	//总页数
			map.put("recentvisitlist",list);	//每页的数据
			return map;
		} catch (Exception e) {
			System.err.println(e);
		}
		return map;
	}
	
	private int selectBlogCount(String sql,int status) {
		try {
			Object res=session.getCurrentSession().createSQLQuery(sql).setInteger(0, status).uniqueResult();
			return Integer.parseInt(res.toString());
		} catch (Exception e) {
			return 0;
		}
	}

}
