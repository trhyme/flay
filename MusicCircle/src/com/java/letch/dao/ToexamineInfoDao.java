package com.java.letch.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.java.letch.daoimp.ToexamineInfoDaoImp;
import com.java.letch.model.MenuInfo;
import com.java.letch.model.ToexamineInfo;
import com.java.letch.tools.DateHelper;

/***
 * 用户信息认证信息数据操作类
 * @author Administrator
 * 2017-09-29
 */
@Repository(value="ToexamineInfoDao")
public class ToexamineInfoDao implements ToexamineInfoDaoImp {

	@Resource(name="sessionFactory")
	private SessionFactory session;		//数据源
	
	//分页查询
	@Override
	public Map<String, Object> selectToexamineList(Map<String, Object> map) {
		try {
			//查询总条数的SQL
			StringBuffer sqlnum=new StringBuffer("select count(*) from toexamineinfo where 1=1");
			//分页查询HQL
			StringBuffer hqlstr=new StringBuffer("from ToexamineInfo as b where 1=1");
			
			/**查询条件**/
			if(map.get("uphone")!=null && !"".equals(map.get("uphone"))){
				sqlnum.append(" and uphone like '%"+map.get("uphone")+"%' ");
				hqlstr.append(" and b.uphone like '%"+map.get("uphone")+"%'");
			}
			if(map.get("uuid")!=null && !"".equals(map.get("uuid"))){
				sqlnum.append(" and uuid like '%"+map.get("uuid")+"%'");
				hqlstr.append(" and b.uuid like '%"+map.get("uuid")+"%'");
			}
			if(map.get("unames")!=null && !"".equals(map.get("unames"))){
				sqlnum.append(" and unames like '%"+map.get("unames")+"%'");
				hqlstr.append(" and b.unames like '%"+map.get("unames")+"%'");
			}
			if(map.get("zhuangtai")!=null && !"".equals(map.get("zhuangtai"))){
				if(Integer.parseInt(map.get("zhuangtai").toString())>=0){
					sqlnum.append(" and stratus="+map.get("zhuangtai"));
					hqlstr.append(" and b.stratus="+map.get("zhuangtai"));
				}
			}
			/**查询条件**/
			
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
			map.put("toexaminelist", list);	//每页的数据
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

	
	/*修改认证状态的方法*/
	@Override
	public boolean updateToexamineStratus(int id, int stratus) {
		try {
			String sql="update toexamineinfo set stratus=?,updtime=? where id=?";
			int num=session.getCurrentSession().createSQLQuery(sql).setInteger(0, stratus).setString(1,DateHelper.getNewData()).setInteger(2, id).executeUpdate();
			if(num>0){
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}
	
	/*添加认证信息的方法*/
	@Override
	public boolean insertToexamOne(ToexamineInfo toexa) {
		try {
			toexa.setId(selectCheckToexamine(toexa.getUid()));
			session.getCurrentSession().saveOrUpdate(toexa);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	//判断信息
	private int selectCheckToexamine(int uid){
		try {
			String sql="select id from toexamineinfo where uid=?";
			Object res=session.getCurrentSession().createSQLQuery(sql).setInteger(0, uid).uniqueResult();
			return Integer.parseInt(res.toString());
		} catch (Exception e) {
			return 0;
		}
		
	}
	
	
	/*添加修改认证的消息*/
	@Override
	public boolean updatePiclistBy(String userid, String photo) {
		try {
			String sql="update toexamineinfo set piclist=? where uid=?";
			int num=session.getCurrentSession().createSQLQuery(sql).setString(0, photo).setString(1, userid).executeUpdate();
			if(num>0){
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}

	/***1.根据用户编号查询认证信息***/
	@Override
	public ToexamineInfo selectToexamByUserid(int userid) {
		try {
			String hql="select * from toexamineinfo where stratus=2 and delstra=1 and uid=?";
			return (ToexamineInfo) session.getCurrentSession().createSQLQuery(hql).addEntity(ToexamineInfo.class).setInteger(0, userid).uniqueResult();
			
		} catch (Exception e) {
			return null;
		}
		
	}
	
	/***2.根据用户编号查询认证信息***/
	@Override
	public ToexamineInfo selectToexamByUseridAll(int userid) {
		try {
			String hql="select * from toexamineinfo where uid=?";
			return (ToexamineInfo) session.getCurrentSession().createSQLQuery(hql).addEntity(ToexamineInfo.class).setInteger(0, userid).uniqueResult();
		} catch (Exception e) {
			return null;
		}
	}
	
}
