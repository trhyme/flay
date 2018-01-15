package com.java.letch.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.java.letch.dao.AgreesInfoDao;
import com.java.letch.model.AgreesInfo;
import com.java.letch.serviceimp.AgreesInfoServiceImp;

/***
 * 点赞业务操作类
 * @author Administrator
 * 2017-10-24
 */
@Repository(value="AgreesInfoService")
public class AgreesInfoService implements AgreesInfoServiceImp{

	@Resource(name="AgreesInfoDao")
	private AgreesInfoDao agreesinfoDao;		//点赞数据操作
	
	/**1.添加点赞信息**/
	@Override
	public int insertAgressToDB(AgreesInfo agress) {
		return this.agreesinfoDao.insertAgressToDB(agress);
	}

	/**2.根据用户、动态编号查询**/
	@Override
	public AgreesInfo selectOneByID(int userid, int voicesid) {
		return this.agreesinfoDao.selectOneByID(userid, voicesid);
	}

	/**3.查询赞的总数**/
	@Override
	public int selectCountNumByID(int userid, int voicesid) {
		return this.agreesinfoDao.selectCountNumByID(userid, voicesid);
	}

	/**4.跟新点赞总数**/
	@Override
	public boolean updateVoicesCountNum(int voicesid, int count) {
		return this.agreesinfoDao.updateVoicesCountNum(voicesid, count);
	}
	
}
