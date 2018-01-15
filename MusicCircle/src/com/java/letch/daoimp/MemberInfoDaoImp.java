package com.java.letch.daoimp;

import java.util.Map;

import com.java.letch.model.MemberInfo;

/***
 * 用户会员信息的数据操作接口类
 * @author Administrator
 * 2017-09-28
 */
public interface MemberInfoDaoImp {
	
	/**分页查询数据信息**/
	public Map<String, Object> selectMemberPage(Map<String, Object> map);
	
	/**1.根据用户的编号查询会员信息**/
	public MemberInfo selectMemberOneByID(int userid);
	
	//查询出会员的总数量
	public int selectMemberCount(int strtus);
	
	/***2.添加数据***/
	public boolean insertIntoMemberInfoDB(MemberInfo member);
	
}
