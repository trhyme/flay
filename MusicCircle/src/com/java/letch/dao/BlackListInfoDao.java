package com.java.letch.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.java.letch.daoimp.BlackListInfoDaoImp;
import com.java.letch.model.BlackListInfo;
import com.java.letch.model.MemberInfo;
import com.java.letch.model.view.BlackListinfoView;
import com.java.letch.tools.DateHelper;

/***
 * 黑名单数据操作类
 * @author Administrator
 * 2017-11-10
 */
@Repository(value="BlackListInfoDao")
public class BlackListInfoDao implements BlackListInfoDaoImp {

	@Resource(name="sessionFactory")
	private SessionFactory session;		//数据源
	
	//1.添加黑名称
	@Override
	public boolean insertBlackListIntoDB(BlackListInfo black) {
		try {
			session.getCurrentSession().save(black);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	//2.分页查询黑名单信息
	@Override
	public Map<String, Object> seelctBlockListPage(Map<String, Object> map,int userid) {
		try {
			//查询总条数的SQL
			StringBuffer sqlnum=new StringBuffer("select count(1) from blacklistinfoview where mainuser=?");
			//分页查询HQL
			StringBuffer hqlstr=new StringBuffer("select * from blacklistinfoview as a where a.mainuser=?");
			//根据SQL语句已经状态查询
			int count=selectBlogCountTwo(sqlnum.toString(),userid);
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
			Query query=session.getCurrentSession().createSQLQuery(hqlstr.toString()).addEntity(BlackListinfoView.class).setInteger(0,userid);
			query.setFirstResult((page-1)*pagenum);		//分页开始
			query.setMaxResults(pagenum);		//每页条数
			@SuppressWarnings("unchecked")
			List<BlackListinfoView> list=query.list();
			
			if(list!=null && list.size()>0){
				for(int x=0;x<list.size();x++){
					//查询会员信息
					//3.会员记录信息
					String hql3="from MemberInfo where uid=?";
					MemberInfo member=(MemberInfo) session.getCurrentSession().createQuery(hql3).setInteger(0, list.get(x).getSecondid()).uniqueResult();
					if(member!=null){
						//判断会员是否过期
						//计算会员天数
          				//1.会员开始时间--->到当前时间的天数
          				int num1=DateHelper.getDaysBetween(member.getBegintime(), DateHelper.getNewData());
          				//2.会员开始时间--->会员到期时间
          				int num2=DateHelper.getDaysBetween(member.getBegintime(), member.getEndestime());
          				if(num1<=num2){   		//1.会员还未到期
          					member.setStrutus(1);
          					list.get(x).checkmember=1;
          				}else{
          					member.setStrutus(0);
          					list.get(x).checkmember=0;
          				}
					}else{
						list.get(x).checkmember=0;
					}
					
				}
			}
			
			map=new HashMap<>();
			map.put("nowpage",page);	//当前页数
			map.put("counts", count);	//总条数
			map.put("pagecount", pagenum);		//每页条数
			map.put("page",countpage);	//总页数
			map.put("blacklist",list);	//每页的数据
			return map;
		} catch (Exception e) {
			System.out.println(e);
		}
		return map;
	}

	//查询信息的总页数
	private int selectBlogCountTwo(String sql,int status) {
		try {
			Object res=session.getCurrentSession().createSQLQuery(sql).setInteger(0, status).uniqueResult();
			return Integer.parseInt(res.toString());
		} catch (Exception e) {
			return 0;
		}
	}
	
	
	//3.解除拉黑名单信息
	@Override
	public boolean userBlockRemove(int userid, int blockuserid) {
		try {
			System.out.println(userid+":"+blockuserid);
			String sql="delete from blacklistinfo where mainuser=? and secondid=?";
			int num=session.getCurrentSession().createSQLQuery(sql).setInteger(0, userid).setInteger(1, blockuserid).executeUpdate();
			if(num>0){
				return true;
			}else{
				return false;
			}
			
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
	}
	
}
