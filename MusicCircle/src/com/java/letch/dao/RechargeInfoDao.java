package com.java.letch.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.java.letch.daoimp.RechargeInfoDaoImp;
import com.java.letch.model.MenuInfo;
import com.java.letch.model.RechargeInfo;

/***
 * 用户会员充值信息数据操作类
 * @author Administrator
 * 2017-09-29
 */
@Repository(value="RechargeInfoDao")
public class RechargeInfoDao implements RechargeInfoDaoImp {

	@Resource(name="sessionFactory")
	private SessionFactory session;		//数据源
	
	/*分页查询信息*/
	@Override
	public Map<String, Object> selectRechargeInfoPage(Map<String, Object> map) {
		try {
			//查询总条数的SQL
			StringBuffer sqlnum=new StringBuffer("select count(*) from rechargeinfo where 1=1");
			//分页查询HQL
			StringBuffer hqlstr=new StringBuffer("from RechargeInfo as b where 1=1");
			
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
			map.put("rechargelist", list);	//每页的数据
			return map;
		} catch (Exception e) {}
		
		return null;
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

	/**1.添加操作**/
	@Override
	public boolean insertRechargeToDB(RechargeInfo rech) {
		try {
			session.getCurrentSession().save(rech);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**2.根据订单编号查询信息**/
	@Override
	public RechargeInfo selectRechargeByOrderID(String orderid) {
		try {
			String hql="from RechargeInfo where orderid=?";
			return (RechargeInfo) session.getCurrentSession().createQuery(hql).setString(0, orderid).uniqueResult();
		} catch (Exception e) {
			return null;
		}
		
	}
	
	/**3.根据编号修改会员状态**/
	@Override
	public boolean updateRechargeByOrderID(String orderid) {
		try {
			String sql="update rechargeinfo set stratus=1 where orderid=?";
			session.getCurrentSession().createSQLQuery(sql).setString(0, orderid).executeUpdate();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
}
