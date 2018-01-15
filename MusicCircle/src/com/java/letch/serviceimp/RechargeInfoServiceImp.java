package com.java.letch.serviceimp;

import java.util.Map;

import com.java.letch.model.RechargeInfo;

/***
 * 用户会员充值信息业务操作类接口
 * @author Administrator
 * 2017-09-29 
 */
public interface RechargeInfoServiceImp {
	
	/*分页查询信息*/
	public Map<String, Object> selectRechargeInfoPage(Map<String, Object> map);
	
	/**1.添加操作**/
	public boolean insertRechargeToDB(RechargeInfo rech);
	
	/**2.根据订单编号查询信息**/
	public RechargeInfo selectRechargeByOrderID(String orderid);
	
	/**3.根据编号修改会员状态**/
	public boolean updateRechargeByOrderID(String orderid);
	
}
