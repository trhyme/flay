package com.java.letch.dao;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.java.letch.daoimp.UsersInfoDaoImp;
import com.java.letch.model.AgreesInfo;
import com.java.letch.model.CollectionInfo;
import com.java.letch.model.FollowsInfo;
import com.java.letch.model.IphoneConfigure;
import com.java.letch.model.MemberInfo;
import com.java.letch.model.UsersInfo;
import com.java.letch.model.view.AppIndexFollowView;
import com.java.letch.model.view.AppUserFansView;
import com.java.letch.model.view.CollectionVoicesView;
import com.java.letch.model.view.MatchUserView;
import com.java.letch.model.view.UsersMemberView;
import com.java.letch.model.view.VoicesUserView;
import com.java.letch.service.FollowsInfoService;
import com.java.letch.tools.DateHelper;
import com.java.letch.tools.NumberCodeTools;

import javazoom.jl.decoder.Bitstream;
import javazoom.jl.decoder.Header;

/***
 * APP用户的数据操作类
 * @author Administrator
 * 2017-09-18
 */
@Repository(value="UsersInfoDao")
public class UsersInfoDao implements UsersInfoDaoImp {

	@Resource(name="sessionFactory")
	private SessionFactory session;		//数据源
	
	@Resource(name="FollowsInfoService")
	private FollowsInfoService followsinfoService;		//用户关注信息
	
	
	/**8.查询总的用户数据**/
	@Override
	public int selectAllUserCounts() {
		try {
			//查询总条数的SQL
			StringBuffer sqlnum=new StringBuffer("select count(*) from users");
			Object res=session.getCurrentSession().createSQLQuery(sqlnum.toString()).uniqueResult();
			int count=Integer.parseInt(res.toString());
			return count;
		} catch (Exception e) {
			return 0;
		}
	}
	
	
	/**APP用户数据分页查询**/
	@Override
	public Map<String, Object> selectUserListByPage(Map<String, Object> map) {
		try {
			//查询总条数的SQL
			StringBuffer sqlnum=new StringBuffer("select count(*) from usersmemberview m where m.auth=?");
			//分页查询HQL
			StringBuffer hqlstr=new StringBuffer("from UsersMemberView as b where b.auth=?");
			
			/**查询条件**/
			if(map.get("uphone")!=null && !"".equals(map.get("uphone"))){
				sqlnum.append(" and m.phone like '%"+map.get("uphone")+"%' ");
				hqlstr.append(" and b.phone like '%"+map.get("uphone")+"%'");
			}
			if(map.get("uuid")!=null && !"".equals(map.get("uuid"))){
				sqlnum.append(" and m.num like '%"+map.get("uuid")+"%'");
				hqlstr.append(" and b.num like '%"+map.get("uuid")+"%'");
			}
			if(map.get("unames")!=null && !"".equals(map.get("unames"))){
				sqlnum.append(" and m.username like '%"+map.get("unames")+"%'");
				hqlstr.append(" and b.username like '%"+map.get("unames")+"%'");
			}
			//账号状态：auth
			if(map.get("zhuangtai")!=null && !"".equals(map.get("zhuangtai"))){
				if(Integer.parseInt(map.get("zhuangtai").toString())>=0){
					sqlnum.append(" and m.auth="+map.get("zhuangtai"));
					hqlstr.append(" and b.auth="+map.get("zhuangtai"));
				}
			}
			//是否会员
			if(map.get("huiyuan")!=null && !"".equals(map.get("huiyuan"))){
				if(Integer.parseInt(map.get("huiyuan").toString())==0){
					sqlnum.append(" and m.strutus ="+null);
					hqlstr.append(" and b.strutus ="+null);
				}if(Integer.parseInt(map.get("huiyuan").toString())==1){
					sqlnum.append(" and m.strutus="+map.get("huiyuan"));
					hqlstr.append(" and b.strutus="+map.get("huiyuan"));
				}
			}
			//注册时间
			if(map.get("begintimes")!=null && !"".equals(map.get("begintimes"))){
				if(map.get("endstimes")!=null && !"".equals(map.get("endstimes"))){
					sqlnum.append(" and ( m.date >= '"+map.get("begintimes")+" 00:00:00' and m.date<='"+map.get("endstimes")+"')");
					hqlstr.append(" and ( b.date >= '"+map.get("begintimes")+" 00:00:00' and b.date<='"+map.get("endstimes")+"')");
				}else{
					sqlnum.append(" and ( m.date >= '"+map.get("begintimes")+" 00:00:00' and m.date<='"+DateHelper.getNewData()+"')");
					hqlstr.append(" and ( b.date >= '"+map.get("begintimes")+" 00:00:00' and b.date<='"+DateHelper.getNewData()+"')");
				}
				
			}
			/**查询条件**/
			
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
			List<UsersMemberView> list=query.list();
			map.put("nowpage",page);	//当前页数
			map.put("counts", count);	//总条数
			map.put("pagecount", pagenum);		//每页条数
			map.put("page",countpage);	//总页数
			map.put("appuserlist", list);	//每页的数据
			return map;
			
		} catch (Exception e) {
			System.out.println(e);
			return map;
		}
	}
	
	//查询信息的总页数
	private int selectBlogCount(String sql,int status) {
		try {
			Object res=session.getCurrentSession().createSQLQuery(sql).setInteger(0, status).uniqueResult();
			return Integer.parseInt(res.toString());
		} catch (Exception e) {
			return 0;
		}
	}
	//查询信息的总页数
	private int selectBlogCountTwo(String sql,String status) {
		try {
			Object res=session.getCurrentSession().createSQLQuery(sql).setInteger(0, Integer.parseInt(status)).uniqueResult();
			return Integer.parseInt(res.toString());
		} catch (Exception e) {
			return 0;
		}
	}
	
	//查询信息的总页数
	private int selectBlogCountThree(String sql) {
		try {
			Object res=session.getCurrentSession().createSQLQuery(sql).uniqueResult();
			return Integer.parseInt(res.toString());
		} catch (Exception e) {
			return 0;
		}
	}

	/**APP操作**/
	/* 1.根据手机号码查询是否被注册 */
	@Override
	public boolean selectUserByPhone(String phone) {
		try {
			String hql="from UsersInfo as auser where auser.phone=?";
			if(session.getCurrentSession().createQuery(hql).setString(0, phone).list().size()>0){
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}

	/* 2.手机号码注册 */
	@Override
	public boolean insertIntoUserToDB(UsersInfo user) {
		try {
			if(this.selectUserByPhone(user.getPhone())){
				return false;
			}else{
				//先生成一个随机的9位的数字,并查询是否存在
				String num=NumberCodeTools.getRandomNumberCode(9);
				for(int i=0;i<999999999;i++){
					num=NumberCodeTools.getRandomNumberCode(9);
					if(selectUserOneByNum(num)==null){
						user.setNum(num);
						break;
					}else{
						continue;
					}
				}
				session.getCurrentSession().save(user);
				return true;
			}
			
		} catch (Exception e) {
			return false;
		}
	}

	/* 3.根据唯一NUM查询用户 */
	@Override
	public UsersInfo selectUserOneByNum(String num) {
		try {
			String hql="from UsersInfo as ua where ua.num=?";
			return (UsersInfo) session.getCurrentSession().createQuery(hql).setString(0, num).uniqueResult();
		} catch (Exception e) {
			return null;
		}
	}

	/* 4.根据编号查询用户信息 */
	@Override
	public UsersInfo selectUserForPhone(String phone) {
		try {
			String hql="from UsersInfo where phone=?";
			return (UsersInfo) session.getCurrentSession().createQuery(hql).setString(0, phone).uniqueResult();
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}

	/* 5.修改用户信息 */
	@Override
	public boolean updateUserByUser(UsersInfo user) {
		try {
			session.getCurrentSession().update(user);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/***6. 根据APPID判断是否登录 ***/
	@Override
	public UsersInfo selectAppUserByAPPid(String appid) {
		try {
			String hql="from UsersInfo as u where u.apponlyid=?";
			return (UsersInfo) session.getCurrentSession().createQuery(hql).setString(0, appid).uniqueResult();
		} catch (Exception e) {
			return null;
		}
	}

	/**7.修改资料信息**/
	@Override
	public boolean updateAppUserByUser(UsersInfo user) {
		try {
			session.getCurrentSession().update(user);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**9.根据用户的主键ID查询信息**/
	@Override
	public UsersInfo selectAppUserByID(int id) {
		try {
			String hql="from UsersInfo where id=?";
			return (UsersInfo) session.getCurrentSession().createQuery(hql).setInteger(0, id).uniqueResult();
		} catch (Exception e) {
			return null;
		}
	}
	
	
	/*******10.查询出所有用户的电话号码******/
	@Override
	public List<String> selectAllPhone(){
		try {
			String sql="select phone from users where phone<>''";
			@SuppressWarnings("unchecked")
			List<String> list = session.getCurrentSession().createSQLQuery(sql).list();
			return list;
		} catch (Exception e) {
			return null;
		}
	}
	
	
	/****11.根据AppID查询出关注用户的动态信息****/
	@Override
	public Map<String , Object> selectAppIndexFollowVoices(Map<String, Object> map,String appid){
		try {
			//查询总条数的SQL
			StringBuffer sqlnum=new StringBuffer("select count(1) from appindexfollow where fl=?");
			//分页查询HQL
			StringBuffer hqlstr=new StringBuffer("select * from appindexfollow as a where a.fl=?");
			//根据SQL语句已经状态查询
			int count=selectBlogCountTwo(sqlnum.toString(),appid);
			int countpage=(count-1)/Integer.parseInt(map.get("pagenum").toString())+1;
			int page=Integer.parseInt(map.get("nowpage").toString());
			int pagenum=Integer.parseInt(map.get("pagenum").toString());
			if(page<=0){
				page=1;
			}
			if(pagenum<=0){
				pagenum=10;
			}
			if(page>countpage){		//判断是否超过
				page=countpage;
				map=new HashMap<>();
				map.put("nowpage",page);	//当前页数
				map.put("counts", count);	//总条数
				map.put("pagecount", pagenum);		//每页条数
				map.put("page",countpage);	//总页数
				map.put("dynamiclist",null);	//每页的数据
			}else{
				if(page>=countpage){
					page=countpage;
				}
				//分页查询语句排序
				hqlstr.append(" order by a.id desc");
				Query query=session.getCurrentSession().createSQLQuery(hqlstr.toString()).addEntity(AppIndexFollowView.class).setInteger(0, Integer.parseInt(appid));
				query.setFirstResult((page-1)*pagenum);		//分页开始
				query.setMaxResults(pagenum);		//每页条数
				@SuppressWarnings("unchecked")
				List<AppIndexFollowView> list=query.list();
				
				/****【查询是否点赞和是否收藏】****/
				/**用户的编号**/
				int userid=Integer.parseInt(map.get("userid").toString());
				for(int x=0;x<list.size();x++){
					if(userid>0){
						//System.out.println(list.get(x).getId());
						//1.查询出点赞数
						String hql="from AgreesInfo where users_id="+userid+" and voices_id="+list.get(x).getId();
						@SuppressWarnings("unchecked")
						List<AgreesInfo> listzan=session.getCurrentSession().createQuery(hql).list();
						if(listzan!=null && listzan.size()>0){
							list.get(x).whether=1;
						}
						//2.查询出是否收藏
						String hql2="from CollectionInfo where userid="+userid+" and voicesid="+list.get(x).getId();
						@SuppressWarnings("unchecked")
						List<CollectionInfo> listshou=session.getCurrentSession().createQuery(hql2).list();
						System.out.println("收藏："+listshou.size());
						if(listshou!=null && listshou.size()>0){
							list.get(x).storeup=1;
						}
						//3.会员记录信息
						String hql3="from MemberInfo where uid=?";
						MemberInfo member=(MemberInfo) session.getCurrentSession().createQuery(hql3).setInteger(0, list.get(x).getUsers_id()).uniqueResult();
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
				map.put("dynamiclist",list);	//每页的数据
			}
			
			
			return map;
		} catch (Exception e) {
			System.out.println(e);
		}
		return map;
	}

	/****12.根据用户的ID查询出对应的粉丝信息（分页）***/
	@Override
	public Map<String, Object> selectAppUserFansByID(Map<String, Object> map, int userid) {
		try {
			//查询总条数的SQL
			StringBuffer sqlnum=new StringBuffer("select count(1) from appuserfansview where followsid=?");
			//分页查询HQL
			StringBuffer hqlstr=new StringBuffer("select a.* from appuserfansview as a where a.followsid=?");
			//根据SQL语句已经状态查询
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
				pagenum=15;
			}
			//分页查询语句排序
			hqlstr.append(" order by a.id desc");
			Query query=session.getCurrentSession().createSQLQuery(hqlstr.toString()).setInteger(0, userid);
			query.setFirstResult((page-1)*pagenum);		//分页开始
			query.setMaxResults(pagenum);		//每页条数
			@SuppressWarnings("unchecked")
			List<Object[]> list=query.list();
			List<AppUserFansView> list2=new ArrayList<AppUserFansView>();
			//封装数据
			for(Object[] o:list){
				AppUserFansView vi=new AppUserFansView();
				if(o[0]!=null && !"".equals(o[0])){
					vi.setId(Integer.parseInt(o[0].toString()));
				}
				
				vi.setNum(o[1].toString());
				vi.setUsername(o[2].toString());
				vi.setHeadpic(o[3].toString());
				vi.setSign(o[4].toString());
				if(o[5]!=null && !"".equals(o[5])){
					vi.setFollowsid(Integer.parseInt(o[5].toString()));
				}
				System.out.println();
				if(o[6]==null){
					vi.setMutualnum(0);
				}else{
					vi.setMutualnum(Integer.parseInt(o[6].toString()));
				}
				list2.add(vi);
			}
			map.put("nowpage",page);	//当前页数
			map.put("counts", count);	//总条数
			map.put("pagecount", pagenum);		//每页条数
			map.put("page",countpage);	//总页数
			map.put("dynamiclist",list2);	//每页的数据
			return map;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}


	/****13.首页查询【最新】动态****/
	@Override
	public Map<String, Object> selectAppIndexNowVoices(Map<String, Object> map) {
		try {
			//查询总条数的SQL
			StringBuffer sqlnum=new StringBuffer("select count(1) from voicesuserview where `work`=1");
			//分页查询HQL
			StringBuffer hqlstr=new StringBuffer("select * from voicesuserview where `work`=1");
			//根据SQL语句已经状态查询
			int count=selectBlogCountThree(sqlnum.toString());
			int countpage=(count-1)/Integer.parseInt(map.get("pagenum").toString())+1;
			int page=Integer.parseInt(map.get("nowpage").toString());
			int pagenum=Integer.parseInt(map.get("pagenum").toString());
			if(page<=0){
				page=1;
			}
			if(pagenum<=0){
				pagenum=10;
			}
			
			//判断页数是否大于总页数
			if(page>countpage){
				page=countpage;
				map.put("nowpage",page);	//当前页数
				map.put("counts", count);	//总条数
				map.put("pagecount", pagenum);		//每页条数
				map.put("page",countpage);	//总页数
				map.put("dynamiclist",null);	//每页的数据
			}else{
				if(page>=countpage){
					page=countpage;
				}
				
				//分页查询语句排序
				hqlstr.append(" order by id desc");
				Query query=session.getCurrentSession().createSQLQuery(hqlstr.toString()).addEntity(VoicesUserView.class);
				query.setFirstResult((page-1)*pagenum);		//分页开始
				query.setMaxResults(pagenum);		//每页条数
				@SuppressWarnings("unchecked")
				List<VoicesUserView> list=query.list();
				/**用户的编号**/
				int userid=Integer.parseInt(map.get("userid").toString());
				
				/****【查询是否点赞和是否收藏】****/
				for(int x=0;x<list.size();x++){
					if(userid>0){
						//System.out.println(list.get(x).getId());
						//1.查询出点赞数
						String hql="from AgreesInfo where users_id="+userid+" and voices_id="+list.get(x).getId();
						@SuppressWarnings("unchecked")
						List<AgreesInfo> listzan=session.getCurrentSession().createQuery(hql).list();
						if(listzan!=null && listzan.size()>0){
							list.get(x).whether=1;
						}
						//2.查询出是否收藏
						String hql2="from CollectionInfo where userid="+userid+" and voicesid="+list.get(x).getId();
						@SuppressWarnings("unchecked")
						List<CollectionInfo> listshou=session.getCurrentSession().createQuery(hql2).list();
						System.out.println("收藏："+listshou.size());
						if(listshou!=null && listshou.size()>0){
							list.get(x).storeup=1;
						}
						//3.会员记录信息
						String hql3="from MemberInfo where uid=?";
						MemberInfo member=(MemberInfo) session.getCurrentSession().createQuery(hql3).setInteger(0, list.get(x).getUsers_id()).uniqueResult();
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
						//4.判断关注状态
						//判断我是否关注对方
						FollowsInfo follow1=this.followsinfoService.selectFollowsByTwoID(userid,list.get(x).getUsers_id());
						if(follow1!=null){
							list.get(x).mutualnum=2;		//我关注了对方
							if(follow1.getMutualnum()==1){
								list.get(x).mutualnum=1;
							}else{
								//判断对方是否关注我
								FollowsInfo follow2=this.followsinfoService.selectFollowsByTwoID(list.get(x).getUsers_id(),userid);
								if(follow2!=null){
									list.get(x).mutualnum=3;		//对方关注了我
								}
							}
						}else{
							list.get(x).mutualnum=0;
						}
						
					}
				}
				/****【查询是否点赞和是否收藏】****/
				map.put("nowpage",page);	//当前页数
				map.put("counts", count);	//总条数
				map.put("pagecount", pagenum);		//每页条数
				map.put("page",countpage);	//总页数
				map.put("dynamiclist",list);	//每页的数据
			}
			return map;
		} catch (Exception e) {
			System.out.println(e);
			return map;
		}
	}
	
	/***000.首页最热***/
	
	
	/****14.根据APPID修改用户的头像信息****/
	@Override
	public boolean updateAppuserHeadPhoto(String appid, String url) {
		try {
			String sql="update users set headpic=? where apponlyid=?";
			int num=session.getCurrentSession().createSQLQuery(sql).setString(0, url).setString(1, appid).executeUpdate();
			if(num>0){
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}
	
	/****15.根据用户的APPID上传修改用户的资料声音****/
	@Override
	public boolean updateAppuserVoices(String appid, String url) {
		try {
			String sql="update users set voicesurl=?,isstratus=2 where apponlyid=?";
			int num=session.getCurrentSession().createSQLQuery(sql).setString(0, url).setString(1, appid).executeUpdate();
			if(num>0){
				return true;
			}
		} catch (Exception e) {
		}
		return false;
	}
	
	/****16.根据用户的APPID修改用户的昵称和性别信息****/
	@Override
	public boolean updateAppuserNickname(String appid, String nickname,String sex,String fredstate,String province,String city,String area,String birth) {
		try {
			
			String sql="update users set username=?,sex=?,isstratus=3,friendstate=?,province='"+province+"',city='"+city+"',area='"+area+"',birth='"+birth+"' where apponlyid=?";
			int num=session.getCurrentSession().createSQLQuery(sql).setString(0, nickname).setString(1, sex).setString(2, fredstate).setString(3, appid).executeUpdate();
			System.out.println();
			if(num>0){
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return false;
	}

	/***17.根据编号设置用户的个性签名***/
	@Override
	public boolean updateAppUserSign(String appid, String sign) {
		try {
			String sql="update users set sign=? where apponlyid=?";
			int num=session.getCurrentSession().createSQLQuery(sql).setString(0, sign).setString(1, appid).executeUpdate();
			if(num>0){
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}
	
	/***18.修改密码操作***/
	@Override
	public boolean updateAppUserPwds(String phone, String pwds) {
		try {
			String sql="update users set password=? where phone=?";
			int num=session.getCurrentSession().createSQLQuery(sql).setString(0, pwds).setString(1, phone).executeUpdate();
			if(num>0){
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}
	
	/***19.修改用户的基本信息***/
	@Override
	public boolean updateBasicData(String appid, int age, String birth, String height, String workes, String school,
			String province, String city, String area) {
		try {
			String sql="update users set age="+age+",birth='"+birth+"',height='"+height+"'"
					+ ",province='"+province+"',city='"+city+"',area='"+area+"' "
					+ "where apponlyid='"+appid+"'";
			int num=session.getCurrentSession().createSQLQuery(sql).executeUpdate();
			if(num>0){
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}
	
	/***20.修改介绍声音***/
	@Override
	public boolean updateUserVoices(String appid, String voices) {
		try {
			String sql="update users set voicesurl=? where apponlyid=?";
			int num=session.getCurrentSession().createSQLQuery(sql).setString(0, voices).setString(1, appid).executeUpdate();
			if(num>0){
				return true;
			}
		} catch (Exception e) {
		}
		return false;
	}
	
	/***21.修改标签信息***/
	@Override
	public boolean updateLabelByAppID(String appid, String label) {
		try {
			String sql="update users set types_id=? where apponlyid=?";
			int num=session.getCurrentSession().createSQLQuery(sql).setString(0, label).setString(1, appid).executeUpdate();
			if(num>0){
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}
	
	/***22.修改用户的打招呼内容***/
	@Override
	public boolean updateGreetHello(String appid, String greethello) {
		try {
			String sql="update users set greethello=? where apponlyid=?";
			int num=session.getCurrentSession().createSQLQuery(sql).setString(0, greethello).setString(1, appid).executeUpdate();
			if(num>0){
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}
	
	/***23.匹配查询***/
	@Override
	public Map<String, Object> matchingUserList(int userid, String agebegin, String ageend, String sex, String cityarea,
			String height, String ischeck,int nowpage,Map<String, Object> map) {
		try {
			//查询总条数的SQL
			StringBuffer sqlnum=new StringBuffer("select count(*) from matchuserview as a where a.id<>"+userid);
			//查询数据
			StringBuffer sqlbf=new StringBuffer("select * from matchuserview as a where a.id<>"+userid);
			//1.年龄
			if(agebegin!=null && !"".equals(agebegin) && Integer.parseInt(agebegin)>0){
				sqlbf.append(" and a.age>="+agebegin);
				sqlnum.append(" and a.age>="+agebegin);
			}
			if(ageend!=null && !"".equals(ageend) && Integer.parseInt(ageend)>0){
				sqlbf.append(" and a.age<="+ageend);
				sqlnum.append(" and a.age<="+ageend);
			}
			//2.性别
			if(sex!=null && !"".equals(sex)){
				sqlbf.append(" and a.sex='"+sex+"'");
				sqlnum.append(" and a.sex='"+sex+"'");
			}
			//3.城市
			if(cityarea!=null && !"".equals(cityarea)){
				sqlbf.append(" and (province like '%"+cityarea+"%' or city like '%"+cityarea+"%' or area like '%"+cityarea+"%'");
				sqlnum.append(" and (province like '%"+cityarea+"%' or city like '%"+cityarea+"%' or area like '%"+cityarea+"%'");
			}
			//4.是否认证
			if(ischeck!=null && !"".equals(ischeck) && Integer.parseInt(ischeck)>0){
				sqlbf.append(" and ischeck=3");
				sqlnum.append(" and ischeck=3");
			}
			
			int count=selectBlogCountThree(sqlnum.toString());
			int countpage=(count-1)/10+1;
			int page=nowpage;
			int pagenum=10;
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
			sqlbf.append(" order by a.id desc");
			Query query=session.getCurrentSession().createSQLQuery(sqlbf.toString());
			query.setFirstResult((page-1)*pagenum);		//分页开始
			System.out.println((page-1)*pagenum);
			query.setMaxResults(pagenum);		//每页条数
			map.put("nowpage",page);	//当前页数
			map.put("counts", count);	//总条数
			map.put("pagecount", pagenum);		//每页条数
			map.put("page",countpage);	//总页数
			
			List<Object[]> list=query.list();
			List<MatchUserView> li=new ArrayList<>();
			for(Object[] o:list){
				MatchUserView m=new MatchUserView();
				m.setId(Integer.parseInt(o[0].toString()));
				m.setNum(o[1].toString());
				
				if(o[2]==null){
					m.setPhone("");
				}else{
					m.setPhone(o[2].toString());
				}
				
				if(o[3]==null){
					//m.setUsername("");
				}else{
					m.setUsername(o[3].toString());
				}
				
				if(o[4]==null){
					m.setHeadpic("");
				}else{
					m.setHeadpic(o[4].toString());
				}
				
				if(o[5]==null){
					m.setFriendstate("");
				}else{
					m.setFriendstate(o[5].toString());
				}
				
				if(o[6]==null){
					m.setSex("");
				}else{
					m.setSex(o[6].toString());
				}
				
				if(o[7]==null){
					m.setAge(0);
				}else{
					m.setAge(Integer.parseInt(o[7].toString()));
				}
				
				if(o[8]==null){
					m.setSign("");
				}else{
					m.setSign(o[8].toString());
				}
				
				if(o[9]==null){
					m.setVoicesurl("");
				}else{
					m.setVoicesurl(o[9].toString());
				}
				
				if(o[10]==null){
					m.setProvince("");
				}else{
					m.setProvince(o[10].toString());
				}
				
				if(o[11]==null){
					m.setCity("");
				}else{
					m.setCity(o[11].toString());
				}
				
				if(o[12]==null){
					m.setArea("");
				}else{
					m.setArea(o[12].toString());
				}
				
				if(o[13]==null){
					m.setHeight("");
				}else{
					m.setHeight(o[13].toString());
				}
				
				
				if(o[14]==null){
					m.setIscheck(1);
				}else{
					m.setIscheck(Integer.parseInt(o[14].toString()));	
				}
				
				if(o[15]==null){
					m.setVipid(0);
				}else{
					m.setVipid(Integer.parseInt(o[15].toString()));
				}
				
				if(o[16]==null){
					m.setMegname("");
				}else{
					m.setMegname(o[16].toString());
				}
				
				
				
				if(o[17]==null){
					m.setStrutus(0);
				}else{
					m.setStrutus(Integer.parseInt(o[17].toString()));
				}
				
				//1.查询出是否已经心动
				String hqls="from FollowsInfo where users_id=? and followsid=?";
				List<FollowsInfo> lis=session.getCurrentSession().createQuery(hqls).setInteger(0, userid).setInteger(1, Integer.parseInt(o[0].toString())).list();
				if(lis!=null && lis.size()>0){
					m.checkbeckoning=1;
				}
				//2.获取长度
				URLConnection con = null;
				try {
					if(o[9]==null){
						m.setVoicesurl("");
					}else{
						URL urlfile = new URL(o[9].toString());
					    try {
					        con = urlfile.openConnection();
					    } catch (IOException e) {
					    	System.out.println(e);
					    	e.printStackTrace();
					    }
					    int b = con.getContentLength();//
					    BufferedInputStream bis = new BufferedInputStream(con.getInputStream());
					    Bitstream bt = new Bitstream(bis);
					    Header h = bt.readFrame();
					    int time = (int) h.total_ms(b);
					    m.length=(time/1000+1);
					}
				} catch (Exception e) {
					m.length=0;
					System.out.println("voices_Length："+e);
				}
				
				li.add(m);
			}
			
			map.put("userlist", li);	//每页的数据
			return map;
			
			/*//随机查询
			sqlbf.append(" ORDER BY RAND() LIMIT 10");
			@SuppressWarnings("unchecked")
			List<Object[]> list=this.session.getCurrentSession().createSQLQuery(sqlbf.toString()).list();
			List<MatchUserView> li=new ArrayList<>();
			for(Object[] o:list){
				MatchUserView m=new MatchUserView();
				m.setId(Integer.parseInt(o[0].toString()));
				m.setNum(o[1].toString());
				
				if(o[2]==null){
					m.setPhone("");
				}else{
					m.setPhone(o[2].toString());
				}
				
				if(o[3]==null){
					//m.setUsername("");
				}else{
					m.setUsername(o[3].toString());
				}
				
				if(o[4]==null){
					m.setHeadpic("");
				}else{
					m.setHeadpic(o[4].toString());
				}
				
				if(o[5]==null){
					m.setFriendstate("");
				}else{
					m.setFriendstate(o[5].toString());
				}
				
				if(o[6]==null){
					m.setSex("");
				}else{
					m.setSex(o[6].toString());
				}
				
				
				
				if(o[7]==null){
					m.setAge(0);
				}else{
					m.setAge(Integer.parseInt(o[7].toString()));
				}
				
				if(o[8]==null){
					m.setSign("");
				}else{
					m.setSign(o[8].toString());
				}
				
				if(o[9]==null){
					m.setVoicesurl("");
				}else{
					m.setVoicesurl(o[9].toString());
				}
				
				if(o[10]==null){
					m.setProvince("");
				}else{
					m.setProvince(o[10].toString());
				}
				
				if(o[11]==null){
					m.setCity("");
				}else{
					m.setCity(o[11].toString());
				}
				
				if(o[12]==null){
					m.setArea("");
				}else{
					m.setArea(o[12].toString());
				}
				
				if(o[13]==null){
					m.setHeight("");
				}else{
					m.setHeight(o[13].toString());
				}
				
				
				if(o[14]==null){
					m.setIscheck(1);
				}else{
					m.setIscheck(Integer.parseInt(o[14].toString()));	
				}
				
				if(o[15]==null){
					m.setVipid(0);
				}else{
					m.setVipid(Integer.parseInt(o[15].toString()));
				}
				
				if(o[16]==null){
					m.setMegname("");
				}else{
					m.setMegname(o[16].toString());
				}
				
				
				
				if(o[17]==null){
					m.setStrutus(0);
				}else{
					m.setStrutus(Integer.parseInt(o[17].toString()));
				}
				
				//1.查询出是否已经心动
				String hqls="from FollowsInfo where users_id=? and followsid=?";
				List<FollowsInfo> lis=session.getCurrentSession().createQuery(hqls).setInteger(0, userid).setInteger(1, Integer.parseInt(o[1].toString())).list();
				if(lis!=null && lis.size()>0){
					m.checkbeckoning=1;
				}
				//2.获取长度
				URLConnection con = null;
				try {
					if(o[9]==null){
						m.setVoicesurl("");
					}else{
						URL urlfile = new URL(o[9].toString());
					    try {
					        con = urlfile.openConnection();
					    } catch (IOException e) {
					    	System.out.println(e);
					    	e.printStackTrace();
					    }
					    int b = con.getContentLength();//
					    BufferedInputStream bis = new BufferedInputStream(con.getInputStream());
					    Bitstream bt = new Bitstream(bis);
					    Header h = bt.readFrame();
					    int time = (int) h.total_ms(b);
					    m.length=(time/1000+1);
					}
				} catch (Exception e) {
					m.length=0;
					System.out.println("voices_Length："+e);
				}
				
				li.add(m);
			}
			return li;*/
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}

	

	/***24.首页的【最热消息】***/
	@Override
	public Map<String, Object> appIndexHot(Map<String, Object> map) {
		try {
			//查询总条数的SQL
			StringBuffer sqlnum=new StringBuffer("select count(*) from voicesuserview where work=1");
			//分页查询HQL
			StringBuffer hqlstr=new StringBuffer("select * from voicesuserview where work=1 ORDER BY fabulous,commentnum DESC");
			//根据SQL语句已经状态查询
			int count=selectBlogCountThree(sqlnum.toString());
			int countpage=(count-1)/Integer.parseInt(map.get("pagenum").toString())+1;
			int page=Integer.parseInt(map.get("nowpage").toString());
			int pagenum=Integer.parseInt(map.get("pagenum").toString());
			if(page<=0){
				page=1;
			}
			if(pagenum<=0){
				pagenum=10;
			}
			if(page==1){
				int x=1+(int)(Math.random()*countpage);
				System.out.println("随机页数："+x);
				
				page=x;
			}
			
			if(page>countpage){
				page=countpage;
				map.put("nowpage",page);	//当前页数
				//map.put("counts", count);	//总条数
				map.put("pagecount", pagenum);		//每页条数
				map.put("page",countpage);	//总页数
				map.put("dynamiclist", null);	//每页的数据
			}else{
				if(page>=countpage){
					page=countpage;
				}
				Query query=session.getCurrentSession().createSQLQuery(hqlstr.toString()).addEntity(VoicesUserView.class);
				query.setFirstResult((page-1)*pagenum);		//分页开始
				query.setMaxResults(pagenum);		//每页条数
				List<VoicesUserView> list=query.list();
				
				
				/****【查询是否点赞和是否收藏】****/
				/**用户的编号**/
				int userid=Integer.parseInt(map.get("userid").toString());
				for(int x=0;x<list.size();x++){
					if(userid>0){
						//System.out.println(list.get(x).getId());
						//1.查询出点赞数
						String hql="from AgreesInfo where users_id="+userid+" and voices_id="+list.get(x).getId();
						@SuppressWarnings("unchecked")
						List<AgreesInfo> listzan=session.getCurrentSession().createQuery(hql).list();
						if(listzan!=null && listzan.size()>0){
							list.get(x).whether=1;
						}
						//2.查询出是否收藏
						String hql2="from CollectionInfo where userid="+userid+" and voicesid="+list.get(x).getId();
						@SuppressWarnings("unchecked")
						List<CollectionInfo> listshou=session.getCurrentSession().createQuery(hql2).list();
						if(listshou!=null && listshou.size()>0){
							list.get(x).storeup=1;
						}
						//3.会员记录信息
						String hql3="from MemberInfo where uid=?";
						MemberInfo member=(MemberInfo) session.getCurrentSession().createQuery(hql3).setInteger(0, list.get(x).getUsers_id()).uniqueResult();
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
						//4.判断关注状态
						//判断我是否关注对方
						FollowsInfo follow1=this.followsinfoService.selectFollowsByTwoID(userid,list.get(x).getUsers_id());
						if(follow1!=null){
							list.get(x).mutualnum=2;		//我关注了对方
							if(follow1.getMutualnum()==1){
								list.get(x).mutualnum=1;
							}
						}else{
							list.get(x).mutualnum=0;
						}
						//判断对方是否关注我
						FollowsInfo follow2=this.followsinfoService.selectFollowsByTwoID(list.get(x).getUsers_id(),userid);
						if(follow2!=null){
							list.get(x).mutualnum=3;		//对方关注了我
						}
					}
				}
				/****【查询是否点赞和是否收藏】****/
				
				
				map.put("nowpage",page);	//当前页数
				//map.put("counts", count);	//总条数
				map.put("pagecount", pagenum);		//每页条数
				map.put("page",countpage);	//总页数
				map.put("dynamiclist", list);	//每页的数据
			}
			
 		} catch (Exception e) {
			System.out.println(e);
		}
		return map;
	}
	/***24.首页的【最热消息】***/
	
	/***25.我的发布动态查看***/
	@Override
	public Map<String, Object> selectVoicesMyself(Map<String, Object> map,int userid) {
		try {
			//查询总条数的SQL
			StringBuffer sqlnum=new StringBuffer("select count(*) from voicesuserview where users_id="+userid+" and work=1");
			//分页查询HQL
			StringBuffer hqlstr=new StringBuffer("select * from voicesuserview where work=1 and users_id="+userid+" ORDER BY id DESC");
			//根据SQL语句已经状态查询
			int count=selectBlogCountThree(sqlnum.toString());
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
			Query query=session.getCurrentSession().createSQLQuery(hqlstr.toString()).addEntity(VoicesUserView.class);
			query.setFirstResult((page-1)*pagenum);		//分页开始
			query.setMaxResults(pagenum);		//每页条数
			List<VoicesUserView> list=query.list();
			
			/****【查询是否点赞和是否收藏】****/
			for(int x=0;x<list.size();x++){
				if(userid>0){
					//System.out.println(list.get(x).getId());
					//1.查询出点赞数
					String hql="from AgreesInfo where users_id="+userid+" and voices_id="+list.get(x).getId();
					@SuppressWarnings("unchecked")
					List<AgreesInfo> listzan=session.getCurrentSession().createQuery(hql).list();
					if(listzan!=null && listzan.size()>0){
						list.get(x).whether=1;
					}
					//2.查询出是否收藏
					String hql2="from CollectionInfo where userid="+userid+" and voicesid="+list.get(x).getId();
					@SuppressWarnings("unchecked")
					List<CollectionInfo> listshou=session.getCurrentSession().createQuery(hql2).list();
					if(listshou!=null && listshou.size()>0){
						list.get(x).storeup=1;
					}
				}
			}
			/****【查询是否点赞和是否收藏】****/
			
			map.put("nowpage",page);	//当前页数
			//map.put("counts", count);	//总条数
			map.put("pagecount", pagenum);		//每页条数
			map.put("page",countpage);	//总页数
			map.put("voiceslist", list);	//每页的数据
		} catch (Exception e) {
			System.out.println(e);
		}
		return map;
	}
	/***25.我的发布动态查看***/
	
	/***25_1.查看别人的动态信息***/
	@Override
	public Map<String, Object> selectVoicesYour(Map<String, Object> map, int userid, int seeuserid) {
		try {
			//查询总条数的SQL
			StringBuffer sqlnum=new StringBuffer("select count(*) from voicesuserview where users_id="+userid+" and work=1");
			//分页查询HQL
			StringBuffer hqlstr=new StringBuffer("select * from voicesuserview where work=1 and users_id="+userid+" ORDER BY id DESC");
			//根据SQL语句已经状态查询
			int count=selectBlogCountThree(sqlnum.toString());
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
			Query query=session.getCurrentSession().createSQLQuery(hqlstr.toString()).addEntity(VoicesUserView.class);
			query.setFirstResult((page-1)*pagenum);		//分页开始
			query.setMaxResults(pagenum);		//每页条数
			List<VoicesUserView> list=query.list();
			
			/****【查询是否点赞和是否收藏】****/
			for(int x=0;x<list.size();x++){
				if(userid>0){
					//System.out.println(list.get(x).getId());
					//1.查询出点赞数
					String hql="from AgreesInfo where users_id="+seeuserid+" and voices_id="+list.get(x).getId();
					@SuppressWarnings("unchecked")
					List<AgreesInfo> listzan=session.getCurrentSession().createQuery(hql).list();
					if(listzan!=null && listzan.size()>0){
						list.get(x).whether=1;
					}
					//2.查询出是否收藏
					String hql2="from CollectionInfo where userid="+seeuserid+" and voicesid="+list.get(x).getId();
					@SuppressWarnings("unchecked")
					List<CollectionInfo> listshou=session.getCurrentSession().createQuery(hql2).list();
					if(listshou!=null && listshou.size()>0){
						list.get(x).storeup=1;
					}
				}
			}
			/****【查询是否点赞和是否收藏】****/
			
			map.put("nowpage",page);	//当前页数
			//map.put("counts", count);	//总条数
			map.put("pagecount", pagenum);		//每页条数
			map.put("page",countpage);	//总页数
			map.put("voiceslist", list);	//每页的数据
		} catch (Exception e) {
			System.out.println(e);
		}
		return map;
	}
	/***25_1.查看别人的动态信息***/
	

	/***26.修改动态的是否删除动态***/
	@Override
	public boolean deleteVoicesByID(int voicesid, int userid) {
		try {
			String sql="update voices set `work`=0 where id=? and users_id=?";
			int num=session.getCurrentSession().createSQLQuery(sql).setInteger(0, voicesid).setInteger(1, userid).executeUpdate();
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
	/***26.修改动态的是否删除动态***/
	
	/***27.修改用户的基本信息***/
	@Override
	public boolean updateAppOneUserByUser(UsersInfo user) {
		try {
			String sql="update users set phone=?,username=?,birth=?,sign=?,height=?,sex=?,workes=?,types_id=? where id=?";
			int num=session.getCurrentSession().createSQLQuery(sql).setString(0, user.getPhone()).
			setString(1, user.getUsername()).setString(2, user.getBirth()).setString(3, user.getSign()).setString(4, user.getHeight()).
			setString(5, user.getSex()).setString(6, user.getWorkes()).setString(7, user.getTypes_id()).
			setInteger(8, user.getId()).executeUpdate();
			if(num<0){
				return false;
			}else{
				return true;
			}
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
	}
	/***27.修改用户的基本信息***/
	
	/***28.后台删除用户的声音信息***/
	@Override
	public boolean updateDeleteUserVoices(int id) {
		try {
			String sql="update users set voicesurl=null where id=?";
			int num=session.getCurrentSession().createSQLQuery(sql).setInteger(0, id).executeUpdate();
			if(num>0){
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}
	/***28.后台删除用户的声音信息***/
	
	/****29.第三方登录：微信、微博、QQ****/
	@Override
	public UsersInfo selectQQWXWBbyOpenID(String openid) {
		try {
			String hql="from UsersInfo where openid=?";
			return (UsersInfo) session.getCurrentSession().createQuery(hql).setString(0, openid).uniqueResult();
		} catch (Exception e) {
			return null;
		}
	}
	/****29.第三方登录：微信、微博、QQ****/
	
	
	/***30.修改用户的职业信息***/
	@Override
	public boolean updateUserWorks(int id, String works) {
		try {
			String sql="update users set workes=? where id=?";
			int num=session.getCurrentSession().createSQLQuery(sql).setString(0, works).setInteger(1, id).executeUpdate();
			if(num>0){
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}
	/***30.修改用户的职业信息***/
	
	/***31.修改用户的院校信息***/
	@Override
	public boolean updateUserSchool(int id, String school) {
		try {
			String sql="update users set school=? where id=?";
			int num=session.getCurrentSession().createSQLQuery(sql).setString(0, school).setInteger(1, id).executeUpdate();
			if(num>0){
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}
	/***31.修改用户的院校信息***/
	
	
	
	/****32.查看其它用户的动态消息****/
	public Map<String, Object> selectAppIndexSeeUserVoices(Map<String, Object> map,int seeuserid) {
		try {
			//查询总条数的SQL
			StringBuffer sqlnum=new StringBuffer("select count(1) from voicesuserview where `work`=1 and users_id=?");
			//分页查询HQL
			StringBuffer hqlstr=new StringBuffer("select * from voicesuserview where `work`=1 and users_id=?");
			//根据SQL语句已经状态查询
			int count=selectBlogCountTwo(sqlnum.toString(),seeuserid+"");
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
			hqlstr.append(" order by id desc");
			Query query=session.getCurrentSession().createSQLQuery(hqlstr.toString()).addEntity(VoicesUserView.class).setInteger(0, seeuserid);
			query.setFirstResult((page-1)*pagenum);		//分页开始
			query.setMaxResults(pagenum);		//每页条数
			@SuppressWarnings("unchecked")
			List<VoicesUserView> list=query.list();
			/**用户的编号**/
			int userid=Integer.parseInt(map.get("userid").toString());
			
			/****【查询是否点赞和是否收藏】****/
			for(int x=0;x<list.size();x++){
				if(userid>0){
					//System.out.println(list.get(x).getId());
					//1.查询出点赞数
					String hql="from AgreesInfo where users_id="+userid+" and voices_id="+list.get(x).getId();
					@SuppressWarnings("unchecked")
					List<AgreesInfo> listzan=session.getCurrentSession().createQuery(hql).list();
					if(listzan!=null && listzan.size()>0){
						list.get(x).whether=1;
					}
					//2.查询出是否收藏
					String hql2="from CollectionInfo where userid="+userid+" and voicesid="+list.get(x).getId();
					@SuppressWarnings("unchecked")
					List<CollectionInfo> listshou=session.getCurrentSession().createQuery(hql).list();
					if(listshou!=null && listshou.size()>0){
						list.get(x).storeup=1;
					}
				}
			}
			/****【查询是否点赞和是否收藏】****/
			map.put("nowpage",page);	//当前页数
			map.put("counts", count);	//总条数
			map.put("pagecount", pagenum);		//每页条数
			map.put("page",countpage);	//总页数
			map.put("dynamiclist",list);	//每页的数据
			return map;
		} catch (Exception e) {
			System.out.println(e);
			return map;
		}
	}
	/****32.首页其它用户的动态消息****/


	/****33.获取iPhone的设置****/
	@Override
	public List<IphoneConfigure> selectIphoneConfigure() {
		return session.getCurrentSession().createQuery("from IphoneConfigure").list();
	}
	
	
	/****34.设置iPhone用户App的支付方式****/
	@Override
	public boolean updateIphoneConfigure(int strat) {
		try {
			String sql="update iphoneconfigure set iphone=?";
			int num=session.getCurrentSession().createSQLQuery(sql).setInteger(0, strat).executeUpdate();
			if(num>0){
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			return false;
		}
		
	}
	
	/****35.随机获取一个用户信息****/
	@Override
	public UsersInfo selectRandomUserOne(int myselectuserid, String sex) {
		try {
			String sql="select * from users where id <>? and sex=? ORDER BY RAND() LIMIT 1";
			return (UsersInfo) session.getCurrentSession().createSQLQuery(sql).addEntity(UsersInfo.class).setInteger(0, myselectuserid).setString(1, sex).uniqueResult();
		} catch (Exception e) {
			return null;
		}
		
	}
	
	/*****36.修改用户的交友状态******/
	@Override
	public boolean updateFriendsState(int userid, String friends) {
		try {
			String sql="update users set friendstate=? where id=?";
			int num=session.getCurrentSession().createSQLQuery(sql).setString(0, friends).setInteger(1, userid).executeUpdate();
			if(num>0){
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			return false;
		}
		
	}
	
	
	/*****37.用户的点击【收藏】信息****/
	@Override
	public Map<String, Object> selectCollectionVoicesPage(Map<String, Object> map,int userid) {
		try {
			//查询总条数的SQL
			StringBuffer sqlnum=new StringBuffer("select count(*) from collectionvoicesview where work=1 and fl=?");
			//分页查询HQL
			StringBuffer hqlstr=new StringBuffer("select * from collectionvoicesview where work=1 and fl="+userid);
			//根据SQL语句已经状态查询
			int count=selectBlogCount(sqlnum.toString(),userid);
			int countpage=(count-1)/Integer.parseInt(map.get("pagenum").toString())+1;
			int page=Integer.parseInt(map.get("nowpage").toString());
			int pagenum=Integer.parseInt(map.get("pagenum").toString());
			if(page<=0){
				page=1;
			}
			if(pagenum<=0){
				pagenum=10;
			}
			
			if(page>countpage){
				page=countpage;
				map.put("nowpage",page);	//当前页数
				//map.put("counts", count);	//总条数
				map.put("pagecount", pagenum);		//每页条数
				map.put("page",countpage);	//总页数
				map.put("dynamiclist", null);	//每页的数据
			}else{
				if(page>=countpage){
					page=countpage;
				}
				
				Query query=session.getCurrentSession().createSQLQuery(hqlstr.toString()).addEntity(CollectionVoicesView.class);
				query.setFirstResult((page-1)*pagenum);		//分页开始
				query.setMaxResults(pagenum);		//每页条数
				List<CollectionVoicesView> list=query.list();
				
				/****【查询是否点赞和是否收藏】****/
				for(int x=0;x<list.size();x++){
					if(userid>0){
						//System.out.println(list.get(x).getId());
						//1.查询出点赞数
						String hql="from AgreesInfo where users_id="+userid+" and voices_id="+list.get(x).getId();
						@SuppressWarnings("unchecked")
						List<AgreesInfo> listzan=session.getCurrentSession().createQuery(hql).list();
						if(listzan!=null && listzan.size()>0){
							list.get(x).whether=1;
						}
						//2.查询出是否收藏
						list.get(x).storeup=1;
						//3.会员记录信息
						String hql3="from MemberInfo where uid=?";
						MemberInfo member=(MemberInfo) session.getCurrentSession().createQuery(hql3).setInteger(0, list.get(x).getUsers_id()).uniqueResult();
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
						//4.判断关注状态
						//判断我是否关注对方
						FollowsInfo follow1=this.followsinfoService.selectFollowsByTwoID(userid,list.get(x).getUsers_id());
						if(follow1!=null){
							list.get(x).mutualnum=2;		//我关注了对方
							if(follow1.getMutualnum()==1){
								list.get(x).mutualnum=1;
							}
						}else{
							list.get(x).mutualnum=0;
						}
						//判断对方是否关注我
						FollowsInfo follow2=this.followsinfoService.selectFollowsByTwoID(list.get(x).getUsers_id(),userid);
						if(follow2!=null){
							list.get(x).mutualnum=3;		//对方关注了我
						}
					}
				}
				/****【查询是否点赞和是否收藏】****/
				
				map.put("nowpage",page);	//当前页数
				//map.put("counts", count);	//总条数
				map.put("pagecount", pagenum);		//每页条数
				map.put("page",countpage);	//总页数
				map.put("dynamiclist", list);	//每页的数据
			}
			
			
			
		} catch (Exception e) {
			return map;
		}
		return map;
	}


	/*****38.修改用户的昵称*****/
	@Override
	public boolean updateUserNameByID(int userid, String username) {
		try {
			String sql="update users set username=? where id=?";
			int num=session.getCurrentSession().createSQLQuery(sql).setString(0, username).setInteger(1, userid).executeUpdate();
			if(num>0){
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}
	
	/*****39.根据昵称查询用户信息******/
	@Override
	public List<UsersInfo> selectUserByUserName(String username) {
		try {
			String hql="from UsersInfo where username=?";
			List<UsersInfo> list=session.getCurrentSession().createQuery(hql).setString(0, username).list();
			if(list!=null && list.size()>0){
				return list;
			}else{
				return null;
			}
		} catch (Exception e) {
			return null;
		}
	}
	
	/******40.修改手机唯一标识*****/
	@Override
	public boolean updatePhoneOnlyID(String phoneid, int userid) {
		try {
			String sql="update users set android_ios=? where id=?";
			int num=session.getCurrentSession().createSQLQuery(sql).setString(0, phoneid).setInteger(1, userid).executeUpdate();
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
