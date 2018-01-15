package com.java.letch.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.java.letch.dao.SetsInfoDao;
import com.java.letch.model.SetsInfo;
import com.java.letch.serviceimp.SetsInfoServiceImp;

/***
 * 配置信息设置的业务操作类
 * @author Administrator
 * 2017-09-21
 */
@Repository(value="SetsInfoService")
public class SetsInfoService implements SetsInfoServiceImp{

	@Resource(name="SetsInfoDao")
	private SetsInfoDao setsInfoDao;		//配置信息的数据操作类
	
	/**查询出配置信息**/
	@Override
	public SetsInfo selectSetsInfo() {
		return this.setsInfoDao.selectSetsInfo();
	}

	/*1.修改货币兑换率信息*/
	@Override
	public boolean updateRatenb(int id, String ratenb) {
		return this.setsInfoDao.updateRatenb(id, ratenb);
	}

	/*2.修改用户登录的奖励信息*/
	@Override
	public boolean updateLoginnb(int id, String loginnb) {
		return this.setsInfoDao.updateLoginnb(id, loginnb);
	}

	/*3.修改用户的偷听/看的所需金币*/
	@Override
	public boolean updateTapnb(int id, String tapnb) {
		return this.setsInfoDao.updateTapnb(id, tapnb);
	}

	/*4.修改用户发单信息*/
	@Override
	public boolean updateRewardnbs(int id, String rewardnb) {
		return this.setsInfoDao.updateRewardnbs(id, rewardnb);
	}

	/*5.修改蓝砖与1元人民币的比例*/
	@Override
	public boolean updateBluebrick(int id, String bluebrick) {
		return this.setsInfoDao.updateBluebrick(id, bluebrick);
	}

	/*6.修改蓝砖与1元人民币的比例*/
	@Override
	public boolean updateRedsbrick(int id, String redsbrick) {
		return this.setsInfoDao.updateRedsbrick(id, redsbrick);
	}

	/**7.修改蓝砖与红砖的兑换比率**/
	@Override
	public boolean updateRates(int id, String bluerates, String redsrates) {
		return this.setsInfoDao.updateRates(id, bluerates, redsrates);
	}
	
}
