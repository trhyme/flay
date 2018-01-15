package com.java.letch.dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.java.letch.daoimp.CollectionInfoDaoImp;
import com.java.letch.model.AgreesInfo;
import com.java.letch.model.CollectionInfo;

/***
 * 收藏信息的数据操作类
 * @author Administrator
 * 2017-10-24
 */
@Repository(value="CollectionInfoDao")
public class CollectionInfoDao implements CollectionInfoDaoImp {

	@Resource(name="sessionFactory")
	private SessionFactory session; //数据源
	
	//1.添加信息
	@Override
	public int insertCollectionToDB(CollectionInfo coll) {
		//查询出总的收藏数
		int count=selectCountNumByID(coll.getUserid(), coll.getVoicesid());
		if(count<=0){
			count=0;
		}
		try {
			CollectionInfo cl=selectOneConll(coll.getUserid(), coll.getVoicesid());
			if(cl==null){
				session.getCurrentSession().save(coll);
				count=count+1;
				//跟新总数
				updateVoicesCollNum(coll.getVoicesid(), count);
				
			}else{
				//删除收藏
				session.getCurrentSession().delete(cl);
				count=count-1;
				if(count<=0){
					count=0;
				}
				//跟新总数
				updateVoicesCollNum(coll.getVoicesid(), count);
			}
			return count;
		} catch (Exception e) {
			System.out.println(e);
			return 0;
		}
		
	}

	//2.根据语音动态编号查询出评论信息
	@Override
	public CollectionInfo selectOneConll(int userid, int cid) {
		try {
			String hql="from CollectionInfo where userid=? and voicesid=?";
			@SuppressWarnings("unchecked")
			List<CollectionInfo> list=session.getCurrentSession().createQuery(hql).setInteger(0, userid).setInteger(1, cid).list();
			if(list!=null && list.size()>0){
				return list.get(0);
			}else{
				return null;
			}
		} catch (Exception e) {
			return null;
		}
	}

	//3.查询总的收藏数
	@Override
	public int selectCountNumByID(int userid, int voicesid) {
		try {
			String sql="select count(1) from collectioninfo where voicesid=?";
			Object res=session.getCurrentSession().createSQLQuery(sql).setInteger(0, voicesid).uniqueResult();
			return Integer.parseInt(res.toString());
		} catch (Exception e) {
			return 0;
		}
	}
	
	//4.根据编号修改总的收藏数
	@Override
	public boolean updateVoicesCollNum(int voicesid, int count) {
		String sql="update voices set collection=? where id=?";
		System.out.println("收藏Voices编号："+voicesid);
		try {
			session.getCurrentSession().createSQLQuery(sql).setInteger(0, count).setInteger(1, voicesid).executeUpdate();
			return true;
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
	}

}
