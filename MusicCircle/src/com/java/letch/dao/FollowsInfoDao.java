package com.java.letch.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.java.letch.daoimp.FollowsInfoDaoImp;
import com.java.letch.model.FollowsInfo;
import com.java.letch.model.IntimacyInfo;
import com.java.letch.model.MemberInfo;
import com.java.letch.model.view.FollowMeView;
import com.java.letch.model.view.MyFollowView;
import com.java.letch.service.IntimacyInfoService;
import com.java.letch.tools.DateHelper;

/***
 * APP用户关注信息数据操作类
 * @author Administrator
 * 2017-09-26
 */
@Repository(value="FollowsInfoDao")
public class FollowsInfoDao implements FollowsInfoDaoImp {

	@Resource(name="sessionFactory")
	private SessionFactory session; //数据源
	
	//亲密度数据操作类
	@Resource(name="IntimacyInfoService")
	private IntimacyInfoService intimacyinfoService;	
	
	/*APP用户添加关注信息*/
	@Override
	public boolean insertAddFollows(FollowsInfo follow) {
		try {
			session.getCurrentSession().save(follow);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**1.根据关注者编号以及被关注者编号查询是否被关注**/
	@Override
	public FollowsInfo selectFollowsByTwoID(int idA, int idB) {
		try {
			String sql="select count(1) from follows where users_id=? and followsid=?";
			Object res=session.getCurrentSession().createSQLQuery(sql).setInteger(0, idA).setInteger(1, idB).uniqueResult();
			//System.out.println("id1:"+idA);
			//System.out.println("id2:"+idB);
			if(Integer.parseInt(res.toString())>0){
				System.out.println(res);
				return new FollowsInfo();
			}else{
				return null;
			}
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}

	/**2.修改互相关注信息**/
	@Override
	public boolean updateFollowsMutualnumByTwoID(int idA, int idB,int stratus) {
		try {
			String sql="update follows set mutualnum="+stratus+" where (users_id=? and followsid=?) or (users_id=? and followsid=?)";
			int num=session.getCurrentSession().createSQLQuery(sql).setInteger(0, idA).setInteger(1, idB).setInteger(2, idB).setInteger(3, idA).executeUpdate();
			if(num>0){
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}

	
	/***3.根据编号分页查询该用户的关注信息***/
	@Override
	public Map<String, Object> selectFollowsByID(Map<String, Object> map,int uid) {
		try {
			//查询总条数的SQL
			StringBuffer sqlnum=new StringBuffer("select count(1) from follows as a INNER JOIN users as b ON a.followsid=b.id where a.users_id=?");
			//分页查询HQL
			StringBuffer hqlstr=new StringBuffer("select a.*,b.num,b.phone,b.username,b.headpic from follows as a INNER JOIN users as b ON a.followsid=b.id where a.users_id=?");
			//根据SQL语句已经状态查询
			int count=selectBlogCount(sqlnum.toString(),uid);
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
			hqlstr.append(" order by a.id desc");
			Query query=session.getCurrentSession().createSQLQuery(hqlstr.toString()).setInteger(0, uid);
			query.setFirstResult((page-1)*pagenum);		//分页开始
			query.setMaxResults(pagenum);		//每页条数
			@SuppressWarnings("unchecked")
			List<String> list=query.list();
			map.put("nowpage",page);	//当前页数
			map.put("counts", count);	//总条数
			map.put("pagecount", pagenum);		//每页条数
			map.put("page",countpage);	//总页数
			map.put("followlist", list);	//每页的数据
			return map;
			
		} catch (Exception e) {
		}
		return null;
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
	
	//查询信息的总页数2
	private int selectBlogCountTwo(String sql) {
		try {
			Object res=session.getCurrentSession().createSQLQuery(sql).uniqueResult();
			return Integer.parseInt(res.toString());
		} catch (Exception e) {
			return 0;
		}
	}
	
	

	/***4.根据编号分页查询该用户的粉丝信息***/
	@Override
	public Map<String, Object> selectFansByID(Map<String, Object> map, int uid) {
		try {
			//查询总条数的SQL
			StringBuffer sqlnum=new StringBuffer("select count(1) from follows as a INNER JOIN users as b ON a.users_id=b.id where a.followsid=?");
			//分页查询HQL
			StringBuffer hqlstr=new StringBuffer("select a.*,b.num,b.phone,b.username,b.headpic from follows as a INNER JOIN users as b ON a.users_id=b.id where a.followsid=?");
			//根据SQL语句已经状态查询
			int count=selectBlogCount(sqlnum.toString(),uid);
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
			hqlstr.append(" order by a.id desc");
			Query query=session.getCurrentSession().createSQLQuery(hqlstr.toString()).setInteger(0, uid);
			query.setFirstResult((page-1)*pagenum);		//分页开始
			query.setMaxResults(pagenum);		//每页条数
			@SuppressWarnings("unchecked")
			List<String> list=query.list();
			map.put("nowpage",page);	//当前页数
			map.put("counts", count);	//总条数
			map.put("pagecount", pagenum);		//每页条数
			map.put("page",countpage);	//总页数
			map.put("fanslist", list);	//每页的数据
			return map;
		} catch (Exception e) {
		}
		return null;
	}
	
	/***5.取消关注***/
	@Override
	public boolean deleteFansByFollows(FollowsInfo follow,int idA,int idB) {
		try {
			String sql="delete from follows where users_id=? and followsid=?";
			//System.out.println("关注ID："+follow.getId());
			int num=-1;
			num=session.getCurrentSession().createSQLQuery(sql).setInteger(0, idA).setInteger(1, idB).executeUpdate();
			if(num<=0){
				return false;
			}else{
				return true;
			}
			
		} catch (Exception e) {
			return false;
		}
	}
	
	/***【心动我的1】***/
	/* (non-Javadoc)
	 * @see com.java.letch.daoimp.FollowsInfoDaoImp#selectFollowsMePage(java.util.Map, int)
	 */
	@Override
	public Map<String, Object> selectFollowsMePage(Map<String, Object> map, int userid) {
		try {
			/*int memberid=Integer.parseInt(map.get("checkmember").toString());*/
			/*if(memberid<=0){		//不是会员
				String sql="select * from followmeview where followsid=? ORDER BY id LIMIT 1";
				List<Object[]> query=session.getCurrentSession().createSQLQuery(sql).setInteger(0, userid).list();
				List<FollowMeView> list=new ArrayList<>();
				if(query!=null && query.size()>0){
					for(Object[] o:query){
						FollowMeView f=new FollowMeView();
						if(o[0]!=null && o[0].toString()!=""){f.setId(Integer.parseInt(o[0].toString()));}
						if(o[1]!=null && o[1].toString()!=""){f.setDate(o[1].toString());}
						if(o[2]!=null && o[2].toString()!=""){f.setUsers_id(Integer.parseInt(o[2].toString()));}
						if(o[3]!=null && o[3].toString()!=""){f.setFollowsid(Integer.parseInt(o[3].toString()));}
						if(o[4]!=null && o[4].toString()!=""){f.setBecknum(Integer.parseInt(o[4].toString()));}
						if(o[5]!=null && o[5].toString()!=""){f.setMutualnum(Integer.parseInt(o[5].toString()));}
						if(o[6]!=null && o[6].toString()!=""){f.setUsername(o[6].toString());}
						if(o[7]!=null && o[7].toString()!=""){f.setHeadpic(o[7].toString());}
						if(o[8]!=null && o[8].toString()!=""){f.setSex(o[8].toString());}
						if(o[9]!=null && o[9].toString()!=""){f.setAge(Integer.parseInt(o[9].toString()));}
						if(o[10]!=null && o[10].toString()!=""){f.setProvince(o[10].toString());}
						if(o[11]!=null && o[11].toString()!=""){f.setCity(o[11].toString());}
						if(o[12]!=null && o[12].toString()!=""){f.setArea(o[12].toString());}
						if(o[13]!=null && o[13].toString()!=""){f.setSign(o[13].toString());}
						if(o[14]!=null && o[14].toString()!=""){f.setFriendstate(o[14].toString());}
						//3.会员记录信息
						String hql3="from MemberInfo where uid=?";
						MemberInfo member=(MemberInfo) session.getCurrentSession().createQuery(hql3).setInteger(0, Integer.parseInt(o[2].toString())).uniqueResult();
						if(member!=null){
							//判断会员是否过期
							//计算会员天数
	          				//1.会员开始时间--->到当前时间的天数
	          				int num1=DateHelper.getDaysBetween(member.getBegintime(), DateHelper.getNewData());
	          				//2.会员开始时间--->会员到期时间
	          				int num2=DateHelper.getDaysBetween(member.getBegintime(), member.getEndestime());
	          				if(num1<=num2){   		//1.会员还未到期
	          					member.setStrutus(1);
	          					f.checkmember=1;
	          				}else{
	          					member.setStrutus(0);
	          					f.checkmember=0;
	          				}
						}else{
							f.checkmember=0;
						}
						list.add(f);
					}
				}
				map.put("nowpage",1);	//当前页数
				map.put("counts", list.size());	//总条数
				map.put("pagecount", list.size());		//每页条数
				map.put("page",1);	//总页数
				map.put("followslist", list);
			}else{					//会员
				//查询总条数的SQL
				StringBuffer sqlnum=new StringBuffer("select count(1) from followmeview where followsid=?");
				//分页查询HQL
				StringBuffer hqlstr=new StringBuffer("select * from followmeview as a where a.followsid=?");
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
				List<FollowMeView> list=new ArrayList<>();
				if(queryList!=null && queryList.size()>0){
					for(Object[] o:queryList){
						FollowMeView f=new FollowMeView();
						if(o[0]!=null && o[0].toString()!=""){f.setId(Integer.parseInt(o[0].toString()));}
						if(o[1]!=null && o[1].toString()!=""){f.setDate(o[1].toString());}
						if(o[2]!=null && o[2].toString()!=""){f.setUsers_id(Integer.parseInt(o[2].toString()));}
						if(o[3]!=null && o[3].toString()!=""){f.setFollowsid(Integer.parseInt(o[3].toString()));}
						if(o[4]!=null && o[4].toString()!=""){f.setBecknum(Integer.parseInt(o[4].toString()));}
						if(o[5]!=null && o[5].toString()!=""){f.setMutualnum(Integer.parseInt(o[5].toString()));}
						if(o[6]!=null && o[6].toString()!=""){f.setUsername(o[6].toString());}
						if(o[7]!=null && o[7].toString()!=""){f.setHeadpic(o[7].toString());}
						if(o[8]!=null && o[8].toString()!=""){f.setSex(o[8].toString());}
						if(o[9]!=null && o[9].toString()!=""){f.setAge(Integer.parseInt(o[9].toString()));}
						if(o[10]!=null && o[10].toString()!=""){f.setProvince(o[10].toString());}
						if(o[11]!=null && o[11].toString()!=""){f.setCity(o[11].toString());}
						if(o[12]!=null && o[12].toString()!=""){f.setArea(o[12].toString());}
						if(o[13]!=null && o[13].toString()!=""){f.setSign(o[13].toString());}
						if(o[14]!=null && o[14].toString()!=""){f.setFriendstate(o[14].toString());}
						//3.会员记录信息
						String hql3="from MemberInfo where uid=?";
						MemberInfo member=(MemberInfo) session.getCurrentSession().createQuery(hql3).setInteger(0, Integer.parseInt(o[2].toString())).uniqueResult();
						if(member!=null){
							//判断会员是否过期
							//计算会员天数
	          				//1.会员开始时间--->到当前时间的天数
	          				int num1=DateHelper.getDaysBetween(member.getBegintime(), DateHelper.getNewData());
	          				//2.会员开始时间--->会员到期时间
	          				int num2=DateHelper.getDaysBetween(member.getBegintime(), member.getEndestime());
	          				if(num1<=num2){   		//1.会员还未到期
	          					member.setStrutus(1);
	          					f.checkmember=1;
	          				}else{
	          					member.setStrutus(0);
	          					f.checkmember=0;
	          				}
						}else{
							f.checkmember=0;
						}
						list.add(f);
					}
				}
				map.put("nowpage",page);	//当前页数
				map.put("counts", count);	//总条数
				map.put("pagecount", pagenum);		//每页条数
				map.put("page",countpage);	//总页数
				map.put("followslist", list);
				
			}*/
			//查询总条数的SQL
			StringBuffer sqlnum=new StringBuffer("select count(1) from followmeview where followsid=?");
			//分页查询HQL
			StringBuffer hqlstr=new StringBuffer("select * from followmeview as a where a.followsid=?");
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
			List<FollowMeView> list=new ArrayList<>();
			if(queryList!=null && queryList.size()>0){
				for(Object[] o:queryList){
					FollowMeView f=new FollowMeView();
					if(o[0]!=null && o[0].toString()!=""){f.setId(Integer.parseInt(o[0].toString()));}
					if(o[1]!=null && o[1].toString()!=""){f.setDate(o[1].toString());}
					if(o[2]!=null && o[2].toString()!=""){f.setUsers_id(Integer.parseInt(o[2].toString()));}
					if(o[3]!=null && o[3].toString()!=""){f.setFollowsid(Integer.parseInt(o[3].toString()));}
					if(o[4]!=null && o[4].toString()!=""){f.setBecknum(Integer.parseInt(o[4].toString()));}
					if(o[5]!=null && o[5].toString()!=""){f.setMutualnum(Integer.parseInt(o[5].toString()));}
					if(o[6]!=null && o[6].toString()!=""){f.setUsername(o[6].toString());}
					if(o[7]!=null && o[7].toString()!=""){f.setHeadpic(o[7].toString());}
					if(o[8]!=null && o[8].toString()!=""){f.setSex(o[8].toString());}
					if(o[9]!=null && o[9].toString()!=""){f.setAge(Integer.parseInt(o[9].toString()));}
					if(o[10]!=null && o[10].toString()!=""){f.setProvince(o[10].toString());}
					if(o[11]!=null && o[11].toString()!=""){f.setCity(o[11].toString());}
					if(o[12]!=null && o[12].toString()!=""){f.setArea(o[12].toString());}
					if(o[13]!=null && o[13].toString()!=""){f.setSign(o[13].toString());}
					if(o[14]!=null && o[14].toString()!=""){f.setFriendstate(o[14].toString());}
					//3.会员记录信息
					String hql3="from MemberInfo where uid=?";
					MemberInfo member=(MemberInfo) session.getCurrentSession().createQuery(hql3).setInteger(0, Integer.parseInt(o[2].toString())).uniqueResult();
					if(member!=null){
						//判断会员是否过期
						//计算会员天数
          				//1.会员开始时间--->到当前时间的天数
          				int num1=DateHelper.getDaysBetween(member.getBegintime(), DateHelper.getNewData());
          				//2.会员开始时间--->会员到期时间
          				int num2=DateHelper.getDaysBetween(member.getBegintime(), member.getEndestime());
          				if(num1<=num2){   		//1.会员还未到期
          					member.setStrutus(1);
          					f.checkmember=1;
          				}else{
          					member.setStrutus(0);
          					f.checkmember=0;
          				}
					}else{
						f.checkmember=0;
					}
					list.add(f);
				}
			}
			map.put("nowpage",page);	//当前页数
			map.put("counts", count);	//总条数
			map.put("pagecount", pagenum);		//每页条数
			map.put("page",countpage);	//总页数
			map.put("followslist", list);
			
			return map;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
		
	}

	/***【我的心动3】***/
	@Override
	public Map<String, Object> selectMyFollowsPage(Map<String, Object> map, int userid) {
		try {
			/*int memberid=Integer.parseInt(map.get("checkmember").toString());
			if(memberid<=0){		//不是会员
				String sql="select * from myfollowview where users_id=? ORDER BY id LIMIT 1";
				List<Object[]> query=session.getCurrentSession().createSQLQuery(sql).setInteger(0, userid).list();
				List<MyFollowView> list=new ArrayList<>();
				if(query!=null && query.size()>0){
					for(Object[] o:query){
						MyFollowView f=new MyFollowView();
						if(o[0]!=null && o[0].toString()!=""){f.setId(Integer.parseInt(o[0].toString()));}
						if(o[1]!=null && o[1].toString()!=""){f.setDate(o[1].toString());}
						if(o[2]!=null && o[2].toString()!=""){f.setUsers_id(Integer.parseInt(o[2].toString()));}
						if(o[3]!=null && o[3].toString()!=""){f.setFollowsid(Integer.parseInt(o[3].toString()));}
						if(o[4]!=null && o[4].toString()!=""){f.setBecknum(Integer.parseInt(o[4].toString()));}
						if(o[5]!=null && o[5].toString()!=""){f.setMutualnum(Integer.parseInt(o[5].toString()));}
						if(o[6]!=null && o[6].toString()!=""){f.setUsername(o[6].toString());}
						if(o[7]!=null && o[7].toString()!=""){f.setHeadpic(o[7].toString());}
						if(o[8]!=null && o[8].toString()!=""){f.setSex(o[8].toString());}
						if(o[9]!=null && o[9].toString()!=""){f.setAge(Integer.parseInt(o[9].toString()));}
						if(o[10]!=null && o[10].toString()!=""){f.setProvince(o[10].toString());}
						if(o[11]!=null && o[11].toString()!=""){f.setCity(o[11].toString());}
						if(o[12]!=null && o[12].toString()!=""){f.setArea(o[12].toString());}
						if(o[13]!=null && o[13].toString()!=""){f.setSign(o[13].toString());}
						if(o[14]!=null && o[14].toString()!=""){f.setFriendstate(o[14].toString());}
						//3.会员记录信息
						String hql3="from MemberInfo where uid=?";
						MemberInfo member=(MemberInfo) session.getCurrentSession().createQuery(hql3).setInteger(0, Integer.parseInt(o[2].toString())).uniqueResult();
						if(member!=null){
							//判断会员是否过期
							//计算会员天数
	          				//1.会员开始时间--->到当前时间的天数
	          				int num1=DateHelper.getDaysBetween(member.getBegintime(), DateHelper.getNewData());
	          				//2.会员开始时间--->会员到期时间
	          				int num2=DateHelper.getDaysBetween(member.getBegintime(), member.getEndestime());
	          				if(num1<=num2){   		//1.会员还未到期
	          					member.setStrutus(1);
	          					f.checkmember=1;
	          				}else{
	          					member.setStrutus(0);
	          					f.checkmember=0;
	          				}
						}else{
							f.checkmember=0;
						}
						list.add(f);
					}
				}
				map.put("nowpage",1);	//当前页数
				map.put("counts", list.size());	//总条数
				map.put("pagecount", list.size());		//每页条数
				map.put("page",1);	//总页数
				map.put("followslist", list);
			}else{					//会员
				//查询总条数的SQL
				StringBuffer sqlnum=new StringBuffer("select count(1) from myfollowview where users_id=?");
				//分页查询HQL
				StringBuffer hqlstr=new StringBuffer("select * from myfollowview as a where a.users_id=?");
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
				List<MyFollowView> list=new ArrayList<>();
				if(queryList!=null && queryList.size()>0){
					for(Object[] o:queryList){
						MyFollowView f=new MyFollowView();
						if(o[0]!=null && o[0].toString()!=""){f.setId(Integer.parseInt(o[0].toString()));}
						if(o[1]!=null && o[1].toString()!=""){f.setDate(o[1].toString());}
						if(o[2]!=null && o[2].toString()!=""){f.setUsers_id(Integer.parseInt(o[2].toString()));}
						if(o[3]!=null && o[3].toString()!=""){f.setFollowsid(Integer.parseInt(o[3].toString()));}
						if(o[4]!=null && o[4].toString()!=""){f.setBecknum(Integer.parseInt(o[4].toString()));}
						if(o[5]!=null && o[5].toString()!=""){f.setMutualnum(Integer.parseInt(o[5].toString()));}
						if(o[6]!=null && o[6].toString()!=""){f.setUsername(o[6].toString());}
						if(o[7]!=null && o[7].toString()!=""){f.setHeadpic(o[7].toString());}
						if(o[8]!=null && o[8].toString()!=""){f.setSex(o[8].toString());}
						if(o[9]!=null && o[9].toString()!=""){f.setAge(Integer.parseInt(o[9].toString()));}
						if(o[10]!=null && o[10].toString()!=""){f.setProvince(o[10].toString());}
						if(o[11]!=null && o[11].toString()!=""){f.setCity(o[11].toString());}
						if(o[12]!=null && o[12].toString()!=""){f.setArea(o[12].toString());}
						if(o[13]!=null && o[13].toString()!=""){f.setSign(o[13].toString());}
						if(o[14]!=null && o[14].toString()!=""){f.setFriendstate(o[14].toString());}
						list.add(f);
					}
				}
				map.put("nowpage",page);	//当前页数
				map.put("counts", count);	//总条数
				map.put("pagecount", pagenum);		//每页条数
				map.put("page",countpage);	//总页数
				map.put("followslist", list);
				
			}*/
			//查询总条数的SQL
			StringBuffer sqlnum=new StringBuffer("select count(1) from myfollowview where users_id=?");
			//分页查询HQL
			StringBuffer hqlstr=new StringBuffer("select * from myfollowview as a where a.users_id=?");
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
			List<MyFollowView> list=new ArrayList<>();
			if(queryList!=null && queryList.size()>0){
				for(Object[] o:queryList){
					MyFollowView f=new MyFollowView();
					if(o[0]!=null && o[0].toString()!=""){f.setId(Integer.parseInt(o[0].toString()));}
					if(o[1]!=null && o[1].toString()!=""){f.setDate(o[1].toString());}
					if(o[2]!=null && o[2].toString()!=""){f.setUsers_id(Integer.parseInt(o[2].toString()));}
					if(o[3]!=null && o[3].toString()!=""){f.setFollowsid(Integer.parseInt(o[3].toString()));}
					if(o[4]!=null && o[4].toString()!=""){f.setBecknum(Integer.parseInt(o[4].toString()));}
					if(o[5]!=null && o[5].toString()!=""){f.setMutualnum(Integer.parseInt(o[5].toString()));}
					if(o[6]!=null && o[6].toString()!=""){f.setUsername(o[6].toString());}
					if(o[7]!=null && o[7].toString()!=""){f.setHeadpic(o[7].toString());}
					if(o[8]!=null && o[8].toString()!=""){f.setSex(o[8].toString());}
					if(o[9]!=null && o[9].toString()!=""){f.setAge(Integer.parseInt(o[9].toString()));}
					if(o[10]!=null && o[10].toString()!=""){f.setProvince(o[10].toString());}
					if(o[11]!=null && o[11].toString()!=""){f.setCity(o[11].toString());}
					if(o[12]!=null && o[12].toString()!=""){f.setArea(o[12].toString());}
					if(o[13]!=null && o[13].toString()!=""){f.setSign(o[13].toString());}
					if(o[14]!=null && o[14].toString()!=""){f.setFriendstate(o[14].toString());}
					list.add(f);
				}
			}
			map.put("nowpage",page);	//当前页数
			map.put("counts", count);	//总条数
			map.put("pagecount", pagenum);		//每页条数
			map.put("page",countpage);	//总页数
			map.put("followslist", list);
			return map;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
		
	}
	
	/***【相互心动2】***/
	@Override
	public Map<String, Object> selectFollowMutualPage(Map<String, Object> map, int userid) {
		try {
			//查询总数
			String sqlNum="select count(1) from follows where (users_id="+userid+" or followsid="+userid+") and mutualnum=1";
			int count=selectBlogCountTwo(sqlNum);
			/*int memberid=Integer.parseInt(map.get("checkmember").toString());
			if(memberid<=0){		//不是会员
				count=1;
			}*/
			int countpage=(count-1)/Integer.parseInt(map.get("pagenum").toString())+1;
			int page=Integer.parseInt(map.get("nowpage").toString());
			int pagenum=Integer.parseInt(map.get("pagenum").toString());
			/*if(memberid<=0){		//不是会员
				pagenum=1;
			}*/
			if(page<=0){
				page=1;
			}
			if(page>=countpage){
				page=countpage;
			}
			
			
			if(pagenum<=0){
				pagenum=20;
			}
			map.put("nowpage",page);	//当前页数
			map.put("counts", count);	//总条数
			map.put("pagecount", pagenum);		//每页条数
			map.put("page",countpage);	//总页数
			map.put("followslist", null);
			//1.查询出用户编号
			String sql1="select users_id,followsid from follows where (users_id="+userid+" or followsid="+userid+") and mutualnum=1";
			Query query=session.getCurrentSession().createSQLQuery(sql1);
			query.setFirstResult((page-1)*pagenum);		//分页开始
			query.setMaxResults(pagenum);		//每页条数
			@SuppressWarnings("unchecked")
			List<Object[]> obj=query.list();
			Set<String> set=new HashSet<>();
			if(obj!=null && obj.size()>0){
				for(Object[] o:obj){
					set.add(o[0].toString());
					set.add(o[1].toString());
				}
				set.remove(userid);
				//2.获取字符串
				String[] arrayResult = (String[]) set.toArray(new String[set.size()]);
				String strID=Arrays.toString(arrayResult);
				strID=strID.replace("[", "");
				strID=strID.replace("]", "");
				
				//3.查询出部分用户信息【通过视图 userview 】
				String sql3="select * from userview where id in ("+strID+")";
				@SuppressWarnings("unchecked")
				List<Object[]> ouser=this.session.getCurrentSession().createSQLQuery(sql3).list();
				if(ouser!=null && ouser.size()>0){
					List<MyFollowView> list=new ArrayList<>();
					for(Object[] o:ouser){
						MyFollowView v=new MyFollowView();
						v.setUsers_id(userid);
						
						int s=0;
						if(o[0]!=null){v.setFollowsid(Integer.parseInt(o[0].toString()));
							s=o[0]==null?0:Integer.parseInt(o[0].toString());
						}
						
						if(s!=userid){
							String ss="select becknum from follows where (users_id="+userid+" and followsid="+s+") or (users_id="+s+" and followsid="+userid+") LIMIT 1";
							//查询心动值
							Object so=session.getCurrentSession().createSQLQuery(ss).uniqueResult();
							int becknum=(so==null?10:Integer.parseInt(so.toString()));
							if(becknum<=0){
								becknum=10;
							}
							int xindong=(so==null?10:Integer.parseInt(so.toString()));
							
							/****增加亲密度****/
							IntimacyInfo tim=new IntimacyInfo();
							tim=this.intimacyinfoService.selectIntimacyInfoOne(userid, s);
							if(tim!=null){
								if(tim.getIntimacye()<becknum){
									xindong=becknum;
								}else{
									xindong=tim.getIntimacye();
								}
							}
							/****增加亲密度****/
							v.setBecknum(xindong);
							v.setMutualnum(1);
							
							if(o[1]!=null){v.setUsername(o[1].toString());}
							if(o[2]!=null){v.setHeadpic(o[2].toString());}
							if(o[3]!=null){v.setSex(o[3].toString());}
							if(o[4]!=null){v.setAge(Integer.parseInt(o[4].toString()));}
							if(o[5]!=null){v.setProvince(o[5].toString());}
							if(o[6]!=null){v.setCity(o[6].toString());}
							if(o[7]!=null){v.setArea(o[7].toString());}
							if(o[8]!=null){v.setSign(o[8].toString());}
							if(o[9]!=null){v.setFriendstate(o[9].toString());}
							
							list.add(v);
						}
					}
					map.put("followslist", list);
				}
				
			}
			return map;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
		
	}

	
	/***查询心动信息***/
	@Override
	public List<FollowsInfo> selectFollowList(int userid, int followid) {
		try {
			//查询出心动
			String hqls="from FollowsInfo where users_id=? and followsid=?";
			List<FollowsInfo> lis=session.getCurrentSession().createQuery(hqls).setInteger(0, userid).setInteger(1, followid).list();
			if(lis!=null && lis.size()>0){
				return lis;
			}else{
				return null;
			}
		} catch (Exception e) {
			return null;
		}
		
	}
	
	
}
