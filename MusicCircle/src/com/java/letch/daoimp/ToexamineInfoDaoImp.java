package com.java.letch.daoimp;

import java.util.Map;

import com.java.letch.model.ToexamineInfo;

/***
 * 用户认证信息的数据操作类接口
 * @author Administrator
 * 2017-09-29
 */
public interface ToexamineInfoDaoImp {

	/*分页查询*/
	public Map<String, Object> selectToexamineList(Map<String, Object> map);
	
	/*修改状态的方法*/
	public boolean updateToexamineStratus(int id,int stratus);
	
	/*添加认证信息的方法*/
	public boolean insertToexamOne(ToexamineInfo toexa);
	
	/*添加修改认证的消息*/
	public boolean updatePiclistBy(String userid,String photo);
	
	/***1.根据用户编号查询认证信息***/
	public ToexamineInfo selectToexamByUserid(int userid);
	
	
	/***2.根据用户编号查询认证信息***/
	public ToexamineInfo selectToexamByUseridAll(int userid);
}
