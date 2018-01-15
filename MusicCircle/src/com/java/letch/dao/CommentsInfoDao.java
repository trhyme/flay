package com.java.letch.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.java.letch.daoimp.CommentsInfoDaoImp;
import com.java.letch.model.AgreesInfo;
import com.java.letch.model.CommentGetupInfo;
import com.java.letch.model.CommentsInfo;
import com.java.letch.model.MemberInfo;
import com.java.letch.model.VoicesInfo;
import com.java.letch.model.view.CommitsUserViceosView;
import com.java.letch.model.view.CommitsUserView;
import com.java.letch.tools.DateHelper;

/***
 * 用户的评论信息数据操作类
 * @author Administrator
 * 2017-10-19
 */
@Repository(value="CommentsInfoDao")
public class CommentsInfoDao implements CommentsInfoDaoImp {

	@Resource(name="sessionFactory")
	private SessionFactory session;		//数据源
	
	//1.用户添加评论信息
	@Override
	public boolean insertIntoCommentsOne(CommentsInfo comm) {
		try {
			session.getCurrentSession().save(comm);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	//2.根据语音动态编号查询出评论信息
	@Override
	public Map<String, Object> selectCommitsPage(Map<String, Object> map,int voicesid) {
		try {
			//查询总条数的SQL
			StringBuffer sqlnum=new StringBuffer("select count(1) from commitsuserview where voices_id="+voicesid+" and type<>4");
			//分页查询HQL
			StringBuffer hqlstr=new StringBuffer("select * from commitsuserview as a where a.voices_id="+voicesid+" and type<>4");
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
				pagenum=20;
			}
			//分页查询语句排序
			hqlstr.append(" order by a.id desc");
			Query query=session.getCurrentSession().createSQLQuery(hqlstr.toString()).addEntity(CommitsUserView.class);
			query.setFirstResult((page-1)*pagenum);		//分页开始
			query.setMaxResults(pagenum);		//每页条数
			@SuppressWarnings("unchecked")
			List<CommitsUserView> list=query.list();
			int userid=Integer.parseInt(map.get("userid").toString());
			if(list!=null && list.size()>0){
				for(int i=0;i<list.size();i++){
					String hql="from CommentGetupInfo where userid="+userid+" and commid="+list.get(i).getId();
					List<CommentGetupInfo> listzan=this.session.getCurrentSession().createQuery(hql).list();
					if(listzan!=null && listzan.size()>0){
						list.get(i).checkup=1;
					}
					//会员字段
					//3.会员记录信息
					String hql3="from MemberInfo where uid=?";
					MemberInfo member=(MemberInfo) session.getCurrentSession().createQuery(hql3).setInteger(0, list.get(i).getUsers_id()).uniqueResult();
					if(member!=null){
						//判断会员是否过期
						//计算会员天数
          				//1.会员开始时间--->到当前时间的天数
          				int num1=DateHelper.getDaysBetween(member.getBegintime(), DateHelper.getNewData());
          				//2.会员开始时间--->会员到期时间
          				int num2=DateHelper.getDaysBetween(member.getBegintime(), member.getEndestime());
          				if(num1<=num2){   		//1.会员还未到期
          					member.setStrutus(1);
          					list.get(i).checkmember=1;
          				}else{
          					member.setStrutus(0);
          					list.get(i).checkmember=0;
          				}
					}else{
						list.get(i).checkmember=0;
					}
				}
			}
			map.put("nowpage",page);	//当前页数
			map.put("counts", count);	//总条数
			map.put("pagecount", pagenum);		//每页条数
			map.put("page",countpage);	//总页数
			map.put("commitslist",list);	//每页的数据
			return map;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}
	
	
	/****新的消息*****/
	//6.根据用户编号查询出新的回复消息
	@Override
	public Map<String, Object> selectCommitsUserViceosView(Map<String, Object> map,int userid) {
		try {
			//查询总条数的SQL
			StringBuffer sqlnum=new StringBuffer("select count(1) from commitsuserviceosview where theuserid=?");
			//分页查询HQL
			StringBuffer hqlstr=new StringBuffer("select * from commitsuserviceosview as a where a.theuserid=?");
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
				pagenum=15;
			}
			Query query=session.getCurrentSession().createSQLQuery(hqlstr.toString()).addEntity(CommitsUserViceosView.class).setInteger(0, userid);
			query.setFirstResult((page-1)*pagenum);		//分页开始
			query.setMaxResults(pagenum);		//每页条数
			List<CommitsUserViceosView> list=query.list();
			//list=query.list();
			map.put("nowpage",page);	//当前页数
			map.put("counts", count);	//总条数
			map.put("pagecount", pagenum);		//每页条数
			map.put("page",countpage);	//总页数
			map.put("replylist",list);	//每页的数据
			return map;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
		
	}
	/****新的消息*****/
	
	
	//查询信息的总页数
	private int selectBlogCountTwo(String sql,int status) {
		try {
			Object res=session.getCurrentSession().createSQLQuery(sql).setInteger(0, status).uniqueResult();
			return Integer.parseInt(res.toString());
		} catch (Exception e) {
			return 0;
		}
	}
	
	//查询信息的总页数
	private int selectBlogCount(String sql) {
		try {
			Object res=session.getCurrentSession().createSQLQuery(sql).uniqueResult();
			return Integer.parseInt(res.toString());
		} catch (Exception e) {
			return 0;
		}
	}
	
	//3.根据动态编号查询总的评论数
	@Override
	public int selectCommentCountById(int voicesid) {
		try {
			String sql="select count(1) from comments where voices_id=? and type<>4";
			Object res=session.getCurrentSession().createSQLQuery(sql).setInteger(0, voicesid).uniqueResult();
			return Integer.parseInt(res.toString());
		} catch (Exception e) {
			System.out.println(e);
			return 0;
		}
	}

	//4.跟新评论总数
	@Override
	public boolean updateVoicesCommentnumById(int voicesid,int count) {
		try {
			String sql="update voices set commentnum=? where id=?";
			if((session.getCurrentSession().createSQLQuery(sql).setInteger(0, count).setInteger(1, voicesid).executeUpdate())>0){
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}

	//5.根据编号查询单个信息
	@Override
	public CommitsUserView seelctCommitsUserViewOne(int id) {
		try {
			String hql="from CommitsUserView where id=?";
			return (CommitsUserView) session.getCurrentSession().createQuery(hql).setInteger(0, id).uniqueResult();
		} catch (Exception e) {
			return null;
		}
		
	}

	
	
	
}
