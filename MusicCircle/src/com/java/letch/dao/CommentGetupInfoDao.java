package com.java.letch.dao;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.java.letch.daoimp.CommentGetupInfoDaoImp;
import com.java.letch.model.CommentGetupInfo;
import com.java.letch.model.CommentsInfo;
import com.java.letch.tools.DateHelper;

/***
 * 评论信息点赞的数据操作类
 * @author Administrator
 * 2017-11-17
 */
@Repository(value="CommentGetupInfoDao")
public class CommentGetupInfoDao implements CommentGetupInfoDaoImp {

	@Resource(name="sessionFactory")
	private SessionFactory session; //数据源
	
	/**1.点赞信息**/
	@Override
	public int insertIntoCommentGetup(CommentGetupInfo comup) throws Exception{
		//查询总数
		int count=0;
		String ss="select count(1) from commentgetupinfo where commid="+comup.getCommid();
		Object obj=this.session.getCurrentSession().createSQLQuery(ss).uniqueResult();
		count=Integer.parseInt(obj.toString());
		try {
			//查询信息
			String sql="from CommentGetupInfo where userid=? and commid=?";
			String bb="update comments set tapcount=tapcount+1 where id="+comup.getCommid();
			CommentGetupInfo com=(CommentGetupInfo) this.session.getCurrentSession().createQuery(sql).setInteger(0, comup.getUserid()).setInteger(1, comup.getCommid()).uniqueResult();
			if(com==null){
				this.session.getCurrentSession().save(comup);
				//改变点赞数
				count=count+1;
				this.session.getCurrentSession().createSQLQuery(bb).executeUpdate();
				session.getCurrentSession().saveOrUpdate(comup);
			}else{
				this.session.getCurrentSession().delete(com);
				//改变点赞数
				count=count-1;
				if(count<=0){
					count=0;
				}
				String bb2="update comments set tapcount="+count+" where id="+comup.getCommid();
				this.session.getCurrentSession().createSQLQuery(bb2).executeUpdate();
			}
			
			return count;
		} catch (Exception e) {
			return count;
		}
	}

}
