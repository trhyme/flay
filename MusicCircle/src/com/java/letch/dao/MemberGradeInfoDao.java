package com.java.letch.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.annotation.Resources;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.java.letch.daoimp.MemberGradeInfoDaoImp;
import com.java.letch.model.MemberGradeInfo;
import com.java.letch.model.MenuInfo;

/***
 * 会员类别信息数据操作类
 * @author Administrator
 * 2017-09-28
 */
@Repository(value="MemberGradeInfoDao")
public class MemberGradeInfoDao implements MemberGradeInfoDaoImp {

	@Resource(name="sessionFactory")
	private SessionFactory session; //数据源
	
	/*分页查询信息*/
	@Override
	public Map<String, Object> selectMemberGradePage(Map<String, Object> map) {
		try {
			//查询总条数的SQL
			StringBuffer sqlnum=new StringBuffer("select count(*) from membergradeinfo");
			//分页查询HQL
			StringBuffer hqlstr=new StringBuffer("from MemberGradeInfo as b");
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
			map.put("membergradelist", list);	//每页的数据
			return map;
		} catch (Exception e) {
			
		}
		return map;
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

	//添加数据信息
	@Override
	public boolean insertMemberGradeToDB(MemberGradeInfo membergrd) {
		try {
			session.getCurrentSession().save(membergrd);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/*修改会员等级信息的状态*/
	@Override
	public boolean updateMemberGradeStratus(int id, int stra) {
		try {
			String sql="update membergradeinfo set stratu=? where id=?";
			int num=session.getCurrentSession().createSQLQuery(sql).setInteger(0, stra).setInteger(1, id).executeUpdate();
			if(num>0){
				return false;
			}else{
				return false;
			}
		} catch (Exception e) {return false;}
	}

	/***1.查询出所有的会员等级类别信息***/
	@SuppressWarnings("unchecked")
	@Override
	public List<MemberGradeInfo> selectAllMemberGrade() {
		try {
			String hql="from MemberGradeInfo where stratu=?";
			return (List<MemberGradeInfo>)session.getCurrentSession().createQuery(hql).setInteger(0, 1).list();
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}

	/*2.根据编号查询单条信息*/
	@Override
	public MemberGradeInfo selectMemberGradeOneByID(int id) {
		try {
			String hql="from MemberGradeInfo where id=?";
			return (MemberGradeInfo) session.getCurrentSession().createQuery(hql).setInteger(0, id).uniqueResult();
		} catch (Exception e) {
			return null;
		}
	}

	/*3.修改会员的信息*/
	@Override
	public boolean updateMemberGradeByOne(MemberGradeInfo membergrade) {
		try {
			session.getCurrentSession().update(membergrade);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
}
