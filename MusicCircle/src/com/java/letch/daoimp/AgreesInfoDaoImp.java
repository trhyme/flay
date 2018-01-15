package com.java.letch.daoimp;

import com.java.letch.model.AgreesInfo;

/***
 * 点赞信息接口
 * @author Administrator
 * 2017-10-24
 */
public interface AgreesInfoDaoImp {
	
	/**1.添加点赞信息**/
	public int insertAgressToDB(AgreesInfo agress);
	
	/**2.根据用户、动态编号查询**/
	public AgreesInfo selectOneByID(int userid,int voicesid);
	
	/**3.查询赞的总数**/
	public int selectCountNumByID(int userid,int voicesid);
	
	/**4.跟新点赞总数**/
	public boolean updateVoicesCountNum(int voicesid,int count);
	
}
