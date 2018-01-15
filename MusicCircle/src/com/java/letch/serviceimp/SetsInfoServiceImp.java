package com.java.letch.serviceimp;

import com.java.letch.model.SetsInfo;

/***
 * 配置信息的业务处理接口
 * @author Administrator
 * 2017-09-21
 */
public interface SetsInfoServiceImp {
	
	/**查询出配置信息**/
	public SetsInfo selectSetsInfo();
	
	/*1.修改货币兑换率信息*/
	public boolean updateRatenb(int id,String ratenb);
	
	/*2.修改用户登录的奖励信息*/
	public boolean updateLoginnb(int id,String loginnb);
	
	/*3.修改用户的偷听/看的所需金币*/
	public boolean updateTapnb(int id,String tapnb);

	/*4.修改用户发单信息*/
	public boolean updateRewardnbs(int id,String rewardnb);
	
	/*5.修改蓝砖与1元人民币的比例*/
	public boolean updateBluebrick(int id,String bluebrick);
	
	/*6.修改红砖与1元人民币的比例*/
	public boolean updateRedsbrick(int id,String redsbrick);
	
	/**7.修改蓝砖与红砖的兑换比率**/
	public boolean updateRates(int id,String bluerates,String redsrates);
	
}
