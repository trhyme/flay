package com.java.letch.service;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.java.letch.dao.SystemNewsInfoDao;
import com.java.letch.model.SystemNewsInfo;
import com.java.letch.serviceimp.SystemNewsInfoServiceImp;

/***
 * 系统消息的数据业务操作类
 * @author Administrator
 * 2017-11-16
 */
@Repository(value="SystemNewsInfoService")
public class SystemNewsInfoService implements SystemNewsInfoServiceImp {

	@Resource(name="SystemNewsInfoDao")				
	private SystemNewsInfoDao systemnewsinfoDao;	//系统消息数据操作类
	
	/**APP用户查询系统信息根据用户ID**/
	@Override
	public Map<String, Object> appSelectSystemNewPage(Map<String, Object> map, int userid) {
		return this.systemnewsinfoDao.appSelectSystemNewPage(map, userid);
	}

	/***1.后台查询显示系统信息记录***/
	@Override
	public Map<String, Object> selectSystemPageList(Map<String, Object> map) {
		return this.systemnewsinfoDao.selectSystemPageList(map);
	}

	/***2.添加的系统消息的方法***/
	@Override
	public boolean insertIntoSystemOneToDB(SystemNewsInfo sys) {
		return this.systemnewsinfoDao.insertIntoSystemOneToDB(sys);
	}

	/***3.根据编号查询单个信息***/
	@Override
	public SystemNewsInfo selectSystemByOneID(int id) {
		return this.systemnewsinfoDao.selectSystemByOneID(id);
	}

	/***4.删除单条系统信息的方法***/
	@Override
	public boolean deleteSystemNewsInfoByID(int id, int stratus) {
		return this.systemnewsinfoDao.deleteSystemNewsInfoByID(id, stratus);
	}

}
