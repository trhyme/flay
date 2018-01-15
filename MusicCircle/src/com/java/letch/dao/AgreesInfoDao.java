package com.java.letch.dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.java.letch.daoimp.AgreesInfoDaoImp;
import com.java.letch.model.AgreesInfo;

/***
 * 点赞信息数据操作类
 * @author Administrator
 * 2017-10-24
 */
@Repository(value="AgreesInfoDao")
public class AgreesInfoDao implements AgreesInfoDaoImp {

	@Resource(name="sessionFactory")
	private SessionFactory session; //数据源
	
	/**1.添加点赞信息**/
	@Override
	public int insertAgressToDB(AgreesInfo agress) {
		//1.查询出总的点赞数
		int count=selectCountNumByID(agress.getUsers_id(), agress.getVoices_id());
		//System.out.println("点赞数据："+count);
		try {
			AgreesInfo ag=selectOneByID(agress.getUsers_id(), agress.getVoices_id());
			if(ag==null){
				//添加
				session.getCurrentSession().save(agress);
				//2.跟新点赞数
				updateVoicesCountNum(agress.getVoices_id(),count+1);
				count=count+1;
			}else{
				//删除点赞
				session.getCurrentSession().delete(ag);
				count=count-1;
				if(count<0){
					count=0;
				}
				updateVoicesCountNum(agress.getVoices_id(),count);
				try {
					//移除点赞消息
					String deleteSQL="delete from comments where users_id=? and voices_id=? and type=4";
					session.getCurrentSession().createSQLQuery(deleteSQL).setInteger(0, agress.getUsers_id()).setInteger(1, agress.getUsers_id()).executeUpdate();
				} catch (Exception e) {
					System.out.println(e);
				}
				return -1;
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return count;
	}

	/**2.根据用户、动态编号查询**/
	@Override
	public AgreesInfo selectOneByID(int userid, int voicesid) {
		try {
			String hql="from AgreesInfo where users_id=? and voices_id=?";
			@SuppressWarnings("unchecked")
			List<AgreesInfo> list=session.getCurrentSession().createQuery(hql).setInteger(0, userid).setInteger(1, voicesid).list();
			if(list!=null && list.size()>0){
				return list.get(0);
			}else{
				return null;
			}
		} catch (Exception e) {
			return null;
		}
	}

	/**3.查询赞的总数**/
	@Override
	public int selectCountNumByID(int userid, int voicesid) {
		try {
			String sql="select count(1) from agrees where voices_id=?";
			Object res=session.getCurrentSession().createSQLQuery(sql).setInteger(0, voicesid).uniqueResult();
			return Integer.parseInt(res.toString());
		} catch (Exception e) {
			return 0;
		}
	}

	/**4.跟新点赞总数**/
	@Override
	public boolean updateVoicesCountNum(int voicesid,int count) {
		try {
			String sql="update voices set fabulous=? where id=?";
			if((session.getCurrentSession().createSQLQuery(sql).setInteger(0, count).setInteger(1, voicesid).executeUpdate())>0){
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}
	
	

}
