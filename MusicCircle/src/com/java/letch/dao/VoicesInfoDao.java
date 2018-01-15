package com.java.letch.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.java.letch.daoimp.VoicesInfoDaoImp;
import com.java.letch.model.AgreesInfo;
import com.java.letch.model.CollectionInfo;
import com.java.letch.model.FollowsInfo;
import com.java.letch.model.MemberInfo;
import com.java.letch.model.VoicesInfo;
import com.java.letch.model.view.VoicesUserView;
import com.java.letch.service.FollowsInfoService;
import com.java.letch.tools.DateHelper;

/***
 * 音频、视频、动态信息  数据操作类
 * @author Administrator
 * 2017-09-20
 */
@Repository(value="VoicesInfoDao")
public class VoicesInfoDao implements VoicesInfoDaoImp {

	@Resource(name="sessionFactory")
	private SessionFactory session;		//数据源
	
	@Resource(name="FollowsInfoService")
	private FollowsInfoService followsinfoService;		//用户关注信息
	
	
	/*后台分页查询信息*/
	@Override
	public Map<String, Object> selectVoicesPageList(Map<String, Object> map) {
		try {
			//查询总条数的SQL
			StringBuffer sqlnum=new StringBuffer("select count(*) from voices m where m.work=?");
			//分页查询HQL
			StringBuffer hqlstr=new StringBuffer("from VoicesInfo as b where b.work=?");
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
				pagenum=15;
			}
			//分页查询语句排序
			hqlstr.append(" order by b.id desc");
			Query query=session.getCurrentSession().createQuery(hqlstr.toString()).setInteger(0, Integer.parseInt(map.get("bgstatrus").toString()));
			query.setFirstResult((page-1)*pagenum);		//分页开始
			query.setMaxResults(pagenum);		//每页条数
			@SuppressWarnings("unchecked")
			List<VoicesInfo> list=query.list();
			map.put("nowpage",page);	//当前页数
			map.put("counts", count);	//总条数
			map.put("pagecount", pagenum);		//每页条数
			map.put("page",countpage);	//总页数
			map.put("voicelist", list);	//每页的数据
			return map;
			
		} catch (Exception e) {
			System.out.println(e);
			return map;
		}
	}

	/*查询总数*/
	private int selectBlogCount(String sql,int status) {
		try {
			Object res=session.getCurrentSession().createSQLQuery(sql).setInteger(0, status).uniqueResult();
			return Integer.parseInt(res.toString());
		} catch (Exception e) {
			return 0;
		}
	}

	//查询总数
	@Override
	public int selectVoicesAllNum() {
		try {
			//查询总条数的SQL
			StringBuffer sqlnum=new StringBuffer("select count(*) from voices where work=1");
			Object res=session.getCurrentSession().createSQLQuery(sqlnum.toString()).uniqueResult();
			int count=Integer.parseInt(res.toString());
			return count;
		} catch (Exception e) {
			return 0;
		}
	}
	
	
	/***1.根据动态ID查询评论的详细信息***/
	public VoicesUserView selectVoicesUser(int voicesid){
		try {
			String sql="select * from voicesuserview where work=1 and id="+voicesid;
			VoicesUserView list=(VoicesUserView) session.getCurrentSession().createSQLQuery(sql).addEntity(VoicesUserView.class).uniqueResult();
			/*if(list.size()>0){
				return list.get(0);
			}*/
			/**用户的编号**/
			//int userid=Integer.parseInt(map.get("userid").toString());
			return list;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}
	
	/***2.添加数据信息***/
	public boolean insertVoicesToDB(VoicesInfo voice){
		try {
			session.getCurrentSession().save(voice);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	/***3.根据用户的编号查询动态信息***/
	@Override
	public Map<String, Object> selectVoicesByUserid(int userid) {
		try {
			Map<String, Object> map=new HashMap<String, Object>();
			String sql="select count(*) from voices m where m.work=1 and m.users_id=?";
			int count=selectBlogCount(sql, userid);
			//查询出一条数据
			String sql2="select * from voices where work=1 and users_id=? ORDER BY id DESC LIMIT 1";
			VoicesInfo voices=(VoicesInfo) session.getCurrentSession().createSQLQuery(sql2).addEntity(VoicesInfo.class).setInteger(0, userid).uniqueResult();
			map.put("voices", voices);		//一条数据
			map.put("voicescount", count);	//动态条数
			return map;
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}
	
	/***4.浏览量+1***/
	@Override
	public boolean updateplaycount(int voicesid) {
		try {
			String sql="update voices set playcount=playcount+1 where id=?";
			int num=session.getCurrentSession().createSQLQuery(sql).setInteger(0, voicesid).executeUpdate();
			if(num>0){
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}
	
	/****5.修改动态的状态是否可用****/
	@Override
	public boolean updateVoicesWork(int voicesid, int statrus) {
		try {
			String sql="update  voices set work=? where id=?";
			int num=session.getCurrentSession().createSQLQuery(sql).setInteger(0, statrus).setInteger(1, voicesid).executeUpdate();
			if(num>0){
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}

	/***6.根据动态ID查询评论的详细信息2【更多详细】***/
	@Override
	public VoicesUserView selectVoicesUserTwo(int voicesid, int userid) {
		try {
			String sql="select * from voicesuserview where work=1 and id="+voicesid;
			VoicesUserView list=(VoicesUserView) session.getCurrentSession().createSQLQuery(sql).addEntity(VoicesUserView.class).uniqueResult();
			/*if(list.size()>0){
				return list.get(0);
			}*/
			/**用户的编号**/
			/****【查询是否点赞和是否收藏】****/
			if(list!=null){
				if(userid>0){
					//System.out.println(list.get(x).getId());
					//1.查询出点赞数
					String hql="from AgreesInfo where users_id="+userid+" and voices_id="+voicesid;
					@SuppressWarnings("unchecked")
					List<AgreesInfo> listzan=session.getCurrentSession().createQuery(hql).list();
					if(listzan!=null && listzan.size()>0){
						list.whether=1;
					}
					//2.查询出是否收藏
					String hql2="from CollectionInfo where userid="+userid+" and voicesid="+voicesid;
					@SuppressWarnings("unchecked")
					List<CollectionInfo> listshou=session.getCurrentSession().createQuery(hql2).list();
					System.out.println("收藏："+listshou.size());
					if(listshou!=null && listshou.size()>0){
						list.storeup=1;
					}
					//3.会员记录信息
					String hql3="from MemberInfo where uid=?";
					MemberInfo member=(MemberInfo) session.getCurrentSession().createQuery(hql3).setInteger(0, userid).uniqueResult();
					if(member!=null){
						//判断会员是否过期
						//计算会员天数
          				//1.会员开始时间--->到当前时间的天数
          				int num1=DateHelper.getDaysBetween(member.getBegintime(), DateHelper.getNewData());
          				//2.会员开始时间--->会员到期时间
          				int num2=DateHelper.getDaysBetween(member.getBegintime(), member.getEndestime());
          				if(num1<=num2){   		//1.会员还未到期
          					list.checkmember=1;
          				}else{
          					list.checkmember=0;
          				}
					}else{
						list.checkmember=0;
					}
					//4.判断关注状态
					//判断我是否关注对方
					FollowsInfo follow1=this.followsinfoService.selectFollowsByTwoID(userid,list.getUsers_id());
					if(follow1!=null){
						list.mutualnum=2;		//我关注了对方
						if(follow1.getMutualnum()==1){
							list.mutualnum=1;
						}
					}else{
						list.mutualnum=0;
					}
					//判断对方是否关注我
					FollowsInfo follow2=this.followsinfoService.selectFollowsByTwoID(list.getUsers_id(),userid);
					if(follow2!=null){
						list.mutualnum=3;		//对方关注了我
					}
				}
			}
			/****【查询是否点赞和是否收藏】****/
			return list;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}
	
}
