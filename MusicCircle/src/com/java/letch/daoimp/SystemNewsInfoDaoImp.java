package com.java.letch.daoimp;

import java.util.Map;

import com.java.letch.model.SystemNewsInfo;

/***
 * 系统消息的数据操作接口
 * @author Administrator
 * 2017-11-16
 */
public interface SystemNewsInfoDaoImp {
	
	/**APP用户查询系统信息根据用户ID**/
	public Map<String, Object> appSelectSystemNewPage(Map<String , Object> map,int userid);
	
	
	/***1.后台查询显示系统信息记录***/
	public Map<String, Object> selectSystemPageList(Map<String, Object> map);
	
	/***2.添加的系统消息的方法***/
	public boolean insertIntoSystemOneToDB(SystemNewsInfo sys);
	
	/***3.根据编号查询单个信息***/
	public SystemNewsInfo selectSystemByOneID(int id);
	
	/***4.删除单条系统信息的方法***/
	public boolean deleteSystemNewsInfoByID(int id,int stratus);
	
}
