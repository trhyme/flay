package com.java.letch.service;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.java.letch.dao.RechargeInfoDao;
import com.java.letch.model.RechargeInfo;
import com.java.letch.serviceimp.RechargeInfoServiceImp;

/***
 * 用户会员充值信息业务操作类
 * @author Administrator
 * 2017-09-29
 */
@Repository(value="RechargeInfoService")
public class RechargeInfoService implements RechargeInfoServiceImp {

	@Resource(name="RechargeInfoDao")
	private RechargeInfoDao rechargeinfoDao;		//用户会员充值信息数据操作类
	
	/*分页查询信息*/
	@Override
	public Map<String, Object> selectRechargeInfoPage(Map<String, Object> map) {
		return this.rechargeinfoDao.selectRechargeInfoPage(map);
	}

	/**1.添加操作**/
	@Override
	public boolean insertRechargeToDB(RechargeInfo rech) {
		return this.rechargeinfoDao.insertRechargeToDB(rech);
	}

	/**2.根据订单编号查询信息**/
	@Override
	public RechargeInfo selectRechargeByOrderID(String orderid) {
		return this.rechargeinfoDao.selectRechargeByOrderID(orderid);
	}

	/**3.根据编号修改会员状态**/
	@Override
	public boolean updateRechargeByOrderID(String orderid) {
		return this.rechargeinfoDao.updateRechargeByOrderID(orderid);
	}

}
