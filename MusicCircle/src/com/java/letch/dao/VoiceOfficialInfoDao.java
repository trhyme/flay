package com.java.letch.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.java.letch.daoimp.VoiceOfficialInfoDaoImp;
import com.java.letch.model.VoiceOfficialInfo;

/***
 * 声音录制文案数据操作类
 * @author Administrator
 * 2017-10-31
 */
@Repository(value="VoiceOfficialInfoDao")
public class VoiceOfficialInfoDao implements VoiceOfficialInfoDaoImp {

	@Resource(name="sessionFactory")
	private SessionFactory session;		//数据源
	
	//查看录制声音的文案消息
	@Override
	public Map<String, Object> selectVoicesOfficialPage(Map<String, Object> map) {
		try {
			//查询总条数的SQL
			StringBuffer sqlnum=new StringBuffer("select count(*) from voiceofficialinfo m where m.strus=?");
			//分页查询HQL
			StringBuffer hqlstr=new StringBuffer("from VoiceOfficialInfo as b where b.strus=?");
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
			List<VoiceOfficialInfo> list=query.list();
			map.put("nowpage",page);	//当前页数
			map.put("counts", count);	//总条数
			map.put("pagecount", pagenum);		//每页条数
			map.put("page",countpage);	//总页数
			map.put("voicesofferlist", list);	//每页的数据
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
	
	//添加录制声音文案信息
	@Override
	public boolean insertIntoVoicesOffer(VoiceOfficialInfo voicesoff) {
		try {
			session.getCurrentSession().saveOrUpdate(voicesoff);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	//根据编号查询出单挑信息
	@Override
	public VoiceOfficialInfo selectVoicesOffByID(int id) {
		try {
			String hql="from VoiceOfficialInfo where id=?";
			return (VoiceOfficialInfo) session.getCurrentSession().createQuery(hql).setInteger(0, id).uniqueResult();
		} catch (Exception e) {
			return null;
		}
	}
	
	//根据编号修改状态
	@Override
	public boolean updateStatusByID(int id, int stratus) {
		try {
			String sql="update voiceofficialinfo set strus=? where id=?";
			int num=session.getCurrentSession().createSQLQuery(sql).setInteger(0, stratus).setInteger(1, id).executeUpdate();
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
	
	/***1.根据数量随机查询出文案信息***/
	@SuppressWarnings("unchecked")
	@Override
	public List<VoiceOfficialInfo> selectVoicesByNum(int num,int id) {
		try {
			if(num<0){
				num=1;
			}
			String sql="select * from voiceofficialinfo where id <> ? order by rand() LIMIT ?";
			return this.session.getCurrentSession().createSQLQuery(sql).addEntity(VoiceOfficialInfo.class).setInteger(0, id).setInteger(1, num).list();
		} catch (Exception e) {
			return null;
		}
	}
	
}
