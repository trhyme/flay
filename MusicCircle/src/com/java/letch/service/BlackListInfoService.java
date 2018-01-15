package com.java.letch.service;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.java.letch.dao.BlackListInfoDao;
import com.java.letch.model.BlackListInfo;
import com.java.letch.serviceimp.BlackListInfoServiceImp;

/***
 * 黑名单的业务操作类
 * @author Administrator
 * 2017-11-10
 */
@Repository(value="BlackListInfoService")
public class BlackListInfoService implements BlackListInfoServiceImp{

	//黑名单数据操作
	@Resource(name="BlackListInfoDao")
	private BlackListInfoDao blacklistinfoDao;
	
	@Override
	public boolean insertBlackListIntoDB(BlackListInfo black) {
		return this.blacklistinfoDao.insertBlackListIntoDB(black);
	}

	//2.分页查询黑名单信息
	@Override
	public Map<String, Object> seelctBlockListPage(Map<String, Object> map, int userid) {
		return this.blacklistinfoDao.seelctBlockListPage(map, userid);
	}

	//3.解除拉黑名单信息
	@Override
	public boolean userBlockRemove(int userid, int blockuserid) {
		return this.blacklistinfoDao.userBlockRemove(userid, blockuserid);
	}

}
