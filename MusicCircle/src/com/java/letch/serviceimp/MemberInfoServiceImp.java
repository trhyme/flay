package com.java.letch.serviceimp;

import java.util.Map;

import com.java.letch.model.MemberInfo;

/***
 * 用户会员信息的业务操作接口类
 * @author Administrator
 * 2017-09-28
 */
public interface MemberInfoServiceImp {
	
	/**分页查询数据信息**/
	public Map<String, Object> selectMemberPage(Map<String, Object> map);
	
	/**1.根据用户的编号查询会员信息**/
	public MemberInfo selectMemberOneByID(int userid);
	
	//查询出会员的总数量
	public int selectMemberCount(int strtus);
	
	/***2.添加数据***/
	public boolean insertIntoMemberInfoDB(MemberInfo member);
	
	/***判断用户是否为会员***/
	public int checkMemberByUserID(int userid) throws Exception;
}
